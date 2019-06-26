package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sanbox.Point;

public class PointTests {

    @Test
    public void test1(){
        Point p1 = new Point(14 ,1);
        Point p2 = new Point(17,5);
        Assert.assertEquals(p1.distance(p2), 5.0);
    }

    @Test
    public void test2(){
        Point p1 = new Point(1,1);
        Point p2 = new Point(2,1);
        Assert.assertEquals(p1.distance(p2), 1.0);
    }

    @Test
    public void test3(){
        Point p1 = new Point(0 ,0);
        Point p2 = new Point(0,0);
        Assert.assertEquals(p1.distance(p2), 0.0);
    }
}
