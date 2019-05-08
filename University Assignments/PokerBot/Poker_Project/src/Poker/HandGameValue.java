package Poker;

/**
 * Created by scott on 16/03/17.
 */
public class HandGameValue {

    private PlayingCard[] hand;
    private String handType;
    private int handGameValue;
    private int[] numOfEachCard;

   public HandGameValue(String handType, PlayingCard[] hand, int[] numOfEachCard){

       this.handType = handType;
       this.handGameValue = 0;
       this.hand = hand;
       this.numOfEachCard = numOfEachCard;
    }


    public int getGameValue(int initialValue){

       handGameValue = initialValue;
       calculateGameValue();

       return handGameValue;
    }

    private void calculateGameValue(){

        int i = 0;

        switch (handType){

            case Constants.ROYAL_FLUSH:
                /* one royal flush does not beat another - they are the best hands in the game and therefore
                 * would be a tie if two showed up. So no other values needed
                 * this switch statement is just here to catch the case of a royal flush
                 */
                break;
            case Constants.STRAIGHT_FLUSH:
                /* Straight flushes use all 5 cards so no "loose" card calculation needed
                 * they are distinguished by how high they are, ie do they range in the upper or lower set of cards
                 *
                 * If statement below is for a special case - an Ace low straight; this is when an Ace is used as a 1
                 * rather than a 14, as it is part of the straight 5 4 3 2 A. This is the only case where Ace is used
                 * as a 1, and otherwise the Straights are calculated normally
                 */

                if(hand[0].getGameValue() == 14 && hand[1].getGameValue() == 5){  // if the highest card is Ace and 2nd highest card is a 5 then it is an Ace low straight

                    handGameValue += hand[0].getFaceValue(); // adding 1 for a low ACE
                    i = 1;
                }

                while(i < Constants.HAND_SIZE){

                    handGameValue += hand[i].getGameValue();
                    i++;
                }

                break;
            case Constants.FOUR_OF_A_KIND:
                // the 4 of a kind card^4 plus the extra card^1

                for(i = 0; i < Constants.SUIT_SIZE; i++){

                    if(numOfEachCard[i] == 4){ // four of a kind found
                        handGameValue += Math.pow(i+2, 4); // i+2 is the gameValue of the card
                    }
                    if(numOfEachCard[i] == 1){ // the final card in the hand
                        handGameValue += i+2; // i+2 is the gameValue of the card
                    }
                }


                break;
            case Constants.FULL_HOUSE:
                // all 5 cards used so no "loose" card calculation

                for(i = 0; i < Constants.SUIT_SIZE; i++){

                    if(numOfEachCard[i] == 3){ // the three pair of the full house
                        handGameValue += Math.pow(i+2, 4); // i+2 is the gameValue of the card
                    }
                    if(numOfEachCard[i] == 2){ // the two pair of the full house
                        handGameValue += i+2; // i+2 is the gameValue of the card
                    }
                }
                break;
            case Constants.FLUSH:
                /* 5 cards used, but here will calculate like they are "loose" cards
                 * this is because when comparing two flushes, the highest card wins rule applies
                 * an Ace is always a high Ace though, so will be calculated by the highest value in the hand
                 * (ie position 0)^5, 2nd highest^4 and so on
                 */

                int j = Constants.HAND_SIZE;  // 5 cards used, so x^5 is first case

                for(i = 0; i < Constants.HAND_SIZE; i++){
                    handGameValue += Math.pow(hand[i].getGameValue(), j);
                    j--;
                }

                break;
            case Constants.STRAIGHT:
                // calculated the same way as a Straight Flush, with the ace low case also

                if(hand[0].getGameValue() == 14 && hand[1].getGameValue() == 5){ // if the 2nd highest card is a 5 then it is an Ace low straight

                    handGameValue += hand[0].getFaceValue(); // adding 1 for a low ACE
                    i = 1;
                }

                while(i < Constants.HAND_SIZE){

                    handGameValue += hand[i].getGameValue();
                    i++;
                }

                break;
            case Constants.THREE_OF_A_KIND:
                // Nothing Fancy here; calculated in stated top comment above
                int threeCard = 0; // will hold the gameValue of the three of a kind card
                int exp = Constants.HAND_SIZE - 3; // 2 cards left in hand after three of a kind -- calculate loose cards with this

                // loop to find the three of a kind
                for(i = 0; i < Constants.SUIT_SIZE; i++){

                    if(numOfEachCard[i] == 3) {
                        threeCard = i + 2; // gameValue is index of array + 2
                    }
                }

                // loop to calculate weight of cards not in the three of a kind
                // goes through the hand so it will know which are the higher cards
                for(i = 0; i < Constants.HAND_SIZE; i++){

                    if(hand[i].getGameValue() != threeCard){
                        handGameValue += Math.pow(hand[i].getGameValue(), exp);
                        exp--;
                    }
                }

                // threeCard^3 to weigh the value of the three of a kind
                // * 1,000 so that it is more valuable than any other configuration of the two remaining cards
                handGameValue += (Math.pow(threeCard, 3) * 1000);

                break;
            case Constants.TWO_PAIR:
                // two pairs calculated by highest_pair^4 + lowest_pair^2 + extra_card^1
                int[] pairs = new int[2]; // holds the gameValue of the two pair
                int numPairs = 0; // current 0 number of pairs found

                for(i = 0; i < Constants.SUIT_SIZE; i++){

                    if(numOfEachCard[i] == 2){ // pair found
                        pairs[numPairs] = i+2; // putting pair game value in array
                        numPairs += 1; // a pair found
                    }
                    if(numOfEachCard[i] == 1){ // loose card found
                        handGameValue += i+2;
                    }
                }

                // weighing the higher pair to the power of 4, the second to the power of 2 eggs
                if(pairs[0] > pairs[1]){
                    handGameValue += Math.pow(pairs[0], 4);
                    handGameValue += Math.pow(pairs[1], 2);
                }
                else{
                    handGameValue += Math.pow(pairs[1], 4);
                    handGameValue += Math.pow(pairs[0], 2);
                }

                break;
            case Constants.ONE_PAIR:

                // calculated same as three of a kind
                int pairCard = 0; // will hold the gameValue of the one pair card
                exp = Constants.HAND_SIZE - 2; // 3 cards left in hand after one pair -- calculate loose cards with this

                // loop to find the three of a kind
                for(i = 0; i < Constants.SUIT_SIZE; i++){

                    if(numOfEachCard[i] == 2) {
                        pairCard = i + 2; // gameValue is index of array + 2
                    }
                }

                // loop to calculate weight of cards not in the three of a kind
                // goes through the hand so it will know which are the higher cards
                for(i = 0; i < Constants.HAND_SIZE; i++){

                    if(hand[i].getGameValue() != pairCard){
                        handGameValue += Math.pow(hand[i].getGameValue(), exp);
                        exp--;
                    }
                }

                // pairCard^2 to weigh the value of the one pair
                // * 10,000 so that it is more valuable than any other configuration of the three remaining cards
                handGameValue += (Math.pow(pairCard, 2) * 10000);

                break;
            case Constants.HIGH_HAND:
                // will weigh cards from the highest^5 to the lowest^1 and add them together
                exp = Constants.HAND_SIZE; // power to be used to weigh higher cards more

                for(i = 0; i < Constants.HAND_SIZE; i++){
                    handGameValue += Math.pow(hand[i].getGameValue(), exp);
                    exp--;
                }

                break;
            default:
                System.out.println("ERROR IN HAND TYPE");
                handGameValue = -1;
                break;
        }
    }
}
