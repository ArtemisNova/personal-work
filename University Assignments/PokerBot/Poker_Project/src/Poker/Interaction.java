package Poker;

/**
 * Created by Luke on 09/04/17.
**/

import java.util.*;

import twitter4j.TwitterException;

public interface Interaction {

	public void welcomeSplash(PokerPlayer user);
	public void showChipCounts(PokerPlayer[] user);
	public void showDealtHandAskDiscard(PokerPlayer[] user);
	public void showUpdatedHandAskFold(PokerPlayer[] user);
	public void showFinalHands(PokerPlayer[] user);
	public void sayWin(PokerPlayer user, PokerPlayer winner, GameStats stats);
	
	public ArrayList<String> runSearch(String str) throws TwitterException;
	public void setLatestSearch(Date date);
	
	public ArrayList<String> getDiscardResponse(PokerPlayer user);
	public ArrayList<String> getYesOrNoResponse(PokerPlayer user);
	public String getBetResponse(PokerPlayer user);
	
	public ArrayList<String> splitStatus(String str, PokerPlayer user); //splits strings longer than 140 characters to fit into multiple tweets
	public void postStatus(String str); //tweet string less than 140 characters
	
	public String getUserResponse(String str); //returns the latest tweet from user with handle "str"
	
}
