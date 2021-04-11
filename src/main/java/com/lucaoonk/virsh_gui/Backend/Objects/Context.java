package com.lucaoonk.virsh_gui.Backend.Objects;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.lucaoonk.virsh_gui.Backend.Processors.VMDOMProcessor;
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

    public void updateVMList(ArrayList<VM> vmList){
        this.vmList= vmList;
        defaultSaveLocation= "";
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
    
}
