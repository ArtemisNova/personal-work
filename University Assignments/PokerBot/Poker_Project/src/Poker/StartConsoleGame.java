package Poker;

import java.util.Scanner;

public class StartConsoleGame implements GameInterface{
	private GameManager manager;
	
	public StartConsoleGame(GameManager manager){
		this.manager = manager;
	}
	
	@Override
	public void update(){
		Scanner scanner = new Scanner(System.in);
		System.out.print("What is your name? ");
		String name = scanner.next();
		Game game = new Game(manager);
		game.stats.getPlayers()[0].setName(name);
		
		manager.addGame(game);
		manager.removeGame(this);
	}
}
