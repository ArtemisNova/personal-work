package Poker;

/**
 * Created by Luke on 09/04/17.
**/

import twitter4j.*;
import twitter4j.auth.AccessToken;
import java.io.*;
import java.util.*;

public class TwitterInteraction implements Interaction{
	
    //Twitter App's Keys and Access Tokens
    private static String consumerKey = "N/A";
    private static String consumerSecret = "N/A";
    private static String accessToken = "N/A";
    private static String accessTokenSecret = "N/A";
    
    //Twitter and TwitterFactory instances
    private static TwitterFactory twitterFactory;
    private static Twitter twitter;
    
    private int avoidDuplicateCounter = 0;
    private Date latestSearch = new Date();
	
	public TwitterInteraction() {
		//Call to load in the access keys from the file
        getKeys();
        //Instantiate a re-usable and thread-safe factory
        twitterFactory = new TwitterFactory();
        //Instantiate a new Twitter instance
        twitter = twitterFactory.getInstance();
        //Authorise the above instances to access Twitter
        authoriseInstance();
    }

	//Read in the access_keys file to get the keys and tokens needed to post to Twitter
	private void getKeys() {
		
		BufferedReader br;
		String[] arr = new String[4];
		try {
			
			InputStream iStream = getClass().getResourceAsStream("/access_keys.txt");
			br = new BufferedReader(new InputStreamReader(iStream));
			
			for(int i=0 ; i < arr.length ; i++){
				arr[i] = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		consumerKey = arr[0];
		consumerSecret = arr[1];
		accessToken = arr[2];
		accessTokenSecret = arr[3];
	}
	
	//Authorise the Twitter instance to access the account using the account's unique keys and tokens
	private void authoriseInstance() {
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
	}
	
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
	
	//For posting generic status updates to twitter
	@Override
	public void postStatus(String str) {
		//Create a new StatusUpdate object
        StatusUpdate statusUpdate = new StatusUpdate(str);
        //Tweet the StatusUpdate
        try {
			twitter.updateStatus(statusUpdate);	
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	
	//Run generic search for tweets containing a given string and return the user id's of the senders
	public ArrayList<String> runSearch(String str) throws TwitterException {
		ArrayList<String> waitingHandles = new ArrayList<String>();
		List<Status> statusList = null;
		Query query = new Query(str);
	    QueryResult result = twitter.search(query);
	    statusList = result.getTweets();
	    
		for(int i = 0; i < statusList.size(); i++) {
			Status current = statusList.get(i);
			if(current.getCreatedAt().after(latestSearch) || current.getCreatedAt().equals(latestSearch)) {
				waitingHandles.add(statusList.get(i).getUser().getScreenName());
			}
			
		}
		return waitingHandles;
	}
	
	private void adjustDuplicate() {
		if(avoidDuplicateCounter >= 9) {
			avoidDuplicateCounter = 0;
		}
		else {
			avoidDuplicateCounter++;
		}
	}
	
	//Return a string of the text in the latest tweet for a given user
	@Override
	public String getUserResponse(String str) {
		List<Status> statusList = null;
	    try {
	        statusList = twitter.getUserTimeline("@" + str);  
	    } catch (TwitterException e) {
	        e.printStackTrace();
	    }
		return statusList.get(0).getText(); 
	}

	//Return the latest tweet object for a given user
	private Status getStatus(String str) {
		List<Status> statusList = null;
	    try {
	        statusList = twitter.getUserTimeline("@" + str);  
	    } catch (TwitterException e) {
	        e.printStackTrace();
	    }
		return statusList.get(0);
	}
	
	private void updateLatestToUser(PokerPlayer user, Status latestTweet) {
		user.setRecievedDate(latestTweet.getCreatedAt());
	}
	
	@Override
	public void welcomeSplash(PokerPlayer user) {
		StatusUpdate statusUpdate = new StatusUpdate("@" + user.getName() + " Welcome to SBF poker bot, you are now in a game with "
													+ Constants.NUM_BOT_PLAYERS + " AI. Tweet #Bye at our handle at any time to leave."
													+ " Please wait..." + avoidDuplicateCounter);
		 try {
			 twitter.updateStatus(statusUpdate);
		 } catch (TwitterException e) {
			e.printStackTrace();
		 }
		 updateLatestToUser(user, getStatus(Constants.PB_HANDLE));
		 adjustDuplicate();
	}
	
	
	@Override
	public void showChipCounts(PokerPlayer[] user) {
		String str = "@" + user[0].getName() + " has " + user[0].getBalance() + " chip(s)";
		for(int i = 1; i < user.length; i++) {
			String append = " | " + user[i].getName() + " has " + user[i].getBalance() + " chip(s)";
			str += append;
		}
		str += avoidDuplicateCounter;
		adjustDuplicate();
		
		StatusUpdate statusUpdate = new StatusUpdate(str);
		try {
			twitter.updateStatus(statusUpdate);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		updateLatestToUser(user[0], getStatus(Constants.PB_HANDLE));
	}

	@Override
	public void showDealtHandAskDiscard(PokerPlayer[] user) {
		StatusUpdate statusUpdate = new StatusUpdate("@" + user[0].getName() + " " + avoidDuplicateCounter + " You've been dealt: " + user[0].getHandAsString()
													+ " | Which (up to 3) card(s) would you like to discard (e.g., None, 0 1, 2 3 4)");
		try {
			twitter.updateStatus(statusUpdate);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		updateLatestToUser(user[0], getStatus(Constants.PB_HANDLE));
		adjustDuplicate();
	}
	
	@Override
	public void showUpdatedHandAskFold(PokerPlayer[] user) {
		StatusUpdate statusUpdate = new StatusUpdate("@" + user[0].getName() + " Your hand now looks like: " + user[0].getHandAsString());
		try {
			twitter.updateStatus(statusUpdate);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		updateLatestToUser(user[0], getStatus(Constants.PB_HANDLE));
	}
	
	@Override
	public void showFinalHands(PokerPlayer[] user) {
		String str = new String("@" + user[0].getName() + " Your's: "  + user[0].getHandAsString());
		for(int i = 1; i < user.length; i++){
			if(!user[i].hasFolded()){
				str += " | " + user[i].getName() + ": " + user[i].getHandAsString();
			}
		}
		StatusUpdate statusUpdate = new StatusUpdate(str);
		try {
			twitter.updateStatus(statusUpdate);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		updateLatestToUser(user[0], getStatus(Constants.PB_HANDLE));
	}


	@Override
	public void sayWin(PokerPlayer user, PokerPlayer winner, GameStats stats) {
		StatusUpdate statusUpdate = new StatusUpdate("@" + user.getName() + " " + winner.getName() + " wins " + stats.getPot() + " chip(s)!");
		try {
			twitter.updateStatus(statusUpdate);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		updateLatestToUser(user, getStatus(Constants.PB_HANDLE));
	}
	
	//Sends out a generic formatting error message asking the user to retry sending their response
	private void errorAskResendMessage(PokerPlayer user) {
		StatusUpdate statusUpdate = new StatusUpdate("@" + user.getName() + " Your response is not formatted correctly! Please try again. "
													+ "Check our acc. description for info on our formatting system. " + avoidDuplicateCounter);
		try {
			twitter.updateStatus(statusUpdate);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		updateLatestToUser(user, getStatus(Constants.PB_HANDLE));
		adjustDuplicate();
	}
	
	//Returns an ArrayList of Strings containing, either "#Bye", "None" or several numbers representing card positions for discarding
	//Used for getting responses to the discard request
	@Override
	public ArrayList<String> getDiscardResponse(PokerPlayer user) {
		ArrayList<String> response = new ArrayList<String>();
		List<Status> statusList = null;

	    try {
	        statusList = twitter.getUserTimeline("@" + user.getName());  
	    } catch (TwitterException e) {
	        e.printStackTrace();
	    }
	    
	    String tweetBody = statusList.get(0).getText();
	    tweetBody = tweetBody.replaceAll("\\s+","");
	    String bodySansHandle = tweetBody;
	    if(bodySansHandle.contains("@SuckyBeigeFish")) {
	    	bodySansHandle = bodySansHandle.replace("@SuckyBeigeFish", "");
	    }
    	StringTokenizer counter = new StringTokenizer(bodySansHandle);
	    
	    if(tweetBody.contains("#Bye")){
	    	response.add("#Bye");
	    	return response;
	    }
	    else if(statusList.get(0).getCreatedAt().after(user.getRecievedDate())) {
	    	
	    	if(tweetBody.contains("None") || tweetBody.contains("none")) {
	    		response.add("None");
		    	return response;
	    	}
	    	else if(counter.countTokens() < 3 && bodySansHandle.matches("[0-4]+")) {
	    		for(int i = 0; i < bodySansHandle.length(); i++) {
	    			response.add(String.valueOf(bodySansHandle.charAt(i)));
	    		}
	    		return response;
	    	}
	    	else if(tweetBody.contains(Constants.PB_HANDLE)) {
	    		errorAskResendMessage(user);
	    		return null;
	    	}
	    	else {
	    		return null;
	    	}
	    }
	    else {
	    	return null;
	    }
	}

	//Returns an ArrayList of Strings containing one value, either "#Bye", "y" or "n"
	//Used for getting responses to yes/no questions
	@Override
	public ArrayList<String> getYesOrNoResponse(PokerPlayer user) {
		ArrayList<String> response = new ArrayList<String>();
		List<Status> statusList = null;

	    try {
	        statusList = twitter.getUserTimeline("@" + user.getName());  
	    } catch (TwitterException e) {
	        e.printStackTrace();
	    }
	    
	    String tweetBody = statusList.get(0).getText();
	    tweetBody = tweetBody.replaceAll("\\s+","");
	    String bodySansHandle = tweetBody;
	    if(bodySansHandle.contains("@SuckyBeigeFish")) {
	    	bodySansHandle = bodySansHandle.replace("@SuckyBeigeFish", "");
	    }
    	StringTokenizer counter = new StringTokenizer(bodySansHandle);
	    
	    if(bodySansHandle.contains("#Bye")){
	    	response.add("#Bye");
	    	return response;
	    }
	    else if(statusList.get(0).getCreatedAt().after(user.getRecievedDate())) {
	    	
	    	if(counter.countTokens() >= 1 && (bodySansHandle.matches("y") || bodySansHandle.matches("n") || bodySansHandle.matches("yes") || bodySansHandle.matches("no"))) {
	    		response.add(String.valueOf(bodySansHandle.charAt(0)));
	    		return response;
	    	}
	    	else if(bodySansHandle.contains(Constants.PB_HANDLE)) {
	    		errorAskResendMessage(user);
	    		return null;
	    	}
	    	else {
	    		return null;
	    	}
	    }
	    else {
	    	return null;
	    }
	}
	
	
	public String getBetResponse(PokerPlayer user) {
		String response = "";
		List<Status> statusList = null;

	    try {
	        statusList = twitter.getUserTimeline("@" + user.getName());  
	    } catch (TwitterException e) {
	        e.printStackTrace();
	    }
	    
	    String tweetBody = statusList.get(0).getText();
	    tweetBody = tweetBody.replaceAll("\\s+","");
	    String bodySansHandle = tweetBody;
	    if(bodySansHandle.contains("@SuckyBeigeFish")) {
	    	bodySansHandle = bodySansHandle.replace("@SuckyBeigeFish", "");
	    }
	    
	    if(bodySansHandle.contains("#Bye")){
	    	response = "#Bye";
	    	return response;
	    }
	    else if(bodySansHandle.contains("Fold")){
	    	response = "Fold";
	    	return response;
	    }
	    else if(statusList.get(0).getCreatedAt().after(user.getRecievedDate())) {
	    	
	    	if(bodySansHandle.matches("[0-9]+")) {
	    		response = bodySansHandle;
	    		return response;
	    	}
	    	else if(tweetBody.contains(Constants.PB_HANDLE)) {
	    		errorAskResendMessage(user);
	    		return null;
	    	}
	    	else {
	    		return null;
	    	}
	    }
	    else {
	    	return null;
	    }
	}

	@Override
	public void setLatestSearch(Date date) {
		latestSearch = date;
	}
}