package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData("Olga", "Yugoman", "address",
                "0981223344", "mail@mail.ru", "test1"), true);
        app.getContactHelper().submitContactForm();
        app.getContactHelper().returntoHomePage();
    }
}
