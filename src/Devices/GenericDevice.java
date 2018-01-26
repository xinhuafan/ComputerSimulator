/**
 *@Author Jeremy Case
 *George Washington University
 *CSCI 6461:  Computer Architecture
 *****************************************************************************/

package Devices;

/**
*GenericDevice class
* 
*This class represents generic devices for the console 
*******************************************************************************/

public class  GenericDevice
{
    String deviceValue;   
    static ASCiiTable converter = new ASCiiTable();
    
    public GenericDevice()
    {
        // constructor stuff...
    }   
    
    public void setValue(final String toSet) {deviceValue = toSet;}
    
   /**
    *getFirstCharAsBitValue
    * 
    *@return the integer value of the first char of the user's input
    *******************************************************************************/
    public int getLastCharAsIntValue()
    {
        // So long as user input isnt empty, return the last value of the 
        // keyboard's input
        if (!deviceValue.isEmpty())
        {
            String tempString = deviceValue.substring(deviceValue.length() -1);
                    
            // Erase the value from the user's input now that we're retreiving
            // it...
            if (deviceValue.length() > 1)
                deviceValue = deviceValue.substring(0, deviceValue.length() -1);
            else
                deviceValue ="";
            
            return converter.convertStringToInt(tempString);
        }
        
        else 
            return 0;
    }
    
   /**
    *setInputViaInt
    * 
    *@param toConvert the integer to set the user input to once converted
    *******************************************************************************/
    public void setValueViaInt(final int toConvert)
    {
        deviceValue = converter.convertIntToString(toConvert);
    } 
    
    public String getDeviceValue() {return deviceValue;}
}

