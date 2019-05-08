package Poker;

/**
 * Created by Luke on 09/04/17.
 **/

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import twitter4j.StatusUpdate;
import twitter4j.TwitterException;


public class ConsoleInteraction implements Interaction {

	@Override
	public ArrayList<String> splitStatus(String str, PokerPlayer user) {
		ArrayList<String> splitArray = new ArrayList<String>();

		String[] sentances = str.split("\\|");
		String tweet = "";

		for(int i = 0; i < sentances.length; i++) {
			if((tweet.length() + sentances[i].length()) < 115) {
				tweet += sentances[i];
			}
			else {
				tweet = "@" + user.getName() + tweet;
				splitArray.add(tweet);
				tweet = "";
				i--;
			}   
		}

		for(int i = 0; i < splitArray.size(); i++) {
			String withTweetCount = splitArray.get(i);
			withTweetCount = withTweetCount + " (" + (i+1) + "/" + splitArray.size() + ")"; //add E.g. (1/3), (2/3), (3/3) to end of split tweets
			splitArray.set(i, withTweetCount);
		}

		return splitArray;
	}

	@Override
	public void welcomeSplash(PokerPlayer user) {
		System.out.println("@" + user.getName() + " Welcome to SBF poker bot, you are now in a game with "
				+ Constants.NUM_BOT_PLAYERS + " AI. Tweet #Bye at our handle at any time to leave. "
				+ "Please wait...");
	}

	@Override
	public void postStatus(String str) {
		System.out.println(str);

	}

	@Override
	public String getUserResponse(String str) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	@Override
	public void showChipCounts(PokerPlayer[] user) {
		String str = "@" + user[0].getName() + " has " + user[0].getBalance() + " chip(s)";
		for(int i = 1; i < user.length; i++) {
			String append = " | " + user[i].getName() + " has " + user[i].getBalance() + " chip(s)";
			str += append;
		}
		System.out.println(str);
	}

	@Override
	public void showDealtHandAskDiscard(PokerPlayer[] user) {
		System.out.println("@" + user[0].getName() + " You've been dealt: " + user[0].getHandAsString()
				+ " | Which (up to 3) card(s) would you like to discard (e.g., None, 0 1, 2 3 4)");
	}

	@Override
	public void showUpdatedHandAskFold(PokerPlayer[] user) {
		System.out.println("@" + user[0].getName() + " Your hand now looks like: " + user[0].getHandAsString());
	}

	@Override
	public void sayWin(PokerPlayer user, PokerPlayer winner, GameStats stats) {
		System.out.println("@" + user.getName() + " " + winner.getName() + " wins " + stats.getPot() + " chip(s)!");

	}

	@Override
	public void setLatestSearch(Date date){

	}

	@Override
	public ArrayList<String> getDiscardResponse(PokerPlayer user) {
		while(true){
			ArrayList<String> response = new ArrayList<String>();

			String input = getUserResponse(user.getName());

			input = input.replaceAll("\\s+","");
			StringTokenizer counter = new StringTokenizer(input);

			if(input.contains("#Bye")){
				response.add("#Bye");
				return response;
			}
			else if(input.contains("None")) {
				response.add("None");
				return response;
			}
			else if(counter.countTokens() <= 3 && input.matches("[0-4]+")) {
				for(int i = 0; i < input.length(); i++) {
					response.add(String.valueOf(input.charAt(i)));
				}
				return response;
			}
			else {
				System.out.println("@" + user.getName() + " Your response was not formatted correctly! Please try again. "
						+ "Check our account description for info on our formatting system.");
			}
		}
	}

	@Override
	public ArrayList<String> getYesOrNoResponse(PokerPlayer user) {
		while(true){
			ArrayList<String> response = new ArrayList<String>();

			String input = getUserResponse(user.getName());

			input = input.replaceAll("\\s+","");
			StringTokenizer counter = new StringTokenizer(input);

			if(input.contains("#Bye")){
				response.add("#Bye");
				return response;
			}
			else if(counter.countTokens() >= 1 && (input.matches("y") || input.matches("n") || input.matches("yes") || input.matches("no"))) {
				response.add(String.valueOf(input.charAt(0)));
				return response;
			}
			else {
				System.out.println("@" + user.getName() + " Your response was not formatted correctly! Please try again. "
						+ "Check our account description for info on our formatting system.");
			}
		}
	}

	@Override
	public String getBetResponse(PokerPlayer user) {
		while(true){
			String response = new String();

			String input = getUserResponse(user.getName());

			if(input.contains("#Bye")){
				return "#Bye";
			}
			else if(input.contains("Fold")){
				return "Fold";
			}
			else if(input.matches("[0-9]+")) {
				response = input;
				return response;
			}
			else {
				System.out.println("@" + user.getName() + " Invalid response.");
			}
		}
	}

	@Override
	public void showFinalHands(PokerPlayer[] user) {
		String str = new String("@" + user[0].getName() + " Your's: "  + user[0].getHandAsString());
		for(int i = 1; i < user.length; i++){
			if(!user[i].hasFolded()){
				str += " | " + user[i].getName() + ": " + user[i].getHandAsString();
			}
		}
		postStatus(str);
	}

	@Override
	public ArrayList<String> runSearch(String str) throws TwitterException {
		return null;
	}
}
