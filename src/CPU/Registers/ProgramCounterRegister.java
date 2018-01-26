/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package CPU.Registers;

/**
* @ ProgramCounterRegister (PC)
*
* @ This class represents a 12 bit Program Counter register.
* @
* @ It should be noted that currently, registers utilize a "little-endian"
* @ structure for the opcode.  EG, the bits are read from the right to the left.
* 
* @ This register will hold the ADDRESS of the next instruction to be executed
*******************************************************************************/

public class ProgramCounterRegister extends Register
{
    final int PROGRAM_COUNTER_REGISTER_SIZE = 12;
    
    public ProgramCounterRegister()
    {        
        boolean[] tempArray = new boolean[PROGRAM_COUNTER_REGISTER_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        bits = tempArray;
    }
}
