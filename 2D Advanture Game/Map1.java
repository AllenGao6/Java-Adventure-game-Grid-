import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Map1
{
    char [][] map;
    Location location;
    private int mapWidth;
    private int mapHeight;
    private int playerStartRow;
    private int playerStartCol;
    Location check;
    Location check2;
    Location check3;
    private Map<Location, Character> hash = new HashMap<Location, Character>();
    
    public Map1(int level)
    {
        try
        {
            Scanner in = new Scanner(new File("level"+level+".txt"));
            mapHeight = in.nextInt();
            mapWidth = in.nextInt();
            in.nextLine();
            map = new char[mapWidth][mapHeight];
            int row = 0;
            while(in.hasNextLine())
            {
                String line = in.nextLine();
                String[] squares = line.split(" ");
                
                for(int i=0;i<mapWidth;i++)
                {
                    map[row][i] = squares[i].charAt(0);
                    hash.put(new Location(row,i),map[row][i]);
                    //System.out.println(row + " " + i);
                    //System.out.println(map[row][i]);
                    
                    if(map[row][i] == 'P')
                    {   
                        playerStartRow = row;
                        playerStartCol = i;
                        System.out.println(playerStartRow + " " + playerStartCol);
                        hash.put(new Location(row, i), '_');
                    }
                }
                row++;
            }
        
        }
        catch(Exception e)
        {
        
        }

    }
    
    
    
    public char get(int row, int col)
    {   
        Iterator<Location> it = hash.keySet().iterator();
        while(it.hasNext()){
            check = it.next();
            if(check.getX() == row && check.getY() == col){
                 break;
            }
        }
        return hash.get(check);
    }
    
    public void set(int row, int col, char symbol)
    {
        Iterator<Location> it = hash.keySet().iterator();
        Location a;
        while(it.hasNext()){
            a = it.next();
            if(a.getX() == row && a.getY() == col){
                 hash.put(a,symbol);
            }
        }
    }
    
    public int getX(char a){
        Iterator<Location> it = hash.keySet().iterator();
        while(it.hasNext()){
            check2 = it.next();
            if(hash.get(check2) == a){
                 break;
            }
        }
        System.out.println(check2.getX()); 
        return check2.getX();
    }
    
    public int getY(char a){
        Iterator<Location> it = hash.keySet().iterator();
        while(it.hasNext()){
            check3 = it.next();
            if(hash.get(check3) == a){
                 break;
            }
        }
        System.out.println(check3.getY());
        return check3.getY();
    }
    
    public void move(int x, int y, char symbol){
        //System.out.println(x + "_____" + y);
        Iterator<Location> it = hash.keySet().iterator();
        while(it.hasNext()){
            check = it.next();
            if(check.getX() == x && check.getY() == y){
                 break;
            }
        }
        hash.remove(check);
        hash.put(check,'_');
        
        it = hash.keySet().iterator();
        Location a;
        while(it.hasNext()){
            a = it.next();
            if(a.getX() == x+1 && a.getY() == y+1){
                 hash.put(a,symbol);
            }
        }
        
        
        
        //System.out.println("____________________");

    }
    public void moveback(int x, int y, char symbol){
        System.out.println(x + "_____" + y);
        hash.put(new Location(x,y),'_');
        hash.put(new Location(x-2,y-2),symbol);
        System.out.println("____________________");
        
    }
    public int getHeight()
    {
        return mapHeight;
    }
    
    public int getWidth()
    {
        return mapWidth;
    }
    
    public int getPlayerStartRow()
    {
        return playerStartRow;
    }
    public int getPlayerStartCol()
    {
        return playerStartCol;
    }    
}