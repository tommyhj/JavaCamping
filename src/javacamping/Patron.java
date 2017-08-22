package javacamping;

import java.io.Serializable;

/** Class for handling the customers ("patrons")
 *
 * @author Tommy Hjertberg
 * @version 2017-08-13
 */
public class Patron implements Serializable{
    private String name;
    private String adress;
    private String phoneNr;
    private boolean checkedIn;
    
    /** Constructor for Patron objects
     *
     * @param name
     * @param adress
     * @param phoneNr
     */
    public Patron(String name, String adress, String phoneNr) {
        this.name = name;
        this.adress = adress;
        this.phoneNr = phoneNr;
        this.checkedIn = false;

    }

    /** Getter for the name of the patron
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /** Setter for setting the name of the patron
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for the patrons adress
     *
     * @return address
     */
    public String getAdress() {
        return adress;
    }

    /**
     *
     * @param adress
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }

    /** Getter for the patrons phone number
     *
     * @return the phone number as string
     */
    public String getPhoneNr() {
        return phoneNr;
    }

    /** Setter for the patrons phone number
     *
     * @param phoneNr Phonenumber as string
     */
    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    /** Checks if the patron is checked in
     *
     * @return
     */
    public boolean isCheckedIn() {
        return checkedIn;
    }

    /** Sets the patron as checked in
     *
     * @param checkedIn
     */
    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    /** Returns the name of the patron
     *
     * @return
     */
    @Override
    public String toString() {
        return name;
    }
    
    
}
