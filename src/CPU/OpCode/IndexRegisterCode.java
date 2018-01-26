/**
 * @Author Jeremy Case
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */

package CPU.OpCode;

/******************************************************************************
* @ IndexRegisterCode Class
* 
* @ This class represents the 2-bit portion of a word instruction.  It will 
* @ designate which Index register is referenced by each instruction.
*******************************************************************************/
public class IndexRegisterCode extends bitArray
{
    final int INDEX_REGISTER_CODE_SIZE = 2;
    
    public IndexRegisterCode()
    {        
        boolean[] tempArray = new boolean[INDEX_REGISTER_CODE_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        this.bits = tempArray;
    }
    
   /**************************************************************************
    *getShiftDirection
    * 
    *Returns a boolean telling the ALU to shift bits right or left
    * 
    *false = right, true = left
    ***************************************************************************/
    public boolean getShiftDirection() {return bits[1];}
    
   /**************************************************************************
    *getShiftType
    * 
    *Returns a boolean telling the ALU to shift arithmetically or logically
    * 
    *false = arithmetically, true = logically
    ***************************************************************************/    
    public boolean getShiftType() {return bits[0];}
}    