package com.inforetrieval.filemanager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class CustomFileReader 
{
	DBCollection collection;
	public CustomFileReader(DBCollection dbCollection)
	{
		collection = dbCollection;
	}
	public boolean WriteFilesIntoDatabase(ArrayList<String> listOfFiles) throws Exception
	{
		BufferedReader _bufReader;
		String inputLine = "";
		BasicDBObject document = null;
		try
		{
			for(String fp : listOfFiles)
			{
				_bufReader = new BufferedReader(new FileReader(fp));
				while((inputLine = _bufReader.readLine()) != null )
				{
					document = FileUtilities.ReadFileLineIntoDBDocument(inputLine,fp);
					collection.insert(document);
				}
				_bufReader.close();
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
