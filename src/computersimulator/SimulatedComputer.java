/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */

package ComputerSimulator;

import CPU.*;
import static java.lang.System.exit;
import Memory.ROMLoader;

/**
* @ ComputerSimulator
*  
* @ This class will "contain all the parts" (Objects) that represent the
* @ entirety of this computer.
*******************************************************************************/

public class SimulatedComputer
{
    CPU simulatedCPU = new CPU();
    
    public SimulatedComputer()
    {

    }   

    /******************************* Getters **********************************/ 
    public CPU getCPU() {return simulatedCPU;}
}
