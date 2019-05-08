package Poker;

import java.util.ArrayList;

/**
 * Created by scott on 16/03/17.
 */
public class ProbabilityCalculator {

    private PlayingCard[] hand;
    private String handType;
    private int[] numOfEachCard;
    private int higherBoundAdd = 0;

    public ProbabilityCalculator(PlayingCard[] hand, String handType, int[] numOfEachCard){
          this.hand = hand;
          this.handType = handType;
          this.numOfEachCard = numOfEachCard;
    }

    // calculates the probability of getting a desired card out of the remaining cards
    private int calculateProbability(int numGoodOutcomes, int numCardsLeft){
        float prob = 0;
        
        if(numGoodOutcomes == 0){
            return 10; // never truly no better outcomes, just very low chance
        }

        prob = ((float)numGoodOutcomes / (float)numCardsLeft) * 100.0f;

        return (int)prob;
    }

    private int looseCardImprovements(int looseCardValue){

        int aceCardValue = 14;
        int numBetterCards = 0;

        /* for each type of card type above looseCard, there are 4 cards better in game value
           so for ace - looseCard, there is 4 * result better cards
         */

        numBetterCards = (aceCardValue - looseCardValue) * 4;

        // loop to remove better cards that may already be in the hand
        for(int i = looseCardValue+1; i < aceCardValue; i++){
            numBetterCards -= numOfEachCard[i-2]; // i+2 = game value of index i
        }

        return numBetterCards;
    }


    /* method to check for a busted flush - this is where 4 cards have the same suit and one does not
    * returns true if there is 4 of one suit, therefore there is only one loose
    */
    private char checkForBustedFlush(){
        int[] numSuit = new int[Constants.NUM_SUITS];
        char suitOfFourCards = 'N';
        char[] suits = {'H', 'D', 'S', 'C'};

        for(PlayingCard card: hand){
            switch(card.getSuit()){
                case Constants.HEARTS:
                    numSuit[0]++;
                    break;
                case Constants.DIAMONDS:
                    numSuit[1]++;
                    break;
                case Constants.SPADES:
                    numSuit[2]++;
                    break;
                case Constants.CLUBS:
                    numSuit[3]++;
                    break;
            }
        }

        for(int i = 0; i < Constants.NUM_SUITS; i++){
            if(numSuit[i] == 4){
                suitOfFourCards = suits[i];
            }
        }

        return suitOfFourCards;
    }

    // method to check for an Ace low Straight - A 2 3 4 5
    // pass in false to check for X 5 4 3 2, where X is any card besides A
    private boolean AceLowStraight(boolean broken){
        if(!broken) {
            if (hand[0].getGameValue() == 14 && hand[1].getGameValue() == 5 && hand[2].getGameValue() == 4 &&
                    hand[3].getGameValue() == 3 && hand[4].getGameValue() == 2) {
                return true;
            } else {
                return false;
            }
        }
        else{
            if(hand[1].getGameValue() == 5 && hand[2].getGameValue() == 4 &&
                    hand[3].getGameValue() == 3 && hand[4].getGameValue() == 2){
                return true;
            }
            else {
                return false;
            }
        }
    }


    private int checkForBrokenStraight(){

        int lowerBound;

        if((lowerBound = checkForOpenEndedStraight()) != -1){
            higherBoundAdd = 3; // if four cards in row, then +3 to lowerBound to get hihger
            return lowerBound;
        }
        else if((lowerBound = checkForInsideStraight()) != -1) {
            higherBoundAdd = 4; // four cards in row and one space: +4 to account for this
            return lowerBound;
        }
        else{
            return -1;
        }
    }

    // method to check for open ended straight - ie 4 cards out of five for a straight,
    // missing either one below or above the current range
    private int checkForOpenEndedStraight() {
        int numOfCardsInARow = 0;
        int lowerBound = -1;
        boolean broken = false;

        for (int i = 0; i < Constants.SUIT_SIZE; i++) {
            if (numOfEachCard[i] == 0) {
                numOfCardsInARow = 0;
                if (i != Constants.SUIT_SIZE - 1) {
                    lowerBound = i + 1; // +1 because it will assume next index is start of straight
                }
            } else if (numOfEachCard[i] > 1) { // at least one card of this value
                numOfCardsInARow++;
            }
        }

        if(AceLowStraight(broken)){
            return 14; // the one card on an open ended ace low straight that would fix it
        }
        else if (numOfCardsInARow == 4) {
            return lowerBound + 2; // +2 to get the game value of the lowest index card
        }
        else {
            return -1;
        }
    }

    // checks if the hand is almost a straight, but is missing a card in the middle of 4 straight cards to complete
    // checks for four cards in a row with only one gap in between them, if so returns the lowerBound of the straight
    // returns -1 if not true
    private int checkForInsideStraight(){
        int lowerBound = -1;
        int numCardsInRow = 0;
        int numGaps = 0;

        for(int i = 0; i < Constants.SUIT_SIZE && numCardsInRow < 4; i++){ // go through all different types of cards


            if(numOfEachCard[i] > 0){ // there is at least one card of this value
                numCardsInRow++;

                if(lowerBound == -1){ // setting low bound of new possible straight
                    lowerBound = i+2; // +2 to get gameValue from index
                }
            }
            else if(numOfEachCard[i] == 0){ // gap found
                numGaps++;

                if(numGaps > 1){ // too many gaps found - reset
                    numGaps = 0;
                    numCardsInRow = 0;
                    lowerBound = -1;
                }
            }
        }

        if(numCardsInRow < 4){
            lowerBound = -1;
        }
        return lowerBound;
    }


    private ArrayList<Integer> findPairs(){
        ArrayList<Integer> pairsValues = new ArrayList<Integer>();

        for(int index = 0; index < Constants.SUIT_SIZE; index++) {
            if (numOfEachCard[index] == 2) {// pair found
                pairsValues.add(index + 2); // +2 for game Value
            }
        }
        return pairsValues;
    }

    public int getProbability(int cardPosition){
        int numCardsDrawn = Constants.HAND_SIZE * Constants.TOTAL_NUM_PLAYERS;
        int numCardsLeft = Constants.DECK_SIZE - Constants.HAND_SIZE;
        int probability = 0;
        int numBetterCards;

        if(cardPosition < 0 || cardPosition > Constants.HAND_SIZE){
            System.out.println("Card index incorrect");
            return 0;
        }

        switch(handType){
            case Constants.ROYAL_FLUSH:
                // best hand in game -- no need to discard any card
                probability = 0;
                break;
            case Constants.STRAIGHT_FLUSH:
                probability = 0;
                break;
            case Constants.FOUR_OF_A_KIND:
                int looseCard = 0;

                if(hand[0].getGameValue() == hand[1].getGameValue()){
                    looseCard = Constants.HAND_SIZE-1;  // four of a kind is the highest four in hand
                }
                else{
                    looseCard = 0; // loose card is highest card in hand
                }

                numBetterCards = looseCardImprovements(hand[looseCard].getGameValue());
                probability = calculateProbability(numBetterCards, numCardsLeft);
                break;
            case Constants.FULL_HOUSE:
                // if Full house, the middle card will always be part of the three of a pair
                // cos either two pair is higher, three of a kind from card 3-5
                // or two pair is lower, card 1-3 is the three of a kind
                int threeOfAKindValue = hand[Constants.HAND_SIZE / 2].getGameValue();

                if(hand[cardPosition].getGameValue() != threeOfAKindValue){
                    // 1 great outcome (becomes four of a kind) 2 good outcomes, draw same card value
                    probability = calculateProbability(3, numCardsLeft);
                }
                else{
                    probability = 0; // if part of the three of a kind, would not be worth to discard
                }

                break;
            case Constants.FLUSH:
                int lowerBound = 0;

                if((lowerBound = checkForBrokenStraight()) != -1){

                    int higherBound = lowerBound + higherBoundAdd; // higher bound of broken straight is 3 or 4 cards higher than lowerBound
                    int currentGameValue = hand[cardPosition].getGameValue();

                    if(currentGameValue < lowerBound || currentGameValue > higherBound){
                        probability = calculateProbability(4, numCardsLeft);
                    }
                    else{
                        probability = 0; // do not discard card of flush which won't lead to a straight flush
                    }
                }


                break;
            case Constants.STRAIGHT:
                char primarySuit = checkForBustedFlush();

                if(primarySuit != 'N'){ // it is a busted flush

                    if(hand[cardPosition].getSuit() != primarySuit){
                        // 13 - 4 = 9 good outcomes; the remaining num of primarySuit to create a flush
                        probability = calculateProbability(9, numCardsLeft);
                    }

                }
                else{
                    probability = 0; // no need to risk breaking the straight for no gain
                }

                break;
            case Constants.THREE_OF_A_KIND:
                // if three of a kind, middle card (index 2) will always be part of that three of a kind
                threeOfAKindValue = hand[2].getGameValue();
                int currentGameValue = hand[cardPosition].getGameValue();
                
                if(currentGameValue != threeOfAKindValue){// loose card; calculate prob accordingly
                        numBetterCards = looseCardImprovements(hand[cardPosition].getGameValue());
                        probability = calculateProbability(numBetterCards, numCardsLeft);
                }
                else{  // don't ruin the current three of a kind
                    probability = 0;
                }

                break;
            case Constants.TWO_PAIR:
                ArrayList<Integer> pairs = findPairs();
                currentGameValue = hand[cardPosition].getGameValue();

                if(pairs.contains(currentGameValue)){
                    // 4 possible good values - get the pair card again or turn other pair into three of a kind
                    probability = calculateProbability(4, numCardsLeft);
                }
                else{ // loose cards
                    numBetterCards = looseCardImprovements(currentGameValue);
                    probability = calculateProbability(numBetterCards, numCardsLeft);
                }
                break;
            case Constants.ONE_PAIR:
                pairs = findPairs();
                primarySuit = checkForBustedFlush();


                if(primarySuit != 'N'){
                    char currentSuit = hand[cardPosition].getSuit();

                    if(currentSuit != primarySuit){
                        // 9 good outcomes, the remaining cards of the primary suit
                        probability = calculateProbability(9, numCardsLeft);
                    }
                }
                else if((lowerBound = checkForBrokenStraight()) != -1){
                    int higherBound = lowerBound + higherBoundAdd;

                    if(hand[cardPosition].getGameValue() < lowerBound || hand[cardPosition].getGameValue() > higherBound
                            || pairs.contains(hand[cardPosition].getGameValue())){
                        probability = calculateProbability(4, numCardsLeft);
                    }
                }
                else if(!pairs.contains(hand[cardPosition].getGameValue())){
                    // loose cards

                    numBetterCards = looseCardImprovements(hand[cardPosition].getGameValue());
                    probability = calculateProbability(numBetterCards, numCardsLeft);
                }
                else{
                    probability = 0; // no benefit is discarding a card of a pair
                }

                break;
            case Constants.HIGH_HAND:

                primarySuit = checkForBustedFlush();
                lowerBound = checkForBrokenStraight();

                if(primarySuit != 'N') {

                    char currentSuit = hand[cardPosition].getSuit();

                    if (currentSuit != primarySuit) {
                        // 9 good outcomes, the remaining cards of the primary suit
                        probability = calculateProbability(9, numCardsLeft);
                    }
                }
                else if(lowerBound != -1){

                    int higherBound = lowerBound + higherBoundAdd;
                    currentGameValue = hand[cardPosition].getGameValue();

                    if(currentGameValue < lowerBound || currentGameValue > higherBound){

                        probability = 90; // relatively high to try finish the straight
                    }
                    else{
                        probability = 0; // do not discard card of flush which won't lead to a straight flush
                    }
                }
                else{
                    numBetterCards = looseCardImprovements(hand[cardPosition].getGameValue());

                    probability = calculateProbability(numBetterCards, numCardsLeft);
                }

                break;
            default:
                System.out.println("ERROR IN HAND TYPE");
                probability = 0;
        }
        return probability;
    }
}
