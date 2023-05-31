package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;

public class Reservation {

    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;
    private String dateFormat = "MM/dd/yyyy";

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("MMMM/dd/yyyy");
        return ("Customer: " + customer + "\n" + "Room: " + room + "\n" + "Check-In: " + df.format(checkInDate) + "\n" + "Check-Out: " + df.format(checkOutDate));
    }
}
