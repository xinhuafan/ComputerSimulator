/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package Memory;

import CPU.OpCode.bitArray;
import static java.lang.System.exit;
import java.util.Arrays;

/**
* @ Word
* 
* @ This class represents a 16-bit word contained within a memory location
*******************************************************************************/

public class Word extends bitArray
{            
    public Word()
    {
        this.bits = new boolean[16];
    }
    
    public Word(final int intToSet)
    {
        this.bits = new boolean[16];
        setValue(intToSet);
    }
    
    // Clear the register (Set all bits to zero/false)
    public void clearBits()
    {
        clear();
    }
}