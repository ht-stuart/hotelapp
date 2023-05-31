package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Test {
    public static void main(String[] args) {
        Customer Tyrone = new Customer("Tyrone", "Stuart", "harold@yahoo.com");

        System.out.println(Tyrone.toString());

        Room newRoom = new Room("100", 500.00, RoomType.SINGLE);
        System.out.println(newRoom.toString());

        LocalDate checkIn = LocalDate.of(2023, 5, 22);
        LocalDate checkOut = LocalDate.of(2023, 10, 28);


    }
    }
