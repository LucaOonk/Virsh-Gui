package com.lucaoonk.virsh_gui.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lucaoonk.virsh_gui.Backend.Controllers.DOMController;
import com.lucaoonk.virsh_gui.Backend.Controllers.VMController;
import com.lucaoonk.virsh_gui.Backend.Objects.Context;
import com.lucaoonk.virsh_gui.Backend.Objects.Device;
import com.lucaoonk.virsh_gui.Backend.Objects.Disk;
import com.lucaoonk.virsh_gui.Backend.Objects.VM;


public class VMDetailsPanel extends JPanel implements ActionListener{

     
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Context context;
    private VM vm;
    protected JDialog dialog;
    private JButton connectVMButton;
    private JButton stopVMButton;
    private JButton startVMButton;
    private JButton undefineVMButton;

    public VMDetailsPanel(Context context) {

        this.context = context;
        context.VMDetailsPanel = this;
    }

    public JPanel getPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());
        panel.add(headPanel(), BorderLayout.NORTH);

        panel.add(getVMDetailsPanel(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel headPanel(){
        JPanel panel = new JPanel();
        VM vm = context.getCurrentSelectedVM();
        String headerText;
        if(vm.isRunning()){
            headerText= "<html><b><font size=16>"+vm.getDomain()+"</font><font size=16 color=Green> Running</font></b></html>";
        }else{
            headerText= "<html><b><font size=16>"+vm.getDomain()+"</font><font size=16 color=Red> Shutdown</font></b></html>";
        }
        panel.add(new JLabel(headerText));

        return panel;
    }

    

    private JPanel getVMDetailsPanel(){

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        VM vm = context.getCurrentSelectedVM();

       
        panel.add(new JLabel(vm.vmDetailsTable()));

        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(getButtonPanel(), BorderLayout.NORTH);

        return mainPanel;
    }


    private JPanel getButtonPanel(){
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.setLayout(new GridLayout(3,1));
        panel.setSize(300, 400);

        this.vm = context.getCurrentSelectedVM();
            
        JButton button = new JButton("Connect to VM");
        this.connectVMButton = button;
        panel.add(button); // now add to jpanel

        button.addActionListener(this);

        JButton UndefineButton = new JButton("Undefine VM");
        UndefineButton.setForeground(Color.RED);;

        panel.add(UndefineButton); // now add to jpanel
        this.undefineVMButton = UndefineButton;
        UndefineButton.addActionListener(this);

        JButton startbutton = new JButton("Start VM");

        panel.add(startbutton); // now add to jpanel
        this.startVMButton = startbutton;
        startbutton.addActionListener(this);

        JButton stopbutton = new JButton("Shutdown VM");

        panel.add(stopbutton); // now add to jpanel
        this.stopVMButton = stopbutton;
        stopbutton.addActionListener(this);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if(e.getSource().equals(this.connectVMButton)){
            if(!VMDetailsPanel.this.vm.isRunning()){
                JDialog d = new JDialog(context.mainJFrame, "Error");
  
                // create a label
                JLabel l = new JLabel("The vm is not running");
      
                d.add(l);
      
                // setsize of dialog
                d.setSize(150, 150);
                d.setLocationRelativeTo(null);
                // set visibility of dialog
                d.setVisible(true);
            }else{
                VMController VMC = new VMController(context);
                VMC.connectToVM(vm);
            }
        }

        if(e.getSource().equals(this.startVMButton)){
            if(VMDetailsPanel.this.vm.isRunning()){
                JDialog d = new JDialog(context.mainJFrame, "Error");
  
                // create a label
                JLabel l = new JLabel("The vm is already running");
      
                d.add(l);
      
                // setsize of dialog
                d.setSize(150, 150);
                d.setLocationRelativeTo(null);
                // set visibility of dialog
                d.setVisible(true);
            }else{
                VMController VMC = new VMController(context);
                VMC.startVM(VMDetailsPanel.this.vm);
            }
        }

        if(e.getSource().equals(this.stopVMButton)){
            if(!VMDetailsPanel.this.vm.isRunning()){
                JDialog d = new JDialog(context.mainJFrame, "Error");
  
                // create a label
                JLabel l = new JLabel("The vm is already shutdown");
      
                d.add(l);
      
                // setsize of dialog
                d.setSize(150, 150);
                d.setLocationRelativeTo(null);
                // set visibility of dialog
                d.setVisible(true);
            }else{
                VMController VMC = new VMController(context);
                VMC.stopVM(VMDetailsPanel.this.vm);
            }
        }

        if(e.getSource().equals(this.undefineVMButton)){

            if(VMDetailsPanel.this.vm.isRunning()){
                JDialog d = new JDialog(context.mainJFrame, "Error");
  
                // create a label
                JLabel l = new JLabel("The vm is running");
      
                d.add(l);
      
                // setsize of dialog
                d.setSize(150, 150);
                d.setLocationRelativeTo(null);
                // set visibility of dialog
                d.setVisible(true);
            }else{

                this.dialog = new JDialog(context.mainJFrame, "Are you sure?");
                dialog.setLayout(new FlowLayout());
                // create a label
                JLabel l = new JLabel("<html>This cant be undone, the vm will be undefined.<br>The files will not be deleted.</html>");
                JButton b = new JButton("Undefine VM");

                b.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        DOMController.undefineDomain(VMDetailsPanel.this.vm.getDomain(), context);
                        VMDetailsPanel.this.dialog.setVisible(false);

                    }

                });

                dialog.add(l);
                dialog.add(b);

                // setsize of dialog
                dialog.setSize(350, 125);
                dialog.setLocationRelativeTo(null);
                // set visibility of dialog
                dialog.setVisible(true);


            }
        }
        
    }
    
    
}
