package com.lucaoonk.Virt_Commander.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lucaoonk.Virt_Commander.Backend.RemoteConnector;
import com.lucaoonk.Virt_Commander.Backend.Objects.Context;
import com.lucaoonk.Virt_Commander.Backend.Objects.RemoteConnection;

public class RemoteBanner extends JPanel implements ActionListener{

    private Context context;
    private JButton switchToLocalButton;
    private JButton switchConnectionButton;
 
    public RemoteBanner(Context context){
        this.context = context;
    }
    
    public JPanel getPanel(){

        this.context = context;
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(getBanner(), BorderLayout.CENTER);

        panel.add(topLeftPanel(), BorderLayout.WEST);
        panel.add(switchToLocalPanel(), BorderLayout.EAST);        

        return panel;
    }

    private JPanel getBanner(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("<html><b>Workspace: Remote ("+context.remoteAddress+")</b></html>"));

        return panel;
    }

    private JPanel topLeftPanel(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("<html><b>Auto refresh rate ("+context.autoRefreshRate+" Sec)</b></html>"));

        return panel;
    }

    private JPanel switchToLocalPanel(){
        JPanel panel = new JPanel();
        JButton switchToLocalButton = new JButton("Switch to Local");
        this.switchToLocalButton = switchToLocalButton;
        switchToLocalButton.addActionListener(this);
        panel.add(switchToLocalButton);

        JButton switchConnectionButton = new JButton("Switch Connection");
        this.switchConnectionButton = switchConnectionButton;
        switchConnectionButton.addActionListener(this);
        panel.add(switchConnectionButton);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource().equals(switchToLocalButton)){
            context.local = true;
            context.refresh();
        }
        if(e.getSource().equals(switchConnectionButton)){
            RemoteConnector remote=  new RemoteConnector(context);
            remote.showConnectorDialog();
            context.refresh();
        }
        
    }
}
