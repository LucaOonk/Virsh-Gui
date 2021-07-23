package com.lucaoonk.Virt_Commander.Backend.Controllers.VNCConnection;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import com.lucaoonk.Virt_Commander.CrashReporter.CrashReporter;

public class ConnectionData {

    private String FriendlyName;
    private String ip;
    private Enum<connectionTypes> connectionType;


    public enum connectionTypes{
        TCP,
        UDP
    }
    
    public ConnectionData(){
        this.connectionType = connectionTypes.TCP;
    }

    public void setFriendlyName(String friendlyName){
        this.FriendlyName = friendlyName;
    }

    public void setIP(String ip){
        this.ip = ip;
    }

    public void setConnectionType(Enum<connectionTypes> connectionType){
        this.connectionType = connectionType;
    }

    public String getFriendlyName(){
        return this.FriendlyName;
    }
    public String getIP(){
        return this.ip;
    }
    public Enum<connectionTypes> getConnectionType(){
        return this.connectionType;
    }

    public void export(String location) throws IOException {

        StringBuilder connection = new StringBuilder();
        FileWriter connectionFile;
        try{
            connectionFile= new FileWriter(location);
            if(this.getConnectionType().equals(ConnectionData.connectionTypes.TCP)){

                connection.append("ConnMethod=tcp\n");
    
            }else{
                if(this.getConnectionType().equals(ConnectionData.connectionTypes.UDP)){
                    
                    connection.append("ConnMethod=udp\n");
    
                }
            }
    
            connection.append("ConnTime="+Calendar.getInstance().getTime()+"\n");
            connection.append("FriendlyName="+this.getFriendlyName()+"\n");
            connection.append("Host="+this.getIP()+"\n");
            connection.append("RelativePtr=0\n");
            connection.append("Uuid="+UUID.randomUUID()+"\n");
    
            connectionFile.write(connection.toString());
            connectionFile.close();
            
        } catch (Exception e) {
            CrashReporter.logCrash(e);
        }
        

    }

}