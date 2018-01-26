/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package CPU.Registers;

import java.math.*;
import java.util.Arrays;
import Memory.*;
import static java.lang.System.exit;
import CPU.OpCode.bitArray;

/**
*Register
*
*This class represents a register to be inherited from. 
*
*It should be noted that currently, registers utilize a "little-endian"
*structure for the opcode.  EG, the bits are read from the right to the left.
* 
*Furthermore, it is up to the specific types of registers to define how many
*bits they contain.
*******************************************************************************/

public class Register extends bitArray
{
    public boolean updateOutputFlag = false;
    
    public Register()
    {
        unsigned = false;
    }
    
    // Clear the register (Set all bits to zero/false)
    public void clearRegister()
    {
        super.clear();
    }
    
    /******************************* Setters **********************************/
    public void flagAsUpdated() {updateOutputFlag = false;}
    
    public void setValue(int value)
    {
        super.setValue(value);
        
        updateOutputFlag = true;
    }
    
    public void toggleBit(int bit)
    {
        super.toggleBit(bit);
        
        updateOutputFlag = true;
    }    
        
    /******************************* Getters **********************************/  
    
    public boolean getUpdateFlag() {return updateOutputFlag;}
    
    /******************************** Shifters ********************************/
    
    /**
    * Shifters will shift bits to the right or left, and come in three types
    *
    * arithmeticShiftBits:
    *    Arithmetic shifts will shift every bit excluding the sign bit, and 
    *    will not currently manipulate the first iteration bit (EG, leftmost bit
    *    on a shiftRight)
    * 
    * rotateBits:
    *    Rotations will shift bits just like arithmetic shifting, except they 
    *    don't worry about sign bits.  Also, they will wrap the leftmost and 
    *    rightmost bits so that nothing is truncated.
    * 
    * logicalShift:
    *    Logical shifts will shift bits, but ignore sign bits.  Furthermore, 
    *    they will set the first iteration bit (EG, rightmost bit on a left 
    *    shift) to zero.
    ***************************************************************************/
    
    // maintains the sign bit
    public void arithmeticShiftBitsLeft()
    {
        if (bits.length > 2)
        {
            // Stop iterator before it hits leftmost bit (the sign bit)
            for (int itr = 1; itr < bits.length - 1; itr++)
                bits[itr] = bits[itr + 1];
        }

        else
            System.out.println("Error with shifting register bits left...");
        
    }
    
    public void rotateBitsLeft()
    {
        if (bits.length > 0)
        {
            boolean leftMostBit = bits[0];

            for (int itr = 0; itr < bits.length - 1; itr++)
                bits[itr] = bits[itr + 1];

            bits[bits.length - 1] = leftMostBit;
        }

        else
            System.out.println("Error with rotating register bits left...");
    }
    
    public void setValue(Word word)
    {
        int value = word.getDecimalValue();
        this.setValue(value);
    }
    
    public void logicalShiftBitsLeft()
    {
        for (int itr = 0; itr < bits.length - 1; itr++)
            bits[itr] = bits[itr + 1];

        bits[bits.length - 1] = false;
    }

    public void arithmeticShiftBitsRight()
    {
        if (bits.length > 2)
        {
            for (int itr = bits.length - 1; itr > 0; itr--)
                bits[itr] = bits[itr - 1];
        }

        else
            System.out.println("Error with shifting bits right...");
    }

    public void rotateBitsRight()
    {
        if (bits.length > 0)
        {
            boolean rightMostBit = bits[bits.length - 1];

            for (int itr = bits.length - 1 ; itr > 0; itr--)
                bits[itr] = bits[itr - 1];       

            bits[0] = rightMostBit;
        }

        else
            System.out.println("Error with rotating register bits right...");
    }

    public void logicalShiftBitsRight()
    {
        for (int itr = bits.length - 1; itr > 0; itr--)
                bits[itr] = bits[itr - 1];

        bits[bits.length - 1] = false;
    }      

    /****************************** Arithmetic ********************************/    
    
    /**
    *addRegisterValue
    *
    *@param toAdd - the register to add to this register
    * 
    *Takes a register as an argument, and adds that register's value to this
    *register, and toggles the bits accordingly.
    ***************************************************************************/
    
    // TODO: overflow/underflow and handle sign bits
    public void addRegisterValue(Register toAdd)
    {
        // Get the values of both registers in decimal and add them...
        int toAddDecimal = toAdd.getDecimalValue();
        int targetRegisterDecimal = getDecimalValue();
        int tally = toAddDecimal + targetRegisterDecimal;

        // ...Then clear this register...
        clearRegister();

        // Now loop through the sum of both registers...
        for (int itr = 0; itr < bits.length; itr++)
        {
            // ...subtracting from it each time it can "fit" in the current
            // iteration's power of 2...
            if (tally >= Math.pow(2, (bits.length - 1 - itr) ) )
            {
                tally -= Math.pow(2, (bits.length - 1 - itr) );
                toggleBit(bits.length - 1 - itr);   
            }
        }    		
    }
    
    /**
    *addRegisterValue
    *
    *@param toAdd - the word to add to this register
    * 
    *Takes a register as an argument, and adds that register's value to this
    *register, and toggles the bits accordingly.
    ***************************************************************************/
    public void addRegisterValue(Word toAdd)
    {
        // Get the values of both registers in decimal and add them...
        int toAddDecimal = toAdd.getDecimalValue();
        int targetRegisterDecimal = getDecimalValue();
        int tally = toAddDecimal + targetRegisterDecimal;

        // ...Then clear this register...
        clearRegister();

        // Now loop through the sum of both registers...
        for (int itr = 0; itr < bits.length; itr++)
        {
            // ...subtracting from it each time it can "fit" in the current
            // iteration's power of 2...
            if (tally >= Math.pow(2, (bits.length - 1 - itr) ) )
            {
                tally -= Math.pow(2, (bits.length - 1 - itr) );
                toggleBit(bits.length - 1 - itr);   
            }
        }    		
    }    

    /**
    *addRegisterValue
    *
    *@param toAdd - the immediate value to add to this register
    * 
    *Takes an immediate value as an argument, and adds that immediate value to this
    *register, and toggles the bits accordingly.
    ***************************************************************************/
    public void addRegisterValue(int toAdd)
    {
        // Get the values of both registers in decimal and add them...
        int tally = toAdd + getDecimalValue();

        // ...Then clear this register...
        clearRegister();

        // Now loop through the sum of both registers...
        for (int itr = 0; itr < bits.length; itr++)
        {
            // ...subtracting from it each time it can "fit" in the current
            // iteration's power of 2...
            if (tally >= Math.pow(2, (bits.length - 1 - itr) ) )
            {
                tally -= Math.pow(2, (bits.length - 1 - itr) );
                toggleBit(bits.length - 1 - itr);   
            }
        } 	    		
    }  
    
   /**
    * subRegisterValue
    * 
    * @param toSub - register to use to subtract from this register
    * 
    * Takes a register as an argument, and subtracts that register's 
    * value to this register, and toggles the bits accordingly.
    * 
    * Subtracting is currently done on a "two's-complement" basis.  The most 
    * significant bit represents a negative number in the "to subtract" register
    * while every other bit is positive.
    ***************************************************************************/    
    
    // TODO: overflow/underflow 
    public void subRegisterValue(Register toSub)
    {   
        int toSubDecimalValue = getDecimalValue();
        int toSubValue = toSub.getDecimalValue();
        
        int tally = Math.abs(toSubDecimalValue - toSubValue);
        
        clearRegister();
        
        for (int itr = 0; itr < bits.length; itr++)
        {
            // ...subtracting from the value each time it can "fit" in 
            // the current iteration's power of 2...
            if (tally >= Math.pow(2, (bits.length - 1 - itr) ) )
            {
                tally -= Math.pow(2, (bits.length - 1 - itr) );
                toggleBit(bits.length - 1 - itr);   
            }
        } 
    }
    
    /**
    * subRegisterValue
    * 
    * @param toSub - integer value to subtract from register
    * 
    * This is an overloaded method meant ONLY for subtracting an immediate value
    * from a register via SIR opcodes.
    ***************************************************************************/    
    
    // TODO: overflow/underflow 
    public void subRegisterValue(final int toSub)
    {   
        // Now grab this register's value and "add" both register values
        int targetRegisterDecimal = getDecimalValue();
        int tally = targetRegisterDecimal - toSub;
        
        // ...Then clear this register...
        clearRegister();
        
        // If the register ends up being negative, let's handle that...
        if (tally < 0)
        {
            // Now that we have flagged register as negative, absolute value it
            // so that we now have a positive number to work with.
            tally = Math.abs(tally);
            
            // Toggle "sign bit" to signify a negative register.
            toggleBit(bits.length - 1);
            
            // ...now loop through the bits with the decimal value after 
            // subtracting...
            for (int itr = 1; itr < bits.length; itr++)
            {
                // ...subtracting from the value each time it can "fit" in 
                // the current iteration's power of 2...
                if (tally >= Math.pow(2, (bits.length - 1 - itr) ) )
                {
                    tally -= Math.pow(2, (bits.length - 1 - itr) );
                    toggleBit(bits.length - 1 - itr);   
                }
            }  
        }    
        
        // else if new number is not negative...
        else
        {
            // ...now loop through the bits with the decimal value after 
            // subtracting...
            for (int itr = 0; itr < bits.length; itr++)
            {
                // ...subtracting from the value each time it can "fit" in 
                // the current iteration's power of 2...
                if (tally >= Math.pow(2, (bits.length - 1 - itr) ) )
                {
                    tally -= Math.pow(2, (bits.length - 1 - itr) );
                    toggleBit(bits.length - 1 - itr);   
                }
            }    
        }
    }    
    
    /**
    *  multiplyRegisterValue
    * 
    * @param toMult this is this register that will be used to multiply this
    * register
    * @param lowOrder this is a register that will hold the "low order" part of
    * the product of multiplying these two registers
    * @param ccReg this is the condition code register and may need to be set
    * for overflow in case of overflow
    * 
    * The "Low order" bits (the first 32767 values) will be put in the loworder
    * register, while the remainder (if any) get put into this register.  This
    * will give the appearance that a register may have strange values after
    * multiplying.  Keep in mind that to get the product after multiplication
    * you must "add" this register plus the register+1.  
    ***************************************************************************/
    public void multiplyRegisterValue(Register toMult, Register lowOrder, ConditionCodeRegister ccReg)
    {
        // get the product of both registers...
        int product = toMult.getDecimalValue() * getDecimalValue();

        // If product causes overflow, handle it
        if (product > 32767)
        {
            ccReg.setValue(ConditionCodeRegister.OVERFLOW);
            lowOrder.clearRegister();
           
            for (int itr = 1; itr < bits.length; itr++)
            {
                lowOrder.getBits()[itr] = true;
            }
            
            this.setValue(product - lowOrder.getDecimalValue());
        }
        
        // If product causes a negative overflow, handle it
        else if (product  < -32767)
        {
            ccReg.setValue(ConditionCodeRegister.OVERFLOW);
            lowOrder.clearRegister();
            lowOrder.toggleBit(0);
            this.setValue(product - lowOrder.getDecimalValue());
        }
        
        // Otherwise, just set this register to negative and 
        else
        {
            this.clearRegister();
            lowOrder.setValue(product);
        }
    }

    /**
    * divideRegisterValue
    * 
    * @param toDiv this is this register that will be used to get a quotient 
    * by dividing
    * @param remainder this is the register that will receive the remainder
    * after dividing the two registers
    * @param ccReg this is the condition code register and may need to be set
    * if a divide by zero occurs
    * 
    * This method will handle register divisions
    ***************************************************************************/
    public void divideRegisterValue(Register toDiv, Register remainder, ConditionCodeRegister ccReg)
    {
        // If we don't have a divide by zero scenario...
        if (toDiv.getDecimalValue() != 0)
        {
            // Then get the quotient...
            int quotient = getDecimalValue() / toDiv.getDecimalValue();
            
            // Then set the remainder register to the value of the remainder
            remainder.setValue( (int) (getDecimalValue() % toDiv.getDecimalValue()));
            
            // Then set this register to the quotient value
            setValue(quotient);
        }
        
        // Otherwise, set the condition code and ignore the division
        else
        {
            ccReg.setValue(ConditionCodeRegister.DIVZERO);
        }
    }

    public boolean areRegistersEqual(Register toParse)
    {
        if (toParse.getBits().length != bits.length)
            return false;
        
        else
            return Arrays.equals(toParse.getBits(), bits);
    }
}
