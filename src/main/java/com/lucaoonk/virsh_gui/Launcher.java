package com.lucaoonk.virsh_gui;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.lucaoonk.virsh_gui.ui.MainFrame;


public class Launcher {

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException, IOException {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame w1 = new MainFrame();

                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Virsh GUI");
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                        | UnsupportedLookAndFeelException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                w1.init();
                try {
                    w1.showMainFrame();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

}