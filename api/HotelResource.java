package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public class HotelResource {


    private static HotelResource INSTANCE;
    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();

    private HotelResource() {
    }

    public static HotelResource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HotelResource();
        }

        return INSTANCE;
    }


    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String firstName, String lastName, String email) {
        customerService.addCustomer(firstName, lastName, email);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }


    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();

    }

    public Reservation reserveARoom(String customerEmail, IRoom room, Date checkIn, Date checkOut) {
        Customer newCustomer = customerService.getCustomer(customerEmail);
        return reservationService.reserveARoom(newCustomer, room, checkIn, checkOut);
    }

    public Collection<Reservation> getCustomerReservation(String customerEmail) {
        Customer validCustomer = getCustomer(customerEmail);
        return reservationService.getCustomerReservation(validCustomer);
    }

    public Collection<IRoom> findAvailableRooms(Date checkIn, Date checkOut) {
        return reservationService.openRooms(checkIn, checkOut);
    }

    public Collection<IRoom> optionalRooms(Date checkIn, Date checkOut) {
        return reservationService.optionalRooms(checkIn, checkOut);
    }


}
