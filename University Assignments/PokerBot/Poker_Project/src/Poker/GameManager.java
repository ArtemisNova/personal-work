package Poker;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameManager{
	private ArrayList<GameInterface> gameList;
	private Interaction interaction;

	public GameManager(Interaction interaction){
		gameList = new ArrayList<GameInterface>();
		this.interaction = interaction;
	}

	public void addGame(GameInterface game){
		gameList.add(game);
	}

	public void removeGame(GameInterface game){
		gameList.remove(game);
	}

	public Interaction getInteraction(){
		return interaction;
	}

	public void run(){
		while(true){
			int size = gameList.size();
			for(int i = 0; i < size; i++){
				gameList.get(i).update();
				if(!Constants.TEST_MODE){
					try {
						TimeUnit.SECONDS.sleep(Constants.UPDATE_DELAY_SECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(!Constants.TEST_MODE && gameList.size() != size){
					break;
				}else if(gameList.isEmpty()){
					return;
				}
			}
		}
	}
}

