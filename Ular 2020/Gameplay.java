import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.*;
import java.lang.Object;
import java.io.*;
   
public class Gameplay extends JPanel implements KeyListener, ActionListener{

   private int[] snakexlength = new int[750];
   private int[] snakeylength = new int[750];
   
   private boolean left = false; 
   private boolean right = true;
   private boolean up = false;
   private boolean down = false;
   
   private ImageIcon rightmouth;
   private ImageIcon upmouth;
   private ImageIcon downmouth;
   private ImageIcon leftmouth;
   private ImageIcon snakeimage;
   private ImageIcon titleImage;
   private ImageIcon enemyimage;
   private Random random = new Random();
   private Timer timer;
   
   private int lengthofsnake = 3;
   private int delay = 100;
   private int moves = 0;
   private int xpos = random.nextInt(34);
   private int ypos = random.nextInt(23);
   private int counter = 0;
   
   private JButton startButton;
   private JButton continueButton;
   private JPanel menu;
   
   private int[] foodxpos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
   private int[] foodypos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625}; 
   private int score = 0;
   private int highscore = 0;
   private int z = 1;
   
   public Gameplay()
   {
      addKeyListener(this);
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
      timer = new Timer(delay, this);
      timer.start();  
      try{
         FileInputStream saveFile = new FileInputStream("saveFile.sav");
         ObjectInputStream save = new ObjectInputStream(saveFile);
         highscore = (Integer) save.readObject();
         save.close();
      }
      catch(Exception exc){
         exc.printStackTrace();
      }  
   }
   
   public void paint(Graphics g)
   { 
      //snake default position
      if(moves == 0)
      {
         snakexlength[2] = 50;
         snakexlength[1] = 75;
         snakexlength[0] = 100;
          
         snakeylength[2] = 100;
         snakeylength[1] = 100;
         snakeylength[0] = 100;
      }
   
      //title image border
      g.setColor(Color.WHITE);
      g.drawRect(24, 10, 851, 55);
       
      //title image
      titleImage = new ImageIcon("snaketitle.jpg");   
      titleImage.paintIcon(this, g, 25, 11);
       
      //Border for gameplay
      g.setColor(Color.WHITE);
      g.drawRect(24, 74, 851, 576);
       
      //draw background
      g.setColor(Color.black);
      g.fillRect(25, 75, 850, 575);
      
      //score
      g.setColor(Color.white);
      g.setFont(new Font("arial", Font.PLAIN, 14));
      g.drawString("Score: "+score, 780, 30);
      
      //length
      g.setColor(Color.white);
      g.setFont(new Font("arial", Font.PLAIN, 14));
      g.drawString("Highscore: "+highscore, 780, 50);
      
      //draw snake
      rightmouth = new ImageIcon("rightmouth.png");
      rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
      for(int i=0; i<lengthofsnake; i++)
      {
         if(i==0 && right)
         {
            rightmouth = new ImageIcon("rightmouth.png");
            rightmouth.paintIcon(this, g, snakexlength[i], snakeylength[i]);
         }
         if(i==0 && left)
         {
            leftmouth = new ImageIcon("leftmouth.png");
            leftmouth.paintIcon(this, g, snakexlength[i], snakeylength[i]);
         }
         if(i==0 && up)
         {
            upmouth = new ImageIcon("upmouth.png");
            upmouth.paintIcon(this, g, snakexlength[i], snakeylength[i]);
         }
         if(i==0 && down)
         {
            downmouth = new ImageIcon("downmouth.png");
            downmouth.paintIcon(this, g, snakexlength[i], snakeylength[i]);
         }
         if(i!=0)
         {
            snakeimage = new ImageIcon("snakeimage.png");
            snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);
         }   
      }
      
      //draw snake food
      enemyimage = new ImageIcon("enemy.png");
      enemyimage.paintIcon(this, g, foodxpos[xpos], foodypos[ypos]);
      if((foodxpos[xpos] == snakexlength[0]) && (foodypos[ypos] == snakeylength[0]))
      {
         score = score + 5;
         lengthofsnake++;
         xpos = random.nextInt(34);
         ypos = random.nextInt(23);
      }
      
      //Start manu
      if(moves == 0)
      {
         g.setColor(Color.WHITE);
         g.drawRect(239, 199, 446, 176);   
         g.setColor(Color.DARK_GRAY);
         g.fillRect(240, 200, 445, 175);
      
         g.setColor(Color.white);
         g.setFont(new Font("Tahoma", Font.PLAIN, 23));
         g.drawString("Feed Esther's Snake and make it grow!", 265, 255);
          
         g.setFont(new Font("arial", Font.BOLD, 15));
         g.drawString("Press any arrow key to move", 350, 320);
         
         g.setFont(new Font("arial", Font.BOLD, 15));
         g.drawString("Press 'R' to reset highscore", 350, 345);
         
         
         if(z == 0)
         {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(255,220,420,50);
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("High score is Reset!", 355, 255);
         }  
            
      }
      
      //gameover screen
      for(int i = 1; i<lengthofsnake; i ++)
      {
         if((snakexlength[i] == snakexlength[0]) && (snakeylength[i] == snakeylength[0]))
         {
            right = false;
            left = false;
            up = false;
            down = false;
            
            g.setColor(Color.WHITE);
            g.drawRect(284, 199, 306, 176);   
            g.setColor(Color.DARK_GRAY);
            g.fillRect(285, 200, 305, 175);
            
            g.setColor(Color.RED);
            g.setFont(new Font("arial", Font.BOLD, 50));
            g.drawString("Game Over", 305, 275);
             
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.PLAIN, 20));
            g.drawString("Press SPACE to restart", 330, 325);
            counter++;
            
         }
      }  
      g.dispose(); 
   }
   
   @Override
   public void actionPerformed(ActionEvent e){
      timer.start();
      
      if(right)
      {
         for(int r = lengthofsnake-1; r>=0; r--)
         {
            snakeylength[r+1] = snakeylength[r];
         }
         for(int r = lengthofsnake; r>=0; r--)
         {
            if(r==0)
            {
               snakexlength[r] = snakexlength[r]+25;
            }
            else
            {
               snakexlength[r] = snakexlength[r-1];
            }
            if(snakexlength[r] > 850)
            {
               snakexlength[r] = 25;
            }
         }
         repaint();
      }
      if(left)
      {
         for(int r = lengthofsnake-1; r>=0; r--)
         {
            snakeylength[r+1] = snakeylength[r];
         }
         for(int r = lengthofsnake; r>=0; r--)
         {
            if(r==0)
            {
               snakexlength[r] = snakexlength[r]-25;
            }
            else
            {
               snakexlength[r] = snakexlength[r-1];
            }
            if(snakexlength[r] < 25)
            {
               snakexlength[r] = 850;
            }
         }
         repaint();
      }
   
      if(up)
      {
         for(int r = lengthofsnake-1; r>=0; r--)
         {
            snakexlength[r+1] = snakexlength[r];
         }
         for(int r = lengthofsnake; r>=0; r--)
         {
            if(r==0)
            {
               snakeylength[r] = snakeylength[r]-25;
            }
            else
            {
               snakeylength[r] = snakeylength[r-1];
            }
            if(snakeylength[r] < 75)
            {
               snakeylength[r] = 625;
            }
         }
         repaint();
      }
   
      
      if(down)
      {
         for(int r = lengthofsnake-1; r>=0; r--)
         {
            snakexlength[r+1] = snakexlength[r];
         }
         for(int r = lengthofsnake; r>=0; r--)
         {
            if(r==0)
            {
               snakeylength[r] = snakeylength[r]+25;
            }
            else
            {
               snakeylength[r] = snakeylength[r-1];
            }
            if(snakeylength[r] > 625)
            {
               snakeylength[r] = 75;
            }
         }
         repaint();
      }
   }
   
   @Override
   public void keyTyped(KeyEvent e) {
   }
   
   @Override
   public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
      
      if(key == KeyEvent.VK_SPACE)
      {  
         if(score > highscore){
            try{
               FileOutputStream saveFile = new FileOutputStream("saveFile.sav");
               ObjectOutputStream save = new ObjectOutputStream(saveFile);
               save.writeObject(score);
               save.close();
            }
            catch(Exception exc){
               exc.printStackTrace();
            }
         }   
         try{
            FileInputStream saveFile = new FileInputStream("saveFile.sav");
            ObjectInputStream save = new ObjectInputStream(saveFile);
            highscore = (Integer) save.readObject();
            save.close();
         }
         catch(Exception exc){
            exc.printStackTrace();
         }
         
         counter=0;
         moves=0;
         score=0;
         lengthofsnake = 3;
         repaint();
         right = true;
         left = false;
         up = false;
         down = false;   
      }
      
      if(counter == 0){
         if(key == KeyEvent.VK_RIGHT)
         {
            moves++;
            z++;
            right = true;
            if(!left)
            {
               right = true;
            }
            else
            {
               right = false;
               left = true;
            }
            up = false;
            down = false;
         }
      
         if(key == KeyEvent.VK_LEFT)
         {
            if(moves!=0){
               moves++;
               z++;
               left = true;
               if(!right)
               {
                  left = true;
               }
               else
               {
                  left = false;
                  right = true;
               }
               up = false;
               down = false;
            }
         }
      
         if(key == KeyEvent.VK_UP)
         {
            moves++;
            z++;
            up = true;
            if(!down)
            {
               up = true;
            }
            else
            {
               up = false;
               down = true;
            }
            left = false;
            right = false;
         }
      
         if(key == KeyEvent.VK_DOWN)
         {
            moves++;
            z++;
            down = true;
            if(!up)
            {
               down = true;
            }
            else
            {
               down = false;
               up = true;
            }
            left = false;
            right = false;
         }
         
         if(key == KeyEvent.VK_R)
         {
            highscore = 0;
            counter=0;
            moves=0;
            score=0;
            lengthofsnake = 3;
            z = 0;
            
            try{
               FileOutputStream saveFile = new FileOutputStream("saveFile.sav");
               ObjectOutputStream save = new ObjectOutputStream(saveFile);
               save.writeObject(z);
               save.close();
            }
            catch(Exception exc){
               exc.printStackTrace();
            }
            
            right = true;
            left = false;
            up = false;
            down = false;
            
            repaint();
         }
      }
   }
   
   @Override
   public void keyReleased(KeyEvent e){
   
   }
}