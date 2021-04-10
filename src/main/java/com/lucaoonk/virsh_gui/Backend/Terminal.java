package src.main.java.com.lucaoonk.virsh_gui.Backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {
    public void runCommand() throws IOException{

        Process process = Runtime.getRuntime().exec("virsh list --all --name");
        printResults(process);

    }
    public static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

}
