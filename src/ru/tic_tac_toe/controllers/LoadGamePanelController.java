package ru.tic_tac_toe.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

public class LoadGamePanelController {
	//-----------------------------------------------------------------------------fields
	private ObservableList<File> allFiles;
	private String checkedFile;
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//-----------------------------------------------------------------------------fields
	@FXML
	private TableView<File> loadingTableView;

	@FXML
	private TableColumn<File, String> loadingTableColumn;

	@FXML
	private Button loadButton;

	@FXML
	private Button deleteButton;
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	@FXML
	void deleteButtonOnAction(ActionEvent event) {
		try{
			Files.delete(Paths.get(this.loadingTableView.getSelectionModel().getSelectedItem().getPath()));
			this.allFiles.remove(this.loadingTableView.getSelectionModel().getSelectedItem());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.loadingTableView.getSelectionModel().select(null);
		event.consume();
	}

	@FXML
	void loadButtonOnAction(ActionEvent event) {
		this.checkedFile = this.loadingTableView.getSelectionModel().getSelectedItem().getPath();
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}
	//-----------------------------------------------------------------------------initialization
	@FXML
	void initialize() {
		//-----------------------------------------------------------------------------
		this.allFiles = FXCollections.observableArrayList();
		String pathName = ("saves" + File.separator);
		File folder = new File(pathName);
		//-----------------------------------------------------------------------------
		File[] listOfFiles = folder.listFiles();
		if((listOfFiles != null) && (listOfFiles.length > 0)) {
			this.allFiles.addAll(Arrays.asList(listOfFiles));
			Collections.reverse(this.allFiles);
			this.loadingTableView.setItems(this.allFiles);
			this.loadingTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		}
		else {
			this.loadButton.setDisable(true);
			this.deleteButton.setDisable(true);
		}
		//-----------------------------------------------------------------------------
		this.loadingTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.loadButton.setDisable(false);
				this.deleteButton.setDisable(false);
			}
			else {
				this.loadButton.setDisable(true);
				this.deleteButton.setDisable(true);
			}
		});
	}
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	String getCheckedFile() {
		return this.checkedFile;
	}
}