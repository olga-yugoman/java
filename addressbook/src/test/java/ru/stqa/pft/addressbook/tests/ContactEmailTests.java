package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Olga").withSurname("Yugoman")
                    .withEmail("q@q.ru").withEmail2("wwww@qaz.com").withEmail3("wwww@qaz.com").withGroup("test1"), true);
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
