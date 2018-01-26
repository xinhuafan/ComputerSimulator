/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package CPU.Registers;

/**
*MachineFaultRegister
*
*This class represents a 4 bit "Machine Fault" register.
*
*It should be noted that currently, registers utilize a "little-endian"
*structure for the opcode.  EG, the bits are read from the right to the left.
* 
*This register will contain the ID Code of a machine fault if it occurs...
*******************************************************************************/

public class MachineFaultRegister extends Register
{
    final int MACHINE_FAULT_REGISTER_SIZE = 4;
    
    public MachineFaultRegister()
    {        
        boolean[] tempArray = new boolean[MACHINE_FAULT_REGISTER_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
            tempArray[itr] = false;
        
        bits = tempArray;
    }
}
