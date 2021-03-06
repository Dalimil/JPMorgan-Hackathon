package entity;

/**
 * Created by whdinata on 11/7/15.
 */
public class RegistrationData {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private String password;

    public RegistrationData(String firstName, String lastName, String address, String email, String phone, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
