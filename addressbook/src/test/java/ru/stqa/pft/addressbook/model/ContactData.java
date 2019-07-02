package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String firstName;
    private final String surname;
    private final String address;
    private final String homePhone;
    private final String email;
    private String group;

    public ContactData(String firstName, String surname, String address, String homePhone, String email, String group) {
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.homePhone = homePhone;
        this.email = email;
        this.group = group;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }
}
