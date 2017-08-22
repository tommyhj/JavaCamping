package javacamping;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Random;

/** Class for handling the reservation objects
 *
 * @author Tommy Hjertberg
 * @version 2017-08-13
 */
public final class Reservation implements Serializable{

    /** A constant that defines which month the high season begins
     *
     */
    public static final int HIGH_SEASON_STARTS = 6;

    /**A constant that defines which month the high season ends
     *
     */
    public static final int HIGH_SEASON_ENDS = 9;
    private final LocalDate from;
    private LocalDate until;
    private final Bookable bookable;
    private final Patron patron;
    private double paid;
    private boolean usesPower;

    /** Sets that the patron will subscribe to electricity
     *
     * @param usesPower
     */
    public void setUsesPower(boolean usesPower) {
        this.usesPower = usesPower;
    }
    private double powerCost;
    private int powerUse;

    /** Returns the patron object
     *
     * @return
     */
    public Patron getPatron() {
        return patron;
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

    /** Constructor for new reservation objects
     *
     * @param from Localdate for when the reservation begins
     * @param until Localdate for when the reservation ends
     * @param bookable The object being booked
     * @param customer The customer booking
     */
    public Reservation(LocalDate from, LocalDate until, Bookable bookable, Patron customer) {
        this.patron = customer;
        this.from = from;
        this.until = until;
        this.bookable = bookable;
        this.paid = 0;
    }

    /** Sets the last date of the reservation
     *
     * @param until
     */
    public void setUntil(LocalDate until) {
        this.until = until;
    }

    /** Returns the total cost of the stay
     *
     * @return the total cost of the stay
     */
    public double getPrice() {
        int price = 0;
        ArrayList<Prices> priceList = getPriceList(); 
        for (Prices p : priceList) {
            price += p.price();
        }
        price += getPowerCost();
        return price;
    }

    /** Getter for the power usage
     *
     * @return
     */
    public int getPowerUse() {
        return powerUse;
    }
    
    /** Returns the price of the stay as an arraylist
     *
     * @return
     */
    public ArrayList getPriceList(){
        LocalDate d = from;
        ArrayList<Prices> priceList = new ArrayList<>();
        int weekCounter = 0;
        do {
            if (d.getMonthValue() >= HIGH_SEASON_STARTS && d.getMonthValue() < HIGH_SEASON_ENDS){
                    if (bookable instanceof Cabin){
                        priceList.add(Prices.CABIN_DAY_HIGHSEASON );
                    } else if (bookable instanceof Trailer) {
                        priceList.add(Prices.TRAILER_DAY_HIGHSEASON );
                    } else if (bookable instanceof Tent) {
                        priceList.add(Prices.TRAILER_DAY_HIGHSEASON );
                    }
            } else {                    
                if (bookable instanceof Cabin){
                    priceList.add(Prices.CABIN_DAY_LOWSEASON );
                } else if (bookable instanceof Trailer) {
                    priceList.add(Prices.TRAILER_DAY_LOWSEASON );
                } else if (bookable instanceof Tent) {
                    priceList.add(Prices.TRAILER_DAY_LOWSEASON );
                }
            }
            d = d.plusDays(1);
            weekCounter += 1;
            if (weekCounter == 7) {
                if (isWeekCheaper(d, bookable, priceList)) {
                    for (int i = 0; i < 7; i++) {
                        priceList.remove(priceList.size()-1);
                    }
                    if (isHighSeason(d)  && bookable instanceof Cabin) {
                        priceList.add(Prices.CABIN_WEEK_HIGHSEASON);
                    } else if (!isHighSeason(d)  && bookable instanceof Cabin) {
                        priceList.add(Prices.CABIN_WEEK_LOWSEASON);
                    }
                    if (isHighSeason(d)  && bookable instanceof Trailer) {
                        priceList.add(Prices.TRAILER_WEEK_HIGHSEASON);
                    } else if (!isHighSeason(d)  && bookable instanceof Trailer) {
                        priceList.add(Prices.TRAILER_WEEK_LOWSEASON);
                    }
                    if (isHighSeason(d)  && bookable instanceof Tent) {
                        priceList.add(Prices.TENT_WEEK_HIGHSEASON);
                    } else if (!isHighSeason(d)  && bookable instanceof Tent) {
                        priceList.add(Prices.TENT_WEEK_LOWSEASON);
                    }
                }
                weekCounter = 0;
            }               
        } while (d.isBefore(until) || d.equals(until));       
        return priceList;
    }

    /** Returns the number of nights that the stay encompasses
     *
     * @return
     */
    public int numberOfNights(){
        return from.until(until).getDays();
    }
    
    /** Returns the number of nights of the stay that are in a certain period
     *
     * @param start the forst Localdate of the period
     * @param end the last localdate of the period
     * @return returns the number of nights as a long
     */
    public long numberOfNightsInPeriod(LocalDate start, LocalDate end){       
            if (start.equals(end) && this.containsDate(start)){
                return 1;
            }
            LocalDate earliest;
            if (from.isAfter(start)){
                earliest = from;
            } else {
                earliest = start;
            }
            LocalDate latest;
            if (until.isAfter(end)){
                latest = end;
            } else {
                latest = until;
            }

            long days = DAYS.between(earliest, latest);
            
            if (days < 0) {
                return 0;
            }
        return days;
    }
    
    /** Getter for the bookable object
     *
     * @return
     */
    public Bookable getBookable() {
        return bookable;
    }
    
    private boolean isHighSeason(LocalDate date) {
        return date.getMonthValue() >= HIGH_SEASON_STARTS && date.getMonthValue() < HIGH_SEASON_ENDS;
    }
    
    private boolean isWeekCheaper(LocalDate until, Bookable bookable, ArrayList<Prices> priceList) {
        LocalDate from = until.minusWeeks(1);
        int cost = 0;
        
        for (int i = 1; i <= 7; i++) {
            cost += priceList.get(priceList.size() - i).price(); 
        }
        /* Determine if any day i the week is in the high season */
        if (isHighSeason(from) || isHighSeason(until) )
        {
                if (bookable instanceof Cabin && cost > Prices.CABIN_WEEK_HIGHSEASON.price()){
                    return true;
                } else if (bookable instanceof Trailer && cost > Prices.TRAILER_WEEK_HIGHSEASON.price()) {
                    return true;
                } else if (bookable instanceof Tent && cost > Prices.TENT_WEEK_HIGHSEASON.price()) {
                    return true;
                } else {
                    return false;
                }
        } 
        return true;
    }

    /** Checks if a date is within the booked period
     *
     * @param checkDate LocalDate to be checked
     * @return if the date is within the booking
     */
    public boolean containsDate (LocalDate checkDate){
        return checkDate.isAfter(from.minusDays(1)) && 
                checkDate.isBefore(until.plusDays(1));
    }
    
    /** Checks whether the booking is within a certain range
     *
     * @param checkDateFrom the first date of the range
     * @param checkDateUntil the last date of the range
     * @return whether it is or not
     */
    public boolean inDateRange (LocalDate checkDateFrom, LocalDate checkDateUntil){
        return (from.isAfter(checkDateFrom.minusDays(1)) 
                && from.isBefore(checkDateUntil.plusDays(1)))
                || (until.isAfter(checkDateFrom.minusDays(1)) 
                && until.isBefore(checkDateUntil.plusDays(1)));
    }
    
    
    /** Returns if a power subscription is active
     *
     * @return
     */
    public boolean isUsesPower(){
        return usesPower;
    }
    
    /** Randomises the amount of power used
     *
     */
    public void calculatePowerUse(){
        Random rand = new Random(); 
        int randomNumber = (rand.nextInt(20) + 1); 
        if (this.isUsesPower()) {
            this.powerUse = randomNumber * numberOfNights();
        }
    }
    
    /** Uses the price list to calculate the cost of power
     *
     */
    public void calculatePowerCost(){
        this.powerCost = this.powerUse * Prices.ELECTRICITY.price();
    }

    /**
     *
     * @return
     */
    public double getPowerCost(){
        return powerCost; 
    }
    
    /**
     *
     * @return
     */
    public LocalDate getFrom() {
        return from;
    }

    /** Getter for the last date of the reservation
     *
     * @return
     */
    public LocalDate getUntil() {
        return until;
    }
        
    /** Registers that the customer has paid a certain amount
     *
     * @param sum as double
     */
    public void registerPayment(double sum){
        this.paid += sum;
    }
    
    /** Registers that the customer has paid a certain amount
     *
     * @param sum as int
     */
    public void registerPayment(int sum){
        this.paid += sum;
    }
    
    /** Renders a string of the booking
     *
     * @return
     */
    @Override
    public String toString() {
        return getPatron().getName() + "(från " + from + " till " + until + ')';
    }

    /** Returns how much a customer has paid
     *
     * @return
     */
    public double getPaid() {
        return paid;
    }
    
}
