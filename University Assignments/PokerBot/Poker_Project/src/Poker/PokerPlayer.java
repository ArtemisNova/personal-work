package Poker;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by scott on 09/03/17.
 */
public class PokerPlayer {

	private Date lastTweetRecieved;
	private String name = "BingBong";
	private int	balance = Constants.STARTING_BALANCE;

	private boolean folded = false;
	public Bot bot;

	public boolean hasFolded(){
		return folded;
	}

	public void fold(boolean val){
		folded = val;
	}

	public PokerPlayer(GameStats stats){
		int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
		if(randomNum == 0){
			bot = new Bot(stats, "conservative");
		}else if(randomNum == 1){
			bot = new Bot(stats, "moderate");
		}else{
			bot = new Bot(stats, "aggressive");
		}
	}

	public Date getRecievedDate() {
		return this.lastTweetRecieved;
	}

	public void setRecievedDate(Date update) {
		lastTweetRecieved = update;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	public HandOfCards getHand(){
		return hand;
	}

	public int getBalance() {
		return this.balance;
	}

	private HandOfCards hand;

	public String getHandAsString(){
		return hand.toString();
	}

	public void newHand(DeckOfCards deck){
		hand = new HandOfCards(deck);
	}

	public void replaceCards(ArrayList<String> cards){
		ArrayList<Integer> cardsToBeDiscarded = new ArrayList<>();

		for(String s: cards){
			int index = Integer.parseInt(s);
			cardsToBeDiscarded.add(index);
			hand.setCardForDiscard(index);
		}

		for(int index: cardsToBeDiscarded){
			PlayingCard temp = hand.replaceCardAt(index);
		}

		hand.discardFinished();
	}

	// method to discard up to three cards to try improve the current player's hand
	// currently generates random number between 0 and 99 inclusive
	// if random num is less than the card's discard probability then it will be discarded and replaced
	public int discard(){

		int numDiscarded = 0;
		int discardProbability;
		int randNum;
		Random rand = new Random();
		ArrayList<Integer> cardsToBeDiscarded = new ArrayList<>();


		for(int pos = 0; pos < Constants.HAND_SIZE; pos++){
			discardProbability = hand.getDiscardProbability(pos);

			if(discardProbability == 100){
				hand.setCardForDiscard(pos);
				cardsToBeDiscarded.add(pos);
			}
			else if(discardProbability > 0){
				randNum = rand.nextInt(100);

				if(randNum < discardProbability && cardsToBeDiscarded.size() < 3){
					hand.setCardForDiscard(pos);
					cardsToBeDiscarded.add(pos);
				}
			}

		}

		for(int index: cardsToBeDiscarded){
			PlayingCard temp = hand.replaceCardAt(index);
			numDiscarded++;
			//System.out.println("Card discarded: " + temp.toString());
		}

		hand.discardFinished();

		return numDiscarded;
	}
	public void changeBalance(int i) {
		balance += i;

	}
}
