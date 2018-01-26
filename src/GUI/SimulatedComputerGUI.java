/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import CPU.OpCode.WordInstructions;
import CPU.Registers.Register;
import ComputerSimulator.*;
import Memory.*;
import TestPrograms.*;
import java.util.Arrays;

/**
 *
 * @author jeremy.case
 */
public class SimulatedComputerGUI extends javax.swing.JPanel {

    SimulatedComputer simComputer;
    TestProgram1 program1;
    TestProgram2 program2;
    private int currentMemoryLoc = 0;
    private WordInstructions manualWord = new WordInstructions();
    
    /*************************************************************************** 
    *Integer representing decimal value of a word that will input the 
    *value contained in the keyboard device, and input into GPR0
    ***************************************************************************/
    private final int KEYBOARD_TO_GPR0_INT = 50176;
    private Word KEYBOARD_TO_GPR0_WORD = new Word(KEYBOARD_TO_GPR0_INT);
    private WordInstructions KEYBOARD_TO_GPR0_INSTRUCTION = convertWordToInstruction(KEYBOARD_TO_GPR0_WORD);
    
    /*set WordInstructions as zero - halt*/
    private final int HALT_INT = 0;
    private Word HALT_WORD = new Word(HALT_INT);
    private WordInstructions HALT_INSTRUCTION = convertWordToInstruction(HALT_WORD);
    
    /*************************************************************************** 
    * Integer representing memory address to output keyboard values to for test
    * program 1.
    ***************************************************************************/
    private final int KEYBOARD_OUTPOUT_INITIAL_ADDRESS = 200;
    
    /*************************************************************************** 
    * Integer representing where to store user input to search for for program2  
    ***************************************************************************/
    private final int TEST_PROGRAM_TWO_USER_INPUT_INITIAL_ADDRESS = 1900;
    
    // Word Instruction lengths for individual components
    private final int WORD_BITS = 16;
    private final int OPCODE_LENGTH = 6;
    private final int IX_REG_LENGTH = 2;
    private final int GPR_LENGTH = 2;
    private final int IA_BIT_LENGTH = 1;
    private final int ADDRESS_LENGTH = 5;
    
    // test program booleans
    private boolean testProgram1Running = false;
    private boolean testProgram2Running = false;
    private boolean acceptTestProgram2Input = false;
    
    public SimulatedComputerGUI() 
    {
        initComponents();
    }
    
    public void setGUIComputer(SimulatedComputer toSet) {simComputer = toSet;}
    
    public void populateOutput()
    {
        populateGPR0Output();
        populateGPR1Output();
        populateGPR2Output();
        populateGPR3Output();
        populateIXReg1Output();
        populateIXReg2Output();
        populateIXReg3Output();
        populatePCOutput();
        populateCCOutput();
        populateIROutput();
        populateMSROutput();
        populateMFROutput();
        populateMAROutput();
        populateMBROutput();
        populateOpCodeOutput();
        populateAddressCodeOutput();
        populateIABitCodeOutput();
        populateIXRegCodeOutput();
        populateGPRCodeOutput();  
        
        // Now populate initial memory output...
        boolean[] tempArray = simComputer.getCPU().getCacheMemory().getWordAtAddress(currentMemoryLoc).getBits();

        int[] memoryBinary = new int[tempArray.length];

        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (tempArray[itr] != false)
                memoryBinary[itr] = 1;
            else
                memoryBinary[itr] = 0;
        }

        CurrentMemoryLocBinary.setText(Arrays.toString(memoryBinary));        
    }
    
    public void updateOutput()
    {
        Register[] tempArray = simComputer.getCPU().getRegisters();
        
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (tempArray[itr].getUpdateFlag())
            {
                determineRegToUpdate(itr);
            }
        }
        
        // Now populate initial memory output...
        boolean[] tempArray2 = simComputer.getCPU().getCacheMemory().getWordAtAddress(currentMemoryLoc).getBits();

        int[] memoryBinary = new int[tempArray2.length];

        for (int itr = 0; itr < tempArray2.length; itr++)
        {
            if (tempArray2[itr] != false)
                memoryBinary[itr] = 1;
            else
                memoryBinary[itr] = 0;
        }

        CurrentMemoryLocBinary.setText(Arrays.toString(memoryBinary));          
    }
    
    public void populateGPR0Output()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedGPR0.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedGPR0.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        GPR0Binary.setText(Arrays.toString(tempArray));
        GPR0Decimal.setText(Integer.toString(simComputer.getCPU().simulatedGPR0.getDecimalValue()));        
    }
    
    public void populateGPR1Output()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedGPR1.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedGPR1.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        GPR1Binary.setText(Arrays.toString(tempArray));
        GPR1Decimal.setText(Integer.toString(simComputer.getCPU().simulatedGPR1.getDecimalValue()));         
    }   
    
    public void populateGPR2Output()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedGPR2.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedGPR2.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        GPR2Binary.setText(Arrays.toString(tempArray));
        GPR2Decimal.setText(Integer.toString(simComputer.getCPU().simulatedGPR2.getDecimalValue()));       
    }  
    
    public void populateGPR3Output()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedGPR3.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedGPR3.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        GPR3Binary.setText(Arrays.toString(tempArray));
        GPR3Decimal.setText(Integer.toString(simComputer.getCPU().simulatedGPR3.getDecimalValue()));        
    }
    
    public void populateIXReg1Output()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedIXReg1.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedIXReg1.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        IXReg1Binary.setText(Arrays.toString(tempArray));
        IXReg1Decimal.setText(Integer.toString(simComputer.getCPU().simulatedIXReg1.getDecimalValue()));
    }
    
    public void populateIXReg2Output()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedIXReg1.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedIXReg2.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        IXReg2Binary.setText(Arrays.toString(tempArray));
        IXReg2Decimal.setText(Integer.toString(simComputer.getCPU().simulatedIXReg2.getDecimalValue()));
    }
    
    public void populateIXReg3Output()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedIXReg1.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedIXReg3.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        IXReg3Binary.setText(Arrays.toString(tempArray));
        IXReg3Decimal.setText(Integer.toString(simComputer.getCPU().simulatedIXReg3.getDecimalValue()));
    }
    
    public void populatePCOutput()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedPC.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedPC.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        PCBinary.setText(Arrays.toString(tempArray));
        PCDecimalInput.setText(Integer.toString(simComputer.getCPU().simulatedPC.getDecimalValue()));
    }
    
    public void populateCCOutput()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedCC.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedCC.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        CCBinary.setText(Arrays.toString(tempArray));        
    }    
    
    public void populateIROutput()
    {
        int[] tempArray = new int[16];
        int itr = 0;
        
        for (int tempItr = 0; tempItr < 6; tempItr++, itr++)
            if (simComputer.getCPU().simulatedIR.getBits()[tempItr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        
        for (int tempItr = 0; tempItr < 2; tempItr++, itr++)
            if (simComputer.getCPU().simulatedIR.getBits()[tempItr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;        
        
        for (int tempItr = 0; tempItr < 2; tempItr++, itr++)
            if (simComputer.getCPU().simulatedIR.getBits()[tempItr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        
        for (int tempItr = 0; tempItr < 1; tempItr++, itr++)
            if (simComputer.getCPU().simulatedIR.getBits()[tempItr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        
        for (int tempItr = 0; tempItr < 5; tempItr++, itr++)
            if (simComputer.getCPU().simulatedIR.getBits()[tempItr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        
        IRBinary.setText(Arrays.toString(tempArray));        
    }
    
    public void populateMSROutput()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedMSR.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedMSR.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        MSRBinary.setText(Arrays.toString(tempArray));        
    }  
    
    public void populateMFROutput()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedMFR.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedMFR.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        MFRBinary.setText(Arrays.toString(tempArray));        
    }    
    
    public void populateMAROutput()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedMAR.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedMAR.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        MARBinary.setText(Arrays.toString(tempArray));        
    }
    
    public void populateMBROutput()
    {
        int[] tempArray = new int[simComputer.getCPU().simulatedMBR.getBits().length];
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (simComputer.getCPU().simulatedMBR.getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        MBRBinary.setText(Arrays.toString(tempArray));        
    }    
    
    public void populateOpCodeOutput()
    {
        int[] tempArray = new int[manualWord.getOpCode().getBits().length];
        
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (manualWord.getOpCode().getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        OpCodeOutput.setText(Arrays.toString(tempArray));
    }
    
    public void populateIXRegCodeOutput()
    {
        int[] tempArray = new int[manualWord.getIxRegCode().getBits().length];
        
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (manualWord.getIxRegCode().getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        IXRegOutput.setText(Arrays.toString(tempArray));
    }
    
    public void populateGPRCodeOutput()
    {
        int[] tempArray = new int[manualWord.getRegCode().getBits().length];
        
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (manualWord.getRegCode().getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        
        GPROutput.setText(Arrays.toString(tempArray));
    }
    
    public void populateIABitCodeOutput()
    {
        int[] tempArray = new int[manualWord.getIABit().getBits().length];
        
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (manualWord.getIABit().getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        IABitOutput.setText(Arrays.toString(tempArray));
    }
    
    public void populateAddressCodeOutput()
    {
        int[] tempArray = new int[manualWord.getAddressCode().getBits().length];
        
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            if (manualWord.getAddressCode().getBits()[itr] != false)
                tempArray[itr] = 1;
            else
                tempArray[itr] = 0;
        }     
        AddressOutput.setText(Arrays.toString(tempArray));
    }    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        GPR3Binary = new javax.swing.JTextField();
        GPR2Binary = new javax.swing.JTextField();
        GPR1Binary = new javax.swing.JTextField();
        GPR0Binary = new javax.swing.JTextField();
        GPR1Decimal = new javax.swing.JTextField();
        GPR2Decimal = new javax.swing.JTextField();
        GPR3Decimal = new javax.swing.JTextField();
        GPR3AcceptInput = new javax.swing.JButton();
        PCAcceptInput = new javax.swing.JButton();
        GPR1AcceptInput = new javax.swing.JButton();
        GPR0AcceptInput = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        CurrentMemoryLocation = new javax.swing.JTextField();
        MemoryLocationInputAccept = new javax.swing.JButton();
        CurrentMemoryLocBinary = new javax.swing.JTextField();
        CurrentMemoryDecimalInput = new javax.swing.JTextField();
        CurrentMemoryDecimalInputAccept = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        GPR0Decimal = new javax.swing.JTextField();
        IXReg1Binary = new javax.swing.JTextField();
        IXReg2Binary = new javax.swing.JTextField();
        IXReg3Binary = new javax.swing.JTextField();
        consoleOutput = new java.awt.TextArea();
        IPL = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        PCBinary = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        MARBinary = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        MBRBinary = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        MFRBinary = new javax.swing.JTextField();
        IRBinary = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        CCBinary = new javax.swing.JTextField();
        PCDecimalInput = new javax.swing.JTextField();
        GPR2AcceptInput = new javax.swing.JButton();
        PCSingleStep = new javax.swing.JButton();
        label1 = new java.awt.Label();
        keyboardInputTextField = new java.awt.TextField();
        keyboardEnterButton = new java.awt.Button();
        TestProgram1 = new javax.swing.JButton();
        halt = new javax.swing.JButton();
        resume = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        ixRegComboBox = new javax.swing.JComboBox();
        gPRComboBox = new javax.swing.JComboBox();
        IABitComboBox = new javax.swing.JComboBox();
        MSRBinary = new javax.swing.JTextField();
        opCodeComboBox = new javax.swing.JComboBox();
        addressCodeComboBox = new javax.swing.JComboBox();
        OpCodeOutput = new javax.swing.JTextField();
        IXRegOutput = new javax.swing.JTextField();
        GPROutput = new javax.swing.JTextField();
        IABitOutput = new javax.swing.JTextField();
        AddressOutput = new javax.swing.JTextField();
        manualAcceptBtn = new javax.swing.JButton();
        CCDecimalInput = new javax.swing.JTextField();
        CCAcceptInput = new javax.swing.JButton();
        IXReg3AcceptInput = new javax.swing.JButton();
        IXReg3Decimal = new javax.swing.JTextField();
        IXReg2Decimal = new javax.swing.JTextField();
        IXReg2AcceptInput = new javax.swing.JButton();
        IXReg1AcceptInput = new javax.swing.JButton();
        IXReg1Decimal = new javax.swing.JTextField();
        TestProgram2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Computer Simulator CSCI 6461");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("GPR0");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("GPR1");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("GPR2");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("GPR3");

        GPR3Binary.setEditable(false);
        GPR3Binary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        GPR3Binary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        GPR3Binary.setText("|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|");

        GPR2Binary.setEditable(false);
        GPR2Binary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        GPR2Binary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        GPR2Binary.setText("|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|");

        GPR1Binary.setEditable(false);
        GPR1Binary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        GPR1Binary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        GPR1Binary.setText("|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|");
        GPR1Binary.setToolTipText("");

        GPR0Binary.setEditable(false);
        GPR0Binary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        GPR0Binary.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        GPR1Decimal.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        GPR1Decimal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        GPR1Decimal.setText("0");
        GPR1Decimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GPR1DecimalActionPerformed(evt);
            }
        });

        GPR2Decimal.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        GPR2Decimal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        GPR2Decimal.setText("0");
        GPR2Decimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GPR2DecimalActionPerformed(evt);
            }
        });

        GPR3Decimal.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        GPR3Decimal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        GPR3Decimal.setText("0");
        GPR3Decimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GPR3DecimalActionPerformed(evt);
            }
        });

        GPR3AcceptInput.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        GPR3AcceptInput.setText("Accept ");
        GPR3AcceptInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GPR3AcceptInputActionPerformed(evt);
            }
        });

        PCAcceptInput.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        PCAcceptInput.setText("Accept Input");
        PCAcceptInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PCAcceptInputActionPerformed(evt);
            }
        });

        GPR1AcceptInput.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        GPR1AcceptInput.setText("Accept ");
        GPR1AcceptInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GPR1AcceptInputActionPerformed(evt);
            }
        });

        GPR0AcceptInput.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        GPR0AcceptInput.setText("Accept ");
        GPR0AcceptInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GPR0AcceptInputActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Memory");

        CurrentMemoryLocation.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        CurrentMemoryLocation.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CurrentMemoryLocation.setText("0");
        CurrentMemoryLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CurrentMemoryLocationActionPerformed(evt);
            }
        });

        MemoryLocationInputAccept.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        MemoryLocationInputAccept.setText("Accept ");
        MemoryLocationInputAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MemoryLocationInputAcceptActionPerformed(evt);
            }
        });

        CurrentMemoryLocBinary.setEditable(false);
        CurrentMemoryLocBinary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        CurrentMemoryLocBinary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CurrentMemoryLocBinary.setText("|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|");

        CurrentMemoryDecimalInput.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        CurrentMemoryDecimalInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CurrentMemoryDecimalInput.setText("0");
        CurrentMemoryDecimalInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CurrentMemoryDecimalInputActionPerformed(evt);
            }
        });

        CurrentMemoryDecimalInputAccept.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        CurrentMemoryDecimalInputAccept.setText("Accept ");
        CurrentMemoryDecimalInputAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CurrentMemoryDecimalInputAcceptActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("IX1");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("IX2");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("IX3");

        GPR0Decimal.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        GPR0Decimal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        GPR0Decimal.setText("0");
        GPR0Decimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GPR0DecimalActionPerformed(evt);
            }
        });

        IXReg1Binary.setEditable(false);
        IXReg1Binary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        IXReg1Binary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IXReg1Binary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IXReg1BinaryActionPerformed(evt);
            }
        });

        IXReg2Binary.setEditable(false);
        IXReg2Binary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        IXReg2Binary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IXReg2Binary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IXReg2BinaryActionPerformed(evt);
            }
        });

        IXReg3Binary.setEditable(false);
        IXReg3Binary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        IXReg3Binary.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        IPL.setBackground(new java.awt.Color(255, 0, 0));
        IPL.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        IPL.setText("IPL");
        IPL.setToolTipText("");
        IPL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IPLActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("MSR");

        PCBinary.setEditable(false);
        PCBinary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        PCBinary.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("MFR");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("MAR");

        MARBinary.setEditable(false);
        MARBinary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MARBinary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MARBinary.setText("|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("MBR");

        MBRBinary.setEditable(false);
        MBRBinary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MBRBinary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MBRBinary.setText("|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("PC");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("IR");

        MFRBinary.setEditable(false);
        MFRBinary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MFRBinary.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        IRBinary.setEditable(false);
        IRBinary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        IRBinary.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("CC");

        CCBinary.setEditable(false);
        CCBinary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        CCBinary.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        PCDecimalInput.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        PCDecimalInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PCDecimalInput.setText("0");
        PCDecimalInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PCDecimalInputActionPerformed(evt);
            }
        });

        GPR2AcceptInput.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        GPR2AcceptInput.setText("Accept ");
        GPR2AcceptInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GPR2AcceptInputActionPerformed(evt);
            }
        });

        PCSingleStep.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        PCSingleStep.setText("Single Step");
        PCSingleStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PCSingleStepActionPerformed(evt);
            }
        });

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        label1.setText("Keyboard");

        keyboardInputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyboardInputTextFieldActionPerformed(evt);
            }
        });

        keyboardEnterButton.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        keyboardEnterButton.setLabel("ENTER");
        keyboardEnterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyboardEnterButtonActionPerformed(evt);
            }
        });

        TestProgram1.setBackground(new java.awt.Color(255, 0, 0));
        TestProgram1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        TestProgram1.setText("Test Program 1");
        TestProgram1.setToolTipText("");
        TestProgram1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TestProgram1ActionPerformed(evt);
            }
        });

        halt.setBackground(new java.awt.Color(255, 0, 0));
        halt.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        halt.setText("HALT");
        halt.setToolTipText("");
        halt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                haltActionPerformed(evt);
            }
        });

        resume.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        resume.setText("RESUME");
        resume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Manual Instructions");

        ixRegComboBox.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        ixRegComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "None", "IXReg 1", "IXReg 2", "IXReg 3" }));
        ixRegComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ixRegComboBoxActionPerformed(evt);
            }
        });

        gPRComboBox.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        gPRComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GPR0", "GPR1", "GPR2", "GPR3" }));
        gPRComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gPRComboBoxActionPerformed(evt);
            }
        });

        IABitComboBox.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        IABitComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "IA Bit Not Set", "IA Bit Set" }));
        IABitComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IABitComboBoxActionPerformed(evt);
            }
        });

        MSRBinary.setEditable(false);
        MSRBinary.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MSRBinary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MSRBinary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MSRBinaryActionPerformed(evt);
            }
        });

        opCodeComboBox.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        opCodeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"HLT", "LDR", "STR", "LDA", "AMR", "SMR", "AIR", "SIR", "JZ", "JNE", "JCC", "JMA", "JSR", "RFS", "SOB", "JGE", "MLT", "DVD", "TRR", "AND", "ORR", "NOT", "SRC", "RRC", "LDX", "STX", "LFDR", "STFR", "IN", "OUT", "CHK", "TRAP", "Invalid OpCode"}));
        opCodeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opCodeComboBoxActionPerformed(evt);
            }
        });

        addressCodeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        addressCodeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressCodeComboBoxActionPerformed(evt);
            }
        });

        OpCodeOutput.setEditable(false);
        OpCodeOutput.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        OpCodeOutput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        OpCodeOutput.setText("jTextField1");
        OpCodeOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpCodeOutputActionPerformed(evt);
            }
        });

        IXRegOutput.setEditable(false);
        IXRegOutput.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        IXRegOutput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IXRegOutput.setText("jTextField1");

        GPROutput.setEditable(false);
        GPROutput.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        GPROutput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        GPROutput.setText("jTextField1");

        IABitOutput.setEditable(false);
        IABitOutput.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        IABitOutput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IABitOutput.setText("jTextField1");

        AddressOutput.setEditable(false);
        AddressOutput.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        AddressOutput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AddressOutput.setText("jTextField1");

        manualAcceptBtn.setText("Accept Manual Word Instruction");
        manualAcceptBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualAcceptBtnActionPerformed(evt);
            }
        });

        CCDecimalInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CCDecimalInput.setText("0");

        CCAcceptInput.setText("Accept Input");
        CCAcceptInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CCAcceptInputActionPerformed(evt);
            }
        });

        IXReg3AcceptInput.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        IXReg3AcceptInput.setText("Accept ");
        IXReg3AcceptInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IXReg3AcceptInputActionPerformed(evt);
            }
        });

        IXReg3Decimal.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        IXReg3Decimal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IXReg3Decimal.setText("0");
        IXReg3Decimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IXReg3DecimalActionPerformed(evt);
            }
        });

        IXReg2Decimal.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        IXReg2Decimal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IXReg2Decimal.setText("0");
        IXReg2Decimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IXReg2DecimalActionPerformed(evt);
            }
        });

        IXReg2AcceptInput.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        IXReg2AcceptInput.setText("Accept ");
        IXReg2AcceptInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IXReg2AcceptInputActionPerformed(evt);
            }
        });

        IXReg1AcceptInput.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        IXReg1AcceptInput.setText("Accept ");
        IXReg1AcceptInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IXReg1AcceptInputActionPerformed(evt);
            }
        });

        IXReg1Decimal.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        IXReg1Decimal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IXReg1Decimal.setText("0");
        IXReg1Decimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IXReg1DecimalActionPerformed(evt);
            }
        });

        TestProgram2.setBackground(new java.awt.Color(255, 0, 0));
        TestProgram2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        TestProgram2.setText("Test Program 2");
        TestProgram2.setToolTipText("");
        TestProgram2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TestProgram2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GPR1Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GPR1Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(GPR1AcceptInput))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GPR2Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GPR2Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(GPR2AcceptInput))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(GPR3Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GPR3Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(GPR3AcceptInput)))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IXReg2Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IXReg1Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(IXReg3Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MFRBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MSRBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(IXReg2Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(IXReg2AcceptInput))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(IXReg3Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(IXReg3AcceptInput))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(IXReg1Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(IXReg1AcceptInput))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CurrentMemoryLocBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CurrentMemoryDecimalInput, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CurrentMemoryDecimalInputAccept))
                            .addComponent(consoleOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(keyboardInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(resume, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(halt, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(keyboardEnterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(IRBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(CCBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(CurrentMemoryLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(MemoryLocationInputAccept)
                                                .addGap(13, 13, 13))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(154, 154, 154)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(MARBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(PCBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(MBRBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(CCDecimalInput, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PCDecimalInput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(PCAcceptInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CCAcceptInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PCSingleStep))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(opCodeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(OpCodeOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(ixRegComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(gPRComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(IXRegOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(22, 22, 22)
                                                .addComponent(GPROutput, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(IABitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(addressCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(IABitOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(AddressOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(265, 265, 265)
                                        .addComponent(manualAcceptBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(0, 811, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(IPL, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TestProgram1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GPR0Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GPR0Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(GPR0AcceptInput))
                    .addComponent(TestProgram2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IPL, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TestProgram1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TestProgram2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR0Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR0AcceptInput)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR0Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IXReg1Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR1Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR1Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR1AcceptInput)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IXReg2Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR2Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR2Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IXReg3Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR2AcceptInput))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IXReg1AcceptInput)
                            .addComponent(IXReg1Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IXReg2Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IXReg2AcceptInput))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IXReg3Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IXReg3AcceptInput))
                        .addGap(5, 5, 5)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GPR3Binary, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GPR3Decimal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GPR3AcceptInput)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MFRBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MSRBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MARBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MBRBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(opCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ixRegComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gPRComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IABitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addressCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(OpCodeOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IXRegOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPROutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IABitOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddressOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(manualAcceptBtn)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PCBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PCDecimalInput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PCAcceptInput)
                    .addComponent(PCSingleStep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IRBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(halt, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(resume, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(keyboardInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(keyboardEnterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(CCBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CCDecimalInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CCAcceptInput)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CurrentMemoryLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MemoryLocationInputAccept)
                            .addComponent(CurrentMemoryLocBinary, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CurrentMemoryDecimalInput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CurrentMemoryDecimalInputAccept))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(consoleOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(241, Short.MAX_VALUE))
        );

        label1.getAccessibleContext().setAccessibleName("KeyboardLabel");
    }// </editor-fold>//GEN-END:initComponents

    private void GPR1DecimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GPR1DecimalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GPR1DecimalActionPerformed

    private void GPR2DecimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GPR2DecimalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GPR2DecimalActionPerformed

    private void GPR3DecimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GPR3DecimalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GPR3DecimalActionPerformed

    private void CurrentMemoryLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CurrentMemoryLocationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CurrentMemoryLocationActionPerformed

    private void CurrentMemoryDecimalInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CurrentMemoryDecimalInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CurrentMemoryDecimalInputActionPerformed

    private void GPR0AcceptInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GPR0AcceptInputActionPerformed
        String userInput = GPR0Decimal.getText();
        int userInputInt = Integer.parseInt(userInput);
        simComputer.getCPU().simulatedGPR0.setValue(userInputInt);
        populateGPR0Output();
    }//GEN-LAST:event_GPR0AcceptInputActionPerformed

    private void GPR1AcceptInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GPR1AcceptInputActionPerformed
        String userInput = GPR1Decimal.getText();
        int userInputInt = Integer.parseInt(userInput);
        simComputer.getCPU().simulatedGPR1.setValue(userInputInt);
        populateGPR1Output();
    }//GEN-LAST:event_GPR1AcceptInputActionPerformed

    private void PCAcceptInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PCAcceptInputActionPerformed
        String userInput = PCDecimalInput.getText();
        int userInputInt = Integer.parseInt(userInput);
        simComputer.getCPU().simulatedPC.setValue(userInputInt);
        populatePCOutput();
    }//GEN-LAST:event_PCAcceptInputActionPerformed

    private void GPR3AcceptInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GPR3AcceptInputActionPerformed
        String userInput = GPR3Decimal.getText();
        int userInputInt = Integer.parseInt(userInput);
        simComputer.getCPU().simulatedGPR3.setValue(userInputInt);
        populateGPR3Output();
    }//GEN-LAST:event_GPR3AcceptInputActionPerformed

    private void MemoryLocationInputAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MemoryLocationInputAcceptActionPerformed
        String userInput = CurrentMemoryLocation.getText();
        int userInputInt = Integer.parseInt(userInput);
        
        if (userInputInt < simComputer.getCPU().getCacheMemory().getMaxMemory() )
        {
            currentMemoryLoc = userInputInt;
            boolean[] tempArray = simComputer.getCPU().getCacheMemory().getWordAtAddress(currentMemoryLoc).getBits();
            int[] memoryBinary = new int[tempArray.length];

            for (int itr = 0; itr < tempArray.length; itr++)
            {
                if (tempArray[itr] != false)
                    memoryBinary[itr] = 1;
                else
                    memoryBinary[itr] = 0;
            }

            CurrentMemoryLocBinary.setText(Arrays.toString(memoryBinary));  
            Word temp = simComputer.getCPU().getCacheMemory().getWordAtAddress(currentMemoryLoc);
            CurrentMemoryDecimalInput.setText(Integer.toString(temp.getDecimalValue()));
        }
        
        else
            consoleOutput.setText("Memory locations are limited to " + 
                               (simComputer.getCPU().getCacheMemory().getMaxMemory() - 1) +
                               " and below...");
    }//GEN-LAST:event_MemoryLocationInputAcceptActionPerformed

    private void CurrentMemoryDecimalInputAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CurrentMemoryDecimalInputAcceptActionPerformed
        String userInput = CurrentMemoryDecimalInput.getText();
        int userInputInt = Integer.parseInt(userInput);
        
 //       if (currentMemoryLoc > 5)
        if (currentMemoryLoc != 3 || currentMemoryLoc != 5)
        {
            Word tempWord = new Word();
            tempWord.setValue(userInputInt);
            simComputer.getCPU().getCacheMemory().setWordAtAddress(currentMemoryLoc, tempWord);
            boolean[] tempArray = simComputer.getCPU().getCacheMemory().getWordAtAddress(currentMemoryLoc).getBits();

            int[] memoryBinary = new int[tempArray.length];

            for (int itr = 0; itr < tempArray.length; itr++)
            {
                if (tempArray[itr] != false)
                    memoryBinary[itr] = 1;
                else
                    memoryBinary[itr] = 0;
            }

            CurrentMemoryLocBinary.setText(Arrays.toString(memoryBinary));
        }
        
        else
        {
            switch (currentMemoryLoc)
            {
    /*            case (0) : // reserved for trap instruction
                {
                    consoleOutput.setText("Memory Location 0 reserved for trap "
                            + "instruction.  Please try another location...\n");
                }
                break;
                    
                case (1) : // reserved for machine fault
                {
                    consoleOutput.setText("Memory Location 1 reserved for machine "
                            + "fault.  Please try another location...\n");
                }
                break;
                    
                case (2) : // reserved for storing PC on trap
                {
                    consoleOutput.setText("Memory Location 2 reserved for storing "
                            + "PC on a trap.  Please try another location...\n");
                }
                break;
     */               
                case (3) : // not used
                {
                    consoleOutput.setText("Memory Location 3 reserved. "
                            + "Please try another location...\n");
                }
                break;
                    
                    
    /*            case (4) : // Reserved for storing PC for machine fault
                {
                    consoleOutput.setText("Memory Location 4 reserved for storing "
                            + "PC on a machine fault.  "
                            + "Please try another location...\n");
                }
                break;
     */               
                case (5) : // not used
                {
                    consoleOutput.setText("Memory Location 5 reserved. "
                            + "Please try another location...\n");
                }
                break;    
            }
        }        
    }//GEN-LAST:event_CurrentMemoryDecimalInputAcceptActionPerformed

    private void GPR0DecimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GPR0DecimalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GPR0DecimalActionPerformed

    private void IPLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IPLActionPerformed

        consoleOutput.setText("Loading bootstrap from ROM...");
        
        // Pass the CPU to the ROMLoader, and let it allow ALU to handle stuff
        loadROM();
        updateOutput();
              
    }//GEN-LAST:event_IPLActionPerformed

    private void PCDecimalInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PCDecimalInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PCDecimalInputActionPerformed

    private void GPR2AcceptInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GPR2AcceptInputActionPerformed
        String userInput = GPR2Decimal.getText();
        int userInputInt = Integer.parseInt(userInput);
        simComputer.getCPU().simulatedGPR2.setValue(userInputInt);
        populateGPR2Output();
    }//GEN-LAST:event_GPR2AcceptInputActionPerformed

    private void PCSingleStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PCSingleStepActionPerformed

        // Get the word value from memory referenced by the PC...
        Word temp = simComputer.getCPU()
                       .getCacheMemory()
                       .getWordAtAddress(
                        simComputer
                            .getCPU()
                            .simulatedPC
                            .getDecimalValue());
        
        // ...Now convert the binary into an instruction...
        WordInstructions tempInstruction = convertWordToInstruction(temp);
        
        // ...Now Pass the instruction to the CPU...
        simComputer.getCPU().ALULogic(tempInstruction);
        
        // ...finally, update output...
        updateOutput();
    }//GEN-LAST:event_PCSingleStepActionPerformed

    private void keyboardInputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyboardInputTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keyboardInputTextFieldActionPerformed

    private void keyboardEnterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyboardEnterButtonActionPerformed
        simComputer.getCPU().keyboard.setValue(keyboardInputTextField.getText());
        keyboardInputTextField.setText("");
        
        int keyboardInputLength = simComputer.getCPU().keyboard.getDeviceValue().length();
        
        if (testProgram1Running)
        { 
            // clear keyboard memory inputs for test program 1 for now...
            for (int itr = 0; itr < 5; itr++)
            {
                simComputer.getCPU().simulatedGPR0.setValue(0);
                simComputer.getCPU().simulatedIXReg1.setValue(KEYBOARD_OUTPOUT_INITIAL_ADDRESS);
                WordInstructions tempInstruction = new WordInstructions();
                tempInstruction.getIABit().setValue(1);
                tempInstruction.getOpCode().setValue(2);
                tempInstruction.getIxRegCode().setValue(1);
                tempInstruction.getAddressCode().setValue(itr);
                simComputer.getCPU().ALULogic(tempInstruction);
            }

            for (int itr = 0; itr < keyboardInputLength; itr++)
            {
                simComputer.getCPU().ALULogic(KEYBOARD_TO_GPR0_INSTRUCTION);
                WordInstructions tempInstruction = new WordInstructions();
                tempInstruction.getIABit().setValue(1);
                tempInstruction.getOpCode().setValue(2);
                tempInstruction.getIxRegCode().setValue(1);
                simComputer.getCPU().simulatedIXReg1.setValue(KEYBOARD_OUTPOUT_INITIAL_ADDRESS + itr);
                simComputer.getCPU().simulatedIR.setValue(convertInstructionToWord(tempInstruction));
                simComputer.getCPU().simulatedMBR.setValue(convertInstructionToWord(tempInstruction));
                simComputer.getCPU().ALULogic(tempInstruction);
                updateOutput();  
            }
            
            if (simComputer.getCPU().simulatedGPR2.getDecimalValue() >= 20)
            {
                program1.handleUserInput(simComputer.getCPU());
                testProgram1Running = false;
                consoleOutput.setText("The closest number input to the 21st number was " +
                                      simComputer.getCPU().simulatedGPR2.getDecimalValue());
            }
            
            else
                program1.handleUserInput(simComputer.getCPU());
        }
        
        else if (testProgram2Running && !acceptTestProgram2Input)
        {
            acceptTestProgram2Input = true;
            
            for (int itr = keyboardInputLength - 1; itr >= 0; itr--)
            {
                simComputer.getCPU().ALULogic(KEYBOARD_TO_GPR0_INSTRUCTION);
                WordInstructions tempInstruction = new WordInstructions();
                tempInstruction.getIABit().setValue(1);
                tempInstruction.getOpCode().setValue(2);
                tempInstruction.getIxRegCode().setValue(1);
                simComputer.getCPU().simulatedIXReg1.setValue(KEYBOARD_OUTPOUT_INITIAL_ADDRESS + itr);
                simComputer.getCPU().simulatedIR.setValue(convertInstructionToWord(tempInstruction));
                simComputer.getCPU().simulatedMBR.setValue(convertInstructionToWord(tempInstruction));
                simComputer.getCPU().ALULogic(tempInstruction);
                updateOutput();                  
            }
        }
        
        else if (testProgram2Running && acceptTestProgram2Input)
        {
            for (int itr = keyboardInputLength - 1; itr >= 0; itr--)
            {
                simComputer.getCPU().ALULogic(KEYBOARD_TO_GPR0_INSTRUCTION);
                WordInstructions tempInstruction = new WordInstructions();
                tempInstruction.getIABit().setValue(1);
                tempInstruction.getOpCode().setValue(2);
                tempInstruction.getIxRegCode().setValue(1);
                simComputer.getCPU().simulatedIXReg1.setValue(TEST_PROGRAM_TWO_USER_INPUT_INITIAL_ADDRESS + itr);
                simComputer.getCPU().simulatedIR.setValue(convertInstructionToWord(tempInstruction));
                simComputer.getCPU().simulatedMBR.setValue(convertInstructionToWord(tempInstruction));
                simComputer.getCPU().ALULogic(tempInstruction);               
            }      
            
            program2 = new TestProgram2(simComputer.getCPU());
            updateOutput();
            testProgram2Running = false;
            acceptTestProgram2Input = false;
            
            if (program2.getMatch())
            {
                consoleOutput.setText("Match found at word " + simComputer.getCPU().simulatedGPR0.getDecimalValue() + 
                                      " of sentence " + (simComputer.getCPU().simulatedGPR1.getDecimalValue()));
            }
            
            else
                consoleOutput.setText("Match not found...");
        }
    }//GEN-LAST:event_keyboardEnterButtonActionPerformed

    private void TestProgram1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TestProgram1ActionPerformed
        testProgram1Running = true;
        program1 = new TestProgram1(simComputer.getCPU());
        consoleOutput.setText("Program 1 running..." + 
                      "Please input 21 numbers between 0 and 36767..." +
                      "The program will output a number from the first" +
                      " 20 inputs that is closest to the 21st user input...");
        updateOutput();
    }//GEN-LAST:event_TestProgram1ActionPerformed

    private void haltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_haltActionPerformed
        simComputer.getCPU().ALULogic(HALT_INSTRUCTION); 
 //       consoleOutput.setText(simComputer.getCPU().getHlt_flag() + "    ");
        consoleOutput.setText("Machine is stopping work now...please press down RESUME button if you want to get it back to work!");
    }//GEN-LAST:event_haltActionPerformed

    private void resumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resumeActionPerformed
        simComputer.getCPU().setHlt_flag();
 //       consoleOutput.setText(simComputer.getCPU().getHlt_flag() + "    ");
        consoleOutput.setText("Machine is getting back to work!");
    }//GEN-LAST:event_resumeActionPerformed

    private void opCodeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opCodeComboBoxActionPerformed
        int temp = opCodeComboBox.getSelectedIndex();
        
        switch (temp)
        {
            case(0) : {manualWord.getOpCode().setValue(0);} break;
            case(1) : {manualWord.getOpCode().setValue(1);} break;
            case(2) : {manualWord.getOpCode().setValue(2);} break;
            case(3) : {manualWord.getOpCode().setValue(3);} break;
            case(4) : {manualWord.getOpCode().setValue(4);} break;
            case(5) : {manualWord.getOpCode().setValue(5);} break;
            case(6) : {manualWord.getOpCode().setValue(6);} break;
            case(7) : {manualWord.getOpCode().setValue(7);} break;
            case(8) : {manualWord.getOpCode().setValue(8);} break;
            case(9) : {manualWord.getOpCode().setValue(9);} break;
            case(10) : {manualWord.getOpCode().setValue(10);} break;
            case(11) : {manualWord.getOpCode().setValue(11);} break;
            case(12) : {manualWord.getOpCode().setValue(12);} break;
            case(13) : {manualWord.getOpCode().setValue(13);} break;
            case(14) : {manualWord.getOpCode().setValue(14);} break;
            case(15) : {manualWord.getOpCode().setValue(15);} break;
            case(16) : {manualWord.getOpCode().setValue(16);} break;
            case(17) : {manualWord.getOpCode().setValue(17);} break;
            case(18) : {manualWord.getOpCode().setValue(18);} break;
            case(19) : {manualWord.getOpCode().setValue(19);} break;
            case(20) : {manualWord.getOpCode().setValue(20);} break;
            case(21) : {manualWord.getOpCode().setValue(21);} break;
            case(22) : {manualWord.getOpCode().setValue(25);} break;
            case(23) : {manualWord.getOpCode().setValue(26);} break;
            case(24) : {manualWord.getOpCode().setValue(33);} break;
            case(25) : {manualWord.getOpCode().setValue(34);} break;
            case(26) : {manualWord.getOpCode().setValue(40);} break;
            case(27) : {manualWord.getOpCode().setValue(41);} break;
            case(28) : {manualWord.getOpCode().setValue(49);} break;
            case(29) : {manualWord.getOpCode().setValue(50);} break;
            case(30) : {manualWord.getOpCode().setValue(51);} break;
            case(31) : {manualWord.getOpCode().setValue(24);} break;
            case(32) : {manualWord.getOpCode().setValue(55);} break;
            default : {manualWord.getOpCode().setValue(0);}
        }
        
        populateOpCodeOutput();
        
    }//GEN-LAST:event_opCodeComboBoxActionPerformed

    private void IXReg1BinaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IXReg1BinaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IXReg1BinaryActionPerformed

    private void IXReg2BinaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IXReg2BinaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IXReg2BinaryActionPerformed

    private void OpCodeOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpCodeOutputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OpCodeOutputActionPerformed

    private void ixRegComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ixRegComboBoxActionPerformed
        int temp = ixRegComboBox.getSelectedIndex();
        
        switch (temp)
        {
            case(0) : {manualWord.getIxRegCode().setValue(0);} break;
            case(1) : {manualWord.getIxRegCode().setValue(1);} break;
            case(2) : {manualWord.getIxRegCode().setValue(2);} break;
            case(3) : {manualWord.getIxRegCode().setValue(3);} break;
            default : {manualWord.getIxRegCode().setValue(0);}
        }
        
        populateIXRegCodeOutput();
    }//GEN-LAST:event_ixRegComboBoxActionPerformed

    private void gPRComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gPRComboBoxActionPerformed
        int temp = gPRComboBox.getSelectedIndex();
        
        switch (temp)
        {
            case(0) : {manualWord.getRegCode().setValue(0);} break;
            case(1) : {manualWord.getRegCode().setValue(1);} break;
            case(2) : {manualWord.getRegCode().setValue(2);} break;
            case(3) : {manualWord.getRegCode().setValue(3);} break;
            default : {manualWord.getRegCode().setValue(0);}
        }
            
        populateGPRCodeOutput();
    }//GEN-LAST:event_gPRComboBoxActionPerformed

    private void IABitComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IABitComboBoxActionPerformed
        int temp = IABitComboBox.getSelectedIndex();
        
        switch (temp)
        {
            case(0) : {manualWord.getIABit().setValue(0);} break;
            case(1) : {manualWord.getIABit().setValue(1);} break;
            default : {manualWord.getIABit().setValue(0);}
        }
            
        populateIABitCodeOutput();
    }//GEN-LAST:event_IABitComboBoxActionPerformed

    private void addressCodeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressCodeComboBoxActionPerformed
        int temp = addressCodeComboBox.getSelectedIndex();
        
        switch (temp)
        {
            case (0) : {manualWord.getAddressCode().setValue(0);} break;
            case (1) : {manualWord.getAddressCode().setValue(1);} break;
            case (2) : {manualWord.getAddressCode().setValue(2);} break;
            case (3) : {manualWord.getAddressCode().setValue(3);} break;
            case (4) : {manualWord.getAddressCode().setValue(4);} break;
            case (5) : {manualWord.getAddressCode().setValue(5);} break;
            case (6) : {manualWord.getAddressCode().setValue(6);} break;
            case (7) : {manualWord.getAddressCode().setValue(7);} break;
            case (8) : {manualWord.getAddressCode().setValue(8);} break;
            case (9) : {manualWord.getAddressCode().setValue(9);} break;
            case (10) : {manualWord.getAddressCode().setValue(10);} break;
            case (11) : {manualWord.getAddressCode().setValue(11);} break;
            case (12) : {manualWord.getAddressCode().setValue(12);} break;
            case (13) : {manualWord.getAddressCode().setValue(13);} break;
            case (14) : {manualWord.getAddressCode().setValue(14);} break;
            case (15) : {manualWord.getAddressCode().setValue(15);} break;
            case (16) : {manualWord.getAddressCode().setValue(16);} break;
            case (17) : {manualWord.getAddressCode().setValue(17);} break;
            case (18) : {manualWord.getAddressCode().setValue(18);} break;
            case (19) : {manualWord.getAddressCode().setValue(19);} break;
            case (20) : {manualWord.getAddressCode().setValue(20);} break;
            case (21) : {manualWord.getAddressCode().setValue(21);} break;
            case (22) : {manualWord.getAddressCode().setValue(22);} break;
            case (23) : {manualWord.getAddressCode().setValue(23);} break;
            case (24) : {manualWord.getAddressCode().setValue(24);} break;
            case (25) : {manualWord.getAddressCode().setValue(25);} break;
            case (26) : {manualWord.getAddressCode().setValue(26);} break;
            case (27) : {manualWord.getAddressCode().setValue(27);} break;
            case (28) : {manualWord.getAddressCode().setValue(28);} break;
            case (29) : {manualWord.getAddressCode().setValue(29);} break;
            case (30) : {manualWord.getAddressCode().setValue(30);} break;
            case (31) : {manualWord.getAddressCode().setValue(31);} break;
            default : {manualWord.getAddressCode().setValue(0);}
        }
            
        populateAddressCodeOutput();
    }//GEN-LAST:event_addressCodeComboBoxActionPerformed

    private void manualAcceptBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualAcceptBtnActionPerformed
        simComputer.getCPU().ALULogic(manualWord); 
        consoleOutput.setText("Accepting manual instruction input...");
        populateOutput();
    }//GEN-LAST:event_manualAcceptBtnActionPerformed

    private void CCAcceptInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CCAcceptInputActionPerformed
        String userInput = CCDecimalInput.getText();
        int userInputInt = Integer.parseInt(userInput);
        simComputer.getCPU().simulatedCC.setValue(userInputInt);
        populateCCOutput();
    }//GEN-LAST:event_CCAcceptInputActionPerformed

    private void MSRBinaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MSRBinaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MSRBinaryActionPerformed

    private void IXReg3AcceptInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IXReg3AcceptInputActionPerformed
        String userInput = IXReg3Decimal.getText();
        int userInputInt = Integer.parseInt(userInput);
        simComputer.getCPU().simulatedIXReg3.setValue(userInputInt);
        populateIXReg3Output();
    }//GEN-LAST:event_IXReg3AcceptInputActionPerformed

    private void IXReg3DecimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IXReg3DecimalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IXReg3DecimalActionPerformed

    private void IXReg2DecimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IXReg2DecimalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IXReg2DecimalActionPerformed

    private void IXReg2AcceptInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IXReg2AcceptInputActionPerformed
        String userInput = IXReg2Decimal.getText();
        int userInputInt = Integer.parseInt(userInput);
        simComputer.getCPU().simulatedIXReg2.setValue(userInputInt);
        populateIXReg2Output();
    }//GEN-LAST:event_IXReg2AcceptInputActionPerformed

    private void IXReg1AcceptInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IXReg1AcceptInputActionPerformed
        String userInput = IXReg1Decimal.getText();
        int userInputInt = Integer.parseInt(userInput);
        simComputer.getCPU().simulatedIXReg1.setValue(userInputInt);
        populateIXReg1Output();
    }//GEN-LAST:event_IXReg1AcceptInputActionPerformed

    private void IXReg1DecimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IXReg1DecimalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IXReg1DecimalActionPerformed

    private void TestProgram2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TestProgram2ActionPerformed
        testProgram2Running = true;
        
        consoleOutput.setText("Test program 2 is running... Please input "
                            + "6 sentences into the keyboard separated by periods"
                            + ". Afterwards, input a final word.  The program"
                            + " will search the first 6 sentences for the word. "
                            + "If found, the word will be output, and the word's"
                            + " sentence number and word number will be output.");
        updateOutput();        
    }//GEN-LAST:event_TestProgram2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AddressOutput;
    private javax.swing.JButton CCAcceptInput;
    private javax.swing.JTextField CCBinary;
    private javax.swing.JTextField CCDecimalInput;
    private javax.swing.JTextField CurrentMemoryDecimalInput;
    private javax.swing.JButton CurrentMemoryDecimalInputAccept;
    private javax.swing.JTextField CurrentMemoryLocBinary;
    private javax.swing.JTextField CurrentMemoryLocation;
    private javax.swing.JButton GPR0AcceptInput;
    private javax.swing.JTextField GPR0Binary;
    private javax.swing.JTextField GPR0Decimal;
    private javax.swing.JButton GPR1AcceptInput;
    private javax.swing.JTextField GPR1Binary;
    private javax.swing.JTextField GPR1Decimal;
    private javax.swing.JButton GPR2AcceptInput;
    private javax.swing.JTextField GPR2Binary;
    private javax.swing.JTextField GPR2Decimal;
    private javax.swing.JButton GPR3AcceptInput;
    private javax.swing.JTextField GPR3Binary;
    private javax.swing.JTextField GPR3Decimal;
    private javax.swing.JTextField GPROutput;
    private javax.swing.JComboBox IABitComboBox;
    private javax.swing.JTextField IABitOutput;
    private javax.swing.JButton IPL;
    private javax.swing.JTextField IRBinary;
    private javax.swing.JButton IXReg1AcceptInput;
    private javax.swing.JTextField IXReg1Binary;
    private javax.swing.JTextField IXReg1Decimal;
    private javax.swing.JButton IXReg2AcceptInput;
    private javax.swing.JTextField IXReg2Binary;
    private javax.swing.JTextField IXReg2Decimal;
    private javax.swing.JButton IXReg3AcceptInput;
    private javax.swing.JTextField IXReg3Binary;
    private javax.swing.JTextField IXReg3Decimal;
    private javax.swing.JTextField IXRegOutput;
    private javax.swing.JTextField MARBinary;
    private javax.swing.JTextField MBRBinary;
    private javax.swing.JTextField MFRBinary;
    private javax.swing.JTextField MSRBinary;
    private javax.swing.JButton MemoryLocationInputAccept;
    private javax.swing.JTextField OpCodeOutput;
    private javax.swing.JButton PCAcceptInput;
    private javax.swing.JTextField PCBinary;
    private javax.swing.JTextField PCDecimalInput;
    private javax.swing.JButton PCSingleStep;
    private javax.swing.JButton TestProgram1;
    private javax.swing.JButton TestProgram2;
    private javax.swing.JComboBox addressCodeComboBox;
    private java.awt.TextArea consoleOutput;
    private javax.swing.JComboBox gPRComboBox;
    private javax.swing.JButton halt;
    private javax.swing.JComboBox ixRegComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private java.awt.Button keyboardEnterButton;
    private java.awt.TextField keyboardInputTextField;
    private java.awt.Label label1;
    private javax.swing.JButton manualAcceptBtn;
    private javax.swing.JComboBox opCodeComboBox;
    private javax.swing.JButton resume;
    // End of variables declaration//GEN-END:variables

    private void determineRegToUpdate(final int itr)
    {
        switch (itr)
        {
            case (0) :
            {
                populateIXReg1Output();
                simComputer.getCPU().simulatedIXReg1.flagAsUpdated();
            }
            break;
                
            case (1) :
            {
                populateIXReg2Output();
                simComputer.getCPU().simulatedIXReg2.flagAsUpdated();
            }
            break;
                
            case (2) :
            {
                populateIXReg3Output();
                simComputer.getCPU().simulatedIXReg3.flagAsUpdated();
            }
            break;
                
            case (3) :
            {
                populateGPR0Output();
                simComputer.getCPU().simulatedGPR0.flagAsUpdated();
            }
            break;                
                
            case (4) :
            {
                populateGPR1Output();
                simComputer.getCPU().simulatedGPR1.flagAsUpdated();
            }
            break;          
                
            case (5) :
            {
                populateGPR2Output();
                simComputer.getCPU().simulatedGPR2.flagAsUpdated();
            }
            break;          
    
            case (6) :
            {
                populateGPR3Output();
                simComputer.getCPU().simulatedGPR3.flagAsUpdated();
            }
            break; 
                
            case (7) :
            {
                populatePCOutput();
                simComputer.getCPU().simulatedPC.flagAsUpdated();
            }
            break;     
                
            case (8) :
            {
                populateCCOutput();
                simComputer.getCPU().simulatedCC.flagAsUpdated();
            }
            break;     
             
            case (9) :
            {
                populateIROutput();
                simComputer.getCPU().simulatedIR.flagAsUpdated();
            }
            break;        
                
            case (10) :
            {
                populateMSROutput();
                simComputer.getCPU().simulatedMSR.flagAsUpdated();
            }
            break;         
                
            case (11) :
            {
                populateMFROutput();
                simComputer.getCPU().simulatedMFR.flagAsUpdated();
            }
            break;
                
            case (12) :
            {
                populateMAROutput();
                simComputer.getCPU().simulatedMAR.flagAsUpdated();
            }
            break;         
                
            case (13) :
            {
                populateMBROutput();
                simComputer.getCPU().simulatedMBR.flagAsUpdated();
            }
            break;         
        }
    }
    
   /**
    * convertWordToInstruction
    * 
    * @param toConvert - 16 bit word to convert to an instruction
    * 
    * @return - a WordInstruction that is formatted properly
    * 
    * Method will convert each bit into each component of the instruction
    ***************************************************************************/
    private WordInstructions convertWordToInstruction(Word toConvert)
    {
        WordInstructions tempInstruction = new WordInstructions();
        int itr = WORD_BITS - 1;
        
        for (int tempItr = ADDRESS_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert.getBits()[itr] != false)
                tempInstruction.getAddressCode().toggleBit(ADDRESS_LENGTH-tempItr);
       
        for (int tempItr = IA_BIT_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert.getBits()[itr] != false)
                tempInstruction.getIABit().toggleBit(IA_BIT_LENGTH-tempItr);
        
        for (int tempItr = GPR_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert.getBits()[itr] != false)
                tempInstruction.getRegCode().toggleBit(GPR_LENGTH-tempItr);  
        
        for (int tempItr = IX_REG_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert.getBits()[itr] != false)
                tempInstruction.getIxRegCode().toggleBit(IX_REG_LENGTH-tempItr);
        
        for (int tempItr = OPCODE_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert.getBits()[itr] != false)
                tempInstruction.getOpCode().toggleBit(OPCODE_LENGTH-tempItr);
        
        return tempInstruction;
    }
    
    private Word convertInstructionToWord(WordInstructions toSet)
    {
        int intToSet = 0;
        int itr = 0;
        
        for (int tempItr = ADDRESS_LENGTH; tempItr > 0; tempItr--, itr++)
            if (toSet.getAddressCode().getBits()[tempItr - 1] != false)
                intToSet += (Math.pow(2, itr));
       
        for (int tempItr = IA_BIT_LENGTH; tempItr > 0; tempItr--, itr++)
            if (toSet.getIABit().getBits()[tempItr - 1] != false)
                intToSet += (Math.pow(2, itr));
        
        for (int tempItr = GPR_LENGTH; tempItr > 0; tempItr--, itr++)
            if (toSet.getRegCode().getBits()[tempItr - 1] != false)
                intToSet += (Math.pow(2, itr));
        
        for (int tempItr = IX_REG_LENGTH; tempItr > 0; tempItr--, itr++)
            if (toSet.getIxRegCode().getBits()[tempItr - 1] != false)
                intToSet += (Math.pow(2, itr));
        
        for (int tempItr = OPCODE_LENGTH; tempItr > 0; tempItr--, itr++)
            if (toSet.getOpCode().getBits()[tempItr - 1] != false)
                intToSet += (Math.pow(2, itr));     
        
        Word tempWord = new Word(intToSet);
        return tempWord;
    }    

   /**
    * loadROM
    * 
    * Method will load ROM and its instructions into memory, and then 
    * point the PC at the first address of the ROM, and execute the instruction
    * at that address.
    **************************************************************************/
    private void loadROM()
    {
        // Load ROM contents into memory...
        ROMLoader tempLoader = new ROMLoader(simComputer.getCPU());
    }
}
