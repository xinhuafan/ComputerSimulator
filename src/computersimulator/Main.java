package ComputerSimulator;

import CPU.*;
import GUI.*;
import Memory.*;
import javax.swing.*;

public class Main 
{  
    public static void main(String[] args)
    {
        
        SimulatedComputer simComputer = new SimulatedComputer();
        
        // Load our GUI...
        SimulatedComputerGUI simGUI = new SimulatedComputerGUI();
        simGUI.setGUIComputer(simComputer);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(simGUI);
        frame.pack();
        frame.setVisible(true);  
        
        simGUI.populateOutput();
    }
}