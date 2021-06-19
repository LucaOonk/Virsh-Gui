package com.lucaoonk.Virt_Commander.ui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.lucaoonk.Virt_Commander.Backend.Objects.ApplicationSettings;
import com.lucaoonk.Virt_Commander.Backend.Objects.Context;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ApplicationSettingsView implements ActionListener{
    
    private Context context;
    private JTextArea defaultSaveLocationTextArea;
    private JCheckBox checkForUpdatesCheckbox;
    private JDialog settingsDialog;
    private JTextArea windowHeight;
    private JTextArea windowWidth;
    private JCheckBox autoSizeWindow;
    

    public void show(Context context){

        this.context = context;
        JDialog settingsDialog = new JDialog(context.mainJFrame);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(6,2));

        settingsDialog.setTitle("Virsh GUI settings");

        showDefaultSaveLocation(panel, context);

        showCheckForUpdatesSetting(panel, context);
        showWindowSizeSettings(panel, context);


        addApplySettings(panel, context);
        
        settingsDialog.add(panel);
        settingsDialog.setSize(400, 500);
        settingsDialog.setLocationRelativeTo(null);
        settingsDialog.setVisible(true);

        this.settingsDialog = settingsDialog;
    }

    private void showCheckForUpdatesSetting(JPanel settingsPane, Context context){

        settingsPane.add(new JLabel());

        JCheckBox checkForUpdates = new JCheckBox("Check for updates");
        
        checkForUpdates.setSelected(context.checkForUpdates);
        this.checkForUpdatesCheckbox = checkForUpdates;

        settingsPane.add(checkForUpdates);
    }

    private void addApplySettings(JPanel settingsPane, Context context) {

        settingsPane.add(new JLabel());
        JButton applySettingsButton = new JButton("Apply Settings");

        applySettingsButton.addActionListener(this);

        settingsPane.add(applySettingsButton);
    }

    private void showDefaultSaveLocation(JPanel settingsPane, Context context){

        settingsPane.add(new JLabel("Default vm location: "));
        JTextArea defaultSaveLocationTextArea = new JTextArea(context.defaultSaveLocation);
        this.defaultSaveLocationTextArea = defaultSaveLocationTextArea;
        settingsPane.add(defaultSaveLocationTextArea);

    }

    private void showWindowSizeSettings(JPanel settingsPane, Context context){

        settingsPane.add(new JLabel("Window Heigt: "));
        JTextArea windowHeight = new JTextArea(context.windowHeight.toString());
        this.windowHeight = windowHeight;
        settingsPane.add(windowHeight);

        settingsPane.add(new JLabel("Window Width: "));
        JTextArea windowWidth = new JTextArea(context.windowWidth.toString());
        this.windowWidth = windowWidth;
        settingsPane.add(windowWidth);

        settingsPane.add(new JLabel("<html>If disabled the width and height defined above will be used.</html>"));

        JCheckBox autoSizeWindow = new JCheckBox("Auto size window");
        
        autoSizeWindow.setSelected(context.autoSizeWindow);
        this.autoSizeWindow = autoSizeWindow;

        settingsPane.add(autoSizeWindow);
    }

    @Override
    public void actionPerformed(ActionEvent e){

        // TODO make changes persistant

        context.checkForUpdates = checkForUpdatesCheckbox.isSelected();
        context.autoSizeWindow = autoSizeWindow.isSelected();


        if(context.defaultSaveLocation.equals(defaultSaveLocationTextArea.getText())){

        }else{
            context.defaultSaveLocation = defaultSaveLocationTextArea.getText();
        }

        if(context.windowHeight.equals(windowHeight.getText())){

        }else{
            context.windowHeight = Integer.parseInt(windowHeight.getText());
        }       
        
        if(context.windowWidth.equals(windowWidth.getText())){

        }else{
            context.windowWidth = Integer.parseInt(windowWidth.getText());
        }

        ApplicationSettings.writeSettings(context);
        this.settingsDialog.setVisible(false);
    }
}
