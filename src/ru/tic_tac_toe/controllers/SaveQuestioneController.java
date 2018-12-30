package ru.tic_tac_toe.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SaveQuestioneController {
	//-----------------------------------------------------------------------------fields
	private Boolean isYes;
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	@FXML
	void noButtonOnAction(ActionEvent event) {
		this.isYes = false;
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}

	@FXML
	void yesButtonOnAction(ActionEvent event) {
		this.isYes = true;
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}
	//-----------------------------------------------------------------------------initialization
	@FXML
	void initialize() {
		this.isYes = null;
	}
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	public Boolean isYes() {
		return this.isYes;
	}
}