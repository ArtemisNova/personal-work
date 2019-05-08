package Poker;

import java.util.Random;

/**
 * Scott Kelly, 14346251
 * Class to create a deck of cards
 * Array holds 52 PlayingCard objects representing all cards to be used
 *
 */
public class DeckOfCards {

    private PlayingCard[] deck = new PlayingCard[Constants.DECK_SIZE];
    private PlayingCard[] discardPile = new PlayingCard[Constants.DECK_SIZE];
    private final int MAX_SHUFFLE = 52 * 52;  // number of times shuffle switching operation happens
    private int nextCard = 0;
    private int nextDiscardedPlace = 0;
    private int nextDiscardDeal = 0;
    
    

    public DeckOfCards(){
        this.reset();
    }

    // re-initialises the deck array to have it full and ready for the next game
    public void reset(){

        deck = new PlayingCard[52];
        String[] cardTypes =  {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        // initialise Aces separately for a cleaner loop later
        deck[0] = new PlayingCard("A", PlayingCard.HEARTS, 1, 14);
        deck[1] = new PlayingCard("A", PlayingCard.DIAMONDS, 1, 14);
        deck[2] = new PlayingCard("A", PlayingCard.CLUBS, 1, 14);
        deck[3] = new PlayingCard("A", PlayingCard.SPADES, 1, 14);

        int j = 0;

        for(int i = 0; i < Constants.SUIT_SIZE-1; ++i) {

            j = (i+1) * 4; // used to be able to declare 4 cards at once without overwriting
            deck[j] = new PlayingCard(cardTypes[i], PlayingCard.HEARTS, i + 2, i + 2);
            deck[j+1] = (new PlayingCard(cardTypes[i], PlayingCard.CLUBS, i + 2, i + 2));
            deck[j+2] = new PlayingCard(cardTypes[i], PlayingCard.DIAMONDS, i + 2, i + 2);
            deck[j+3] = new PlayingCard(cardTypes[i], PlayingCard.SPADES, i + 2, i + 2);
        }

        this.shuffle();
    }

    // shuffles the deck by swapping two cards in the deck MAX_SHUFFLE times
    public void shuffle(){

        // resetting the int counters
        nextCard = 0;
        nextDiscardedPlace = 0;
        discardPile = new PlayingCard[Constants.DECK_SIZE];

        PlayingCard temp = null;
        Random rand = new Random();

        for(int i = 0; i < MAX_SHUFFLE; i++) {
            int pos1 = rand.nextInt(Constants.DECK_SIZE);
            int pos2 = rand.nextInt(Constants.DECK_SIZE);

            temp = deck[pos1];
            deck[pos1] = deck[pos2];
            deck[pos2] = temp;
        }
    }

    // deals card at index nextCard
    // if no cards left (ie index > 52) returns null
    public synchronized PlayingCard dealNext() {


        try {
            PlayingCard temp = deck[nextCard];
            nextCard++;
            return temp;
        } catch (ArrayIndexOutOfBoundsException e) {
            PlayingCard temp = null;
            if(nextDiscardDeal < nextDiscardedPlace) {
                temp = discardPile[nextDiscardDeal];
                nextDiscardDeal++;
            }
            return temp;
        }
    }

    // cards discarded by players are sent back into discard pile
    // catch to deny somehow returning too many cards
    // is useless the way I have implemented the code but brief says to do it
    public synchronized void returnCard(PlayingCard discarded) {

        if (discarded != null){
            try {
                discardPile[nextDiscardedPlace] = discarded;
                nextDiscardedPlace++;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Discard pile is full!");
            }
        }
    }
}
