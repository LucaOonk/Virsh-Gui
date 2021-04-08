package Backend.Objects;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import Backend.Processors.VMDDetailProcessor;
import Backend.Processors.VMDOMProcessor;
import Backend.Processors.VMListProcessor;
import ui.*;

public class Context {

    private ArrayList<VM> vmList;
    private VM currentSelectedVM;
    public ScrollableVMList ScrollableVmListPane;
    public VMDetailsPanel VMDetailsPanel;
    public MainContent mainContent;
    public JFrame mainJFrame;

    public void updateVMList(ArrayList<VM> vmList){
        this.vmList= vmList;
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
            for (VM vm : vmList) {
                VMDOMProcessor.getDetails(vm);

            }
            // VMDDetailProcessor.startVMDetailthread(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mainContent.update();
    }
    
}
