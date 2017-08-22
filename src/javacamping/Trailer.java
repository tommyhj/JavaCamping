package javacamping;

import java.io.Serializable;
import java.util.Random;

/** Class for handling trailer objects
 *
 * @author Tommy Hjertberg
 * @version 2017-08-13
 */
public class Trailer extends Bookable implements Serializable  {
    private int powerMeter;

    /** Constructor for trailer objects
     *
     * @param serialNumber
     */
    public Trailer(int serialNumber) {
        Random rand = new Random(); 
        int randomNumber = (rand.nextInt(2000) + 2000); 
        this.serialNumber = serialNumber;
        this.powerMeter = randomNumber;
        this.kind = "Parkering för husvagn/-bil";
    }
  
}
