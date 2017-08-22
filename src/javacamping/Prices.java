package javacamping;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Enumerator for prices
 *
 * @author Tommy Hjertberg
 * @version 2017-08-13
 */
public enum Prices {

    /** The high season day price of a cabin
     *
     */
    CABIN_DAY_HIGHSEASON("Stuga, dygn högsäsong", loadPrice("CABIN_DAY_HIGHSEASON")),

    /**The high season week price of a cabin
     *
     */
    CABIN_WEEK_HIGHSEASON("Stuga, vecka högsäsong", loadPrice("CABIN_WEEK_HIGHSEASON")),

    /**The low season day price of a cabin
     *
     */
    CABIN_DAY_LOWSEASON("Stuga, dygn lågsäsong",loadPrice("CABIN_DAY_LOWSEASON")),

    /**The low season week price of a cabin
     *
     */
    CABIN_WEEK_LOWSEASON("Stuga, vecka lågsäsong",loadPrice("CABIN_WEEK_LOWSEASON")),

    /**The high season day price of a trailer parking
     *
     */
    TRAILER_DAY_HIGHSEASON("Husvagnsparkering, dygn högsäsong", loadPrice("TRAILER_DAY_HIGHSEASON")),

    /**The high season week price of a trailer parking
     *
     */
    TRAILER_WEEK_HIGHSEASON("Husvagnsparkering, vecka högsäsong", loadPrice("TRAILER_WEEK_HIGHSEASON")),

    /**The low season day price of a trailer parking
     *
     */
    TRAILER_DAY_LOWSEASON("Husvagnsparkering, dygn lågsäsong", loadPrice("TRAILER_DAY_LOWSEASON")),

    /**The low season week price of a trailer parking
     *
     */
    TRAILER_WEEK_LOWSEASON("Husvagnsparkering, vecka lågsäsong", loadPrice("TRAILER_WEEK_LOWSEASON")),

    /**The high season day price of a tent place
     *
     */
    TENT_DAY_HIGHSEASON("Tältplats, dygn högsäsong", loadPrice("TENT_DAY_HIGHSEASON")),

    /**The high season week price of a tent place
     *
     */
    TENT_WEEK_HIGHSEASON("Tältplats, vecka högsäsong", loadPrice("TENT_WEEK_HIGHSEASON")),

    /**The low season day price of a tent place
     *
     */
    TENT_DAY_LOWSEASON("Tältplats, dygn lågsäsong", loadPrice("TENT_DAY_LOWSEASON")),

    /**The low season week price of a tent place
     *
     */
    TENT_WEEK_LOWSEASON("Tältplats, vecka lågsäsong", loadPrice("TENT_WEEK_LOWSEASON")),

    /**The price of a kilowatt of electric power
     *
     */
    ELECTRICITY("Elkostnad", loadPrice("ELECTRICITY"));
    
    private final String description;
    private final double price;
    static private final String CSV_PRICELIST = "prices.csv";
    
    Prices (String description, double price) {
        this.description = description;
        this.price = price;    
    }
    /** Loads price from CSV-file to the enum. Only possible on startup. So the program must be restarted if prices change.
     *
     * @param item the the to load price of
     */
    static private double loadPrice(String item){
        try {
            BufferedReader inStream = new BufferedReader
                                          (new InputStreamReader
                                          (new FileInputStream(CSV_PRICELIST), "UTF-8"));
            while (true) {
                String line = inStream.readLine();
                if (line == null) {
                    break;
                }
                String[] lineParts = line.split("; ");
                if (item.equals(lineParts[0])) {
                    return Double.parseDouble(lineParts[1]);               
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Bookable.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException e) {
            return 0;
        }   
        return 0;
    }
    
    /** Saves a changed price in the CSV-file. 
     *
     * @param toChange the item to be changed
     * @param newPrice the new price of the item
     */
    static public void savePrice(String toChange, double newPrice) {
        try {
            BufferedReader inStream = new BufferedReader
                                          (new InputStreamReader
                                          (new FileInputStream(CSV_PRICELIST), "UTF-8"));
            String inText = "";
            while (true) {
                String line = inStream.readLine();
                if (line == null) {
                    break;
                }
                String[] lineParts = line.split("; ");
                if (toChange.equals(lineParts[0])) {
                    lineParts[1] = String.valueOf(newPrice);               
                }
                inText += lineParts[0] + "; " + lineParts[1] + "\n";
            }
            inStream.close();
            
            PrintWriter outStream = new PrintWriter
                                            (new BufferedWriter
                                            (new OutputStreamWriter
                                            (new FileOutputStream(CSV_PRICELIST),
                                                    "UTF-8")));
            outStream.print(inText);
            outStream.close();            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Bookable.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (IOException e) {
            return;
        }   
        return;        
       
    }

    /** Describes an item from the price list
     *
     * @return A string describing the item
     */
    public String description(){
        return description;
    }
    
    /** Returns the price of an item
     *
     * @return price as a double
     */
    public double price(){
        return price;
    }
    
    /** Displays the object as a string
     *
     * @return the description and price
     */
    @Override
    public String toString() {
        return description + " Pris: " + price + " kr\n";
    }

}
