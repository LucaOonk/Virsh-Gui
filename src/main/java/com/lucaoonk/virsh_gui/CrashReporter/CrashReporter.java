package com.lucaoonk.virsh_gui.CrashReporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;

import com.lucaoonk.virsh_gui.ui.MainFrame;

import org.json.simple.JSONObject;

public class CrashReporter{
    

    public static void main(String[] args) {
        CrashReporter.logCrash(new Exception());
    }

    public static void logCrash(Exception exception) {

        LocalDateTime crashTime= getDateTime();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd:MM:yyyy-HH:mm:ss");  
        String file=System.getProperty("user.home") + "/Library/Application Support/Virsh_GUI/crashlogs/crash-"+dtf.format(crashTime)+".log";
        
        File crashFile = new File(file);
        crashFile.getParentFile().mkdirs();

        JSONObject crashInfo = new JSONObject();
        crashInfo.put("Date-Time Of Crash", dtf.format(crashTime));

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String stackTrace = sw.toString(); // stack    

        crashInfo.put("Stacktrace",stackTrace);

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file, false);
            fileWriter.write(crashInfo.toJSONString());
            fileWriter.close();   

            JDialog crashreportDialog = new JDialog();
            JPanel panel = new JPanel();
            
            crashreportDialog.setTitle("Oops something went wrong");
            panel.setBorder(new EmptyBorder(10,10,10,10));
            JLabel crashText = new JLabel("<html>A crashlog has been created: <br>"+file+"<br> Please send this to us, or create an issue on Github with this crashlog.<br></html>");
            panel.add(crashText);
            JButton goToRepo = new JButton("Open new issue on Github");
            goToRepo.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop desktop = java.awt.Desktop.getDesktop();
                        URI oURL = new URI("https://github.com/LucaOonk/Virsh-Gui/issues");
                        desktop.browse(oURL);
                      } catch (Exception f) {
                        f.printStackTrace();
                      }
                        
                }
    

            });
            panel.add(goToRepo);
            crashreportDialog.add(panel);
            crashreportDialog.setSize(650, 150);
            crashreportDialog.setVisible(true);
            crashreportDialog.addWindowListener(new WindowListener(){

                @Override
                public void windowOpened(WindowEvent e) {}
            
                @Override
                public void windowClosing(WindowEvent e) {
                    MainFrame.exitProgram();
                }
            
                @Override
                public void windowClosed(WindowEvent e) {}
            
                @Override
                public void windowIconified(WindowEvent e) {}
            
                @Override
                public void windowDeiconified(WindowEvent e) {}
            
                @Override
                public void windowActivated(WindowEvent e) {}
            
                @Override
                public void windowDeactivated(WindowEvent e) {}

            });
            crashreportDialog.setLocationRelativeTo(null);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  

    }

    private static LocalDateTime getDateTime(){
        LocalDateTime now = LocalDateTime.now();  
        return now;
    }


    
}
