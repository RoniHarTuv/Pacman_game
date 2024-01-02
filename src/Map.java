//ID 207199282;
import java.util.Arrays;

/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D {
	private int[][] _map;
	private boolean _cyclicFlag = true;

	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {init(w,h, v);}

	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {this(size,size, 0);}

	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {init(data);
	}

	@Override
	/**
	 * this function Constructs a 2D raster map from a given 2D int array,
	 * by create new array and fill it(with for loop) in v value;
	 */
	public void init(int w, int h, int v) {
		//create new 2D array
		this._map= new int[w][h];
		// fill all the indexes in "v" value;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				this._map[i][j]=v;
			}
		}
	}

	@Override
	/**
	 *this function Computes a deep copy of the underline 2D matrix :
	 * by create new 2D array
	 * and fill it in the same value of arr values.
	 * before the creating,make sure that the array is not ragged;
	 */
	public void init(int[][] arr) {
		//check if ragged;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].length!= arr[0].length){
				throw new  RuntimeException("ragged array");
			}
		}
		//create new 2D array in the same length of arr;
		this._map= new int[arr.length][arr[0].length];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				//fill this map in the same value of arr;
				this._map[i][j]=arr[i][j];
			}
		}
	}

	@Override
	/**
	 * this function Computes a deep copy of the underline 2D matrix by creating new array
	 * and fill it by go over all the indexes;
	 */
	public int[][] getMap() {
		int[][] ans = null;
		//create a new array
		ans=new int[this._map.length][this._map[0].length];
		//fill the map
		for (int i
			 = 0; i < this._map.length; i++) {
			for (int j = 0; j < this._map[0].length; j++) {
				ans[i][j]= this._map[i][j];
			}
		}
		return ans;
	}

	@Override
	/**
	 * this function return the width of this 2D map (first coordinate) by using 'length' function;
	 */
	public int getWidth() {
		int ans=0;
		ans= this._map.length;
		return ans;
	}

	@Override
	/**
	 * this function return the height of this 2D map (second coordinate) by using 'length' function;
	 */
	public int getHeight() {
		int ans=0;
		ans=this._map[0].length;
		return ans;
	}

	@Override
	/**
	 * giving X and Y values, this function return the [x][y] (int) value of the map[x][y].
	 */
	public int getPixel(int x, int y) {
		int ans=0;
		ans= this._map[x][y];
		return ans;}

	@Override
	/**
	 * this function return the [p.x][p.y] (int) value of the map by using 'get' function
	 */
	public int getPixel(Pixel2D p) {
		int ans=0;
		ans= this._map[p.getX()][p.getY()];
		return ans;    //this.getPixel(p.getX(),p.getY());
	}

	@Override
	/**
	 * this function Set the [x][y] coordinate of the map to v.
	 */

	public void setPixel(int x, int y, int v) {
		this._map[x][y]=v;
	}

	@Override
	/**
	 * this function Set the [x][y] coordinate of the map to v.
	 */
	public void setPixel(Pixel2D p, int v) {
		this._map[p.getX()][p.getY()]=v;
	}

	@Override
	/**
	 * Fills this map with the new color (new_v) starting from p.
	 * based on : https://en.wikipedia.org/wiki/Flood_fill.
	 *  this function calling to 'allDistance' function in order to get a map with all potential places to replace
	 *  (find the place that aren't obsColor)
	 *  after, go over the map and replace all the legal places in new_v;
	 *  return the numbers of replacement;
	 */
	public int fill(Pixel2D xy, int new_v) {
		int ans=0;// numbers of pixels;
		//set the obsColor as -2 and get the map
		int[][] map= this.allDistance(xy,-2).getMap();
		//search all the places that after 'all distance' still 'obsColor'
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j]>=0){
					//each time add 1 to answer in order to count how many places replaced.
					ans++;
					this.setPixel(i, j, new_v);
				}
			}
		}
		return ans;
	}

	/**
	 *  this function create the route between Pixels according to map of distances from point;
	 *  starts from the 'end' point to the 'start' point
	 * 	this function is complimentary to shortestPath;
	 * 	returns the pixel that should be the next step.
	 * 	checks the pixel's neighbors and if it finds a relevant one, returns it.
	 * 	after a relevant neighbor is found, there is no need to check the other neighbors.
	 **/
	public static Pixel2D waysPixel(Map2D map, Pixel2D p){
		int[][] mapArr = map.getMap();
		//mke sure that p is inside the map;
		if(map.isInside(p)){
			//define x and y values, find the array settings;
			int x = p.getX(), y = p.getY(), w = mapArr.length, h = mapArr[0].length, v = map.getPixel(p);
			// if the map is cyclic;
			if(map.isCyclic()){
				if(x==0 && mapArr[w-1][y]==v-1){
					return new Index2D(w-1, y);
				}
				if(y==0 && mapArr[x][h-1]==v-1){
					return new Index2D(x,h-1);
				}
				if(x==w-1 && mapArr[0][y]==v-1){
					return new Index2D(0,y);
				}
				if(y==h-1 && mapArr[x][0]==v-1) {
					return new Index2D(x, 0);
				}
			}
			if(x>0 && mapArr[x-1][y]==v-1){
				return new Index2D(x-1, y);
			}
			if(y>0 && mapArr[x][y-1]==v-1){
				return new Index2D(x, y-1);
			}
			if(x<w-1 && mapArr[x+1][y]==v-1){
				return new Index2D(x+1, y);
			}
			if(y<h-1 && mapArr[x][y+1]==v-1){
				return new Index2D(x, y+1);
			}
		} // if no neighbor was found, return (-2, -2) as default:
		return new Index2D(-2, -2);
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search.
	 * this function using 'allDistance' function in order to check the shortest path between two points on the map.
	 * first, check if p1 equal to p2. if so, return p2.
	 * after,make sure that p2 is point that can be reached.
	 * if it does,create new arrays of indexes,
	 * using 'waysPixel' function to reach to p2, by going from start point to end point.
	 * each step is 1 (r), according to 'allDistances' function.
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {
		Pixel2D[] ans = null;
		//check if p1==p2,return p2
		if (p1.equals(p2)){
			ans= new Pixel2D[]{p2};
		} else if (this.isInside(p1)&& this.isInside(p2)) {
			Map2D mapD= this.allDistance(p1,obsColor);
			mapD.setCyclic(this.isCyclic());
			if (mapD.getPixel(p2)<0) {
				return ans;
			}// length= p2 values according 'allDistance' function;
			int length= mapD.getPixel(p2);
			//create new array of the indexes from p1 to p2;
			ans= new Pixel2D[length+1];
			ans[length]= new Index2D(p2);
			//i goes from p2 to p1, and using 'waysPixel' to create the route between them;
			for (int i = length-1; i>=0; i--) {
				ans[i] = new Index2D(waysPixel(mapD,p2));
				p2 = new Index2D(waysPixel(mapD,p2));
			}
		}
		return ans;
	}

	@Override
	/**
	 * this function return false if X or Y values bigger than map's width or high,using the function 'get'.
	 */
	public boolean isInside(Pixel2D p) {

		boolean ans= true;
		//check if 'p' exceeds the map range
		if (p.getX()>this._map.length || p.getX()<0 ||
				p.getY()>this._map[0].length || p.getY()<0){
			ans= false;
		}
		return ans;
	}

	@Override
	/**
	 * this function check if the map is cyclic by calling to 'cyclicFlag';
	 */
	public boolean isCyclic() {
		boolean ans= false;
		if(this._cyclicFlag){
			ans= true;
		}
		return ans;
	}

	@Override
	/**
	 * this function sets the cyclic of the map by cy;
	 */
	public void setCyclic(boolean cy) {
		this._cyclicFlag = cy;
	}

	@Override
	/**
	 * creates an int[][] array, a copy of 'this._map'
	 * sets obstacles to -2, every other point to -1
	 * sets the pixel at the start point to zero
	 * r (radius) is set to 1.
	 * Iterates over array. For every pixel, if it's value is a natural number -
	 * meaning it's reachable from the start point - change its relevant neighbors to the current radius.
	 * a neighbor is relevant if it's in the array and not an obstacle.
	 * When changing the neighbors, also set 'go' to true.
	 * advance 'r' by 1.
	 * this repeats as long as 'go' is true. Every repetition starts by setting 'go' to false,
	 * and only changing it back to true when a pixel is changed.
	 * so the loop stops if a full iteration goes by with no change.
	 */
	public Map2D allDistance(Pixel2D start, int obsColor) {
		Map2D ans = null;
		// define all data about the map;
		int r=1;
		int w= this.getWidth();
		int h=this.getHeight();
		int[][] map= this.getMap();
		//define obstacle as -2 , and all other places as -1;
		for (int i = 0; i <w; i++){
			for (int j = 0; j <h; j++) {
				if(map[i][j]== obsColor){
					map[i][j]= -2;
				} else{ map[i][j]=-1;}
			}
		}
		boolean forward= true;
		//define the start points as 0;
		map[start.getX()][start.getY()]=0;
		while (forward){
			forward= false;
			for (int i = 0; i <w; i++){
				for (int j = 0; j <h; j++) {
					//get all the places that is (-1);
					if (map[i][j]==r-1){
						//check if the map is cyclic;
						if (this.isCyclic()){
							//define the frame;
							if(i==0 && map[w-1][j]==-1){
								map[w-1][j] = r; forward = true;
							}
							if(j==0 && map[i][h-1]==-1){
								map[i][h-1] = r; forward = true;
							}
							if(i==w-1 && map[0][j]==-1){
								map[0][j] = r; forward = true;
							}
							if(j==h-1 && map[i][0]==-1){
								map[i][0] = r; forward = true;
							}
						} //if the map isn't cyclic
						if(i>0 && map[i-1][j] == -1){
							map[i-1][j] = r; forward = true;
						}
						if(j>0 && map[i][j-1] == -1){
							map[i][j-1] = r; forward = true;
						}
						if(i<w-1 && map[i+1][j] == -1){
							map[i+1][j] = r; forward = true;
						}
						if(j<h-1 && map[i][j+1] == -1){
							map[i][j+1] = r; forward = true;
						}
					}
				}
			}
			r++;
		}
		ans = new Map(map);
		return ans;
	}
}
