package com.lucaoonk.Virt_Commander.Backend.Processors.Remote;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import com.lucaoonk.Virt_Commander.Backend.Objects.Context;

public class RemoteVMListActionListener implements ActionListener{

    private Context context;
    private JDialog dialog;

    public RemoteVMListActionListener(Context context, JDialog dialog) {
        this.context = context;
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dialog.dispose();
        context.local = true;
        context.refresh();
        
    }


    
}