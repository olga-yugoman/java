package ru.stqa.pft.rest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

public class RestTests extends TestBase{

    @Test
    public void testCreationIssue() throws IOException {
        skipIfNotFixed(1570);
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue().withSubject("TestOY").withDescription("New test issue");
        int issueId = createIssue(newIssue);

        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId));
        Assert.assertEquals(newIssues, oldIssues);
    }
}
