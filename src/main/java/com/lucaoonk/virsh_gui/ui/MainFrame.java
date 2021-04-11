package com.lucaoonk.virsh_gui.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.lucaoonk.virsh_gui.Backend.Objects.Context;
import com.lucaoonk.virsh_gui.Backend.Objects.VM;
import com.lucaoonk.virsh_gui.Backend.Processors.VMDOMProcessor;
import com.lucaoonk.virsh_gui.Backend.Processors.VMListProcessor;


public class MainFrame extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public JFrame mainFrame;

    private Context context;

    public MainFrame() {


    }

    public void init() {
        this.context = new Context();
        this.mainFrame = new JFrame();
        this.context.mainJFrame = mainFrame;

        VMListProcessor t = new VMListProcessor(context);

        try {
            t.runCommand();
            // VMDDetailProcessor.startVMDetailthread(context);

            for (VM vm : context.getVMList()) {
                VMDOMProcessor.getDetails(vm);
            }
            // VMDOMProcessor.startVMDOMDetailthread(context);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // java.net.URL url = ClassLoader.getSystemResource("resources/images/cloud.png");
        // Toolkit kit = Toolkit.getDefaultToolkit();
        // Image img = kit.createImage(url);

        // this.mainFrame.setIconImage(img);

        // packContent(mainFrame);

    }

    // public void packContent(JFrame mainFrame) {

    //     mainFrame.getContentPane().add(container);
        

    // }

    public void showMainFrame() throws InterruptedException {
        this.mainFrame = new JFrame();


        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setTitle("Virsh GUI");
        mainFrame.setSize(new Dimension(1100, 400));

        JMenuBar menuBar = new JMenuBar();

        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem settingsMenuItem = new JMenuItem("Application Settings");

        settingsMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationSettings settings = new ApplicationSettings();
                settings.show(context);

            }

        });

        settingsMenu.add(settingsMenuItem);
        menuBar.add(settingsMenu);
        mainFrame.setJMenuBar(menuBar);

        // this.portalpage = new MainPortalPage(this.user, this);
        // mainFrame.setJMenuBar(menu());
        mainFrame.add(new MainContent(context).getContent());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        mainFrame.setSize(width / 2, height / 2);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

}