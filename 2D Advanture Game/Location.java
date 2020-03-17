public class Location{
    private int x;
    private int y;
    
    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public String toString(){
        return "X : " + x + " Y " + y;
    }   
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean equals(Object o){
        Location a = (Location) o;
        if(x == a.getX() && y == a.getY()){
            return true;
        }
        else
        return false;
    }
    
    public int hashCode(){
        int hashCode = 0;
        hashCode = hashCode * 31 + x + y; 
        return hashCode;
    }
    /*@Override
    public int compareTo(Item o){
        if(x == o.getX() && y == o.getY()){
            return 0;
        }
        else if(!x == o.getX()){
            return y - x;
        }
        else{
            return x == o.getX();
        }
    
    }*/

    
    
}