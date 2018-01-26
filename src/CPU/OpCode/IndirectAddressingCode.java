/* @ Author Jeremy Case
 * @ George Washington University
 * @ CSCI 6461:  Computer Architecture
 */

package CPU.OpCode;

/******************************************************************************
* @ RegisterCode Class
* 
* @ This class represents the 1-bit portion of a word instruction.  It will 
* @ designate whether or not indirect addressing is utilized.
*******************************************************************************/
public class IndirectAddressingCode extends bitArray
{
    final int IA_CODE_SIZE = 1;
    
    public IndirectAddressingCode()
    {        
        boolean[] tempArray = new boolean[IA_CODE_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            tempArray[itr] = false;
        }
        
        this.bits = tempArray;
    }
    
    public boolean isBitSet() {return bits[0];}
}