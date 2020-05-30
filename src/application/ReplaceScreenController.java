package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.geom.FlatteningPathIterator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

public class ReplaceScreenController {
	@FXML
	private TextField queryTextField;
	@FXML
	private TextField replaceTextField;
	@FXML
	private Button findNextButton;
	@FXML
	private Button replaceButton;
	@FXML
	private Button replaceAllButton;
	@FXML
	private Button cancelButton;
	@FXML
	private CheckBox spellCheck;
	@FXML
	private CheckBox repeatCheck;
	@FXML
	private TextArea mainTextArea;
	private boolean found = false;
	private boolean firstClick=true;
	// Event Listener on Button[#findNextButton].onAction
	public void setMainTextArea(TextArea mainTextArea) {
		this.mainTextArea = mainTextArea;
	}
	private void findNext(String queryString, String tmpString, int oriPosition) {
		// TODO Auto-generated method stub
		int index = tmpString.indexOf(queryString);
		if(index==-1)
		{
			found = false;
			DialogControl.notFoundPrompt(queryString);
			return;
		}
		mainTextArea.deselect();
		index = oriPosition + index;
		mainTextArea.positionCaret(index);
		found = true;
		mainTextArea.selectRange(mainTextArea.getCaretPosition(),
				mainTextArea.getCaretPosition() + queryString.length());// ��һ����ʾѡ��ĳ��ȣ��ڶ�����ʾ��ʼλ��

	}
	@FXML
	public void clickFindNextButton(ActionEvent event) {
		// TODO Autogenerated
		int oriPosition = mainTextArea.getCaretPosition();
		String queryString = queryTextField.getText();
		String tMPString;
		tMPString = mainTextArea.getText().substring(mainTextArea.getCaretPosition());
		if (!spellCheck.isSelected())// ���ִ�Сд
		{
			tMPString = tMPString.toLowerCase();
			queryString = queryString.toLowerCase();
		}
		int index = tMPString.indexOf(queryString);
		if (index == -1) {
			if (repeatCheck.isSelected()) {
				findNext(queryString, mainTextArea.getText(), 0);
			} else {
				DialogControl.notFoundPrompt(queryString);
				found = false;
			}

		} else {
			findNext(queryString, tMPString, oriPosition);
		}
	}
	// Event Listener on Button[#replaceButton].onAction
	@FXML
	public void clickReplaceButton(ActionEvent event) {
		// TODO Autogenerated
		
		if(firstClick)
		{
			clickFindNextButton(event);
			if(found)
			{
				firstClick = false;
			}
		}else {
			String selectString = mainTextArea.getSelectedText();
			if(!selectString.equals(""))
			{
				mainTextArea.replaceSelection(replaceTextField.getText());
				mainTextArea.selectRange(mainTextArea.getCaretPosition()-replaceTextField.getText().length(), mainTextArea.getCaretPosition());
				firstClick = true;
			}
			
		}
		
	}
	// Event Listener on Button[#replaceAllButton].onAction
	@FXML
	public void clickReplaceAllButton(ActionEvent event) {
		// TODO Autogenerated
		String queryString = queryTextField.getText();
		String replaceString = replaceTextField.getText();
		String textString = mainTextArea.getText();
		if(spellCheck.isSelected()) {
			mainTextArea.setText(textString.replaceAll(queryString, replaceString));	
		}else {
			mainTextArea.setText(textString.replaceAll("(?i)"+queryString, replaceString));	
		}
		
	}
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void clickCancelButton(ActionEvent event) {
		// TODO Autogenerated
		Stage s = (Stage) cancelButton.getScene().getWindow();
		s.close();
	}
	
	public void initialize()
	{

		findNextButton.setDisable(true);
		replaceButton.setDisable(true);
		replaceAllButton.setDisable(true);
		queryTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(newValue.equals(""))
				{
					findNextButton.setDisable(true);
					replaceButton.setDisable(true);
					replaceAllButton.setDisable(true);
				}else {
					findNextButton.setDisable(false);
					replaceButton.setDisable(false);
					replaceAllButton.setDisable(false);
				}
				
			}
		});
	}
}