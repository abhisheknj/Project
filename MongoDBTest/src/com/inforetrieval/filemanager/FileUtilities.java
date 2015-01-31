package com.inforetrieval.filemanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;

public class FileUtilities 
{
	//converts a regular string to a MongoDB compatible 'document'
	public static BasicDBObject ReadFileLineIntoDBDocument(String line, String fp) throws Exception
	{
		BasicDBObject document;
		try
		{
			document = new BasicDBObject();
			document.put("file name", fp);
			BasicDBObject documentDetail = new BasicDBObject();
			documentDetail.put("value",line);
			document.put("detail", documentDetail);
			return document;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	//returns a list of file names corresponding to the file path.
	public static ArrayList<String> GetListOfFiles(String path) throws Exception
	{
		ArrayList<String> filePathList = new ArrayList<String>();
		try 
		{
			Files.walk(Paths.get(path)).forEach(filePath -> 
			{
			    if(Files.isRegularFile(filePath)) 
			    {
			    	filePathList.add(filePath.toString());
			    }
			});
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		return filePathList;
	}
}
