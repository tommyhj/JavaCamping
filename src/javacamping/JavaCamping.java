package javacamping;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Program for handling bookings at the camping named JavaCamping
 *
 * @author Tommy Hjertberg
 * @version 2017-08-13
 */
public class JavaCamping implements Serializable {

    /**
     * List that handles the registered customers
     *
     */
    public static final ArrayList<Patron> patronList = new ArrayList<>();

    /**
     * List that handles all the reservations
     *
     */
    public static final ArrayList<Reservation> reservationList = new ArrayList<>();

    /**
     * List that handles the bookable objects
     *
     */
    public static ArrayList<Bookable> bookableList = new ArrayList<>();
    private static final String CSV_BOOKABLES = "bookables.csv";
    private static final String SAVE_FILE = "save.dat";

    /**
     * Main function of the program. Loads the program saved files and verifies
     * them. Then starts the GUI
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        loadObjects();
        if (!checkBookableList()) {
            bookableList = loadBookablesCSV();
        }

        GUI main_window = new GUI();
        main_window.setTitle("JavaCamping");
        main_window.setVisible(true);

        saveObjects();

    }

    /**
     * Saves the state of the program
     *
     */
    public static void saveObjects() {
        try (ObjectOutputStream objectsOut = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            for (Bookable b : bookableList) {
                objectsOut.writeObject(b);
            }
            for (Patron p : patronList) {
                objectsOut.writeObject(p);
            }
            for (Reservation r : reservationList) {
                objectsOut.writeObject(r);
            }
        } catch (IOException e) {
            showErrorDialog("Sparning misslyckades. "
                    + "Programmet kunde inte skriva till fil. Du kan fortsätta "
                    + "att använda programmet men eventuella ändringar kommer "
                    + "inte att kunna sparas.", "Fel");
        }
    }

    /**
     * Loads the program state
     *
     */
    public static void loadObjects() {
        ObjectInputStream objectsIn = null;
        try {
            objectsIn = new ObjectInputStream(new FileInputStream(SAVE_FILE));
            bookableList.clear();
            patronList.clear();
            reservationList.clear();
            try {
                while (true) {
                    Object o = objectsIn.readObject();
                    if (o instanceof Bookable) {
                        Bookable b = (Bookable) o;
                        bookableList.add(b);
                    } else if (o instanceof Patron) {
                        Patron p = (Patron) o;
                        patronList.add(p);
                    } else if (o instanceof Reservation) {
                        Reservation r = (Reservation) o;
                        reservationList.add(r);
                    }
                }
            } catch (EOFException e) {
                objectsIn.close();
            } catch (ClassNotFoundException ex) {
                showErrorDialog("Programmets databas är inte av korrekt "
                        + "version. Programmet kan inte fortsätta", "Fel");
                System.exit(1);
            }
        } catch (IOException ex) {
        } finally {
            try {
                objectsIn.close();
            } catch (IOException | NullPointerException e) {
            }
        }

    }

    /**
     * Verifies that the bookable objects stored in the CSV-file are the same as
     * the ordinary savefile.
     *
     * @return
     */
    public static boolean checkBookableList() {
        ArrayList<Bookable> checkList = loadBookablesCSV();
        if (checkList.size() == bookableList.size()) {
            for (int i = 0; i < checkList.size(); i++) {
                if (!checkList.get(i).toString().equals(bookableList.get(i).toString())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Loads the bookable objects from a CSV-file if the program needs to build
     * a new database (i.e. the normal sav-file is missing or corrupt).
     *
     * @return ArratList of bookable objects
     */
    public static ArrayList loadBookablesCSV() {
        try {
            BufferedReader inStream = new BufferedReader(new InputStreamReader(new FileInputStream(CSV_BOOKABLES), "UTF-8"));
            ArrayList<Bookable> bList = new ArrayList<>();
            while (true) {
                String line = inStream.readLine();
                if (line == null) {
                    break;
                }
                String[] lineParts = line.split("; ");
                switch (lineParts[0]) {
                    case "Cabin":
                        bList.add(new Cabin(lineParts[2], Integer.parseInt(lineParts[1])));
                        break;
                    case "Trailer":
                        bList.add(new Trailer(Integer.parseInt(lineParts[1])));
                        break;
                    case "Tent":
                        bList.add(new Tent(Integer.parseInt(lineParts[1])));
                        break;
                    default:
                        break;
                }
            }
            return bList;
        } catch (FileNotFoundException ex) {
            showErrorDialog("Programmet lyckas inte återskapa databasen. "
                    + "Programmet kan inte fortsätta", "Fel");
            System.exit(1);
        } catch (UnsupportedEncodingException ex) {
            showErrorDialog("Programmet lyckas inte återskapa databasen. "
                    + "Programmet kan inte fortsätta", "Fel");
            System.exit(1);
        } catch (IOException ex) {
            showErrorDialog("Programmet lyckas inte återskapa databasen. "
                    + "Programmet kan inte fortsätta", "Fel");
            System.exit(1);
        }
        return null;
    }

    /**
     * Adds a new customer
     *
     */
    static public void addPatron() {
        String message = "Ange kundens namn: ";
        String title = "Registrera ny kund";
        String name;
        while (true) {
            name = inputStringDialog(message, title);
            if (name == null) {
                return;
            } else if (name.split(" ").length != 2) {
                message = "Ange kundens namn i formen \"Förnamn Efternamn\": ";
            } else {
                break;
            }
        }

        String address = inputStringDialog("Ange kundens adress: ", title);
        if (address == null) {
            return;
        }

        String phone;
        message = "Ange kundens telefonnummer: ";
        while (true) {
            phone = inputStringDialog(message, title);
            if (phone == null) {
                return;
            }
            try {
                phone = phone.replace(" ", "").replace("-", "");
                Integer.valueOf(phone);
                break;
            } catch (NumberFormatException e) {
                message = "Ange kundens telefonnummer. Använd endast siffror.";
            }

        }
        patronList.add(new Patron(name, address, phone));
    }

    /**
     * A graphical input box for inputting String. Repeats the question if the
     * user leaves the field blank.
     *
     * @param question
     * @param title
     * @return The string entered by the user
     */
    static public String inputStringDialog(String question, String title) {
        String s = "";
        while (s.equals("")) {
            s = JOptionPane.showInputDialog(null, question,
                    title, JOptionPane.QUESTION_MESSAGE);
            if (s == null) {
                return null;
            }
        }
        return s;
    }

    /**
     * A graphical input box for inputting a LocalDate. Repeats the question if
     * the user leaves the field blank.
     *
     * @param question
     * @param title
     * @return A LocalDate corresponding to what the user wrote
     */
    static public LocalDate inputDateDialog(String question, String title) {
        LocalDate d;
        String q = question;
        while (true) {
            try {
                d = LocalDate.parse(inputStringDialog(q, title));
                /*if (d == null){
                    return null;
                }*/
                break;
            } catch (DateTimeParseException e) {
                q += "\nDu måste ange datumet med korrekt format";
            } catch (NullPointerException e) {
                return null;
            }
        }
        return d;
    }

    /**
     * A graphic information dialogue.
     *
     * @param message
     * @param title
     */
    static public void showInfoDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays an error window
     *
     * @param message
     * @param title
     */
    static public void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a dialogue for choosing yes or no
     *
     * @param message
     * @param title
     * @return an int corresponding to JOptionPane.YES_OPTION or NO_OPTION
     */
    static public int showYesNoDialog(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }

    /**
     * Handles the generation of reports on customers.
     *
     */
    static public void generateReport() {
        Object[] report = {"Nuvarande gäster", "Tidigare gäster", "Samtliga gäster", "Städlista"};
        String reportType = (String) JOptionPane.showInputDialog(null,
                "Välj typ av rapport", "Generera rapport",
                JOptionPane.QUESTION_MESSAGE, null, report, report[0]);
        if (reportType == null) {
            return;
        }

        switch (reportType) {
            case "Tidigare gäster":
                String hreport = historyReport();
                if (hreport == null) {
                    showInfoDialog("Det finns inga tidigare gäster att visa.", "Tidigare gäster");
                } else {
                    showInfoDialog("Föjande gäster har tidigare bott på JavaCamping:\n" + historyReport(), "Tidigare gäster");
                }
                break;
            case "Nuvarande gäster":
                String creport = currentReport();
                if (creport == null) {
                    showInfoDialog("Det finns inga gäster på campingen just nu.", "Nuvarande gäster");
                } else {
                    showInfoDialog("Föjande gäster bor för närvarande på JavaCamping:\n" + creport, "Nuvarande gäster");
                }
                break;
            case "Samtliga gäster":
                if (allReport() == null) {
                    showInfoDialog("Det finns inga gäster registrerade.", "Samtliga gäster");
                } else {
                    showInfoDialog("Nuvarande och tidigare gäster på JavaCamping:\n" + allReport(), "Samtliga gäster");
                }
                break;
            case "Städlista":
                if (cleaningReport().equals("")) {
                    showInfoDialog("Inga stugor har blivit lediga idag:\n", "Städlista");
                } else {
                    showInfoDialog("Följande stugor har blivit lediga idag:\n" + cleaningReport(), "Städlista");
                }
                break;
        }
    }

    /**
     * Compiles a String listing former guests at the camping
     *
     * @return
     */
    static public String historyReport() {
        ArrayList<Patron> guests = new ArrayList<>();
        /* Goes through reservation list, finds reservations with an end date
         * later than now. */
        for (Reservation r : reservationList) {
            if (r.getUntil().isBefore(LocalDate.now())) {
                if (!guests.contains(r.getPatron())) {
                    guests.add(r.getPatron());
                }
            }
        }
        String history = "";
        for (Patron g : guests) {
            history += g.getName() + "\n";
        }
        return history;
    }

    /**
     * Compiles a String listing current guests at the camping
     *
     * @return
     */
    static public String currentReport() {
        ArrayList<Patron> guests = new ArrayList<>();
        for (Reservation r : reservationList) {
            if (r.containsDate(LocalDate.now())) {
                System.out.println("onec");
                if (!guests.contains(r.getPatron())) {
                    guests.add(r.getPatron());
                }
            }
        }
        String history = "";
        for (Patron g : guests) {
            if (g.isCheckedIn()) {
                history += g.getName() + " (incheckad)\n";
            } else {
                history += g.getName() + " (ej incheckad)\n";
            }
        }
        return history;
    }

    /**
     * Compiles a report of all registred customers as a String
     *
     * @return
     */
    static public String allReport() {
        String guests = "";
        for (Patron g : patronList) {
            guests += g.getName() + "\n";
        }
        return guests;
    }

    /**
     * Handles the checking in of a customer
     *
     */
    static public void checkIn() {
        ArrayList<Patron> guests = new ArrayList<>();
        for (Reservation r : reservationList) {
            if (r.containsDate(LocalDate.now()) && r.getPaid() == 0) {
                if (!r.getPatron().isCheckedIn() && !guests.contains(r.getPatron())) {
                    guests.add(r.getPatron());
                }
            }
        }
        if (!guests.isEmpty()) {
            Object[] guestarray = new Object[guests.size()];
            for (int i = 0; i < guests.size(); i++) {
                guestarray[i] = guests.get(i);
            }
            try {
                Patron guest = (Patron) JOptionPane.showInputDialog(null,
                        "Välj vilken gäst som ska checka in (endast bokade gäster kan väljas).", "Incheckning",
                        JOptionPane.QUESTION_MESSAGE, null, guestarray, guestarray[0]);
                guest.setCheckedIn(true);
                showInfoDialog(guest + " har checkats in.", "Incheckning");
            } catch (NullPointerException e) {
                return;
            }
        } else {
            showInfoDialog("Det finns inga aktuella bokningar att checka in. Du måste registrera kunden och skapa en bokning innan incheckning kan ske.", "Incheckning");
        }

    }

    /**
     * Handles the checking out of a customer
     *
     */
    static public void checkOut() {
        String title = "Utcheckning";
        ArrayList<Patron> guests = new ArrayList<>();
        for (Patron p : patronList) {
            if (p.isCheckedIn()) {
                guests.add(p);
            }
        }
        if (guests.isEmpty()) {
            showInfoDialog("Det finns inga incheckade gäster", title);
        } else {
            Object[] guestarray = new Object[guests.size()];
            for (int i = 0; i < guests.size(); i++) {
                guestarray[i] = guests.get(i);
            }
            Patron guest = (Patron) JOptionPane.showInputDialog(null,
                    "Välj vilken gäst som ska checka ut.", title,
                    JOptionPane.QUESTION_MESSAGE, null, guestarray, guestarray[0]);

            for (Reservation r : reservationList) {
                if (r.getPatron() == guest && r.containsDate(LocalDate.now())) {
                    String priceListString = "";
                    if (r.getUntil().isBefore(LocalDate.now())) {
                        if (showYesNoDialog("Den här gästens är bokad till "
                                + r.getUntil().toString() + ". \nVill du ändå "
                                + "fortsätta utcheckningen?", title) == JOptionPane.YES_OPTION) {
                            r.setUntil(LocalDate.now());
                        } else {
                            return;
                        }
                    } else if (r.getUntil().isAfter(LocalDate.now())) {
                        showInfoDialog("Den här kundens utcheckning är försenad. "
                                + "Kunden kommer att debiteras för de överskridande dagarna.", title);
                        r.setUntil(LocalDate.now());
                    }

                    for (int i = 0; i < r.getPriceList().size(); ++i) {
                        priceListString += r.getPriceList().get(i).toString();
                    }
                    if (r.isUsesPower()) {
                        r.calculatePowerUse();
                        r.calculatePowerCost();
                        priceListString += Prices.ELECTRICITY.description() + " "
                                + r.getPowerUse() + " kilowatt "
                                + r.getPowerCost() + " kr";
                    }
                    showInfoDialog(guest + " har checkats ut." + "\nNota:\n"
                            + priceListString + "\nSumma att betala:\n"
                            + r.getPrice(),
                            "Utcheckning");
                    r.registerPayment(r.getPrice());
                    guest.setCheckedIn(false);
                    return;
                }
            }

        }
    }

    /**
     * Reports any cabins vacated on current day, to be cleaned
     *
     * @return
     */
    static public String cleaningReport() {
        String cleaning = "";
        for (Reservation r : reservationList) {
            if (LocalDate.now().equals(r.getUntil())) {
                if (r.getBookable() instanceof Cabin) {
                    cleaning += r.getBookable().getName();
                }
            }
        }
        return cleaning;
    }

    /**
     * Finds a free bookable for a new guest
     *
     * @param from
     * @param until
     * @param kind
     * @return
     */
    static public Bookable findBookable(LocalDate from, LocalDate until, String kind) {
        ArrayList<Bookable> bookables = new ArrayList<>();
        for (Bookable b : bookableList) {
            if (kind.equals("Cabin") && b instanceof Cabin && !b.isBooked(from, until, reservationList)) {
                bookables.add(b);
            } else if (kind.equals("Trailer") && b instanceof Trailer && !b.isBooked(from, until, reservationList)) {
                bookables.add(b);
            } else if (kind.equals("Tent") && b instanceof Tent && !b.isBooked(from, until, reservationList)) {
                bookables.add(b);
            }
        }
        if (bookables.isEmpty()) {
            return null;
        }
        Random rand = new Random();
        int randomNumber;
        do {
            randomNumber = (rand.nextInt(bookables.size()));
        } while (randomNumber == 0);
        return bookables.get(randomNumber);
    }

    /**
     * Handles the process of creating a new reservation
     *
     */
    static public void newBooking() {
        Bookable toBeBooked;
        String title = "Ny bokning";
        Object[] bookable = {"Stuga", "Plats för husvagn/husbil", "Tältplats"};
        String bookableType = (String) JOptionPane.showInputDialog(null,
                "Välj typ av boende:", title,
                JOptionPane.QUESTION_MESSAGE, null, bookable, bookable[0]);
        if (bookableType == null) {
            return;
        }
        LocalDate arrives;
        while (true) {
            try {
                arrives = LocalDate.parse(inputStringDialog("Ange ankomstdatum (enligt formatet ÅÅÅÅ-MM-DD): ", title));
                if (arrives == null) {
                    return;
                }
                break;
            } catch (DateTimeParseException e) {
            } catch (NullPointerException e) {
                return;
            }
        }
        LocalDate departs;
        while (true) {
            try {
                departs = LocalDate.parse(inputStringDialog("Ange utceckningsdatum (enligt formatet ÅÅÅÅ-MM-DD): ", title));
                if (departs == null) {
                    return;
                }
                break;
            } catch (DateTimeParseException e) {
            } catch (NullPointerException e) {
                return;
            }
        }
        boolean power = false;
        /* bookable[1] refers to trailer parkings */
        if (bookableType.equals(bookable[1])) {
            int p = showYesNoDialog("Ska elanslutning bokas?", title);
            if (p == JOptionPane.YES_OPTION) {
                power = true;
            } else if (p == JOptionPane.NO_OPTION) {
                power = false;
            } else {
                return;
            }
            toBeBooked = findBookable(arrives, departs, "Trailer");
            if (toBeBooked == null) {
                showInfoDialog("Det finns tyvärr inga husvagns eller husbilsplatser lediga det datumet.", title);
                return;
            }
            /* bookable[0] refers to cabins */
        } else if (bookableType.equals(bookable[0])) {
            toBeBooked = findBookable(arrives, departs, "Cabin");
            if (toBeBooked == null) {
                showInfoDialog("Det finns tyvärr inga stugor tillgängliga det datumet.", title);
                return;
            }
            /* Handles bookable[2] refers to tent places */
        } else {
            toBeBooked = findBookable(arrives, departs, "Tent");
            if (toBeBooked == null) {
                showInfoDialog("Det finns tyvärr inga tältplatser tillgängliga det datumet.", title);
                return;
            }
        }

        Patron patron;
        while (true) {
            Object[] patrons = new Object[patronList.size() + 1];
            for (int i = 0; i < patronList.size(); i++) {
                patrons[i] = patronList.get(i);
            }
            patrons[patronList.size()] = "Lägg till ny kund";
            try {
                patron = (Patron) JOptionPane.showInputDialog(null,
                        "Välj kund som ska bokas in", title,
                        JOptionPane.QUESTION_MESSAGE, null, patrons, patrons[0]);
                if (patron == null) {
                    return;
                } else {
                    for (Reservation r : reservationList) {
                        if (r.getPatron() == patron) {
                            if (r.containsDate(arrives) || r.containsDate(departs) || r.inDateRange(arrives, departs)) {
                                showInfoDialog("Den här gästen har redan en bokning som innefattar ett eller flera av dessa datum. Du måste ta bort tidigare bokningar innan du kan lägga till nya på samma dagar.", title);
                                return;
                            } else {
                                break;
                            }

                        }
                    }
                    break;

                }
            } catch (ClassCastException e) {
                addPatron();
            }
        }
        if (JOptionPane.showConfirmDialog(null,
                "Följande bokning kommer att genomföras:\n" + "Kund: " + patron
                + "\nAnkomstdatum: " + arrives + "\nAvresedatum: " + departs
                + "\n" + bookableType + ": " + toBeBooked + "\n",
                title, JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION) {
            reservationList.add(new Reservation(arrives, departs, toBeBooked, patron));
            reservationList.get(reservationList.size() - 1).setUsesPower(power);
        }
    }

    /**
     * Handles searching for and deleting a booking
     *
     */
    static public void removeBooking() {
        String title = "Sök och ta bort bokning";
        LocalDate startDate;
        LocalDate stopDate;
        String error = "";
        do {
            startDate = inputDateDialog("Ange det datum du vill söka från (i formatet ÅÅÅÅ-MM-DD)." + error, title);
            if (startDate == null) {
                return;
            }
            stopDate = inputDateDialog("Ange det datum du vill söka till (i formatet ÅÅÅÅ-MM-DD).", title);
            if (stopDate == null) {
                return;
            }
            error = "\nSlutdatum kan inte vara tidigare än startdatumet.";
        } while (stopDate.isBefore(startDate));

        ArrayList<Reservation> found = new ArrayList<>();
        for (Reservation r : reservationList) {
            if (r.inDateRange(startDate, stopDate)) {
                found.add(r);
            }
        }
        if (found.isEmpty()) {
            showInfoDialog("Det finns inga bokningar inom den angivna periden", title);
            return;
        }

        Object[] foundArray = new Object[found.size()];
        for (int i = 0; i < found.size(); i++) {
            foundArray[i] = found.get(i);
        }

        Reservation toBeRemoved;
        try {
            toBeRemoved = (Reservation) JOptionPane.showInputDialog(null,
                    "Följande bokningar finns inom denna period.", title,
                    JOptionPane.QUESTION_MESSAGE, null, foundArray, foundArray[0]);
        } catch (NullPointerException e) {
            return;
        }
        int verify;
        try {
            verify = showYesNoDialog("Du har valt följande bokning:\n"
                    + "Kund: " + toBeRemoved.getPatron().getName()
                    + "\nAnkomstdatum: " + toBeRemoved.getFrom() + "\nAvresedatum: "
                    + toBeRemoved.getUntil()
                    + "\n" + toBeRemoved.getBookable().getKind() + "\n\n"
                    + "Vill du ta bort denna bokning?", title);
            if (verify == JOptionPane.YES_OPTION) {
                if (toBeRemoved.getPaid() != 0) {
                    showInfoDialog("Du kan inte ta bort bokningar som redan betalats.", title);
                } else if (toBeRemoved.getPatron().isCheckedIn() && toBeRemoved.containsDate(LocalDate.now())) {
                    showInfoDialog("Du kan inte ta bort bokningen eftersom kunden har checkat in.", title);
                } else {
                    reservationList.remove(toBeRemoved);
                }
            }
        } catch (NullPointerException e) {
            return;
        }

    }

    /**
     * Generates statistical reports
     *
     * @param start
     * @param end
     * @param kind
     * @return A String summarising the report
     */
    static public String generateEconomyReports(LocalDate start, LocalDate end, String kind) {
        double sum = 0;
        int nights = 0;
        ArrayList<Reservation> unPaid = new ArrayList<>();
        for (Reservation r : reservationList) {
            if (r.inDateRange(start, end)) {
                if (kind.equals("Stugor") && r.getBookable() instanceof Cabin || kind.equals("Alla boenden")) {
                    nights += r.numberOfNightsInPeriod(start, end);
                    sum += r.getPaid();
                    if (r.getPaid() == 0) {
                        unPaid.add(r);
                    }
                }
                if (kind.equals("Parkeringsplatser för husvagn/husbil") && r.getBookable() instanceof Trailer) {
                    nights += r.numberOfNightsInPeriod(start, end);
                    sum += r.getPaid();
                    if (r.getPaid() == 0) {
                        unPaid.add(r);
                    }
                }
                if (kind.equals("Tältplats") && r.getBookable() instanceof Tent) {
                    nights += r.numberOfNightsInPeriod(start, end);
                    sum += r.getPaid();
                    if (r.getPaid() == 0) {
                        unPaid.add(r);
                    }
                }
            }
        }
        String report = "Statistik över gästnätter med " + kind + "\n"
                + "Under perioden " + start.toString() + " till "
                + end.toString() + "\n" + "Antal nätter: " + nights + "\n"
                + "Intäkter av " + kind.toLowerCase() + " under denna period: "
                + sum;
        if (!unPaid.isEmpty()) {
            report += "\nBetalning saknas från följande gäster under denna period:\n";
            for (Reservation r : unPaid) {
                if (!report.contains(r.getPatron().getName())) {
                    report += r.getPatron().getName() + "\n";
                }
            }

        }
        report += "\nFörklaringar till statistiken\n\n"
                + "Om bokningen börjar på sökperiodens sista dag blir "
                + "antalet gästnätter 0, trots att det finns en bokning under "
                + "tidsperioden.\n\n" + "Eftersom summan som betalas för ett "
                + "boende rabatteras vid vistelser som omfattar hela veckor"
                + ", och eftersom elpriset inte beräknas på enskilda dagar "
                + "redovisas intäkterna för hela bokningen om någon dag av den "
                + "ingår i den valda statistikperioden.";

        return report;
    }

    /**
     * Lists all the cabins at the camping in a graphic window
     *
     */
    static public void listCabins() {
        String report = "";
        for (Bookable b : bookableList) {
            if (b instanceof Cabin) {
                report += String.valueOf(b.getSerialNumber()) + ". " + b.getName() + "\n";
            }
        }
        showInfoDialog("Följande stugor finns på Java Camping:\n\n" + report, "Lista över stugor");
    }

    /**
     * Makes changes to the CSV-file storing the price information
     *
     */
    static public void changePriceList() {

        JTextField CABIN_DAY_HIGHSEASON = new JTextField();
        JTextField CABIN_WEEK_HIGHSEASON = new JTextField();
        JTextField CABIN_DAY_LOWSEASON = new JTextField();
        JTextField CABIN_WEEK_LOWSEASON = new JTextField();
        JTextField TRAILER_DAY_HIGHSEASON = new JTextField();
        JTextField TRAILER_WEEK_HIGHSEASON = new JTextField();
        JTextField TRAILER_DAY_LOWSEASON = new JTextField();
        JTextField TRAILER_WEEK_LOWSEASON = new JTextField();
        JTextField TENT_DAY_HIGHSEASON = new JTextField();
        JTextField TENT_WEEK_HIGHSEASON = new JTextField();
        JTextField TENT_DAY_LOWSEASON = new JTextField();
        JTextField TENT_WEEK_LOWSEASON = new JTextField();
        JTextField ELECTRICITY = new JTextField();

        CABIN_DAY_HIGHSEASON.setText(String.valueOf(Prices.CABIN_DAY_HIGHSEASON.price()));
        CABIN_WEEK_HIGHSEASON.setText(String.valueOf(Prices.CABIN_WEEK_HIGHSEASON.price()));
        CABIN_DAY_LOWSEASON.setText(String.valueOf(Prices.CABIN_DAY_LOWSEASON.price()));
        CABIN_WEEK_LOWSEASON.setText(String.valueOf(Prices.CABIN_WEEK_LOWSEASON.price()));
        TRAILER_DAY_HIGHSEASON.setText(String.valueOf(Prices.TRAILER_DAY_HIGHSEASON.price()));
        TRAILER_WEEK_HIGHSEASON.setText(String.valueOf(Prices.TRAILER_WEEK_HIGHSEASON.price()));
        TRAILER_DAY_LOWSEASON.setText(String.valueOf(Prices.TRAILER_DAY_LOWSEASON.price()));
        TRAILER_WEEK_LOWSEASON.setText(String.valueOf(Prices.TRAILER_WEEK_LOWSEASON.price()));
        TENT_DAY_HIGHSEASON.setText(String.valueOf(Prices.TENT_DAY_HIGHSEASON.price()));
        TENT_WEEK_HIGHSEASON.setText(String.valueOf(Prices.TENT_WEEK_HIGHSEASON.price()));
        TENT_DAY_LOWSEASON.setText(String.valueOf(Prices.TENT_DAY_LOWSEASON.price()));
        TENT_WEEK_LOWSEASON.setText(String.valueOf(Prices.TENT_WEEK_LOWSEASON.price()));
        ELECTRICITY.setText(String.valueOf(Prices.ELECTRICITY.price()));

        Object[] questions = {Prices.CABIN_DAY_HIGHSEASON.description(), CABIN_DAY_HIGHSEASON,
            Prices.CABIN_WEEK_HIGHSEASON.description(), CABIN_WEEK_HIGHSEASON,
            Prices.CABIN_DAY_LOWSEASON.description(), CABIN_DAY_LOWSEASON,
            Prices.CABIN_WEEK_LOWSEASON.description(), CABIN_WEEK_LOWSEASON,
            Prices.TRAILER_DAY_HIGHSEASON.description(), TRAILER_DAY_HIGHSEASON,
            Prices.TRAILER_WEEK_HIGHSEASON.description(), TRAILER_WEEK_HIGHSEASON,
            Prices.TRAILER_DAY_LOWSEASON.description(), TRAILER_DAY_LOWSEASON,
            Prices.TRAILER_WEEK_LOWSEASON.description(), TRAILER_WEEK_LOWSEASON,
            Prices.TENT_DAY_HIGHSEASON.description(), TENT_DAY_HIGHSEASON,
            Prices.TENT_WEEK_HIGHSEASON.description(), TENT_WEEK_HIGHSEASON,
            Prices.TENT_DAY_LOWSEASON.description(), TENT_DAY_LOWSEASON,
            Prices.TENT_WEEK_LOWSEASON.description(), TENT_WEEK_LOWSEASON,
            Prices.ELECTRICITY.description(), ELECTRICITY};

        int option = JOptionPane.showConfirmDialog(null, questions, "Redigera prislistan", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if (option == JOptionPane.OK_OPTION) {
            String[] priceNames = {"CABIN_DAY_HIGHSEASON", "CABIN_WEEK_HIGHSEASON", "CABIN_DAY_LOWSEASON",
                "CABIN_WEEK_LOWSEASON", "TRAILER_DAY_HIGHSEASON", "TRAILER_WEEK_HIGHSEASON", "TRAILER_DAY_LOWSEASON", "TRAILER_WEEK_LOWSEASON", "TENT_DAY_HIGHSEASON", "TENT_WEEK_HIGHSEASON", "TENT_DAY_LOWSEASON", "TENT_WEEK_LOWSEASON", "ELECTRICITY"};

            int i = 0;

            for (Object o : questions) {
                try {
                    JTextField t = (JTextField) o;
                    Prices.savePrice(priceNames[i], Double.valueOf(t.getText()));
                    i++;
                } catch (ClassCastException e) {
                }
            }
            showInfoDialog("Prislistan har uppdaterats. Men måste startas om för att"
                    + " förändringarna ska verkställas.", "Redigera prislista");
        }
    }

    /**
     * Displays a window with general information about the camping.
     *
     */
    static public void showInformationWindow() {
        int cabins = 0;
        int trailers = 0;
        for (Bookable b : bookableList) {
            if (b instanceof Cabin) {
                cabins++;
            } else if (b instanceof Trailer) {
                trailers++;
            }
        }

        String message = "JavaCamping\n\nKontaktuppgifter\n"
                + "Javavägen 12\n" + "123 45 JAVASTAD\n" + "0123 – 456 789\n\n" + "Anläggning\n"
                + "Antal husvagns-/husbilstomter " + trailers + " st\n"
                + "Antal stugor " + cabins + " st\n\nFaciliteter\n"
                + "Familjecamping Kiosk Fotbollsplan Tvättmaskin, torktumlare Bangolf\n\n"
                + "Beskrivning\n"
                + "Från campingens platser är det bara ett stenkast till havsbad. I Hamnen, ca tio minuters\n"
                + "promenad, kan du köpa färsk fisk. Javastad har en naturlig hamn i en mysig skärgårdsmiljö.\n\n"
                + "Alldeles invid badstranden ligger fyra tennisbanor av hög klass – gratis övningsbana finns.\n\n"
                + "Golfentusiasterna väljer att spela på den närliggande 18-hålsbanan, som ligger två km bort.\n\n"
                + "Det är bara 500 meter till samhället, där affärer, banker och restauranger finns.";

        showInfoDialog(message, "JavaCamping");
    }
}
