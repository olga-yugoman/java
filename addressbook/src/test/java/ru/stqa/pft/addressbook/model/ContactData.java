package ru.stqa.pft.addressbook.model;

public class ContactData {
    private int id;
    private final String firstName;
    private final String surname;
    private final String address;
    private final String homePhone;
    private final String email;
    private String group;

    public ContactData(int id, String firstName, String surname, String address, String homePhone, String email, String group) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.homePhone = homePhone;
        this.email = email;
        this.group = group;
    }

    public ContactData(String firstName, String surname, String address, String homePhone, String email, String group) {
        this.id = Integer.MAX_VALUE;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.homePhone = homePhone;
        this.email = email;
        this.group = group;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "ContactData{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        return surname != null ? surname.equals(that.surname) : that.surname == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }
}
