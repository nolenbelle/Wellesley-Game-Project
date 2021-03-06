import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javafoundations.*;

/**
 * A memory based mini game. The user is given a list of words and then 
 * prompted to input into a JTextField that same list of words in the correct 
 * order. The constructor takes a string as a parameter and if the string
 * is one of the hard coded scenarios in the circumstances array, the constructor
 * forms the corresponding game for the scenario. 
 * <br></br> Acceptable parameters for the constructor: Tunnel, FYM, Squirrel
 * <br></br> Methods include a method for each panel of the game which creates that
 * panel, randomize() which takes the situational commands that the user must 
 * memorize and randomly puts them into memQueue, and a toString for memeQueue.
 * Two private classes are created, one for each action listener. One is for the
 * JTextField which the user inputs answers into and the other is for the 
 * variouse buttons. 
 * 
 * @author  Nolen Belle Bryant 
 * @author  Giulia Bronzi
 * @version 12.17.18
 */
public class MemoryPanel extends JPanel {

    protected ArrayQueue<String> memQueue; //holds the words given to player 
    //The commands given in different situations
    protected final static String [][] wordList={{"right", "left", "center", 
                "jump", "crawl" },
            {"Zahra","Giulia", "Kalau", "Tamara", "NB"},
            {"run", "freeze", "fight", "scamper", "hide"}};
    //references to the needed array memory words
    protected final static String [] circumstance = {"Tunnel","FYM","Squirrel"};
    //all of the text which is specific to each circumstance
    protected final static String [] welcomeList ={"Oh no, you got lost in the"+
            " tunnels!\nIf you can't remember how to get out,\nyou'll be stuck"+
            " down here forever!" ,"Welcome to your study group!" +
            "\nYou had better remember everyone's name, or else you'll die of" +
            " embarrassment",
            "Ack! A rabbid squirrel has started to chase you!\nRun, fight, hide-"+
            " do whatever you can to escape before it is too late!           "};
    protected final static String [] losingList = {"You took a wrong turn."+
            "\nNow you will be stuck in the tunnels forever.",
            "How embarrassing, you messed up someone's name!" +            
            "\nYou tried to hide in your room until you got over it," +
            "\nbut missed too many meals and died.",
            "Oh dear, you couldn't outsmart the squirrel." +
            "\nYou got bitten and died."};  
    protected final static String [] winningList = {"You successfully made it"+
            " out of the tunnels, hurray!",
            "Great job. You learned everyone's name and earned a few new "+ 
            "friends. Hurray!",
            "In the nick of time, you made it into the nearest building and "+
            "escaped!\nThe squirrel moved on to its next victim,\nand you"+
            " are one day closer to graduation."};             
    protected final static String [] images = {"images/Tunnel.jpg", "images/Campus.jpg", 
            "images/Squirrel.jpg"};                 
    protected Vector<String> answerKey; 
    protected String name;//the circumstance senario passed in the constructor;

    protected CardLayout cl,clBig;//contains to flip through the panels
    //holds two panels one of which is the background image
    protected JLayeredPane content;
    protected ImageIcon image;
    protected JLabel inputLabel, outputLabel,pic;
    protected JTextField answer;
    protected JTextArea path;
    protected JPanel scenario, instructions, game, gameOver, win, deck, 
    background, dying, deckBig;
    protected JButton next, start, dead, alive;
    protected int index,count;
    protected Font font;

    protected Person player;
    protected TrailsBinaryTree tree;
    protected Boolean isLeft;

    /**
     * The constructor for the running game takes three parameters and creates
     * the running mini game
     * 
     * @param p      the person class holding the current stats
     * @param t      where the user is in the binaryTree determines their in
     *               game location
     * @param direct determines to which child of the current leaf the user
     *               goes to after the game
     */
    public MemoryPanel(Person p, TrailsBinaryTree t, Boolean direct, 
    String scenario){
        player=p;
        tree=t;
        isLeft=direct;
        name=scenario;

        font = new Font("Verdana", Font.BOLD, 20);

        index = -1;//hold the index of the desired wordList array
        //check that name is in circumstance

        for (int i = 0; i<circumstance.length;i++)
            if (circumstance[i].equals(name)) index=i;
        if (index == -1) {
            System.out.println("Nonvalid circumstance passed in constructor");  
            return;
        }

        randomize(); //initializes the random commands into memQueue
        //olds a background images and a panel on top
        content = new JLayeredPane();
        background = new JPanel();//holds the scenario image
        //container for the panels, switches between them
        deck = new JPanel(new CardLayout());
        cl = (CardLayout)(deck.getLayout());//manages the deck

        //holds all the pieces as one card and the death scene as the other
        deckBig = new JPanel(new CardLayout());//the largest container
        clBig = (CardLayout)(deckBig.getLayout());//manages the big deck

        //The scene if the user loses
        dying = new DeathPanel();
        dying.setBounds(0, 0, 610, 455); 

        //add image to the background
        try {
            //scaling all input files to be the same size
            image = new ImageIcon(ImageIO.read(new File(images[index])));
            Image pic = image.getImage(); // transform it 
            Image newimg = pic.getScaledInstance(
                    1200, 800,  java.awt.Image.SCALE_SMOOTH);  
            image = new ImageIcon(newimg);  // transform it back

            background.add(new JLabel(image));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //add all the panel to the deck so they can be flipped through
        deck.add(scenarioPanel(), "situation");
        deck.add(introPanel(), "rules");
        deck.add(game(), "game");
        deck.add(gameOver(), "loser");
        deck.add(win(), "winner");
        deck.setFont(font);

        //the largest container, to flip between game and outside panel
        deckBig.add(content,"main");
        deckBig.add(dying,"dead");

        //set up how the gui is displayed
        setLayout(new BorderLayout());
        content.setBounds(0, 0, 1200, 800); //same as frame
        deck.setBounds(125, 150, 900, 200);
        background.setOpaque(true);
        background.setBounds(0, 0, 1200, 800); 
        deck.setOpaque(true);
        content.add(background, new Integer(0), 0); //sets to the background
        content.add(deck, new Integer(1), 0);//sets to the foreground      

        add(deckBig, BorderLayout.CENTER);
    }

    /**
     * Class for recieving text input from the user
     */
    protected class UserListener implements ActionListener {
        /**
         * Takes the user input and checks it with the correct answer by 
         * comparing it to the current item dequeued from memQueue.
         * If the answer is correct, the user may continue until all the 
         * answers have been inputted. If the answer is incorrect,
         * the user is taken to a game over screen.
         * 
         * @param event action of user entering text
         */
        public void actionPerformed (ActionEvent event) {      
            String input = answer.getText();//the command input by the user
            //displays the user's previous choices
            answer.setText("");
            if (count==0){
                path.setText("Your answer:\n" +input);
            } else {
                path.setText(path.getText()+ "\n" + input );
            }
            count++;
            //if word doesn't match the top of the queue, game over. 
            //If it does, just keep playing
            if (!input.equals(memQueue.dequeue())){
                //switch panels to a game over panel
                cl.show(deck, "loser");
            }
            //if the queue gets empty, it means all the words matched and 
            //they won
            if (memQueue.size()==0)
                cl.show(deck, "winner");
        }
    }

    /**
     * Class for the actions taken when the user clicks various buttons
     */
    protected class ButtonListener implements ActionListener {
        /**
         * Depending on which button is selected, a different action occures.
         * All of these buttons are screen transitions
         * 
         * @param event action of button being pressed
         */
        public void actionPerformed(ActionEvent event){
            //all of these buttons are transitions for the user
            if (event.getSource() == next){
                cl.show(deck,"game");//changes to the game screen
            }else if (event.getSource() == start){
                cl.show(deck,"rules");//go to instructions
            }else if (event.getSource() == dead){
                //JPanel pare = (JPanel) deck.getParent();
                JPanel cardLayoutPanel = (JPanel) content.getParent()
                                                    .getParent().getParent();
                CardLayout layout = (CardLayout) cardLayoutPanel.getLayout(); 

                //exit and go to the Death Screen
                cardLayoutPanel.add(dying, "dead");
                layout.show(cardLayoutPanel,"dead");

            }else if (event.getSource() == alive){                 
                JPanel memPanel = (JPanel) content.getParent();
                JPanel inbetween = (JPanel) memPanel.getParent();
                JPanel innn = (JPanel) content.getParent();
                JPanel buffer = (JPanel) innn.getParent();
                JPanel cardLayoutPanel = (JPanel)buffer.getParent();
                CardLayout layout = (CardLayout)cardLayoutPanel.getLayout();
                try{
                    if (isLeft){
                        //incremements the tree so that the current Situation
                        //is the left child of the current Situation
                        tree.nextLeft();
                        //shows a SituationPanel of the new current Situation
                        SituationPanel nextPanel = new SituationPanel(player,
                                tree);         
                        cardLayoutPanel.add(nextPanel,"left");
                        layout.show(cardLayoutPanel, "left");
                    }else{
                        //incremements the tree so that the current Situation
                        //is the left child of the current Situation
                        tree.nextRight();
                        //shows a SituationPanel of the new current Situation
                        SituationPanel nextPanel = new SituationPanel(player, 
                                tree);        
                        cardLayoutPanel.add(nextPanel,"right");
                        layout.show(cardLayoutPanel, "right");
                    }
                }catch(ArrayIndexOutOfBoundsException e){
                    GraduationPanel win = new GraduationPanel(); 
                    cardLayoutPanel.add(win,"winPanel");
                    layout.show(cardLayoutPanel, "winPanel");
                }
            }
        }
    }

    /**
     * Randomizes order of words in one of the arrays in wordList and puts them 
     * in memory queue.
     */
    public void randomize(){

        memQueue = new ArrayQueue<String>();
        Random rand = new Random();
        answerKey = new Vector<String>();

        int listLength = wordList[index].length;
        while (memQueue.size()<listLength){//until the memQueue is full
            int n = rand.nextInt(listLength);//pick a rand index in the list
            if (answerKey.indexOf(wordList[index][n])==-1){
                //keep answerKey in the dequeue order
                answerKey.add(wordList[index][n]);
                memQueue.enqueue(wordList[index][n]);//add it to memQueue
            }
        }
    }

    /**
     * First scenario screen- welcome the user and set the scene
     * 
     * @return first JPanel which is an introduction
     */
    protected JPanel scenarioPanel(){
        scenario = new JPanel();

        JTextArea welcome = new JTextArea(welcomeList[index]);
        start = new JButton ("Start");
        start.addActionListener(new ButtonListener());
        welcome.setFont(font);
        start.setFont(font);

        //scenario.setLayout(new BoxLayout(scenario, BoxLayout.Y_AXIS));  
        scenario.setLayout(new FlowLayout());
        scenario.add(welcome);
        scenario.add(start);

        return scenario;
    }


    /**
     * Second scenario screen- provides instructions for the game
     * 
     * @return second JPanel in sequence
     */
    protected JPanel introPanel() {
        //randomize();
        instructions = new JPanel();
        instructions.setPreferredSize(new Dimension(500,200));

        JLabel intro = new JLabel("Welcome to the memory mini game! Here is"+
                " how you play:");
        JTextArea segundo = new JTextArea("Below is a list of words. Memorize"+
                " them if you can!\nOn the next screen, you will input"+
                " those words in the correct order.\nWhen ready, hit the 'Go!'"+
                " Button to move on.");

        next = new JButton ("Go!");
        next.addActionListener(new ButtonListener());

        JLabel commands = new JLabel("Memory words: " + answerKey.toString());

        intro.setFont(font);
        segundo.setFont(font);
        commands.setFont(font);
        next.setFont(font);

        instructions.setLayout(new FlowLayout());
        instructions.add(intro);
        instructions.add(segundo);
        instructions.add(commands);
        instructions.add(next);

        return instructions;
    }

    /**
     * Third scenario screen- Where the user plays the game by inputting words
     * into a text field
     * 
     * @return third JPanel in sequence showing the game
     */
    protected JPanel game(){
        game = new JPanel();

        inputLabel = new JLabel ("Enter the words in the order in which they"+
            " appeared:");
        path = new JTextArea ();//displays which word the user has put in

        answer = new JTextField (5); //box for the user input

        UserListener listener = new UserListener();
        answer.addActionListener (listener);
        count = 0;

        inputLabel.setFont(font);
        answer.setFont(font);
        path.setFont(font);

        game.setLayout(new FlowLayout());
        game.add (inputLabel);
        game.add(answer);
        game.add (path);

        return game;
    }

    /**
     * One of two possible final scenario screen, appears if the user does 
     * not input the correct word
     * 
     * @return loser JPanel
     */
    protected JPanel gameOver(){
        gameOver = new JPanel();
        JTextArea exit = new JTextArea(losingList[index] + 
                "\nPlease click 'Game Over' to move on");

        dead = new JButton("Game Over");
        dead.addActionListener(new ButtonListener());

        exit.setFont(font);
        dead.setFont(font);

        gameOver.setLayout(new FlowLayout());

        gameOver.add(exit);
        gameOver.add(dead);

        return gameOver;    
    }

    /**
     * One of two possible final scenario screen, appears if the user inputs 
     * all the correct words
     * 
     * @return JPanel winner screen
     */
    protected JPanel win(){
        win = new JPanel();

        JTextArea congrats = new JTextArea(winningList[index] +
                "\nPlease click 'Back to School' to move on");
        alive = new JButton("Back to School");
        alive.addActionListener(new ButtonListener());

        congrats.setFont(font);
        alive.setFont(font);

        win.setLayout(new FlowLayout());
        win.add(congrats);
        win.add(alive);

        return win;    
    }

    /**
     * Gets String of memQueue in order.
     * 
     * @return a String representation of the memory queue 
     */
    public String toString(){
        String result = "";
        for (int i = 0; i<memQueue.size();i++){
            String temp = memQueue.dequeue();
            result+=temp + ", ";
            memQueue.enqueue(temp);
        }
        return result;
    }
}