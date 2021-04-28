package com.lucaoonk.virsh_gui.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.lucaoonk.virsh_gui.Backend.Objects.Context;
import com.lucaoonk.virsh_gui.Backend.Objects.VM;
import com.lucaoonk.virsh_gui.ui.Buttons.VMinfoButton;

public class ScrollableVMList extends JScrollPane implements ActionListener{

    /**
     *
     */
    private static final long serialVersionUID = 3439173766707055854L;
    private Context context;
    private VM vm;
    private JButton refreshButton;
    private JButton addNewVMButton;
    private JButton showVMInfoButton;


    public ScrollableVMList(Context context){
        this.context = context;
        context.ScrollableVmListPane = this;
    }

    private JPanel getContent(){
        JPanel content = new JPanel();
        content.setBorder(new EmptyBorder(10, 10, 10, 10));

        content.setLayout(new GridLayout(context.getVMList().size() +2, 2));

        content.add(new JLabel("<html><b>VM name</b></html>")); // now add to jpanel   

        // first create button
        JButton refreshButton = new JButton("Refresh");
        content.add(refreshButton); // now add to jpanel
        this.refreshButton = refreshButton;

        // now add anonymous action listener
        refreshButton.addActionListener(this);


          for (VM vm : context.getVMList()) {

            this.vm = vm;

            // content.add(new JLabel(vm.getID().toString())); // now add to jpanel

            if(vm.isRunning()){
                content.add(new JLabel("<html><b><font color=Green>"+vm.getDomain()+"</b></font></html>")); // now add to jpanel   

            }else{
                content.add(new JLabel("<html><b><font color=Red>"+vm.getDomain()+"</b></font></html>")); // now add to jpanel   

            }

            // first create button
            VMinfoButton button = new VMinfoButton("Show Info", vm, context);
            content.add(button); // now add to jpanel
            // now add anonymous action listener       
          }

        content.add(new JLabel("")); // now add to jpanel

        // first create button
        JButton button = new JButton("add New VM");
        content.add(button); // now add to jpanel
        this.addNewVMButton = button;
        // now add anonymous action listener
        button.addActionListener(this);

        return content;
    }


    public JScrollPane getPanel() {
        JScrollPane pane = new JScrollPane();
        pane.getViewport().add(getContent());
        return pane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if(e.getSource().equals(this.refreshButton)){
            context.refresh();

        }

        if(e.getSource().equals(this.addNewVMButton)){
            // VMConfigurator vmConfigurator = new VMConfigurator(context);
            // vmConfigurator.show();
            VMConfiguratorTabbedPane configurator = new VMConfiguratorTabbedPane(context);
            configurator.show();
        }

        
    }
    
}
