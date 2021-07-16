package com.lucaoonk.Virt_Commander.Backend.Controllers.VNCConnection;

import java.io.File;
import java.io.IOException;

import com.lucaoonk.Virt_Commander.Backend.Objects.VM;

public class VNCConnection {

    private VM vm;

    public VNCConnection(VM vm){
        this.vm = vm;
    }

    public void connect(){

        System.out.println("Triggerd VNC connect");

        String location = System.getProperty("user.home") + "/Desktop/Virt_Commander-VNC/";
        String vncConnectFile = location+vm.getDomain()+".vnc";
        ConnectionData data = new ConnectionData();
        data.setFriendlyName(vm.getDomain());
        data.setIP(vm.vncIP+":"+vm.vncPort);
        
        try {
            File file = new File(vncConnectFile);
            file.getParentFile().mkdirs();
            
            data.export(vncConnectFile);
            System.out.println("VNC file: "+vncConnectFile);

                
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
