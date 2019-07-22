package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class ChangeUserPasswordTest extends TestBase {

    private String password = "new_password";

    @BeforeMethod
    public void ensurePrecondition() throws IOException, MessagingException {
        if (app.db().users().size() == 0) {
            long now = System.currentTimeMillis();
            app.action().register(new UserData().setUsername(String.format("user%s", now))
                    .setEmail(String.format("user%s@localhost.localdomain", now)).setPassword("password"));
        }
    }

    @BeforeMethod
    public void StartMailServer() {
        app.mail().start();
    }

    @Test
    public void changeUserPasswordTest() throws IOException, MessagingException {
        Users allUsers =  app.db().users();
        UserData user = allUsers.iterator().next();

        app.action().adminLogin();
        app.goTo().manageUsersTab();
        app.action().selectUser(user.getId());
        app.action().resetPassword();

        app.action().userSetNewPassword(user, password);
        assertTrue(app.newSession().login(user.getUsername(), password));
    }

    @AfterMethod(alwaysRun = true)
    public void StopMailServer() {
        app.mail().stop();
    }
}
