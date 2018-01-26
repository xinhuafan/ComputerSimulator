/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package CPU.Registers;

import CPU.OpCode.WordInstructions;
import static java.lang.System.exit;

/**
*InstructionRegister (IR)
*
*This class represents a 16 bit "Instruction" register.
*
*It should be noted that currently, registers utilize a "little-endian"
*structure for the opcode.  EG, the bits are read from the right to the left.
* 
*This is a particularly important register since it will hold the next
*instruction to be executed.
*******************************************************************************/

public class InstructionRegister extends Register
{
    // Does not use "normal bits" but instructions
    final int INSTRUCTION_REGISTER_SIZE = 16;  
    
    // Word Instruction lengths for individual components
    private final int OPCODE_LENGTH = 6;
    private final int IX_REG_LENGTH = 2;
    private final int GPR_LENGTH = 2;
    private final int IA_BIT_LENGTH = 1;
    private final int ADDRESS_LENGTH = 5;    
    
    public InstructionRegister()
    {        
        boolean[] tempArray = new boolean[INSTRUCTION_REGISTER_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        bits = tempArray;
    }
}