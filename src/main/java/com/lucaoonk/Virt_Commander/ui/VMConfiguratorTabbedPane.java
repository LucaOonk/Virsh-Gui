package com.lucaoonk.Virt_Commander.ui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.lucaoonk.Virt_Commander.Backend.Controllers.DOMController;
import com.lucaoonk.Virt_Commander.Backend.Controllers.VMController;
import com.lucaoonk.Virt_Commander.Backend.Objects.Context;
import com.lucaoonk.Virt_Commander.Backend.Objects.Disk;
import com.lucaoonk.Virt_Commander.Backend.Objects.VMCreationObject;
import com.lucaoonk.Virt_Commander.Backend.Processors.Local.VMDOMCreatorProcessor;
import com.lucaoonk.Virt_Commander.CrashReporter.CrashReporter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VMConfiguratorTabbedPane implements ActionListener {

    /**
     *
     */
    private Context context;
    private JPanel generalPane;
    private String[] diskTargets = {"vda", "vdb", "vdc", "vdd", "vde", "vdf", "vdg", "vdh", "vdi"};
    private String[] cdromTargets = {"sda", "sdb", "sdc", "sdd", "sde", "sdf", "sdg", "sdh", "sdi"};
    private String[] archs = {"x86_64", "arm", "i386", "m68k", "ppc", "ppc64"};
    private String htmlStart = "<html>";
    private String htmlEnd = "</html>";
    private String boldStart = "<b>";
    private String boldlEnd = "</b>";
    private JPanel finalPane;
    private JDialog dialog;

    private JTextField vmName;
    private JTextField vmRam;
    private JTextField vmLocation;
    private JPanel contentPane;
    private JPanel advancedPane;
    private JPanel disksPanel;
    private JButton createVMButton;
    private JComboBox<String> vmArch;
    private JTextField vmCpus;
    private JTextField vmArguments;
    private JTextField diskFileLocation;
    private JComboBox<String> cdromTarget;
    private JTextField cdromFileLocation;
    private JComboBox<String> diskTarget;
    private JTextField vmForwardedPorts;
    private JCheckBox createNewDisk;
    private JTextField newDiskSize;
    
    public VMConfiguratorTabbedPane(Context context){
        UIManager.put("TabbedPane.selected", Color.BLACK);
        UIManager.put("TabbedPane.unselected", Color.BLACK);

        this.contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(1,1));
        this.context = context;
        final JDialog dialog = new JDialog(context.mainJFrame, "VM Configurator");
        this.dialog = dialog;

        this.generalPane = generalPane();
        this.finalPane = finalPane();
        this.advancedPane = advancedPane();
        this.disksPanel = disksPanel();


    }

    public JPanel getPane(){


        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("1. General", generalPane);
        tabs.addTab("2. Storage & cd", disksPanel);

        tabs.addTab("3. Advanced Settings", advancedPane);
        
        
        tabs.addTab("4. Final Step", finalPane);

        this.contentPane.add(tabs);

        return contentPane;

    }

    public void show(){

        dialog.setSize(600, 600);
        dialog.setLocationRelativeTo(null);
        dialog.add(this.getPane());
        dialog.setVisible(true);



    }

    private JPanel disksPanel(){
        JPanel d = new JPanel();
        d.setLayout(new GridLayout(7,2));

        d.add(new JLabel(htmlStart+"If selected a new diskfile wil be created in the VM folder with the size set below and attached to the selected Disk Target."+htmlEnd));
        JCheckBox createNewDisk = new JCheckBox(htmlStart+boldStart+"Create new Disk"+boldlEnd+htmlEnd);
        this.createNewDisk = createNewDisk;
        d.add(createNewDisk);

        d.add(new JLabel(htmlStart+boldStart+"New disk size in GB:"+boldlEnd+htmlEnd));
        final JTextField newDiskSize = new JTextField();
        this.newDiskSize = newDiskSize;
        d.add(newDiskSize);

        final JComboBox<String> diskTarget = new JComboBox<String>(diskTargets);
        d.add(new JLabel(htmlStart+boldStart+"Disk device target:"+boldlEnd+htmlEnd));
        this.diskTarget = diskTarget;
        d.add(diskTarget);

        d.add(new JLabel(htmlStart+boldStart+"Existing disk file location:"+boldlEnd+htmlEnd));
        final JTextField diskFileLocation = new JTextField();
        this.diskFileLocation = diskFileLocation;
        d.add(diskFileLocation);

        d.add(new JLabel());
        d.add(new JLabel());

        // TODO Add option for multiple iso's to be mounted
        final JComboBox<String> cdromTarget = new JComboBox<String>(cdromTargets);
        d.add(new JLabel(htmlStart+boldStart+"CDrom device target:"+boldlEnd+htmlEnd));
        this.cdromTarget = cdromTarget;
        d.add(cdromTarget);

        d.add(new JLabel(htmlStart+boldStart+"CDrom file location:"+boldlEnd+htmlEnd));
        final JTextField cdromFileLocation = new JTextField();
        this.cdromFileLocation = cdromFileLocation;
        d.add(cdromFileLocation);

        return d;

    }

    private JPanel generalPane(){

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));

        panel.add(new JLabel(htmlStart+boldStart+"VM Name:"+boldlEnd+"<br> The VM name wil also be the name of the folder where the VM files are stored."+htmlEnd));
        final JTextField vmName = new JTextField();
        this.vmName = vmName;
        panel.add(vmName);

        panel.add(new JLabel(htmlStart+boldStart+"VM Cpu's:"+boldlEnd+htmlEnd));
        final JTextField vmCpus = new JTextField();
        this.vmCpus = vmCpus;
        panel.add(vmCpus);

        panel.add(new JLabel(htmlStart+boldStart+"Ram in GB:"+boldlEnd+htmlEnd));
        final JTextField vmRam = new JTextField();
        this.vmRam = vmRam;
        panel.add(vmRam);


        panel.add(new JLabel(htmlStart+boldStart+"VM location"+boldlEnd+": <br>if empty it wil be placed in the set default folder: "+context.getDefaultSaveLocation()+"<br>This can be set in the application settings."+htmlEnd));
        final JTextField vmLocation = new JTextField();
        this.vmLocation = vmLocation;

        panel.add(vmLocation);


        return panel;


    }

    private JPanel advancedPane(){

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2));
        
        final JComboBox<String> vmArch = new JComboBox<String>(archs);
        panel.add(new JLabel(htmlStart+boldStart+"VM arch:"+boldlEnd+htmlEnd));
        this.vmArch = vmArch;
        panel.add(vmArch);

        panel.add(new JLabel(htmlStart+boldStart+"VM arguments:"+boldlEnd+htmlEnd));
        final JTextField vmArguments = new JTextField();
        this.vmArguments = vmArguments;
        panel.add(vmArguments);


        panel.add(new JLabel(htmlStart+boldStart+"Forwarded ports"+boldlEnd+":<br> format: protocol::external Port-:interal Port<br>e.g.: tcp::2222:22,tcp::8080-8081:80"+htmlEnd));
        final JTextField vmForwardedPorts = new JTextField();
        this.vmForwardedPorts = vmForwardedPorts;
        panel.add(vmForwardedPorts);

        return panel;


    }

    private JPanel finalPane(){

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton createVM = new JButton("Create VM");
        this.createVMButton = createVM;

        createVMButton.addActionListener(this);
        panel.add(createVMButton);

        return panel;


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(this.createVMButton)){


            if(vmName.getText().equals("")){
                System.out.println("Name must be set");

            }else{
                
                VMCreationObject newVM = parseVMSettings();
                // summarydialog.setVisible(false);
                dialog.setVisible(false);
                try {
                    VMDOMCreatorProcessor.createNewVMDomain(newVM, vmLocation.getText(), context);
                    DOMController.defineDomain(vmLocation.getText(), newVM.vmName, context);
                    context.refresh();   

                } catch (Exception e1) {
                    CrashReporter.logCrash(e1);
                } 
                
    

            }

            
        }
     
    }

    private VMCreationObject parseVMSettings(){
        final VMCreationObject newVM = new VMCreationObject();
        newVM.vmName = vmName.getText();
        if(!vmCpus.getText().equals("")){
            newVM.cpus = Integer.parseInt(vmCpus.getText());
        }
        if(!vmRam.getText().equals("")){
            newVM.ramInGB = Integer.parseInt(vmRam.getText());
        }
        
        newVM.arch = vmArch.getSelectedItem().toString();
        
        newVM.suspendToDisk = "no";
        newVM.suspendToMem = "no";
        Disk disk1 = new Disk();
    
        if(createNewDisk.isSelected()){
            disk1.device = "disk";
            disk1.type = "qcow2";
            disk1.target = diskTarget.getSelectedItem().toString();
    
    
                    
            disk1.source= VMController.createVMDiskthread(newVM.vmName, context, newDiskSize.getText(), diskFileLocation.getText());
            newVM.devices.add(disk1);
    
        }else{
            disk1.device = "disk";
            disk1.source = diskFileLocation.getText();
            disk1.type = "qcow2";
            disk1.target = diskTarget.getSelectedItem().toString();
            newVM.devices.add(disk1);
        }
                
    
    
        
        if(!vmForwardedPorts.getText().equals("")){
        
            newVM.arguments = "-machine type=q35,accel=hvf -netdev user,id=n1,"+vmForwardedPorts.getText()+" -device virtio-net-pci,netdev=n1,bus=pcie.0,addr=0x19 "+vmArguments.getText();
        
        }else{
            newVM.arguments = "-machine type=q35,accel=hvf -netdev user,id=n1 -device virtio-net-pci,netdev=n1,bus=pcie.0,addr=0x19 "+vmArguments.getText();
        
        }
                
        if(cdromFileLocation.getText().equals("")){
    
        }else{
            final Disk cdrom = new Disk();
            cdrom.device = "cdrom";
            cdrom.type = "raw";
            cdrom.source = cdromFileLocation.getText();
            cdrom.target = cdromTarget.getSelectedItem().toString();
            newVM.devices.add(cdrom);
        }

        return newVM;

    }

}
