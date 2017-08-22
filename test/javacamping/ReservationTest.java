package javacamping;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tommy Hjertberg
 * @version 2017-08-13
 */
public class ReservationTest {
    
    public ReservationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }




  
    /**
     * Test of numberOfNights method, of class Reservation.
     */
    @Test
    public void testNumberOfNights() {
        LocalDate from = LocalDate.parse("2017-01-01");
        LocalDate until = LocalDate.parse("2017-01-05");

        Bookable bookable = new Cabin("Testcabin", 1);
        Patron patron = new Patron("Nils Nilsson", "Blockv. 1", "093093309");
        
        System.out.println("numberOfNights");
        Reservation instance = new Reservation(from, until, bookable, patron );
        int expResult = 4;
        int result = instance.numberOfNights();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of numberOfNightsInPeriod method, of class Reservation.
     */
    @Test
    public void testNumberOfNightsInPeriod() {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        System.out.println("numberOfNightsInPeriod");
        LocalDate from = LocalDate.parse("2017-01-01");
        LocalDate until = LocalDate.parse("2017-01-05");

        Bookable bookable = new Cabin("Testcabin", 1);
        Patron patron = new Patron("Nils Nilsson", "Blockv. 1", "093093309");

        LocalDate start = LocalDate.parse("2017-01-02");
        LocalDate end = LocalDate.parse("2017-01-09");
        
        Reservation instance = new Reservation(from, until, bookable, patron );

        long expResult = 3;
        
        long result = instance.numberOfNightsInPeriod(start, end);
        assertEquals(expResult, result);
    }

    /**
     * Test of getBookable method, of class Reservation.
     */
    @Test
    public void testGetBookable() {
        System.out.println("getBookable");
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        System.out.println("numberOfNightsInPeriod");
        LocalDate from = LocalDate.parse("2017-01-01");
        LocalDate until = LocalDate.parse("2017-01-05");
        Bookable bookable = new Cabin("Testcabin", 1);
        Patron patron = new Patron("Nils Nilsson", "Blockv. 1", "093093309");       
        Reservation instance = new Reservation(from, until, bookable, patron );
        Bookable expResult = bookable;
        Bookable result = instance.getBookable();
        assertEquals(expResult, result);
        
    }


    /**
     * Test of containsDate method, of class Reservation.
     */
    @Test
    public void testContainsDate() {
        System.out.println("containsDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        System.out.println("numberOfNightsInPeriod");
        LocalDate from = LocalDate.parse("2017-01-01");
        LocalDate until = LocalDate.parse("2017-01-05");
        Bookable bookable = new Cabin("Testcabin", 1);
        Patron patron = new Patron("Nils Nilsson", "Blockv. 1", "093093309");          
        LocalDate checkDate = LocalDate.parse("2017-01-03");
        Reservation instance = new Reservation(from, until, bookable, patron );
        boolean expResult = true;
        boolean result = instance.containsDate(checkDate);
        assertEquals(expResult, result);

    }

    /**
     * Test of inDateRange method, of class Reservation.
     */
    @Test
    public void testInDateRange() {
        System.out.println("inDateRange");
        
        
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        System.out.println("numberOfNightsInPeriod");
        LocalDate from = LocalDate.parse("2017-01-01");
        LocalDate until = LocalDate.parse("2017-01-05");
        Bookable bookable = new Cabin("Testcabin", 1);
        Patron patron = new Patron("Nils Nilsson", "Blockv. 1", "093093309");          
        Reservation instance = new Reservation(from, until, bookable, patron );
        LocalDate checkDateFrom = LocalDate.parse("2017-01-03");
        LocalDate checkDateUntil = LocalDate.parse("2017-01-09");
        boolean expResult = true;
        boolean result = instance.inDateRange(checkDateFrom, checkDateUntil);
        assertEquals(expResult, result);

    }



    
}
