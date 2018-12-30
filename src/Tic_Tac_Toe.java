import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.tic_tac_toe.controllers.ChoiceGamePanelController;
import ru.tic_tac_toe.controllers.SaveQuestioneController;
import ru.tic_tac_toe.controllers.Tic_Tac_ToeController;

import java.io.File;
import java.io.IOException;

public class Tic_Tac_Toe extends Application {
	//-----------------------------------------------------------------------------fields
	private final String title;
	private final String ticTacToePathFXML;
	private final String choiceGamePanelFXML;
	private final String saveQuestioneFXML;
	private Tic_Tac_ToeController mainController;
	private ChoiceGamePanelController CGPController;
	private SaveQuestioneController SQController;
	//-----------------------------------------------------------------------------constructors
	public Tic_Tac_Toe()
	{
		//-----------------------------------------------------------------------------
		this.title = "Tic-Tac-Toe";
		this.ticTacToePathFXML = (File.separator + "ru" + File.separator + "tic_tac_toe" + File.separator +
				"fxml" + File.separator + "Tic_Tac_Toe.fxml");
		this.choiceGamePanelFXML = (File.separator + "ru" + File.separator + "tic_tac_toe" + File.separator +
				"fxml" + File.separator + "ChoiceGamePanel.fxml");
		this.saveQuestioneFXML = (File.separator + "ru" + File.separator + "tic_tac_toe" + File.separator +
				"fxml" + File.separator + "SaveQuestione.fxml");
		//-----------------------------------------------------------------------------
	}
	//-----------------------------------------------------------------------------main
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	@Override
	public void start(Stage primaryStage) throws Exception {
		//primaryStage adjustment
		//-----------------------------------------------
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle(title);
		primaryStage.centerOnScreen();
		primaryStage.setOnShown(windowEvent -> choiceGame(primaryStage));
		primaryStage.setOnCloseRequest(windowEvent -> whenClose(primaryStage, windowEvent));

		//FXML adjustment
		//-----------------------------------------------
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource(this.ticTacToePathFXML));
		Parent fxmlMainWindow = fxmlLoader.load();

		//controller adjustment
		//-----------------------------------------------
		this.mainController = fxmlLoader.getController();

		//start-up window
		//-----------------------------------------------
		Scene ticTacToe = new Scene(fxmlMainWindow);
		primaryStage.setScene(ticTacToe);
		primaryStage.show();
	}

	//-----------------------------------------------onShown start()
	private void choiceGame(Stage owner) {
		try {
			//primaryStage adjustment
			//-----------------------------------------------
			Stage dialogueStage = new Stage();
			dialogueStage.setResizable(false);
			dialogueStage.sizeToScene();
			dialogueStage.setTitle(null);
			dialogueStage.centerOnScreen();
			dialogueStage.setOnHidden(windowEvent -> onChoiceGame());

			//FXML adjustment
			//-----------------------------------------------
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(this.choiceGamePanelFXML));
			Parent fxmlDialogueWindow = fxmlLoader.load();
			this.CGPController = fxmlLoader.getController();

			//modality adjustment
			//-----------------------------------------------
			dialogueStage.initModality(Modality.WINDOW_MODAL);
			dialogueStage.initOwner(owner);

			//start-up window
			//-----------------------------------------------
			Scene choice = new Scene(fxmlDialogueWindow);
			dialogueStage.setScene(choice);
			dialogueStage.showAndWait();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	//-----------------------------------------------onHidden choiceGame()
	private void onChoiceGame() {
		if(this.CGPController.isPvPGame() == null) {
			Platform.exit();
		}
		else if(this.CGPController.isPvPGame()) {
			this.mainController.setPvPGame();
		}
		else {
			this.mainController.setPvCGame();
		}
	}

	//-----------------------------------------------onCloseRequest start()
	private void whenClose(Stage owner, WindowEvent event)
	{
		if(this.mainController.isPvPGame()) {

		}
		else {
			//-----------------------------------------------------------------------------
			if (!this.mainController.isStartVisible()) {
				event.consume();
				try {
					//primaryStage adjustment
					//-----------------------------------------------
					Stage dialogueStage = new Stage();
					dialogueStage.setResizable(false);
					dialogueStage.sizeToScene();
					dialogueStage.setTitle(null);
					dialogueStage.centerOnScreen();
					dialogueStage.setOnHidden(windowEvent -> onSave());

					//FXML adjustment
					//-----------------------------------------------
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource(this.saveQuestioneFXML));
					Parent fxmlDialogueWindow = fxmlLoader.load();
					this.SQController = fxmlLoader.getController();

					//modality adjustment
					//-----------------------------------------------
					dialogueStage.initModality(Modality.WINDOW_MODAL);
					dialogueStage.initOwner(owner);

					//start-up window
					//-----------------------------------------------
					Scene choice = new Scene(fxmlDialogueWindow);
					dialogueStage.setScene(choice);
					dialogueStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//-----------------------------------------------------------------------------
		}
	}
	//-----------------------------------------------onHidden whenClose()
	private void onSave()
	{
		if(this.SQController.isYes() != null) {
			//-----------------------------------------------------------------------------
			if (this.SQController.isYes()) {
				this.mainController.saveMenuItemOnAction(new ActionEvent());
				Platform.exit();
			}
			else{
				Platform.exit();
			}
			//-----------------------------------------------------------------------------
		}
	}
}