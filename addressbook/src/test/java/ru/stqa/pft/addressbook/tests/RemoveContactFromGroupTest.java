package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collector;

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
        //Получаем из БД список всех констактов и групп и выбираем контакт для теста
        Contacts allContactsBeforeTest = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData contactBefore = allContactsBeforeTest.iterator().next();

        //проверяем наличие групп у контакта, если нет - добавляем в группу
        if (contactBefore.getGroups().size() == 0) {
            GroupData group = groups.iterator().next();
            app.contact().addToGroup(contactBefore, group);
            app.goTo().homePage();
        }

        //получаем список групп контакта и выбираем одну
        Groups groupsOfContact = contactBefore.getGroups();
        GroupData group = groupsOfContact.iterator().next();

        //фильтруем страницу приложения по группам
        app.contact().filterPageByGroup(group.getId());

        //удаляем контакт из группы
        app.contact().removeFromGroup(contactBefore);

        //получаем заново контакты из БД и выбираем контакт из теста
        Contacts allContactsAfterTest = app.db().contacts();
        Assert.assertEquals(allContactsAfterTest, allContactsBeforeTest, "Contacts in DB are not equal");

        /*ContactData contact = allContactsAfterTest
                .stream()
                .filter(c -> contactBefore.getId() == c.getId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Contact with id %s was not found", contactBefore.getId())));

        Assert.assertTrue(contact.getGroups()
                .stream().noneMatch(g -> group.getId() == g.getId()));*/
 }
}
