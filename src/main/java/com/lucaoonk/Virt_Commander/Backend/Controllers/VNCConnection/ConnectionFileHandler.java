package com.lucaoonk.Virt_Commander.Backend.Controllers.VNCConnection;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class ConnectionFileHandler {

    private ConnectionData connectionData;

    public ConnectionFileHandler(ConnectionData connectionData) {

        this.connectionData = connectionData;

    }

    public void export(String location) throws IOException {

        StringBuilder connection = new StringBuilder();
        FileWriter connectionFile;
        try{
            connectionFile= new FileWriter(location);
            if(connectionData.getConnectionType().equals(ConnectionData.connectionTypes.TCP)){

                connection.append("ConnMethod=tcp\n");
    
            }else{
                if(connectionData.getConnectionType().equals(ConnectionData.connectionTypes.UDP)){
                    
                    connection.append("ConnMethod=udp\n");
    
                }
            }
    
            connection.append("ConnTime="+Calendar.getInstance().getTime()+"\n");
            connection.append("FriendlyName="+connectionData.getFriendlyName()+"\n");
            connection.append("Host="+connectionData.getIP()+"\n");
            connection.append("RelativePtr=0\n");
            connection.append("Uuid="+UUID.randomUUID()+"\n");
    
            connectionFile.write(connection.toString());
            connectionFile.close();
            
        } catch (Exception e) {
            //TODO: handle exception
        }
        

    }

}
