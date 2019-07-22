package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.contact().all().size() == 0) {
            Groups groups = app.db().groups();
            File photo = new File("src/test/resources/stud.png");
            app.contact().create(new ContactData().withFirstName("Olga").withSurname("Yugoman")
                    .withEmail("q@q.ru").withEmail2("wwww@qaz.com").withEmail3("wwww@qaz.com")
                    .withPhoto(photo).inGroup(groups.iterator().next()), true);
        }
    }

    @Test
    public void testContactEmails() {
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().inforomEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails((contactInfoFromEditForm))));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }
}
