package com.lucaoonk.Virt_Commander.Backend.Processors.Remote;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lucaoonk.Virt_Commander.Backend.Objects.Context;
import com.lucaoonk.Virt_Commander.Backend.Objects.VM;
import com.lucaoonk.Virt_Commander.Backend.Processors.Remote.Objects.VMList;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class RemoteVMListProcessor {

    private ArrayList<VM> vmList;
    private Context context;

    public RemoteVMListProcessor(Context context){
        this.vmList = new ArrayList<VM>();
        this.context = context;
    }

    public void runCommand() throws IOException{
        this.vmList = getVMdomainList(context.remoteAddress, context, context.httpAuth);
        
        context.updateVMList(vmList);
    }

    public static ArrayList<VM> getVMdomainList(String remoteAddress, Context context, String httpAuth) throws IOException {

        try {
            HttpResponse<String> response = Unirest.get(remoteAddress+"/list")
            .header("Content-Type", "application/json")
            .header("Authentication", httpAuth)
            .asString();
    
            Gson g = new Gson();
    
            VMList list = g.fromJson(response.getBody(), VMList.class);
    
            ArrayList<VM> actualVMs = new ArrayList<VM>();
    
            for (VM vm : list.vms) {
                actualVMs.add(RemoteVMDetailProcessor.getDetails(vm, remoteAddress, httpAuth));
            }
    
            return actualVMs;
        } catch (Exception e) {

            JDialog dialog = new JDialog();
            JPanel panel = new JPanel();
            JButton switchToLocalButton = new JButton("Switch to local");
            // switchToLocalButton.addActionListener(new SwitchToLocalListener());
            dialog.setTitle("Error connecting to remote server!");
            JLabel label = new JLabel("<html><b>Make sure u entered the correct address</b></html>");
            switchToLocalButton.addActionListener(new RemoteVMListActionListener(context, dialog));
            panel.setBorder(new EmptyBorder(10,10,10,10));
            panel.add(label);
            panel.add(switchToLocalButton);

            // panel.add(switchToLocalButton);
            dialog.add(panel);
            dialog.setSize(300, 150);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            dialog.setResizable(false);
            dialog.setAlwaysOnTop(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            return null;
        }


    }
    
}


