import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.*;
import sun.audio.*;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.TreeMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.GraphicsEnvironment;
import java.util.Stack;


public class Screen extends JPanel implements ActionListener, KeyListener{
    private ArrayList<String> itemlist = new ArrayList<String>();
    private Map<String,Integer> item = new TreeMap<String,Integer>();
    
    private Clip clip1;
    private Clip clip;
    Map1[] maps;
    int i;
    private String dialog = "";
    private String dialogColor = "blue";

    private String gameplayState;
    private int blockSize = 50; //every block is 20x20
    private int displayWidth = 15; //measured in blocks
    private int displayHeight = 13;
    private int playerRow;
    private int playerCol;
    private BufferedImage Water;
    private BufferedImage Lava;
    private BufferedImage Knight;
    private BufferedImage Key;
    private BufferedImage Herb;
    private BufferedImage Mountain;
    private BufferedImage castle;
    private BufferedImage Wall;
    private BufferedImage Smashwall;
    private BufferedImage Grass;
    private BufferedImage Person;
    private BufferedImage BossDoor;
    private BufferedImage Passdoor1;
    private BufferedImage dragon;
    private BufferedImage Paper;
    private BufferedImage BlackKey;
    private BufferedImage Lock;
    private BufferedImage superSword;
    private BufferedImage Shield;
    private BufferedImage Armor;
    private BufferedImage Apple;
    private BufferedImage Dragon1;
    private BufferedImage Passdoor2;
    private BufferedImage End;

    private int hasKey = 0;
    private int hasHerb = 0;
    private int hasBlackkey = 0;
    int count;
    private JButton Heal;
    private JButton exitButton;
    private JButton restart;
    
    private boolean hasSword = false;
    private boolean hasShield = false;
    private boolean hasArmor = false;
    
    int dragonX;
    int dragonY;
    
    Font font1;
    
    
    Level lev;
    
    private Stack<Integer> blood = new Stack<Integer>();

    
    public Screen(){
        
        setLayout(null);
        this.setFocusable(true);
        lev = new Level();

        maps = new Map1[2];        
        //level = lev.getlevel();
        maps[0] = new Map1(1);
        maps[1] = new Map1(2);
        playerRow = maps[0].getPlayerStartRow();
        playerCol = maps[0].getPlayerStartCol();
        
        dragonX = maps[0].getX('k');
        dragonY = maps[0].getY('k');
    
        gameplayState = "playing";
        //this.animate();
        count = 0;
        try{
            Knight = ImageIO.read(new File("Knight.png"));
            Key = ImageIO.read(new File("Key.png"));
            Herb = ImageIO.read(new File("Herb.jpg"));
            Water = ImageIO.read(new File("Water.jpg"));
            Lava = ImageIO.read(new File("Lava.jpg"));
            castle = ImageIO.read(new File("Castle.png"));
            Smashwall = ImageIO.read(new File("Smashwall.jpg"));
            Wall = ImageIO.read(new File("Wall.jpg"));
            Mountain = ImageIO.read(new File("Mountain.jpg"));
            Grass = ImageIO.read(new File("Grass.jpg"));
            Person = ImageIO.read(new File("Person.png"));
            BossDoor = ImageIO.read(new File("BossDoor.jpg"));
            Passdoor1 = ImageIO.read(new File("Passdoor1.png"));
            dragon = ImageIO.read(new File("dragon.png"));
            Paper = ImageIO.read(new File("Paper.png"));
            BlackKey = ImageIO.read(new File("Key4.png"));
            Lock = ImageIO.read(new File("Lock.jpg"));
            superSword = ImageIO.read(new File("Sword1.png"));
            Shield = ImageIO.read(new File("Shield.png"));
            Armor = ImageIO.read(new File("Armor.png"));
            Apple = ImageIO.read(new File("Apple.png"));
            Dragon1 = ImageIO.read(new File("Dragon1.png"));
            Passdoor2 = ImageIO.read(new File("Passdoor2.png"));
            End = ImageIO.read(new File("End.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for(int i = 0; i<20; i++){
            blood.push(i);
        }   
        
            Heal = new JButton("Herb");
		    Heal.setBounds(750,20,55,30);
		    Heal.addActionListener(this);
		    this.add(Heal);
            Heal.setVisible(false);
            
            
		    exitButton = new JButton("Quit");
		    exitButton.setBounds(750,65,55,30);
		    exitButton.addActionListener(this);
		    this.add(exitButton);
		    
		    restart = new JButton("Restart");
		    restart.setBounds(370,350,70,35);
		    restart.addActionListener(this);
		    this.add(restart);
		    restart.setVisible(false);
		    
		    
		    this.addKeyListener(this);
    }
    
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
	    g.fillRect(740,0,800,600);	    
	    
        
	        if(gameplayState.equals("playing"))
            {
                //this.playsound();
                for(int row = 0; row<displayHeight; row++)
            {
                for(int col=0; col<displayWidth; col++)
                {
                    int mapRow = row+playerRow-displayHeight/2;
                    int mapCol = col+playerCol-displayWidth/2;
                    if(mapRow < 0 || mapCol < 0 || mapRow >= maps[lev.getLevel()].getHeight() || mapCol >= maps[lev.getLevel()].getWidth()){
                            if(lev.getLevel() == 0)
                                g.drawImage(Water, col*blockSize, row*blockSize, null);
                            else if(lev.getLevel() == 1)
                                g.drawImage(Lava, col*blockSize, row*blockSize, null);
                            else{
                                g.drawImage(Lava, col*blockSize, row*blockSize, null);
                            }
                        }
                        else if(maps[lev.getLevel()].get(mapRow, mapCol)=='#') //# is wall
                            g.drawImage(Mountain, col*blockSize, row*blockSize, null);
                        else if(maps[lev.getLevel()].get(mapRow, mapCol) == '@') //@ is castle wall
                            g.drawImage(castle, col*blockSize, row*blockSize, null);
                        else
                        {
                            //not mountain or water, then ALWAYS draw grass, then whatever else
                            g.drawImage(Grass, col*blockSize, row*blockSize, null);
                            
                            if(maps[lev.getLevel()].get(mapRow, mapCol) == 'K') //# is wall
                                g.drawImage(Key, col*blockSize+blockSize/2, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol)=='W') //# is wall
                                g.drawImage(Wall, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) =='w') //# is wall
                                g.drawImage(Smashwall, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'H') //# is wall
                                g.drawImage(Herb, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'I') //# is wall
                                g.drawImage(Person, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'L') //# is wall
                                g.drawImage(BossDoor, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'D') //# is wall
                                g.drawImage(Passdoor1, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'k'){ //# is wall
                                g.drawImage(dragon, col*blockSize, row*blockSize, null);
                                System.out.println(mapRow+","+ mapCol);
                                System.out.println(dragonX+","+ dragonY);
                                //System.out.println(maps[lev.getLevel()].get(5, 3) + "  aaa");
                                //System.out.println(maps[lev.getLevel()].get(6, 4) + "  aaa");
                                //System.out.println(maps[lev.getLevel()].get(7, 5) + "  aaa");
                                //System.out.println(maps[lev.getLevel()].get(8, 5) + "  aaa");  
                            }
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'g') //# is wall
                                g.drawImage(Paper, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'i') //# is wall
                                g.drawImage(BlackKey, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'u') //# is wall
                                g.drawImage(Lock, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'Z') //# is wall
                                g.drawImage(superSword, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 's') //# is wall
                                g.drawImage(Shield, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'A') //# is wall
                                g.drawImage(Armor, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'h') //# is wall
                                g.drawImage(Apple, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'q') //# is wall
                                g.drawImage(Dragon1, col*blockSize, row*blockSize, null);
                            else if(maps[lev.getLevel()].get(mapRow, mapCol) == 'Q') //# is wall
                                g.drawImage(Passdoor2, col*blockSize, row*blockSize, null);
                        g.drawImage(Knight, displayWidth/2*blockSize, displayHeight/2*blockSize, null);

	                }
	            }
	        }
	            
	    if(hasSword)
            g.drawImage(superSword, displayWidth/2*blockSize, displayHeight/2*blockSize, null);
        if(hasArmor)
            g.drawImage(Armor, displayWidth/2*blockSize+10, displayHeight/2*blockSize+15, null);
        if(hasShield)
            g.drawImage(Shield, displayWidth/2*blockSize, displayHeight/2*blockSize, null);
                
	    if(lev.getLevel() == 0)
	        g.drawString("Level : 1" ,150 , 20);
	    if(lev.getLevel() == 1)
	        g.drawString("Level : 2" ,150 , 20);
	    
	    g.setColor(Color.red);
	    
	    for(int i = 0; i < blood.size(); i++){
	        g.fillRect(755,600-20*i,40,15);
	    }
	    
	    
	    item.clear();
	    for(String c : itemlist){
            if(item.containsKey(c)){
                item.put(c,item.get(c)+1);
            }
            else{
                item.put(c,1);
            }
        }
        
        if(hasHerb != 0)
            Heal.setVisible(true);
        else
            Heal.setVisible(false); 
        
        Iterator<String> it;
        it = item.keySet().iterator();
        int i = 30;
        while(it.hasNext()){
            String c = it.next();
            g.setColor(Color.black);
            g.drawString(c + " --- " +item.get(c),50, 10 + i);
            i = i + 30;
        }	
        
        
	    if(dialog.length() != 0)
            { 
                font1 = new Font("Arial",Font.PLAIN, 20);
                g.setColor(Color.black);
                g.fillRect(100, 450, 800,600);
                g.setColor(Color.white);
                g.fillRect(130, 480, 800,600);
                g.drawLine(100,450,130,480);
                if(dialogColor.equals("green"))
                {
                    g.setColor(Color.green);
                }
                else if(dialogColor.equals("blue"))
                {
                    g.setColor(Color.blue);
                }
                else if(dialogColor.equals("red"))
                {
                    g.setColor(Color.red);
                }
                else if(dialogColor.equals("yellow"))
                {
                    g.setColor(Color.yellow);
                }
                //drawText(String text, Font font, Graphics g, int width, int startX, int startY)
                DrawString.drawText(dialog, font1, g, 800, 150, 500);
                dialog = "";
                
            }
	    }
	    
	    if(gameplayState.equals("Ending")){
	        g.drawImage(End, 0, 0, null);
	        g.drawString("Game Over" , 380, 200);
	    }
	    
	    if(gameplayState.equals("Ending")){
		        restart.setVisible(true);
		    }
		    else{
		        restart.setVisible(false);
		    }
	}
    
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
    
    public Dimension getPreferredSize() 
    {
        //Sets the size of the panel
        return new Dimension(800,600);
    }
    
    public void playsound()
	{
	    if(lev.getLevel() == 0 && gameplayState.equals("playing"))
        {
            try
            {
                URL level0 = this.getClass().getClassLoader().getResource("Level2.wav");
                clip1 = AudioSystem.getClip();
                clip1.open(AudioSystem.getAudioInputStream(level0));
                clip1.start();
            }
                catch (Exception exc)
            {
                exc.printStackTrace(System.out);
            }
        }
        if(lev.getLevel() == 1 && gameplayState.equals("playing"))
        {
            clip1.stop();
            try
            {
                URL level2 = this.getClass().getClassLoader().getResource("Level0.wav");
                clip1 = AudioSystem.getClip();
                clip1.open(AudioSystem.getAudioInputStream(level2));
                clip1.start();
            }
                catch (Exception exc)
            {
                exc.printStackTrace(System.out);
            }
        }
        
        if(gameplayState.equals("Ending"))
        {
            clip1.stop();
            try
            {
                URL level4 = this.getClass().getClassLoader().getResource("Finish.wav");
                clip1 = AudioSystem.getClip();
                clip1.open(AudioSystem.getAudioInputStream(level4));
                clip1.start();
            }
                catch (Exception exc)
            {
                exc.printStackTrace(System.out);
            }
        }
	}
	
	public void actionPerformed(ActionEvent e){
	   if( e.getSource() == Heal )
        {
            blood.push(1);
            blood.push(1);
            hasHerb--;
            itemlist.remove("Herb");
		}
		else if( e.getSource() == exitButton )
        {
            System.exit(0);
		}
		else if( e.getSource() == restart )
        {
            gameplayState = "playing";
            lev.setLevel(0);
	        itemlist.clear();
	        item.clear();
	        playerRow = maps[lev.getLevel()].getPlayerStartRow();
            playerCol = maps[lev.getLevel()].getPlayerStartCol();
            blood.clear();
            for(int i = 0; i<20; i++){
                blood.push(i);
            }
            maps[lev.getLevel()].set(0, 1, 'W');
            maps[lev.getLevel()].set(2, 2, 'K');
            maps[lev.getLevel()].set(1, 12, 'K');
            maps[lev.getLevel()].set(1, 7, 'H');
            maps[lev.getLevel()].set(5, 3, 'k');
            maps[lev.getLevel()].set(5, 10, 'H');
            maps[lev.getLevel()].set(14, 2, 'L');
            maps[lev.getLevel()].set(8, 9, 'K');
            hasKey = 0;
            hasHerb = 0;
            hasBlackkey = 0;
            hasSword = false;
            hasShield = false;
            hasArmor = false;
            maps[1].set(1, 0, 'A');
            maps[1].set(9, 0, 'h');
            maps[1].set(14, 0, 'W');
            maps[1].set(15, 0, 'W');
            maps[1].set(16, 0, 'W');
            maps[1].set(14, 1, 'W');
            maps[1].set(16, 3, 'W');
            maps[1].set(18, 0, 'u');
            maps[1].set(28, 1, 'h');
            maps[1].set(27, 1, 'Z');
            maps[1].set(27, 3, 'W');
            maps[1].set(27, 8, 'W');
            maps[1].set(29, 29, 'i');
            maps[1].set(18, 18, 'q');
            maps[1].set(21, 18, 'Q');
            maps[1].set(19, 26, 'i');
            maps[1].set(20, 28, 's');
            maps[1].set(1, 29, 'i');
            maps[1].set(1, 13, 'i');
            maps[1].set(2, 19, 'i');
            maps[1].set(4, 14, 'W');
            maps[1].set(5, 14, 'W');
            maps[1].set(4, 18, 'W');
            maps[1].set(5, 18, 'W');
            maps[1].set(6, 21, 'W');
            maps[1].set(3, 26, 'h');
            maps[1].set(8, 25, 'W');
            maps[1].set(8, 26, 'W');
            //this.playsound();
		}
		repaint();
	}
	
	public void keyTyped(KeyEvent e) {
        System.out.println("Typed: " + e.getKeyChar());
    }
    
    public void keyPressed(KeyEvent e) {

                
        if(blood.size() == 1){
	        lev.setLevel(0);
	        itemlist.clear();
	        item.clear();
	        playerRow = maps[lev.getLevel()].getPlayerStartRow();
            playerCol = maps[lev.getLevel()].getPlayerStartCol();
            for(int i = 0; i<20; i++){
                blood.push(i);
            }
            maps[lev.getLevel()].set(0, 1, 'W');
            maps[lev.getLevel()].set(2, 2, 'K');
            maps[lev.getLevel()].set(1, 12, 'K');
            maps[lev.getLevel()].set(1, 7, 'H');
            maps[lev.getLevel()].set(5, 3, 'k');
            maps[lev.getLevel()].set(5, 10, 'H');
            maps[lev.getLevel()].set(14, 2, 'L');
            maps[lev.getLevel()].set(8, 9, 'K');
            hasKey = 0;
            hasHerb = 0;
            hasBlackkey = 0;
            hasSword = false;
            hasShield = false;
            hasArmor = false;
            maps[1].set(1, 0, 'A');
            maps[1].set(9, 0, 'h');
            maps[1].set(14, 0, 'W');
            maps[1].set(15, 0, 'W');
            maps[1].set(16, 0, 'W');
            maps[1].set(14, 1, 'W');
            //maps[1].set(15, 2, 'W');
            maps[1].set(16, 3, 'W');
            maps[1].set(18, 0, 'u');
            maps[1].set(28, 1, 'h');
            maps[1].set(27, 1, 'Z');
            maps[1].set(27, 3, 'W');
            maps[1].set(27, 8, 'W');
            maps[1].set(29, 29, 'i');
            maps[1].set(18, 18, 'q');
            maps[1].set(21, 18, 'Q');
            maps[1].set(19, 26, 'i');
            maps[1].set(20, 28, 's');
            maps[1].set(1, 29, 'i');
            maps[1].set(1, 13, 'i');
            maps[1].set(2, 19, 'i');
            maps[1].set(4, 14, 'W');
            maps[1].set(5, 14, 'W');
            maps[1].set(4, 18, 'W');
            maps[1].set(5, 18, 'W');
            maps[1].set(6, 21, 'W');
            maps[1].set(3, 26, 'h');
            maps[1].set(8, 25, 'W');
            maps[1].set(8, 26, 'W');

        }
        
        System.out.println("Pressed: " + e.getKeyChar());
        if(e.getKeyChar() == 's' && playerRow < maps[lev.getLevel()].getHeight()-1){
            if(maps[lev.getLevel()].get(playerRow+1, playerCol) == '#' || maps[lev.getLevel()].get(playerRow+1, playerCol) == '@'){
                blood.pop();
                playerRow = maps[lev.getLevel()].getPlayerStartRow();
                playerCol = maps[lev.getLevel()].getPlayerStartCol();
                try
                {
                    URL newGameSound = this.getClass().getClassLoader().getResource("Pain.wav");
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(newGameSound));
                    clip.start();
                 }
                catch (Exception exc)
                {
                    exc.printStackTrace(System.out);
                }
            }
            else{
                playerRow++;
                System.out.println(playerRow);
            }
        }
        else if(e.getKeyChar() == 'w' && playerRow > 0){
            if(maps[lev.getLevel()].get(playerRow-1, playerCol) == '#' || maps[lev.getLevel()].get(playerRow-1, playerCol) == '@'){
                blood.pop();
                playerRow = maps[lev.getLevel()].getPlayerStartRow();
                playerCol = maps[lev.getLevel()].getPlayerStartCol();
                try
                {
                    URL newGameSound = this.getClass().getClassLoader().getResource("Pain.wav");
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(newGameSound));
                    clip.start();
                 }
                catch (Exception exc)
                {
                    exc.printStackTrace(System.out);
                }
            }
            else
                playerRow--;
        }
        else if(e.getKeyChar() == 'a' && playerCol > 0){
            if(maps[lev.getLevel()].get(playerRow, playerCol-1) == '#' || maps[lev.getLevel()].get(playerRow, playerCol-1) == '@'){
                blood.pop();
                playerRow = maps[lev.getLevel()].getPlayerStartRow();
                playerCol = maps[lev.getLevel()].getPlayerStartCol();
                try
                {
                    URL newGameSound = this.getClass().getClassLoader().getResource("Pain.wav");
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(newGameSound));
                    clip.start();
                 }
                catch (Exception exc)
                {
                    exc.printStackTrace(System.out);
                }
            }
            else
                playerCol--;
        }
        else if(e.getKeyChar() == 'd' && playerCol < maps[lev.getLevel()].getHeight()-1){
            if( maps[lev.getLevel()].get(playerRow, playerCol+1) == '#' || maps[lev.getLevel()].get(playerRow, playerCol+1) == '@'){
                blood.pop();
                playerRow = maps[lev.getLevel()].getPlayerStartRow();
                playerCol = maps[lev.getLevel()].getPlayerStartCol();
                try
                {
                    URL newGameSound = this.getClass().getClassLoader().getResource("Pain.wav");
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(newGameSound));
                    clip.start();
                 }
                catch (Exception exc)
                {
                    exc.printStackTrace(System.out);
                }
            }
            else
                playerCol++;
        }
        else if(e.getKeyChar() == 'p' && gameplayState.equals("playing"))
        {
            //this.playsound();
            if(lev.getLevel() == 0)
            {
                lev.addLevel();
                itemlist.clear();
                itemlist.add("Key");
                itemlist.add("Key");
                itemlist.add("Key");
                itemlist.add("Herb");
                itemlist.add("Herb");
                item.clear();
	            hasHerb = 2;     
            }
            else if(lev.getLevel() == 1)
            {
                itemlist.clear();
                item.clear();
                itemlist.add("Key");
                itemlist.add("Key");
                itemlist.add("Key");
                itemlist.add("Herb");
                itemlist.add("Herb");
	            hasHerb = 2;
                itemlist.add("Black Key");
                itemlist.add("Black Key");
                itemlist.add("Black Key");
                itemlist.add("Black Key");
                itemlist.add("Shield");
                itemlist.add("Armor");
                itemlist.add("Sword");
                hasSword = true;
                hasShield = true;
                hasArmor = true;
                hasBlackkey = 4;
                //this.playsound();
            }
            playerRow = maps[lev.getLevel()].getPlayerStartRow();
            playerCol = maps[lev.getLevel()].getPlayerStartCol();
            repaint();
        }            
        boolean backup = false;
        
        if(maps[lev.getLevel()].get(playerRow, playerCol) == 'I')
        {
            //hasKey = true;
            backup = true;
            dialogColor = "green";
            dialog = "Hey, young man. You need to collect three keys and two herbs to go to the next level, Good luck!";
            //maps[lev.getLevel()].set(playerRow, playerCol, '_');
        }
        if(maps[lev.getLevel()].get(playerRow, playerCol) == 'K')
        {
            //hasKey = true;
            dialogColor = "blue";
            dialog = "You found a key!";
            itemlist.add("Blue Key");
            hasKey++;
            try
            {
                URL newGameSound = this.getClass().getClassLoader().getResource("Key.wav");
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(newGameSound));
                clip.start();
            }
            catch (Exception exc)
            {
                exc.printStackTrace(System.out);
            }
            maps[lev.getLevel()].set(playerRow, playerCol, '_');
        }
        
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'H')
        {
            hasHerb++;
            dialogColor = "blue";
            dialog = "You found a herb!";
            itemlist.add("Herb");
            maps[lev.getLevel()].set(playerRow, playerCol, '_');
        }
        
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'g')
        {
            itemlist.add("Note");
            dialogColor = "red";
            dialog = "There is a dragon at the exit, you need to collect a shield, a sword, and an armor in order to defeat the guard. DON'T TRY TO DEFEAT THAT DRAGON WITHOUT THESE THREE ITEMS!!!";
            maps[lev.getLevel()].set(playerRow, playerCol, '_');
        }
        
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'W')
        {
                dialogColor = "red";
                dialog = "CRUNCH!";
                blood.pop();
                maps[lev.getLevel()].set(playerRow, playerCol, 'w');
                
                try
                {
                    URL newGameSound = this.getClass().getClassLoader().getResource("StoneBreaking.wav");
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(newGameSound));
                    clip.start();
                 }
                catch (Exception exc)
                {
                    exc.printStackTrace(System.out);
                }
            
        }
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'h')
        {
                dialogColor = "green";
                dialog = "Gain 2 bars of health!!";
                blood.push(1);
                blood.push(1);
                maps[lev.getLevel()].set(playerRow, playerCol, '_');
            
        }
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'L')
        {
            if(hasKey == 3){
                dialogColor = "blue";
                dialog = "You opened a lock!";
                try
                {
                    URL newGameSound = this.getClass().getClassLoader().getResource("OpenLock.wav");
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(newGameSound));
                    clip.start();
                 }
                catch (Exception exc)
                {
                    exc.printStackTrace(System.out);
                }
                maps[lev.getLevel()].set(playerRow, playerCol, '_');
            }
            else{
                backup = true;
                dialogColor = "red";
                dialog = "You need three blue keys to open this lock!";
            }
        }
        
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'D')
        {
                lev.addLevel();
                //this.playsound();
                playerRow = maps[lev.getLevel()].getPlayerStartRow();
                playerCol = maps[lev.getLevel()].getPlayerStartCol();
                dialog = "Next Level!";
                dialogColor = "blue";
        }
        
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'k')
        {
                blood.pop();
                blood.pop();
                playerRow = maps[lev.getLevel()].getPlayerStartRow();
                playerCol = maps[lev.getLevel()].getPlayerStartCol();
                try
                {
                    URL newGameSound = this.getClass().getClassLoader().getResource("Pain.wav");
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(newGameSound));
                    clip.start();
                 }
                catch (Exception exc)
                {
                    exc.printStackTrace(System.out);
                }
        }
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'i')
        {
                itemlist.add("Black Key");
                hasBlackkey++;
                dialog = "You found a black key";
                dialogColor = "green";
            try
            {
                URL newGameSound = this.getClass().getClassLoader().getResource("Key.wav");
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(newGameSound));
                clip.start();
            }
            catch (Exception exc)
            {
                exc.printStackTrace(System.out);
            }
                maps[lev.getLevel()].set(playerRow, playerCol, '_');
                
        }
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'u')
        {
                if(hasBlackkey == 4){
                    dialog = "You opened a lock!";
                    dialogColor = "blue";
                    try
                    {
                        URL newGameSound = this.getClass().getClassLoader().getResource("OpenLock.wav");
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(newGameSound));
                        clip.start();
                    }
                    catch (Exception exc)
                    {
                        exc.printStackTrace(System.out);
                    }
                    maps[lev.getLevel()].set(playerRow, playerCol, '_');
                }
                else{
                    dialog = "You need four black keys to open this lock!!";
                    dialogColor = "red";
                    backup = true;
                }
                
        }
         else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'Z')
        {
            hasSword = true;
            dialogColor = "red";
            dialog = "You found the [SWORD]!";
            itemlist.add("Sword");
            maps[lev.getLevel()].set(playerRow, playerCol, '_');
        }
         else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'A')
        {
            hasArmor = true;
            dialogColor = "red";
            dialog = "You found the [ARMOR]!";
            itemlist.add("Armor");
            maps[lev.getLevel()].set(playerRow, playerCol, '_');
        }
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 's')
        {
            hasShield = true;
            dialogColor = "red";
            dialog = "You found the [SHIELD]!";
            itemlist.add("Shield");
            maps[lev.getLevel()].set(playerRow, playerCol, '_');
        }
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'q')
        {
            if(hasShield && hasArmor && hasSword){
                dialogColor = "red";
                dialog = "You defeated the guard dragon!";
                maps[lev.getLevel()].set(playerRow, playerCol, '_');
            }
            else{
                dialogColor = "red";
                dialog = "You are not strong enough to defeat this dragon";
                if(blood.size() <6){
                    blood.clear();
                }
                else{
                    for(int i = 0; i<5; i++){
                        blood.pop();
                    }
                }
                backup = true;
                try
                {
                    URL newGameSound = this.getClass().getClassLoader().getResource("Pain.wav");
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(newGameSound));
                    clip.start();
                 }
                catch (Exception exc)
                {
                    exc.printStackTrace(System.out);
                }
            }
        }
        else if(maps[lev.getLevel()].get(playerRow, playerCol) == 'Q')
        {
            gameplayState = "Ending";
        }
        if(backup)
        {
            if(e.getKeyChar() == 's')
                playerRow--;
            else if(e.getKeyChar() == 'w')
                playerRow++;
            else if(e.getKeyChar() == 'a')
                playerCol++;
            else if(e.getKeyChar() == 'd')
                playerCol--;
        }
        repaint();
        System.out.println("Pressed: " + e.getKeyChar() + " on " + maps[lev.getLevel()].get(playerRow, playerCol));
    }
    
    public void keyReleased(KeyEvent e){}
    
    /*public void move(){
        System.out.println(dragonX + " " + dragonY);
        maps[0].set(dragonX, dragonY, '_');
        dragonX++;
        dragonY++;
        maps[0].set(dragonX, dragonY, 'k');
        System.out.println(maps[0].get(dragonX, dragonY));
        System.out.println(dragonX + "____" + dragonY);
    }
    public void moveback(){
        maps[lev.getLevel()].set(dragonX, dragonY, '_');
        dragonX = 5;
        dragonY = 3;
        maps[lev.getLevel()].set(dragonX, dragonY, 'k');        
    }*/
    
    public void animate()
    {
        
        //maps[lev.getLevel()].set(5, 3, '_');
        //maps[lev.getLevel()].set(5, 4, 'k');
        //System.out.println(dragonX + " " + dragonY);
        while (true)
        {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            moveIt();
            //System.out.println(dragonX + " " + dragonY);
                
            repaint();
        }
    
    }
    public void moveIt(){
            dragonX++;
            dragonY++;
            if (dragonX >= 20) {
				dragonX = 17;
			}
			if (dragonY >= 10) {
				dragonY = 6;
			}
	    maps[lev.getLevel()].set(dragonX-1, dragonY-1, '_');
        maps[lev.getLevel()].set(dragonX, dragonY, 'k');
        System.out.println(dragonX + " :::" + dragonY);
    }
}


















