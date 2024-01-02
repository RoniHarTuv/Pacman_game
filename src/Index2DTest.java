//ID 207199282;
import org.junit.Test;

import static org.junit.Assert.*;
public class Index2DTest {

    @Test
    /**
     *tests that the distance between two pixels is symmetric;
     */
    public void testDistance1(){
        Index2D p1 = new Index2D(2,2);
        Index2D p2 = new Index2D(-2,-2);
        double d1= p1.distance2D(p2);
        double d2= p2.distance2D(p1);
        assertEquals(d1,d2,0.0001);
    }
    @Test
    /**
     * Test that tha function return the same distance from p1 and p2 to (0,0)
     * Test that distance 0 return 0
     */
    public void testDistance2(){
        Index2D p1 = new Index2D(2,2);
        Index2D p2 = new Index2D(-2,-2);
        Index2D p3= new Index2D(0,0);
        double d1= p1.distance2D(p3);
        double d2= p2.distance2D(p3);
        assertEquals(d1,d2,0.0001);
        double d3= p3.distance2D(p3);
        assertEquals(d3,0,0.0001);
    }
    @Test
    /**
     * tests that the function can work with negative values:
     */
    public void testDistance3(){
        Index2D p1 = new Index2D(3, 2);
        Index2D p2 = new Index2D(8, 10);
        Index2D p3 = new Index2D(-5, -3);
        double d1 = p1.distance2D(p2);
        double d2 = p1.distance2D(p3);
        assertEquals(d1, d2, 0.001);
    }
    @Test
    /**
     * Test that the function return the correct distance;
     */
    public void testDistance4() {
        Index2D p1 = new Index2D(1, 1);
        Index2D p2 = new Index2D(0, 0);
        Index2D p3 = new Index2D(0, -1);
        Index2D p4 = new Index2D(1, 0);
        double d = p1.distance2D(p2);
        assertEquals(d, Math.sqrt(2), 0.0001);
        double d1 = p3.distance2D(p4);
        assertEquals(d1, Math.sqrt(2), 0.0001);
    }
    @Test
    /**
     * Test that two pixels with same value are equal ;
     * Tests that two pixels with different value are not equal;
     */
    public void testEquals1() {
        Pixel2D p1= new Index2D(5,5);
        Pixel2D p3= new Index2D(5,5);
        Pixel2D p2= new Index2D(8,10);
        assertFalse(p1.equals(p2));
        assertTrue(p1.equals(p3));
    }
    @Test
    /**
     * Test that if t isn't pixel-return false;
     */
    public void testEquals2() {
        Pixel2D p1= new Index2D(5,5);
        int[][]t={{5},{5}};
        assertFalse(p1.equals(t));

    }
    @Test
    /**
     * tests that different types return false:
     */
    public void testDifferentType(){
        Index2D p1 = new Index2D(8, 13);
        int[] t1 = {8, 13};
        assertFalse(p1.equals(t1));
        Index2D p2 = new Index2D(0, 0);
        int[] t2 = {0, 0};
        assertFalse(p1.equals(t1));
    }

}
