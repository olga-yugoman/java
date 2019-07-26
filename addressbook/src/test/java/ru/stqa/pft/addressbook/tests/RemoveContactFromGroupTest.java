package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
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
        //Получаем из БД список всех констактов
        Contacts allContactsBeforeTest = app.db().contacts();

        //Выбираем контакт для теста и получаем список его групп
        ContactData contactBefore = allContactsBeforeTest.iterator().next();
        Groups groupsOfContactBefore = contactBefore.getGroups();

        //если у контакта нет групп, то добавляем
        if (groupsOfContactBefore.size() == 0) {
            Groups groups = app.db().groups();
            GroupData group = groups.iterator().next();
            app.contact().addToGroup(contactBefore, group);
            app.goTo().homePage();
            groupsOfContactBefore.add(group);
        }

        //выбираем у контакта группу, из которой его удалим
        GroupData deletedGroup = groupsOfContactBefore.iterator().next();

        //фильтруем страницу приложения по этой группе
        app.contact().filterPageByGroup(deletedGroup.getId());

        //удаляем контакт из группы
        app.contact().removeFromGroup(contactBefore);

        //получаем из БД заново список всех контактов
        Contacts allContactsAfterTest = app.db().contacts();

        //Получаем по id контакт, на котором проводился тест, и список его групп
        ContactData contactAfter = allContactsAfterTest
                .stream()
                .filter(c -> contactBefore.getId() == c.getId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Contact with id %s was not found", contactBefore.getId())));
        Groups groupsOfContactAfter = contactAfter.getGroups();


        assertThat(groupsOfContactAfter, equalTo(contactBefore.getGroups().without(deletedGroup)));

        /*Assert.assertTrue(contact.getGroups()
                .stream().noneMatch(g -> group.getId() == g.getId()));*/
 }
}
