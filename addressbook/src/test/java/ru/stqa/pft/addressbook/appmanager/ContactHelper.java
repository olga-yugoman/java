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
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitContactForm() {
        click(By.xpath("(//input[@type='submit'])"));
    }

    public void create(ContactData contact, boolean creation) {
        initContactCreation();
        fillContactForm(contact, creation);
        submitContactForm();
        contactCache = null;
        returntoHomePage();
    }

    /******************************CONTACT MODIFICATION*************************/
    public void initContactModification(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
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
        return wd.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        } else {
            contactCache = new Contacts();
            List<WebElement> rows = wd.findElements(By.cssSelector("tr[name='entry']"));
            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
                String name = cells.get(2).getText();
                String surname = cells.get(1).getText();
                String allPhones = cells.get(5).getText();
                contactCache.add(new ContactData().withId(id).withFirstName(name).withSurname(surname)
                        .withAllPhones(allPhones));
            }
            return contactCache;
        }
    }

    public ContactData inforomEditForm(ContactData contact) {
        initContactModification(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstname).withSurname(lastname)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
    }
}
