package Backend.Processors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.SwingWorker;

import Backend.Objects.Context;
import Backend.Objects.VM;

public class VMDDetailProcessor {

    public static void getVMDetails(VM vm){
        

        Process process;
        try {

            process = Runtime.getRuntime().exec("virsh dominfo "+vm.getDomain());//
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";

            while ((line = reader.readLine()) != null) {
                String[] templine=line.split("\\s+");
                
                if(templine[0].equals("UUID:")){
                    vm.updateUUID(templine[1]);

                }
                if(templine[0].equals("CPU(s):")){
                    vm.updateCPUs(templine[1]);

                }
                if(templine[0].equals("Used") && templine[1].equals("memory:")){
                    vm.updateRam(templine[2]);

                }

                
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    
    }
    public static void startVMDetailthread(Context context) 
    {
  
        SwingWorker sw1 = new SwingWorker() 
        {
  
            @Override
            protected String doInBackground() throws Exception 
            {
                // define what thread will do here

                for (VM vm : context.getVMList()) {
                    getVMDetails(vm);
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
