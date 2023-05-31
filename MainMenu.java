import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Calendar;

public class MainMenu {

    static HotelResource hotelResource = HotelResource.getInstance();
    private static String emailPattern = "^(.+)@(.+).com$";
    static Calendar cal = Calendar.getInstance();



    public static void printCustomerMenu() {
        Scanner keyboard = new Scanner(System.in);

        String userChoice = "";
        do {
            try {
                System.out.println("""
                                                
                        1. Find and reserve room
                        2. See my reservations
                        3. Create an account
                        4. Admin
                        5. Exit
                        """);
                userChoice = keyboard.next();
                switch (userChoice) {
                    case "1" -> findAndReserveRoom();
                    case "2" -> seeMyReservations();
                    case "3" -> createAccount();
                    case "4" -> AdminMenu.printAdminMenu();
                    case "5" -> HotelApplication.mainApplication();

                    //
                    default -> System.out.println("Please enter a valid option");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } while (true);
    }

    public static Date dateInput(String userInput) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");;
        return df.parse(userInput);
    }

    public static void validInputChecker(String firstStatement) {
        Scanner keyboard = new Scanner(System.in);
        String userAnswer = "";
        while (true) {
            System.out.println(firstStatement);
            userAnswer = keyboard.next().toLowerCase();
            if (userAnswer.charAt(0) == 'y') {
                break;
            } else if (userAnswer.charAt(0) == 'n') {
                System.out.println("Exiting to main menu...");
                printCustomerMenu();
                break;
            } else {
                System.out.println("""
                                                 
                        Please enter either Yes(y) or No(n) as an answer!
                                                 
                           """);
            }
        }
    }

    public static void validReservationDates(Date checkIn, Date checkOut) {
        Date currentDay = new Date();
        if (checkIn.before(currentDay) || checkOut.before(currentDay)) {
            System.out.println("Reservation cannot be before current day! ");
            findAndReserveRoom();
        } else if (checkIn.after(checkOut) || checkOut.before(checkIn)) {
            System.out.println("Check in and check out days must be valid");
            findAndReserveRoom();
        }
    }

    private static void findAndReserveRoom() {
        Scanner keyboard = new Scanner(System.in);

        SimpleDateFormat df = new SimpleDateFormat("MMMM/dd/yyyy");



        System.out.println("Please enter a check-in date: (mm/dd/yyy) ");
        Date checkIn = null;
        Date checkout = null;
        try {
            String stringDateCheckIn = keyboard.next();
            checkIn = dateInput(stringDateCheckIn);
            System.out.println("Please enter a check-out date: (mm/dd/yyyy) ");
            String stringDateCheckOut = keyboard.next();
            checkout = dateInput(stringDateCheckOut);
            validReservationDates(checkIn, checkout);
        } catch (DateTimeException e) {
            System.out.println("Please use valid date input. (EX. 05/06/2023 ) ");
            findAndReserveRoom();
        } catch (ParseException e) {
            System.out.println("Invalid date input! Please try again with valid date format. (EX. 08/03/2024)");
            findAndReserveRoom();
        }


        validInputChecker("Would you like to reserve a room? Yes(y) or No(n)");

        while (true) {
            System.out.println("Do you have an account with us? Yes(y) or No(n)");
            String roomBookingChoice = keyboard.next().toLowerCase();
            if (roomBookingChoice.charAt(0) == 'y') {
                System.out.println("Please enter email address associated with your account: ");
                break;
            } else if (roomBookingChoice.charAt(0) == 'n') {
                System.out.println("\n" + "Please create an account at the menu (option 3)");
                printCustomerMenu();
                break;
            } else {
                System.out.println("""
                                                           
                        Please enter either Yes(y) or No(n) as an answer!
                                                           
                         """);
            }
        }

        String email = keyboard.next();
        if (hotelResource.getCustomer(email) == null) {
            while (true) {
                System.out.println("""
                        No email address associated with that account!
                        Enter (1) to create account
                        Enter (2) to retry
                        Enter (3) to go back to main menu
                        """);
                String choice = keyboard.next();
                if (choice.charAt(0) == '1') {
                    createAccount();
                    break;
                } else if (choice.charAt(0) == '2') {
                    findAndReserveRoom();
                    break;
                } else if (choice.charAt(0) == '3') {
                    printCustomerMenu();
                    break;
                } else {
                    System.out.println("Please input valid option");
                }
            }
        }

        hotelResource.getCustomer(email);

        String customerFirstName = hotelResource.getCustomer(email).getFirstName();
        try {
            if (!isEmptyOrNull(checkIn, checkout)) {
                System.out.println("Hello " + customerFirstName + ", here are the available rooms based on your date requests: " + "\n" );
                //System.out.println(openRooms(checkIn, checkout));
                roomPrinter(openRooms(checkIn, checkout));
            } else {
                if (hotelResource.optionalRooms(checkIn, checkout) == null || hotelResource.optionalRooms(checkIn, checkout).isEmpty()) {
                    System.out.println("Sorry, no rooms are available near your requested timeframe");
                    printCustomerMenu();
                } else {
                    cal.setTime(checkIn);
                    cal.add(Calendar.DAY_OF_MONTH, 7);
                    checkIn = cal.getTime();
                    cal.setTime(checkout);
                    cal.add(Calendar.DAY_OF_MONTH, 7);
                   checkout = cal.getTime();

                    System.out.println("We apologize " + customerFirstName + ", no rooms available for your requested time frame, " + "\n" + "but these room(s) are available from: " + "\n" + df.format(checkIn) + " - \n" + df.format(checkout) + "\n");
                    //System.out.println(hotelResource.optionalRooms(checkIn, checkout));
                    roomPrinter(hotelResource.optionalRooms(checkIn, checkout));
                    validInputChecker("Would you like to update your reservation dates? Yes(y) or No(n)");
                    findAndReserveRoom();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Reservation error");
        }


        validInputChecker("Would you like to reserve a room? Yes(y) or No(n)");


        String roomNumberChoice = null;
        IRoom roomBooking = null;
        while (true) {
            System.out.println("Please enter the room number you would like to book: ");
            roomNumberChoice = keyboard.next();
            if (roomNumberChoice.equalsIgnoreCase("exit")) {
                printCustomerMenu();
            } else if (hotelResource.getRoom(roomNumberChoice) == null) {
                System.out.println("""
                                                            
                        Could not find room!
                        Please enter valid room number 
                        or enter (exit) as room number to exit. 
                                                            
                        """);
            } else {
                roomBooking = hotelResource.getRoom(roomNumberChoice);
                break;
            }
        }


        Reservation newReserve = hotelResource.reserveARoom(email, roomBooking, checkIn, checkout);
        if(newReserve == null) {
            printCustomerMenu();
        }

        System.out.println("Reservation confirmed!" + "\n" +
                hotelResource.getCustomer(email) + "\n" + hotelResource.getRoom(roomNumberChoice) +
                "\n From: " + df.format(checkIn) + "- \n To:   " + df.format(checkout));
        printCustomerMenu();
    }



    public static boolean isEmptyOrNull(Date checkIn, Date checkOut) {
        Collection<IRoom> rooms = new ArrayList<>();

        try {
            if (!rooms.addAll(hotelResource.findAvailableRooms(checkIn, checkOut))) {
                return true;
            }
            return false;
        } catch(NullPointerException e) {
            return true;
        }
    }

    public static Collection<IRoom> openRooms(Date checkIn, Date checkOut) {
        Collection<IRoom> rooms = new ArrayList<>();

        rooms.addAll(hotelResource.findAvailableRooms(checkIn, checkOut));
        return rooms;
    }


    private static void seeMyReservations() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the email address associated with your account: ");
        String customerEmail = keyboard.next();
        try {
            if (hotelResource.getCustomerReservation(customerEmail) == null) {
                System.out.println("No reservations associated with that account!");
                printCustomerMenu();
            }
            for(Reservation reservation : hotelResource.getCustomerReservation(customerEmail)) {
                System.out.println(reservation + "\n");
            }

            printCustomerMenu();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: Please re-check email address typo and make sure you have an active account");
            printCustomerMenu();
        } catch (NullPointerException e) {
            System.out.println("No user found with that email address.");
        }
    }



    private static void createAccount() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter your first name: ");
        String firstName = keyboard.nextLine();

        System.out.println("Enter your last name: ");
        String lastName = keyboard.nextLine();

        if(firstName.isEmpty() || lastName.isEmpty()) {
            System.out.println("Names cannot be blank!" + "/n");
            createAccount();
        }

        System.out.println("Please enter valid email address: (format example@example.com)");
        String validEmail = validEmail();

        hotelResource.createACustomer(firstName, lastName, validEmail);

        System.out.println();

        printCustomerMenu();
    }

    public static String validEmail() {
        Scanner keyboard = new Scanner(System.in);
        String email = "";
        while (true) {
            email = keyboard.next();
            if (Pattern.compile(emailPattern).matcher(email).matches()) {
                break;
            } else {
                System.out.println("Invalid email input. Please enter valid email address: (format example@example.com)");
            }
        }
        return email;
    }

    public static void roomPrinter(Collection<IRoom> roomsList) {
        for(IRoom room : roomsList) {
            System.out.println(room + "\n");
        }
    }

}



