package com.lucaoonk.virsh_gui.Backend.Objects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.Gson;

import org.json.simple.JSONObject;

public class ApplicationSettings{

    private static final String settingsFileLocation = System.getProperty("user.home") + "/Library/Application Support/Virsh_GUI/settings.json";

    public static void writeSettings(Context context){
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("checkForUpdates", context.checkForUpdates);
        jsonObject.put("defaultSaveLocation", context.defaultSaveLocation);

        File location = new File(settingsFileLocation);
        location.getParentFile().mkdirs();

        FileWriter file;
        try {
        if(ApplicationSettings.settingsFileExists()){


                file = new FileWriter(settingsFileLocation, false);

            file.write(jsonObject.toJSONString());
            file.close();       
        }else{

            file = new FileWriter(settingsFileLocation);


            file.write(jsonObject.toJSONString());
            file.close();   
        }
        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
    }

    public static Boolean settingsFileExists(){

        File f = new File(settingsFileLocation);
        if(f.exists() && !f.isDirectory()) { 
            return true;
        }else{
            return false;
        }


    }

    public static void readSettingsFile(Context context){

        if(ApplicationSettings.settingsFileExists()){

            try {
                // create Gson instance
                Gson gson = new Gson();
            
                // create a reader
                Reader reader = Files.newBufferedReader(Paths.get(settingsFileLocation));
            
                // convert JSON file to map
                Map<?, ?> map = gson.fromJson(reader, Map.class);
            
                // print map entries
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    if(entry.getKey().equals("checkForUpdates")){
                        context.checkForUpdates = (Boolean) entry.getValue();

                        System.out.println(context.checkForUpdates);

                    }

                    if(entry.getKey().equals("defaultSaveLocation")){
                        context.defaultSaveLocation = (String) entry.getValue();
                        System.out.println(context.defaultSaveLocation);

                    }
                }
            
                // close reader
                reader.close();
            
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }else{
            System.out.println("File does not exists");

        }


    }
    
}
