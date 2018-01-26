/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package CPU.OpCode;

import static java.lang.System.exit;

/**
*WordInstructions
*  
*It should be noted that currently, instructions utilize a "little-endian"
*structure for the opcode.  EG, the bits are read from the right to the left.
*******************************************************************************/

/**
*Logical Representation of Word Instructions
* 
* |Opcode=O|Register=R|IndexRegister=I|IndirectAddressing=N|Address= A|
* 
*  Bit Representation:
*  |0|1|2|3|4|5|6|7|8|9|0|1|2|3|4|5|
*  |O|O|O|O|O|0|R|R|I|I|N|A|A|A|A|A|
* 
*  Opcode:
*    The opcode is 6 bits (bits 0 - 5) and represents one of 64 possible 
*    operation codes.
* 
*  IndexRegister
*    The index register portion is 2 bits (bits 6-7) and represents one of 
*    3 possible index registers that will be utilized depending on the opcode
* 
*  Register:
*    The register portion is 2 bits (bits 8-9) and represents one of 4 
*    possible registers that will be utilized depending on the opcode
*    
*  IndirectAddressing
*    The indirect addressing portion is 1 bit (bit 10) and determines
*    whether or not the instruction will use indirect addressing
*  
*  Address
*    The address portion is 5 bits (bits 11-15) and determines one of 32
*    locations for the load/store instruction to act upon
*******************************************************************************/

public class WordInstructions 
{
    OpCode opCode = new OpCode();                               // Elements 0-5
    IndexRegisterCode iReg = new IndexRegisterCode();           // Elements 6-7
    RegisterCode register = new RegisterCode();                 // Elements 8-9
    IndirectAddressingCode ia = new IndirectAddressingCode();   // Element 10
    AddressCode address = new AddressCode();                    // Element 11-15
    
    // Each element represents a Logical portion of the instruction
    Object[] instruction = {opCode, register, iReg, ia, address};
                                            
    public WordInstructions()
    {
        // default constructor, set all objects to null....
    }
    
    public WordInstructions(OpCode opCode,
                            IndexRegisterCode IRRef,
                            RegisterCode registerRef,
                            IndirectAddressingCode IAbit,
                            AddressCode address)   
    {
        Check.NotNull(opCode, "WordInstruction::Constructor::null opCode");
        
        this.opCode = opCode;
        instruction[0] = opCode; // need to re-set this, apparently  
        this.address = address;
        instruction[4] = address;
    }
    
    public OpCode getOpCode() {return opCode;}
    public RegisterCode getRegCode() {return register;}
    public IndexRegisterCode getIxRegCode() {return iReg;}
    public IndirectAddressingCode getIABit() {return ia;}
    public AddressCode getAddressCode() {return address;}
    
    /**
    *getInstructionOpCodeValue
    * 
    *@return int - integer representing an octal value
    *  
    *Method will return this opCode's octal value
    ***************************************************************************/
    public int getInstructionOpCodeValue()
    {
        // Verify the first element of the instruction array is an opCode 
        // just in case...
        if (this.opCode instanceof OpCode && this.opCode != null)
        {
            // ...and if it is, then get the opCode value...
            return this.opCode.calculateOpCodeInOctal();
        }
        else
        {
            System.out.println("ERROR: instruction does not contain opcode!");
            exit(-1);
            return 0; // think Java requires a return here even though we're
                      // trying to crash.
        }
    }
    
   
}
    

