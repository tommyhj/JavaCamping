package javacamping;

import java.io.Serializable;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;

/** Class for handling bookable objects
 *
 * @author Tommy Hjertberg
 * @version 2017-08-13
 */
abstract public class Bookable implements Serializable {

    /** A number given to each bookable. Used to make a difference between otherwise indefferentiable bookables (all but cabins).
     *
     */
    public int serialNumber;

    /** Stores a description of what kind of bookable it is (cabin etc).
     *
     */
    public String kind;
    
    /** Getter for serialNumber
     *
     * @return returns a string of the serial number of the bookable
     */
    public int getSerialNumber() {
        return serialNumber;
        
    }
    
    /** Determines if a booking is within a period
     *
     * @param from A LocalDate for the date to search from
     * @param until A LocalDate for the date to search until
     * @param reservationList The ArrayList of Reservation-object to check against
     * @return
     */
    public boolean isBooked(LocalDate from, LocalDate until, ArrayList<Reservation> reservationList){
        for (Reservation r: reservationList){
            if (r.getBookable() == this){
                for (int i=0; i < from.until(until, DAYS);i++){
                    if (r.containsDate(from.plusDays(i))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** Getter for desciption of bookable
     *
     * @return returns the description of the bookable
     */
    public String getKind() {
        return kind;
    }
    
    /** toString override. Returns only the serial Number
     *
     * @return the serial number of the bookable
     */
    @Override
    public String toString() {
        return String.valueOf(serialNumber);
    }
    
    /** Placeholder getter. Does nothing for most bookables and is overridden by those who need it
     *
     * @return null
     */
    public String getName() {
        return null;
    }

}
