package com.inforetrieval.runnable;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;




import com.inforetrieval.datarepository.DatabaseClient;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
public class MongoTest 
{
	public static void main(String[] args)
	{
		try
		{
			MongoClient mongoClient = DatabaseClient.getClient();
			mongoClient.setWriteConcern(WriteConcern.JOURNALED);
            // creating a db named "ICS" and a collection named "book"
            DB db = mongoClient.getDB("ICS");
			DBCollection collection = db.getCollection("files");
			BufferedReader _bufReader;
			ArrayList<String> filePathList = new ArrayList<String>();
			//For every file in the path containing crawled data
			Files.walk(Paths.get("E:\\Software\\New folder")).forEach(filePath -> 
			{
			    if(Files.isRegularFile(filePath)) 
			    {
			    	filePathList.add(filePath.toString());
			    }
			});
			String inputLine = "";
			BasicDBObject document = null;
			for(String fp : filePathList)
			{
				_bufReader = new BufferedReader(new FileReader(fp));
				while((inputLine = _bufReader.readLine()) != null )
				{
					document = ReadFileLineIntoDBDocument(inputLine,fp);
					collection.insert(document);
				}
			}
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
	
	public static BasicDBObject ReadFileLineIntoDBDocument(String line, String fp)
	{
		BasicDBObject document = new BasicDBObject();
		//document.put("database", "mkyongDB");
		//document.put("table", "hosting");
		document.put("file name", fp);
		BasicDBObject documentDetail = new BasicDBObject();
		documentDetail.put("value",line);
		//documentDetail.put("index", "vps_index1");
		//documentDetail.put("active", "true");
		document.put("detail", documentDetail);
		return document;
	}
}
