package GUI;

import Game.Callback;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame; //imports JFrame library
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GUI extends JFrame implements KeyListener {



    JLabel label[][]; // Grid with GUI of values and color.
 // Grid with values in in int.




        public GUI(int x, int y) {

            label = new JLabel[x][y];

            JPanel grid = new JPanel(new GridLayout(x, y, 2, 2));
            grid.addKeyListener(this);

            grid.setBackground( Color.WHITE );
            grid.setBorder( new MatteBorder(2, 2, 2, 2, Color.BLACK) );

            for (int i = 0; i < x; i++) {
                for(int j = 0; j<y; j++) {
                    label[i][j] = new JLabel();
                    label[i][j].setFont(new Font("Serif", Font.BOLD, 60));
                    //label.setPreferredSize(new Dimension(200,200));
                    //label.setText(" label" + i);
                    label[i][j].setOpaque(true);
                    grid.add(label[i][j]);
                }
            }




            add( grid );
        }


    public Color getBackGroundColor(int value){
        switch (value){
            case 2:    return new Color(0xeee4da);
            case 4:    return new Color(0xede0c8);
            case 8:    return new Color(0xf2b179);
            case 16:   return new Color(0xf59563);
            case 32:   return new Color(0xf67c5f);
            case 64:   return new Color(0xf65e3b);
            case 128:  return new Color(0xedcf72);
            case 256:  return new Color(0xedcc61);
            case 512:  return new Color(0xedc850);
            case 1024: return new Color(0xedc53f);
            case 2048: return new Color(0xedc22e);
            default: return new Color(0,0,0);

        }

    }



    public void setTileSize(int numx,int numy,int Dimx,int Dimy){

        label[numx-1][numy-1].setPreferredSize(new Dimension(Dimx,Dimy));
    }
    private void displayInfo(KeyEvent e, String keyStatus) {

        //You should only rely on the key char if the event
        //is a key typed event.
        int id = e.getID();
        String keyString;
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } else {
            int keyCode = e.getKeyCode();
            keyString = "key code = " + keyCode
                    + " ("
                    + KeyEvent.getKeyText(keyCode)
                    + ")";
        }
        System.out.println(keyString);
    }

    public void setTileText(int x,int y, String text){
        setTileColor(x,y,Integer.parseInt(text));  // setting int and color

        label[x][y].setText(text);
        label[x][y].setHorizontalAlignment(SwingConstants.CENTER);
        label[x][y].setVerticalAlignment(SwingConstants.CENTER);
        label[x][y].setForeground(Color.WHITE);

    }

    public void setTileColor(int x,int y,int number){
        label[x][y].setBackground(getBackGroundColor(number));
    }

    @Override
    public void keyTyped(KeyEvent e) {
         System.out.println(e.getKeyCode());

    }

    @Override
    public void keyPressed(KeyEvent e) {
        displayInfo(e, "KEY PRESSED: ");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(""+e.getKeyCode());
    }
}






   /* public static void main(String[] args) {
        GUI r = new GUI(4,4);//makes new ButtonGrid with 2 parameters
        r.setText(3,3,"Svett");
    }*/
//}