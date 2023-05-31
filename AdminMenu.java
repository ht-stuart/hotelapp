import api.AdminResource;
import model.*;

import java.util.*;

public class AdminMenu {

    static AdminResource adminResource = AdminResource.getInstance();

    public static void printAdminMenu() {
        Scanner keyboard = new Scanner(System.in);

        String userChoice = "";
        do {
            try {
                System.out.println("""
                                                
                        1. See all Customers
                        2. See all Rooms
                        3. See all Reservations
                        4. Add a Room
                        5. Back to Main Menu
                        """);
                userChoice = keyboard.next();
                switch (userChoice) {
                    case "1" -> seeAllCustomers();
                    case "2" -> seeAllRooms();
                    case "3" -> seeAllReservations();
                    case "4" -> addARoom();
                    case "5" -> MainMenu.printCustomerMenu();
                    default -> System.out.println("Please enter a valid option");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } while (true);
    }


    private static void seeAllCustomers() {

        for (Customer customer : adminResource.getAllCustomers()) {
            System.out.println(customer + "\n");
        }
        printAdminMenu();
    }

    private static void seeAllRooms() {

        for (IRoom room : adminResource.getAllRooms()) {
            System.out.println(room + "\n");
        }
        printAdminMenu();
    }

    private static void seeAllReservations() {
        if (adminResource.getAllReservations().isEmpty() || adminResource.getAllReservations() == null) {
            System.out.println("No reservations currently in system. ");
            printAdminMenu();
        } else {
            for(Collection<Reservation> reservations : adminResource.getAllReservations()){
                for(Reservation booking : reservations) System.out.println(booking + "\n");
            }
        } printAdminMenu();
        }

    private static void addARoom() {
        Scanner keyboard = new Scanner(System.in);


        System.out.println("Please enter a room number: ");
        String roomNumber = keyboard.nextLine();
        if(!validRoomNumber(roomNumber)) {
            System.out.println("Please enter a valid room number that contains only numbers." + "\n");
            addARoom();
        }

        System.out.println("Please enter room price: (EX. 500.00 = $500)");
        Double price = null;
        try {
            price = keyboard.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Not a valid price input! Please try again with correct format. (Ex 100.25)");
            addARoom();
        } catch (ConcurrentModificationException e) {
            System.out.println("Not a valid price input! Please try again with correct format. (Ex 100.25 or 0.0)");
        }

        System.out.println("Please enter room type: (Single or Double)");

        String roomChoice = null;
        RoomType roomType = null;
        try {
            roomChoice = keyboard.next().toUpperCase();
            roomType = RoomType.valueOf(roomChoice);
        } catch (IllegalArgumentException e) {
            System.out.println("""
                    Not a valid room type input! Please try to create a room again with one 
                    of the following roomtype choices:
                    Single
                    Double
                    """);
            addARoom();
        }
        //boolean addRoom;

        try {
            Room newRoom = new Room(roomNumber, price, roomType);

            boolean addRoom = adminResource.addRoom(newRoom);


            if (!addRoom) {
                printAdminMenu();
            }

            System.out.println("""
                            
                    New room added!
                            
                    Would you like to add another room? Yes(y) or No(n)
                    """);

        } catch (ConcurrentModificationException e) {
            System.out.println("""
                            
                    New room added!
                            
                    Would you like to add another room? Yes(y) or No(n)
                    """);

        }

        String choice = keyboard.next().toLowerCase();

        if (choice.charAt(0) == 'y') {
            addARoom();
        } else {
            System.out.println("Exiting to main menu...");
            printAdminMenu();
        }


        printAdminMenu();
    }

    public static boolean validRoomNumber(String roomNumber) {
        for (int x = 0; x < roomNumber.length(); x++) {
            if (!Character.isDigit(roomNumber.charAt(x))) {
                return false;
            }
        } return true;

    }

}
