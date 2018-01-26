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
* Exponent
* 
* This class represents the 7 digit exponent portion of a floating point
* register.  The first digit of this class is a sign bit - but only to 
* represent whether or not the exponent that this class represents is negative
* or not.
*******************************************************************************/

public class Exponent extends bitArray
{
    final int EXPONENT_BITS = 7;
    
    public Exponent()
    {
        unsigned = false;
        
        boolean[] tempArray = new boolean[EXPONENT_BITS];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        bits = tempArray;
    }   
}