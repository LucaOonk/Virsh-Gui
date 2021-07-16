package com.lucaoonk.Virt_Commander.Backend;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.lucaoonk.Virt_Commander.Backend.Objects.Context;
import com.lucaoonk.Virt_Commander.Backend.Objects.RemoteConnection;
import com.lucaoonk.Virt_Commander.Backend.Objects.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class RemoteConnector implements ActionListener{
 
    private JDialog remoteDialog;
    private JButton switchButton;
    private JButton editConnections;
    private Context context;
    private JComboBox combobox;
    private JTextArea remoteAddressText;
    private JButton removeButton;
    private JTextArea remoteNameText;
    private JButton addButton;
    private JButton switchToLocalButton;

    public RemoteConnector(Context context){
        this.context = context;
    }

    public void showConnectorDialog(){
        JDialog dialog = new JDialog();
        this.remoteDialog = dialog;
        JPanel panel = new JPanel();
        JButton switchButton = new JButton("Switch to remote");
        switchButton.addActionListener(this);
        this.switchButton = switchButton;
        JButton editConnections = new JButton("Edit connections");
        editConnections.addActionListener(this);
        this.editConnections = editConnections;
        // switchToLocalButton.addActionListener(new SwitchToLocalListener());
        dialog.setTitle("Remote workspace");
        JComboBox comboBox = new JComboBox<>(this.context.getRemoteConnectionComboItems());
        this.combobox = comboBox;
        JLabel label = new JLabel("<html><b>Select a remote address to connect to</b></html>");
        JTextArea remoteAddressText = new JTextArea();
        this.remoteAddressText = remoteAddressText;
        // remoteAddressText.setSize(100, 20);
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setLayout(new GridLayout(4,1));

        panel.add(label);
        panel.add(comboBox);
        panel.add(switchButton);
        panel.add(editConnections);

        // panel.add(switchToLocalButton);
        dialog.add(panel);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.setResizable(false);
        dialog.setAlwaysOnTop(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource().equals(switchButton)){

            if(context.remoteConnections.size() == 0){
             
                showInvalidConnectionError();

            }else{

                Object item = this.combobox.getSelectedItem();
                RemoteConnection connection = ((RemoteConnectionComboItem)item).getValue();
    
                context.remoteAddress = connection.address;
                context.local = false;
                remoteDialog.dispose();
                context.refresh();
    
            }


        }

        if(e.getSource().equals(editConnections)){
            remoteDialog.setVisible(false);

            showConnectionEditor();
        }

        if(e.getSource().equals(removeButton)){
            Object item = this.combobox.getSelectedItem();
            RemoteConnection value = ((RemoteConnectionComboItem)item).getValue();        
            
            context.remoteConnections.remove(value);
            context.writeConnections();
            context.refresh();

            remoteDialog.setVisible(false);

            RemoteConnector newDialog = new RemoteConnector(context);
            newDialog.showConnectionEditor();
            
        }
        
        if(e.getSource().equals(addButton)){

            RemoteConnection connection = new RemoteConnection();
            connection.address = remoteAddressText.getText();
            connection.name = remoteNameText.getText();
            
            System.out.println(connection.name+" " + connection.address);
            context.remoteConnections.add(connection);
            context.writeConnections();
            context.refresh();
            remoteDialog.revalidate();
        }
        if(e.getSource().equals(switchToLocalButton)){
            context.local = true;
            context.refresh();
        }
    }

    private void showConnectionEditor(){
        JDialog dialog = new JDialog();
        this.remoteDialog = dialog;
        JPanel panel = new JPanel();
        dialog.setTitle("Connections Editor");
        JComboBox comboBox = new JComboBox<>(context.getRemoteConnectionComboItems());
        this.combobox = comboBox;
        JTextArea remoteAddressText = new JTextArea();
        remoteAddressText.setSize(80, 20);
        this.remoteAddressText = remoteAddressText;

        JTextArea remoteNameText = new JTextArea();
        this.remoteNameText = remoteNameText;
        remoteNameText.setSize(80, 20);

        // remoteAddressText.setSize(100, 20);
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setLayout(new GridLayout(5,2));

        panel.add(new JLabel("<html><b>Select a connection and click remove to remove it</b></html>"));
        panel.add(new JLabel("<html><b>New Connection Name</b></html>"));

        panel.add(comboBox);

        panel.add(remoteNameText);

        JButton removeButton = new JButton("Remove");
        this.removeButton = removeButton;
        removeButton.addActionListener(this);
        panel.add(removeButton);

        panel.add(new JLabel("<html><b>New Connection Adress</b></html>"));


        panel.add(new JLabel());


        panel.add(remoteAddressText);

        panel.add(new JLabel());

        JButton addButton = new JButton("Add new Connection");
        this.addButton = addButton;
        addButton.addActionListener(this);
        panel.add(addButton);

        
        // panel.add(switchToLocalButton);
        dialog.add(panel);
        dialog.setSize(500, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.setResizable(false);
        dialog.setAlwaysOnTop(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void showInvalidConnectionError(){
        JDialog dialog = new JDialog();
        this.remoteDialog = dialog;
        JPanel panel = new JPanel();
        dialog.setTitle("Invalid connection info");
        JComboBox comboBox = new JComboBox<>(context.getRemoteConnectionComboItems());
        this.combobox = comboBox;
        JLabel label = new JLabel("<html><b>This connection does not have a valid address. Try adding one or add it again.</b></html>");
        JTextArea remoteAddressText = new JTextArea();
        this.remoteAddressText = remoteAddressText;
        // remoteAddressText.setSize(100, 20);
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setLayout(new GridLayout(1,1));


        panel.add(label);

        // panel.add(switchToLocalButton);
        dialog.add(panel);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.setResizable(false);
        dialog.setAlwaysOnTop(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

}
