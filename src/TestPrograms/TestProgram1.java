/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package TestPrograms;

import CPU.*;
import CPU.OpCode.*;
import Devices.ASCiiTable;

/**
*TestProgram1
*
*This class represents a program that will ask a user for 21 inputs.  It will
*match the 21st input against the first 20 to determine which of them is the
*nearest number to the 21st number, and then output that.
*******************************************************************************/

public class TestProgram1
{
    private ASCiiTable converter = new ASCiiTable();
    
    // List of predefined instructions for this program
    private WordInstructions address100 = new WordInstructions();                   // load location 100 with value 0
    private WordInstructions address101 = new WordInstructions();                   // load location 101 with value 1
    private WordInstructions address102 = new WordInstructions();                   // load location 102 with value 2
    private WordInstructions address103 = new WordInstructions();                   // load location 103 with value 3
    private WordInstructions address104 = new WordInstructions();                   // load location 104 with value 4
    private WordInstructions address105 = new WordInstructions();                   // load location 105 with value 5
    private WordInstructions address106 = new WordInstructions();                   // load location 106 with value 6
    private WordInstructions address107 = new WordInstructions();                   // load location 107 with value 7
    private WordInstructions address108 = new WordInstructions();                   // load location 108 with value 8
    private WordInstructions address109 = new WordInstructions();                   // load location 109 with value 9
    private WordInstructions incrementGPR2 = new WordInstructions();                // Increment GPR2 by 1
    private WordInstructions incrementGPR1 = new WordInstructions();                // Increment GPR1 by 1
    private WordInstructions loadGPR0WithChar = new WordInstructions();             // Load GPR0 with first char of keyboard buffer
    private WordInstructions loadGPR1WithItr = new WordInstructions();              // Load GPR1 with a user-input value
    private WordInstructions strGPR1ValueToIXReg3Address = new WordInstructions();  // Store GPR3 into memory
    private WordInstructions loadGPR0WithChar21 = new WordInstructions();           // Load GPR0 with last user input
    private WordInstructions loadGPR1WithChar21 = new WordInstructions();           // Load GPR1 with last user input
    private WordInstructions strGPR3ToIXReg1 = new WordInstructions();              // store GPR3 to memory
    private WordInstructions subGPR1WithItr = new WordInstructions();               // subtract memory location value from GPR1
    private WordInstructions loadGPR2WithItr = new WordInstructions();              // Load GPR2 with memory location's value
    private WordInstructions addMemToGPR3 = new WordInstructions();                 // Add IXReg3 memory to GPR1
    private WordInstructions strGPR3ToIXReg1Again = new WordInstructions();         // Place GPR1 to memory referenced by IXReg1   
    private WordInstructions strGPR1ToIXReg3  = new WordInstructions();             // Place GPR1 to memory referenced by IXReg3
    private WordInstructions areGPR0AndGPR2Equal  = new WordInstructions();         // Test for equality between these two registers
    
   /**
    *TestProgram1
    *
    *The constructor will initialize several registers and memory locations to
    *the appropriate values to handle user inputs for test program 1
    ***************************************************************************/
    public TestProgram1(CPU cpu)
    {
        /***********************************************************************
        * For sake of simplicity, when program is loaded, just set the registers
        * to these initial values.  
        * 
        * For this program, rather than setting the program counter to a 
        * location, and looping over and over and over on those spots, we have
        * decided to "simulated" writing the equivalent of an "assembly" program
        * that simply has a long list of 16-bit word instructions.  
        * 
        * These instructions are not represented here as 0's and 1's, but as
        * objects made up of 5 smaller objects.  We set the smaller objects 
        * values individually for hte sake of simplicity.
        ***********************************************************************/
        cpu.simulatedIXReg1.setValue(100);
        cpu.simulatedIXReg2.setValue(200);
        cpu.simulatedIXReg3.setValue(300);
        cpu.simulatedGPR1.setValue(0);
        cpu.simulatedGPR2.setValue(0);
        
        /***********************************************************************
        * This instruction will increment GPR1 by one by using the immediate
        * value of the address
        ***********************************************************************/
        incrementGPR1.getRegCode().setValue(1);     // GPR1
        incrementGPR1.getAddressCode().setValue(1); // set address value to 1
        incrementGPR1.getOpCode().setValue(6);      // AIR opcode
        
        /***********************************************************************
        * This instruction will increment GPR2 by one by using the immediate
        * value of the address
        ***********************************************************************/
        incrementGPR2.getRegCode().setValue(2);     // GPR2
        incrementGPR2.getAddressCode().setValue(1); // set address value to 1
        incrementGPR2.getOpCode().setValue(6);      // AIR opcode
        
        /***********************************************************************
        * This instruction will load the memory addressed by the IX register
        * plus the effective address into GPR0
        ***********************************************************************/        
        loadGPR0WithChar.getIABit().setValue(1);    // set indirect addressing
        loadGPR0WithChar.getIxRegCode().setValue(2);// reference IX Reg 2
        loadGPR0WithChar.getOpCode().setValue(1);   // LDR opcode
        
        /***********************************************************************
        * This instruction will load the memory addressed by the IX register
        * plus the effective address into GPR1
        ***********************************************************************/            
        loadGPR1WithItr.getIABit().setValue(1);     // set indirect addressing
        loadGPR1WithItr.getIxRegCode().setValue(1); // reference IX Reg 1
        loadGPR1WithItr.getOpCode().setValue(1);    // LDR opcode
        loadGPR1WithItr.getRegCode().setValue(1);   // reference GPR 1
        
        /***********************************************************************
        * This instruction will store GPR3 into the memory address referenced
        * by the IXReg 3 plus the effective address
        ***********************************************************************/         
        strGPR1ValueToIXReg3Address.getIABit().setValue(1);         // set indirect addressing
        strGPR1ValueToIXReg3Address.getOpCode().setValue(2);        // STR opcode
        strGPR1ValueToIXReg3Address.getRegCode().setValue(1);       // reference GPR3
        strGPR1ValueToIXReg3Address.getIxRegCode().setValue(3);     // reference IX Reg 3
        
        /***********************************************************************
        * This instruction will load GPR0 with the user's last (21st) input 
        * of this program
        ***********************************************************************/                 
        loadGPR0WithChar21.getOpCode().setValue(1);         // LDR opcode
        loadGPR0WithChar21.getAddressCode().setValue(19);   // reference memory containing last input
        loadGPR0WithChar21.getIxRegCode().setValue(1);      // reference IX Reg 3
        loadGPR0WithChar21.getIABit().setValue(1);
        
        /***********************************************************************
        * This instruction will load GPR1 with the user's last (21st) input 
        * of this program
        ***********************************************************************/                         
        loadGPR1WithChar21.getOpCode().setValue(1);         // LDR Opcode
        loadGPR1WithChar21.getAddressCode().setValue(20);   // reference memory contianing last input
        loadGPR1WithChar21.getIABit().setValue(1);          // indirect addressing
        loadGPR1WithChar21.getIxRegCode().setValue(1);      // reference IX Reg 1
        loadGPR1WithChar21.getRegCode().setValue(1);        // reference GPR 1
        
        /***********************************************************************
        * This instruction will store GPR3 to the memory location referenced
        * by IX Reg 1
        ***********************************************************************/
        strGPR3ToIXReg1.getOpCode().setValue(2);            // STR Opcode
        strGPR3ToIXReg1.getIABit().setValue(1);             // set indirect addressing
        strGPR3ToIXReg1.getIxRegCode().setValue(1);         // reference IX reg 1
        strGPR3ToIXReg1.getRegCode().setValue(3);           // refrence GPR 3
        
        /***********************************************************************
        * This instruction will subtract from GPR1 the memory location referenced
        * by IX Reg 1 plus the effective address
        ***********************************************************************/        
        subGPR1WithItr.getIABit().setValue(1);              // indirect addressing
        subGPR1WithItr.getIxRegCode().setValue(1);          // reference IXReg 1
        subGPR1WithItr.getRegCode().setValue(1);            // reference GPR3
        subGPR1WithItr.getOpCode().setValue(5);             // SUB Opcode
        
        /***********************************************************************
        * This instruction will load GPR 2 with a value from memory 
        ***********************************************************************/                
        loadGPR2WithItr.getIABit().setValue(1);             // indirect addressing
        loadGPR2WithItr.getRegCode().setValue(2);           // GPR 2
        loadGPR2WithItr.getIxRegCode().setValue(1);         // IX reg 3
        loadGPR2WithItr.getOpCode().setValue(1);            // LDR Opcode
        
        /***********************************************************************
        * This instruction will load GPR 1 with a value from memory 
        ***********************************************************************/
        addMemToGPR3.getIABit().setValue(1);
        addMemToGPR3.getRegCode().setValue(3);
        addMemToGPR3.getOpCode().setValue(4);
        addMemToGPR3.getIxRegCode().setValue(3);
        
        /***********************************************************************
        * This instruction will store GPR3 to the memory referenced by IX Reg 1
        ***********************************************************************/
        strGPR3ToIXReg1Again.getIABit().setValue(1);
        strGPR3ToIXReg1Again.getRegCode().setValue(3);
        strGPR3ToIXReg1Again.getIxRegCode().setValue(1);
        strGPR3ToIXReg1Again.getOpCode().setValue(2);
        
        /***********************************************************************
        * This instruction will store GPR1 to IX Reg 3's memory location
        ***********************************************************************/        
        strGPR1ToIXReg3.getIABit().setValue(1);
        strGPR1ToIXReg3.getRegCode().setValue(1);
        strGPR1ToIXReg3.getIxRegCode().setValue(3);
        strGPR1ToIXReg3.getOpCode().setValue(2);
        
        /***********************************************************************
        * This instruction will test for equality between GPR0 and GPR2
        ***********************************************************************/        
        areGPR0AndGPR2Equal.getIxRegCode().setValue(2);
        areGPR0AndGPR2Equal.getOpCode().setValue(18);
        
        // Initialize all 10 address instructions to be store instructions
        address100.getOpCode().setValue(2);
        address101.getOpCode().setValue(2);
        address102.getOpCode().setValue(2);
        address103.getOpCode().setValue(2);
        address104.getOpCode().setValue(2);
        address105.getOpCode().setValue(2);
        address106.getOpCode().setValue(2);
        address107.getOpCode().setValue(2);
        address108.getOpCode().setValue(2);
        address109.getOpCode().setValue(2);
        
        // Have each address instruction add to the index register
        address100.getAddressCode().setValue(0);
        address101.getAddressCode().setValue(1);
        address102.getAddressCode().setValue(2);
        address103.getAddressCode().setValue(3);
        address104.getAddressCode().setValue(4);
        address105.getAddressCode().setValue(5);
        address106.getAddressCode().setValue(6);
        address107.getAddressCode().setValue(7);
        address108.getAddressCode().setValue(8);
        address109.getAddressCode().setValue(9);
        
        // Have each instruction utilize indirect addressing
        address100.getIABit().setValue(1);
        address101.getIABit().setValue(1);
        address102.getIABit().setValue(1);
        address103.getIABit().setValue(1);
        address104.getIABit().setValue(1);
        address105.getIABit().setValue(1);
        address106.getIABit().setValue(1);
        address107.getIABit().setValue(1);
        address108.getIABit().setValue(1);
        address109.getIABit().setValue(1);
        
        // Have each address utilize index register 1
        address100.getIxRegCode().setValue(1);
        address101.getIxRegCode().setValue(1);
        address102.getIxRegCode().setValue(1);
        address103.getIxRegCode().setValue(1);
        address104.getIxRegCode().setValue(1);
        address105.getIxRegCode().setValue(1);
        address106.getIxRegCode().setValue(1);
        address107.getIxRegCode().setValue(1);
        address108.getIxRegCode().setValue(1);
        address109.getIxRegCode().setValue(1);
        
        // Have each instruction utilize register 1
        address100.getRegCode().setValue(1);
        address101.getRegCode().setValue(1);
        address102.getRegCode().setValue(1);
        address103.getRegCode().setValue(1);
        address104.getRegCode().setValue(1);
        address105.getRegCode().setValue(1);
        address106.getRegCode().setValue(1);
        address107.getRegCode().setValue(1);
        address108.getRegCode().setValue(1);
        address109.getRegCode().setValue(1);
        
        // Make a quick array to make it easy to loop through each instruction
        WordInstructions[] tempArray = {address100,
                                        address101,
                                        address102,
                                        address103,
                                        address104,
                                        address105,
                                        address106,
                                        address107,
                                        address108,
                                        address109};
        
        /***********************************************************************
        *For loop will call each instruction, and increment GPR1.
        *
        *By doing this, the program will load 10 memory addresses with values 
        *from 0-9.
        ***********************************************************************/
        for (WordInstructions tempArray1 : tempArray) 
        {
            cpu.ALULogic(tempArray1);
            cpu.ALULogic(incrementGPR1);  
        }
        
        cpu.simulatedPC.setValue(1000);     // Set to 1000 for now...
    }
    
   /**
    * handleUserInput
    * 
    * @param  CPU the computer to manipulate
    * 
    * This instruction will be called each time a user inputs a value from the 
    * keyboard when program 1 runs.  It will increment GPR2 each time so that
    * the program knows how many times the user has input a value.  When it 
    * reachers 21, it will call a helper method to determine which of the first
    * 20 user input values are closest to the 21st.
    * 
    * Furthermore, this handle method will "tally" user inputs from the 
    * keyboard, verify data integrity, add the chars together (so that data read
    * in from 5 memory locations as 5,2,1,0,0 will get stored as 52100 elsewhere
    * ).  Then, it will output those tallied values to memory locations 300-321
    * so that they can be compared as mentioned above.
    * 
    * This instruction is the equivalent of writing several assembly-language
    * instructions that catch each user input in the same way, but increment
    * memory addresses by one 
    * 
    * OR
    * 
    * it is the equivalent of writing a loop instruction that counts down 
    * memory locations executing each along the way until it reaches a certain
    * location, and then does a "GOTO" statement back to the original memory 
    * location, awaiting the next user input.
    ***************************************************************************/
    public void handleUserInput(CPU cpu)
    {
        cpu.ALULogic(incrementGPR2);        // increment GPR2 each user input
        cpu.simulatedGPR0.setValue(0);      
        cpu.simulatedGPR1.setValue(0);
        cpu.simulatedGPR3.setValue(0);
        cpu.simulatedIXReg3.setValue(300);  // IXReg 3 will reference values of chars after mulitplication
        cpu.simulatedIXReg2.setValue(200);  // IXReg 2 will reference each char of a user input
        cpu.simulatedIXReg1.setValue(100);  // IXReg 1 will contain the numbers to compare user inputs against
        
        // Verify that GPR 2 is not zero...
        cpu.ALULogic(areGPR0AndGPR2Equal);
        
        // If condition code register hasn't been set to 8, then GPR2 doesnt equal 0
        if (cpu.simulatedCC.getDecimalValue() != 8)
        {   
            /*******************************************************************
            * Loop through user inputs at memory location 200-204.  No need
            * to loop through anything more since user inputs are limited to 
            * 65535 (5 chars). 
            *******************************************************************/
            for (int itr = 0; itr < 5; itr++)
            {
                /***************************************************************
                * Each iteration, grab the first char of the user input.  ALU
                * will "delete" the char after placing it into memory, so 
                * each iteration can simply grab the first char of the buffer
                * and load GPR0 with that char.
                * 
                * Also, the keyboard will read in user input and store it 
                * "backwards".  EG, user input of 12345 will be stored to memory
                * as 5,4,3,2,1 in 5 separate memory locations.
                * 
                * The instruction is simply referencing IXReg2 plus the EA of 
                * the instruction.  In this case, we are adding itr to the EA
                * each time so that the word can effectively loop.  This is the 
                * same as writing in "assembly"
                * LDR GPR0, Memory Address in IX Reg 2 + 0
                * LDR GPR0, Memory Address in IX Reg 2 + 1 
                * LDR GPR0, Memory Address in IX Reg 2 + 2
                ***************************************************************/
                loadGPR0WithChar.getAddressCode().setValue(itr);
                cpu.ALULogic(loadGPR0WithChar);

                /***************************************************************
                * The inner for loop will now compare the user input against 9
                * separate chars loaded into memory locations 100-109.  These
                * are numbers 0-9, and correspond to each location in memory.
                * If a user's input char doesn't equal that number, the program
                * will simply assume "0" and place that into the buffer.  This
                * ensures some form of data integrity for user inputs.
                ***************************************************************/
                for (int itr2 = 0; itr2 < 10; itr2++)
                {
                    // Load GPR 1 with address at IXReg1 plus iterator
                    loadGPR1WithItr.getAddressCode().setValue(itr2);
                    cpu.ALULogic(loadGPR1WithItr);

                    // Convert user input to ASCii and check to see if that value in
                    // GPR 0 is also contained in GPR 1
                    // We could do a TRR instruction here, but with an ASCii conversion
                    // it's too much
                    if (converter.convertASCiiToInt(cpu.simulatedGPR0.getDecimalValue()) ==
                        (cpu.simulatedGPR1.getDecimalValue()))
                    {
                        /*******************************************************
                        * If registers are equal, we have a matching char.  If
                        * chars are equal, we now determine what location the 
                        * char is in regarding the user input.  
                        * 
                        * Since the keyboard inputs chars backwards, we can
                        * grab each char starting at memory location 200 and 
                        * put them to the exponent of 10 * iterator.  In this
                        * way, the keyboard will read in 12345, store it as 
                        * 5,4,3,2,1 into memory locations 200-204, and the 
                        * program will then add them together to produce 12345.
                        *******************************************************/
                        
                        // We have taken some liberties with this code.
                        // It is entirely possible to increment a GPR each time this loop happens, 
                        // and use that GPR to multiply 10 times itself the amount of times in that GPR.
                        // However, that would also require a TRR opcode to check for a ZERO value in teh GPR,
                        // which would overcomplicate this code even more.
                        cpu.simulatedGPR1.setValue((int) (converter.convertASCiiToInt(cpu.simulatedGPR0.getDecimalValue()) * (Math.pow(10, itr))));

                        // Now store GPR1's value to memory...
                        cpu.ALULogic(strGPR1ToIXReg3);
                        
                        // ...and wow add that memory value to GPR1 so that it may
                        // "tally" the user's 5 char inputs into a single value
                        cpu.ALULogic(addMemToGPR3);     
                    }
                }
            }

            // Store value 400 into the IX Reg 1..
            cpu.simulatedIXReg1.setValue(400);
            
            // ...then store GPR3's tallied user input to memory at IX Reg 1...
            strGPR3ToIXReg1Again.getAddressCode().setValue(cpu.simulatedGPR2.getDecimalValue() - 1);
            cpu.ALULogic(strGPR3ToIXReg1Again);

            // Now verify that the GPR 2 is not 21...
            cpu.simulatedGPR0.setValue(21);
            cpu.ALULogic(areGPR0AndGPR2Equal);

            // ...when GPR0 does equal 21, begin comparing user inputs...
            if (cpu.simulatedCC.getDecimalValue() == 8) 
            {
                compareInputs(cpu);
                
                // now set CC  back to zero for other programs...
                cpu.simulatedCC.setValue(0);
            } 
        }
    }
    
    /**
    * compareInputs
    * 
    * @param  CPU the computer to manipulate
    * 
    * This method will loop through 20 user inputs and seek to find the one that
    * is closest to the 21st user input in terms of value
    ***************************************************************************/
    public void compareInputs(CPU cpu)
    {
        cpu.simulatedIXReg1.setValue(400);  // reference tallied values of user inputs
        cpu.ALULogic(loadGPR0WithChar21);   // Load GPR 0 with last user input
        
        cpu.simulatedGPR3.setValue(0);  
        cpu.ALULogic(strGPR3ToIXReg1);      // store that max value to memory for later
        
        // Set to maximum value so any user input will be a better match
        cpu.simulatedGPR3.setValue(32767);  
        
        /***********************************************************************
        * Loop through each of the 20 user inputs with this for loop.  
        * 
        * It will load GPR1 with the last user input, and then subtract from 
        * GPR1 with the memory referenced by the current loop iteration's memory.
        * 
        * Then, it will compare GPR1 to GPR3.  If it is lower than GPR3, it will
        * store GPR1's value to GPR3, and then store the resulting subtraction
        * value to memory. 
        * 
        * Finally, when this occurs, the program will load GPR2 with the actual
        * user input value (rather than the resulting subtraction value). 
        * 
        * Whatever value resides in GPR2 on the last iteration will be output 
        * to the user.
        ***********************************************************************/
        for (int itr = 0; itr < 20; itr++)
        {
            cpu.ALULogic(loadGPR1WithChar21);               // Load GPR1 with final user input
            subGPR1WithItr.getAddressCode().setValue(itr);  // Subtract from it the value currently in memory
            cpu.ALULogic(subGPR1WithItr);                                  
            
            // TODO:  turn this into a TRR opcode
            if (cpu.simulatedGPR1.getDecimalValue() < cpu.simulatedGPR3.getDecimalValue())
            {
                // If GPR1 is lower than previous lowest, set GPR3 to the new "subtraction" value
                cpu.simulatedGPR3.setValue(cpu.simulatedGPR1.getDecimalValue());
                
                // Now store that value to memory
                cpu.ALULogic(strGPR3ToIXReg1);
                
                // Now load GPR2 with the actual user input (rather than the subtraction value) referenced by IXReg 3
                loadGPR2WithItr.getAddressCode().setValue(itr);
                cpu.ALULogic(loadGPR2WithItr);
            }
        }
    }
}