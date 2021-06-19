package com.lucaoonk.Virt_Commander.Backend.Processors.Remote;

import com.google.gson.Gson;
import com.lucaoonk.Virt_Commander.Backend.Objects.VM;
import com.lucaoonk.Virt_Commander.Backend.Processors.Remote.Objects.VMList;
import com.lucaoonk.Virt_Commander.CrashReporter.CrashReporter;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class RemoteVMDetailProcessor {

    public static VM getDetails(VM vm, String remoteAddress) {

        try {
            HttpResponse<String> response = Unirest.post(remoteAddress+"/vm/details/")
            .header("Content-Type", "application/json")
            .body("{\"name\":\""+vm.getDomain()+"\"}")
            .asString();
    
            Gson g = new Gson();
            VM remote = g.fromJson(response.getBody(), VM.class);

            if(remote.getID() != null){
                remote.updateRunningState(true);
            }

            return remote;

        } catch (Exception e) {

            throw e;
        }

        
        
    }
    
}
