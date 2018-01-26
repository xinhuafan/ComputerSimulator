/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package CPU.Registers;

/**
*GeneralRegister
*
*This class represents a 16 bit "general purpose" register.
*
*It should be noted that currently, registers utilize a "little-endian"
*structure for the opcode.  EG, the bits are read from the right to the left.
*******************************************************************************/

public class GeneralRegister extends Register
{
    final int GENERAL_REGISTER_SIZE = 16;
    
    public GeneralRegister()
    {        
        boolean[] tempArray = new boolean[GENERAL_REGISTER_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        bits = tempArray;
    }  
}