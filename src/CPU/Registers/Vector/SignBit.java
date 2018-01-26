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
* SignBit
* 
* This class represents the sole bit of a floating point register.  Its only
* purpose is to modify the floating point register to positive or negative 
* depending on whether or not the bit is set.
*******************************************************************************/

public class SignBit
{
    private boolean bit;
    
    SignBit()
    {
        bit = false;
    }
    
    public void setNegative() { bit = false; }
    public void setPositive() { bit = true; }
    
    public boolean getBit() { return bit; }
}