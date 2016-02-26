package info.esblurock.reaction.client.panel.transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import info.esblurock.reaction.client.ReactionProcessUploadedLines;
import info.esblurock.reaction.client.ReactionProcessUploadedLinesAsync;
import info.esblurock.reaction.client.panel.description.DataDescriptionAsRows;
import info.esblurock.reaction.client.panel.transaction.process.ProcessUploadCallback;
import info.esblurock.reaction.client.panel.transaction.process.ProcessUploadFiles;
import info.esblurock.reaction.client.panel.transaction.process.RemoveTransactionCallback;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.InputTextSource;
import info.esblurock.reaction.data.upload.TextSetUploadData;

public class UploadFileTransaction extends Composite implements HasText {

	private static UploadFileTransactionUiBinder uiBinder = GWT.create(UploadFileTransactionUiBinder.class);

	interface UploadFileTransactionUiBinder extends UiBinder<Widget, UploadFileTransaction> {
	}

	private List<InputTextSource> orders = new ArrayList<InputTextSource>();
	private DataGrid<InputTextSource> dataGrid;
	private ListDataProvider<InputTextSource> InputTextSourceProvider = new ListDataProvider<InputTextSource>();
	private ListHandler<InputTextSource> sortDataHandler;
	private final ProvidesKey<InputTextSource> KEY_PROVIDER = new ProvidesKey<InputTextSource>() {

		@Override
		public Object getKey(InputTextSource item) {
			return item.getKey();
		}
	};

	private final SelectionModel<InputTextSource> selectionModel = new MultiSelectionModel<InputTextSource>(
			KEY_PROVIDER);

	@UiField
	MaterialButton close;
	@UiField
	MaterialButton delete;
	@UiField
	MaterialModalContent content;
	@UiField
	SimplePanel gridPanel, pagerPanel,descriptionPanel;

	TextSetUploadData data;
	InputTextSource inputTextSource;

	public UploadFileTransaction() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public UploadFileTransaction(TextSetUploadData data) {
		this.data = data;
		initWidget(uiBinder.createAndBindUi(this));
		sortDataHandler = new ListHandler<InputTextSource>(orders);
		setGrid(orders);
		dataGrid.setStyleName("striped responsive-table");
		init();
		if (data.getDescription() != null) {
			DataDescriptionAsRows description = new DataDescriptionAsRows(data.getKey(), data.getDescription());
			descriptionPanel.add(description);
			if (data.getInputTextSources() != null) {
				if (data.getInputTextSources().size() > 0) {
					for (InputTextSource source : data.getInputTextSources()) {
						orders.add(source);
					}
				} else {
					Window.alert("No InputTextSources");
				}
			} else {
				Window.alert("No data files");
			}
		} else {
			Window.alert("No 'Description' information about data");
		}
		refresh();
	}

	InterfaceConstants interfaceConstants = GWT.create(InterfaceConstants.class);
	DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);


	private void init() {

	}

	@UiHandler("close")
	void onClose(ClickEvent e) {
		MaterialModal.closeModal();
	}

	@UiHandler("delete")
	void onDelete(ClickEvent e) {
		String key = data.getKey();
		RemoveTransactionCallback callback = new RemoveTransactionCallback();
		TransactionServiceAsync async = TransactionService.Util.getInstance();
		async.removeTransaction(key, callback);
	}

	public void setGrid(List<InputTextSource> orders) {
		dataGrid = createDatagrid();
		gridPanel.clear();
		gridPanel.setWidget(dataGrid);
		InputTextSourceProvider.setList(orders);
		sortDataHandler.setList(InputTextSourceProvider.getList());
	}

	private Column<InputTextSource, MaterialButton> setActionButton() {
		// Test ACTION BUTTON
		Column<InputTextSource, MaterialButton> testbutton = new Column<InputTextSource, MaterialButton>(
				new MaterialButtonCell()) {
			@Override
			public MaterialButton getValue(InputTextSource object) {
				MaterialButton button = new MaterialButton("Test", "blue-text text-darken-2 light-blue lighten-5","light");
				button.setIcon("mdi-action-list");
				button.setIconPosition("left");
				button.getElement().getStyle().setProperty("display", "inline-flex");
				return button;
			}

		};

		testbutton.setFieldUpdater(new FieldUpdater<InputTextSource, MaterialButton>() {

			@Override
			public void update(int index, InputTextSource object, MaterialButton value) {
				MaterialToast.alert("Test Data: " + object.getTextType());
				ProcessUploadFiles process = ProcessUploadFiles.valueOf(object.getTextType());
				process.process(data.getDescription(),object.getID(), object.getTextname(), false);
			}
		});
		return testbutton;
	}

	Column<InputTextSource, MaterialButton> processButton() {
		// Process ACTION BUTTON
		Column<InputTextSource, MaterialButton> processbutton = new Column<InputTextSource, MaterialButton>(
				new MaterialButtonCell()) {
			@Override
			public MaterialButton getValue(InputTextSource object) {
				MaterialButton button = new MaterialButton("Process", "blue-text text-darken-2 light-blue lighten-5","light");
				button.setIcon("mdi-action-list");
				button.setIconPosition("left");
				button.getElement().getStyle().setProperty("display","inline-flex");
				return button;
			}

		};

		processbutton.setFieldUpdater(new FieldUpdater<InputTextSource, MaterialButton>() {

			@Override
			public void update(int index, InputTextSource object, MaterialButton value) {
				/*
				MaterialToast.alert("Process Data: " + object.getTextType());
				ProcessUploadFiles process = ProcessUploadFiles.valueOf(object.getTextType());
				DescriptionDataData description = data.getDescription();
				process.process(description,object.getID(), object.getTextname(), true);
				*/
				
				UploadFileProcessModal modal = new UploadFileProcessModal(data,object);
				MaterialModal.showModal(modal, TYPE.FIXED_FOOTER);
			}
		});
		return processbutton;
	}
	private Column<InputTextSource, MaterialButton> textType() {
		// Text Type
		Column<InputTextSource, MaterialButton> textType = new Column<InputTextSource, MaterialButton>(
				new MaterialButtonCell()) {
			@Override
			public MaterialButton getValue(InputTextSource object) {
				MaterialButton link = new MaterialButton(object.getTextType(),"blue-text text-darken-2 light-blue lighten-5", "light");
				return link;
			}
		};
		textType.setSortable(true);
		sortDataHandler.setComparator(textType, new Comparator<InputTextSource>() {
			@Override
			public int compare(InputTextSource o1, InputTextSource o2) {
				return o1.getTextType().compareTo(o2.getTextType());
			}
		});
		return textType;
	}

	private Column<InputTextSource, MaterialButton> textName() {
		// Text Name
		Column<InputTextSource, MaterialButton> textName = new Column<InputTextSource, MaterialButton>(
				new MaterialButtonCell()) {
			@Override
			public MaterialButton getValue(InputTextSource object) {
				MaterialButton link = new MaterialButton(object.getSourceType(),"blue-text text-darken-2 light-blue lighten-5", "light");
				return link;
			}
		};
		textName.setSortable(true);
		sortDataHandler.setComparator(textName, new Comparator<InputTextSource>() {
			@Override
			public int compare(InputTextSource o1, InputTextSource o2) {
				return o1.getTextname().compareTo(o2.getTextname());
			}
		});
		return textName;
	}

	private DataGrid<InputTextSource> createDatagrid() {
		Column<InputTextSource, MaterialButton> testbutton = setActionButton();
		Column<InputTextSource, MaterialButton> processbutton = processButton();
		Column<InputTextSource, MaterialButton> textType = textType();
		Column<InputTextSource, MaterialButton> textName = textName();
		
		final DataGrid<InputTextSource> dataGrid = new DataGrid<InputTextSource>(100, KEY_PROVIDER);
		dataGrid.setSize("100%", "40vh");

		dataGrid.addColumn(testbutton, "Test");
		dataGrid.addColumn(processbutton, interfaceConstants.action());
		dataGrid.addColumn(textType, "Text type");
		dataGrid.addColumn(textName, "Text Name");
		dataGrid.setStyleName("striped responsive-table");
		
		 SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class); 
		 SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		 pager.setDisplay(dataGrid);
		 pagerPanel.add(pager);
		 
		InputTextSourceProvider = new ListDataProvider<InputTextSource>();
		InputTextSourceProvider.addDataDisplay(dataGrid);
		dataGrid.addColumnSortHandler(sortDataHandler);

		return dataGrid;
	}
	public void refresh() {
		InputTextSourceProvider.setList(orders);
		sortDataHandler.setList(InputTextSourceProvider.getList());
	}

	public InputTextSource getInputTextSource() {
		return this.inputTextSource;
	}

	public void setInputTextSource(InputTextSource inputTextSource) {
		this.inputTextSource = inputTextSource;
	}

	public void addInputTextSource(InputTextSource source) {
		orders.add(source);
	}

	public List<InputTextSource> getTransactions() {
		return orders;
	}

	public void setText(String text) {

	}

	public String getText() {
		return null;
	}
};
