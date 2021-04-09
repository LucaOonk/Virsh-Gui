package ui;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Backend.Controllers.DOMController;
import Backend.Objects.Context;
import Backend.Objects.Disk;
import Backend.Objects.VM;
import Backend.Objects.VMCreationObject;
import Backend.Processors.VMDOMCreatorProcessor;

public class ScrollableVMList extends JScrollPane {

    /**
     *
     */
    private static final long serialVersionUID = 3439173766707055854L;
    private Context context;


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
        
        // now add anonymous action listener
        refreshButton.addActionListener(new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            context.refresh();
        }
        });

          for (VM vm : context.getVMList()) {

            // content.add(new JLabel(vm.getID().toString())); // now add to jpanel

            if(vm.isRunning()){
                content.add(new JLabel("<html><b><font color=Green>"+vm.getDomain()+"</b></font></html>")); // now add to jpanel   

            }else{
                content.add(new JLabel("<html><b><font color=Red>"+vm.getDomain()+"</b></font></html>")); // now add to jpanel   

            }

            // first create button
            JButton button = new JButton("Show Info");
            content.add(button); // now add to jpanel

            // now add anonymous action listener
            button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                context.updateCurrentSelectedVM(vm);
                context.refresh();
            }
            });
       
          }

        content.add(new JLabel("")); // now add to jpanel

        // first create button
        JButton button = new JButton("add New VM");
        content.add(button); // now add to jpanel

        // now add anonymous action listener
        button.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            VMCreationObject vmCreationObject = new VMCreationObject();
            vmCreationObject.vmName = "test";
            vmCreationObject.cpus = 2;
            vmCreationObject.ramInGB = 2;
            vmCreationObject.arch = "x86_64";
    
            Disk disk1 = new Disk();
            disk1.device = "disk";
            disk1.type = "qcow2";
            disk1.target = "vda";
            disk1.source = "/Users/lucaoonk/vms/Ubuntu/ubuntu.qcow2";
    
            vmCreationObject.devices.add(disk1);
    
            Disk cdrom = new Disk();
            cdrom.device = "cdrom";
            cdrom.target = "sdb";
            cdrom.source = "/Users/lucaoonk/vms/Ubuntu/ubuntu-20.04.1-live-server-amd64.iso";
    
            vmCreationObject.devices.add(cdrom);
    
            vmCreationObject.arguments = "-machine type=q35,accel=hvf -netdev user,id=n1 -device virtio-net-pci,netdev=n1,bus=pcie.0,addr=0x19";
    
            VMDOMCreatorProcessor.createNewVMDomain(vmCreationObject, "", context);
    
            DOMController.defineDomain("", vmCreationObject.vmName, context);
            context.refresh();
        }
        });

        return content;
    }


    public JScrollPane getPanel() {
        JScrollPane pane = new JScrollPane();
        pane.getViewport().add(getContent());
        return pane;
    }
    
}
