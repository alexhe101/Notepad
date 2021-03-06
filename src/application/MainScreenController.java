package application;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import com.sun.glass.ui.ClipboardAssistance;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
public class MainScreenController {
	//private Stage mainScreen;
	final Clipboard clipboard = Clipboard.getSystemClipboard();
	private boolean unsaveFlag = false;
	private File currentFile;
	private String currentTitle = "无标题";
	private String currentAbsoultPath = "无标题";
	private FindScreenController findScreenController;
	private Stage findScreen;
	private ReplaceScreenController replaceScreenController;
	private Stage replaceScreen;
	private FontScreenController fontScreenController;
	private Stage fontScreen;
	
	@FXML
	private MenuItem redoButton;
	@FXML
	private GridPane gridPane;
	@FXML
	private BorderPane borderPane;
	@FXML
	private MenuItem newButton;
	@FXML
	private MenuItem newWindowButton;
	@FXML
	private MenuItem openButton;
	@FXML
	private MenuItem saveButton;
	@FXML
	private MenuItem saveAsButton;
	@FXML
	private MenuItem exitButton;
	@FXML
	private MenuItem cancelButton;
	@FXML
	private MenuItem cutButton;
	@FXML
	private MenuItem copyButton;
	@FXML
	private MenuItem pasteButton;
	@FXML
	private MenuItem deleteButton;
	@FXML
	private MenuItem findButton;
	@FXML
	private MenuItem findNext;
	@FXML
	private MenuItem findPrev;
	@FXML
	private MenuItem replaceButton;
	@FXML
	private MenuItem turnButton;
	@FXML
	private MenuItem selectAllButton;
	@FXML
	private MenuItem timeButton;
	@FXML
	private CheckMenuItem changLineCheck;
	@FXML
	private MenuItem frontMenuItem;
	@FXML
	private MenuItem scaleUpButton;
	@FXML
	private MenuItem scaleDownButton;
	@FXML
	private MenuItem resetButton;
	@FXML
	private CheckMenuItem statusCheck;
	@FXML
	private MenuItem aboutButton;
	@FXML
	private TextArea textArea;
	@FXML
	private Label indexLabel;
	@FXML
	private Label zoomLabel;
	private int lineCount;

	private void textIni() {
		// TODO Auto-generated method stub

		currentFile = null;
		currentTitle = "无标题";
		currentAbsoultPath = "无标题";
		textArea.clear();
		unsaveFlag = false;
		Stage s = (Stage) (borderPane.getScene().getWindow());

		s.setTitle(String.format("%s-记事本", currentTitle));

	}
	private void initialFindScreen() {
		// TODO Auto-generated method stub
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("FindScreen.fxml"));
		Parent root;
		findScreen = new Stage();
		try {
			root = fxmlLoader.load();
			findScreenController = fxmlLoader.getController();
			findScreenController.setTextArea(textArea);
			Scene scene = new Scene(root,538,226);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			findScreen.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initialReplaceScreen() {
		// TODO Auto-generated method stub
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("ReplaceScreen.fxml"));
		Parent root;
		replaceScreen = new Stage();
		try {
			root = fxmlLoader.load();
			replaceScreenController = fxmlLoader.getController();
			replaceScreenController.setMainTextArea(textArea);
			Scene scene = new Scene(root,517,265);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			replaceScreen.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on MenuItem[#newButton].onAction
	private void initialFontScreen() {
		// TODO Auto-generated method stub
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("FontScreen.fxml"));
		Parent root;
		fontScreen = new Stage();
		try {
			root = fxmlLoader.load();
			fontScreenController = fxmlLoader.getController();
			Scene scene = new Scene(root,544,612);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			fontScreen.setScene(scene);
			fontScreenController.setMainTextArea(textArea);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void clickNewFileButton(ActionEvent event) {
		// TODO Autogenerated
		if (unsaveFlag) {
			int result = DialogControl.unsavePrompt(currentAbsoultPath);
			if (result != 1) {
				if (result == 0) {
					clickSaveFileButton(event);
					clickNewFileButton(event);
				}
				textIni();
			}
		}

	}

	// Event Listener on MenuItem[#newWindowButton].onAction
	@FXML
	public void clickNewWindowButton(ActionEvent event) {
		// TODO Autogenerated
		Platform.runLater(new Runnable() {
			public void run() {
				try {
					new StageControl().start(new Stage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	// Event Listener on MenuItem[#openButton].onAction
	@FXML
	public void clickOpenFileButton(ActionEvent event) {
		// TODO Autogenerated
		if (unsaveFlag) {
			int result = DialogControl.unsavePrompt(currentAbsoultPath);
			if (result == 0) {
				clickSaveFileButton(event);
			} else if (result == 1) {
				return;
			}
		}

		FileChooser fc = new FileChooser();
		fc.setTitle("打开");
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("文本文档", "*.txt*"),
				new FileChooser.ExtensionFilter("所有文件", "*."));
		File nf = fc.showOpenDialog(borderPane.getScene().getWindow());
		if (nf != null) {
			currentFile = nf;
			try {
				String tmpString = FileOperator.readFile(currentFile);

				currentTitle = currentFile.getName();
				textArea.setText(tmpString);
				currentAbsoultPath = currentFile.getAbsolutePath();
				unsaveFlag = false;
				Stage s = (Stage) (borderPane.getScene().getWindow());
				s.setTitle(String.format("%s-记事本", currentTitle));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				DialogControl.showFileOpenError();
			}
		}

	}

	// Event Listener on MenuItem[#saveButton].onAction
	@FXML
	public void clickSaveFileButton(ActionEvent event) {
		// TODO Autogenerated
		if (currentFile == null) {
			clickSaveAsButton(event);
		} else {
			try {
				FileOperator.writeFile(currentFile, textArea.getText());
				unsaveFlag = false;
				Stage s = (Stage) (borderPane.getScene().getWindow());
				s.setTitle(String.format("%s-记事本", currentTitle));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				DialogControl.showFileSaveError();
			}
		}
	}

	// Event Listener on MenuItem[#saveAsButton].onAction
	@FXML
	public void clickSaveAsButton(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("另存为");
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("文本文档", "*.txt*"),
				new FileChooser.ExtensionFilter("所有文件", "*."));
		fc.setInitialFileName("*.txt");
		currentFile = fc.showSaveDialog(borderPane.getScene().getWindow());
		if (currentFile != null) {
			try {
				FileOperator.writeFile(currentFile, textArea.getText());
				currentTitle = currentFile.getName();
				textArea.setText(textArea.getText());
				currentAbsoultPath = currentFile.getAbsolutePath();
				unsaveFlag = false;
				Stage s = (Stage) (borderPane.getScene().getWindow());
				s.setTitle(String.format("%s-记事本", currentTitle));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				DialogControl.showFileSaveError();
			}
		}
		// TODO Autogenerated
	}

	// Event Listener on MenuItem[#exitButton].onAction
	@FXML
	public void clickExitButton(ActionEvent event) {
		// TODO Autogenerated
		if (unsaveFlag) {
			int result = DialogControl.unsavePrompt(currentAbsoultPath);
			if (result != 1) {
				if (result == 0) {
					clickSaveFileButton(event);
				}
			}else {
				return;
			}
		}
		System.exit(0);
	}

	// Event Listener on MenuItem[#cancelButton].onAction
	@FXML
	public void clickCancelButton(ActionEvent event) {
		// TODO Autogenerated
		if (textArea.isUndoable()) {
			textArea.undo();
		} 
	}

	@FXML
	public void clickRedoButton(ActionEvent event) {
		// TODO Auto-generated method stub
		if(textArea.isRedoable())
		{
			textArea.redo();
		}
	}
	// Event Listener on MenuItem[#cutButton].onAction
	@FXML
	public void clickCutButton(ActionEvent event) {
		// TODO Autogenerated
		textArea.cut();
	}

	// Event Listener on MenuItem[#copyButton].onAction
	@FXML
	public void clickCopyButton(ActionEvent event) {
		// TODO Autogenerated
		textArea.copy();
	}

	// Event Listener on MenuItem[#pasteButton].onAction
	@FXML
	public void clickPasteButton(ActionEvent event) {
		// TODO Autogenerated
		textArea.paste();
	}

	// Event Listener on MenuItem[#deleteButton].onAction
	@FXML
	public void clickDeleteButton(ActionEvent event) {
		// TODO Autogenerated
		textArea.deleteText(textArea.getSelection());
	}
	
	// Event Listener on MenuItem[#findButton].onAction
	@FXML
	public void clickFindButton(ActionEvent event) {
		findScreen.show();
	}
	// Event Listener on MenuItem[#findNext].onAction
	@FXML
	public void clickFindNextButton(ActionEvent event) {
		// TODO Autogenerated
		//System.out.println(findScreenController);
		if(findScreenController.getLastQueryString()==null)
		{
			findScreenController.setCheckDown();
			clickFindButton(event);
		}else {
			findScreenController.findNext(event);
		}
	}

	// Event Listener on MenuItem[#findPrev].onAction
	@FXML
	public void clickFindPrevButton(ActionEvent event) {
		// TODO Autogenerated
		if(findScreenController.getLastQueryString()==null)
		{
			findScreenController.setCheckUp();
			clickFindButton(event);
		}else {
			findScreenController.findNext(event);
		}
	}

	// Event Listener on MenuItem[#replaceButton].onAction
	@FXML
	public void clickReplaceButton(ActionEvent event) {
		// TODO Autogenerated
		replaceScreen.show();
	}

	// Event Listener on MenuItem[#turnButton].onAction
	@FXML
	public void clickTurnButton(ActionEvent event) {
		// TODO Autogenerated
		// 不用做
//		String result = DialogControl.turnPrompt();
//		textArea.positionCaret(Integer.parseInt(result));
////		textArea.getCursor()
	}

	// Event Listener on MenuItem[#selectAllButton].onAction
	@FXML
	public void clickSelectAllButton(ActionEvent event) {
		// TODO Autogenerated
		textArea.selectAll();
	}
	
	// Event Listener on MenuItem[#timeButton].onAction
	@FXML
	public void clickTimeButton(ActionEvent event) {
		// TODO Autogenerated
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm yyy/MM/dd");
		textArea.appendText(sdf.format(date));
	}

	// Event Listener on CheckMenuItem[#changLineCheck].onAction
	@FXML
	public void clickChangeLineCheck(ActionEvent event) {
		// TODO Autogenerated
		if (changLineCheck.isSelected()) {
			textArea.setWrapText(true);
		} else {
			textArea.setWrapText(false);
		}

	}

	// Event Listener on MenuItem[#frontMenuItem].onAction
	@FXML
	public void clickFrontMenuItem(ActionEvent event) {
		// TODO Autogenerated 
		Stage s = (Stage)gridPane.getScene().getWindow();
		fontScreen.show();
	}

	// Event Listener on MenuItem[#scaleUpButton].onAction
	@FXML
	public void clickZoomUpButton(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on MenuItem[#scaleDownButton].onAction
	@FXML
	public void clickZoomDownButton(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on MenuItem[#resetButton].onAction
	@FXML
	public void clickResetButton(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on CheckMenuItem[#statusCheck].onAction
	@FXML
	public void ClickStatusCheckButton(ActionEvent event) {
		// TODO Autogenerated
		if (statusCheck.isSelected()) {
			gridPane.setVisible(true);
		} else {
			gridPane.setVisible(false);
		}
	}

	// Event Listener on MenuItem[#aboutButton].onAction
	@FXML
	public void clickAboutButton(ActionEvent event) {
		// TODO Autogenerated
	}

	public void initialize() {
		initialReplaceScreen();
		initialFindScreen();
		initialFontScreen();
		redoButton.setDisable(true);
		
//		mainScreen.setOnCloseRequest(new EventHandler<WindowEvent>() {
//			
//			@Override
//			public void handle(WindowEvent event) {
//				// TODO Auto-generated method stub
//				DialogControl.unsavePrompt(currentAbsoultPath);
//				System.exit(0);
//			}
//		});
		textArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				Stage s = (Stage) (borderPane.getScene().getWindow());
				if (!newValue.equals("")) {
					unsaveFlag = true;
					ArrayList<String> t = new ArrayList<String>();
					Collections.addAll(t, textArea.getText().split(""));
					lineCount = Collections.frequency(t, "\n") + 1;
					s.setTitle(String.format("*%s-记事本", currentTitle));
					findButton.setDisable(false);
					findPrev.setDisable(false);
					findNext.setDisable(false);
				}else {
					findButton.setDisable(true);
					findPrev.setDisable(true);
					findNext.setDisable(true);
				}
			}
		});
		timeButton.setAccelerator(new KeyCodeCombination(KeyCode.F5));// 快捷键冲突，暂时无法使用
		deleteButton.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
		findNext.setAccelerator(new KeyCodeCombination(KeyCode.F3));
		textArea.selectedTextProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("")) {
					copyButton.setDisable(true);
					cutButton.setDisable(true);
					deleteButton.setDisable(true);

				} else {
					copyButton.setDisable(false);
					cutButton.setDisable(false);
					deleteButton.setDisable(false);

				}

			}
		});
		textArea.undoableProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (textArea.isUndoable()) {
					cancelButton.setDisable(false);
				} else {
					cancelButton.setDisable(true);
				}
			}

		});
		
		textArea.redoableProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(textArea.isRedoable())
				{
					redoButton.setDisable(false);
				}else {
					redoButton.setDisable(true);
				}
			}
			
		});
		
		
		textArea.caretPositionProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				String currentLineString = textArea.getText().substring(0, (int) newValue);
				int currentRow;
				int currentCol;
				ArrayList<String> t = new ArrayList<String>();
				Collections.addAll(t, currentLineString.split(""));
				currentRow = Collections.frequency(t, "\n") + 1;
				currentCol = currentLineString.substring(currentLineString.lastIndexOf("\n") + 1).length() + 1;
				indexLabel.setText(String.format("第%d行,第%d列", currentRow, currentCol));
			}

		});

		new ClipboardAssistance(com.sun.glass.ui.Clipboard.SYSTEM) {
			@Override
			public void contentChanged() {
				if (clipboard.hasString()) {
					pasteButton.setDisable(false);
				} else {
					pasteButton.setDisable(true);
				}
			}
		};
		turnButton.setVisible(false);
	}

}
