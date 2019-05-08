package Poker;

/**
 * Created by scott on 17/02/17.
 */
public class Constants {

	//set to false to enable Twitter functionality
	public static final boolean TEST_MODE = true;
	public static final int UPDATE_DELAY_SECONDS = 20;
	
    public static final int NUM_HUMAN_PLAYERS = 1;
    public static final int NUM_BOT_PLAYERS = 4;
    public static final int TOTAL_NUM_PLAYERS = NUM_HUMAN_PLAYERS + NUM_BOT_PLAYERS;
    
	private final int BUY_IN = 1;
    
    public static final String PB_HANDLE = "SuckyBeigeFish";
    
    //The sum of the characters in these names must be less than 37
    public static final String [] BOT_NAME = {"John", "Paul", "George", "Ringo"};

    public static final char HEARTS   = 'H';
    public static final char SPADES   = 'S';
    public static final char DIAMONDS = 'D';
    public static final char CLUBS    = 'C';

    public static final int HAND_SIZE = 5;
    public static final int DECK_SIZE = 52;
    public static final int SUIT_SIZE = 13;
    public static final int NUM_SUITS = 4;

    public static final int MAX_DISCARD = 3;
	public static final int STARTING_BALANCE = 20;


    // string values for each type of hand
    // used for printing and also verifying hand type once found
    public static final String ROYAL_FLUSH = "Royal Flush";
    public static final String STRAIGHT_FLUSH = "Straight Flush";
    public static final String FOUR_OF_A_KIND = "Four Of A Kind";
    public static final String FULL_HOUSE = "Full House";
    public static final String FLUSH = "Flush";
    public static final String STRAIGHT = "Straight";
    public static final String THREE_OF_A_KIND = "Three Of A Kind";
    public static final String TWO_PAIR = "Two Pair";
    public static final String ONE_PAIR = "One Pair";
    public static final String HIGH_HAND = "High Hand";

    // base level values for each type of hand
    // starting at 100 million (100 * 10^6) down to 10 million
    // such a large gap between each hand is to ensure the best possible hand of one type lower
    // will never beat the worst hand of the type above
    // ie the best full house will not beat the worst four of a kind in gameValue
    public static final int ROYAL_FLUSH_VALUE = 100 * (int)Math.pow(10, 6);
    public static final int STRAIGHT_FLUSH_VALUE = 90 * (int)Math.pow(10, 6);
    public static final int FOUR_OF_A_KIND_VALUE = 80 * (int)Math.pow(10, 6);
    public static final int FULL_HOUSE_VALUE = 70 * (int)Math.pow(10, 6);
    public static final int FLUSH_VALUE = 60 * (int)Math.pow(10, 6);
    public static final int STRAIGHT_VALUE = 50 * (int)Math.pow(10, 6);
    public static final int THREE_OF_A_KIND_VALUE = 40 * (int)Math.pow(10, 6);
    public static final int TWO_PAIR_VALUE = 30 * (int)Math.pow(10, 6);
    public static final int ONE_PAIR_VALUE = 20 * (int)Math.pow(10, 6);
    public static final int HIGH_HAND_VALUE = 10 * (int)Math.pow(10, 6);

    

}
