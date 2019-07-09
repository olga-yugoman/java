package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    /******************************CONTACT CREATION******************/
    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getSurname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("email"), contactData.getEmail());

        if(creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitContactForm() {
        click(By.xpath("(//input[@type='submit'])"));
    }

    public void create (ContactData contact, boolean creation) {
        initContactCreation();
        fillContactForm(contact, creation);
        submitContactForm();
        contactCache = null;
        returntoHomePage();
    }

    /******************************CONTACT MODIFICATION*************************/
    public void initContactModification(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
    }

    public void modify(ContactData contact) {
        initContactModification(contact.getId());
        fillContactForm(contact, false);
        submitContactForm();
        contactCache = null;
        returntoHomePage();
    }

    /******************************CONTACT DELETION*************************/
    public void deleteSelected() {
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


    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        contactCache = null;
        deleteSelected();
    }

    /****************************************COMMON*********************************/
    public void returntoHomePage() {
        click(By.linkText("home page"));
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return  wd.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        } else {
            contactCache = new Contacts();
            List<WebElement> elements = wd.findElements(By.cssSelector("tr[name='entry']"));
            for (WebElement element : elements) {
                int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
                String name = element.findElement(By.xpath(".//td[3]")).getText();
                String surname = element.findElement(By.xpath(".//td[2]")).getText();
                contactCache.add(new ContactData().withId(id).withFirstName(name).withSurname(surname));
            }
            return contactCache;
        }
    }
}
