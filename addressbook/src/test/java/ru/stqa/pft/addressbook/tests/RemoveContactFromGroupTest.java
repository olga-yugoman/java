package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveContactFromGroupTest extends TestBase {

    @BeforeTest
    public void ensurePreconditions () {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("group1"));
        }

        if (app.db().contacts().size() == 0) {
            Groups groups = app.db().groups();
            app.contact().create(new ContactData().withFirstName("Olga").withSurname("Yugoman")
                    .withAddress("address").withHomePhone("0981223344").withEmail("mail@mail.ru")
                    .inGroup(groups.iterator().next()), true);
        }
    }

    @Test
    public void RemoveContactFromGroupTest() {
        Contacts allContacts = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData contact = allContacts.iterator().next();
        GroupData group = groups.iterator().next();
        app.contact().addToGroup(contact, group);
        app.contact().pageFilteredByGroup(group);

        app.contact().removeFromGroup(contact);

        assertThat(contact.getGroups(), not(group));
    }
}
