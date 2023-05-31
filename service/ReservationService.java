package service;

import model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Calendar;

public class ReservationService {

    private static ReservationService INSTANCE;

    private final HashMap<String, Collection<Reservation>> allReservationsInApp = new HashMap<>();
    private final HashSet<IRoom> allRoomsInApp = new HashSet<>();

    Calendar cal = Calendar.getInstance();

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReservationService();
        }

        return INSTANCE;
    }


    public Collection<IRoom> getAllRooms() {
        if (allRoomsInApp.isEmpty()) {
            System.out.println("No rooms currently in system.");
            return allRoomsInApp;
        }
        return allRoomsInApp;
    }


    public boolean addRoom(IRoom room) {
        if (allRoomsInApp.isEmpty()) {
            allRoomsInApp.add(room);
        } else {
            for (IRoom eachRoom : getAllRooms()) {
                if (!room.getRoomNumber().equals(eachRoom.getRoomNumber())) {
                    allRoomsInApp.add(room);
                } else {
                    System.out.println("""
                                                    
                            Room number exists in system! Please add a room with a new room number.
                            """);
                    return false;
                }
            }
        }
        return true;
    }

    public IRoom getARoom(String roomId) {
        for( IRoom room : allRoomsInApp) {
            if(Objects.equals(room.getRoomNumber(), roomId)) {
                return room;
            }
        }
        return null;
    }


    public Collection<Collection<Reservation>> getAllReservations() {

        return allReservationsInApp.values();
    }


    public Collection<Reservation> getCustomerReservation(Customer customer) {
        String validKey = customer.getEmail();
        return allReservationsInApp.get(validKey);

    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        Reservation newReservation = new Reservation(customer, room, checkIn, checkOut);
        String validKey;
        validKey = customer.getEmail();
        if(allReservationsInApp.get(validKey) == null || allReservationsInApp.get(validKey).isEmpty()){
            allReservationsInApp.put(validKey, new ArrayList<Reservation>());
            allReservationsInApp.get(validKey).add(newReservation);
        } else {
            allReservationsInApp.get(validKey).add(newReservation);
        }
        return newReservation;
    }


    public Collection<IRoom> unavailableRooms(Date checkIn, Date checkOut) {
        Collection<IRoom> unavailableRooms = new ArrayList<>();

        for (Collection<Reservation> Reservation : getAllReservations()) {
            for (Reservation booking : Reservation) {
                if (checkIn.before(booking.getCheckOutDate()) && checkOut.after(booking.getCheckInDate()) ||
                        checkIn.equals(booking.getCheckInDate()) || checkIn.equals(booking.getCheckOutDate()) ||
                        checkOut.equals(booking.getCheckInDate()) || checkOut.equals(booking.getCheckOutDate())) {
                    unavailableRooms.add(booking.getRoom());
                }
            }
        }return unavailableRooms;
    }


    public Collection<IRoom> openRooms(Date checkIn, Date checkOut) {
        Collection<IRoom> availableRooms = new ArrayList<>();

        if (allRoomsInApp.isEmpty()) {
            return allRoomsInApp;
        }

        Collection<IRoom> unavailableRooms = unavailableRooms(checkIn, checkOut);

        availableRooms = allRoomsInApp.stream().filter(r -> !unavailableRooms.contains(r)).collect(Collectors.toList());

       return availableRooms;
    }

    public Collection<IRoom> optionalRooms(Date checkIn, Date checkOut) {
        cal.setTime(checkIn);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        checkIn = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 14);
        checkOut = cal.getTime();


        Collection<IRoom> optionalUnavailableRooms = new ArrayList<>();
        Collection<IRoom> optionalRooms = new ArrayList<>();


        for (Collection<Reservation> Reservation : getAllReservations()) {
            for (Reservation booking : Reservation) {
                if (checkIn.before(booking.getCheckOutDate()) && checkOut.after(booking.getCheckInDate()) ||
                        checkIn.equals(booking.getCheckInDate()) || checkIn.equals(booking.getCheckOutDate()) ||
                        checkOut.equals(booking.getCheckInDate()) || checkOut.equals(booking.getCheckOutDate())) {
                    optionalUnavailableRooms.add(booking.getRoom());
                }
            }
        }
        optionalRooms = allRoomsInApp.stream().filter(r -> !optionalUnavailableRooms.contains(r)).collect(Collectors.toList());

        return optionalRooms;
    }

}


/*
    public boolean addRoom(IRoom room) {
        if (allRoomsInApp.isEmpty()) {
            allRoomsInApp.add(room);
        } else {
            for (IRoom eachRoom : getAllRooms()) {
                if (!room.getRoomNumber().equals(eachRoom.getRoomNumber())) {
                    allRoomsInApp.put(room.getRoomNumber(), room);
                } else {
                    System.out.println("""

                            Room number exists in system! Please add a room with a new room number.
                            """);
                    return false;
                }
            }
        }
        return true;
    }*/