package com.lucaoonk.virsh_gui.ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.lucaoonk.virsh_gui.Backend.Objects.Context;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ApplicationSettings{
    
    private Context context;
    private JTextArea defaultSaveLocationTextArea;
    

    public void show(Context context){

        this.context = context;
        JDialog settingsDialog = new JDialog(context.mainJFrame);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(2,2));

        settingsDialog.setTitle("Virsh GUI settings");

        showDefaultSaveLocation(panel, context);

        addApplySettings(panel, context);

        settingsDialog.add(panel);
        settingsDialog.setSize(300, 300);
        settingsDialog.setLocationRelativeTo(null);
        settingsDialog.setVisible(true);

    }

    private void addApplySettings(JPanel settingsPane, Context context) {

        settingsPane.add(new JLabel(""));
        JButton applySettingsButton = new JButton("Apply Settings");

        final Context context2 = context;
        applySettingsButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){

                // TODO make changes persistant

                if(context2.defaultSaveLocation.equals(defaultSaveLocationTextArea.getText())){

                }else{
                    context2.defaultSaveLocation = defaultSaveLocationTextArea.getText();
                }
                
            }


        });

        settingsPane.add(applySettingsButton);
    }

    private void showDefaultSaveLocation(JPanel settingsPane, Context context){

        settingsPane.add(new JLabel("Default vm location: "));
        JTextArea defaultSaveLocationTextArea = new JTextArea(context.defaultSaveLocation);
        this.defaultSaveLocationTextArea = defaultSaveLocationTextArea;
        settingsPane.add(defaultSaveLocationTextArea);

    }
}
