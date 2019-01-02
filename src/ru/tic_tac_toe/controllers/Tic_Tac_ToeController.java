package ru.tic_tac_toe.controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.tic_tac_toe.games.AreaConditions;
import ru.tic_tac_toe.games.Game;
import ru.tic_tac_toe.games.GameVsComputer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.time.LocalDateTime;

public class Tic_Tac_ToeController {
	//-----------------------------------------------------------------------------fields
	private final Image DASH = new Image("Images" + File.separator + "Cross.jpg");
	private final Image ZERO = new Image("Images" + File.separator + "Zero.jpg");
	private final Image GREY = new Image("Images" + File.separator + "Grey.jpg");
	private Image iComp;
	private Image iPerson;
	private Game game;
	private AreaConditions personSymbol;
	private AreaConditions computerSymbol;
	private LoadGamePanelController LGPController;
	private ChoiceGamePanelController CGPController;
	private boolean isPvPGame;
	private int winCombo;
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//-----------------------------------------------------------------------------fields
	@FXML
	private BorderPane mainBorderPane;

	@FXML
	private GridPane mainGridPane;

	@FXML
	private VBox chatBorderPane;

	@FXML
	private MenuItem saveMenuItem;

	@FXML
	private MenuItem anotherGameTypeMenuItem;

	@FXML
	private Button startButton;

	@FXML
	private Button newGameButton;

	@FXML
	private CheckBox goFirstCheckBox;

	@FXML
	private Label resultLabel;

	@FXML
	private Line centerLine;

	@FXML
	private Line upLine;

	@FXML
	private Line downLine;

	@FXML
	private Line leftLine;

	@FXML
	private Line rightLine;
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	@FXML
	void startButtonOnAction(ActionEvent event) {
		if(this.isPvPGame) {

		}
		else {
			this.startButtonPvC();
		}
		event.consume();
	}

	@FXML
	void newGameButtonOnAction(ActionEvent event) {
		if(this.isPvPGame) {

		}
		else {
			this.newGameButtonPvC();
		}
		event.consume();
	}

	@FXML
	void imageViewsOnMouseClicked(MouseEvent event) {
		if(this.isPvPGame) {

		}
		else {
			this.goPvC(event);
		}
	}

	@FXML
	void imageViewsOnMouseEntered(MouseEvent event) {
		ImageView image = (ImageView)event.getTarget();
		image.setImage(this.GREY);
	}

	@FXML
	void imageViewsOnMouseExited(MouseEvent event) {
		ImageView image = (ImageView)event.getTarget();
		if (image.getImage() == this.GREY) {
			image.setImage(null);
		}
	}

	@FXML
	public void saveMenuItemOnAction(ActionEvent event) {
		String path = ("saves" + File.separator);
		if(Files.exists(Path.of(path))){
			String time = LocalDateTime.now().toString();
			time = time.substring(0, 19);
			time = time.replaceAll(":", "-");
			path = String.format("%s%19s%s", path, time, ".dat");
			this.game.saveGame(path, this.personSymbol);
		}
		else {
			//-----------------------------------------------------------------------------
			try {
				Files.createDirectory(Path.of(path));
				String time = LocalDateTime.now().toString();
				time = time.substring(0, 19);
				time = time.replaceAll(":", "-");
				path = String.format("%s%19s%s", path, time, ".dat");
				this.game.saveGame(path, this.personSymbol);
			}catch (IOException e) {
				e.printStackTrace();
			}
			//-----------------------------------------------------------------------------
		}
		event.consume();	}

	@FXML
	void loadMenuItemOnAction(ActionEvent event) {
		try {
			//primaryStage adjustment
			//-----------------------------------------------
			Stage dialogueStage = new Stage();
			dialogueStage.setResizable(false);
			dialogueStage.sizeToScene();
			dialogueStage.setTitle("");
			dialogueStage.centerOnScreen();
			dialogueStage.setOnHidden(windowEvent -> this.onLoadGame());

			//FXML adjustment
			//-----------------------------------------------
			FXMLLoader fxmlLoaderDialogue = new FXMLLoader();
			fxmlLoaderDialogue.setLocation(getClass().getResource("/ru/tic_tac_toe/fxml/LoadGamePanel.fxml"));
			Parent fxmlDialogue = fxmlLoaderDialogue.load();
			this.LGPController = fxmlLoaderDialogue.getController();

			//modality adjustment
			//-----------------------------------------------
			dialogueStage.initModality(Modality.WINDOW_MODAL);
			dialogueStage.initOwner(this.mainBorderPane.getScene().getWindow());

			//start-up window
			//-----------------------------------------------
			Scene choice = new Scene(fxmlDialogue);
			dialogueStage.setScene(choice);
			dialogueStage.show();


		} catch (IOException e) {
			e.printStackTrace();
		}
		event.consume();
	}

	@FXML
	void exitMenuItemOnAction(ActionEvent event) {
		Platform.exit();
		event.consume();
	}

	@FXML
	void anotherGameTypeMenuItemOnAction(ActionEvent event) {
		try {
			//primaryStage adjustment
			//-----------------------------------------------
			Stage dialogueStage = new Stage();
			dialogueStage.setResizable(false);
			dialogueStage.sizeToScene();
			dialogueStage.setTitle("");
			dialogueStage.centerOnScreen();
			dialogueStage.setOnHidden(windowEvent -> setGame());

			//FXML adjustment
			//-----------------------------------------------
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/ru/tic_tac_toe/fxml/ChoiceGamePanel.fxml"));
			Parent fxmlDialogueWindow = fxmlLoader.load();
			this.CGPController = fxmlLoader.getController();

			//modality adjustment
			//-----------------------------------------------
			dialogueStage.initModality(Modality.WINDOW_MODAL);
			dialogueStage.initOwner(this.mainBorderPane.getScene().getWindow());

			//start-up window
			//-----------------------------------------------
			Scene choice = new Scene(fxmlDialogueWindow);
			dialogueStage.setScene(choice);
			dialogueStage.show();


		} catch (IOException e) {
			e.printStackTrace();
		}
		event.consume();
	}

	@FXML
	void aboutMenuItemOnAction(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URI("https://ru.wikihow.com/играть-в-крестики-нолики"));
		} catch (IOException | URISyntaxException e1) {
			e1.printStackTrace();
		}
		event.consume();
	}
	//-----------------------------------------------------------------------------initialization
	@FXML
	void initialize() {
	}
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	//-----------------------------------------------------------------------------is's
	public boolean isStartVisible()
	{
		return this.startButton.isVisible();
	}

	public boolean isPvPGame()
	{
		return this.isPvPGame;
	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------set's
	public void setPvCGame()
	{
		this.isPvPGame = false;
		this.newGameButtonOnAction(new ActionEvent());
		this.goFirstCheckBox.setSelected(false);
		this.mainBorderPane.setVisible(true);
		this.anotherGameTypeMenuItem.setText("Choice game vsPlayer");
		this.saveMenuItem.setDisable(true);
		this.game = new GameVsComputer();
		this.chatBorderPane.setVisible(false);
	}

	public void setPvPGame()
	{
		this.isPvPGame = true;
	}

	private void setGame()
	{
		if(this.CGPController.isPvPGame() == null) {
			Platform.exit();
		}
		else if(this.CGPController.isPvPGame()) {
			this.setPvPGame();
		}
		else {
			this.setPvCGame();
		}
	}
	//-----------------------------------------------------------------------------PvC logic
	private void startButtonPvC()
	{
		this.saveMenuItem.setDisable(false);
		this.newGameButton.setVisible(true);
		this.startButton.setVisible(false);
		this.goFirstCheckBox.setVisible(false);
		this.mainGridPane.setDisable(false);
		if(this.goFirstCheckBox.isSelected()) {
			this.iPerson = this.DASH;
			this.personSymbol = AreaConditions.DASH;
			this.iComp = this.ZERO;
			this.computerSymbol = AreaConditions.ZERO;
		}
		else {
			SecureRandom randomNumbers = new SecureRandom();
			if((randomNumbers.nextInt() % 2) == 0) {
				this.iComp = this.DASH;
				this.computerSymbol = AreaConditions.DASH;
				this.iPerson = this.ZERO;
				this.personSymbol = AreaConditions.ZERO;
				GameVsComputer.go_Number++;
				int place = game.opponent_Go(this.computerSymbol, this.personSymbol);
				ImageView image = (ImageView)getNodeFromGridPane(this.mainGridPane, findRow(place), findColumn(place));
				image.setImage(this.iComp);
				image.setDisable(true);
			}
			else {
				this.iPerson = this.DASH;
				this.personSymbol = AreaConditions.DASH;
				this.iComp = this.ZERO;
				this.computerSymbol = AreaConditions.ZERO;
			}
		}
	}

	private void newGameButtonPvC()
	{
		this.saveMenuItem.setDisable(true);
		this.game = new GameVsComputer();
		for(Node n : this.mainGridPane.getChildren()) {
			if(n instanceof  ImageView) {
				ImageView image = (ImageView) n;
				image.setImage(null);
				image.setDisable(false);
				image.setPreserveRatio(false);
			}
		}
		GameVsComputer.go_Number = 0;
		this.newGameButton.setVisible(false);
		this.startButton.setVisible(true);
		this.goFirstCheckBox.setVisible(true);
		this.resultLabel.setVisible(false);
		this.mainGridPane.setDisable(true);
		this.saveMenuItem.setDisable(false);

		switch(this.winCombo) {
			case 0:
				this.centerLine.setVisible(false);
				break;
			case 1:
				this.centerLine.setVisible(false);
				break;
			case 2:
				this.centerLine.setVisible(false);
				break;
			case 3:
				this.centerLine.setVisible(false);
				break;
			case 4:
				this.upLine.setVisible(false);
				break;
			case 5:
				this.downLine.setVisible(false);
				break;
			case 6:
				this.leftLine.setVisible(false);
				break;
			case 7:
				this.rightLine.setVisible(false);
				break;
			default:
				break;
		}
	}
	//-----------------------------------------------------------------------------
	private void goPvC(MouseEvent event)
	{
		ImageView image = (ImageView)event.getTarget();
		image.setImage(this.iPerson);
		image.setPreserveRatio(true);
		image.setDisable(true);
		Integer row = GridPane.getRowIndex(image);
		Integer column = GridPane.getColumnIndex(image);
		if(row == null) row = 0;
		if(column == null) column = 0;
		int place = this.findPlace(row, column);
		game.player_Go(this.personSymbol, place);

		if(GameVsComputer.go_Number < 4) {
			GameVsComputer.go_Number++;
			place = game.opponent_Go(this.computerSymbol, this.personSymbol);
			image = (ImageView) getNodeFromGridPane(this.mainGridPane, findRow(place), findColumn(place));
			image.setPreserveRatio(true);
			image.setImage(this.iComp);
			image.setDisable(true);
			this.winCombo = this.game.isWin(this.computerSymbol);
			if(this.winCombo != -1) {
				this.resultLabel.setText("Computer won!");
				this.resultLabel.setVisible(true);
				this.mainGridPane.setDisable(true);
				this.saveMenuItem.setDisable(true);
				printLine();
			}
		}
		else {
			if(this.iComp == this.DASH) {
				GameVsComputer.go_Number++;
				place = game.opponent_Go(this.computerSymbol, this.personSymbol);
				image = (ImageView) getNodeFromGridPane(this.mainGridPane, findRow(place), findColumn(place));
				image.setPreserveRatio(true);
				image.setImage(this.iComp);
				image.setDisable(true);
				this.winCombo = this.game.isWin(this.computerSymbol);
				if(this.winCombo != -1) {
					this.resultLabel.setText("Computer won!");
					this.resultLabel.setVisible(true);
					this.printLine();
				}
				else {
					this.resultLabel.setText("Dead hit!");
					this.resultLabel.setVisible(true);
				}
			}
			else {
				this.resultLabel.setText("Dead hit!");
				this.resultLabel.setVisible(true);
			}
			this.saveMenuItem.setDisable(true);
			this.mainGridPane.setDisable(true);
		}
	}

	private void printLine()
	{
		switch(this.winCombo) {
			case 0:
				this.centerLine.setRotate(46.0);
				this.centerLine.setEndX(300.0);
				this.centerLine.setVisible(true);
				break;
			case 1:
				this.centerLine.setRotate(90.0);
				this.centerLine.setEndX(170.0);
				this.centerLine.setVisible(true);
				break;
			case 2:
				this.centerLine.setRotate(134.0);
				this.centerLine.setEndX(300.0);
				this.centerLine.setVisible(true);
				break;
			case 3:
				this.centerLine.setRotate(0.0);
				this.centerLine.setEndX(170.0);
				this.centerLine.setVisible(true);
				break;
			case 4:
				this.upLine.setVisible(true);
				break;
			case 5:
				this.downLine.setVisible(true);
				break;
			case 6:
				this.leftLine.setVisible(true);
				break;
			case 7:
				this.rightLine.setVisible(true);
				break;
		}
	}
	//-----------------------------------------------------------------------------
	private void onLoadGame()
	{
		if (this.LGPController.getCheckedFile() != null) {
			this.personSymbol = this.game.loadGame(this.LGPController.getCheckedFile());
			if(this.personSymbol == AreaConditions.DASH) {
				this.computerSymbol = AreaConditions.ZERO;
				this.iPerson = this.DASH;
				this.iComp = this.ZERO;
			}
			else {
				this.computerSymbol = AreaConditions.DASH;
				this.iPerson = this.ZERO;
				this.iComp = this.DASH;
			}
			for (Node n : this.mainGridPane.getChildren()) {
				if (n instanceof ImageView) {
					ImageView image = (ImageView) n;
					Integer row = GridPane.getRowIndex(image);
					Integer column = GridPane.getColumnIndex(image);
					if (row == null) row = 0;
					if (column == null) column = 0;
					int place = this.findPlace(row, column);
					switch (this.game.getElement(place)) {
						case DASH:
							image.setImage(this.DASH);
							image.setDisable(true);
							image.setPreserveRatio(true);
							break;
						case ZERO:
							image.setImage(this.ZERO);
							image.setDisable(true);
							image.setPreserveRatio(true);
							break;
						default:
							image.setImage(null);
							image.setDisable(false);
							image.setPreserveRatio(false);
							break;
					}
				}
			}
			this.resultLabel.setVisible(false);
			this.saveMenuItem.setDisable(false);
			this.newGameButton.setVisible(true);
			this.startButton.setVisible(false);
			this.goFirstCheckBox.setVisible(false);
			this.mainGridPane.setDisable(false);

			switch(this.winCombo) {
				case 0:
					this.centerLine.setVisible(false);
					break;
				case 1:
					this.centerLine.setVisible(false);
					break;
				case 2:
					this.centerLine.setVisible(false);
					break;
				case 3:
					this.centerLine.setVisible(false);
					break;
				case 4:
					this.upLine.setVisible(false);
					break;
				case 5:
					this.downLine.setVisible(false);
					break;
				case 6:
					this.leftLine.setVisible(false);
					break;
				case 7:
					this.rightLine.setVisible(false);
					break;
				default:
					break;
			}
		}
	}
	//-----------------------------------------------------------------------------convertione
	private int findRow(int place)
	{
		return (place / 3);
	}

	private int findColumn(int place)
	{
		return (place % 3);
	}

	private int findPlace(int row, int column)
	{
		return ((row * 3) + column);
	}

	private Node getNodeFromGridPane(GridPane gridPane, int row, int col) {
		for (Node node : gridPane.getChildren()) {
			Integer r = GridPane.getRowIndex(node);
			Integer c = GridPane.getColumnIndex(node);
			if(r == null) r = 0;
			if(c == null) c = 0;
			if (c == col && r == row) {
				return node;
			}
		}
		return new ImageView();
	}
}