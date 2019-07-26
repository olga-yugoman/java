package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.*;

public class AddContactToGroupTest extends TestBase {

    @BeforeTest
    public void ensurePreconditions () {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("group1"));
            app.goTo().homePage();
        }

        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Olga").withSurname("Yugoman")
                    .withAddress("address").withHomePhone("0981223344").withEmail("mail@mail.ru"), true);
        }
    }

    @Test
    public void testAddContactToGroup() {
        //получаем все контакты из БД и выбираем контакт для теста
        Contacts allContactsBefore = app.db().contacts();
        ContactData contactBefore = allContactsBefore.iterator().next();

        //получаем все группы из БД и выбираем группу, в которую добавить
        Groups groups = app.db().groups();
        GroupData group = groups.iterator().next();

        //добавили контакт в группу
        app.contact().addToGroup(contactBefore, group);

        //получаем из БД обновленный список контактов, выбираем тестовый контакт по id, получаем актуальный список его групп
        Contacts allContactsAfter = app.db().contacts();
        ContactData contactAfter = allContactsAfter
                .stream()
                .filter(c -> contactBefore.getId() == c.getId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Contact with id %s was not found", contactBefore.getId())));
        Groups groupsOfContactAfter = contactAfter.getGroups();

        assertThat(groupsOfContactAfter, equalTo(contactBefore.getGroups().withAdded(group)));
        //assertThat(groupsOfContactAfter, hasItem(group));
    }
}
