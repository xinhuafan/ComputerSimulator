package computersimulator;

import CPU.*;
import CPU.OpCode.*;
import CPU.Registers.*;
import CPU.Registers.Vector.FloatingPointRegister;
import Memory.*;
import Memory.ROMLoader;

public class SimulatorTest 
{
    
    public static void testBitArray()
    {
        System.out.println("Starting bitArray tests");
        
        boolean bits[] = {false, true, false, true, false, true};
        bitArray b = new bitArray(bits);
        b.setSign(false);
        System.out.println("\tCreated bitArray: Success");

        int decimalValue = b.getDecimalValue();
        if (decimalValue == 21)
        {
            System.out.println("\tConvert to decimal: Success");
        }
        else
        {
            System.out.println("\t!!! Convert to decimal: Failure " + 
                    decimalValue);
        }

        b.flipBits();
        decimalValue = b.getDecimalValue();
        if (decimalValue == -22)
        {
            System.out.println("\tFlip bits: Success");
        }
        else
        {
            System.out.println("\t!!! Flip bits: Failure " + decimalValue);
        }

        int octalValue = b.calculateOpCodeInOctal();
        if (octalValue == 26)
        {
            System.out.println("\tConvert to octal: Success");
        }
        else
        {
            System.out.println("\t!!! Convert to octal: Failure " + octalValue);
        }

        b.toggleBit(2);
        decimalValue = b.getDecimalValue();
        if (decimalValue == 18)
        {
            System.out.println("\tFlip selected bit: Success");
        }
        else
        {
            System.out.println("\t!!! Flip selected bit: Failure " 
                    + decimalValue);
        }

        b.shiftBitsRight();
        decimalValue = b.getDecimalValue();
        if (decimalValue == 9)
        {
            System.out.println("\tShift bits right: Success");
        }
        else
        {
            System.out.println("\t!!! shift bits right: Failure");
        }

        b.shiftBitsLeft();
        decimalValue = b.getDecimalValue();
        if (decimalValue == 18)
        {
            System.out.println("\tShift bits left: Success");
        }
        else
        {
            System.out.println("\t!!! shift bits left: Failure");
        }

        System.out.println("Finished testing bitArray");
    }
    
    public static void testRegister()
    {        
        System.out.println("Starting general register tests");
        
        GeneralRegister reggie = new GeneralRegister();
        System.out.println("\tCreate General Register: Success");
        
        reggie.setValue(17);
        boolean[] bits = reggie.getBits();
        System.out.print("\t");
        for (int i = 0; i < bits.length; i++)
        {
            System.out.print((bits[i]) ? "1" : "0");
        }
        System.out.println();
        
        System.out.println("Finished testing registers");
    }
    
    public static void testGeneralRegister()
    {
        System.out.println("Starting general registers test");
        
        GeneralRegister tempReg = new GeneralRegister();
        GeneralRegister tempReg2 = new GeneralRegister();
        
        tempReg.toggleBit(0);
        tempReg2.toggleBit(1);
        
        int sum1 = tempReg.getDecimalValue();
        int sum2 = tempReg2.getDecimalValue();
        
        tempReg.logicalShiftBitsLeft();
        tempReg2.toggleBit(15);
        tempReg2.rotateBitsLeft();
        
        sum1 = tempReg.getDecimalValue();
        
        //tempReg.multiplyRegisterValue(tempReg2);
        
        sum2 = tempReg.getDecimalValue();
        
        System.out.println("Finished testing general registers");
    }
    
    public static void testCache()
    {
        // So... this is kind of a mess.
        // Because cache is supposed to be transparent, I really can't access
        // it too easily unless we do some interface things.
        // And those are a bit too big of a hammer for this nail.
        // So we'll make various getters and setters in cache and hope no one
        // else uses them.
        System.out.println("Starting cache test");
        
        Cache simCache = new Cache();
        
        String[] cacheList = simCache.toArray();
        Check.IsTrue(cacheList.length == 0, "SimulatorTest::cache::wrong size");
        System.out.println("\tCreate Cache: Success");
        
        for (int i = 0; i < simCache.getMaxSize() + 1; i++)
        {
            Word w = new Word();
            int address = i;
            
            simCache.setWordAtAddress(i, w);
        }
        
        cacheList = simCache.toArray();
        Check.IsTrue(cacheList.length == simCache.getMaxSize(),
                "SimulatorTest::cache::wrong size after additions");
        
        for (int i = 0; i < cacheList.length; i++)
        {
            System.out.println("\t" + cacheList[i]);
        }
        
        System.out.println("\tCache additions: Success");

        System.out.println("Finished testing cache");
    }

    public static void testTwosComplement(){
        System.out.println("Starting two's complement test");

        boolean[] bits = {false, true, true, false, false};
        bitArray sample = new bitArray(bits);
        sample.setSign(false);
        int number = sample.getDecimalValue();
        sample.twosComplement();
        int number2 = sample.getDecimalValue();
        
        if (number == -1 * number2)
        {
            System.out.println("\tTwo's Complement test: Success");
        }
        else
        {
            System.out.println("\t!!! two's complement: Failure");
        }

        System.out.println("Finished testing two's complement");
    }
    
    public static void main(String[] args) 
    {
        Memory tempMemory = new Memory();
        System.out.println("Create Memory: Success");
        
        /* The reason this sleeps is because if it doesn't, the print
         * statements look garbled. You'd think Netbeans would be
         * able to avoid that, but nooooo...
         */
        
        try
        {
            testBitArray();
            Thread.sleep(1000);
            testRegister();
            Thread.sleep(1000);
            testGeneralRegister();
            Thread.sleep(1000);
            testCache();
            Thread.sleep(1000);
            testTwosComplement();
            testFloatingPointRegister();
        }
        catch (InterruptedException e)
        {
            System.out.println(e);
        }
    }
    
    public static void testFloatingPointRegister()
    {
        FloatingPointRegister temp = new FloatingPointRegister();
        
        temp.setValue(1000);
        double temp2 = temp.getFloatingPointValue();
    }
}
