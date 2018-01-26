/**
 * @Author Jeremy Case, Xinhua Fan, Teodor Georgiev, Julie Yu
 * George Washington University
 * CSCI 6461:  Computer Architecture
 */
package Memory;

import java.util.HashMap;
import java.lang.Integer;
import java.util.Map.Entry;
import java.util.Objects;

/**
*Memory
*
*This class represents the machine's memory.  Each memory location contains
*a 16 bit word.  For encapsulation purposes, memory is represented as a map
*where the 16 bit word at each address can be access via an integer 
*that itself is a key value.
*******************************************************************************/

public class Memory
{
    // Establish the memory to initally handle 2048 words
    public HashMap<Integer, Word> memory = 
            new HashMap<Integer, Word>(MAXWORDS);
    
    // Defines the max amount of memory locations allowed in the memory...
    private static final int MAXWORDS = 2048;
    
    // Reserved memory locations
    private final int ZERO = 0; // TRAP instruction
    private final int ONE = 1;  // Machine Fault
    private final int TWO = 2;  // Store PC for this trap
    private final int THREE = 3;// Not used 
    private final int FOUR = 4; // Store PC for machine fault
    private final int FIVE = 5; // Not used
    private final Word wordZero = new Word();            
    private final Word wordOne = new Word();
    private final Word wordTwo = new Word();
    private final Word wordThree = new Word();
    private final Word wordFour = new Word();
    private final Word wordFive = new Word();

    
    public Memory()
    {
        // Clear the memory on initilization just in case...
        memory.clear();
        
        // Now place blank words into every memory location...
        memory.put(ZERO, wordZero);
        memory.put(ONE, wordOne);
        memory.put(TWO, wordTwo);
        memory.put(THREE, wordThree);
        memory.put(FOUR, wordFour);
        memory.put(FIVE, wordFive);

        // Populate map with empty addresses
        for (int itr = FIVE + 1; itr < MAXWORDS; itr++)
        {
            Word tempWord = new Word();
            memory.put(itr, tempWord);
        }
        
        for (int itr = 0; itr <= 15; itr++){
            memory.put(10+itr, new Word(itr));
        }
    }
    
    /******************************* Getters **********************************/                                    
    public Word getWordAtAddress(final int addressToGet) {return (memory.get(addressToGet));}
    public int getMaxMemory() {return MAXWORDS;}
    public int getAddressByWord(Word wordToGet){
        for (Entry<Integer, Word> entry : memory.entrySet()){
            if (Objects.equals(wordToGet.getDecimalValue(), entry.getValue().getDecimalValue()))
                return entry.getKey();
        }
        return 100;
    }
    
    /******************************* Setters **********************************/
    public void setWordAtAddress(final int address, Word toSet) {memory.put(address, toSet);}
    
    // Overloaded to handle an integer value
    public void setWordAtAddress(final int address, int toSet)
    {
        Word tempWord = new Word();
        tempWord.setValue(toSet);
        memory.put(address, tempWord);
    }
        
}