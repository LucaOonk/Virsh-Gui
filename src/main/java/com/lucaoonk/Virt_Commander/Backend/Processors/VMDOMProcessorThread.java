package com.lucaoonk.Virt_Commander.Backend.Processors;

import javax.swing.SwingWorker;

import com.lucaoonk.Virt_Commander.Backend.Objects.Context;
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

        }

        return null;
    }
    
}
