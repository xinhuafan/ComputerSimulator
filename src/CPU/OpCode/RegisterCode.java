/* @ Author Jeremy Case
 * @ George Washington University
 * @ CSCI 6461:  Computer Architecture
 */

package CPU.OpCode;

/******************************************************************************
* @ RegisterCode Class
* 
* @ This class represents the 2-bit portion of a word instruction.  It will 
* @ designate which register is referenced by each instruction.
*******************************************************************************/
public class RegisterCode extends bitArray
{
    final int REGISTER_CODE_SIZE = 2;
    
    public RegisterCode()
    {        
        boolean[] tempArray = new boolean[REGISTER_CODE_SIZE];        
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        this.bits = tempArray;
    }
}