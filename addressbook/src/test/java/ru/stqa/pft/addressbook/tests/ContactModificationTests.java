package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("update", "update", "update",
                "update", "update", null), false);
        app.getContactHelper().submitContactForm();
        app.getContactHelper().returntoHomePage();
    }
}
