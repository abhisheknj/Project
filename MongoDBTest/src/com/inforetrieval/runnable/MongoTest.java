package com.inforetrieval.runnable;

import java.util.ArrayList;

import com.inforetrieval.datarepository.DatabaseClient;
import com.inforetrieval.filemanager.CustomFileReader;
import com.inforetrieval.filemanager.FileUtilities;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
public class MongoTest 
{
	public static void main(String[] args)
	{
		try
		{
			MongoClient mongoClient = DatabaseClient.getClient();
			mongoClient.setWriteConcern(WriteConcern.JOURNALED);
            // creating a db named "ICS" and a collection named "book"
            DB database = DatabaseClient.GetDatabase("ICS");
			DBCollection collection = DatabaseClient.GetDBCollection(database, "ICSData");
			ArrayList<String> filePathList = new ArrayList<String>();
			//For every file in the path containing crawled data
			filePathList = FileUtilities.GetListOfFiles("E:\\Software\\New folder");
			CustomFileReader fileReader = new CustomFileReader(collection);
			fileReader.WriteFilesIntoDatabase(filePathList);
			
			DBCursor cursorDoc = collection.find();
			while (cursorDoc.hasNext()) {
				System.out.println(cursorDoc.next());
			}
			collection.remove(new BasicDBObject());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
