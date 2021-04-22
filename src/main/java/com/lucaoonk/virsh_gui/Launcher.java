package com.lucaoonk.virsh_gui;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.swing.SwingUtilities;

import com.lucaoonk.virsh_gui.CrashReporter.CrashReporter;
import com.lucaoonk.virsh_gui.ui.MainFrame;


public class Launcher {

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException, IOException {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame w1 = new MainFrame();

                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Virsh GUI");
                try {
                    w1.init();
                    w1.showMainFrame();

                } catch (Exception e) {

                    CrashReporter.logCrash(e);
                    
                }
            }
        });

    }

}