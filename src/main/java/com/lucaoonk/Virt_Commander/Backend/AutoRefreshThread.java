package com.lucaoonk.Virt_Commander.Backend;

import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import com.lucaoonk.Virt_Commander.Backend.Objects.Context;

public class AutoRefreshThread extends SwingWorker{

    private Context context;

    public AutoRefreshThread(Context context){
        super();
        this.context = context;
        this.execute();
    }

    @Override
    protected Object doInBackground() throws Exception {

        while(context.autoRefresh){
            TimeUnit.SECONDS.sleep(context.autoRefreshRate);
            context.refresh();
        }

        return null;
    }
    
}
