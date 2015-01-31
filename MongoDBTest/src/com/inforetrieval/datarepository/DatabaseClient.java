package com.inforetrieval.datarepository;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class DatabaseClient 
{
	private static MongoClient dbInstance = null;
	
	protected DatabaseClient()
	{
		//to defeat instantiation
	}
	
	//returns a client. Can be instantiated only once.
	public static MongoClient getClient() throws Exception
	{
		try
		{
			if(dbInstance == null)
			{
				dbInstance = new MongoClient(new ServerAddress("localhost", 27017));
			}
			return dbInstance;	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
