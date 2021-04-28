package com.lucaoonk.virsh_gui.Backend.Objects;

import java.io.IOException;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import com.lucaoonk.virsh_gui.Backend.Processors.VMDOMProcessorThread;
import com.lucaoonk.virsh_gui.Backend.Processors.VMListProcessor;
import com.lucaoonk.virsh_gui.ui.MainContent;
import com.lucaoonk.virsh_gui.ui.ScrollableVMList;
import com.lucaoonk.virsh_gui.ui.VMDetailsPanel;

public class Context {

    private ArrayList<VM> vmList;
    private VM currentSelectedVM;
    public ScrollableVMList ScrollableVmListPane;
    public VMDetailsPanel VMDetailsPanel;
    public MainContent mainContent;
    public JFrame mainJFrame;
    public String defaultSaveLocation;
    private static final String versionString = "0.4.1";
    public Boolean checkForUpdates;
    private String applicationDefaultSaveLocation;
    public Integer windowHeight;
    public Integer windowWidth;
    public boolean autoSizeWindow;


    public Context(){
        initDefaults();
    }

    private void initDefaults(){
        this.checkForUpdates = true;
        this.defaultSaveLocation=System.getProperty("user.home")+"/vms/";
        this.applicationDefaultSaveLocation=System.getProperty("user.home")+"/vms/";

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        windowHeight = (screenSize.height / 2);
        windowWidth = (screenSize.width / 2);
    }

    public void updateVMList(ArrayList<VM> vmList){
        this.vmList= vmList;
        // defaultSaveLocation= "";
        this.currentSelectedVM = vmList.get(0);
    }

    public ArrayList<VM> getVMList(){
        return this.vmList;
    }

    public VM getCurrentSelectedVM(){

        return currentSelectedVM;
    }

    public void updateCurrentSelectedVM(VM vm){
        this.currentSelectedVM = vm;
    }

    public void refresh() {

        VMListProcessor processor = new VMListProcessor(this);
        try {
            this.vmList = processor.getVMdomainList();
            // for (VM vm : vmList) {
            //     VMDOMProcessor.getDetails(vm);

            // }
            VMDOMProcessorThread domThread = new VMDOMProcessorThread(this);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mainContent.update();
    }
    

    public static String getVersion(){
        return versionString;
    }

    public String getDefaultSaveLocation() {

        if(defaultSaveLocation.equals("")){

            return applicationDefaultSaveLocation;

        }else{

            return defaultSaveLocation;
        }

    }
}
