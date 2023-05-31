package service;

import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws ParseException {

        CustomerService cs = CustomerService.getInstance();
        ReservationService rs = ReservationService.getInstance();
        String email = "harold@yahoo.com";

        Customer Tyrone = new Customer("Tyrone", "Stuart", "harold@yahoo.com");

        Room newRoom = new Room("100", 500.00, RoomType.SINGLE);
        Room twoRoom = new Room("150", 500.00, RoomType.DOUBLE);

        LocalDate ciDate = LocalDate.of(2023, 06, 02);
        LocalDate coDate = LocalDate.of(2023, 06, 10);

        LocalDate ciDate1 = LocalDate.of(2023, 06, 15);
        LocalDate coDate2 = LocalDate.of(2023, 06, 18);


        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a date");
        String date = keyboard.next();

        Date checkIn = df.parse(date);

        System.out.println(date);








    }
}