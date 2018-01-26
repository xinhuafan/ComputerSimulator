/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package CPU.Registers;

/**
* @ MemoryAddressRegister (MAR)
*
* @ This class represents a 16 bit "Memory Address (MAR)" register.
* @
* @ It should be noted that currently, registers utilize a "little-endian"
* @ structure for the opcode.  EG, the bits are read from the right to the left.
* 
* @ This Register holds the ADDRESS of the word to be executed
*******************************************************************************/

public class MemoryAddressRegister extends Register
{
    final int MEMORY_REGISTER_SIZE = 16;
    
    public MemoryAddressRegister()
    {        
        boolean[] tempArray = new boolean[MEMORY_REGISTER_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        bits = tempArray;
    }
}
