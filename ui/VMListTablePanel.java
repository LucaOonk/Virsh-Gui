package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Backend.Objects.Context;
import Backend.Objects.VM;

public class VMListTablePanel extends JPanel{


    private Context context;

    public VMListTablePanel(Context context){

        this.context = context;

    }

    private String vmListTable(){
        StringBuilder builder= new StringBuilder();

        builder.append("<html><table><tr><td><b>ID</b></td><td><b>VM-Name</b></td><td><b>Running</b></td></tr>");

        for (VM vm : context.getVMList()) {
            builder.append("<tr><td>"+vm.getID()+"</td><td>"+vm.getDomain()+"</td><td>"+vm.isRunning()+"</td></tr>");
        }
        builder.append("</table></html>");

        return builder.toString();
    }

    public JPanel getPanel(){
        JPanel panel = new JPanel();

        panel.add(new JLabel(vmListTable()));
        return panel;

    }
    
}
