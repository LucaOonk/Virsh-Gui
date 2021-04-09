package Backend.Objects;

import java.util.ArrayList;

public class VM {
    
    private String domain;
    private Integer id;
    private Boolean isRunning;
    private String UUID;
    private String cpus;
    private String ram;
    private ArrayList<Device> devices;
    private ArrayList<String> portsForwarded;
    public String vncPort;
    public String vncIP;

    public VM(String domain){

        this.domain = domain;
        this.devices = new ArrayList<Device>();
        this.portsForwarded = new ArrayList<String>();

    }

    public VM(String domain, Integer ID){

        this.domain = domain;
        this.id = ID;
        this.isRunning = true;    
        this.devices = new ArrayList<Device>();
        this.portsForwarded = new ArrayList<String>();

    }

    public void setID(Integer ID){
        this.id = ID;
        this.isRunning = true;

    }

    public Boolean isRunning(){
        if(this.isRunning == null){
            return false;
        }else{
            return this.isRunning;

        }
    }

    public void updateRunningState(Boolean state){
        this.isRunning = state;
    }


    public String getDomain(){
        return this.domain;
    }

    public Integer getID(){
        return this.id;
    }

    public void updateUUID(String UUID){
        this.UUID = UUID;
    }

    public String getUUID(){
        return this.UUID;
    }

    public void updateCPUs(String cpus){
        this.cpus = cpus;
    }

    public String getcpus(){
        return this.cpus;
    }

    public void updateRam(String ram) {
        this.ram = ram;
    }
    public String getRam(){
        return this.ram;
    }

    public void addDevice(Device device){
        this.devices.add(device);
    }

    public ArrayList<Device> getDevices(){
        return this.devices;
    }

    public void addForwardedPort(String port){
        this.portsForwarded.add(port);
    }

    public ArrayList<String> getForwardedPorts(){
        return this.portsForwarded;
    }


}
