package com.lucaoonk.virsh_gui.Backend.Processors;

import java.io.File;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.lucaoonk.virsh_gui.Backend.Objects.Context;
import com.lucaoonk.virsh_gui.Backend.Objects.Device;
import com.lucaoonk.virsh_gui.Backend.Objects.Disk;
import com.lucaoonk.virsh_gui.Backend.Objects.VMCreationObject;


public class VMDOMCreatorProcessor {

    public static void createNewVMDomain(VMCreationObject vmCreationObject, String saveLocation, Context context) throws ParserConfigurationException, TransformerException{

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("domain");
            root.setAttribute("type","qemu");
            root.setAttribute("xmlns:qemu","http://libvirt.org/schemas/domain/qemu/1.0");

            doc.appendChild(root);
    
            Element vmName = doc.createElement("name");
            root.appendChild(vmName.appendChild(createNewElementWithValue(doc, "name", vmCreationObject.vmName)));
            
            Element vmUUID = doc.createElement("uuid");
            root.appendChild(vmUUID.appendChild(createNewElementWithValue(doc, "uuid", UUID.randomUUID().toString())));
            
            Element vmvcpu= doc.createElement("vcpu");
            vmvcpu.setAttribute("placement", "static");
            vmvcpu.appendChild(doc.createTextNode(String.valueOf(vmCreationObject.cpus)) );
            root.appendChild(vmvcpu);
    
            Element cpuNode = doc.createElement("cpu");
            root.appendChild(cpuNode.appendChild(CPUNode(doc, "custom", "Westmere")));
            
            Element memoryNode = doc.createElement("memory");
            root.appendChild(memoryNode.appendChild(memoryNode(doc, "GB", String.valueOf(vmCreationObject.ramInGB))));
    
            root.appendChild(featuresNode(doc));
    
            root.appendChild(osNode(doc, vmCreationObject.arch));
    
            Element clockNode = doc.createElement("clock");
            clockNode.setAttribute("offset", "localtime");
            root.appendChild(clockNode);
    
            Element on_poweroffNode = doc.createElement("on_poweroff");
            on_poweroffNode.setTextContent("destroy");
            root.appendChild(on_poweroffNode);
    
            Element on_rebootNode = doc.createElement("on_reboot");
            on_rebootNode.setTextContent("restart");
            root.appendChild(on_rebootNode);
    
            Element on_crashNode = doc.createElement("on_crash");
            on_crashNode.setTextContent("destroy");
            root.appendChild(on_crashNode);
    
    
            root.appendChild(pmNode(doc, vmCreationObject.suspendToMem, vmCreationObject.suspendToDisk));
            
            root.appendChild(devicesNode(doc, vmCreationObject, context));
            
            root.appendChild(arguments(doc, vmCreationObject));
    
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transf = transformerFactory.newTransformer();

            
            transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transf.setOutputProperty(OutputKeys.INDENT, "yes");
            // transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            DOMSource source = new DOMSource(doc);
    

            if(saveLocation.equals("")){

                if(context.defaultSaveLocation.equals("")){
                    File myFile = new File(System.getProperty ("user.home")+"/vms/"+vmCreationObject.vmName+"/"+vmCreationObject.vmName+".xml");
                    myFile.getParentFile().mkdirs();
    
                    StreamResult file = new StreamResult(myFile);
                    transf.transform(source, file);
    
                }else{

                    File myFile = new File(context.defaultSaveLocation+vmCreationObject.vmName+"/"+vmCreationObject.vmName+".xml");
                    myFile.getParentFile().mkdirs();
    
                    StreamResult file = new StreamResult(myFile);
                    transf.transform(source, file);
    
                }


            }else{

                File myFile = new File(context.getDefaultSaveLocation()+vmCreationObject.vmName+"/"+vmCreationObject.vmName+".xml");
                myFile.getParentFile().mkdirs();

                StreamResult file = new StreamResult(myFile);
                transf.transform(source, file);
            }

        
    }

    private static Node memoryNode(Document doc, String ramUnit, String ramAmount) {

        Element memoryNode = doc.createElement("memory");

        memoryNode.setAttribute("unit", ramUnit);

        memoryNode.setTextContent(ramAmount);


        return memoryNode;
    }
    private static Node CPUNode(Document doc, String cpuMode, String cpuModel) {

        Element cpuNode = doc.createElement("cpu");

        cpuNode.setAttribute("mode", cpuMode);
        cpuNode.appendChild(createNewElementWithValue(doc, "model", cpuModel));


        return cpuNode;
    }

    private static Node arguments(Document doc, VMCreationObject vmCreationObject) {


        String[] argumentList = vmCreationObject.arguments.split(" ");

        Element argumentNode = doc.createElement("qemu:commandline");

        for (String argumentString : argumentList) {
            Element argumentChildNode = doc.createElement("qemu:arg");
            argumentChildNode.setAttribute("value", argumentString);
            argumentNode.appendChild(argumentChildNode);
        }

        return argumentNode;
    }
    //        <graphics type='vnc' port='5901' listen='127.0.0.1'/>

    private static Node graphicsNode(Document doc, VMCreationObject vmCreationObject, Context context) {


        Element graphicsNode = doc.createElement("graphics");
        graphicsNode.setAttribute("type", "vnc");
        graphicsNode.setAttribute("listen", "127.0.0.1");
        String port="";
        if(context.getVMList().size()<99 & context.getVMList().size()>9){
            port="59"+context.getVMList().size()+1;


        }else{

            if(context.getVMList().size()<10){
                port="590"+context.getVMList().size()+1;

            }
        }

        graphicsNode.setAttribute("port", port);


        return graphicsNode;
    }

    private static Node featuresNode(Document doc) {

        Element featuresNode = doc.createElement("features");
        Element acpiNode = doc.createElement("acpi");
        Element apicNode = doc.createElement("apic");

        featuresNode.appendChild(acpiNode);
        featuresNode.appendChild(apicNode);



        return featuresNode;
    }

    private static Node createNewElementWithValue(Document doc, String name, 
            String value) {

        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }

    private static Node osNode(Document doc, String arch) {

        Element node = doc.createElement("os");
        Element osType = doc.createElement("type");
        osType.setAttribute("arch", arch);
        osType.setAttribute("machine", "q35");
        osType.setTextContent("hvm");
        Element bootMenuNode = doc.createElement("bootmenu");
        bootMenuNode.setAttribute("enable", "yes");
        osType.setAttribute("arch", arch);

        node.appendChild(osType);
        node.appendChild(bootMenuNode);

        return node;
    }

    private static Node pmNode(Document doc, String suspendToMem,  String suspendToDisk) {

        Element node = doc.createElement("pm");
        Element suspendtomemNode = doc.createElement("suspend-to-mem");
        Element suspendtodiskNode = doc.createElement("suspend-to-disk");
        suspendtomemNode.setAttribute("enabled", suspendToMem);
        suspendtodiskNode.setAttribute("enabled", suspendToDisk);
        node.appendChild(suspendtomemNode);
        node.appendChild(suspendtodiskNode);

        return node;
    }

    private static Node devicesNode(Document doc, VMCreationObject vmCreationObject, Context context) {

        Element node = doc.createElement("devices");
        Element emulatorNode = doc.createElement("emulator");
        emulatorNode.setTextContent("/usr/local/bin/qemu-system-"+vmCreationObject.arch);
        node.appendChild(emulatorNode);

        Element controllerNode = doc.createElement("controller");
        controllerNode.setAttribute("type", "usb");
        controllerNode.setAttribute("model", "ehci");
        node.appendChild(controllerNode);


        Element consoleNode = doc.createElement("console");
        consoleNode.setAttribute("type", "pty");

        Element targetNode = doc.createElement("target");
        targetNode.setAttribute("type", "serial");
        consoleNode.appendChild(targetNode);
        node.appendChild(consoleNode);

        Element inputKeyboardNode = doc.createElement("input");
        inputKeyboardNode.setAttribute("type", "keyboard");
        inputKeyboardNode.setAttribute("bus", "usb");
        node.appendChild(inputKeyboardNode);

        Element inputTabletNode = doc.createElement("input");
        inputTabletNode.setAttribute("type", "tablet");
        inputTabletNode.setAttribute("bus", "usb");
        node.appendChild(inputTabletNode);

        Element videoNode = doc.createElement("video");
        Element videoModelNode = doc.createElement("model");

        
        videoModelNode.setAttribute("type", "virtio");
        videoModelNode.setAttribute("vram", "16384");
        videoNode.appendChild(videoModelNode);

        node.appendChild(graphicsNode(doc, vmCreationObject, context));

        node.appendChild(videoNode);

        for (Device device : vmCreationObject.devices) {

            if(device.getClass().getName().equals("com.lucaoonk.virsh_gui.Backend.Objects.Disk")){
                Disk disk = (Disk) device;


            Element diskNode = doc.createElement("disk");
            diskNode.setAttribute("type", "file");
            if(disk.device.equals("disk")){
                diskNode.setAttribute("device",disk.device);

                Element diskDriver = doc.createElement("driver");
                diskDriver.setAttribute("name", "qemu");
                diskDriver.setAttribute("type", disk.type);
                diskNode.appendChild(diskDriver);

                Element sourceNode = doc.createElement("source");
                sourceNode.setAttribute("file", disk.source);
                diskNode.appendChild(sourceNode);

                Element DisktargetNode = doc.createElement("target");
                DisktargetNode.setAttribute("dev", disk.target);
                DisktargetNode.setAttribute("bus", "virtio");
                diskNode.appendChild(DisktargetNode);

            }

            if(disk.device.equals("cdrom")){
                diskNode.setAttribute("device",disk.device);

                Element sourceNode = doc.createElement("source");
                sourceNode.setAttribute("file", disk.source);
                diskNode.appendChild(sourceNode);

                Element DisktargetNode = doc.createElement("target");
                DisktargetNode.setAttribute("dev", disk.target);
                DisktargetNode.setAttribute("bus", "sata");
                diskNode.appendChild(DisktargetNode);

            }
            node.appendChild(diskNode);

            }
        }
        // Element suspendtodiskNode = doc.createElement("suspend-to-disk");
        // suspendtomemNode.setAttribute("enabled", suspendToMem);
        // suspendtodiskNode.setAttribute("enabled", suspendToDisk);
        // node.appendChild(suspendtomemNode);
        // node.appendChild(suspendtodiskNode);

        return node;
    }

}
