/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package CPU.Registers;

/**
* @ MemoryBufferRegister (MBR)
*
* @ This class represents a 16 bit memory buffer register.
* @
* @ It should be noted that currently, registers utilize a "little-endian"
* @ structure for the opcode.  EG, the bits are read from the right to the left.
* 
* @ This register will hold the word just fetched from memory or sent to memory
*******************************************************************************/

public class MemoryBufferRegister extends Register
{
    final int MEMORY_REGISTER_SIZE = 16;
    
    public MemoryBufferRegister()
    {        
        boolean[] tempArray = new boolean[MEMORY_REGISTER_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        bits = tempArray;
    }
}
