package com.lucaoonk.Virt_Commander.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.lucaoonk.Virt_Commander.Backend.RemoteConnector;
import com.lucaoonk.Virt_Commander.Backend.Objects.Context;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LocalBanner extends JPanel implements ActionListener {
 
    private Context context;
    private JButton switchToRemoteButton;
    
    public JPanel getPanel(Context context){

        this.context = context;
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(getBanner(), BorderLayout.CENTER);

        panel.add(topLeftPanel(), BorderLayout.WEST);
        panel.add(switchToRemotePanel(), BorderLayout.EAST);

        
        return panel;
    }

    private JPanel getBanner(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("<html><b>Workspace: Local</b></html>"));

        return panel;
    }

    private JPanel topLeftPanel(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("<html><b>Auto refresh rate ("+context.autoRefreshRate+" Sec)</b></html>"));

        return panel;
    }


    private JPanel switchToRemotePanel(){
        JPanel panel = new JPanel();
        JButton switchToRemoteButton = new JButton("Switch to remote");
        this.switchToRemoteButton = switchToRemoteButton;
        switchToRemoteButton.addActionListener(this);
        panel.add(switchToRemoteButton);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(switchToRemoteButton)){
            RemoteConnector connector = new RemoteConnector(context);
            connector.showConnectorDialog();

            

        }


        

    }

}
