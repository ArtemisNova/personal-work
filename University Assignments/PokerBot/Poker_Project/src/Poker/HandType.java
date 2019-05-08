package Poker;

/**
 * Class to check what type a hand is.
 * Will do this with a variety of boolean methods.
 */
public class HandType {

    private PlayingCard[] hand;
    private String handType = null;
    private int numPairs = 0;
    private int handGameValue = -1;

    public HandType(PlayingCard[] hand){
        this.hand = hand;

    }

    // return the hand type as a string
    // if not found already, will find then return it
    public String getHandType(){

        if(handType == null){
            findHandType();
        }

        return handType;
    }

    // returns the game value for the specific hand - so it doesnt need to be found again
    // if not already calculated will calculate on the spot
    public int getHandGameValue(){
        if(handGameValue == -1){
            findHandType();
        }

        return handGameValue;
    }

    /* Method to denote hand type and also give the corresponding point value of that hand
     * Does this by calling predetermined boolean methods and getting constant values in Constants.java
     * gives the hand a String "name"
     */
    private void findHandType(){

        if(isRoyalFlush()){
            handGameValue = Constants.ROYAL_FLUSH_VALUE;
            handType = Constants.ROYAL_FLUSH;
        }
        else if(isStraightFlush()){
            handGameValue = Constants.STRAIGHT_FLUSH_VALUE;
            handType = Constants.STRAIGHT_FLUSH;
        }
        else if(isFourOfAKind()){
            handGameValue = Constants.FOUR_OF_A_KIND_VALUE;
            handType = Constants.FOUR_OF_A_KIND;
        }
        else if(isFullHouse()){
            handGameValue = Constants.FULL_HOUSE_VALUE;
            handType = Constants.FULL_HOUSE;
        }
        else if(isFlush()){
            handGameValue = Constants.FLUSH_VALUE;
            handType = Constants.FLUSH;
        }
        else if(isStraight()){
            handGameValue = Constants.STRAIGHT_VALUE;
            handType = Constants.STRAIGHT;
        }
        else if(isThreeOfAKind()){
            handGameValue = Constants.THREE_OF_A_KIND_VALUE;
            handType = Constants.THREE_OF_A_KIND;
        }
        else if(isTwoPair()){
            handGameValue = Constants.TWO_PAIR_VALUE;
            handType = Constants.TWO_PAIR;
        }
        else if(isOnePair()){
            handGameValue = Constants.ONE_PAIR_VALUE;
            handType = Constants.ONE_PAIR;
        }
        else if(isHighHand()){
            handGameValue = Constants.HIGH_HAND_VALUE;
            handType = Constants.HIGH_HAND;
        }

        return;
    }

    /* Method to check for a Royal Flush; ie an Ace, King, Queen, Jack and 10 of the same suit
    * does this by checking the gameValue of the cards, when sorted the cards will be in order of
    * largest (14) to smallest (10) and therefore can be checked for with a decrementer
    */
    private boolean isRoyalFlush(){

        if(hand[0].getGameValue() == 14 && hand[1].getGameValue() == 13 && hand[2].getGameValue() == 12
                && hand[3].getGameValue() == 11 && hand[4].getGameValue() == 10 ){
            return isFlush(); // must now check that they all have same suit
        }
        else{
            return false;
        }

    }

    /* Way of finding if flush or straight already defined
     * so reuse of code for efficiency
     * as a straight flush must be a straight and a flush simultaneously
     */
    private boolean isStraightFlush(){

        return (isFlush() && isStraight());
    }


    /* Method to check if there is 4 of a kind
     * only 2 possible ways of four of a kind: the first four are the same, or the last four are the same
     * so using 2 if statements both possibilities are calculated
     */
    private boolean isFourOfAKind(){

        boolean fourOfAKind = false;

        // first four cards same?
        if(hand[0].getType().equals(hand[1].getType())  && hand[1].getType().equals(hand[2].getType()) &&
                hand[2].getType().equals(hand[3].getType())){

            fourOfAKind = true;
        }  // last four cards same?
        else if(hand[1].getType().equals(hand[2].getType())  && hand[2].getType().equals(hand[3].getType())
                && hand[3].getType().equals(hand[4].getType())){

            fourOfAKind = true;
        }

        return fourOfAKind;
    }


    /* Method to check for three of a kind in the hand
     * works similarly to fourOfAKind - there are only 3 possibilities; first  three, middle three and last three
     * once checking all these threeOfAKind can be determined true or not
     */
    private boolean isThreeOfAKind(){

        boolean threeOfAKind = false;

        if(!isFourOfAKind()){

            // first three cards same?
            if(hand[0].getType().equals(hand[1].getType())  && hand[1].getType().equals(hand[2].getType())){
                threeOfAKind = true;
            }  // middle three cards same?
            else if(hand[1].getType().equals(hand[2].getType())  && hand[2].getType().equals(hand[3].getType())) {
                threeOfAKind = true;
            }  // last three cards same?
            else if(hand[2].getType().equals(hand[3].getType())  && hand[3].getType().equals(hand[4].getType())){
                threeOfAKind = true;
            }

        }

        return threeOfAKind;
    }

    /* method to check for a full house; consists of a three pair and a two pair
     * Makes sure there is threeOfAKind and then checks the only 2 possibilites of a two pair;
     * the first two or the last two
     */
    private boolean isFullHouse(){

        boolean pair = false;

        if(isThreeOfAKind()){   // three of a kind guaranteed; now must check for the pair

            // when three of a kind is at end of hand
            if(hand[0].getType().equals(hand[1].getType()) && hand[2].getType().equals(hand[3].getType())
                    && hand[3].getType().equals(hand[4].getType())){
                pair = true;
            }  // when three of a kind is at the front of the hand
            else if(hand[0].getType().equals(hand[1].getType()) && hand[1].getType().equals(hand[2].getType())
                    && hand[3].getType().equals(hand[4].getType())){
                pair = true;
            }
        }
        return pair;
    }

    /* Method to find if hand is a straight
     * Straight includes all 5 cards, so starts from highest value
      * and checks if the rest are one lower in gameValue than the previous
      * if so in all cases then it is  a straight
     */
    private boolean isStraight(){

        int currentGameValue = hand[0].getGameValue();   // holds value
        boolean straight = true;

        for(int i = 1;  i < Constants.HAND_SIZE; i++){

            currentGameValue--;

            if(hand[i].getGameValue() != currentGameValue) {
                straight = false;
            }
        }

        /* If statement for the specific case of a straight that is 5 4 3 2 A
         * Done separately as the loop above will not recognise this type of straight
         */
        if(hand[0].getGameValue() == 14 && hand[1].getGameValue() == 5 && hand[2].getGameValue() == 4
                && hand[3].getGameValue() == 3 && hand[4].getGameValue() == 2){
            straight = true;
        }

        return straight;
    }

    /* Method to check if hand is flush
     * if it is a flush then all cards are of  the same suit
     * so travels through hand to ensure all same suit
     */
    private boolean isFlush(){

        boolean sameSuit = true;
        if(hand[0].getSuit() == hand[1].getSuit() && hand[1].getSuit() == hand[2].getSuit()
                && hand[2].getSuit() == hand[3].getSuit() && hand[3].getSuit() == hand[4].getSuit()){
            return true;
        }
        else{
            return false;
        }
    }

    /* Method to count num of pairs in the hand
     * Also calls ifFourOfAKind() to make sure it doesn't count 4 of same as 2 pair
     * compares one card's type to the next in the hand, and so on
     */
    private boolean isTwoPair(){
        numPairs = 0;

        /* Since compare one card to the next, i must only ever reach HAND_SIZE-1
         * to ensure no array out of bounds exception
         */
        for(int i = 0; i < Constants.HAND_SIZE-1; i++){

            if(hand[i].getType().equals(hand[i+1].getType())){
                numPairs++;
                i += 1; // increments twice (one here and one in loop) to get passed the two pair cards found
            }
        }

        if(numPairs == 2 && (!isFourOfAKind() || !isThreeOfAKind())){
            return true;
        }
        else{
            return false;
        }
    }

    /* method to see if there is one pair in the hand
     * uses isTwoPair numPairs value and then checks to ensure that method returns false
     * so if it only finds one pair this method can return true
     */
    private boolean isOnePair(){

        if(!isTwoPair() && numPairs == 1){
            return true;
        }
        else {
            return false;
        }
    }

    /* Checks if only outcome of the hand is a HighHand
     * must check all other boolean methods to ensure and will only return true if all the others are false
     */
    private boolean isHighHand(){

        if(!isRoyalFlush() && !isStraightFlush() && !isFourOfAKind() && !isFullHouse() && !isFlush() && !isStraight()
                && !isThreeOfAKind() && !isTwoPair() && !isOnePair()) {
            return true;
        }
        else {
            return false;
        }
    }
}
