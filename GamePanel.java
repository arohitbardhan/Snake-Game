/*import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
*/
//import java.util.Random;
//import java.util.Timer;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{

    static final int screen_width=600;
    static final int screen_height=600;
    static final int unit_size=25;
    static final int game_units=(screen_width*screen_height)/unit_size;
    static final int delay=80;
    final int x[]=new int[game_units];//to hold the x coordinates of the body parts including the head of the snake
    final int y[]=new int[game_units];//to hold the y coordinates of the body parts of the snakes
    int bodyParts=6;
    int applesEaten;
    int appleX;//x coordinate of apple(food)
    int appleY;//y coordinates of apple(food)
    char direction ='R';//snake going right direction
    boolean running = true;
    Timer timer;
    Random random;

    @Override
    public void actionPerformed(ActionEvent e) {
       if(running){
        move();
        checkApple();
        checkCollisions();
       }
       repaint();
       
    }
    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(screen_width,screen_height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        

    }
    public void startGame(){
        newApple();
        running=true;
        timer=new Timer(delay,this);
        timer.start();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
        
    }
    public void draw(Graphics g){

        if(running){
        for(int i=0;i<screen_height/unit_size;i++){
            g.drawLine(i*unit_size, 0, i*unit_size, screen_height);
            g.drawLine(0, i*unit_size, screen_width, i*unit_size);
        }
        //drawing the food
        g.setColor(Color.red);
        g.fillOval(appleX, appleY,unit_size,unit_size);

        //drawing the body parts of the snake
        for(int i=0;i<bodyParts;i++){
            if(i==0){
                g.setColor(Color.green);
                g.fillRect(x[i],y[i],unit_size,unit_size);
            }
            else{
              //  g.setColor(new Color(45,180,0));
                g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));//Multi colour Snake
                g.fillRect(x[i],y[i],unit_size,unit_size);
            }
        }
        //Displaying Score
        g.setColor((Color.red));
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(screen_width-metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        }
        else
        gameOver(g);

    }
    public void newApple(){
        appleX=random.nextInt((int)(screen_width/unit_size))*unit_size;//food appears someplace along the x axis 
        appleY=random.nextInt((int)(screen_height/unit_size))*unit_size;//food appears someplace along the y axis

    }
    public void move(){
        //move the body of the snake
        for(int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':
            y[0]=y[0]-unit_size;
            break;
            case 'D':
            y[0]=y[0]+unit_size;
            break;
            case 'L':
            x[0]=x[0]-unit_size;
            break;
            case 'R':
            x[0]=x[0]+unit_size;
            break;
        }

    }
    public void checkApple(){
        //snake eating the apple
        if(x[0] == appleX && y[0] == appleY){
            bodyParts++;
            applesEaten++;
            newApple();

        }
        

    }
    public void checkCollisions(){
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i]) && (y[0]==y[i])){//Snake  head collides with it's own body
                running=false;
            }
        }
        //if snake head touches left border
        if(x[0]<0){
            running=false;
        }
        //if snake touches right border
        if(x[0]>screen_width){
            running=false;
        }
        //if snake touches top border
        if(y[0]<0){
            running=false;
        }
        //if snake touches bottom border
        if(y[0]>screen_height){
            running = false;
        }
        if(running==false)
        timer.stop();

    }
    public void gameOver(Graphics g){


        //Score
        g.setColor((Color.red));
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(screen_width-metrics1.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());

        //Game Over
        g.setColor((Color.red));
        g.setFont(new Font("Ink Free",Font.BOLD,76));
        FontMetrics metrics2=getFontMetrics(g.getFont());
        g.drawString("Game Over",(screen_width-metrics2.stringWidth("Game Over"))/2,screen_height/2);//To put the text "Game Over" at the centre of tthe screen


    }
   public class MyKeyAdapter extends KeyAdapter{
    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
            if(direction!='R'){
                direction='L';
            }
            break;
            case KeyEvent.VK_RIGHT:
            if(direction!='L'){
                direction='R';
            }
            break;
            case KeyEvent.VK_UP:
            if(direction!='D')
                direction='U';
            break;
            case KeyEvent.VK_DOWN:
            if(direction!='U')
            direction='D';
            break;
        }
        
    }
   }
}
