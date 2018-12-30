package ru.tic_tac_toe.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ChoiceGamePanelController {
	//-----------------------------------------------------------------------------fields
	private Boolean isPvPGame;
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	@FXML
	void PvCOnAction(ActionEvent event) {
		this.isPvPGame = false;
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}

	@FXML
	void PvPOnAction(ActionEvent event) {
		this.isPvPGame = true;
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}
	//-----------------------------------------------------------------------------initialization
	@FXML
	void initialize() {
		this.isPvPGame = null;
	}
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	public Boolean isPvPGame()
	{
		return this.isPvPGame;
	}
}