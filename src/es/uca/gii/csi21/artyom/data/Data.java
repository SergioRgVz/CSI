package es.uca.gii.csi21.artyom.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import java.sql.ResultSet;

import es.uca.gii.csi21.artyom.util.Config;

public class Data {
	
	/* TODO description*/
    public static String getPropertiesUrl() { return "./db.properties"; }
	
    public static Connection Connection() throws Exception {
		
        try {
            Properties properties = Config.Properties(getPropertiesUrl());
            System.out.println(properties.getProperty("jdbc.username"));
            return DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"));
       }
       catch (Exception ee) { throw ee; }
	}
    
    /* TODO description*/
    public static void LoadDriver() 
        throws InstantiationException, IllegalAccessException, 
        ClassNotFoundException, IOException {
			
            Class.forName(Config.Properties(Data.getPropertiesUrl()
            ).getProperty("jdbc.driverClassName")).newInstance();
    }
    
    /* TODO description*/
    public static String String2Sql( String s, boolean bAddQuotes, boolean bAddWildcards) {
    	String sSql = s.replace("'", "''");
    	
    	if(bAddWildcards) {
    		sSql = "%" + sSql + "%";
    	}
    	
    	if(bAddQuotes) {
    		sSql = "'" + sSql + "'";
    	}
    	
    	return sSql;
    }
    
    /* TODO description*/
    public static int Boolean2Sql(boolean b) {
    	return (b) ? 1 : 0;
    }
    
    /* TODO description*/
    /* TODO cambiar lastID (ya no es autoincremental) */
    public static int LastId(Connection con) throws Exception {
    	ResultSet rs = null;
    	
    	try {
    		Properties properties = Config.Properties(getPropertiesUrl());
    		rs = con.createStatement().executeQuery(properties.getProperty("jdbc.lastIdSentence"));
    		rs.next();
    		
    		return rs.getInt("LAST_INSERT_ID()");
    	}
    	catch(Exception ee) { throw ee; }
    	finally { if(rs != null) rs.close(); }
    }
}