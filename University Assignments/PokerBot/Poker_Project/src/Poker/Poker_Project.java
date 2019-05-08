package Poker;

public class Poker_Project{
	public static void main(String[] args){
		InteractionFactory factory = new InteractionFactory();
		
		Interaction interaction = factory.getMode();
		GameManager gameManager = new GameManager(interaction);
		GameInterface starter;
		
		
		if(Constants.TEST_MODE){
			starter = new StartConsoleGame(gameManager);
		}else{
			starter = new StartTwitterGame(gameManager, interaction);
		}
		gameManager.addGame(starter);

		gameManager.run();
	}
}
