//ID 207199282;
public class Index2D implements Pixel2D{
    private int _x, _y;
    public Index2D() {this(0,0);}
    public Index2D(int x, int y) {_x=x;_y=y;}
    public Index2D(Pixel2D t) {this(t.getX(), t.getY());}
    @Override
    public int getX() {
        return _x;
    }
    @Override
    public int getY() {
        return _y;
    }

    /**
     * this function check the distance between two pixels.
     * first, make sure the pixel isn't null.
     * if it isn't, using the distance formula: Math.sqrt(x^2 + y^2) to calculate the distance between them.
     * @param t
     * @return
     */
    public double distance2D(Pixel2D t) {
        double ans = 0;
        // make sure that t isn't null;
        if(t==null){
            throw new RuntimeException();
        }// using distance formula to calculate;
        int xlength= Math.abs(this._x - t.getX());
        int ylength= Math.abs(this._y - t.getY());
        ans=Math.sqrt(xlength*xlength + ylength*ylength);

        return ans;
    }
    @Override
    public String toString() {
        return getX()+","+getY();
    }
    @Override
    /**
     * this function checks if Object t is equal to pixel p;
     * first,make sure that t is of Index2D type.
     * if it is, the function checks equality between the X value and the Y value of the two pixels;
     */
    public boolean equals(Object t) {
        boolean ans = false;
        if(t instanceof Pixel2D){
            Pixel2D p= (Pixel2D) t;
            ans= (this.distance2D(p)==0);
        }
        return ans;
    }
}
