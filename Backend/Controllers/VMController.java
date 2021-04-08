package Backend.Controllers;

import java.io.IOException;
import java.util.List;

import javax.swing.SwingWorker;

import Backend.Objects.Context;
import Backend.Objects.VM;

public class VMController {

    private Context context;

    public VMController(Context context){
        this.context = context;
    }

    public void connectToVM(VM vm){

        VMController.startVMConnection(vm, context);
    }

    public void startVM(VM vm){

        VMController.startVMthread(vm, context);

    }

    public void stopVM(VM vm){

        VMController.stopVMthread(vm, context);

    }

    private static void startVMthread(VM vm, Context context) 
    {
  
        SwingWorker sw1 = new SwingWorker() 
        {
  
            @Override
            protected String doInBackground() throws Exception 
            {
                // define what thread will do here

                if(!vm.isRunning()){
                    try {
                        Process process = Runtime.getRuntime().exec("virsh start "+vm.getDomain());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
        
                }else{
                    System.out.println("Machine is running");
                }
                return "done";
            }
  
            @Override
            protected void process(List chunks)
            {
                // define what the event dispatch thread 
                // will do with the intermediate results received
                // while the thread is executing

            }
  
            @Override
            protected void done() 
            {
                // this method is called when the background 
                // thread finishes execution
                vm.updateRunningState(true);

                context.refresh();

            }
        };
          
        // executes the swingworker on worker thread
        sw1.execute(); 
    }

    private static void stopVMthread(VM vm, Context context) 
    {
  
        SwingWorker sw1 = new SwingWorker() 
        {
  
            @Override
            protected String doInBackground() throws Exception 
            {
                // define what thread will do here

                if(vm.isRunning()){
                    try {
                        Process process = Runtime.getRuntime().exec("virsh shutdown "+vm.getDomain());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
        
                }else{
                    System.out.println("Machine is already stopped");
                }
                return "done";
            }
  
            @Override
            protected void process(List chunks)
            {
                // define what the event dispatch thread 
                // will do with the intermediate results received
                // while the thread is executing

            }
  
            @Override
            protected void done() 
            {
                // this method is called when the background 
                // thread finishes execution
                vm.updateRunningState(false);
                context.refresh();

            }
        };
          
        // executes the swingworker on worker thread
        sw1.execute(); 
    }

    private static void startVMConnection(VM vm, Context context) 
    {
  
        SwingWorker sw1 = new SwingWorker() 
        {
  
            @Override
            protected String doInBackground() throws Exception 
            {
                // define what thread will do here

                if(vm.isRunning()){
                    try {
                        Process process = Runtime.getRuntime().exec("virt-viewer "+vm.getDomain());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
        
                }else{
                    System.out.println("Machine is not running");
                }
                return "done";
            }
  
            @Override
            protected void process(List chunks)
            {
                // define what the event dispatch thread 
                // will do with the intermediate results received
                // while the thread is executing

            }
  
            @Override
            protected void done() 
            {
                // this method is called when the background 
                // thread finishes execution

                context.refresh();

            }
        };
          
        // executes the swingworker on worker thread
        sw1.execute(); 
    }
}
