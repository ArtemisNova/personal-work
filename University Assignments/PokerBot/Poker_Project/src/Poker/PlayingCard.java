package Poker;

/**
 *   Scott Kelly, 14346251
 *   scott.kelly@ucdconnect.ie
 *
 *   Class which creates a poker card object with its own
 *   Type, suit, face value and game value
 *   ToString method returns card's suit and type
 */
public class PlayingCard implements  Comparable<PlayingCard>{

    // constant characters to represent the card's suit
    public static final char HEARTS   = 'H';
    public static final char SPADES   = 'S';
    public static final char DIAMONDS = 'D';
    public static final char CLUBS    = 'C';

    // card's required variables
    private String cardType;
    private char cardSuit;
    private int faceValue;
    private int gameValue;

   
    public PlayingCard(String cardType, char cardSuit, int faceValue, int gameValue){
        this.cardType  = cardType;
        this.cardSuit  = cardSuit;
        this.faceValue = faceValue;
        this.gameValue = gameValue;
    }

    public char getSuit() { return cardSuit; }

    public String getType(){
        return cardType;
    }

    public int getFaceValue(){
        return faceValue;
    }

    public int getGameValue() {
        return gameValue;
    }

    // returns the card's type and suit as a string to be printed
    public String toString() {
        return new StringBuilder().append(cardType).append(cardSuit).toString();
    }

    /*  implementing for comparable
     *  Works in that comparing two card's game values
     *  if the passed in card is of lower value than the current card, returns 1
     *  if equal, then returns 0
     *  and if it is  of higher value, it returns -1
     */
    public int compareTo(PlayingCard other){
        int returnValue;
        
        if(this.gameValue > other.getGameValue()){
            returnValue = 1;
        }
        else if(this.gameValue == other.getGameValue()){
            returnValue = 0;
        }
        else{
            returnValue = -1;
        }

        return returnValue;    // has one return value outside the If/else as I feel it is better to make sure it always reaches the end of the method
    }
}
