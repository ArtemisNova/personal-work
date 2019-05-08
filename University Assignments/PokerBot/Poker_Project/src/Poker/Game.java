package Poker;

import java.util.ArrayList;

public class Game implements GameInterface{
	public enum State{
		UNINITIALIZED,
		NEW_ROUND,
		DISCARD,
		DISCARD_RESPONSE,
		DISCARD_BOTS,
		START_BETTING,
		BET_RESPONSE,
		SHOW_HANDS,
		END_ROUND,
		PLAY_AGAIN,
		EXIT_GAME
	}

	private State state;
	private GameManager manager;
	public GameStats stats;
	private Interaction game;
	
	int count = 0;


	public Game(GameManager manager){
		this.manager = manager;
		state = State.UNINITIALIZED;
		stats = new GameStats();
		game = manager.getInteraction();
	}

	public State getState(){
		return state;
	}

	private PokerPlayer bestHand(PokerPlayer[] players){
		int winnerValue = 0;
		int winnerID = 0;
		for(int i = 0; i < players.length; i++){
			if(players[i].getHand().getGameValue() > winnerValue && !players[i].hasFolded()){
				winnerValue = players[i].getHand().getGameValue();
				winnerID = i;
			}
		}
		return players[winnerID];
	}

	private boolean checkBetting(){
		// Check to see if we have finished betting
		int maxBet = 0;
		for (int i=0; i< stats.getPlayers().length; i++){
			if (stats.potContributions[i] > maxBet){
				maxBet = stats.potContributions[i];
			}
		}
		boolean finishedBetting = true;
		for(int i = 0; i < stats.getPlayers().length; i++){
			if(!stats.getPlayers()[i].hasFolded() && stats.getPlayers()[i].getBalance() != 0){
				if(stats.potContributions[i] != maxBet){
					finishedBetting = false;
				}
			}
		}
		
		if(maxBet == 1){
			return false;
		}
	
		return finishedBetting;
	}

	@Override
	public void update() {
		ArrayList<String> response;

		switch(state){
		case UNINITIALIZED:
			// Create game
			game.welcomeSplash(stats.getPlayers()[0]);
			state = State.NEW_ROUND;
			break;

		case NEW_ROUND:
			stats.reset();
			
			game.showChipCounts(stats.getPlayers());

			// Buy in each player
			for(int i = 0; i < stats.getPlayers().length; i++){
				if(stats.getPlayers()[i].getBalance() <= 0){
					stats.getPlayers()[i].fold(true);
				}else{
					stats.setCurrentPlayer(i);
					stats.addToPot(1);

					stats.getPlayers()[i].changeBalance(-1);
				}
			}

			// Deal new hands
			for(PokerPlayer player: stats.getPlayers()){
				if(!player.hasFolded()){
					player.newHand(stats.getDeck());
				}
			}

			state = State.DISCARD;
			break;

		case DISCARD:
			// Player discard
			game.showDealtHandAskDiscard(stats.getPlayers());

			state = State.DISCARD_RESPONSE;
			break;

		case DISCARD_RESPONSE:
			// Player discard
			response = game.getDiscardResponse(stats.getPlayers()[0]);
			if(response == null){
				break;
			}
			if(response.get(0).equalsIgnoreCase("#Bye")){
				state = State.EXIT_GAME;
				break;
			}else if(!response.get(0).equalsIgnoreCase("None")){
				stats.getPlayers()[0].replaceCards(response);
			}
			game.showUpdatedHandAskFold(stats.getPlayers());

			state = State.DISCARD_BOTS;
			break;

		case DISCARD_BOTS:
			// Bot discard
			String num = new String("@" + stats.getPlayers()[0].getName() + " ");
			for(int i = 1; i < stats.getPlayers().length; i++){
				PokerPlayer bot = stats.getPlayers()[i];
				if(!bot.hasFolded()){
					num += " | " + stats.getPlayers()[i].getName() + " discarded " + bot.discard();
				}
			}
			game.postStatus(num + " (" + count++ + ")");

			state = State.START_BETTING;
			break;

		case START_BETTING:
			stats.setCurrentPlayer(0);
			game.postStatus("@" + stats.getPlayers()[0].getName() + " How much would you like to bet? Type 'Fold' to fold! " + stats.amountToCall() + " to call!" + " (" + count++ + ")");

			state = State.BET_RESPONSE;
			break;

		case BET_RESPONSE:
			// Player bet
			stats.setCurrentPlayer(0);
			if(!stats.getPlayers()[0].hasFolded() && stats.getPlayers()[0].getBalance() > 0){
				String answer = game.getBetResponse(stats.getPlayers()[0]);
				if(answer == null){
					break;
				}else if(answer.equalsIgnoreCase("Fold")){
					stats.getPlayers()[0].fold(true);
				}else if(answer.equalsIgnoreCase("#Bye")){
					state = State.EXIT_GAME;
					break;
				}else if(Integer.parseInt(answer) > stats.getPlayers()[0].getBalance()){
					game.postStatus("@" + stats.getPlayers()[0].getName() + " Invalid amount, you have " + stats.getPlayers()[0].getBalance() + " chips" + " (" + count++ + ")");
					state = State.START_BETTING;
					break;
				}else{
					stats.addToPot(Integer.parseInt(answer));
					stats.getPlayers()[0].changeBalance(-Integer.parseInt(answer));
				}
			}
			
			if(checkBetting() && !stats.getPlayers()[0].hasFolded()){
				state = State.SHOW_HANDS;
				break;
			}

			// Bots bet
			String bets = new String("@" + stats.getPlayers()[0].getName() + " ");
			
			for(int i = 1; i < stats.getPlayers().length; i++){
				stats.setCurrentPlayer(i);
				if(!stats.getPlayers()[i].hasFolded() && stats.getPlayers()[i].getBalance() != 0){
					int bet = stats.getPlayers()[i].bot.makeDecision();
					if(bet != -1){
						stats.addToPot(bet);
						stats.getPlayers()[i].changeBalance(-bet);
						bets += " | " + stats.getPlayers()[i].getName() + " bet " + bet;
					}else{
						stats.getPlayers()[i].fold(true);
						bets += " | " + stats.getPlayers()[i].getName() + " folded";
					}
					if(checkBetting()){
						game.postStatus(bets + " " + count++);
						state = State.SHOW_HANDS;
						return;
					}
				}
			}
			game.postStatus(bets + " (" + count++ + ")");

			if(!stats.getPlayers()[0].hasFolded()){
				state = State.START_BETTING;
			}else{
				state = State.BET_RESPONSE;
			}
			break;

		case SHOW_HANDS:
			// Show players hands
			game.showFinalHands(stats.getPlayers());
			PokerPlayer winner = bestHand(stats.getPlayers());
			game.sayWin(stats.getPlayers()[0], winner, stats);
			winner.changeBalance(stats.getPot());

			state = State.END_ROUND;
			break;

		case END_ROUND:
			// Kill the game if player has no chips
			if(stats.getPlayers()[0].getBalance() <= 0){
				game.postStatus("You have run out of chips!" + count++);
				state = State.EXIT_GAME;
				break;
			}

			// Ask to play another round
			game.postStatus("@" + stats.getPlayers()[0].getName() + " Would you like to play another round? (y/n)" + " (" + count++ + ")");

			state = State.PLAY_AGAIN;
			break;

		case PLAY_AGAIN:
			response = game.getYesOrNoResponse(stats.getPlayers()[0]);
			if(response == null){
				break;
			}
			if(response.get(0).equalsIgnoreCase("#Bye")){
				state = State.EXIT_GAME;
				break;
			}else if(response.get(0).equalsIgnoreCase("y")){
				state = State.NEW_ROUND;
				break;
			}
			state = State.EXIT_GAME;
			break;

		case EXIT_GAME:
			// End the game
			game.postStatus("@" + stats.getPlayers()[0].getName() + " Thanks for playing!" + " (" + count++ + ")");
			manager.removeGame(this);
			break;
		}
	}
}

