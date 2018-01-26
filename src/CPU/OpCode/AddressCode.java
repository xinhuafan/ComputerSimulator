/* @ Author Jeremy Case
 * @ George Washington University
 * @ CSCI 6461:  Computer Architecture
 */

package CPU.OpCode;

/******************************************************************************
* @ AddressCode Class
* 
* @ This class represents the 5-bit portion of a word instruction.  It will, 
* @ in conjunction with the index register part of the instruction, determine
* @ the effective address.
*******************************************************************************/
public class AddressCode extends bitArray
{
    final int ADDRESS_CODE_SIZE = 5;
    
    public AddressCode()
    {        
        boolean[] tempArray = new boolean[ADDRESS_CODE_SIZE];        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        this.bits = tempArray;
    }
}