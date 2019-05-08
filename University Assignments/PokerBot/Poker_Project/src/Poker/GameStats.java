package Poker;

public class GameStats {
	private DeckOfCards deck;
	private PokerPlayer players[];
	private int currentPlayer;
	private int startingPlayer;
	private int pot;
	public int potContributions[];
	
	public GameStats(){
		deck = new DeckOfCards();
		players = new PokerPlayer[Constants.TOTAL_NUM_PLAYERS];
		potContributions = new int[players.length];
		// Create Player
		players[0] = new PokerPlayer(this);
		
		// Set bot names
		for(int i = 1; i < players.length; i++){
			players[i] = new PokerPlayer(this);
			players[i].setName(Constants.BOT_NAME[i-1]);
		}
		
		pot = 0;
		startingPlayer = 0;
	}
	
	public void reset(){
		deck = new DeckOfCards();
		pot = 0;
		currentPlayer = 0;
		startingPlayer = 0;
		for(int i = 0; i < potContributions.length; i++){
			potContributions[i] = 0;
		}
		
		for(PokerPlayer player: players){
			player.fold(false);
		}
	}
	
	public int getOpeningPlayer(){
		return startingPlayer;
	}
	
	public void setOpeningPlayer(int index){
		startingPlayer = index;
	}
	
	public int getCurrentPlayer(){
		return currentPlayer;
	}
	
	public void setCurrentPlayer(int player){
		currentPlayer = player;
	}
	
	// 0 is human player
	// 1 - NUM_BOT_PLAYERS are bots
	public PokerPlayer[] getPlayers(){	
		return players;
	}
	
	public DeckOfCards getDeck(){
		return deck;
	}
	
	public int getPot(){
		return pot;
	}
	
	public void addToPot(int amount){
		pot += amount;
		updateContributions(amount);
	}
	
	public void newGame(){
		pot = 0;
	}

	public void updateContributions(int amount){
		potContributions[currentPlayer] += amount;
	}

	public int getContributions(int playerIndex){
		return potContributions[playerIndex];
	}
	
	public float getCommittedStack(int playerIndex){
		int chips = getContributions(playerIndex);
		float percentageCommitted = chips/(chips + getPlayers()[playerIndex].getBalance());

		return percentageCommitted;
	}

	public int amountToCall(){
		int maxBet = 0;
		//find highest bet in pot
		for (int i=0; i<players.length; i++){
			if (potContributions[i] > maxBet){
				maxBet = potContributions[i];
			}
		}
		return maxBet - potContributions[currentPlayer];
	}
	
	public float potOdds(){
		return getPot()/amountToCall();
	}
}
