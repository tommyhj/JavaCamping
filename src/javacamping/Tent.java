package javacamping;

import java.io.Serializable;

/** Class for handling tent objects
 *
 * @author Tommy Hjertberg
 * @version 2017-08-13
 */
public class Tent extends Bookable implements Serializable {

    /** Constructor for tent objects
     *
     * @param serialNumber
     */
    public Tent(int serialNumber) {
        this.serialNumber = serialNumber;
        this.kind = "Tältplats";
    }    
}
