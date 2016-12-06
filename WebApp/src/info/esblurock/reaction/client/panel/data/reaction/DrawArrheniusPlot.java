package info.esblurock.reaction.client.panel.data.reaction;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ChartType;
import com.googlecode.gwt.charts.client.ChartWrapper;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.controls.Dashboard;
import com.googlecode.gwt.charts.client.controls.filter.ChartRangeFilter;
import com.googlecode.gwt.charts.client.controls.filter.ChartRangeFilterOptions;
import com.googlecode.gwt.charts.client.controls.filter.ChartRangeFilterState;
import com.googlecode.gwt.charts.client.controls.filter.ChartRangeFilterStateRange;
import com.googlecode.gwt.charts.client.controls.filter.ChartRangeFilterUi;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.AxisTitlesPosition;
import com.googlecode.gwt.charts.client.options.ChartArea;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendPosition;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.googlecode.gwt.charts.client.options.HAxis;
import info.esblurock.reaction.data.chemical.reaction.ChemkinCoefficientsData;

public class DrawArrheniusPlot  extends DockLayoutPanel {

	ChemkinCoefficientsData coefficients;
	
	public DrawArrheniusPlot(ChemkinCoefficientsData coeffs) {
		super(Unit.PX);
		this.coefficients = coeffs;
		initialize();
	}
	Dashboard dashboard;
	ChartWrapper<LineChartOptions> lineChart;
	ChartRangeFilter numberRangeFilter;
	
	private void initialize() {
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CONTROLS);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				addNorth(getDashboardWidget(), 400);
				addSouth(getNumberRangeFilter(), 100);
				add(getLineChart());
				draw();
			}
		});
	}
	
	private Dashboard getDashboardWidget() {
		if (dashboard == null) {
			dashboard = new Dashboard();
		}
		return dashboard;
	}

	private ChartWrapper<LineChartOptions> getLineChart() {
		if (lineChart == null) {
			lineChart = new ChartWrapper<LineChartOptions>();
			lineChart.setChartType(ChartType.LINE);
		}
		return lineChart;
	}

	private ChartRangeFilter getNumberRangeFilter() {
		if (numberRangeFilter == null) {
			numberRangeFilter = new ChartRangeFilter();
		}
		return numberRangeFilter;
	}

	private void draw() {
		// Set control options
		ChartRangeFilterOptions chartRangeFilterOptions = ChartRangeFilterOptions.create();
		chartRangeFilterOptions.setFilterColumnIndex(0); // Filter by the date axis
		LineChartOptions controlChartOptions = LineChartOptions.create();
		controlChartOptions.setHeight(100);
		ChartArea chartArea = ChartArea.create();
		chartArea.setWidth("500px");
		chartArea.setHeight("300px");
		controlChartOptions.setChartArea(chartArea);
		ChartRangeFilterUi chartRangeFilterUi = ChartRangeFilterUi.create();
		chartRangeFilterUi.setChartType(ChartType.LINE);
		chartRangeFilterUi.setChartOptions(controlChartOptions);
		chartRangeFilterOptions.setUi(chartRangeFilterUi);
		ChartRangeFilterStateRange stateRange = ChartRangeFilterStateRange.create();
		
		double tmin = 300;
		double tmax = 3000;
		double tmininv = 1.0/tmax;
		double tmaxinv = 1.0/tmin;
		double trange = 0.50*(tmaxinv+tmininv);
		
		chartRangeFilterUi.setMinRangeSize(trange); // 2 days in milliseconds
		stateRange.setStart(tmininv);
		stateRange.setEnd(tmaxinv);
		
		ChartRangeFilterState controlState = ChartRangeFilterState.create();
		controlState.setRange(stateRange);
		numberRangeFilter.setState(controlState);
		numberRangeFilter.setOptions(chartRangeFilterOptions);

		// Set chart options
		LineChartOptions lineChartOptions = LineChartOptions.create();
		//VAxis vaxis = VAxis.create();
		//VAxis vaxis = VAxis.create();
		//vaxis.setLogScale(true);
		//lineChartOptions.setVAxis(vaxis);
		lineChartOptions.setLineWidth(3);
		lineChartOptions.setLegend(Legend.create(LegendPosition.NONE));
		lineChartOptions.setChartArea(chartArea);
		lineChartOptions.setAxisTitlesPosition(AxisTitlesPosition.OUT);
		lineChart.setChartType(ChartType.LINE);
		lineChart.setHeight("400px");
		lineChart.setVisible(true);
		lineChart.setWidth("400px");
		lineChart.setOptions(lineChartOptions);
		
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.NUMBER, "1/temperature");
		dataTable.addColumn(ColumnType.NUMBER, "ln(k)");
		dataTable.addRows(200);
		
		double A = Double.parseDouble(coefficients.getA());
		double logA = Math.log10(A);
		double Ea = Double.parseDouble(coefficients.getEa());
		double R = 1.9872036;
		double EoverR = Ea/R;
		double n = Double.parseDouble(coefficients.getN());
		
		double tinvinterval = (tmaxinv - tmininv)/200.0;
		double tinv = 0.0;
		for(int count = 0; count < 200; count++) {
			double T = 1.0/tinv;
			double k = A * Math.pow(T, n)*Math.exp(-EoverR/T);
			double logT = Math.log10(T);
			double lnk = logA + EoverR * tinv + n*logT;
			dataTable.setValue(count, 0, tinv);
			dataTable.setValue(count, 1, lnk);
			tinv += tinvinterval;
		}
		dashboard.bind(numberRangeFilter, lineChart);
		dashboard.draw(dataTable);
	}	

}
