package fr.mrstandu33.printocraft;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlConnection {

	private Connection connection;
	private String urlbase,user,pass;
	
	public SqlConnection(String urlbase, String user, String pass)
	{
		this.urlbase = urlbase;
		this.user = user;
		this.pass = pass;
	}
	
	public void connection()
	{
		if (!isConnected())
		{
			try
			{
				connection = DriverManager.getConnection(urlbase, user, pass);
				System.out.println("Connected to the database");
			}
			catch (SQLException e)
			{
				System.out.println("Cannot connect to the database");
				e.printStackTrace();
			}
		}
	}
	
	public void disconnect()
	{
		if (isConnected())
		{
			try
			{
				connection.close();
				System.out.println("Disonnected to the database");
			}
			catch (SQLException e)
			{
				System.out.println("Cannot disconnect to the database");
				e.printStackTrace();
			}
		}
	}
	
	public boolean isConnected()
	{
		return connection != null;
	}
	
	public boolean isSchem()
	{
		PreparedStatement q;
		try
		{
			q = connection.prepareStatement("SELECT * FROM Test WHERE Etatschem = 0");
			ResultSet res = q.executeQuery();
			boolean doihavetoprintsomething = res.next();
			q.close();
			return doihavetoprintsomething;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public int coord(String coord)
	{
		try
		{ 
			int id;
			PreparedStatement q = connection.prepareStatement("SELECT * FROM Test WHERE Etatschem = 0");
			ResultSet res = q.executeQuery();
			while (res.next())
		      {
				id = res.getInt(coord);
				q.close();
				return id;
		      }
			return 0;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	public String IDquery()
	{
		try
		{ 
			String id;
			PreparedStatement q = connection.prepareStatement("SELECT * FROM Test WHERE Etatschem = 0");
			ResultSet res = q.executeQuery();
			while (res.next())
		      {
				id = res.getString("ID");
				q.close();
				return id;
		      }
			return null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}