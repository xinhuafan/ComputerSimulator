/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package CPU.Registers;

/**
*MachineStatusRegister (MSR)
*
*This class represents a 16 bit Machine Status register.
*
*It should be noted that currently, registers utilize a "little-endian"
*structure for the opcode.  EG, the bits are read from the right to the left.
* 
*This register will maintain information about the computer
*******************************************************************************/

public class MachineStatusRegister extends Register
{
    final int MACHINE_STATUS_REGISTER_SIZE = 16;
    
    public MachineStatusRegister()
    {        
        boolean[] tempArray = new boolean[MACHINE_STATUS_REGISTER_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        bits = tempArray;
    }
}
