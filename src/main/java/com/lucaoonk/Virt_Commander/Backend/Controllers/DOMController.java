package com.lucaoonk.Virt_Commander.Backend.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.lucaoonk.Virt_Commander.Backend.Objects.Context;

public class DOMController {

    public static void defineDomain(String domainLocation, String domainName, Context context) throws Exception{

        try {
            if(domainLocation.equals("")){

                if(context.defaultSaveLocation.equals("")){
                    Runtime.getRuntime().exec("/usr/local/bin/virsh define "+context.getDefaultSaveLocation()+domainName+"/"+domainName+".xml");

    
                }else{

                    Runtime.getRuntime().exec("/usr/local/bin/virsh define "+context.defaultSaveLocation+domainName+"/"+domainName+".xml");    
                }


            }else{

                Runtime.getRuntime().exec("/usr/local/bin/virsh define "+System.getProperty ("user.home")+"/vms/"+domainName+"/"+domainName+".xml");
 
            }

        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        }


    }

    public static void undefineDomain( String domainName, Context context){

        try {
            Runtime.getRuntime().exec("/usr/local/bin/virsh undefine "+domainName);
            context.updateCurrentSelectedVM(context.getVMList().get(0));

        } catch (IOException e) {

            e.printStackTrace();
        }
        context.refresh();



    }

    public static void createDomain(String domainLocation, String domainName, Context context) throws IOException{

        try {
            Process process = Runtime.getRuntime().exec("virsh create "+domainLocation+domainName+".xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";

            while ((line = reader.readLine()) != null) {

                System.out.println(line);
                
            }
        } catch (IOException e) {

            throw e;
        }


    }
}
