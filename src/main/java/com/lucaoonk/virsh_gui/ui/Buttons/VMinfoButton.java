package src.main.java.com.lucaoonk.virsh_gui.ui.Buttons;

import javax.swing.JButton;

import src.main.java.com.lucaoonk.virsh_gui.Backend.Objects.Context;
import src.main.java.com.lucaoonk.virsh_gui.Backend.Objects.VM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VMinfoButton extends JButton implements ActionListener{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private VM vm;
    private Context context;

    public VMinfoButton(String name, VM vm, Context context){

        super(name);
        this.addActionListener(this);
        this.vm = vm;
        this.context = context;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        context.updateCurrentSelectedVM(vm);
        context.refresh();

    }
    
}
