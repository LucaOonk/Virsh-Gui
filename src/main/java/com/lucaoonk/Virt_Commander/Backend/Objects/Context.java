package com.lucaoonk.Virt_Commander.Backend.Objects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lucaoonk.Virt_Commander.Backend.Processors.Local.VMDOMProcessorThread;
import com.lucaoonk.Virt_Commander.Backend.Processors.Local.VMListProcessor;
import com.lucaoonk.Virt_Commander.Backend.Processors.Remote.RemoteVMListProcessor;
import com.lucaoonk.Virt_Commander.CrashReporter.CrashReporter;
import com.lucaoonk.Virt_Commander.ui.MainContent;
import com.lucaoonk.Virt_Commander.ui.ScrollableVMList;
import com.lucaoonk.Virt_Commander.ui.VMDetailsPanel;

import org.json.simple.JSONObject;

public class Context {

    public static String latestVersion;
    public static boolean updateAvailable;
    private ArrayList<VM> vmList;
    private VM currentSelectedVM;
    public ScrollableVMList ScrollableVmListPane;
    public VMDetailsPanel VMDetailsPanel;
    public MainContent mainContent;
    public JFrame mainJFrame;
    public String defaultSaveLocation;
    private static final String versionString = "0.5.4";
    public Boolean checkForUpdates;
    private String applicationDefaultSaveLocation;
    public Integer windowHeight;
    public Integer windowWidth;
    public boolean autoSizeWindow;
    public boolean autoRefresh;
    public long autoRefreshRate;
    public boolean local;
    public String remoteAddress;
    public String httpAuth;
    private String currentSelectedUUID;

    public ArrayList<RemoteConnection> remoteConnections;
    public String loadingStatus;
    public boolean loadingIsDone;
    private final static String connectionsFileLocation = System.getProperty("user.home") + "/Library/Application Support/Virt_Commander/connections.json";


    public Context(){
        initDefaults();
        this.autoRefresh = true;
        this.autoRefreshRate =10;

        readConnectionsFile();    
}

    private void initDefaults(){
        this.checkForUpdates = true;
        this.local = true;
        this.httpAuth = "";
        this.remoteAddress = "";
        this.remoteConnections = new ArrayList<RemoteConnection>();

        this.defaultSaveLocation=System.getProperty("user.home")+"/vms/";
        this.applicationDefaultSaveLocation=System.getProperty("user.home")+"/vms/";

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        windowHeight = (screenSize.height / 2);
        windowWidth = (screenSize.width / 2);
    }

    public void updateVMList(ArrayList<VM> vmList){
        this.vmList= vmList;
        // defaultSaveLocation= "";
        //this.currentSelectedVM = vmList.get(0);
    }

    public ArrayList<VM> getVMList(){
        return this.vmList;
    }

    public RemoteConnectionComboItem[] getRemoteConnectionComboItems(){
        readConnectionsFile();    

        if(this.remoteConnections == null || this.remoteConnections.size() == 0){

            RemoteConnection r = new RemoteConnection();
            r.name = "No connections";
            RemoteConnectionComboItem[] array = new RemoteConnectionComboItem[1];
            array[0] = new RemoteConnectionComboItem(r);

            return array;

        }else{
            RemoteConnectionComboItem[] array = new RemoteConnectionComboItem[this.remoteConnections.size()];
            int i = 0;
            for (RemoteConnection connection : remoteConnections) {
                array[i] = new RemoteConnectionComboItem(connection);
                i++;
            }

            return array;
        }
    }

    public VM getCurrentSelectedVM(){

        try {
            for (VM vm : vmList) {
                if(currentSelectedUUID == null){
                    this.currentSelectedVM= vmList.get(0);
                }else{
                    if(this.currentSelectedUUID.equals(vm.getUUID())){
                        this.currentSelectedVM = vm;
                        return vm;
                    }   
                }
    
            }    
        } catch (Exception e) {
            this.currentSelectedVM= vmList.get(0);
        }

        return currentSelectedVM;
    }

    public void updateCurrentSelectedVM(VM vm){
        this.currentSelectedVM = vm;
        this.currentSelectedUUID = vm.getUUID();
    }

    public void refresh() {

        if(this.local){

            VMListProcessor processor = new VMListProcessor(this);
            
            try {
                this.vmList = processor.getVMdomainList();

                VMDOMProcessorThread domThread = new VMDOMProcessorThread(this);
                
            } catch (IOException e) {

                CrashReporter.logCrash(e);
            }
        }else{

            RemoteVMListProcessor t = new RemoteVMListProcessor(this);
            
            try {

                t.runCommand();

            } catch (IOException e) {

                CrashReporter.logCrash(e);
            }
        }

        mainContent.update();
        System.gc();
    }
    

    public static String getVersion(){
        return versionString;
    }

    public String getDefaultSaveLocation() {

        if(defaultSaveLocation.equals("")){

            return applicationDefaultSaveLocation;

        }else{

            return defaultSaveLocation;
        }

    }


    public void writeConnections(){

        JSONObject jsonObject = new JSONObject();
        Gson g = new Gson();
        

        jsonObject.put("connections", g.toJson(remoteConnections));


        File location = new File(connectionsFileLocation);
        location.getParentFile().mkdirs();

        FileWriter file;
        try {
        if(Context.connectionsFileExists()){


                file = new FileWriter(connectionsFileLocation, false);

            file.write(jsonObject.toJSONString());
            file.close();       
        }else{

            file = new FileWriter(connectionsFileLocation);


            file.write(jsonObject.toJSONString());
            file.close();   
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    public static Boolean connectionsFileExists(){

        File f = new File(connectionsFileLocation);
        if(f.exists() && !f.isDirectory()) { 
            return true;
        }else{
            return false;
        }


    }

    public void readConnectionsFile(){

        if(Context.connectionsFileExists()){

            try {
                // create Gson instance
                Gson gson = new Gson();
            
                // create a reader
                Reader reader = Files.newBufferedReader(Paths.get(connectionsFileLocation));
            
                // convert JSON file to map
                Map<?, ?> map = gson.fromJson(reader, Map.class);
            
                // print map entries
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    if(entry.getKey().equals("connections")){
                        Gson g = new Gson();
                        remoteConnections = g.fromJson((String) entry.getValue(),new TypeToken<ArrayList<RemoteConnection>>() {
                        }.getType());
                    }
                }
            
                // close reader
                reader.close();
            
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }else{
            System.out.println("Connections-File does not exists. Using defaults");

        }


    }
    
}
