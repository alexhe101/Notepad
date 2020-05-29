package application;

import java.util.Optional;

import javax.naming.spi.DirStateFactory.Result;

import org.omg.CosNaming.NamingContextPackage.AlreadyBound;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.util.Pair;

public class DialogControl {
	public static void showFileSaveError()
	{
		Alert alert = new Alert(AlertType.WARNING);
		showFileError(alert);
		alert.setContentText("�ļ�����ʧ��!");
		alert.showAndWait();
	}
	
	public static void showFileOpenError()
	{

		Alert alert = new Alert(AlertType.WARNING);
		showFileError(alert);
		alert.setContentText("�ļ���ʧ��!");
		alert.showAndWait();
	}
	
	public static int unsavePrompt(String currentAbsoultPath)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("���±�");
		alert.setHeaderText("���뽫���ı��浽 "+currentAbsoultPath+" ��");
		alert.setContentText("Choose your option.");

		ButtonType saveButton = new ButtonType("����(S)");
		ButtonType unsaveButton = new ButtonType("������(N)");
		ButtonType cancelButton = new ButtonType("ȡ��", ButtonData.CANCEL_CLOSE);

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
	
	public static void findPrompt(String srcString)
	{
		//Dialog<>
	}
	
	public static String turnPrompt()
	{
		TextInputDialog dialog = new TextInputDialog("1");
		dialog.setTitle("ת��ָ����");
		dialog.setContentText("�к�(L):");
		while(true)
		{
			Optional<String> resutlt = dialog.showAndWait();
			if(resutlt.isPresent()==false) return null;//ȡ��ѡ��
			if(resutlt.get().matches("\\d")) return resutlt.get();
			else {
				Alert alert  = new Alert(AlertType.ERROR);
				alert.setContentText("���ܽ��ܵ��ַ�������������");
				alert.showAndWait();
			}
		}
	}
	
	public static void notFoundPrompt(String queryString)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText("�Ҳ���"+queryString);
		alert.showAndWait();
	}
	
	private static void showFileError(Alert alert)
	{
		alert.setTitle("Warning Dialog");
		alert.setHeaderText("�ļ�����ʧ��");
	}
	
	
}
