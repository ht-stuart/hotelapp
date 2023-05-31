package model;

import java.util.regex.Pattern;

public class Customer {

    private final String firstName;
    private final String lastName;
    private String email = "^(.+)@(.+).com$";
    private final Pattern pattern = Pattern.compile(email);

    public Customer(String firstName, String lastName, String email) {
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid Email");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return ("First Name: " + firstName + "\n" + "Last Name: " + lastName + "\n" + "E-mail Address: " + email);

    }
}
