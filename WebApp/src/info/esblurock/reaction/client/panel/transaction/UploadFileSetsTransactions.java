package info.esblurock.reaction.client.panel.transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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

import gwt.material.design.client.custom.MaterialButtonCell;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.data.upload.InputTextSource;
import info.esblurock.reaction.data.upload.TextSetUploadData;

public class UploadFileSetsTransactions extends Composite implements HasText {

	private static UploadFileSetsTransactionsUiBinder uiBinder = GWT.create(UploadFileSetsTransactionsUiBinder.class);

	interface UploadFileSetsTransactionsUiBinder extends UiBinder<Widget, UploadFileSetsTransactions> {
	}

	public UploadFileSetsTransactions() {
		initWidget(uiBinder.createAndBindUi(this));
		sortDataHandler = new ListHandler<TextSetUploadData>(orders);
		setGrid(new ArrayList<TextSetUploadData>());
		getAllTextSetUploadData();
		dataGrid.setStyleName("striped responsive-table");
	}

	TransactionServiceAsync async = TransactionService.Util.getInstance();

	InterfaceConstants interfaceConstants = GWT.create(InterfaceConstants.class);
	private List<TextSetUploadData> orders = new ArrayList<TextSetUploadData>();
	private DataGrid<TextSetUploadData> dataGrid;
	private ListDataProvider<TextSetUploadData> TextSetUploadDataProvider = new ListDataProvider<TextSetUploadData>();
	private ListHandler<TextSetUploadData> sortDataHandler;
	private final ProvidesKey<TextSetUploadData> KEY_PROVIDER = new ProvidesKey<TextSetUploadData>() {

	@Override
		public Object getKey(TextSetUploadData item) {
			return item.getDescription().getInputkey();
		}
	};
	private final SelectionModel<TextSetUploadData> selectionModel = new MultiSelectionModel<TextSetUploadData>(
			KEY_PROVIDER);

	private TextSetUploadData TextSetUploadData;

	@UiField
	SimplePanel gridPanel, pagerPanel;

	public void setGrid(List<TextSetUploadData> orders) {
		if(orders != null)
			this.orders = orders;
		dataGrid = createDatagrid();
		
		gridPanel.clear();
		gridPanel.setWidget(dataGrid);
		TextSetUploadDataProvider.setList(orders);
		sortDataHandler.setList(TextSetUploadDataProvider.getList());
	}

	Column<TextSetUploadData, MaterialButton> processButton() {
		// ACTION BUTTON (key)
		Column<TextSetUploadData, MaterialButton> processbutton = new Column<TextSetUploadData, MaterialButton>(
				new MaterialButtonCell()) {
			@Override
			public MaterialButton getValue(TextSetUploadData object) {
				MaterialButton button = new MaterialButton(object.getDescription().getInputkey(),
						"blue-text text-darken-2 light-blue lighten-5",
						"light");
				button.setTooltip(interfaceConstants.processtooltip());
				button.setIcon("mdi-action-list");
				button.setIconPosition("left");
				button.getElement().getStyle()
						.setProperty("display", "inline-flex");
				button.setText(object.getDescription().getInputkey());
				
				return button;
			}

		};

		processbutton.setFieldUpdater(new FieldUpdater<TextSetUploadData, MaterialButton>() {

					@Override
					public void update(int index, TextSetUploadData object,
							MaterialButton value) {
						UploadFileTransaction popup = new UploadFileTransaction(object);
						MaterialModal.showModal(popup, TYPE.FIXED_FOOTER);
					}
				});
		return processbutton;
	}
	
	TextColumn<TextSetUploadData> oneLine() {
		// Source sourceKey
		TextColumn<TextSetUploadData> colOneline = new TextColumn<TextSetUploadData>() {
			@Override
			public String getValue(TextSetUploadData object) {

				return object.getDescription().getOnlinedescription();
			}
		};
		colOneline.setSortable(false);
		sortDataHandler.setComparator(colOneline,
				new Comparator<TextSetUploadData>() {

					@Override
					public int compare(TextSetUploadData o1,
							TextSetUploadData o2) {

						return o1.getDescription().getOnlinedescription().compareTo(o2.getDescription().getOnlinedescription());
					}
				});
		return colOneline;
	}
	TextColumn<TextSetUploadData> sizeOfSets() {
		// Size

		TextColumn<TextSetUploadData> colSize = new TextColumn<TextSetUploadData>() {
			@Override
			public String getValue(TextSetUploadData object) {
				String numS = "empty";
				ArrayList<InputTextSource> lst = object.getInputTextSources();
				if(lst != null) {
					Integer numI = new Integer(lst.size());
					numS = numI.toString();
				}
				return numS;
			}
		};
		colSize.setSortable(true);
		sortDataHandler.setComparator(colSize,
				new Comparator<TextSetUploadData>() {

					@Override
					public int compare(TextSetUploadData o1,
							TextSetUploadData o2) {
						int ans = 0;
						ArrayList<InputTextSource> lst1 = o1.getInputTextSources();
						ArrayList<InputTextSource> lst2 = o2.getInputTextSources();
						if(lst1 == null) {
							if(lst2 == null) {
								ans = 0;
							} else {
								ans = -1;
							}
						} else {
							if(lst2 == null) {
								ans = 1;
							} else {
								if(lst1.size() == lst1.size()) {
									ans = 0;
								} else if(lst1.size() > lst1.size()) {
									ans = 1;
								} else {
									ans = -1;
								}
							}
						}
						return ans;
					}
				});
		return colSize;
	}
	
	TextColumn<TextSetUploadData> sourceKey() {
		// Type

		TextColumn<TextSetUploadData> colSource = new TextColumn<TextSetUploadData>() {
			@Override
			public String getValue(TextSetUploadData object) {

				return object.getDescription().getSourcekey();
			}
		};
		colSource.setSortable(true);
		sortDataHandler.setComparator(colSource,
				new Comparator<TextSetUploadData>() {

					@Override
					public int compare(TextSetUploadData o1,
							TextSetUploadData o2) {

						return o1.getDescription().getSourcekey().compareTo(o2.getDescription().getSourcekey());
					}
				});
		return colSource;
	}
	
	TextColumn<TextSetUploadData> sourceDate() {
		// Enter Date
		TextColumn<TextSetUploadData> colSourceDate = new TextColumn<TextSetUploadData>() {
			@Override
			public String getValue(TextSetUploadData object) {
				String ans = "---";
				Date date = object.getDescription().getCreationDate();
				if(date != null) {
					ans = date.toString();
				}
				return ans;
			}
		};
		colSourceDate.setSortable(true);
		sortDataHandler.setComparator(colSourceDate,
				new Comparator<TextSetUploadData>() {
					@Override
					public int compare(TextSetUploadData o1,
							TextSetUploadData o2) {
						Date o1date = o1.getDescription().getCreationDate();
						Date o2date = o2.getDescription().getCreationDate();

						int ans = 0;
						if(o1date == null){
							if(o2date == null) 
								ans = 0;
							else 
								ans = -1;
						} else if(o2date == null) {
							ans = 1;
						} else {
							ans = o1date.compareTo(o2date);
						}
						return ans;
					}
				});
		return colSourceDate;
	}
	
	TextColumn<TextSetUploadData> creationDate() {
		// Source Date
		TextColumn<TextSetUploadData> colCreationDate = new TextColumn<TextSetUploadData>() {
			@Override
			public String getValue(TextSetUploadData object) {
				String ans = "---";
				if(object.getCreationDate() != null) {
					ans = object.getCreationDate().toString();
				}
				return ans;
			}
		};
		colCreationDate.setSortable(true);
		sortDataHandler.setComparator(colCreationDate,
				new Comparator<TextSetUploadData>() {

					@Override
					public int compare(TextSetUploadData o1,
							TextSetUploadData o2) {
						int ans = 0;
						if(o1.getCreationDate() == null){
							if(o2.getCreationDate() == null) 
								ans = 0;
							else 
								ans = -1;
						} else if(o2.getCreationDate() == null) {
							ans = 1;
						} else {
							ans = o1.getCreationDate().compareTo(o2.getCreationDate());
						}
						return ans;
					}
				});
		return colCreationDate;
	}
	private DataGrid<TextSetUploadData> createDatagrid() {
		
		Column<TextSetUploadData, MaterialButton> processbutton = processButton();
		TextColumn<TextSetUploadData> colOneline = oneLine();
		TextColumn<TextSetUploadData> colSize = sizeOfSets();
		TextColumn<TextSetUploadData> colSource = sourceKey();
		TextColumn<TextSetUploadData> colSourceDate = sourceDate();
		TextColumn<TextSetUploadData> colCreationDate = creationDate();

		final DataGrid<TextSetUploadData> dataGrid = new DataGrid<TextSetUploadData>(100, KEY_PROVIDER);
		dataGrid.setSize("100%", "40vh");

		dataGrid.addColumn(processbutton, interfaceConstants.action());
		dataGrid.addColumn(colSourceDate, interfaceConstants.sourcedate());
		dataGrid.addColumn(colOneline, "Description");
		dataGrid.addColumn(colSize, "# Files");
		dataGrid.addColumn(colSource, "Entry");
		dataGrid.addColumn(colCreationDate, interfaceConstants.entrydate());

		dataGrid.setStyleName("striped responsive-table");

		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER,
				pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);
		pagerPanel.add(pager);

		TextSetUploadDataProvider = new ListDataProvider<TextSetUploadData>();
		TextSetUploadDataProvider.addDataDisplay(dataGrid);
		dataGrid.addColumnSortHandler(sortDataHandler);

		return dataGrid;
	}

	private void getAllTextSetUploadData() {
		TextSetUploadDataProvider.setList(orders);
		sortDataHandler.setList(TextSetUploadDataProvider.getList());
		
		TextSetUploadDataCallback callback = new TextSetUploadDataCallback(this);
		async.getAllUploadTransactions(callback);
		
	}

	public TextSetUploadData getTextSetUploadData() {
		return TextSetUploadData;
	}

	public void setTextSetUploadData(TextSetUploadData TextSetUploadData) {
		this.TextSetUploadData = TextSetUploadData;
	}

	public void addTextSetUploadData(TextSetUploadData source) {
		if(source.getDescription() == null) {
			Window.alert("Description null");	
		} else {
			orders.add(source);
		}
		
	}
	public List<TextSetUploadData> getTransactions() {
		return orders;
	}
	public void refresh() {
		TextSetUploadDataProvider.setList(orders);
		sortDataHandler.setList(TextSetUploadDataProvider.getList());
	}
	@Override
	public String getText() {
		return null;
	}

	@Override
	public void setText(String text) {
	}

}
