package com.lucaoonk.Virt_Commander.UpdateChecker;

import javax.swing.SwingWorker;
import com.lucaoonk.Virt_Commander.Backend.Objects.Context;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class UpdateChecker extends SwingWorker{


    private static final String urlToCheck = "https://api.github.com/repos/LucaOonk/Virt-Commander/releases/latest";
    private Context context;


    public UpdateChecker(Context context){

        super();
        this.context = context;

        try {
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Boolean isNewewVersionAvailable(){
        return this.doInBackground();

    }

    @Override
    protected Boolean doInBackground() {

        System.out.println("Current: "+context.getVersion());
        try {
            HttpResponse<JsonNode> response = Unirest.get(urlToCheck).asJson();
            JsonNode responseBody = response.getBody();

        if (response.isSuccess()) {
        String latestVersion = responseBody.getObject().get("tag_name").toString();

        System.out.println("Latest version: "+ latestVersion);

        if(Context.getVersion().equals(latestVersion)){
            Context.updateAvailable = false;
            return false;

        }else{
            
            Context.latestVersion = latestVersion;
            Context.updateAvailable = true;
            return true;


        }

    } else {
        System.out.println("Connection Failed");
        return null;
     }

    }catch(Exception e){

    }
        return null;
    }
}
