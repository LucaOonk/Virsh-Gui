package com.lucaoonk.Virt_Commander.ui;

import javax.swing.*;
import java.awt.*;

import com.lucaoonk.Virt_Commander.Backend.LoadingScreenThread;
import com.lucaoonk.Virt_Commander.Backend.Objects.Context;

public class LoadingScreen {

    private JLabel statusLabel;
    
    public void showLoadingScreen(Context context){

        JFrame progessFrame = new JFrame("Initializing..."); 
        progessFrame.setSize(200, 200); 
        progessFrame.setLayout(new GridLayout(1,1)); 
        progessFrame.setLocationRelativeTo(null);
          
        JLabel statusLabel = new JLabel("Not Completed", JLabel.CENTER); 
        this.statusLabel = statusLabel;
        progessFrame.add(statusLabel); 

        com.lucaoonk.Virt_Commander.Backend.LoadingScreenThread loadingScreen = new LoadingScreenThread(context, statusLabel, progessFrame);
          
        progessFrame.setVisible(true); 
    }

    public void updateStatusText(){
        
    }

}
