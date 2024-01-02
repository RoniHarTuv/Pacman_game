//ID 207199282;
import exe.ex3.game.Game;
import org.junit.Test;
import static org.junit.Assert.*;
public class Ex3AlgoTest {

    @Test
    /**
     *Test that the target is as expected:
     * considering ghost are close\ not close
     * considering PacMan place
     * considering map;
     */
    public void testTarget() {
        int[][] m = {{-1, -1, -1, -1, -1}, {-1, -2, -2, -1, -1}, {-1, -1, -1, -2, -1}, {-1, -1, -1, -1, -1}, {-2, -2, -1, -1, -2}, {-1, -1, -1, -1, -1}};
        Map map = new Map(m);
        Pixel2D myPlace=new Index2D(0,0);
        int obs= -2;
        int food= -1;
        int[][] ghosts1= {{0,1,5,4,0},{0,2,0,0,0},{0,3,0,0,0},{0,4,0,0,0}};
        int[][] ghosts2= {{0,1,0,1,0},{0,2,0,0,0},{0,3,0,0,0},{1,4,1,4,0}};
        Pixel2D ex1= new Index2D(0,1);
        Pixel2D ex2= new Index2D(4,2);
        //while ghost not close:
        Pixel2D target1= Ex3Algo.target(myPlace,map,ghosts1,obs,food);
        assertEquals(ex1,target1);
        //while ghosts close:
        Pixel2D target2= Ex3Algo.target(myPlace,map,ghosts2,obs,food);
        assertEquals(ex2,target2);
    }

    @Test
    /**
     *test that if there is a ghost in distance 6 pixels or fewer , return true.
     * test that if there is a ghost in distance 7 pixels or more , return false.
     */
    public void testGhostIsClose() {
        //ghost is close:
        int[][] m = {{-1, -1, -1, -1, -1}, {-1, -2, -2, -1, -1}, {-1, -1, -1, -2, -1}, {-1, -1, -1, -1, -1}, {-2, -2, -1, -1, -2}, {-1, -1, -1, -1, -1}};
        Map map = new Map(m);
        Pixel2D myPlace=new Index2D(0,0);
        int obs= -2;
        int[][] ghosts= {{1,1,5,4,0},{0,2,0,0,0},{0,3,0,0,0},{0,4,0,0,0}};
        boolean ans=  Ex3Algo.ghostIsClose(myPlace, map, ghosts,obs);
        assertTrue(ans);
        //ghosts isn't close:
        int[][] ghosts1= {{0,1,4,3,0},{0,2,0,0,0},{0,3,0,0,0},{0,4,0,0,0}};
        boolean ans1=  Ex3Algo.ghostIsClose(myPlace, map, ghosts1,obs);
        assertTrue(!ans1);
    }

    @Test
    /**
     *test that PacMan place in Pixel is as expected ;
     */
    public void testPacPlace() {
        String pacString= "7,10";
        Pixel2D pacPixel= new Index2D(7,10);
        Pixel2D pacPixel1= new Index2D(10,7);
        Pixel2D pacPixel2= new Index2D(8,10);
        Pixel2D pacPlace= Ex3Algo.pacPlace(pacString);
        assertEquals(pacPlace,pacPixel);
        assertNotEquals(pacPixel1,pacPlace);
        assertNotEquals(pacPixel2,pacPlace);
    }

    @Test
    /**
     *
     *Test that the direction pacMan choose is as expected(in cyclic\not cyclic mode)
     */
    public void testDecideDir() {
        int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
        Pixel2D p= new Index2D(1,0);
        Pixel2D target1= new Index2D(0,0);
        Pixel2D target2= new Index2D(2,0);
        Pixel2D target3= new Index2D(1,1);
        Pixel2D target4= new Index2D(5,0);
        Pixel2D target5= new Index2D(0,4);
        int height= 5;
        int wight= 6;
        int ex1= Ex3Algo.decideDir(p,target1,wight,height);
        int ex2= Ex3Algo.decideDir(p,target2,wight,height);
        int ex3= Ex3Algo.decideDir(p,target3,wight,height);
        int ex4= Ex3Algo.decideDir(target1,target4,wight,height);
        int ex5= Ex3Algo.decideDir(target1,target5,wight,height);
        int ex6= Ex3Algo.decideDir(target3,p,wight,height);
        int ex7= Ex3Algo.decideDir(target4,target1,wight,height);
        int ex8= Ex3Algo.decideDir(target5,target1,wight,height);
        assertTrue(ex1==left);
        assertTrue(ex2==right);
        assertTrue(ex3==up);
        assertTrue(ex4==left);
        assertTrue(ex5==down);
        assertTrue(ex6==down);
        assertTrue(ex7==right);
        assertTrue(ex8==up);
    }
}