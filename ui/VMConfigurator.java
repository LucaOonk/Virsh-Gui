package ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Backend.Controllers.DOMController;
import Backend.Objects.Context;
import Backend.Objects.Disk;
import Backend.Objects.VMCreationObject;
import Backend.Processors.VMDOMCreatorProcessor;

public class VMConfigurator extends JDialog{

    private String[] diskTargets = {"vda", "vdb", "vdc", "vdd", "vde", "vdf", "vdg", "vdh", "vdi"};
    private String[] cdromTargets = {"sda", "sdb", "sdc", "sdd", "sde", "sdf", "sdg", "sdh", "sdi"};
    private String[] archs = {"x86_64"};

    /**
     *
     */
    private static final long serialVersionUID = -1070073774863373888L;
    private Context context;

    public VMConfigurator(Context context){
        this.context = context;
    }


    public void show(){
        JDialog d = new JDialog(context.mainJFrame, "VM Configurator");

        d.setLayout(new GridLayout(9,2));
  
        d.add(new JLabel("VM location:"));
        JTextField vmLocation = new JTextField();
        d.add(vmLocation);

        d.add(new JLabel("VM Name:"));
        JTextField vmName = new JTextField();
        d.add(vmName);

        d.add(new JLabel("VM Cpu's:"));
        JTextField vmCpus = new JTextField();
        d.add(vmCpus);

        d.add(new JLabel("Ram in GB:"));
        JTextField vmRam = new JTextField();
        d.add(vmRam);

        JComboBox<String> vmArch = new JComboBox<String>(archs);
        d.add(new JLabel("VM arch:"));
        d.add(vmArch);

        JComboBox<String> diskTarget = new JComboBox<String>(diskTargets);
        d.add(new JLabel("Disk device target:"));
        d.add(diskTarget);

        d.add(new JLabel("Disk file location:"));
        JTextField diskFileLocation = new JTextField();
        d.add(diskFileLocation);
      
        d.add(new JLabel("VM arguments:"));
        JTextField vmArguments = new JTextField();
        d.add(vmArguments);

        // setsize of dialog
        d.setSize(600, 600);
        d.setLocationRelativeTo(null);
        // set visibility of dialog
        d.setVisible(true);

        d.add(new JLabel(""));
        JButton createVM = new JButton("Create VM");
        d.add(createVM);
        createVM.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                VMCreationObject newVM = new VMCreationObject();
                newVM.vmName = vmName.getText();
                newVM.cpus = Integer.parseInt(vmCpus.getText());
                newVM.ramInGB = Integer.parseInt(vmRam.getText());
                newVM.suspendToDisk = "no";
                newVM.suspendToMem = "no";
                newVM.arch = vmArch.getSelectedItem().toString();
                Disk disk1 = new Disk();
                disk1.device = "disk";
                disk1.source = diskFileLocation.getText();
                disk1.type = "qcow2";
                disk1.target = diskTarget.getSelectedItem().toString();
                newVM.devices.add(disk1);
                newVM.arguments = "-machine type=q35,accel=hvf -netdev user,id=n1 -device virtio-net-pci,netdev=n1,bus=pcie.0,addr=0x19 "+vmArguments.getText();
                d.setVisible(false);
                VMDOMCreatorProcessor.createNewVMDomain(newVM, vmLocation.getText(), context);
                DOMController.defineDomain("", newVM.vmName, context);
                context.refresh();

            }
            
        });
    }

    
}
