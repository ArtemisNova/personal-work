package Poker;
import java.util.concurrent.ThreadLocalRandom;
/*
	Created by Adam on 25/4/17
	Assumes an array of players, and their balances
*/

public class Bot {
	private GameStats statsReference;
	private float tolerances[][] = {{0.35f, 0.6f, 0.7f}, {0.75f, 0.5f, 0.4f}, {0.85f, 0.6f, 0.55f}};
	private int call[] = {10, 30, 50, 60, 100, 100, 100, 100, 100, 100};
	private int raise[] = {10, 30, 40, 50, 100, 100, 100, 100, 100, 100};
	private float playStyle;
	public Bot(GameStats stats, String style){
		statsReference = stats;
		switch(style){
			case "conservative":
				playStyle = 0.85f;
				break;
			case "moderate": 
				playStyle = 1;
				break;
			case "aggressive":
				playStyle = 1.15f;
				break;
			default:
				playStyle = 1;
		}
	}

	//players are assigned a 
	public int classifyStacks(int playerPosition){
		int totalChips = Constants.TOTAL_NUM_PLAYERS*Constants.STARTING_BALANCE;
		PokerPlayer[] players = statsReference.getPlayers();
		float median = totalChips/players.length;

		if (players[playerPosition].getBalance() >= median*1.5){
			//tall
			return 2;
		} else if (players[playerPosition].getBalance() <= median*0.5){
			//short
			return 0;
		} else {
			//medium
			return 1;
		}
	}

	public int makeDecision(){
		int[] playersInHand = getPlayersInHand();
		float handTolerance = calculateTolerances(playersInHand);
		int handValue = statsReference.getPlayers()[statsReference.getCurrentPlayer()].getHand().getGameValue();
		int handAsIndex = handValue/10000000 -1;
		float callPercent = (statsReference.getContributions(statsReference.getCurrentPlayer()) + statsReference.amountToCall()) / (statsReference.getContributions(statsReference.getCurrentPlayer()) + statsReference.getPlayers()[statsReference.getCurrentPlayer()].getBalance());
		int bet = -1;
		if (callPercent < raise[handAsIndex]*playStyle*handTolerance){
			int randomNum = ThreadLocalRandom.current().nextInt(0, 100);
			if (randomNum < 40){
				int randomNum2 = ThreadLocalRandom.current().nextInt(1, 3);
				if (randomNum2 == 1){
					bet = (int)(statsReference.amountToCall() * 1.5 );
				} else {
					bet = statsReference.amountToCall()*randomNum2;
				}
				if(bet == 1){
					bet = 2;
				}else if(bet == 0){
					bet = 1;
				}
			}
		} else if (callPercent < call[handAsIndex]*playStyle*handTolerance){
			bet = statsReference.amountToCall();
		} else {
			bet = -1;
		}

		if (bet <= statsReference.getPlayers()[statsReference.getCurrentPlayer()].getBalance()){
			return bet;
		} else {
			return statsReference.getPlayers()[statsReference.getCurrentPlayer()].getBalance();
		}
	}

	public int[] getPlayersInHand(){
		PokerPlayer[] players = statsReference.getPlayers();
		int stillInHand[] = new int[players.length];
		int j=0;
		for (int i=0; i<players.length; i++){
			if (!players[i].hasFolded()){
				stillInHand[j] = i;
				j++;
			}
		}
		return stillInHand;
	}

	public float calculateTolerances(int[] playersInHand){
		int ownTolerance = classifyStacks(statsReference.getCurrentPlayer());
		float overallTolerance = 0;

		for (int i=0; i<playersInHand.length; i++){
			overallTolerance += tolerances[ownTolerance][classifyStacks(playersInHand[i])];
		}
		overallTolerance = overallTolerance/playersInHand.length;

		//normalise tolerance to [0.8,1.2]
		overallTolerance = (((overallTolerance - 0.35f)*0.4f)/0.5f)+0.8f;

		return overallTolerance;
	}


}