/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */

package CPU.OpCode;

import static java.lang.System.exit;

/**
*OpCode Class
* 
*This class represents an 6-bit opCode.  It is represented by a 6-element
*array of boolean values, where false = 0, and true = 1.  Furthermore, this
*class has several methods to manipulate the bits (shifting, toggling,
*flipping).  It also has the ability to calculate the base-10 value of the 
*bits to determine what opCode the array of bits currently represents.
*Finally, the class also has a list of opCodes and their base-10 values for
*the remaining portion of this program to utilize.
*
*This is important - *the opcode is "little-endian"*.  This means the bits 
*are read from the right to left.
*******************************************************************************/
public class OpCode extends bitArray
{
    final int OP_CODE_SIZE = 6;
    
    public OpCode()
    {        
        boolean[] tempArray = new boolean[OP_CODE_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            tempArray[itr] = false;
        }
        
        this.bits = tempArray;
    }
    
       
}