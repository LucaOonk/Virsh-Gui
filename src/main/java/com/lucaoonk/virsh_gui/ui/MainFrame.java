package com.lucaoonk.virsh_gui.ui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.desktop.SystemEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lucaoonk.virsh_gui.Backend.Objects.*;
import com.lucaoonk.virsh_gui.Backend.Processors.VMDOMProcessor;
import com.lucaoonk.virsh_gui.Backend.Processors.VMListProcessor;
import com.lucaoonk.virsh_gui.UpdateChecker.UpdateChecker;


public class MainFrame extends JFrame implements ActionListener, SystemEventListener{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public JFrame mainFrame;

    private Context context;

    public MainFrame() {


    }

    public static void exitProgram(){
        System.exit(0);
    }

    public void init() throws Exception {
        this.context = new Context();
        this.mainFrame = new JFrame();
        this.context.mainJFrame = mainFrame;
        // create an instance of the mac osx Application class

        ApplicationSettings.readSettingsFile(context);

        VMListProcessor t = new VMListProcessor(context);

        try {

            t.runCommand();
            // VMDDetailProcessor.startVMDetailthread(context);

            for (VM vm : context.getVMList()) {
                VMDOMProcessor.getDetails(vm);
            }
            // VMDOMProcessor.startVMDOMDetailthread(context);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            
            JDialog dialog = new JDialog();
            JPanel panel = new JPanel();
            dialog.setTitle("An error occured!");
            JLabel label = new JLabel("<html><b>Make sure the dependencies are installed!</b></html>");
            panel.setBorder(new EmptyBorder(10,10,10,10));
            panel.add(label);
            dialog.add(panel);
            dialog.setSize(300, 150);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            dialog.setAlwaysOnTop(true);
            dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            throw e;
        }

    }


    public void showMainFrame() throws InterruptedException {
        this.mainFrame = new JFrame();

        mainFrame.getContentPane().setBackground(java.awt.Color.black);

        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setTitle("Virsh GUI");
        mainFrame.setSize(new Dimension(1100, 400));

        JMenuBar menuBar = new JMenuBar();

        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem settingsMenuItem = new JMenuItem("Application Settings");

        settingsMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationSettingsView settings = new ApplicationSettingsView();
                settings.show(context);

            }

        });
        settingsMenu.add(settingsMenuItem);
        menuBar.add(settingsMenu);

        if(context.checkForUpdates){
            
            UpdateChecker checker = new UpdateChecker(context);
            if(checker.isNewewVersionAvailable()){
                JMenu updateMenu = new JMenu("New Version Available");
                JMenuItem updateMenuItem = new JMenuItem("Get Update");
                updateMenuItem.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop desktop = java.awt.Desktop.getDesktop();
                        URI oURL = new URI("https://github.com/LucaOonk/Virsh-Gui/releases/latest");
                        desktop.browse(oURL);
                      } catch (Exception f) {
                        f.printStackTrace();
                      }
    
                }
    

            });

            updateMenu.add(updateMenuItem);
            menuBar.add(updateMenu);

            }
        }


        mainFrame.setJMenuBar(menuBar);

        // this.portalpage = new MainPortalPage(this.user, this);
        // mainFrame.setJMenuBar(menu());
        mainFrame.add(new MainContent(context).getContent());

        if(context.autoSizeWindow){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Integer windowHeight = (screenSize.height / 2);
            Integer windowWidth = (screenSize.width / 2);
            mainFrame.setSize(windowWidth, windowHeight);

        }else{
            mainFrame.setSize(context.windowWidth, context.windowHeight);

        }
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

}