package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.*;

public class AddContactToGroupTest extends TestBase {

    @BeforeTest
    public void ensurePreconditions () {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("group1"));
        }

        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Olga").withSurname("Yugoman")
                    .withAddress("address").withHomePhone("0981223344").withEmail("mail@mail.ru"), true);
        }
    }

    @Test
    public void testAddContactToGroup() {
        Contacts allContacts = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData contactToAdd = allContacts.iterator().next();
        GroupData group = groups.iterator().next();
        app.contact().addToGroup(contactToAdd, group);
        app.goTo().homePage();

        assertThat(contactToAdd.getGroups(), hasItem(group));
    }
}
