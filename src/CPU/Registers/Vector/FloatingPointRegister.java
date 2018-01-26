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
* FloatingPointRegister
*
* This class represents a floating point register to be inherited from.
* 
* There are three sections of a 16 bit register for floating point registers:
* the sign bit, the exponent bits, and the mantissa.  The sign bit dictates the
* sign of the entire register.  The first bit of the exponent section (1) 
* determines whether or not an exponent is negative or not.
* 
* |0|1|2|3|4|5|6|7|8|9|0|1|2|3|4|5|
* |S|E|E|E|E|E|E|E|M|M|M|M|M|M|M|M|
* 
* S = sign bit
* E = exponent bits
* M = mantissa bits
*******************************************************************************/

public class FloatingPointRegister extends Register
{
    public boolean updateOutputFlag = false;
    
    private final int MANTISSA_MAX_VALUE = 255;
    private final int EXPONENT_MAX_VALUE = 64;
    private final int EXPONENT_MIN_VALUE = -63; 
    
    private SignBit signBit = new SignBit();
    private Exponent exponentBits = new Exponent();
    private Mantissa mantissaBits = new Mantissa();
    
    public FloatingPointRegister()
    {
        unsigned = false;
    }
    
    /******************************* Setters **********************************/
    
    /**
    * setValue
    * 
    * @param value the value to set the floating point register to.  This method
    * assumes that all incoming values have the ability to be rooted to the nth
    * root up to 255 and produce a whole number.
    ***************************************************************************/
    
    @Override
    public void setValue(int value)
    {       
        double tempValue = 0;
        
        // If value is above 1, we are dealing with a positive number and exponent
        if (value < Math.pow(MANTISSA_MAX_VALUE, EXPONENT_MAX_VALUE) && value > 1)
        {
            // start the exponent at 2 since anything less would break the method
            double exponentValue = 2;
            
            // For loop is expensive but cannot continue past 255
            for (; exponentValue < 255; exponentValue++)
            {
                // Get the nth root of the value passed in
                tempValue = Math.pow(value, (1/exponentValue));
               
                // Now we have to round it to 1 decimal place in case we get
                // repeating decimals like 9.9999999
                tempValue = Math.round(tempValue * 10d) / 10d;
               
                // If the temp value is a whole number...
                if (tempValue % 1 == 0)
                {
                    // break the for-loop...
                    break;
                }
            }
            
            // and use the variables to set the exponent and mantissa
            exponentBits.setValue( (int) exponentValue);
            mantissaBits.setValue( (int) tempValue);
            signBit.setPositive();
        }
        
        updateOutputFlag = true;
    }
        
    /******************************* Getters **********************************/  
    
    public boolean getUpdateFlag() {return updateOutputFlag;}

    public double getFloatingPointValue() 
    {
        // If we have a positive number...
        if (signBit.getBit())
        {
            return Math.pow(mantissaBits.getDecimalValue(), exponentBits.getDecimalValue());
        }
        
        // Otherwise we have a negative number
        else
        {
            return (-1 * Math.pow(mantissaBits.getDecimalValue(), exponentBits.getDecimalValue()));
        }
    }
}