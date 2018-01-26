/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */

package Memory;

import CPU.*;
import CPU.OpCode.WordInstructions;

/**
*ROMLoader Class
* 
*This class represents the ROM "bootstrap" instructions meant to startup 
*the system when the IPL on the console is activated.
*******************************************************************************/

public class ROMLoader
{   
    // List of pre-defined words that are contained as part of rom
    private final int[] intWord6 =  {1,0,0,0,0,1,0,1,0,0,1,1,0,1,1,1}; //address 6
    private final int[] intWord7 =  {1,0,0,0,0,1,1,0,0,0,1,1,1,0,0,0}; //address 7
    private final int[] intWord8 =  {1,0,0,0,0,1,1,1,0,0,1,1,1,0,0,1}; //address 8
    private final int[] intWord9 =  {0,0,0,0,0,1,0,0,0,0,0,1,0,1,1,0}; //address 9 
    private final int[] intWord10 = {0,0,0,0,0,1,0,0,0,0,0,1,0,1,1,1}; //address 10
    private final int[] intWord11 = {0,0,0,0,0,1,0,0,0,1,0,1,1,0,0,0}; //address 11
    private final int[] intWord12 = {0,0,0,0,0,1,0,0,1,0,0,1,1,0,0,1}; //address 12
    private final int[] intWord13 = {0,0,0,0,1,0,0,0,0,0,0,1,1,0,0,1}; //address 13    
    private final int[] intWord14 = {0,0,0,0,1,0,0,0,0,1,1,1,1,0,1,0}; //address 14
    private final int[] intWord15 = {0,0,0,1,0,0,0,0,1,0,1,1,1,0,1,0}; //address 15
    private final int[] intWord16 = {0,0,0,1,0,1,0,1,1,0,0,1,1,0,0,0}; //address 16
    private final int[] intWord17 = {0,0,0,0,1,0,0,0,1,0,1,1,1,0,0,1}; //address 17
    private final int[] intWord18 = {0,0,0,0,1,1,0,0,0,0,0,1,1,0,0,0}; //address 18
    private final int[] intWord19 = {0,0,0,0,1,1,0,0,0,1,0,1,0,1,1,1}; //address 19
    private final int[] intWord20 = {0,0,0,0,1,1,0,0,1,0,1,1,0,1,0,0}; //address 20
    private final int[] intWord21 = {0,0,0,0,1,1,0,0,1,1,1,1,0,1,0,1}; //address 21
    private final int[] intWord22 = {1,0,0,0,1,0,0,1,0,0,0,1,1,0,0,1}; //address 22
    private final int[] intWord23 = {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0}; //address 23
    private final int[] intWord24 = {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0}; //address 24
    private final int[] intWord25 = {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0}; //address 25
    private final int[] intWord26 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //address 26
    private final int[] intWord27 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //address 27
    private final int[] intWord28 = {0,0,0,1,1,0,0,0,0,0,0,0,1,0,0,0};
    private final int[] intWord29 = {0,0,0,1,1,0,0,0,0,1,0,0,0,1,1,0};
    private final int[] intWord30 = {0,0,0,1,1,0,0,0,1,0,0,0,1,1,1,0};
    private final int[] intWord31 = {0,0,0,0,1,0,0,0,0,0,0,1,0,1,1,1};  
    private final int[] intWord32 = {0,0,0,0,1,0,0,0,0,1,0,1,1,0,0,0};
    private final int[] intWord33 = {0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,1};
    
    private final int WORD_BITS = 16;
    
    // Definition of bit size for each word...
    private final int OPCODE_LENGTH = 6;
    private final int IX_REG_LENGTH = 2;
    private final int GPR_LENGTH = 2;
    private final int IA_BIT_LENGTH = 1;
    private final int ADDRESS_LENGTH = 5;
    
    private final int FIRST_INST_ADDRESS = 6;
    
   /**
    *ROMLoader
    *
    *@param - toLoad - The memory to initialize with these ROM instructions
    * 
    *Class will take a memory and load several locations with instructions
    ***************************************************************************/
    public ROMLoader(CPU toLoad)
    {
        Object[] wordArray = {intWord28,//AIR           -> Load GPR0 with immmediate value 8 
                              intWord29,//AIR           -> Load GPR1 with immediate value 6
                              intWord30,//AIR           -> Load GPR2 with immediate value 14
                              intWord31,//STR           -> Store GPR0 into memory location 23
                              intWord32,//STR           -> Store GPR1 into memory location 24
                              intWord33,//STR           -> Store GPR2 into memory location 25
                              intWord6, //LDX(0,1,23,0) -> Load index register 1 with memory location 23
                              intWord7, //LDX(0,2,24,0)	-> Load index register 2 with memory location 24
                              intWord8, //LDX(0,3,25,1)	-> Load index register 3 with memory location 25
                              intWord9, //LDR(0,0,22,0)	-> Load general register 0 with contents of the memory location 22
                              intWord10,//LDR(0,1,23,0)	-> Load general register 1 with contents of the memory location 23
                              intWord11,//LDR(0,1,24,0)	-> Load general register 2 with contents of the memory location 24
                              intWord12,//LDR(0,1,25,0)	-> Load general register 2 with contents of the memory location 25
                              intWord13,//STR(0,0,25,0)	-> Store the value of general register 0 to memory location 25
                              intWord14,//STR(0,1,26,0)	-> Store the value of general register 1 to memory location 26 
                              intWord15,//AMR(0,2,26,1)	-> Add memory location 26 to register 0
                              intWord16,//SMR(1,2,24,0) -> Substract memory location 24 to register 2 
                              intWord17,//STR(0,3,30,1)	-> Store the value of general register 3 to memory location 25
                              intWord18,//LDA(0,0,24,0)	-> Load general register 0 with address 24
                              intWord19,//LDA(0,1,23,0)	-> Load general register 1 with address 23
                              intWord20,//LDA(0,2,20,1)	-> Load general register 2 with address 20
                              intWord21,//LDA(0,3,21,1)	-> Load general register 3 with address 21
                              intWord22,//STX(0,1,25,0) -> Store index register 1 to memory location 25
                              intWord26,//Empty
                              intWord27};//Empty
        
        toLoad.simulatedIR.setValue(FIRST_INST_ADDRESS);
        
        for (int itr = FIRST_INST_ADDRESS; itr < wordArray.length + FIRST_INST_ADDRESS; itr++)
        {
            toLoad.simulatedPC.setValue(toLoad.simulatedIR.getDecimalValue());
            toLoad.simulatedIR.setValue(itr + 1);
            toLoad.ALULogic(convertWordToInstruction((int[]) wordArray[itr - FIRST_INST_ADDRESS]));
        }
    }
    
    public int getFirstInstAddress() {return FIRST_INST_ADDRESS;}
    
   /**
    * convertWordToInstruction
    * 
    * @param toConvert - 16 bit word to convert to an instruction
    * 
    * @return - a WordInstruction that is formatted properly
    * 
    * Method will convert each bit into each component of the instruction
    ***************************************************************************/
    private WordInstructions convertWordToInstruction(int[] toConvert)
    {
        WordInstructions tempInstruction = new WordInstructions();
        int itr = WORD_BITS - 1;
        
        for (int tempItr = ADDRESS_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert[itr] != 0)
                tempInstruction.getAddressCode().toggleBit(ADDRESS_LENGTH-tempItr);
       
        for (int tempItr = IA_BIT_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert[itr] != 0)
                tempInstruction.getIABit().toggleBit(IA_BIT_LENGTH-tempItr);
        
        for (int tempItr = GPR_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert[itr] != 0)
                tempInstruction.getRegCode().toggleBit(GPR_LENGTH-tempItr);  
        
        for (int tempItr = IX_REG_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert[itr] != 0)
                tempInstruction.getIxRegCode().toggleBit(IX_REG_LENGTH-tempItr);
        
        for (int tempItr = OPCODE_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert[itr] != 0)
                tempInstruction.getOpCode().toggleBit(OPCODE_LENGTH-tempItr);
        
        return tempInstruction;
    }   
    
    private WordInstructions convertWordToInstruction(Word toConvert)
    {
        WordInstructions tempInstruction = new WordInstructions();
        int itr = WORD_BITS - 1;
        
        for (int tempItr = ADDRESS_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert.getBits()[itr] != false)
                tempInstruction.getAddressCode().toggleBit(ADDRESS_LENGTH-tempItr);
       
        for (int tempItr = IA_BIT_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert.getBits()[itr] != false)
                tempInstruction.getIABit().toggleBit(IA_BIT_LENGTH-tempItr);
        
        for (int tempItr = GPR_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert.getBits()[itr] != false)
                tempInstruction.getRegCode().toggleBit(GPR_LENGTH-tempItr);  
        
        for (int tempItr = IX_REG_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert.getBits()[itr] != false)
                tempInstruction.getIxRegCode().toggleBit(IX_REG_LENGTH-tempItr);
        
        for (int tempItr = OPCODE_LENGTH; tempItr > 0; tempItr--, itr--)
            if (toConvert.getBits()[itr] != false)
                tempInstruction.getOpCode().toggleBit(OPCODE_LENGTH-tempItr);
        
        return tempInstruction;
    }    
}