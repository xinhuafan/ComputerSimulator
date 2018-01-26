/* General class to check if certain method-entry conditions are true
 * Will try to move this so all classes use it.
 */

package CPU.OpCode;

public class Check
{
    // Check that this object is not null, throw an error if it is.
    public static void NotNull(Object o, String message)
    {
        if (o == null)
        {
            Exception e = new NullPointerException(message);
            System.out.println(e);
            System.exit(-1);
        }
    }

    // Check that the index we're looking at is in the bounds of the array
    // length. If it's not, throw an error.
    public static void InBounds(int length, int i, String message)
    {
        if (i < 0 || i >= length)
        {
            Exception e = new ArrayIndexOutOfBoundsException(message);
            System.out.println(e);
            System.exit(-1);
        }
    }
    
    // Check that the given expression evaluates to true
    public static void IsTrue(boolean b, String message)
    {
        if (!b)
        {
            Exception e = new Exception(message);
            System.out.println(e);
            System.exit(-1);
        }
    }
}