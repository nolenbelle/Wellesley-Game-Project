
/**
 * Write a description of class MemoryPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MemoryPanel extends JPanel {

   private JLabel inputLabel, outputLabel;
   private JTextField answer;
   private JTextArea path;
   private int count,x,y;

   //-----------------------------------------------------------------
   //  Constructor: Sets up the main GUI components.
   //-----------------------------------------------------------------
   public MemoryPanel()
   {
      inputLabel = new JLabel ("Enter the words in the order in which they appeared:");
      outputLabel = new JLabel ("Your chosen tunnel path: ");
      path = new JTextArea ();
      count = 0;
      x=300;
      y=75;

      answer = new JTextField (5);
      TempListener listener = new TempListener();
      answer.addActionListener (listener);


      add (inputLabel);
      add (answer);
      //add (outputLabel);
      add (path);

      //setPreferredSize (new Dimension(x, y));
      //setBackground (Color.blue);
   }

   // public int getX(){
       // return x;
    // }
    
   // public int getY(){
       // return y;
    // }
    

   private class TempListener implements ActionListener
   {
      //--------------------------------------------------------------
      //  Performs the conversion when the enter key is pressed in
      //  the text field.
      //--------------------------------------------------------------
      public void actionPerformed (ActionEvent event)
      {
          String input = answer.getText();
          if (count==0){
            path.setText("Your chosen tunnel path:\n" +input);
          } else {
            path.setText(path.getText()+ "\n" + input );
            //y += 20;
          }
          //y+=20;
          count++;
      }
      }
 }