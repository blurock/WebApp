package info.esblurock.reaction.client.panel.transaction;

import gwt.material.design.client.custom.MaterialButtonCell;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import info.esblurock.reaction.client.FindShortNameFromString;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.data.transaction.TransactionInfo;

import java.util.ArrayList;
import java.util.Comparator;
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

public class ObjectTransaction extends Composite implements HasText {

	private static ObjectTransactionUiBinder uiBinder = GWT
			.create(ObjectTransactionUiBinder.class);

	interface ObjectTransactionUiBinder extends
			UiBinder<Widget, ObjectTransaction> {
	}


	TransactionServiceAsync async = TransactionService.Util.getInstance();

	InterfaceConstants interfaceConstants = GWT.create(InterfaceConstants.class);
	private List<TransactionInfo> orders = new ArrayList<TransactionInfo>();

	private DataGrid<TransactionInfo> dataGrid;
	private ListDataProvider<TransactionInfo> TransactionInfoProvider = new ListDataProvider<TransactionInfo>();
	private ListHandler<TransactionInfo> sortDataHandler;
	private final ProvidesKey<TransactionInfo> KEY_PROVIDER = new ProvidesKey<TransactionInfo>() {

		@Override
		public Object getKey(TransactionInfo item) {
			return item.getKeyword();
		}
	};

	private final SelectionModel<TransactionInfo> selectionModel = new MultiSelectionModel<TransactionInfo>(
			KEY_PROVIDER);

	private TransactionInfo TransactionInfo;

	@UiField
	SimplePanel gridPanel, pagerPanel;

	public ObjectTransaction() {
		initWidget(uiBinder.createAndBindUi(this));
		sortDataHandler = new ListHandler<TransactionInfo>(orders);
		setGrid(new ArrayList<TransactionInfo>());
		getAllTransactionInfo();
		dataGrid.setStyleName("striped responsive-table");
	}
	public void setGrid(List<TransactionInfo> orders) {
		if(orders != null)
			this.orders = orders;
		dataGrid = createDatagrid();
		
		gridPanel.clear();
		gridPanel.setWidget(dataGrid);
		TransactionInfoProvider.setList(orders);
		sortDataHandler.setList(TransactionInfoProvider.getList());
	}

	private DataGrid<TransactionInfo> createDatagrid() {

		// ACTION BUTTON
		Column<TransactionInfo, MaterialButton> processbutton = new Column<TransactionInfo, MaterialButton>(
				new MaterialButtonCell()) {
			@Override
			public MaterialButton getValue(TransactionInfo object) {

				
				MaterialButton button = new MaterialButton(interfaceConstants.process(),
						"blue-text text-darken-2 light-blue lighten-5",
						"light");
				button.setTooltip(object.getKeyword());
				button.setIcon("mdi-action-list");
				button.setIconPosition("left");
				button.getElement().getStyle()
						.setProperty("display", "inline-flex");
				return button;
			}

		};
		processbutton.setFieldUpdater(new FieldUpdater<TransactionInfo, MaterialButton>() {
					@Override
					public void update(int index, TransactionInfo object, MaterialButton value) {		
						ObjectTransactionActions popup = new ObjectTransactionActions(object);
						MaterialModal.showModal(popup, TYPE.FIXED_FOOTER);
					}
				});
		
		// Source sourceKey
		TextColumn<TransactionInfo> colObjectType = new TextColumn<TransactionInfo>() {
			@Override
			public String getValue(TransactionInfo object) {
				String shortname= FindShortNameFromString.findShortClassName(object.getTransactionObjectType());
				return shortname;
			}
		};
		colObjectType.setSortable(true);
		sortDataHandler.setComparator(colObjectType,
				new Comparator<TransactionInfo>() {

					@Override
					public int compare(TransactionInfo o1,
							TransactionInfo o2) {

						return o1.getTransactionObjectType().compareTo(o2.getTransactionObjectType());
					}
				});
		

		// Object name
		TextColumn<TransactionInfo> colObjectName = new TextColumn<TransactionInfo>() {
			@Override
			public String getValue(TransactionInfo object) {
				return object.getKeyword();
			}
		};
		colObjectType.setSortable(true);
		sortDataHandler.setComparator(colObjectType,
				new Comparator<TransactionInfo>() {
					@Override
					public int compare(TransactionInfo o1,
							TransactionInfo o2) {
						return o1.getKeyword().compareTo(o2.getKeyword());
					}
				});
		// User
		TextColumn<TransactionInfo> colUser = new TextColumn<TransactionInfo>() {
			@Override
			public String getValue(TransactionInfo object) {
				return object.getUser();
			}
		};
		colUser.setSortable(true);
		sortDataHandler.setComparator(colUser,
				new Comparator<TransactionInfo>() {

					@Override
					public int compare(TransactionInfo o1,
							TransactionInfo o2) {
						return o1.getUser().compareTo(o2.getUser());
					}
				});
/*
		// Enter Date
		TextColumn<TransactionInfo> colEntryDate = new TextColumn<TransactionInfo>() {
			@Override
			public String getValue(TransactionInfo object) {
				String ans = "---";
				if(object.getInputDate() != null) {
					ans = object.getInputDate().toString();
				}
				return ans;
			}
		};
		colEntryDate.setSortable(true);
		sortDataHandler.setComparator(colEntryDate,
				new Comparator<TransactionInfo>() {

					@Override
					public int compare(TransactionInfo o1,
							TransactionInfo o2) {
						int ans = 0;
						if(o1.getInputDate() == null){
							if(o2.getInputDate() == null) 
								ans = 0;
							else 
								ans = -1;
						} else if(o2.getInputDate() == null) {
							ans = 1;
						} else {
							ans = o1.getInputDate().compareTo(o2.getInputDate());
						}
						return ans;
					}
				});
*/


		final DataGrid<TransactionInfo> dataGrid = new DataGrid<TransactionInfo>(
				100, KEY_PROVIDER);
		dataGrid.setSize("100%", "40vh");

		
		dataGrid.addColumn(processbutton, interfaceConstants.action());
		dataGrid.addColumn(colObjectType, interfaceConstants.sourcekey());
		dataGrid.addColumn(colObjectName, interfaceConstants.keyword());
		dataGrid.addColumn(colUser, interfaceConstants.user());

		dataGrid.setStyleName("striped responsive-table");

		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER,
				pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);
		pagerPanel.add(pager);

		TransactionInfoProvider = new ListDataProvider<TransactionInfo>();
		TransactionInfoProvider.addDataDisplay(dataGrid);
		dataGrid.addColumnSortHandler(sortDataHandler);

		return dataGrid;
	}

	private void getAllTransactionInfo() {
		TransactionInfoProvider.setList(orders);
		sortDataHandler.setList(TransactionInfoProvider.getList());
		
		UpdateObjectTransactionsCallback callback = new UpdateObjectTransactionsCallback(this);
		async.getAllTransactions(callback);
		
	}

	public TransactionInfo getTransactionInfo() {
		return TransactionInfo;
	}

	public void setTransactionInfo(TransactionInfo TransactionInfo) {
		this.TransactionInfo = TransactionInfo;
	}

	public void addTransactionInfo(TransactionInfo source) {
		orders.add(source);
	}
	public List<TransactionInfo> getTransactions() {
		return orders;
	}
	public void refresh() {
		TransactionInfoProvider.setList(orders);
		sortDataHandler.setList(TransactionInfoProvider.getList());
	}
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}
}
