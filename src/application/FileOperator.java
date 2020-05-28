package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileOperator {
	public static String readFile(File f) throws IOException
	{
		byte[] data = Files.readAllBytes(f.toPath());
		String result = new String(data,"utf-8");
		return result;
	}
	
	public static void writeFile(File f,String output) throws IOException
	{
		byte []data = output.getBytes();
		Files.write(f.toPath(), data);
	}
}
