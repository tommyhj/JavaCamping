package javacamping;
import java.io.Serializable;

/** Handles bookable cabins
 *
 * @author Tommy Hjertberg
 * @version 2017-08-13
 */
public class Cabin extends Bookable implements Serializable {

    /** The name of the cabin
     *
     */
    private final String name;

    /** Constructor for cabins
     *
     * @param name The name to be applied to the cabin
     * @param serialNumber The serial number of the cabin
     */
    public Cabin(String name, int serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.kind = "Stuga";
    }

    /** Returns the name of the cabin
     *
     * @return the name of the cabin
     */
    @Override
    public String getName() {
        return name;
    }

    /** Creates string representing the cabin
     *
     * @return String with name and number of cabin
     */
    @Override
    public String toString() {
        return "Nummer " + String.valueOf(serialNumber) + " " + name ;
    }
    
    
    
}
