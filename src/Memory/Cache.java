/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Memory;

import CPU.OpCode.Check;
import java.util.ArrayList;

/**
 *
 * @author georgit
 */
public class Cache 
{
    private class cacheEntry
    {
        public int address;
        public Word value;
        
        public String toString()
        {
            return address + ": " + value;
        }
    }
    
    // put stuff here. Every time you access it, move it to front
    // if > max size addresses, toss out
    private ArrayList<cacheEntry> cache;
    private Memory simMemory;
    private int cacheMaxSize = 16;
    
    public Cache()
    {
        // passing a size doesn't really DO anything substantial but ok I guess
        this.cache = new ArrayList<cacheEntry>(cacheMaxSize);
        this.simMemory = new Memory();
    }
    
    public int getMaxSize()
    {
        return cacheMaxSize;
    }
    
    public String[] toArray()
    {
        String[] strCache = new String[cache.size()];
        
        for (int i = 0; i < cache.size(); i++)
        {
            cacheEntry e = cache.get(i);
            strCache[i] = e.toString();
        }
        
        return strCache;
    }
        
    public Word getWordAtAddress(final int addressToGet)
    {
        for (int i = 0; i < cache.size(); i++)
        {
            cacheEntry current = cache.get(i);
            if (current.address == addressToGet)
            {
                // need to move current to front
                cache.remove(current);
                cache.add(0, current);
                
                return current.value;
            }
        }
        
        return simMemory.getWordAtAddress(addressToGet);
    }
    
    // this is just to make the gui happy
    public Memory getMemory()
    {
        return this.simMemory;
    }
    
    public void setWordAtAddress(final int address, Word word)
    {
        boolean found = false;
        for (int i = 0; i < cache.size(); i++)
        {
            cacheEntry current = cache.get(i);
            if (current.address == address)
            {
                // write to this one, set it to the first one.
                cache.remove(current);
                current.value = word;
                cache.add(0, current);
                found = true;
                
                break;
            }
        }
        
        if (!found)
        {
            // didn't find it - add to cache
            cacheEntry newEntry = new cacheEntry();
            newEntry.address = address;
            newEntry.value = word;
            cache.add(0, newEntry);
        }
        
        // adjust cache size as needed
        while (cache.size() > cacheMaxSize)
        {
            cache.remove(cache.size() - 1);
        }
        
        // now write through to memory
        simMemory.setWordAtAddress(address, word);

    }
    
    public void setWordAtAddress(final int address, int value)
    {
        Word tempWord = new Word();
        tempWord.setValue(value);
        
        this.setWordAtAddress(address, tempWord);        
    }
}