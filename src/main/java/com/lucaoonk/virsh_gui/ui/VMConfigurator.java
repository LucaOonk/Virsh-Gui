package com.lucaoonk.virsh_gui.ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.lucaoonk.virsh_gui.Backend.Controllers.DOMController;
import com.lucaoonk.virsh_gui.Backend.Objects.Context;
import com.lucaoonk.virsh_gui.Backend.Objects.Disk;
import com.lucaoonk.virsh_gui.Backend.Objects.VMCreationObject;
import com.lucaoonk.virsh_gui.Backend.Processors.VMDOMCreatorProcessor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VMConfigurator extends JDialog{

    private String[] diskTargets = {"vda", "vdb", "vdc", "vdd", "vde", "vdf", "vdg", "vdh", "vdi"};
    private String[] cdromTargets = {"sda", "sdb", "sdc", "sdd", "sde", "sdf", "sdg", "sdh", "sdi"};
    private String[] archs = {"x86_64", "arm", "i386", "m68k", "ppc", "ppc64"};
    private String htmlStart = "<html>";
    private String htmlEnd = "</html>";
    private String boldStart = "<b>";
    private String boldlEnd = "</b>";
    /**
     *
     */
    private static final long serialVersionUID = -1070073774863373888L;
    private Context context;

    public VMConfigurator(Context context){
        this.context = context;
    }


    public void show(){
        final JDialog dialog = new JDialog(context.mainJFrame, "VM Configurator");

        JPanel d = new JPanel();
        d.setBorder(new EmptyBorder(10, 10, 10, 10));

        d.setLayout(new GridLayout(12,2));
  
        d.add(new JLabel(htmlStart+boldStart+"VM location"+boldlEnd+": <br>if empty it wil be placed in the set default folder: "+context.getDefaultSaveLocation()+htmlEnd));
        final JTextField vmLocation = new JTextField();
        d.add(vmLocation);

        d.add(new JLabel(htmlStart+boldStart+"VM Name:"+boldlEnd+htmlEnd));
        final JTextField vmName = new JTextField();
        d.add(vmName);

        d.add(new JLabel(htmlStart+boldStart+"VM Cpu's:"+boldlEnd+htmlEnd));
        final JTextField vmCpus = new JTextField();
        d.add(vmCpus);

        d.add(new JLabel(htmlStart+boldStart+"Ram in GB:"+boldlEnd+htmlEnd));
        final JTextField vmRam = new JTextField();
        d.add(vmRam);

        final JComboBox<String> vmArch = new JComboBox<String>(archs);
        d.add(new JLabel(htmlStart+boldStart+"VM arch:"+boldlEnd+htmlEnd));
        d.add(vmArch);

        final JComboBox<String> diskTarget = new JComboBox<String>(diskTargets);
        d.add(new JLabel(htmlStart+boldStart+"Disk device target:"+boldlEnd+htmlEnd));
        d.add(diskTarget);

        d.add(new JLabel(htmlStart+boldStart+"Disk file location:"+boldlEnd+htmlEnd));
        final JTextField diskFileLocation = new JTextField();
        d.add(diskFileLocation);

        final JComboBox<String> cdromTarget = new JComboBox<String>(cdromTargets);
        d.add(new JLabel(htmlStart+boldStart+"CDrom device target:"+boldlEnd+htmlEnd));
        d.add(cdromTarget);

        d.add(new JLabel(htmlStart+boldStart+"CDrom file location:"+boldlEnd+htmlEnd));
        final JTextField cdromFileLocation = new JTextField();
        d.add(cdromFileLocation);
      
        d.add(new JLabel(htmlStart+boldStart+"VM arguments:"+boldlEnd+htmlEnd));
        final JTextField vmArguments = new JTextField();
        d.add(vmArguments);

        d.add(new JLabel(htmlStart+boldStart+"Forwarded ports"+boldlEnd+":<br> format: protocol::external Port-:interal Port<br>e.g.: tcp::2222:22,tcp::8080-8081:80"+htmlEnd));
        final JTextField vmForwardedPorts = new JTextField();
        d.add(vmForwardedPorts);

        // setsize of dialog
        dialog.setSize(600, 600);
        dialog.setLocationRelativeTo(null);
        // set visibility of dialog
        dialog.setVisible(true);

        d.add(new JLabel(""));
        JButton createVM = new JButton("Create VM");
        d.add(createVM);

        dialog.add(d);
        createVM.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                final VMCreationObject newVM = new VMCreationObject();
                newVM.vmName = vmName.getText();
                newVM.cpus = Integer.parseInt(vmCpus.getText());
                newVM.ramInGB = Integer.parseInt(vmRam.getText());
                newVM.suspendToDisk = "no";
                newVM.suspendToMem = "no";
                newVM.arch = vmArch.getSelectedItem().toString();

                final Disk disk1 = new Disk();
                disk1.device = "disk";
                disk1.source = diskFileLocation.getText();
                disk1.type = "qcow2";
                disk1.target = diskTarget.getSelectedItem().toString();
                newVM.devices.add(disk1);

                if(!vmForwardedPorts.getText().equals("")){

                    newVM.arguments = "-machine type=q35,accel=hvf -netdev user,id=n1,"+vmForwardedPorts.getText()+" -device virtio-net-pci,netdev=n1,bus=pcie.0,addr=0x19 "+vmArguments.getText();

                }else{
                    newVM.arguments = "-machine type=q35,accel=hvf -netdev user,id=n1 -device virtio-net-pci,netdev=n1,bus=pcie.0,addr=0x19 "+vmArguments.getText();

                }
                
                final Disk cdrom = new Disk();
                cdrom.device = "cdrom";
                cdrom.source = cdromFileLocation.getText();
                cdrom.target = cdromTarget.getSelectedItem().toString();

                newVM.devices.add(cdrom);

                dialog.setVisible(false);
                VMDOMCreatorProcessor.createNewVMDomain(newVM, vmLocation.getText(), context);
                DOMController.defineDomain(vmLocation.getText(), newVM.vmName, context);
                context.refresh();

            }
            
        });
    }

    
}
