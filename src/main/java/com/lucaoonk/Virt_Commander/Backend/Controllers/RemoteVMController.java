package com.lucaoonk.Virt_Commander.Backend.Controllers;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.lucaoonk.Virt_Commander.Backend.Objects.VM;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class RemoteVMController {
 
    
    public static void startVm(VM vm, String remoteAddress, String httpAuth){

        try {

            HttpResponse<String> response = Unirest.post(remoteAddress+"/vm/")
            .header("Content-Type", "application/json")
            .header("Authentication", httpAuth)
            .body("{\"name\":\""+vm.getDomain()+"\",\"action\":\"start\"}")
            .asString();
        
            Gson g = new Gson();
            VM remote = g.fromJson(response.getBody(), VM.class);

        } catch (Exception e) {

            JDialog dialog = new JDialog();
            JPanel panel = new JPanel();
            dialog.setTitle("Error connecting to remote server!");
            JLabel label = new JLabel("<html><b>Make sure u enterd the correct address</b></html>");
            panel.setBorder(new EmptyBorder(10,10,10,10));
            panel.add(label);
            dialog.add(panel);
            dialog.setSize(300, 150);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            dialog.setResizable(false);
            dialog.setAlwaysOnTop(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        }

    }   


    public static void stopVM(VM vm, String remoteAddress, String httpAuth){

        try {
            HttpResponse<String> response = Unirest.post(remoteAddress+"/vm/")
            .header("Content-Type", "application/json")
            .header("Authentication", httpAuth)
            .body("{\"name\":\""+vm.getDomain()+"\",\"action\":\"stop\"}")
            .asString();
        
            Gson g = new Gson();
            VM remote = g.fromJson(response.getBody(), VM.class);

        } catch (Exception e) {

            JDialog dialog = new JDialog();
            JPanel panel = new JPanel();
            dialog.setTitle("Error connecting to remote server!");
            JLabel label = new JLabel("<html><b>Make sure u enterd the correct address</b></html>");
            panel.setBorder(new EmptyBorder(10,10,10,10));
            panel.add(label);
            dialog.add(panel);
            dialog.setSize(300, 150);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            dialog.setResizable(false);
            dialog.setAlwaysOnTop(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        }

    }   
}
