package com.lucaoonk.Virt_Commander;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.swing.SwingUtilities;

import com.lucaoonk.Virt_Commander.CrashReporter.CrashReporter;
import com.lucaoonk.Virt_Commander.ui.MainFrame;

public class Launcher {

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException, IOException {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame w1 = new MainFrame();

                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Virt Commander");
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