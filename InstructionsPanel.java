import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * Write a description of class InstructionsPanel here.
 *
 * @author (nbryant2, zthabet, gbronzi)
 * @version (12.1.18)
 */
public class InstructionsPanel extends JPanel 
{
       /**
     * Constructs up a JPanel with two labels.
     */
    public InstructionsPanel()
    {
       setBackground (Color.green);
       JTextArea l1 = new JTextArea ("Welcome to The Wellesley Trail! The goal of this game is to" +
                                     " make it to graduation without getting lost in the tunnels," + 
                                     " attacked by geese, or dying from sleep deprivation.");
                                     
       JTextArea l2 = new JTextArea (" First you will pick from six different archtypes; each have" +
                                     " a different set of points for the sleep, smart and social" +
                                     " categories. You will then be prompted with questions and will" +
                                     " have two options to pick from.");
       JTextArea l3 = new JTextArea  (" Based on some decisions, you will have to play a mini-game." +
                                      " If you loose this mini-game,you loose everything." );
                                      
       JTextArea l4 = new JTextArea  ("You will also gain or loose points in the three categories" +
                                      " based on the decisions you choose. If " +
                                      " any of these categories drop below zero, you loose, so " +
                                      " choose wisely and good luck!!");
                                    
       l1.setBackground(Color.green); //so that the JTextArea matches the panel background
       add (l1);
       
    }
    
    public void actionPerformed (ActionEvent event){
        JButton button = (JButton)event.getSource();
        JPanel buttonPanel = (JPanel)button.getParent();
        JPanel cardLayoutPanel = (JPanel)buttonPanel.getParent();
        //make new situation panel from source
        CardLayout layout = (CardLayout)cardLayoutPanel.getLayout();
        CharSelection nextPanel = new CharSelection(new Person(0)); 
        cardLayoutPanel.add(nextPanel,"3");
        layout.show(cardLayoutPanel, "3");
    }
}
