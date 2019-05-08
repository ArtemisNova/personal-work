package Poker;

import java.util.ArrayList;
import java.util.Calendar;

import twitter4j.TwitterException;

public class StartTwitterGame implements GameInterface{
	private GameManager manager;
	private Interaction interaction;

	public StartTwitterGame(GameManager manager, Interaction interaction){
		this.manager = manager;
		this.interaction = interaction;
	}

	@Override
	public void update(){
		ArrayList<String> requests = new ArrayList<String>();
		try {
			requests = interaction.runSearch("@SuckyBeigeFish #DealMeIn");
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		if(!requests.isEmpty()){
			for(String name: requests){
				Game game = new Game(manager);
				game.stats.getPlayers()[0].setName(name);

				manager.addGame(game);
				interaction.setLatestSearch(Calendar.getInstance().getTime());
				game.update();
			}
		}
	}
}
