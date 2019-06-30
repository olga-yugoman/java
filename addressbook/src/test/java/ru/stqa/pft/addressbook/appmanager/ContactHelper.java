package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.concurrent.TimeUnit;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    /******************************CONTACT CREATION******************/
    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getSurname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("email"), contactData.getEmail());
    }

    public void submitContactForm() {
        click(By.xpath("(//input[@type='submit'])"));
    }

    /******************************CONTACT MODIFICATION*************************/
    public void initContactModification() {
        click(By.cssSelector("img[alt='Edit']"));
    }

    /******************************CONTACT DELETION*************************/
    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
        acceptAlert();
        waitDeletionContact();
    }

    public void acceptAlert() {
        wd.switchTo().alert().accept();
        wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    private void waitDeletionContact() {
        wd.findElement(By.cssSelector(".msgbox"));
    }

    /****************************************COMMON*********************************/
    public void returntoHomePage() {
        click(By.linkText("home page"));
    }

    public void selectContact() {
        click(By.name("selected[]"));
    }
}
