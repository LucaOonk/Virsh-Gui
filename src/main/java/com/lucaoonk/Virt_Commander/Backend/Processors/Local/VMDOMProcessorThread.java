package com.lucaoonk.Virt_Commander.Backend.Processors.Local;

import javax.swing.SwingWorker;

import com.lucaoonk.Virt_Commander.Backend.Objects.Context;
import com.lucaoonk.Virt_Commander.Backend.Objects.Device;
import com.lucaoonk.Virt_Commander.Backend.Objects.Disk;
import com.lucaoonk.Virt_Commander.Backend.Objects.VM;

public class VMDOMProcessorThread extends SwingWorker{

    private Context context;

    public VMDOMProcessorThread(Context context){
        super();
        this.context = context;
        this.execute();
    }

    @Override
    protected Object doInBackground() throws Exception {
        for (VM vm : context.getVMList()) {
            VMDOMProcessor.getDetails(vm);

            for (Device device : vm.getDevices()) {

                if(device.getClass().getName().equals("com.lucaoonk.Virt_Commander.Backend.Objects.Disk")){

                    Disk disk = (Disk) device;
                    disk.getDiskDetails();
                }
                        
            }     
            

        }

        return null;
    }
    
}
