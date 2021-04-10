package src.main.java.com.lucaoonk.virsh_gui.Backend.Objects;

import java.util.ArrayList;

public class VMCreationObject {

    public String vmName;
    public int cpus;
    public int ramInGB;
    public String suspendToMem;
    public String suspendToDisk;
    public ArrayList<Device> devices;
    public String arch;
    public String arguments;


    public VMCreationObject(){
        devices = new ArrayList<Device>();
        suspendToMem = "no";
        suspendToDisk = "no";

    }
}
