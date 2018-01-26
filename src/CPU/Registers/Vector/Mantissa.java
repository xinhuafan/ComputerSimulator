/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package CPU.Registers.Vector;

import java.math.*;
import java.util.Arrays;
import Memory.*;
import static java.lang.System.exit;
import CPU.OpCode.bitArray;
import CPU.Registers.Register;

/**
* Mantissa
* 
* This class represents the 8 bit portion of a floating point register.
* 
* This class represents the base number to utilize in conjunction with the 
* exponent class of a floating point register.  Together, these two numbers
* can represent something like 2^12 or 10^-3 or -200^10
*******************************************************************************/

public class Mantissa extends bitArray
{
    final int MANTISSA_BITS = 8;
    
    public Mantissa()
    {
        unsigned = true;
        
        boolean[] tempArray = new boolean[MANTISSA_BITS];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        bits = tempArray;
    }   
}