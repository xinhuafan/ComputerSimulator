/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package TestPrograms;

import CPU.*;
import CPU.OpCode.WordInstructions;
import Devices.ASCiiTable;

/**
* TestProgram2
* 
* This program will take a 6-sentence user input.  Then the program will prompt
* a user for a word to be searched.  The program will look through the 6 
* sentences to find the word to be searched for, and will output that word if it 
* finds it. When a match is found, the program will output the word number of 
* the sentence number the word was found in.
* 
* This program works in several steps...
* 
* 1) The program will input the entire user input char-by-char into memory
* starting at memory location 200.
* 
* 2) When a user inputs a word to be searched for, it will place that word char-
* by-char into memory starting at memory location 1900.  
* 
* 3) The program will then get the user input length (IE, the amount of chars)
* so that the rest of the program can use this information.
* 
* 4) The program will then proceed to step through every char of the 6 sentences
* input.  It has several memory locations reserved where it will store 
* "counters": a sentence counter, word counter, match counter, etc.
* 
* 5) Each char will be compared against several chars loaded into memory...
* 
* 5A) When the program finds a space, it will increment the word counter since 
* a space represents a new word.
* 
* 5B) When the program finds a period, it will increment the sentence counter AND
* set the word counter back to 1 since were are now in a new sentence and with
* the first word of that sentence.
* 
* 5C) Then the program will compare the current char against the user's last 
* input which has been saved into memory.  Whether or not a match is found can
* cause two different events to occur
* 
* 5C1) If a match is found, the program will increment the match counter so
* that the program knows to compare each char against (user input location 1 +
* counter).
* 
* 5C2) If a match is not found, the program will set the match counter back to
* zero.
* 
* 6) When a match is found AND the match counter matches the user input length,
* we know that we have found a matching word.  
* 
* 7) When this occurs, the program will load from memory a few counters, and 
* allow the console to output those numbers.
*/

public class TestProgram2
{
    private boolean match = false;
    
    // Pre-loaded ASCii chars to look for
    private final int SPACE = 32;
    private final int PERIOD = 46;
    
    // Pre-loaded opcode instructions in decmial value
    private final int LDR = 1;
    private final int STR = 2;
    private final int LDA = 3;
    private final int AIR = 6;
    private final int TRR = 18;
    private final int LDX = 33;
    
    // Our GPRs as referenced by intuitive variable names
    private final int GPR1 = 1;
    private final int GPR2 = 2;
    private final int GPR3 = 3;
    
    // Our IX regsiters as referenced by intuitive variable names
    private final int IXREG1 = 1;
    private final int IXREG2 = 2;
    private final int IXREG3 = 3;
    
    private final int INCREMENT = 1;
    
    private final int CC_REG_SET_TO_EQUALORNOT = 8;
    
    // Load GPR0 with the char referenced by IX Reg 2's memory
    private WordInstructions ldrGPR0WithCharAtIXReg2 = new WordInstructions();
    
    // LDR and STR GPR0 to the word counter address
    private WordInstructions ldrGPR0WithWordCounter = new WordInstructions();
    private WordInstructions strGPR0ToWordCounter = new WordInstructions();
    
    // LDR and STR GPR1 to the sentence coutner address
    private WordInstructions ldrGPR1WithSentenceCounter = new WordInstructions();
    private WordInstructions strGPR1ToSentenceCounter = new WordInstructions();
    
    // Convenient incrementer instructions
    private WordInstructions incrementGPR0 = new WordInstructions();
    private WordInstructions incrementGPR1 = new WordInstructions();
    
    // Ask teh CPU if GPR2 and GPR3 are equal
    private WordInstructions areGPR2AndGPR3Equal = new WordInstructions();
    
    // Str the user input length to memory
    private WordInstructions strUserInputLength = new WordInstructions();
    
    // Load GPR0 with the address at IX Reg 3 that contains the current Char
    // iteration's memory location
    private WordInstructions ldaGPR0WithIXReg3ForCharLocation = new WordInstructions();
    
    // STR gpr 0 to IX reg 3 so that IXREg 3 contains the latest char iteration
    private WordInstructions strGPR0ToIXReg3ForCharLocation = new WordInstructions();
    
    // LDX IX Reg 3 with the char's current location
    private WordInstructions ldxIXReg3WithCharLocation = new WordInstructions();
            
    // LDR gpr2 with the current char to be compared against
    private WordInstructions ldrGPR2WithIXReg3Char = new WordInstructions();
    
    // LDR and STR GPR0 to the match counter memory location
    private WordInstructions ldrGPR0WithMatchCounter = new WordInstructions();
    private WordInstructions strGPR0ToMatchCounter = new WordInstructions();
    
    // LDR GPR3 with the currently-referenced user input char
    private WordInstructions ldrGPR3WithUserChar = new WordInstructions();
    
    // LDR GPR1 with the user input length
    private WordInstructions ldrGPR1WithUserInputLength = new WordInstructions();
    
    // Ask the CPU whether GPR0 and GPR1 are equal
    private WordInstructions areGPR0andGPR1Equal = new WordInstructions();
    
    // LDX IXReg 3 with the current memory address locatoin
    private WordInstructions ldxIXReg3WithCurrentLocation = new WordInstructions();
    
   /* Memory locatiobn 97 contains the Match counter, IE, the current number
    * of chars matching.  This number will determine when we have foudn a match.
    * As it gets incremented, we will utilize the number to compare the current
    * char against different chars from the user's final input (IE, the word
    * that we are to look for).
    ***************************************************************************/
    private final int USER_MATCH_COUNTER_ADDRESS = 97;
    
    /* Memory location 98 contains the word counter.  It will get incremented
    * we find spaces in the program.  The counter represents the current word
    * we are at in the program.
    ***************************************************************************/
    private final int WORD_COUNTER_ADDRESS = 98;
    
    /* Memory location 99 contains the sentence coutner.  It will get incremneted
    * as we run into periods within the program.  The counter represents the 
    * current sentence number we are at.
    ***************************************************************************/
    private final int SENTENCE_COUNTER_ADDRESS = 99;
    
    /* Memory location 100 simply contains the length of user input, IE, the 
    * number of chars that we are to look for for an entire match.
    ***************************************************************************/
    private final int USER_INPUT_LENGTH_ADDRESS = 100;
    
    /* Memory location 200 contains the first char of the entire 6 sentences
    * Every memory location thereafter contains a char.
    ***************************************************************************/
    private final int SENTENCE_INITIAL_ADDRESS = 200;
    
    /* Memory location 1900 contains the first char of the last word input by 
    * the user.  Every memory location thereafter contains a char from that 
    * word.  We will use these memory locations to compare against the chars
    * located at locations 200+ until we find a match.
    ***************************************************************************/
    private final int USER_INITIAL_ADDRESS = 1900;
    
    public TestProgram2(CPU cpu)
    {
        // Load each instruction's binary values to do what we want...
        ldrGPR0WithCharAtIXReg2.getIxRegCode().setValue(IXREG2);
        ldrGPR0WithCharAtIXReg2.getOpCode().setValue(LDR);
        ldrGPR0WithCharAtIXReg2.getIABit().setValue(1);
        
        incrementGPR0.getOpCode().setValue(AIR);
        incrementGPR0.getAddressCode().setValue(INCREMENT);
        
        incrementGPR1.getRegCode().setValue(GPR1);
        incrementGPR1.getOpCode().setValue(AIR);
        incrementGPR1.getAddressCode().setValue(INCREMENT);
        
        strUserInputLength.getIABit().setValue(1);
        strUserInputLength.getIxRegCode().setValue(IXREG1);
        strUserInputLength.getOpCode().setValue(STR);
        strUserInputLength.getRegCode().setValue(GPR1);
        
        ldrGPR0WithWordCounter.getIABit().setValue(1);
        ldrGPR0WithWordCounter.getIxRegCode().setValue(IXREG1);
        ldrGPR0WithWordCounter.getOpCode().setValue(LDR);
        
        strGPR0ToWordCounter.getIABit().setValue(1);
        strGPR0ToWordCounter.getIxRegCode().setValue(IXREG1);
        strGPR0ToWordCounter.getOpCode().setValue(STR);
        
        ldrGPR1WithSentenceCounter.getIABit().setValue(1);
        ldrGPR1WithSentenceCounter.getOpCode().setValue(LDR);
        ldrGPR1WithSentenceCounter.getIxRegCode().setValue(IXREG2);
        ldrGPR1WithSentenceCounter.getRegCode().setValue(GPR1);
        
        strGPR1ToSentenceCounter.getIABit().setValue(1);
        strGPR1ToSentenceCounter.getOpCode().setValue(STR);
        strGPR1ToSentenceCounter.getIxRegCode().setValue(IXREG2);
        strGPR1ToSentenceCounter.getRegCode().setValue(GPR1);
        
        ldaGPR0WithIXReg3ForCharLocation.getIxRegCode().setValue(IXREG3);
        ldaGPR0WithIXReg3ForCharLocation.getOpCode().setValue(LDA);
        ldaGPR0WithIXReg3ForCharLocation.getIABit().setValue(1);
        
        ldrGPR2WithIXReg3Char.getIxRegCode().setValue(IXREG3);
        ldrGPR2WithIXReg3Char.getRegCode().setValue(GPR2);
        ldrGPR2WithIXReg3Char.getIABit().setValue(1);
        ldrGPR2WithIXReg3Char.getOpCode().setValue(LDR);
        
        areGPR2AndGPR3Equal.getOpCode().setValue(TRR);
        areGPR2AndGPR3Equal.getRegCode().setValue(GPR2);
        areGPR2AndGPR3Equal.getIxRegCode().setValue(GPR3);
        
        ldxIXReg3WithCharLocation.getOpCode().setValue(LDX);
        ldxIXReg3WithCharLocation.getIABit().setValue(1);
        ldxIXReg3WithCharLocation.getIxRegCode().setValue(IXREG3);
        ldxIXReg3WithCharLocation.getRegCode().setValue(GPR3);
        
        strGPR0ToIXReg3ForCharLocation.getOpCode().setValue(STR);
        strGPR0ToIXReg3ForCharLocation.getIxRegCode().setValue(IXREG3);
        strGPR0ToIXReg3ForCharLocation.getIABit().setValue(1);
        
        ldrGPR0WithMatchCounter.getIABit().setValue(1);
        ldrGPR0WithMatchCounter.getOpCode().setValue(LDR);
        ldrGPR0WithMatchCounter.getIxRegCode().setValue(IXREG2);
        
        strGPR0ToMatchCounter.getIABit().setValue(1);
        strGPR0ToMatchCounter.getIxRegCode().setValue(IXREG2);
        strGPR0ToMatchCounter.getOpCode().setValue(STR);
        
        ldrGPR3WithUserChar.getRegCode().setValue(GPR3);
        ldrGPR3WithUserChar.getIABit().setValue(1);
        ldrGPR3WithUserChar.getOpCode().setValue(LDR);
        ldrGPR3WithUserChar.getIxRegCode().setValue(IXREG1);
        
        ldrGPR1WithUserInputLength.getIABit().setValue(1);
        ldrGPR1WithUserInputLength.getRegCode().setValue(GPR1);
        ldrGPR1WithUserInputLength.getIxRegCode().setValue(IXREG1);
        ldrGPR1WithUserInputLength.getOpCode().setValue(LDR);
        
        areGPR0andGPR1Equal.getOpCode().setValue(TRR);
        areGPR0andGPR1Equal.getIxRegCode().setValue(GPR1);
        
        ldxIXReg3WithCurrentLocation.getIABit().setValue(1);
        ldxIXReg3WithCurrentLocation.getRegCode().setValue(IXREG3);
        ldxIXReg3WithCurrentLocation.getIxRegCode().setValue(IXREG3);
        ldxIXReg3WithCurrentLocation.getOpCode().setValue(LDX);
        
        cpu.simulatedIXReg1.setValue(USER_INPUT_LENGTH_ADDRESS);
        cpu.simulatedIXReg2.setValue(SENTENCE_INITIAL_ADDRESS);
        cpu.simulatedIXReg3.setValue(USER_INITIAL_ADDRESS);
        
        getUserInputLength(cpu);
        loopThroughEntireUserInput(cpu);
    }
    
   /**
    * getUserInputLength
    * 
    * This method will get the user input length of the last user input 
    * (the word to be searched)
    * 
    * 1) While GPR0 doesn't reference a blank memory location 
    *    2) load GPR0 with the char referenced by IXReg 2 memory address
    *    3) increment GPR1 for every loop
    * 
    * 4) When the loop terminates, store the user input length to memory 
    ***************************************************************************/
    private void getUserInputLength(CPU cpu)
    {   
        // Until we run into a null memory location, keep grabbing values...
        for (int itr = 0; cpu.simulatedGPR0.getDecimalValue() != 0; itr++)
        {
            // Utilize the IXRegister to keep track of our location as 
            // we loop through user input
            cpu.simulatedIXReg2.setValue(USER_INITIAL_ADDRESS + itr);
            
            // Each iteration, load the GPR with the current char
            cpu.ALULogic(ldrGPR0WithCharAtIXReg2);
            
            // Now increment GPR1 to keep track of user input length
            cpu.ALULogic(incrementGPR1);
        }

        // When loop terminates, set GPR1 to the user input length
        cpu.simulatedGPR1.setValue(cpu.simulatedGPR1.getDecimalValue() - 1);
        
        // Now store that length to memory....
        cpu.ALULogic(strUserInputLength);
    }
    
   /**
    * loopThroughEntireUserInput
    * 
    * This method will loop through every char until it finds a null memory
    * location.  For extremely large user inputs, it will not work.  
    * 
    * Each step of this loop will be explained below.
    */
    
    private void loopThroughEntireUserInput(CPU cpu)
    {
        // IXReg 1 will now reference word counter address
        cpu.simulatedIXReg1.setValue(WORD_COUNTER_ADDRESS);
        
        // IXReg 2 will now reference sentence counter
        cpu.simulatedIXReg2.setValue(SENTENCE_COUNTER_ADDRESS);
        
        // IXReg 3 will now reference first char of entire input
        cpu.simulatedIXReg3.setValue(SENTENCE_INITIAL_ADDRESS);
        
        // Set our GPR's to 1 to denote the first word of the first sentence
        cpu.simulatedGPR0.setValue(1);
        cpu.simulatedGPR1.setValue(1);  
        
        // Set GPR to 1 so that program knows not to terminate (since it does
        // so when GPR2 equals 0)
        cpu.simulatedGPR2.setValue(1);
        
        // Tell the CPU to store the registers' values to memory
        cpu.ALULogic(strGPR0ToWordCounter);
        cpu.ALULogic(strGPR1ToSentenceCounter);
        
        // Loop through every char of the entire user input
        for (int itr = 0; cpu.simulatedGPR2.getDecimalValue() != 0; itr++)
        {
            // Verify if current char is a space so that we may increment
            // the word counter...
            isCharASpace(cpu);
            
            // Verify if current char is a periods, so that we may increment
            // the sentence counter
            isCharAPeriod(cpu);
            
            // load GPR0 with current memory location of char we are to compare
            cpu.ALULogic(ldaGPR0WithIXReg3ForCharLocation);

            // Set IXReg 3 to that memory address
            cpu.simulatedIXReg3.setValue(cpu.simulatedGPR0.getDecimalValue());
            
            // And now see if that char matches the currently-referenced user
            // char of the word to look for
            doesCurrentCharMatchUserChar(cpu);
            
            // Now load GPR0 with the current char's memory location...
            cpu.ALULogic(ldaGPR0WithIXReg3ForCharLocation);
            
            // ...and increment it to denote the next char to look for...
            cpu.ALULogic(incrementGPR0);
            
            // ...and store to memory.
            cpu.ALULogic(strGPR0ToIXReg3ForCharLocation);            
            cpu.ALULogic(ldxIXReg3WithCurrentLocation);
            
            // Set IX Reg back to user input length mmeory address
            cpu.simulatedIXReg1.setValue(USER_INPUT_LENGTH_ADDRESS);
            
            // LDR GPR 1 with that value
            cpu.ALULogic(ldrGPR1WithUserInputLength);
            
            // Set IX reg 1 to the current match counter
            cpu.simulatedIXReg1.setValue(USER_MATCH_COUNTER_ADDRESS);
            
            // Load GPR0 with that value
            cpu.ALULogic(ldrGPR0WithMatchCounter);
            
            // Now see if GPR0 equals the user input length...
            cpu.ALULogic(areGPR0andGPR1Equal);
            
            // IF it does, we have found the word we are looking for.
            if (cpu.simulatedCC.getDecimalValue() == CC_REG_SET_TO_EQUALORNOT)
            {
                // Now set the registers to the values necessary for an output
                cpu.simulatedIXReg1.setValue(WORD_COUNTER_ADDRESS);
                cpu.simulatedIXReg2.setValue(SENTENCE_COUNTER_ADDRESS);
                cpu.simulatedCC.setValue(0);
                cpu.ALULogic(ldrGPR0WithWordCounter);
                cpu.ALULogic(ldrGPR1WithSentenceCounter);
                match = true;
                break; 
            }
        }
        
    }
   
   /**
    * isCharASpace
    * 
    * 1) This method will load GPR3 with an int value representing the space char
    * 2) It will then load GPR2 with the char referenced by the memory location of
    * IXReg3.
    * 3) It will then ask the CPU if GPR 3 and 2 are equal
    * 4) If they are, it will then increment the "word counter" address at 
    * IXReg 1
    ***************************************************************************/
    private void isCharASpace(CPU cpu)
    {
        // Load IX Reg 1 with the address containing the word counter
        cpu.simulatedIXReg1.setValue(WORD_COUNTER_ADDRESS);
        
        // Set GPR3 to the ASCii value of a space char
        cpu.simulatedGPR3.setValue(SPACE);
        
        // Load GPR2 with the char at IXReg 3
        cpu.ALULogic(ldrGPR2WithIXReg3Char);
        
        // See if GPR2 and GPR3 are both space ASCii values
        cpu.ALULogic(areGPR2AndGPR3Equal);
        
        // if they are, we have found a new word...
        if (cpu.simulatedCC.getDecimalValue() == CC_REG_SET_TO_EQUALORNOT)
        {
            cpu.simulatedCC.setValue(0);
            
            // So set GPR0 to the word counter
            cpu.ALULogic(ldrGPR0WithWordCounter);
            
            // increment it...
            cpu.ALULogic(incrementGPR0);
            
            // and store it to memory
            cpu.ALULogic(strGPR0ToWordCounter);
        }
    }
    
    /**
    * isCharAPeriod
    * 
    * 1) This method will load GPR3 with an int value representing the period char
    * 2) It will then load GPR2 with the char referenced by the memory location of
    * IXReg3.
    * 3) It will then ask the CPU if GPR 3 and 2 are equal
    * 4) If they are, it will then increment the "sentence counter" address at 
    * IXReg 3
    * 5) If the are equal, it will also reset the word counter since we are now
    * in a new sentence.
    ***************************************************************************/
    private void isCharAPeriod(CPU cpu)
    {
        // Load IX Reg 2 with the sentence counter address
        cpu.simulatedIXReg2.setValue(SENTENCE_COUNTER_ADDRESS);
        
        // Set GPR3 to the ASCii value for a period
        cpu.simulatedGPR3.setValue(PERIOD);
        
        // Now see if GPR3 and GPR2 both equal the ASCii value for period
        cpu.ALULogic(areGPR2AndGPR3Equal);
        
        // If they do, we have found a new sentence
        if (cpu.simulatedCC.getDecimalValue() == CC_REG_SET_TO_EQUALORNOT)
        {
            cpu.simulatedCC.setValue(0);
            
            // LDR GPR 1 with the sentence counter and increment it
            cpu.ALULogic(ldrGPR1WithSentenceCounter);
            cpu.ALULogic(incrementGPR1);
            cpu.ALULogic(strGPR1ToSentenceCounter);
            
            // Since we have a new sentence, we have a new word
            cpu.simulatedGPR0.setValue(0);
            
            // So reset the word counter
            cpu.ALULogic(strGPR0ToWordCounter);
        }
    }
    
    /**
    * doesCurrentCharMatchUserChar
    * 
    * This method will load GPR 3 with a char representing the user input as
    * determined by a counter loaded into GPR0.  Then the method will compare
    * whether or not GPR2 (the current char of the entire user input) matches
    * GPR 3 (the current char of the word to look for).  If they are equal,
    * the method will increment GPR0.  If not, it will set GPR0 to zero.  Either
    * way, the method will store GPR0's value to memory (the current match 
    * counter)
    ***************************************************************************/
    private void doesCurrentCharMatchUserChar(CPU cpu)
    {
        cpu.simulatedIXReg2.setValue(USER_MATCH_COUNTER_ADDRESS);
        
        // LDR GPR0 with the current match counter (IE, the number of chars 
        // we have found so far that match the user input word to look for
        cpu.ALULogic(ldrGPR0WithMatchCounter);
        
        // Load IXreg1 with the memory address for user input
        cpu.simulatedIXReg1.setValue(USER_INITIAL_ADDRESS);
        
        // Load GPR3 with the char we are currently looking to compare with.
        // This value changes as the match counter is incremented, so we can 
        // look at something like user input char at location 0 + match counter
        ldrGPR3WithUserChar.getAddressCode().setValue(cpu.simulatedGPR0.getDecimalValue());
        
        // Now load GPR3 with that char
        cpu.ALULogic(ldrGPR3WithUserChar);
        
        // See if current char in sentence and current user input char are a 
        // match
        cpu.ALULogic(areGPR2AndGPR3Equal);
        
        // If they are, we have found a matching char
        if (cpu.simulatedCC.getDecimalValue() == CC_REG_SET_TO_EQUALORNOT)
        {
            // So increment the match counter
            cpu.simulatedCC.setValue(0);
            cpu.ALULogic(incrementGPR0);
            cpu.ALULogic(strGPR0ToMatchCounter);
        }
        
        // If the chars don't match, reset the match counter to zero
        else
        {
            cpu.simulatedGPR0.setValue(0);
            cpu.ALULogic(strGPR0ToMatchCounter);
        }
    }
    
    public boolean getMatch() {return match;}
}