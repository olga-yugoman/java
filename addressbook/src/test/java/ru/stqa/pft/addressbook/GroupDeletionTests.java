package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class GroupDeletionTests extends TestBase {

    @Test
    public void testGroupDeletionTests() throws Exception {
        gotoGroupPage();
        selectGroup();
        deleteSelectedGroups();
        returntoGroupPage();
    }

}
