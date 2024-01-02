//ID 207199282;
import org.junit.Test;

import static org.junit.Assert.*;
public class MapTest {
 @Test
 /**
  *tests that the values of each index it as defined;
  */
 public void testInit1() {
  Map m = new Map(5, 5, 5);
  for (int i = 0; i < 5; i++) {
   for (int j = 0; j < 5; j++) {
    assertEquals(5, m.getMap()[i][j]);
   }
  }
 }

 @Test
 /**
  *tests that the functions "init" return the same map.
  */
 public void testInit2() {
  Map m1 = new Map(3);
  Map m2 = new Map(3, 3, 0);
  int[][] h = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
  Map m3 = new Map(h);
  assertEquals(m1.getMap(), m2.getMap());
  assertEquals(m2.getMap(), m3.getMap());
 }

 @Test
 /**
  *tests that the maps are same by different constructors;
  */
 public void testMapConstracor() {
  Map m1 = new Map(5, 5, 2);
  int[][] arr1 = {{2, 2, 2, 2, 2}, {2, 2, 2, 2, 2}, {2, 2, 2, 2, 2}, {2, 2, 2, 2, 2}, {2, 2, 2, 2, 2}};
  Map m2 = new Map(arr1);
  assertEquals(m1.getMap(), m2.getMap());
  Map m3 = new Map(arr1);
  assertEquals(m2.getMap(), m3.getMap());
 }

 @Test
 /**
  *tests that the functions 'get map' return the same map as set int[][] map.
  */
 public void getMap1() {
  int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
  Map m1 = new Map(a);
  int[][] arr1 = m1.getMap();
  //checks if it gets the same map;
  assertEquals(arr1, a);
  for (int i = 0; i < 2; i++) {
   for (int j = 0; j < 2; j++) {
    // checks that the condition work at the map arr1;
    assertTrue(arr1[i][j] < arr1[i + 1][j + 1]);
   }
  }
 }

 @Test
 /**
  *tests that getWidth() returns the correct width:
  */
 public void getWidth() {
  int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
  Map map = new Map(a);
  assertEquals(map.getWidth(), 3);
  int[][] b = {{1}};
  Map map1 = new Map(b);
  assertEquals(map1.getWidth(), 1);
 }

 @Test
 /**
  * tests that getHeight() returns the correct height:
  */
 public void testGetHeight() {
  int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
  Map map = new Map(a);
  assertEquals(map.getHeight(), 3);
  int[][] b = {{1}};
  Map map1 = new Map(b);
  assertEquals(map1.getHeight(), 1);
 }

 @Test
 /**
  * tests that getPixel-(Pixel2D p) & (int x, int y) returns the correct value:
  */
 public void testGetPixel() {
  int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
  Map map = new Map(a);
  Pixel2D p = new Index2D(2, 2);
  // Pixel2D p1= new Index2D(2,3);
  int t = map.getPixel(0, 0);
  assertEquals(1, t);
  assertEquals(9, map.getPixel(p));
 }

 @Test
 /**
  * tests that setPixel(int x, int y, int v) & (Pixel2D p, int v) sets the pixel to the correct value;
  */
 public void testSetPixel() {
  int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
  Map map = new Map(a);
  map.setPixel(0, 2, 6);
  assertEquals(6, map.getPixel(0, 2));
  Pixel2D p = new Index2D(2, 2);
  map.setPixel(p, 8);
  assertEquals(8, map.getPixel(2, 2));
 }

 @Test
 /**
  * tests that Fill returns the expected map;
  * tests that fill returns the expected sum of replace;
  */
 public void testFill() {
  //create two new map: m,m2
  int[][] m = {{-1, -1, -1, -1, -1}, {-1, -2, -2, -1, -1}, {-1, -1, -1, -2, -1}, {-1, -1, -1, -1, -1}, {-2, -2, -1, -1, -2}, {-1, -1, -1, -1, -1}};
  Map2D map = new Map(m);
  Pixel2D p = new Index2D(3, 2);
  int v = 0;
  map.setCyclic(false);
  map.fill(p, v);
  int[][] m2 = {{0, 0, 0, 0, 0}, {0, -2, -2, 0, 0}, {0, 0, 0, -2, 0}, {0, 0, 0, 0, 0}, {-2, -2, 0, 0, -2}, {0, 0, 0, 0, 0}};
  for (int i = 0; i < m2.length; i++) {
   for (int j = 0; j < m2[0].length; j++) {
    // check equality between the arrays;
    assertEquals(m2[i][j], map.getMap()[i][j]);
    int l = map.fill(p, v);
    // sum of replaces;
    assertEquals(l, 24);
   }
  }
 }

 @Test
 /**
  * tests the allDistance function, with cyclic and not cyclic maps:
  **/
 public void testAllDistance() {
  // array to build maps with:
  int[][] arr1 = {{-1, -1, -1, -1, -2}, {-1, -1, -2, -1, -1}, {-1, -2, -1, -2, -1}, {-1, -1, -2, -1, -1}};
  // expected arrays:
  int[][] exp2 = {{0, 1, 2, 3, -2}, {1, 2, -2, 3, 2}, {2, -2, -1, -2, 3}, {1, 2, -2, 3, 2}};
  int[][] exp3 = {{2, 3, 3, 2, -2}, {3, 4, -2, 3, 2}, {2, -2, -1, -2, 1}, {1, 2, -2, 1, 0}};
  int[][] exp4 = {{0, 1, 2, 3, -2}, {1, 2, -2, 4, 5}, {2, -2, -1, -2, 6}, {3, 4, -2, 8, 7}};
  int[][] exp5 = {{7, 6, 5, 4, -2}, {8, 7, -2, 3, 2}, {9, -2, -1, -2, 1}, {10, 11, -2, 1, 0}};
  // create points to use:
  Pixel2D p3 = new Index2D(0, 0), p4 = new Index2D(3, 4);
  // create map to use:
  Map2D map1 = new Map(arr1);
  // get allDistance maps (cyclic)
  Map2D map2 = map1.allDistance(p3, -2);
  Map2D map3 = map1.allDistance(p4, -2);
  // check that map is equal to exp:
  for (int i = 0; i < arr1.length; i++) {
   for (int j = 0; j < arr1[0].length; j++) {
    assertEquals(exp2[i][j], map2.getMap()[i][j]);
    assertEquals(exp3[i][j], map3.getMap()[i][j]);
   }
  }

  // set map to not cyclic:
  map1.setCyclic(false);
  // get allDistance maps (not cyclic)
  Map2D map4 = map1.allDistance(p3, -2);
  Map2D map5 = map1.allDistance(p4, -2);
  // check that map is equal to exp:
  for (int i = 0; i < arr1.length; i++) {
   for (int j = 0; j < arr1[0].length; j++) {
    assertEquals(exp4[i][j], map4.getMap()[i][j]);
    assertEquals(exp5[i][j], map5.getMap()[i][j]);
   }
  }
 }

 @Test
 /**
  * tests fill on an array which has an inaccessible spot;
  * tests that fills the array correctly and returns the expected value,
  * on non-cyclic and cyclic maps:
  */
 public void testFill2() {
  int[][] arr = {{-1, -1, -1, -1, -2}, {-1, -1, -2, -1, -1}, {-1, -2, -1, -2, -2}, {-1, -1, -2, -1, -1}};
  Pixel2D p = new Index2D(0, 0);

  // not cyclic map;
  int[][] exp = {{6, 6, 6, 6, -2}, {6, 6, -2, 6, 6}, {6, -2, -1, -2, -2}, {6, 6, -2, -1, -1}};
  Map2D map = new Map(arr);
  map.setCyclic(false);
  int n = map.fill(p, 6);
  int[][] ans = map.getMap();
  for (int i = 0; i < exp.length; i++) {
   for (int j = 0; j < exp[0].length; j++) {
    assertEquals(exp[i][j], ans[i][j]);
   }
  }
  assertEquals(11, n);
  // cyclic map;
  int[][] exp2 = {{6, 6, 6, 6, -2}, {6, 6, -2, 6, 6}, {6, -2, -1, -2, -2}, {6, 6, -2, 6, 6}};
  Map2D map2 = new Map(arr);
  map2.setCyclic(true);
  int n2 = map2.fill(p, 6);
  int[][] ans2 = map2.getMap();
  for (int i = 0; i < exp2.length; i++) {
   for (int j = 0; j < exp2[0].length; j++) {
    assertEquals(exp2[i][j], ans2[i][j]);
   }
  }
  assertEquals(13, n2);
 }

 @Test
 /**
  *test that the function return the same Pixel2D as expected;
  */
 public void testWaysPixel() {
  int[][] m = {{5, 6, 7, 6, 5}, {4, -2, -2, 5, 4}, {3, 2, 1, 2, 3}, {2, 1, 0, 1, 2}, {-2, -2, 1, 2, -2}, {4, 3, 2, 3, 4}};
  Map2D map = new Map(m);
  map.setCyclic(false);
  Pixel2D p = new Index2D(0, 0);
  Pixel2D p1 = Map.waysPixel(map, p);
  Pixel2D p2 = new Index2D(1, 0);
  assertEquals(p2, p1);

  int[][] m2 = {{5, 4, 3, 4, 5}, {4, -2, -2, -2, 4}, {3, 2, 1, 2, 3}, {2, 1, 0, 1, 2}, {-2, -2, 1, 2, -2}, {4, 3, 2, 3, 4}};
  Map2D map2 = new Map(m2);
  Pixel2D p3 = new Index2D(5, 0);
  map.setCyclic(true);
  Pixel2D p4 = Map.waysPixel(map2, p);
  assertEquals(p3, p4);
 }

 @Test
 /**
  *test that the shorter path between two Pixels2D are as expected;
  */
 public void testShortestPath() {
  int[][] m = {{5, 6, 7, 6, 5}, {4, -2, -2, -2, 4}, {3, 2, 1, 2, 3}, {2, 1, 0, 1, 2}, {-2, -2, 1, 2, -2}, {4, 3, 2, 3, 4}};
  Map2D map = new Map(m);
  map.setCyclic(false);
  Pixel2D p = new Index2D(0, 0);
  Pixel2D p1 = new Index2D(3, 2);
  Pixel2D p2 = new Index2D(3, 1);
  Pixel2D p3 = new Index2D(3, 0);
  Pixel2D p4 = new Index2D(2, 0);
  Pixel2D p5 = new Index2D(1, 0);
  Pixel2D[] shortestPath = map.shortestPath(p1, p, -2);
  assertEquals(shortestPath, new Pixel2D[]{p1, p2, p3, p4, p5, p});
 }

 @Test
 /**
  *test that some Pixel2D (that out of bounds) is inside the map
  * test that some Pixel2D (that actually inside the map) is inside the map
  */
 public void testIsInside() {
  int[][] m = {{5, 6, 7, 6, 5}, {4, -2, -2, -2, 4}, {3, 2, 1, 2, 3}, {2, 1, 0, 1, 2}, {-2, -2, 1, 2, -2}, {4, 3, 2, 3, 4}};
  Map2D map = new Map(m);
  map.setCyclic(false);
  Pixel2D p = new Index2D(0, 0);
  Pixel2D p1 = new Index2D(3, 2);
  Pixel2D p2 = new Index2D(7, 5);
  Pixel2D p3 = new Index2D();
  assertTrue(map.isInside(p));
  assertTrue(map.isInside(p1));
  assertFalse(map.isInside(p2));
  assertTrue(map.isInside(p3));
 }

 @Test
 /**
  *given a basic map and two variation of cyclic and not cyclic maps, check if return the same maps;
  */
 public void testIsCyclic() {
  int[][] m = {{-1, -1, -1, -1, -1}, {-1, -2, -2, -1, -1}, {-1, -1, -1, -2, -1}, {-1, -1, -1, -1, -1}, {-2, -2, -1, -1, -2}, {-1, -1, -1, -1, -1}};
  Map2D map1 = new Map(m);
  int[][] mNotCyclic = {{5, 6, 7, 6, 5}, {4, -2, -2, -2, 4}, {3, 2, 1, 2, 3}, {2, 1, 0, 1, 2}, {-2, -2, 1, 2, -2}, {4, 3, 2, 3, 4}};
  Map2D map2 = new Map(mNotCyclic);
  map2.setCyclic(false);
  int[][] mCyclic = {{5, 4, 3, 4, 5}, {4, -2, -2, -2, 4}, {3, 2, 1, 2, 3}, {2, 1, 0, 1, 2}, {-2, -2, 1, 2, -2}, {4, 3, 2, 3, 4}};
  Map2D map3 = new Map(mCyclic);
  assertTrue(map3.isCyclic());
  assertFalse(map2.isCyclic());
 }

 @Test
 /**
  *test that after set a map as cyclic ,it really is;
  */
 public void testSetCyclic() {
  int[][] m = {{-1, -1, -1, -1, -1}, {-1, -2, -2, -1, -1}, {-1, -1, -1, -2, -1}, {-1, -1, -1, -1, -1}, {-2, -2, -1, -1, -2}, {-1, -1, -1, -1, -1}};
  Map2D map1 = new Map(m);
  int[][] mNotCyclic = {{5, 6, 7, 6, 5}, {4, -2, -2, -2, 4}, {3, 2, 1, 2, 3}, {2, 1, 0, 1, 2}, {-2, -2, 1, 2, -2}, {4, 3, 2, 3, 4}};
  Map2D map2 = new Map(mNotCyclic);
  map2.setCyclic(false);
  assertFalse(map2.isCyclic());
 }
}