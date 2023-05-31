import java.util.Scanner;

public class HotelApplication {

    public static void main(String[] args) {
        mainApplication();
    }


    public static void mainApplication() {

        Scanner keyboard = new Scanner(System.in);

        String userChoice = "";
        do {
            try {
                System.out.println("""
                        1. Customer Menu
                        2. Admin Menu
                        """);
                userChoice = keyboard.next();
                switch (userChoice) {
                    case "1" -> MainMenu.printCustomerMenu();
                    case "2" -> AdminMenu.printAdminMenu();
                    default -> System.out.println("Please enter a valid option");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } while (true);
    }

}
