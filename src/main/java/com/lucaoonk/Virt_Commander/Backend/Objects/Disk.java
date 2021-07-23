package com.lucaoonk.Virt_Commander.Backend.Objects;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Disk extends Device{


    public String source;
    public String driver;
    public String type;
    public String Available;
    public String Used;


    public void getDiskDetails(){

        Process process;
        try {

            process = Runtime.getRuntime().exec("/usr/local/bin/qemu-img info "+this.source);//
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";

            while ((line = reader.readLine()) != null) {

                String[] separated = line.split(" ");

                if(line.contains("virtual size:")){

                    this.Used = separated[2] +" "+  separated[3];

                }

                if(line.contains("disk size:")){
                    
                    this.Available = separated[2]+" "+  separated[3];

                }
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
