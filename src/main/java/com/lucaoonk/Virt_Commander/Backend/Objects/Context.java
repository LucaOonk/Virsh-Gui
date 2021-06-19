package com.lucaoonk.Virt_Commander.Backend.Objects;

import java.io.IOException;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import com.lucaoonk.Virt_Commander.Backend.Processors.Local.VMDOMProcessorThread;
import com.lucaoonk.Virt_Commander.Backend.Processors.Local.VMListProcessor;
import com.lucaoonk.Virt_Commander.Backend.Processors.Remote.RemoteVMListProcessor;
import com.lucaoonk.Virt_Commander.CrashReporter.CrashReporter;
import com.lucaoonk.Virt_Commander.ui.MainContent;
import com.lucaoonk.Virt_Commander.ui.ScrollableVMList;
import com.lucaoonk.Virt_Commander.ui.VMDetailsPanel;

public class Context {

    public static String latestVersion;
    public static boolean updateAvailable;
    private ArrayList<VM> vmList;
    private VM currentSelectedVM;
    public ScrollableVMList ScrollableVmListPane;
    public VMDetailsPanel VMDetailsPanel;
    public MainContent mainContent;
    public JFrame mainJFrame;
    public String defaultSaveLocation;
    private static final String versionString = "0.5";
    public Boolean checkForUpdates;
    private String applicationDefaultSaveLocation;
    public Integer windowHeight;
    public Integer windowWidth;
    public boolean autoSizeWindow;
    public boolean autoRefresh;
    public long autoRefreshRate;
    public boolean local;
    public String remoteAddress;
    public boolean remoteOrLocal;
    private String currentSelectedUUID;


    public Context(){
        initDefaults();
        this.autoRefresh = true;
        this.autoRefreshRate =10;
    }

    private void initDefaults(){
        this.checkForUpdates = true;
        this.remoteOrLocal = false;
        this.local = true;
        this.remoteAddress = "";

        this.defaultSaveLocation=System.getProperty("user.home")+"/vms/";
        this.applicationDefaultSaveLocation=System.getProperty("user.home")+"/vms/";

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        windowHeight = (screenSize.height / 2);
        windowWidth = (screenSize.width / 2);
    }

    public void updateVMList(ArrayList<VM> vmList){
        this.vmList= vmList;
        // defaultSaveLocation= "";
        //this.currentSelectedVM = vmList.get(0);
    }

    public ArrayList<VM> getVMList(){
        return this.vmList;
    }

    public VM getCurrentSelectedVM(){

        for (VM vm : vmList) {
            if(currentSelectedUUID == null){
                this.currentSelectedVM= vmList.get(0);
            }else{
                if(this.currentSelectedUUID.equals(vm.getUUID())){
                    this.currentSelectedVM = vm;
                    return vm;
                }   
            }

        }

        return currentSelectedVM;
    }

    public void updateCurrentSelectedVM(VM vm){
        this.currentSelectedVM = vm;
        this.currentSelectedUUID = vm.getUUID();
    }

    public void refresh() {

        if(this.local){

            VMListProcessor processor = new VMListProcessor(this);
            
            try {
                
                this.vmList = processor.getVMdomainList();

                VMDOMProcessorThread domThread = new VMDOMProcessorThread(this);
                
            } catch (IOException e) {

                CrashReporter.logCrash(e);
            }
        }else{

            RemoteVMListProcessor t = new RemoteVMListProcessor(this);
            
            try {

                t.runCommand();

            } catch (IOException e) {

                CrashReporter.logCrash(e);
            }
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
