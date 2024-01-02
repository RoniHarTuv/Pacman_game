//ID 207199282;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;
/**
 * This is the major algorithmic class for Ex3 - the PacMan game:
 * This code is a very simple example (random-walk algorithm).
 * Your task is to implement (here) your PacMan algorithm.
 */
public class Ex3Algo implements PacManAlgo{
	private int _count;

	public Ex3Algo() {_count=0;}

	@Override
	/**
	 *  description for the algorithm:
	 */
	public String getInfo() {
		return "description of the algorithm:" +
				"This algorithm uses a number of parameters in order to decide what actions the pacMan need to do." +
				"decides each time which direction to go considering the following circumstances:" +

				"1. Ghosts- If there is a ghost that threatens the Pacman (6 Pixels or less far from PacMan):" +
				"if the ghost is eatable, the ghost location will be the target of tha PacMan." +
				"if the ghost isn't eatable, the algorithm create new distance map frm each active ghost , that calculate all distances from the ghost to PacMan." +
				"after, calculate the sum of the maps,in order to find the Pixel with the maximum value - the furthest from the ghosts."+
				"this Pixel will be the target of tha PacMan." +

				"if no ghosts are close:" +
				"2. Pink/Green points(food) - decide to move to the closet pink point by 'allDistances' function from Pacman to the entire game map:" +
				" if there is a pink point at a distance of 1 from the Pacman"+
				"(that it is not defined as an obstacle, and there is no danger of a ghost) the pacMan will move to it." +
				"  Otherwise, searches in radius 2 (according to 'allDistances') and again repeats the process of checking the ghost, and the goal is to eat a pink point." +

				"3. When deciding where exactly the Pacman should go-" +
				"call the 'shortestPath' function and check what is the shortest way to get from the Pacman to the destination point.";
	}

	@Override
	/**
	 * This function is the main function that :
	 * 1. get the game data.(cyclic, directions, game map....)
	 * 2. consider all this data, and decides the next target.
	 * 3. decide what the next step of the pacman should be to get to the target.
	 */
	/*This function define and print the setting of the game:
	*colors of obs/food
	* directions- up, down, left, right
	* ghosts.
	*/
	public int move(PacmanGame game) {
		int code = 0;
		String pos = game.getPos(code).toString();
		int[][] board = game.getGame(0);
		//color of each pixel on the map
		int blue = Game.getIntColor(Color.BLUE, code);
		int pink = Game.getIntColor(Color.PINK, code);
		GhostCL[] ghosts = game.getGhosts(code);
		if(_count==0 || _count==300) {
			printBoard(board);
			//color of each pixel on the map
			int black = Game.getIntColor(Color.BLACK, code);
			int green = Game.getIntColor(Color.GREEN, code);
			System.out.println("Blue=" + blue + ", Pink=" + pink + ", Black=" + black + ", Green=" + green);
			System.out.println("Pacman coordinate: "+pos);
			printGhosts(ghosts);
			//directions:
			int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
		}
		_count++;
		// use all the functions that was created in this file:
		//ghosts information:
		int[][] ghostsInfo = ghosts(ghosts);
		//the place of the pacman:
		Pixel2D myPlace = pacPlace(pos);
		//the map of this game:
		Map gameMap = new Map(board);
		//the cyclic mode:
		gameMap.setCyclic(game.isCyclic());
		//the target (where the pacman go), consider all the follow data:
		Pixel2D target = target(myPlace, gameMap, ghostsInfo, blue, pink);
		//decide the target by using 'shortestPath':
		Pixel2D[] shortestPath = gameMap.shortestPath(myPlace, target, blue);
		//after find the target and the way to is, decide in which direction the pacman needs to move:
		if(shortestPath!=null && shortestPath.length>1){
			return decideDir(myPlace, shortestPath[1], gameMap.getWidth(), gameMap.getHeight());
		}

		return 0;
	}

	/**
	 * this function return information about all the ghosts - status,type,pos,time.
	 * @param g
	 * @return int[][] ghosts
	 */
	// this function get array of Strings with information about the ghosts.
	// creating new 2D array that go over each ghost(from the 6) and add the information about it to the array
	public static int[][] ghosts(GhostCL[] g){
		int[][] ghosts = new int[6][5];
		for(int i=0; i<6; i++){
			// information about the ghost's status - active or not:
			ghosts[i][0] = g[i].getStatus();
			// information about the ghost's type:
			ghosts[i][1] = g[i].getType();
			String[] split = g[i].getPos(0).split(",");
			//information about the ghost's x value in Pixel2D:
			ghosts[i][2] = Integer.parseInt(split[0]); // x
			//information about the ghost's y value in Pixel2D:
			ghosts[i][3] = Integer.parseInt(split[1]); //y
			//casting to int, information about the time:
			ghosts[i][4] = (int) g[i].remainTimeAsEatable(0);
		}
		return ghosts;
	}

	/**
	 * this function return the pixel2D value of the target pixel that the pacman want to arrive.
	 * @param myPlace
	 * @param map
	 * @param ghosts
	 * @return
	 */
	/*the function decided in which Pixel the pacman needs to go.
	*first,check if there is any ghosts that it needs to go away from.
	* calling to function 'ghostIsClose' to check that.
	*when 'ghostIsClose' return true-create a new map 'distance map', that consider each distance from all the ghosts,
	* to pacman,using 'allDistance' function.
	*decide to move to the Pixel that it farthest from all ghosts.
	*after, consider the pink points as a target, use 'allDistance(myPlace,obs)'
	*in order to find the closet point-and move to it.
	*/
	public static Pixel2D target(Pixel2D myPlace, Map map, int[][] ghosts, int obs, int food){
		int w = map.getWidth(), h = map.getHeight();
		//need to choose the target point;
		//consider two situations:
		Pixel2D target = new Index2D(myPlace);
		//1- ghost. while ghost is close-
		if(ghostIsClose(myPlace, map, ghosts, obs)){
			//create new map of distances from ghosts to pacman:
			int[][] distanceMap= new int[w][h];
			for (int i = 0; i < ghosts.length; i++) {
				//if the ghosts is eatable:
				if (ghosts[i][4]>=1.5){
					Pixel2D g= new Index2D(ghosts[i][2],ghosts[i][3]);
					return map.shortestPath(g,myPlace,obs)[0];
				}
				if (ghosts[i][0]==1){
					Pixel2D p = new Index2D(ghosts[i][2], ghosts[i][3]);
					int[][] temp = map.allDistance(p,obs).getMap();
					for (int j = 0; j < w; j++) {
						for (int k = 0; k < h; k++) {
							distanceMap[j][k] += temp[j][k];
						}
					}
				}
			}
			//find the farthest point to run away:
			for (int j = 0; j < w; j++) {
				for (int k = 0; k < h; k++) {
					Pixel2D temp = new Index2D(j, k);
					if(distanceMap[j][k]>distanceMap[target.getX()][target.getY()]){
						for (int i = 0; i < ghosts.length; i++) {
							if(ghosts[i][4]<0){
								Pixel2D g = new Index2D(ghosts[i][2], ghosts[i][3]);
								map.setPixel(g, obs);
							}
						}
						if(map.shortestPath(target, myPlace,obs)!=null){
							target = new Index2D(temp);
						}
						else {
							Map2D foodMap= map.allDistance(myPlace,obs);
							for (int i = 1; i < 900; i++) {
								for (int x = 0; x < w; x++) {
									for (int y = 0; y < h; y++) {
										if(foodMap.getMap()[x][y]==i && map.getPixel(x,y)==food){
											return new Index2D(x,y);
										}
									}
								}
							}
						}
					}
				}
			}
			return target;
		}
		//2- want to eat the pink point;
		// go over all the map, and find the closest point;
		Map2D foodMap= map.allDistance(myPlace,obs);
		for (int i = 1; i < 900; i++) {
			for (int j = 0; j < w; j++) {
				for (int k = 0; k < h; k++) {
					if(foodMap.getMap()[j][k]==i && map.getPixel(j,k)==food){
						return new Index2D(j,k);
					}
				}
			}
		}
		return target;
	}

	/**
	 * this function return "true" when the algorithm need to consider information about the ghost.
	 * @param myPlace
	 * @param map
	 * @param ghosts
	 * @return
	 */
	//this function was created in order to help the pacman decide if it needs to consider the ghost or not.
	// the function check two things: first, if the ghost is active. second, if the distance between the pacman
	// and the ghost is less than 6. if so, the pacman need to go away. else-keep going.
	public static boolean ghostIsClose(Pixel2D myPlace, Map map, int[][] ghosts, int obs){
		//check each ghost:
		for (int i = 0; i < ghosts.length; i++) {
			//check if active:
			if(ghosts[i][0]==1){
				//check the distance:
				Pixel2D gxy= new Index2D(ghosts[i][2], ghosts[i][3]);
				if(map.shortestPath(myPlace, gxy, obs).length<6){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * this function return the pacman place in Pixel2D.
	 * @param pos
	 * @return
	 */
	//this function was created in order to get information about the Pixel2D where the pacman is located.
	// casting the String to Pixel by split the String, and parse to Int each index. using constructor to create the pixel2D
	public static Pixel2D pacPlace(String pos){
		String[] split = pos.split(",");
		int x = Integer.parseInt(split[0]);
		int y = Integer.parseInt(split[1]);
		Pixel2D ans = new Index2D(x, y);
		return ans;
	}

	/**
	 * this function decide in which direction the pacman will go (consider the target).
	 * @param p- pacman
	 * @param pos -target
	 * @return -direction
	 */
	//get the information about the Pixel we want to arrive by using "getX" and "getY"
	// decide direction according to x and y value of the target point.
	//consider if the map are cyclic or not.
	public static int decideDir(Pixel2D p, Pixel2D pos, int width ,int height){

		int pX = p.getX(), pY = p.getY(), posX = pos.getX(), posY = pos.getY();
		//cyclic mode:
		if(pX==0 && posX== width-1){
			return Game.LEFT;
		}
		if(pX==width-1 && posX==0 ) {
			return Game.RIGHT;
		}
		if(pY==height-1 && posY==0 ) {
			return Game.UP;
		}
		if(pY==0 && posY==height-1 ) {
			return Game.DOWN;
		}
		//not cyclic mode:
		if(pX == posX+1){
			return Game.LEFT;
		}
		if(pX == posX-1){
			return Game.RIGHT;
		}
		if(pY == posY+1){
			return Game.DOWN;
		}
		return Game.UP;
	}

	/**
	 * this function return 2D array of the map of the game.
	 * @param b
	 */
	private static void printBoard(int[][] b) {
		for(int y =0;y<b[0].length;y++){
			for(int x =0;x<b.length;x++){
				int v = b[x][y];
				System.out.print(v+"\t");
			}
			System.out.println();
		}
	}

	/**
	 * this function print information about the ghosts.
	 */
	private static void printGhosts(GhostCL[] gs) {
		for(int i=0;i<gs.length;i++){
			GhostCL g = gs[i];
			System.out.println(i+") status: "+g.getStatus()+",  type: "+g.getType()+",  pos: "+g.getPos(0)+",  time: "+g.remainTimeAsEatable(0));
		}
	}

	/**
	 * this function is a random algorithm that decide direction in random way.
	 * @return direction
	 */
	private static int randomDir() {
		int[] dirs = {Game.UP, Game.LEFT, Game.DOWN, Game.RIGHT};
		int ind = (int)(Math.random()*dirs.length);
		return dirs[ind];
	}
}