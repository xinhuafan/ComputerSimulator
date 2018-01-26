/**
 *@Author Jeremy Case
 *George Washington University
 *CSCI 6461:  Computer Architecture
 *****************************************************************************/

package CPU;


import CPU.OpCode.*;
import CPU.OpCode.WordInstructions;
import CPU.Registers.*;
import Memory.*;
import Devices.*;
import GUI.SimulatedComputerGUI.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*CPU Class
* 
*This class represents the CPU of the simulated computer.
*******************************************************************************/

public class CPU
{
    // for ensuring memory is within bounds...
    private static final int MAXWORDS = 2048;
    private boolean hlt_flag;
    // Definitions of bit sizes for sections of word instructions
    private final int WORD_BITS = 16;
    private final int OPCODE_LENGTH = 6;
    private final int IX_REG_LENGTH = 2;
    private final int GPR_LENGTH = 2;
    private final int IA_BIT_LENGTH = 1;
    private final int ADDRESS_LENGTH = 5;
    
    // Definitions of Device IDs (DEVID)
    private final int CONSOLE_KEYBOARD_ID = 0;
    private final int CONSOLE_PRINTER_ID = 1;
    private final int CONSOLE_CARD_READER_ID = 2;
    
    // Registers
    public IndexRegister simulatedIXReg1 = new IndexRegister();  // X1, 12 bits 
    public IndexRegister simulatedIXReg2 = new IndexRegister();  // X2, 12 bits
    public IndexRegister simulatedIXReg3 = new IndexRegister();  // X3, 12 bits
    public GeneralRegister simulatedGPR0 = new GeneralRegister();  // R0, 16 bits
    public GeneralRegister simulatedGPR1 = new GeneralRegister();  // R1, 16 bits
    public GeneralRegister simulatedGPR2 = new GeneralRegister();  // R2, 16 bits
    public GeneralRegister simulatedGPR3 = new GeneralRegister();  // R3, 16 bits
    public ProgramCounterRegister simulatedPC = new ProgramCounterRegister();  // 12 bits
    public ConditionCodeRegister simulatedCC = new ConditionCodeRegister(); // 4 bits
    public InstructionRegister simulatedIR = new InstructionRegister();  // 16 bits
    public MachineStatusRegister simulatedMSR = new MachineStatusRegister(); // 16 bits
    public MachineFaultRegister simulatedMFR = new MachineFaultRegister(); // 4 bits    
    public MemoryAddressRegister simulatedMAR = new MemoryAddressRegister(); // 16 bits
    public MemoryBufferRegister simulatedMBR = new MemoryBufferRegister(); // 16 bits
    
    // Devices
    public ConsoleKeyboard keyboard = new ConsoleKeyboard();
    public ConsolePrinter printer = new ConsolePrinter();
    public ConsoleCardReader cardReader = new ConsoleCardReader();
    
    // Definition for Machine Fault Events
    private final int ILLEGAL_MEM_ADDR_TO_RESERVED_LOC = 1;   //Illegal Memory Address to Reserved Locations
    private final int ILLEGAL_TRAP_CODE = 2;   //Illegal TRAP code
    private final int ILLEGAL_OPERATION_CODE = 3;   //Illegal Operation Code
    private final int MEM_LOC_OVERFLOW = 4;   //Illegal Memory Address beyond 2048 (memory installed)    
    
    // This is only for the GUI!!! Nothing else should be grabbing from this
    Register[] registerArray = {simulatedIXReg1, 
                                simulatedIXReg2, 
                                simulatedIXReg3, 
                                simulatedGPR0, 
                                simulatedGPR1, 
                                simulatedGPR2, 
                                simulatedGPR3, 
                                simulatedPC, 
                                simulatedCC,
                                simulatedIR,
                                simulatedMSR,
                                simulatedMFR,
                                simulatedMAR,
                                simulatedMBR};
    
    
    /**************************************************************************
     * This is the memory of the machine.  Normally, the CPU wouldn't have 
     * direct access to the memory, but this is for simplicity sake...
     *************************************************************************/
    
    private Cache simCache = new Cache();
    
    public CPU()
    {
        // Constructor stuff...
        hlt_flag = false;
    }
    
    /****************************** Getters *********************************/    
      
    public boolean getHlt_flag(){
        return hlt_flag;
    }
 
    /****************************** Getters *********************************/    
    public void setHlt_flag(){
        hlt_flag = false;
    } 
        
    /****************************** Getters *********************************/    
    public Register[] getRegisters() {return registerArray;}
    
    /******************************* Setters ********************************/
    private List<Integer> opCodeList(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(10);
        list.add(11);
        list.add(12);
        list.add(13);
        list.add(14);
        list.add(16);
        list.add(17);
        list.add(41);
        list.add(42);
        return list;
    }    

    /**************************************************************************
     * This is a list of predefined opcodes.  These are integer representations
     * of octal values.
     *************************************************************************/    
    private final int HLT = 0; // Stops the machine.
    private final int LDR = 1; // LDR r, x, address[,I]
    private final int STR = 2; // STR r, x, address[,I]
    private final int LDA = 3; // LDA r, x, address[,I]
    private final int AMR = 4; // AMR r, x, address[,I]
    private final int SMR = 5; // SMR r, x, address[,I]
    private final int AIR = 6; // AIR r, immed
    private final int SIR = 7; // SIR r, immed
    private final int JZ = 10;  // JZ r, x, address[,I]
    private final int JNE = 11; // JNE r, x, address[,I]
    private final int JCC = 12; // JCC cc, x, address[,I]
    private final int JMA = 13; // JMA x, address[,I]
    private final int JSR = 14; // JSR x, address[,I]
    private final int RFS = 15; // RFS Immed
    private final int SOB = 16; // SOB r, x, address[,I]    
    private final int JGE = 17; // JGE r,x, address[,I]
    private final int MLT = 20; // MLT rx,ry
    private final int DVD = 21; // DVD rx,ry
    private final int TRR = 22; // TRR rx, ry
    private final int AND = 23; // AND rx, ry
    private final int ORR = 24; // ORR rx, ry
    private final int NOT = 25; // NOT rx
    private final int TRAP = 30; // TRAP code
    private final int SRC = 31; // SRC r, count, L/R, A/L
    private final int RRC = 32; // RRC r, count, L/R, A/L
    private final int FADD = 33; // FADD fr, x, address[,I]
    private final int FSUB = 34; // FSUB fr, x, address[,I]    
    private final int VADD = 35; // VADD fr, x, address[,I]
    private final int VSUB = 36; // VSUB fr, x, address[,I]
    private final int CNVRT = 37; // CNVRT r, x, address[,I]
    private final int LDX = 41; // LDX x, address[,I]    
    private final int STX = 42; // STX x, address[,I]
    private final int LDFR = 50; // LDFR fr, x, address [,i]
    private final int STFR = 51; // STFR fr, x, address [,i]
    private final int IN = 61; // IN r, devid
    private final int OUT = 62; // OUT r, devid    
    private final int CHK = 63; // CHK r, devid
    
   /**
    *ALULogic 
    * 
    *Instruction Set Architecture (ISA)
    * 
    *@param instruction - the instruction to be parsed and executed upon 
    * 
    *This is a switch statement that represents the ALU's decision-making.  
    *Depending on the instruction passed in, one of several events can occur.
    *Each event is described below.
    ***************************************************************************/    
    public void ALULogic(WordInstructions instruction)
    {
        if (hlt_flag){
            return;
        }
        // judge condition for illegal trap code
        int trap_code = getGPR(instruction).getDecimalValue();
        if ( instruction.getInstructionOpCodeValue() == 30 && (trap_code < 0 || trap_code > 15)){
            illegalTrapCode();
            return;
        }
        // judge condition for illegal memory address
        if (getEffectiveAddress(instruction) > 2048){
            illegalMemAddress();
            return;
          }else if(getEffectiveAddress(instruction) >= 0 && getEffectiveAddress(instruction) <= 5 && opCodeList().contains(instruction.getInstructionOpCodeValue())){
            illegalMemAddrToReservedLoc();
            return;
        }
        
         // calculate the word's opcode value in octal and act accordingly...
        switch (instruction.getInstructionOpCodeValue())
        {
                            // Octal Values
            case (HLT) :    // O00
            {
                hlt_flag = true;
            }
            break;
            /**
             * TRAP
             * 
             * There is a table containing 16 routines in the memory, the format is:
             * memory location      routine ID(trap code)
             * 10                    0
             * 11                    1
             * ...                   ...
             * 25                    15
             * steps
             * 1) get memory location for the trap code
             * 2) store the memory address to memory location 0
             * 3) store trap code to MFR
             * 4) store current PC value to memory location 2
             * 5) store content of memory location 0 to PC
             * 6) execute relevant routine for trap code - set MSR with 100 + trap code
             * 7) recover PC value from memory location 2
             */
                            // Octal Values
            case (TRAP) :   // O30
            {
                
                simCache.setWordAtAddress(0, simCache.getMemory().getAddressByWord(new Word(trap_code)));
                simulatedMFR.setValue(simCache.getWordAtAddress(simCache.getWordAtAddress(0).getDecimalValue()));
                simulatedMBR.setValue(simulatedPC.getDecimalValue());
                simCache.setWordAtAddress(2, simulatedMBR.getDecimalValue());                
                simulatedPC.setValue(simCache.getWordAtAddress(0));
                executeRoutine();
                simulatedPC.setValue(simCache.getWordAtAddress(2));
            }
            break;
            /**
             *LDR
             *
             *Method will load a general register with a value from 
             * an address in 3 steps.  
             * 
             * 1) Load MAR with the EA
             * 2) Load MBR with the word at the EA
             * 3) set GPR to that word
             ******************************************************************/    
            case (LDR) :    // 001
            {
                simulatedMAR.setValue(getEffectiveAddress(instruction));
                simulatedMBR.setValue(simCache.getWordAtAddress(simulatedMAR.getDecimalValue()));
                getGPR(instruction).setValue(simulatedMBR.getDecimalValue());
            }
            break;
                
            /**
             *STR
             *
             *Method will store a memory location with the value from a register  
             *in 2 steps 
             * 
             * 1) Set MBR with the value from the register
             * 2) Set the memory value with the word in the MBR
             ******************************************************************/      
            case (STR) :    // 002
            {
                simulatedMBR.setValue(getGPR(instruction).getDecimalValue());
                simCache.setWordAtAddress(getEffectiveAddress(instruction), simulatedMBR.getDecimalValue());
            }
            break;
                
            /**
             *LDA
             *
             *Method will store a memory address into a register
             * 
             * 1) Set register's value to the memory address
             ******************************************************************/    
            case (LDA) :    // 003
            {
                getGPR(instruction).setValue(getEffectiveAddress(instruction));
            }
            break;
                
            /**
             *AMR
             *
             *Method will take a value in memory and add it to a register 
             * 
             * 1) Set the MAR to the address to fetched
             * 2) set the MBR to the value of the address in the MAR
             * 3) add to the referenced register the word value in the MBR
             ******************************************************************/       
            case (AMR) :    // 004
            {
                simulatedMAR.setValue(getEffectiveAddress(instruction));
                simulatedMBR.setValue(simCache.getWordAtAddress(simulatedMAR.getDecimalValue()));
                getGPR(instruction).addRegisterValue(simulatedMBR.getDecimalValue());
            }
            break;   
            
            /**
             *SMR
             *
             *Method will take a value in memory and subtract it from a register 
             * 
             * 1) Set the MAR to the address to fetched
             * 2) set the MBR to the value of the address in the MAR
             * 3) subtract word value from the referenced register 
             ******************************************************************/     
            case (SMR) :    // 005
            {
                simulatedMAR.setValue(getEffectiveAddress(instruction));
                simulatedMBR.setValue(simCache.getWordAtAddress(simulatedMAR.getDecimalValue()));
                getGPR(instruction).subRegisterValue(simulatedMBR);
            }
            break;
            
            /**
             *AIR
             *
             *Method will add to a register the address value of the instruction
             * 
             * 1) Add address value to the referenced register
             ******************************************************************/      
            case (AIR) :    // 006
            {
                getGPR(instruction).addRegisterValue(instruction.getAddressCode().getDecimalValue());
            }
            break;   
            
            /**
             *SIR
             *
             *Method will subtract from a register the address value
             * 
             * 1) Subtract address value from the referenced register
             ******************************************************************/     
            case (SIR) :    // 007
            {
                getGPR(instruction).subRegisterValue(instruction.getAddressCode().getDecimalValue());
            }
            break;
            
            /**
             *JZ
             *
             *Method will jump to a new address if the register's contents are
             *set to 0
             * 
             * 1) If referenced register is empty, set PC to EA
             * 2) else increment PC by 1
             ******************************************************************/     
            case (JZ) :     // 010
            {
                if (getGPR(instruction).getDecimalValue() == 0)
                    simulatedPC.setValue(getEffectiveAddress(instruction));
                else
                    simulatedPC.setValue(simulatedPC.getDecimalValue() + 1);
            }
            break;  
                
            /**
             *JNE
             *
             *Method will jump to a new address if the register's contents are
             *not set to 0
             * 
             * 1) If referenced register != 0, set PC to EA
             * 2) else increment PC by 1
             ******************************************************************/       
            case (JNE) :    // 011
            {
                if (getGPR(instruction).getDecimalValue() != 0)
                    simulatedPC.setValue(getEffectiveAddress(instruction));
                else
                    simulatedPC.setValue(simulatedPC.getDecimalValue() + 1);
            }
            break;   
             
            /**
             *JCC
             *
             *Method will jump to a new address if the condition code register's
             *bit referenced by this instruction is set
             * 
             * 1) If referenced instruction, set PC to EA
             * 2) else increment PC by 1
             ******************************************************************/         
            case (JCC) :    // 012
            {
                boolean[] conditionArray = simulatedCC.getBits();
                
                int bit = getGPR(instruction).getDecimalValue();
                if (conditionArray[bit])
                    simulatedPC.setValue(getEffectiveAddress(instruction));
                else
                    simulatedPC.setValue(simulatedPC.getDecimalValue() + 1);
            }
            break;    

            /**
             *JMA
             * 
             *Method will do an unconditional jump to an address 
             ******************************************************************/      
            case (JMA) :    // 013
            {   
                simulatedPC.setValue(getEffectiveAddress(instruction));
            }
            break;   

            /**
             *JSR
             *
             *Method will jump to an address, and continually execute 
             *instructions starting at that address until a -17777 value is met
             * 
             *Method will store the PC's original address + 1, and the starting
             *address of the list of arguments in registers.
             * 
             *see executeJSR() method for more details
             ******************************************************************/       
            case (JSR) :    // 014
            {
                simulatedGPR3.setValue(simulatedPC.getDecimalValue() + 1);
                simulatedGPR0.setValue(getEffectiveAddress(instruction));
                
                if (instruction.getIABit().isBitSet())
                    simulatedPC.setValue(simCache.getWordAtAddress(getEffectiveAddress(instruction)));
                else
                    simulatedPC.setValue(getEffectiveAddress(instruction));
                
                executeJSR();
            }
            break;   
                
            /**
             *RFS
             *
             *Method will set the Program Counter to the value in General 
             *Register 3.  This occurs after optionally setting General Register
             *0 to the immediate address value of the instruction
             * 
             *Method will store the PC's original address + 1, and the starting
             *address of the list of arguments in registers.
             ******************************************************************/  
            case (RFS) :    // 015
            {
                if (getEffectiveAddress(instruction) <= 32 )
                    simulatedGPR0.setValue(getEffectiveAddress(instruction));
                
                simulatedPC.setValue(simulatedGPR3.getDecimalValue());
            }
            break;   
            
            /**
             *SOB
             *
             *Method will subtract one from the referenced register, and then
             *set the program counter to the effective address of the
             *instruction as long as general register 0 is not refenced, else
             *it will increment the Program Counter by 1
             ******************************************************************/      
            case (SOB) :    // 016
            {
                int temp = getGPR(instruction).getDecimalValue() - 1;
                getGPR(instruction).setValue(temp);
                
                if (getGPR(instruction).getDecimalValue() > 0)
                    simulatedPC.setValue(getEffectiveAddress(instruction));
                else
                    simulatedPC.setValue(simulatedPC.getDecimalValue() + 1);
            }
            break;   
            
            /**
             *JGE
             *
             *Method will set the program counter to the value referenced by
             *the instruction if the referenced general register's value is 
             *greater than zero.  Otherwise, it will increment the program
             *counter.
             ******************************************************************/          
            case (JGE) :    // 017
            {
                if (getGPR(instruction).getDecimalValue() >= 0)
                    simulatedPC.setValue(getEffectiveAddress(instruction));
                else
                    simulatedPC.setValue(simulatedPC.getDecimalValue() + 1);
            }
            break;   
                
           /**
            *MLT
            *
            *Method will multiply two registers, setting the first register's
            *value wthe "leftover" value after setting register+1 to the low
            *order bits. EG, it is possible to have register+1 set to a value
            *while the GPR referred to by the insruction gets set to 0.
            * 
            * This works by utilizing the IXRegister part of the instruction to
            * determine what register to use to multiply the first register.
            * In case of overflow, the method will set the CC register, and 
            * the generalRegister + 1 will retain the high order bits.  For this
            * reason, the method will only allow general registers 0 and 2 to 
            * be multiplied, so that general register 1 and 3 may hold the 
            * overflow bits.
            ******************************************************************/   
            case (MLT) :    // 020
            {
                if ((instruction.getRegCode().getDecimalValue() == 0   ||
                     instruction.getRegCode().getDecimalValue() == 2)  &&
                    (instruction.getIxRegCode().getDecimalValue() == 0 ||
                     instruction.getIxRegCode().getDecimalValue() == 2))
                {
                    if (instruction.getRegCode().getDecimalValue() == 0 && 
                        instruction.getIxRegCode().getDecimalValue() == 0)
                    {
                        simulatedGPR0.multiplyRegisterValue(simulatedGPR0, simulatedGPR1, simulatedCC);
                    }
                    
                    else if (instruction.getRegCode().getDecimalValue() == 0 && 
                             instruction.getIxRegCode().getDecimalValue() == 2)
                    {
                        simulatedGPR0.multiplyRegisterValue(simulatedGPR2, simulatedGPR1, simulatedCC);
                    }
                    
                    else if (instruction.getRegCode().getDecimalValue() == 2 && 
                             instruction.getIxRegCode().getDecimalValue() == 0)
                    {
                        simulatedGPR2.multiplyRegisterValue(simulatedGPR0, simulatedGPR3, simulatedCC);
                    }
                    
                    else if (instruction.getRegCode().getDecimalValue() == 2 && 
                             instruction.getIxRegCode().getDecimalValue() == 2)
                    {
                        simulatedGPR2.multiplyRegisterValue(simulatedGPR2, simulatedGPR3, simulatedCC);
                    }
                }
            }
            break;   
                
           /**
            *DVD
            *
            *Method will divide two registers.
            * 
            * This works by utilizing the IXRegister part of the instruction to
            * determine what register to use to divide the first register.
            * In case of a divide by zero, the condition code register will be 
            * set.  One register (the one referred to by the Register part of 
            * of the instruction) will receive the quotient, while the one 
            * referred to by the IXRegister part of the instruction will get the
            * remainder.
            ******************************************************************/   
            case (DVD) :    // 021
            {
                if ((instruction.getRegCode().getDecimalValue() == 0   ||
                     instruction.getRegCode().getDecimalValue() == 2)  &&
                    (instruction.getIxRegCode().getDecimalValue() == 0 ||
                     instruction.getIxRegCode().getDecimalValue() == 2))
                {
                    if (instruction.getRegCode().getDecimalValue() == 0 && 
                        instruction.getIxRegCode().getDecimalValue() == 0)
                    {
                        simulatedGPR0.divideRegisterValue(simulatedGPR0, simulatedGPR1, simulatedCC);
                    }
                    
                    else if (instruction.getRegCode().getDecimalValue() == 0 && 
                             instruction.getIxRegCode().getDecimalValue() == 2)
                    {
                        simulatedGPR0.divideRegisterValue(simulatedGPR2, simulatedGPR1, simulatedCC);
                    }
                    
                    else if (instruction.getRegCode().getDecimalValue() == 2 && 
                             instruction.getIxRegCode().getDecimalValue() == 0)
                    {
                        simulatedGPR2.divideRegisterValue(simulatedGPR0, simulatedGPR3, simulatedCC);
                    }
                    
                    else if (instruction.getRegCode().getDecimalValue() == 2 && 
                             instruction.getIxRegCode().getDecimalValue() == 2)
                    {
                        simulatedGPR2.divideRegisterValue(simulatedGPR2, simulatedGPR3, simulatedCC);
                    }
                }
            }
            break;   
                
            /**
             *TRR
             *
             *Method will test for equality amongst two registers, and flag the
             *condition code register accordingly
             ******************************************************************/        
            case (TRR) :    // 022
            {
                if (getGPR(instruction).areRegistersEqual(getGPRViaIXReg(instruction)))
                    simulatedCC.setCondition(ConditionCodeRegister.EQUALORNOT);
                else
                    simulatedCC.setCondition(ConditionCodeRegister.NONE);
            }
            break;   
            
                            
            /**
             *AND
             *
             *Method will do a logical AND test on both registers, and set a 
             *register to true/false based on that
             ******************************************************************/         
            case (AND) :    // 023
            {
                for (int itr = 0; itr < getGPR(instruction).getLength(); itr++)
                {
                    if (getGPR(instruction).getBits()[itr] &&
                        getGPRViaIXReg(instruction).getBits()[itr])
                    {
                        getGPR(instruction).getBits()[itr] = true;
                    }

                    else
                        getGPR(instruction).getBits()[itr] = false;
                 
                }
            }
            break;
             
            /**
             *ORR
             *
             *Method will do a logical OR test on both registers
             ******************************************************************/     
            case (ORR) :    // 024
            {
                for (int itr = 0; itr < getGPR(instruction).getLength(); itr++)
                {
                    if (getGPR(instruction).getBits()[itr] ||
                        getGPRViaIXReg(instruction).getBits()[itr])
                    {
                        getGPR(instruction).getBits()[itr] = true;
                    }

                    else
                        getGPR(instruction).getBits()[itr] = false;
                }
            }
            break;   
            
            /**
             *NOT
             *
             *Method will do a logical NOT test on a register and flip the bits
             ******************************************************************/         
            case (NOT) :    // 025
            {
                getGPR(instruction).flipBits();
            }
            break;
            
            /**
             *SRC
             *
             *Method shift a register by a number of times determined by the 
             *instruction.  Furthermore, the instruction will dictate what type
             *of shift, and what direction.
             ******************************************************************/                
            case (SRC) :    // 031
            {
                int shiftCount = instruction.getAddressCode().getDecimalValue();
                if (shiftCount > 15)
                    shiftCount = 15;
                
                if (instruction.getIxRegCode().getShiftDirection() != false)
                {
                    if (instruction.getIxRegCode().getShiftType() != false)
                    {
                        for (; shiftCount > 0; shiftCount--)
                            getGPR(instruction).logicalShiftBitsLeft();
                    }
                    
                    else
                    {
                        for (; shiftCount > 0; shiftCount--)
                            getGPR(instruction).arithmeticShiftBitsLeft();
                    }
                }
                
                else
                {
                    if (instruction.getIxRegCode().getShiftType() != false)
                    {
                        for (; shiftCount > 0; shiftCount--)
                            getGPR(instruction).logicalShiftBitsRight();
                    }
                    
                    else
                    {
                        for (; shiftCount > 0; shiftCount--)
                            getGPR(instruction).arithmeticShiftBitsRight();
                    }
                }
            }
            break;
              
            /**
             *RRC
             *
             *Method will rotate a register by a count determined by the 
             *instruction.  The instruction will also determine what 
             *direction.
             ******************************************************************/      
            case (RRC) :    // 032
            {
                int shiftCount = instruction.getAddressCode().getDecimalValue();
                if (shiftCount > 15)
                    shiftCount = 15;
                
                if (instruction.getIxRegCode().getShiftDirection() != false)
                {
                    for (; shiftCount > 0; shiftCount--)
                        getGPR(instruction).rotateBitsLeft();
                }
                
                else
                {
                    for (; shiftCount > 0; shiftCount--)
                        getGPR(instruction).rotateBitsRight();
                }
                
            }
            break;    
            
            case (FADD) :   // 033
            {

            }
            break;    
                
            case (FSUB) :   // 034
            {

            }
            break;    
                
            case (VADD) :   // 035
            {

            }
            break;    
                
            case (VSUB) :   // 036
            {

            }
            break;    
                
            case (CNVRT) :  // 037
            {

            }
            break;    
            
           /**
            *Method will load an index register with a value from 
            * an address in 2 steps.  
            * 
            * 1) Load MAR with the EA
            * 2) Load MBR with MAR value
            * 3) set IXReg to that word
            ******************************************************************/    
            case (LDX) :    // 041
            {
                // Ignore instruction if it doesnt reference an index reg
                if (instruction.getRegCode().getDecimalValue() > 0)
                {
                    simulatedMAR.setValue(getEffectiveAddress(instruction));
                    simulatedMBR.setValue(simCache.getWordAtAddress(simulatedMAR.getDecimalValue()));
                    
                    if (instruction.getRegCode().getDecimalValue() == 1)
                        simulatedIXReg1.setValue(simulatedMBR.getDecimalValue());
                    else if (instruction.getRegCode().getDecimalValue() == 2)
                        simulatedIXReg2.setValue(simulatedMBR.getDecimalValue());
                    else if (instruction.getRegCode().getDecimalValue() == 3)
                        simulatedIXReg3.setValue(simulatedMBR.getDecimalValue());
                }
            }
            break;    
                
            /**
             *STX
             *
             *Method will store a memory location with the value from an index 
             *register in 2 steps
             * 
             * 1) Set MBR with the value from the register
             * 2) Set the memory value with the word in the MBR
             ******************************************************************/          
            case (STX) :    // 042
            {
                // Ignore instruction if it doesnt reference an index reg
                if (instruction.getIxRegCode().getDecimalValue() > 0)
                {
                    simulatedMBR.setValue(getIXReg(instruction).getDecimalValue());
                    simCache.setWordAtAddress(getEffectiveAddress(instruction), simulatedMBR.getDecimalValue());
                }
            }
            break;    

            case (LDFR) :   // 050
            {

            }
            break;    
            
            case (STFR) :   // 051
            {
                
            }
            break;    
            
           /**
            *IN
            *
            *Method will take a value from a device referenced by the 
            *instruction, and set a register value to that device's value
            ******************************************************************/
            case (IN) :     // 061
            {
                switch(getEffectiveAddress(instruction))
                {
                    case (CONSOLE_KEYBOARD_ID) :
                    {
                        getGPR(instruction).setValue(keyboard.getLastCharAsIntValue());
                    }
                    break;
                        
                    case (CONSOLE_CARD_READER_ID) :
                    {
                        getGPR(instruction).setValue(cardReader.getLastCharAsIntValue());
                    }
                    break;
                }
            }
            break;  
            
           /**
            *OUT
            *
            *Method will take a value from a register referenced by the 
            *instruction, and set a device to that register's value
            ******************************************************************/    
            case (OUT) :    // 062
            {
                switch (getEffectiveAddress(instruction))
                {       
                    case (CONSOLE_PRINTER_ID) :
                    {
                          printer.setValueViaInt(getGPR(instruction).getDecimalValue());
                    }
                    break;                        
                }
            }
            break;   
            
            case (CHK) :    // 063
            {

            }
            break;    
                
            default :       // All other octal values (machine faults)
            {                
                simCache.setWordAtAddress(1, simCache.getMemory().getAddressByWord(new Word(ILLEGAL_OPERATION_CODE)));
                simulatedMFR.setValue(simCache.getWordAtAddress(simCache.getWordAtAddress(1).getDecimalValue()));
                simulatedMBR.setValue(simulatedPC.getDecimalValue());
                simCache.setWordAtAddress(4, simulatedMBR.getDecimalValue());                
                simulatedPC.setValue(simCache.getWordAtAddress(1));
                executeRoutine();
                simulatedPC.setValue(simCache.getWordAtAddress(4));                                
            }
            break;
        }
    }
    
    
   /**
    *getEffectiveAddress
    * 
    *Instruction Set Architecture (ISA)
    * 
    *@return int - returns the effective address (EA) of the instruction 
    * 
    *@param toGet - the instruction to be parsed and executed upon
    * 
    *This is a helper function for ALULogic()
    ***************************************************************************/     
    private int getEffectiveAddress(WordInstructions toGet)
    {
        // get immediate value from the instruction...
        int effectiveAddress = toGet.getAddressCode().getDecimalValue();
        
        // ...and if indirect addressing is set, get that value...
        if (toGet.getIABit().isBitSet() && 
            toGet.getIxRegCode().getDecimalValue() > 0)
        {
            switch (toGet.getIxRegCode().getDecimalValue())
            {
                case (1) :  // IX register 1
                {
                    effectiveAddress += simulatedIXReg1.getDecimalValue();
                }
                break;
                    
                case (2) :  // IX register 2
                {
                    effectiveAddress += simulatedIXReg2.getDecimalValue();
                }
                break;
                    
                case (3) :  // IX register 3
                {
                    effectiveAddress += simulatedIXReg3.getDecimalValue();
                }
                break;
                    
                default :
                {
                    // output error
                }
                break;
            }
        }
        
        return effectiveAddress;
    }
    
   /**
    *getGPRToSet
    * 
    *Instruction Set Architecture (ISA)
    * 
    *@return GeneralRegister - returns the GPR referenced by the instruction
    * 
    *@param instruction - the instruction to be parsed and executed upon
    ***************************************************************************/ 
    private GeneralRegister getGPR(WordInstructions instruction)
    {
        switch (instruction.getRegCode().getDecimalValue())
        {
            case (0) : // GPR 0
            {
                return simulatedGPR0;
            }
                
            case (1) : // GPR 1
            {
                return simulatedGPR1;
            }

            case (2) : // GPR 2
            {
                return simulatedGPR2;
            }
                
            case (3) : // GPR 3
            {
                return simulatedGPR3;
            }  
                
            default :  // shouldnt reach here...
            {
                return simulatedGPR0;
            }
        }
    }
    
/**
    *getGPRViaIXReg
    * 
    *Instruction Set Architecture (ISA)
    * 
    *@return GeneralRegister - returns the GPR referenced by the instruction
    * 
    *@param instruction - the instruction to be parsed and executed upon
    * 
    *This method is for instructions that utilize the IXRegister part of an
    *instruction to reference a general register
    ***************************************************************************/ 
    private GeneralRegister getGPRViaIXReg(WordInstructions instruction)
    {
        switch (instruction.getIxRegCode().getDecimalValue())
        {
            case (0) : // GPR 0
            {
                return simulatedGPR0;
            }
                
            case (1) : // GPR 1
            {
                return simulatedGPR1;
            }

            case (2) : // GPR 2
            {
                return simulatedGPR2;
            }
                
            case (3) : // GPR 3
            {
                return simulatedGPR3;
            }  
                
            default :  // shouldnt reach here...
            {
                return simulatedGPR0;
            }
        }
    }
    
    public Memory getCacheMemory()
    {
        return simCache.getMemory();
    }
       
   /**
    *getIXRegToSet
    * 
    *Instruction Set Architecture (ISA)
    * 
    *@return IndexRegister - returns the IXReg referenced by the instruction
    * 
    *@param instruction - the instruction to be parsed and executed upon
    ***************************************************************************/     
    private IndexRegister getIXReg(WordInstructions instruction)
    {
        switch (instruction.getIxRegCode().getDecimalValue())
        {
            case (1) : // IX1
            {
                return simulatedIXReg1;
            }
                
            case (2) : // IX2
            {
                return simulatedIXReg2;
            }

            case (3) : // IX3
            {
                return simulatedIXReg3;
            }
                
            default :  // shouldnt reach here...
            {
                return simulatedIXReg1;
            }
        }
    }    
    
   /**
    *executeJSR
    * 
    *Instruction Set Architecture (ISA)
    * 
    *Method will execute the instruction pointed to by the PC, and continue to 
    *feed values to the PC and IR registers until a value of -17777 is reached.
    ***************************************************************************/         
    private void executeJSR()
    {
        // Execute a series of instructions in memory until an empty memory
        // location is encountered.
        while (simCache.getWordAtAddress(simulatedPC.getDecimalValue()).getDecimalValue() != -17777)
        {
            // set Instruction register to value referenced by Program Counter...
            simulatedIR.setValue(simCache.getWordAtAddress(simulatedPC.getDecimalValue()));
            
            // ...convert from binary to a word instruction...
            Word tempWord = new Word();
            tempWord.setValue(simulatedIR.getDecimalValue());
            
            // ...allow the ALU to act upon the instruction...
            ALULogic(convertWordToInstruction(tempWord));
            
            // ...Now set the PC to the next location in memory...
            simulatedPC.setValue(simulatedPC.getDecimalValue() + 1);
        }
    }   
    
   /**
    *convertWordToInstruction
    * 
    *Instruction Set Architecture (ISA)
    * 
    *Method will word a 16 bit word to a 16 bit word instruction with its 5
    *separate classes
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
    
    public Word getWordAtAddress(final int addressToGet) 
    {
        // first check the cache
        return simCache.getWordAtAddress(addressToGet);        
    }
    
    public void setConditionCounter(final int param){
        this.simulatedCC.setCondition(param);
    }
    
    // update MSR with 100 + trap code
    private void executeRoutine() {
        switch(simCache.getWordAtAddress(simulatedPC.getDecimalValue()).getDecimalValue()){
            case 0:
                simulatedMSR.setValue(100);
                break;
            case 1:
                simulatedMSR.setValue(101);
                break;
            case 2:
                simulatedMSR.setValue(102);
                break;
            case 3:
                simulatedMSR.setValue(103);
                break;
            case 4:
                simulatedMSR.setValue(104);
                break;
            case 5:
                simulatedMSR.setValue(105);
                break;
             case 6:
                simulatedMSR.setValue(106);
                break;
            case 7:
                simulatedMSR.setValue(107);
                break;  
            case 8:
                simulatedMSR.setValue(108);
                break;
            case 9:
                simulatedMSR.setValue(109);
                break;
            case 10:
                simulatedMSR.setValue(110);
                break;
            case 11:
                simulatedMSR.setValue(111);
                break;
            case 12:
                simulatedMSR.setValue(112);
                break;
            case 13:
                simulatedMSR.setValue(113);
                break;
             case 14:
                simulatedMSR.setValue(114);
                break;
            case 15:
                simulatedMSR.setValue(115);
                break;   
        }
    }

    private void illegalTrapCode() {
        simCache.setWordAtAddress(1, simCache.getMemory().getAddressByWord(new Word(ILLEGAL_TRAP_CODE)));
        simulatedMFR.setValue(simCache.getWordAtAddress(simCache.getWordAtAddress(1).getDecimalValue()));
        simulatedMBR.setValue(simulatedPC.getDecimalValue());
        simCache.setWordAtAddress(4, simulatedMBR.getDecimalValue());
        simulatedPC.setValue(simCache.getWordAtAddress(1));
        executeRoutine();
        simulatedPC.setValue(simCache.getWordAtAddress(4)); 
    }    

    private void illegalMemAddress() {
        simCache.setWordAtAddress(1, simCache.getMemory().getAddressByWord(new Word(MEM_LOC_OVERFLOW)));
        simulatedMFR.setValue(simCache.getWordAtAddress(simCache.getWordAtAddress(1).getDecimalValue()));
        simulatedMBR.setValue(simulatedPC.getDecimalValue());
        simCache.setWordAtAddress(4, simulatedMBR.getDecimalValue());
        simulatedPC.setValue(simCache.getWordAtAddress(1));
        executeRoutine();
        simulatedPC.setValue(simCache.getWordAtAddress(4));                 
    }

    private void illegalMemAddrToReservedLoc() {
        simCache.setWordAtAddress(1, simCache.getMemory().getAddressByWord(new Word(ILLEGAL_MEM_ADDR_TO_RESERVED_LOC)));
        simulatedMFR.setValue(simCache.getWordAtAddress(simCache.getWordAtAddress(1).getDecimalValue()));
        simulatedMBR.setValue(simulatedPC.getDecimalValue());
        simCache.setWordAtAddress(4, simulatedMBR.getDecimalValue());
        simulatedPC.setValue(simCache.getWordAtAddress(1));
        executeRoutine();
        simulatedPC.setValue(simCache.getWordAtAddress(4));        
    }
}