package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;
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

    public void createContact(ContactData contact, boolean creation) {
        initContactCreation();
        fillContactForm(contact, creation);
        submitContactForm();
        returntoHomePage();
    }

    /******************************CONTACT MODIFICATION*************************/
    public void initContactModification(int index) {
        wd.findElements(By.cssSelector("img[alt='Edit']")).get(index).click();
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

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return  wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name='entry']"));
        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String name = element.findElement(By.xpath(".//td[3]")).getText();
            String surname = element.findElement(By.xpath(".//td[2]")).getText();
            ContactData contact = new ContactData(id, name, surname,null, null, null, null);
            contacts.add(contact);
        }
        return contacts;
    }
}
