package info.esblurock.reaction.client.panel.transaction;

import gwt.material.design.client.custom.MaterialButtonCell;
import gwt.material.design.client.custom.MaterialCheckBoxCell;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.TextToDatabaseAsync;
import info.esblurock.reaction.client.panel.modal.TextMessagePopup;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.data.upload.UploadFileTransaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellWidget;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

public class TransactionSources extends Composite {

	private static TransactionSourcesUiBinder uiBinder = GWT
			.create(TransactionSourcesUiBinder.class);

	interface TransactionSourcesUiBinder extends
			UiBinder<Widget, TransactionSources> {
	}

	TextToDatabaseAsync async = TextToDatabase.Util.getInstance();

	InterfaceConstants interfaceConstants = GWT
			.create(InterfaceConstants.class);
	private List<UploadFileTransaction> orders = new ArrayList<UploadFileTransaction>();

	private DataGrid<UploadFileTransaction> dataGrid;
	private ListDataProvider<UploadFileTransaction> UploadFileTransactionProvider = new ListDataProvider<UploadFileTransaction>();
	private ListHandler<UploadFileTransaction> sortDataHandler;
	private final ProvidesKey<UploadFileTransaction> KEY_PROVIDER = new ProvidesKey<UploadFileTransaction>() {

		@Override
		public Object getKey(UploadFileTransaction item) {
			return item.getKey();
		}
	};

	private final SelectionModel<UploadFileTransaction> selectionModel = new MultiSelectionModel<UploadFileTransaction>(
			KEY_PROVIDER);

	private UploadFileTransaction UploadFileTransaction;

	@UiField
	SimplePanel gridPanel, pagerPanel;

	public TransactionSources() {
		initWidget(uiBinder.createAndBindUi(this));
		sortDataHandler = new ListHandler<UploadFileTransaction>(orders);
		setGrid(new ArrayList<UploadFileTransaction>());
		getAllUploadFileTransaction();
		dataGrid.setStyleName("striped responsive-table");
	}

	public void setGrid(List<UploadFileTransaction> orders) {
		if(orders != null)
			this.orders = orders;
		dataGrid = createDatagrid();
		
		gridPanel.clear();
		gridPanel.setWidget(dataGrid);
		UploadFileTransactionProvider.setList(orders);
		sortDataHandler.setList(UploadFileTransactionProvider.getList());
	}

	private DataGrid<UploadFileTransaction> createDatagrid() {

		// ACTION BUTTON
		Column<UploadFileTransaction, MaterialButton> processbutton = new Column<UploadFileTransaction, MaterialButton>(
				new MaterialButtonCell()) {
			@Override
			public MaterialButton getValue(UploadFileTransaction object) {

				MaterialButton button = new MaterialButton(object.getUser(),
						"blue darken-4",
						"light");
				button.setTooltip(interfaceConstants.processtooltip());
				button.setIcon("mdi-action-list");
				button.setIconPosition("left");
				button.getElement().getStyle()
						.setProperty("display", "inline-flex");

				
				return button;
			}

		};

		processbutton.setFieldUpdater(new FieldUpdater<UploadFileTransaction, MaterialButton>() {

					@Override
					public void update(int index, UploadFileTransaction object,
							MaterialButton value) {
						TransactionActions popup = new TransactionActions(object);
						MaterialModal.showModal(popup, TYPE.FIXED_FOOTER);
						//MaterialToast.alert("Process: " + object.getKeyword());
					}
				});
		// Source sourceKey
		TextColumn<UploadFileTransaction> colSourceType = new TextColumn<UploadFileTransaction>() {
			@Override
			public String getValue(UploadFileTransaction object) {

				return object.getSourceType();
			}
		};
		colSourceType.setSortable(true);
		sortDataHandler.setComparator(colSourceType,
				new Comparator<UploadFileTransaction>() {

					@Override
					public int compare(UploadFileTransaction o1,
							UploadFileTransaction o2) {

						return o1.getSourceType().compareTo(o2.getSourceType());
					}
				});
		// Type
		TextColumn<UploadFileTransaction> colUser = new TextColumn<UploadFileTransaction>() {
			@Override
			public String getValue(UploadFileTransaction object) {

				return object.getUser();
			}
		};
		colUser.setSortable(true);
		sortDataHandler.setComparator(colUser,
				new Comparator<UploadFileTransaction>() {

					@Override
					public int compare(UploadFileTransaction o1,
							UploadFileTransaction o2) {

						return o1.getUser().compareTo(o2.getUser());
					}
				});

		// Enter Date
		TextColumn<UploadFileTransaction> colEntryDate = new TextColumn<UploadFileTransaction>() {
			@Override
			public String getValue(UploadFileTransaction object) {
				String ans = "---";
				if(object.getCreationDate() != null) {
					ans = object.getCreationDate().toString();
				}
				return ans;
			}
		};
		colEntryDate.setSortable(true);
		sortDataHandler.setComparator(colEntryDate,
				new Comparator<UploadFileTransaction>() {

					@Override
					public int compare(UploadFileTransaction o1,
							UploadFileTransaction o2) {
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

		// Source Date
		TextColumn<UploadFileTransaction> colNumberInputs = new TextColumn<UploadFileTransaction>() {
			@Override
			public String getValue(UploadFileTransaction object) {
				String ans = "---";
				if(object.getSetOfLinesKeys() != null) {
					ans = Integer.toString(object.getSetOfLinesKeys().size());
				}
				return ans;
			}
		};
		colNumberInputs.setSortable(true);
		sortDataHandler.setComparator(colNumberInputs,
				new Comparator<UploadFileTransaction>() {

					@Override
					public int compare(UploadFileTransaction o1,
							UploadFileTransaction o2) {
						int ans = 0;
						if(o1.getSetOfLinesKeys().size() > o2.getSetOfLinesKeys().size())
							ans = 1;
						else if(o1.getSetOfLinesKeys().size() < o2.getSetOfLinesKeys().size())
							ans = -1;
						return ans;
					}
				});


		final DataGrid<UploadFileTransaction> dataGrid = new DataGrid<UploadFileTransaction>(
				100, KEY_PROVIDER);
		dataGrid.setSize("100%", "40vh");

		dataGrid.addColumn(processbutton, interfaceConstants.action());
		dataGrid.addColumn(colSourceType, interfaceConstants.datatype());
		dataGrid.addColumn(colUser, interfaceConstants.user());
		dataGrid.addColumn(colNumberInputs, interfaceConstants.numberLines());
		dataGrid.addColumn(colEntryDate, interfaceConstants.entrydate());

		dataGrid.setStyleName("striped responsive-table");

		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER,
				pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);
		pagerPanel.add(pager);

		UploadFileTransactionProvider = new ListDataProvider<UploadFileTransaction>();
		UploadFileTransactionProvider.addDataDisplay(dataGrid);
		dataGrid.addColumnSortHandler(sortDataHandler);

		return dataGrid;
	}

	private void getAllUploadFileTransaction() {
		UploadFileTransactionProvider.setList(orders);
		sortDataHandler.setList(UploadFileTransactionProvider.getList());
		getUploadedFiles();
	}

	public UploadFileTransaction getUploadFileTransaction() {
		return UploadFileTransaction;
	}

	public void setUploadFileTransaction(UploadFileTransaction UploadFileTransaction) {
		this.UploadFileTransaction = UploadFileTransaction;
	}

	public void addUploadFileTransaction(UploadFileTransaction source) {
		orders.add(source);
	}
	public List<UploadFileTransaction> getTransactions() {
		return orders;
	}
	public void refresh() {
		UploadFileTransactionProvider.setList(orders);
		sortDataHandler.setList(UploadFileTransactionProvider.getList());
		//dataGrid.redraw();
		//dataGrid.flush();
	}
	public void getUploadedFiles() {
		UpdateUploadTransactionsCallback callback = new UpdateUploadTransactionsCallback(this);
		async.getSetOfUploadedFiles(callback);
	}

}
