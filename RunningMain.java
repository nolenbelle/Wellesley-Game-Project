
/**
 *
 * @author (nbryant2, zthabet, gbronzi)
 * @version (12.10.18)
 */

import javax.swing.* ;
import java.awt.*;

public class RunningMain extends javax.swing.JFrame {
    //private JSplitPane splitPanel;  // split the window in top and bottom
    private JPanel countPanel;
    private JPanel runPanel;
    
    public RunningMain(){
        //splitPanel = new JSplitPane();
        
        //JPanel mainPanel = new JPanel();
        //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JLabel timerField = new JLabel();
        
        
        //JPanel runPanel = new RunningPanel(timerField);
        //JPanel countPanel = new CountdownPanel(timerField);
        //countPanel.add(timerField);
        
        //getContentPane().setLayout(new GridLayout());
        //getContentPane().add(splitPanel);
       
        //Set the panel details
        //splitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        //splitPanel.setDividerLocation(160); 
        //splitPanel.setTopComponent(countPanel);
        //splitPanel.setBottomComponent(runPanel);
        //mainPanel.add(new RunningPanel(timerField));
        //int time = countPanel.getCounter(); 
        //not sure how to get the state of the counter from CountdownPanel
        add (new RunningPanel(timerField));
        setPreferredSize(new Dimension(400, 400));
        pack();       
    }
       
    public static void main (String[] args){
        new RunningMain().setVisible(true);
        
    }
}
