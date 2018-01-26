/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package CPU.Registers;

/**
*IndexRegister (X1, X2, X3)
*
*This class represents a 12 bit "index purpose" register.
*
*It should be noted that currently, registers utilize a "little-endian"
*structure for the opcode.  EG, the bits are read from the right to the left.
*******************************************************************************/

public class IndexRegister extends Register
{
    final int INDEX_REGISTER_SIZE = 16;
    
    public IndexRegister()
    {        
        boolean[] tempArray = new boolean[INDEX_REGISTER_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        bits = tempArray;
    }
}