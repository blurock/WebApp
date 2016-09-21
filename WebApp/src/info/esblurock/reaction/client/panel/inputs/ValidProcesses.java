package info.esblurock.reaction.client.panel.inputs;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

import gwt.material.design.client.base.MaterialButtonCell;
import gwt.material.design.client.constants.ModalType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import info.esblurock.reaction.client.FindShortNameFromString;
import info.esblurock.reaction.client.panel.transaction.TransactionService;
import info.esblurock.reaction.client.panel.transaction.TransactionServiceAsync;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.client.resources.ProcessDescriptions;

public class ValidProcesses extends Composite implements HasText {

	private static ValidProcessesUiBinder uiBinder = GWT.create(ValidProcessesUiBinder.class);

	interface ValidProcessesUiBinder extends UiBinder<Widget, ValidProcesses> {
	}

	String keyword;
	MaterialModal modal;
	
	public ValidProcesses(String keyword, MaterialModal modal) {
		initWidget(uiBinder.createAndBindUi(this));
		sortDataHandler = new ListHandler<String>(orders);
		setGrid(new ArrayList<String>());
		getAllString();
		dataGrid.setStyleName("striped responsive-table");
		this.keyword = keyword;
		this.modal = modal;
	}
	
	InterfaceConstants interfaceConstants = GWT.create(InterfaceConstants.class);
	ProcessDescriptions processDescriptions = GWT.create(ProcessDescriptions.class);
	private List<String> orders = new ArrayList<String>();
	private DataGrid<String> dataGrid;
	private ListDataProvider<String> StringProvider = new ListDataProvider<String>();
	private ListHandler<String> sortDataHandler;
	private final ProvidesKey<String> KEY_PROVIDER = new ProvidesKey<String>() {

	@Override
		public Object getKey(String item) {
			return item;
		}
	};
	private final SelectionModel<String> selectionModel 
		= new MultiSelectionModel<String>(KEY_PROVIDER);

	private String processName;

	@UiField
	SimplePanel gridPanel;
	SimplePanel pagerPanel;

	public void setGrid(List<String> orders) {
		if(orders != null)
			this.orders = orders;
		dataGrid = createDatagrid();
		
		gridPanel.clear();
		gridPanel.setWidget(dataGrid);
		StringProvider.setList(orders);
		sortDataHandler.setList(StringProvider.getList());
	}

	Column<String, MaterialButton> processButton() {
		// ACTION BUTTON (key)
		Column<String, MaterialButton> processbutton = new Column<String, MaterialButton>(
				new MaterialButtonCell()) {
			@Override
			public MaterialButton getValue(String object) {
				MaterialButton button = new MaterialButton();
				button.setText(object);
				button.setTextColor("blue-text text-darken-2 light-blue lighten-5");
				button.setWaves(WavesType.LIGHT);
				button.setTooltip(interfaceConstants.processtooltip());
				button.getElement().getStyle().setProperty("display", "inline-flex");
				String shortname = FindShortNameFromString.findShortName(object, ".");
				button.setTooltip(shortname);
				button.setText(interfaceConstants.process());
				return button;
			}
		};
		processbutton.setFieldUpdater(new FieldUpdater<String, MaterialButton>() {
					@Override
					public void update(int index, String processName,MaterialButton value) {
						String name = value.getTooltip();
						ValidProcessesRunCallback callback = new ValidProcessesRunCallback();
						TransactionServiceAsync async = TransactionService.Util.getInstance();
						async.runProcess(name, keyword, callback);
						modal.closeModal();
					}
				});
		return processbutton;
	}
	
	TextColumn<String> oneLine() {
		// Source sourceKey
		TextColumn<String> colOneline = new TextColumn<String>() {
			@Override
			public String getValue(String object) {
				String shortname = FindShortNameFromString.findShortName(object, ".");
				String description = shortname;
				ProcessDescriptionsForInterface desc = ProcessDescriptionsForInterface.valueOf(shortname);
				if(desc != null) {
					description = desc.getDescription();
				}
				return description;
			}
		};
		colOneline.setSortable(false);
		return colOneline;
	}
	private DataGrid<String> createDatagrid() {
		Column<String, MaterialButton> processbutton = processButton();
		TextColumn<String> colOneline = oneLine();

		final DataGrid<String> dataGrid = new DataGrid<String>(100, KEY_PROVIDER);
		dataGrid.setSize("100%", "40vh");

		dataGrid.addColumn(processbutton, interfaceConstants.action());
		dataGrid.addColumn(colOneline, "Description");

		dataGrid.setStyleName("striped responsive-table");

		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER,
				pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);
		//pagerPanel.add(pager);
		StringProvider = new ListDataProvider<String>();
		StringProvider.addDataDisplay(dataGrid);
		dataGrid.addColumnSortHandler(sortDataHandler);
		return dataGrid;
	}

	private void getAllString() {
		StringProvider.setList(orders);
		sortDataHandler.setList(StringProvider.getList());
		
		//StringCallback callback = new StringCallback(this);
		//async.getAllUploadTransactions(callback);
		
	}

	public void openModal(ModalType type) {
		modal.setType(type);
		modal.openModal();
	}
	
	public void openModal() {
		modal.openModal();
	}
	public String getProcessName() {
		return processName;
	}

	public void setTextSetUploadData(String processName) {
		this.processName = processName;
	}

	public void addString(String source) {
		if(source == null) {
			Window.alert("null");	
		} else {
			orders.add(source);
		}
		
	}
	public List<String> getTransactions() {
		return orders;
	}
	public void refresh() {
		StringProvider.setList(orders);
		sortDataHandler.setList(StringProvider.getList());
	}
	@Override
	public String getText() {
		return null;
	}

	@Override
	public void setText(String text) {
	}

}
