package application;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class DialogControl {
	public static void showFileSaveError()
	{
		Alert alert = new Alert(AlertType.WARNING);
		showFileError(alert);
		alert.setContentText("文件保存失败!");
		alert.showAndWait();
	}
	
	public static void showFileOpenError()
	{

		Alert alert = new Alert(AlertType.WARNING);
		showFileError(alert);
		alert.setContentText("文件打开失败!");
		alert.showAndWait();
	}
	
	public static int unsavePrompt(String currentAbsoultPath)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("记事本");
		alert.setHeaderText("你想将更改保存到 "+currentAbsoultPath+" 吗？");
		alert.setContentText("Choose your option.");

		ButtonType saveButton = new ButtonType("保存(S)");
		ButtonType unsaveButton = new ButtonType("不保存(N)");
		ButtonType cancelButton = new ButtonType("取消", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(saveButton, unsaveButton, cancelButton);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get()==saveButton)
		{
			return 0;
		}else if(result.get()==unsaveButton){
			return -1;
		}else if(result.get()==cancelButton)
		{
			return 1;
		}
		return 1;
	}
	
	private static void showFileError(Alert alert)
	{
		alert.setTitle("Warning Dialog");
		alert.setHeaderText("文件操作失败");
	}
}
