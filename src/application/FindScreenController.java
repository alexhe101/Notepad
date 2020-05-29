package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

public class FindScreenController {
	@FXML
	private TextField findTextField;
	@FXML
	private Button findNextButton;
	@FXML
	private Button cancelButton;
	@FXML
	private CheckBox checkUp;
	@FXML
	private CheckBox checkDown;
	@FXML
	private CheckBox spellCheck;
	@FXML
	private CheckBox repeatButton;

	@FXML
	private TextArea mainTextArea;

	private void findNext(String queryString, String tmpString, int oriPosition) {
		// TODO Auto-generated method stub
		int index = tmpString.indexOf(queryString);
		if(index==-1)
		{
			DialogControl.notFoundPrompt(queryString);
			return;
		}
		mainTextArea.deselect();
		index = oriPosition + index;
		mainTextArea.positionCaret(index);
		mainTextArea.selectRange(mainTextArea.getCaretPosition(),
				mainTextArea.getCaretPosition() + queryString.length());// ��һ����ʾѡ��ĳ��ȣ��ڶ�����ʾ��ʼλ��

	}
	private void findPrev(String queryString,String tmpString,int oriPosition) {
		// TODO Auto-generated method stub
		int index = tmpString.indexOf(queryString);
		if(index==-1)
		{
			DialogControl.notFoundPrompt(queryString);
			return;
		}
		mainTextArea.deselect();
		mainTextArea.positionCaret(index);
		mainTextArea.selectRange(mainTextArea.getCaretPosition() + queryString.length(),mainTextArea.getCaretPosition()
				);
	}
	// Event Listener on Button[#findNextButton].onAction
	@FXML
	public void findNext(ActionEvent event) {
		// TODO Autogenerated
		int oriPosition = mainTextArea.getCaretPosition();
		String queryString = findTextField.getText();
		String tMPString;
		if(checkDown.isSelected())
		{
			tMPString = mainTextArea.getText().substring(mainTextArea.getCaretPosition());
		}else {
			tMPString = mainTextArea.getText().substring(0,mainTextArea.getCaretPosition());
		}	
		if (!spellCheck.isSelected())// ���ִ�Сд
		{
			tMPString = tMPString.toLowerCase();
			tMPString = tMPString.toLowerCase();
		}

		int index = tMPString.indexOf(queryString);
		if(checkDown.isSelected())
		{
			if (index == -1) {
				if (repeatButton.isSelected()) {
					findNext(queryString, mainTextArea.getText(), 0);
				} else {
					DialogControl.notFoundPrompt(queryString);
				}

			} else {
				findNext(queryString, tMPString, oriPosition);
			}
		}else {
			if(index==-1)
			{
				if(repeatButton.isSelected())
				{
					findPrev(queryString, mainTextArea.getText(), mainTextArea.getText().length());
				}else {
					DialogControl.notFoundPrompt(queryString);
				}
			}else {
				findPrev(queryString, tMPString, oriPosition);
			}
		}

	}

	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void findCancel(ActionEvent event) {
		// TODO Autogenerated
		Stage s = (Stage) cancelButton.getScene().getWindow();
		s.close();
	}

	public void setTextArea(TextArea t) {
		mainTextArea = t;
	}

	public void initialize() {
		checkDown.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (checkDown.isSelected()) {
					checkUp.setSelected(false);
				} else if (!checkDown.isSelected()) {
					checkUp.setSelected(true);
				}

			}
		});
		checkUp.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (checkUp.isSelected()) {
					checkDown.setSelected(false);
				} else {
					checkDown.setSelected(true);
				}
			}
		});
		findNextButton.setDisable(true);
		findTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (newValue.equals("")) {
					findNextButton.setDisable(true);
				} else {
					findNextButton.setDisable(false);
				}

			}
		});
	}
}