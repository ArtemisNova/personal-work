package Poker;

//import java.util.ArrayList;

/**
 * Created by Luke on 09/04/17.
**/

public class InteractionFactory {
	public Interaction getMode(){		
		if(Constants.TEST_MODE){
			return new ConsoleInteraction();
		}
		else if(Constants.TEST_MODE == false){
	         return new TwitterInteraction();
		}
		else {
			return null;
		}
	}
	
/*	
  //How the interaction system should work
	public static void main(String[] args) {
		InteractionFactory testFactory = new InteractionFactory();
		
		//Create the correct Interaction, Twitter or Console
		Interaction testInteraction = testFactory.getMode();
		
		PokerPlayer testUser = new PokerPlayer();
		
		//Call any method in Interaction
		testInteraction.welcomeSplash(testUser);
		
		String testLongTweet = " Lorem Ipsum is simply dummy text of the printing and typesetting industry."
				+ " | Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"
				+ " | when an unknown printer took a galley of type and scrambled it to make a type specimen book."
				+ " | It has survived not only five centuries, but also the leap into electronic typesetting,"
				+ " | remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset"
				+ " | sheets containing Lorem Ipsum passages, and more recently with desktop publishing software"
				+ " | like Aldus PageMaker including versions of Lorem Ipsum.";
		
		ArrayList<String> testList = new ArrayList<String>();
		testList = testInteraction.splitStatus(testLongTweet, testUser);
		
		for(int i = 0; i < testList.size(); i++) {
			System.out.println(testList.get(i));
		}
	}
*/
}