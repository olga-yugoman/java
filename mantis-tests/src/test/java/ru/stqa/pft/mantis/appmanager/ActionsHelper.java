package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class ActionsHelper extends HelperBase {

    public ActionsHelper(ApplicationManager app) {
        super(app);
    }

    public void login(String username, String password) {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"),username);
        type(By.name("password"),password);
        click(By.cssSelector("input[type='submit']"));
        checkUserLogin(username);
    }

    private void checkUserLogin(String username) {
        WebElement element = wd.findElement(By.cssSelector("span[class='italic']"));
        element.getText().equals(username);
    }

    public void adminLogin() {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"), "administrator");
        click(By.cssSelector("input[value='Login']"));
        type(By.name("password"), "root");
        click(By.cssSelector("input[value='Login']"));
    }

    public void register(UserData user) throws IOException, MessagingException {
        app.registration().start(user.getUsername(), user.getEmail());
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());
        app.registration().finish(confirmationLink, user.getPassword());
    }

    public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        //фильтруем письма по mail и берем первое
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        //извлекаем ссылку из письма
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    public void selectUser(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", id))).click();
    }

    public void resetPassword() {
        wd.findElement(By.cssSelector("input[value='Reset Password']")).click();
    }

    public void userSetNewPassword(UserData user, String password) throws IOException, MessagingException {
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());
        app.registration().finish(confirmationLink, password);
    }
}
