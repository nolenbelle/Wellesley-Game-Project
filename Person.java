
/**
 * The Person class holds all the information for each of the six archetypes. There are getter and 
 * setter methods that are used to get and set the three different score categories (smart, social, 
 * sleep), as well as a method that checks whether all the scores are above zero.
 *
 * @author (gbronzi, zthabet,nbryant2)
 * @version (12.10.18)
 */
public class Person
{
    //instance variables
    int sleepScore, smartScore, socialScore; // three scores that are based on choosen character
    int[] character; //array holds all the points for the choosen character
    
    //the six archetypes, each have a different array of sleep, social, smart points
    private final static int[] athletic = {2, 2, 4};
    private final static int[] hermit = {4, 5, 0};
    private final static int[] horse = {0, 0, 0};
    private final static int[] offCampus = {0, 3, 4};
    private final static int[] society = {1, 2, 3};
    private final static int[] wendy = {2, 3, 3};
    
    private final static int[][] characters = {athletic,hermit,horse,offCampus,society,wendy};
    //was thinking we could do array of arrays for characters, through gui, each button for 
    //the characters has a different number assigned, which will be the charInt inputted 
    //to create the char
    /**
     * Constructor for objects of class Person
     * 
     * @param charInt 
     */
    public Person(int charInt)
    {
        character = characters[charInt];
        sleepScore = character[0];
        smartScore = character[1];
        socialScore = character[2];
        
    }

    /**
     * Returns the sleep score
     * 
     * @return sleepScore
     */
    public int getSleepScore(){
        return sleepScore;
    }
    
    /**
     * Returns the smart score
     * 
     * @return smartScore
     */
    public int getSmartScore(){
        return smartScore;
    }
    
    /**
     * Returns the social score
     * 
     * @return socialScore
     */
    public int getSocialScore(){
        return socialScore;
    }
    
    /**
     * Changes the sleep score to the inputted value
     * 
     * @param int score
     */
    public void setSleepScore(int score){
        sleepScore = score;
    }
    
    /**
     * Changes the smart score to the inputted value
     * 
     * @param int score
     */
    public void setSmartScore(int score){
        smartScore = score;
    }
    
    /**
     * Changes the social score to the inputted value
     * 
     * @param int score
     */
    public void setSocialScore(int score){
        socialScore = score;
    }
    
    /**
     * Checks to see if scores are above zero
     * 
     * @return boolean true if all scores above zero
     */
    public boolean isAboveZero(){
        //do we want to do this so we check whether all the scores are above zero
        //or only check one? if we did one, we could have a param that would be one of the categories
        
        return sleepScore > 0 && smartScore > 0 && socialScore > 0;      
    }
   
    
}
