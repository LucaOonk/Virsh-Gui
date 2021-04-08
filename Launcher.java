import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.swing.SwingUtilities;

import ui.MainFrame;


public class Launcher {

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException, IOException {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame w1 = new MainFrame();
                System.setProperty("apple.laf.useScreenMenuBar", "true");
    

                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Virsh GUI");
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