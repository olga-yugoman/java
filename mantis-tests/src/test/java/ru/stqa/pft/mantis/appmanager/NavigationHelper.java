package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void manageUsersTab() {
        //click(By.cssSelector("a[href='/mantisbt-2.21.1/manage_overview_page.php']"));
        wd.get(app.getProperty("web.baseUrl") + "/manage_overview_page.php");
        click(By.linkText("manage_users_link"));
    }
}
