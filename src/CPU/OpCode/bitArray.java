package CPU.OpCode;

import static java.lang.System.exit;
import java.lang.reflect.Array;
import java.util.Arrays;

/* We assume little endian so the bits will be going
   16 8 4 2 1
    0 1 1 0 0 = 12
 */

public class bitArray
{
    protected boolean[] bits;
    protected boolean unsigned = true;
    
    public bitArray()
    {
        // constructor stuff...
    }

    public bitArray(boolean[] bits)
    {
        // check not null bits
        Check.NotNull(bits, "BitArray::Constructor::Bits array is null");

        this.bits = bits;
    }
    
    // Only use this during initial creation. Also, since bitArray is
    // meant to be inherited from (probably should make Virtual at some point)
    // don't use this at all.
    public void setSign(boolean unsigned)
    {
        this.unsigned = unsigned;
    }
    
    // this assumes the number passed in will always be positive
    // passing in a negative number will just have it be treated as positive
    private static int getDecimalValueInternal(boolean[] array)
    {
        Check.IsTrue(array.length >= 2, 
                "bitArray::arrays must be at least two elements long");
        int decimalValue = 0;
        
        for (int i = array.length - 1; i >= 0; i--)
        {
            if (array[i])
            {
                decimalValue += Math.pow(2, array.length - 1 - i);
            }
        }
        
        return decimalValue;
    }

    public int getDecimalValue()
    {
        boolean negative = false;
        if (bits[0] && !unsigned) negative = true;
        
        if (negative)
        {
            // this is all because the most efficient way to do two's compliment
            // on a bitArray is to first get the two's compliment
            boolean[] tempBits = bitArray.getTwosComplementArray((bits).clone());
            return -1 * bitArray.getDecimalValueInternal(tempBits);
        }
        else
        {
            return bitArray.getDecimalValueInternal(this.getBits());
        }
    }
    
    public void clear()
    {
        for (int i = 0; i < bits.length; i++)
        {
            bits[i] = false;
        }
    }
    
    public boolean[] getBits()
    {
        return bits;
    }
    
    public int getLength()
    {
        return bits.length;
    }
    
    public void setValue(int value)
    {
        boolean negative = value < 0 && !unsigned;
        
        if (negative && !unsigned)
        {
            value *= -1;
        }
        
        // Clear array so we get a fresh start...
        clear();
        
        // check that the value fits in the number of bits
        if (Math.abs(value) > Math.pow(2, bits.length))
        {
            System.out.println("Value is too big! " + value);
            System.exit(-1);
        }

        // convert value to binary
        for (int i = 0; i < bits.length; i++)
        {
            if (value >= Math.pow(2, bits.length - 1 - i))
            {
                bits[i] = true;
                value -= Math.pow(2, bits.length - 1 - i);
            }
        }
        
        // value must be zero by the end or else we've done something
        // horribly wrong
        if (value != 0)
        {
            System.out.println("Improper conversion from int to binary!");
            exit(-1);
        }
        
        if (negative)
        {
            this.twosComplement();
        }
    }
    
    public void toggleBit(int bit)
    {
        Check.InBounds(bits.length, bit,
            "OpCode::toggleSelectedBit::out of bounds");

        bits[bits.length - 1 - bit] = !bits[bits.length - 1 - bit];
    }
    
    public void flipBits()
    {
        for (int i = 0; i < bits.length; i++)
        {
            bits[i] = !bits[i];
        }
    }
    
    public void shiftBitsRight()
    {
        // Shift all bits to the right
        for (int i = bits.length - 1; i > 0; i--)
        {
            bits[i] = bits[i - 1];
        }
        
        bits[0] = false;
    }
    
    public void shiftBitsLeft()
    {
        // Shift all bits to the left except the right-most....
        for (int i = 0; i < bits.length - 1; i++)
        {
           bits[i] = bits[i + 1];
        }
        
        // Now assign the right-most bit the original value of left-most bit
        bits[5] = false;
    }
    
   /***************************************************************************
    *  This method takes advantage of the fact that integers truncate on 
    *  division of numbers with remainders.  While this method is fine for our
    *  purposes, it will only properly output an octal number of a decimal
    *  number under 80.  Anything over 80 and this method will return 
    *  nonsensical data.  Fortunately, our simulator should only need to 
    *  simulate 64 opcodes.
    * 
    ***************************************************************************/
    public int calculateOpCodeInOctal()
    {
        int value = 0;
        int iteration = 0;
        for (int i = bits.length - 1; i >= 0; i -= 3)
        {
            // actually four because I'm lazy and will abuse
            // getDecimalValueInternal
            boolean[] threeBits = {false, bits[i - 2], bits[i - 1], bits[i]};
            
            value += Math.pow(10, iteration) * 
                    bitArray.getDecimalValueInternal(threeBits);
            iteration++;
        }
        
        return value;
    }
    
    public static boolean[] getTwosComplementArray(boolean[] bits)
    {
        //inversing the bit
        for (int i=0; i<bits.length; i++){
            bits[i] = !bits[i];
        }
        
        boolean carry = true;
        //Adding 1
        for (int i = bits.length - 1; i >= 0 && carry; i--)
        {
            if (bits[i] == false)
            {
                bits[i] = true;
                carry = false;
            }
            else
            {
                bits[i] = false;
            }
        }
        
        return bits;
    }
    
    //Method for two's complement to represent negative number
    public void twosComplement(){
        
        //inversing the bit
        for (int i=0; i<this.bits.length; i++){
            this.bits[i] = !this.bits[i];
        }
        
        boolean carry = true;
        //Adding 1
        for (int i = bits.length - 1; i >= 0 && carry; i--)
        {
            if (bits[i] == false)
            {
                bits[i] = true;
                carry = false;
            }
            else
            {
                bits[i] = false;
            }
        }
    }
    
    public String toString()
    {
        String str = "";
        for (int i = 0; i < bits.length; i++)
        {
            str += (bits[i]) ? "1" : "0";
        }
        
        return str;
    }
}