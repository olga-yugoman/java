package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Olga", "Yugoman", "address",
                    "0981223344", "mail@mail.ru", "test1"), true);
        }

        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().initContactModification(before.size() - 1);

        ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "update", "update", "update",
                "update", "update", null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactForm();
        app.getContactHelper().returntoHomePage();

        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
