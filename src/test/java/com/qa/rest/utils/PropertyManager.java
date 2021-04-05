package com.qa.rest.utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
	private static PropertyManager instance;
    private static final Object lock = new Object();
    private static String propertyFilePath ="rest.properties";
    private static String url;
    private static String access_token;
    private static String user_name;
    private static String updated_name;
    
    
    public static PropertyManager getInstance () {
        if (instance == null) {
            synchronized (lock) {
                instance = new PropertyManager();
                instance.loadData();
            }
        }
        return instance;
    }
    private void loadData() {
        //Declare a properties object
        Properties prop = new Properties();
 
        //Read configuration.properties file
        try {
            prop.load(new FileInputStream(propertyFilePath));
        } catch (IOException e) {
            System.out.println("Configuration properties file cannot be found");
        }
 
        //Get properties from configuration.properties
        url = prop.getProperty("rest.url");
        access_token = prop.getProperty("access.token");
        user_name = prop.getProperty("user.name");
        updated_name = prop.getProperty("user.newName");
    }
    public String getURL () {
        return url;
      }
   
      public String getAccessToken () {
          return access_token;
      }
      
      public String getUserName () {
          return user_name;
      }
      
      public String getUpdatedUserName () {
          return updated_name;
      }
   
}
