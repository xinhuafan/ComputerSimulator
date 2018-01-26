package CPU.Registers;

import CPU.OpCode.bitArray;

/**
 *
 * @author julieyu
 */
public class ConditionCodeRegister extends Register{
    
    final int CONDITION_CODE_SIZE = 4;
    
    public final static int OVERFLOW=1;
    public final static int UNDERFLOW=2;
    public final static int DIVZERO=4;
    public final static int EQUALORNOT=8;
    public final static int NONE=0;
    
    public ConditionCodeRegister()
    {        
        boolean[] tempArray = new boolean[CONDITION_CODE_SIZE];
        
        // Default each bit to zero (false)
        for (int itr = 0; itr < tempArray.length; itr++)
        {
            tempArray[itr] = false;
        }
        
        this.bits = tempArray;
        unsigned = true;
    }
    
    //Setters
    
    public void setCondition(final int code){
        this.setValue(code);
    }
    
}
