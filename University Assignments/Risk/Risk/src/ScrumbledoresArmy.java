import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// put your code here

public class ScrumbledoresArmy implements Bot {
	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	// So you can use player.getNumUnits() but you can't use player.addUnits(10000), for example
	
	private BoardAPI board;
	private PlayerAPI player;
	private int[] Threats = new int[GameData.NUM_COUNTRIES];
	private boolean[] owned = new boolean[GameData.NUM_COUNTRIES];
	private boolean[] chokePoint = new boolean[GameData.NUM_COUNTRIES];
	// array records pairs of territories that are major choke points between continents, eg indonesia and siam
	private int[][] chokePoints = {{8,22}, {4,14}, {7, 32}, {34,37},  {23, 31}};
	private ArrayList<Integer> connected = new ArrayList<Integer>();
	
	private static ArrayList<Integer> countriesOwned = new ArrayList<Integer>();
	int[] territoryDesirability = new int[GameData.NUM_COUNTRIES]; // reward points ranking system starting at 0,countries gain points for different attributes, most desirable gets chosen as territory to place units
	
	
	ScrumbledoresArmy (BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;	
		player = inPlayer;
		// put your code here
		return;
	}
	
	
	public String getName () {
		String command = "";
		// put your code here
		command = "BOT- Scrumbledore's Army";
		return(command);
	}

	public String getReinforcement () {
		String command = "";
		
		getOwned();
		countriesOwned.clear();
		initializeCountriesOwned();
		
		getThreats();
			
		ReinforcementsTerritoriesPerContinent();
		System.out.println("1:");
		for(int i=0; i<GameData.NUM_COUNTRIES; i++){
			System.out.println(i + ": " + territoryDesirability[i]);
		}
		
		
		ReinforcementsThreatLevel();
		System.out.println("2:");
		for(int i=0; i<GameData.NUM_COUNTRIES; i++){
			System.out.println(i + ": " + territoryDesirability[i]);
		}
		
		
		ReinforcementsChokePoints();
		System.out.println("3:");
		for(int i=0; i<GameData.NUM_COUNTRIES; i++){
			System.out.println(i + ": " + territoryDesirability[i]);
		}
		
		/*
		ReinforcementsAdjacentCountries();
		System.out.println("4:");
		for(int i=0; i<GameData.NUM_COUNTRIES; i++){
			System.out.println(i + ": " + territoryDesirability[i]);
		}
		*/
		
		ReinforcementsSortCountriesByDesirability();
		System.out.println("5:");
		for(int i=0; i<GameData.NUM_COUNTRIES; i++){
			System.out.println(i + ": " + territoryDesirability[i]);
		}
		

		command = GameData.COUNTRY_NAMES[countriesOwned.get(0)]; 
		command = command.replaceAll("\\s", "");
		command += " 3";
		
		countriesOwned.clear();
		return(command);
		
	}
	
	private void ReinforcementsTerritoriesPerContinent(){	
		int[] continentCount = new int[GameData.NUM_CONTINENTS]; //North America, Europe, Asia, Australia, South America, Africa - number of countries in each continent
		
		//Gets territories per continents
		for(int i=0; i<GameData.NUM_CONTINENTS; i++){
			for(int j=0; j<GameData.CONTINENT_COUNTRIES[i].length; j++){
				if(owned[GameData.CONTINENT_COUNTRIES[i][j]]){
					if(i == 0){
						continentCount[0]++; //North America
					}
					else if(i == 1){
						continentCount[1]++; //Europe
					}
					else if(i == 2){
						continentCount[2]++; //Asia
					}
					else if(i == 3){
						continentCount[3]++; //Australia
					}
					else if(i == 4){
						continentCount[4]++; //South America
					}
					else if(i == 5){
						continentCount[5]++; //Africa
					}
				}
			}
		}
		
		int bestContinent = 0;
		
		for(int i=1; i<GameData.NUM_CONTINENTS; i++){
			if(continentCount[i] > continentCount[bestContinent]){
				bestContinent = i;
			}
		}
		
		int secondBest;
		
		if(bestContinent == 0){
			secondBest = 1;
		}
		else{
			secondBest = 0;
		}
		
		for(int i=0; i<GameData.NUM_CONTINENTS; i++){
			if(i != bestContinent){
				if(continentCount[i] >= continentCount[secondBest]){
					secondBest = i;
				}
			}
		}
		
		int thirdBest;
		
		if(bestContinent == 0 || secondBest == 0){
			if(bestContinent == 1 || secondBest == 1){
				thirdBest = 2;
			}
			else{
				thirdBest = 1;
			}
		}
		else{
			thirdBest = 0;
		}
		
		for(int i=0; i<GameData.NUM_CONTINENTS; i++){
			if(i != bestContinent && i != secondBest){
				if(continentCount[i] >= continentCount[thirdBest]){
					thirdBest = i;
				}
			}
		}
		
		//20 reward points for each country on best continent
		for(int i=0; i<GameData.CONTINENT_COUNTRIES[bestContinent].length; i++){
			if(owned[GameData.CONTINENT_COUNTRIES[bestContinent][i]]){
				territoryDesirability[GameData.CONTINENT_COUNTRIES[bestContinent][i]] += 20;
			}
		}
		//15 reward points for each country on second best continent
		for(int i=0; i<GameData.CONTINENT_COUNTRIES[secondBest].length; i++){
			if(owned[GameData.CONTINENT_COUNTRIES[secondBest][i]]){
				territoryDesirability[GameData.CONTINENT_COUNTRIES[secondBest][i]] += 15;
			}
		}
		//10 reward points for each country on third best continent
		for(int i=0; i<GameData.CONTINENT_COUNTRIES[thirdBest].length; i++){
			if(owned[GameData.CONTINENT_COUNTRIES[thirdBest][i]]){
				territoryDesirability[GameData.CONTINENT_COUNTRIES[thirdBest][i]] += 10;
			}
		}		
		
		
		//bonus points for Australia
		if(continentCount[3] >= 2 ){
			for(int i=0; i<GameData.CONTINENT_COUNTRIES[3].length; i++){
				if(owned[GameData.CONTINENT_COUNTRIES[3][i]]){
					territoryDesirability[GameData.CONTINENT_COUNTRIES[3][i]] += 20;
				}
			}
		}
		
		//bonus points for South America
		if(continentCount[4] >= 2){
			for(int i=0; i<GameData.CONTINENT_COUNTRIES[4].length; i++){
				if(owned[GameData.CONTINENT_COUNTRIES[4][i]]){
					territoryDesirability[GameData.CONTINENT_COUNTRIES[4][i]] += 15;
				}
			}
		}
	}
	
	private void initializeCountriesOwned(){
		//initializing countriesOwned ArrayList
		int count = 0;
		

		
		for(int i=0; i<GameData.NUM_COUNTRIES; i++){
			if(owned[i]){
				countriesOwned.add(i);
				count++;
			}
		}
	}
	
	private void ReinforcementsThreatLevel(){
		int temp = 0;
		
		//sorting countriesOwned according to ThreatLevel
		for(int i=0; i<countriesOwned.size(); i++){
			for(int j=1; j<countriesOwned.size()-1; j++){
				if(Threats[countriesOwned.get(j-1)] > Threats[countriesOwned.get(j)]){
					temp = countriesOwned.get(j-1);
					countriesOwned.set(j-1, countriesOwned.get(j));
					countriesOwned.set(j, temp);
				}
			}
		}
		
		int rewardPoints = 10;
		
		for(int i=0; i<countriesOwned.size(); i++){
			if(rewardPoints > 0){
				territoryDesirability[countriesOwned.get(i)] += rewardPoints;
				rewardPoints -= 2;
			}
			
		}
	}
	
	private void ReinforcementsChokePoints(){
		for(int i=0; i<GameData.NUM_COUNTRIES; i++){
			if(owned[i]){
				if(isChokePoint(i)){
					territoryDesirability[i] += 20;
				}
			}
		}
	}
	
	private void ReinforcementsAdjacentCountries(){
		for(int i=0; i<GameData.NUM_COUNTRIES; i++){
			if(owned[i]){
				for(int j=0; j<GameData.ADJACENT[i].length; j++){
					if(owned[GameData.ADJACENT[i][j]]){
						territoryDesirability[i] += 15;
						territoryDesirability[j] += 15;
					}
				}
			}
		}
	}

	private	void ReinforcementsSortCountriesByDesirability(){
		int temp = 0;
		
		System.out.println("before:" + countriesOwned);
		
		//sorting countriesOwned according to desirability
		for(int i=0; i<countriesOwned.size(); i++){
			for(int j=1; j<countriesOwned.size()-1; j++){
				if(territoryDesirability[countriesOwned.get(j-1)] < territoryDesirability[countriesOwned.get(j)]){
					temp = countriesOwned.get(j-1);
					countriesOwned.set(j-1, countriesOwned.get(j));
					countriesOwned.set(j, temp);
				}
			}
		}
		
		System.out.println("after:" + countriesOwned);
	}
	
	public String getPlacement (int forPlayer) {
		String command = "";
		command = GameData.COUNTRY_NAMES[(int)(Math.random() * GameData.NUM_COUNTRIES)];
		command = command.replaceAll("\\s", "");
		return(command);	
	}
	
	
	
	public String getCardExchange () {
		String command = "";
		
		int[] cardIDs = UI.getInsigniaIds();
		
		if(Deck.isASet()==true){
			
		}
		
		
		
		
		
		
		
		command = "skip";
		return(command);
	}

	public String getBattle () {
		String command = "";
		// put your code here
		command = "skip";
		return(command);
	}

	public String getDefence (int countryId) {
		String command = "";
		
		// bot will always attack with the maximum number of units
		// either 2 or 1 if it doesn't have 2
		if(board.getNumUnits(countryId) > 1){
			command = "2";
		}
		else{
			command = "1";
		}
		
		return(command);
	}
	
	private void getOwned(){
		
		for(int i = 0; i < GameData.NUM_COUNTRIES; ++i){
			if(board.getOccupier(i) == player.getId()){
				owned[i] = true;
			}
		}
	}
	
	
	private void getConnected(int countryID){
		connected = null; // empty arrayList for new country
		
		for(int i = 0; i < GameData.NUM_COUNTRIES; ++i){
			if(board.isConnected(countryID, i) && i != countryID){
				connected.add(i);
			}
		}
	}
	
	private boolean isChokePoint(int countryID){
		
		boolean chokePoint = false;
		
		for(int i = 0; i < chokePoints.length; ++i){
			if( chokePoints[i][0] == countryID || chokePoints[i][1] == countryID){
				chokePoint = true;
			}
		}
		
		System.out.println("choke point:" + countryID + ", " + chokePoint);
		
		return chokePoint;
	}
	
	
	
	
private int getAdjacentThreats(int countryID){
		
		int threats = 0;
		int enemyPlayer;
		
		// find enemy player's ID to check for their units
		if(player.getId() == 0){
			enemyPlayer = 1;
		}
		else{
			enemyPlayer = 0;
		}
		
		// check all adjacent terrs to owned one to find threats
		for(int j = 0; j < GameData.ADJACENT[countryID].length; ++j){
			
			int temp = GameData.ADJACENT[countryID][j];
			
			if(board.getOccupier(temp) == enemyPlayer){ // if adjacent territory is owned by other player
				if(board.getNumUnits(temp) > 1){
					threats += board.getNumUnits(temp);
				}
			}
		}
		
		return threats; // number of enemy units bordering country returned
	}
	
/*
	private int getAdjacentThreats(int countryID, int enemyPlayer){
		
		int threats = 0;
		
		// check all adjacent terrs to owned one to find threats
		for(int j = 0; j < GameData.ADJACENT[countryID].length; ++j){
			
			if(board.getOccupier(j) == enemyPlayer){ // if adjacent territory is owned by other player
				threats += board.getNumUnits(GameData.ADJACENT[countryID][j]);
			}
		}
		
		return threats; // number of enemy units bordering country returned
	}
	*/

	private void getThreats(){
		// find countries owned by bot
		for(int i = 0; i < GameData.NUM_COUNTRIES; ++i){
			if(board.getOccupier(i) == player.getId()){
				
				/* threat level is num of adjacent enemy units minus current
				 * number of units on owned territory
				 * therefore a country with more units is technically less threatened
				 */
				Threats[i] = getAdjacentThreats(i) - board.getNumUnits(i);
			}
		}
	}
	
	
	public String getMoveIn (int attackCountryId) {
		String command = "";
		// put your code here
		command = "0";
		return(command);
	}


	@Override
	public String getFortify() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	public String getFortify () {
		String command = "";
		int mostThreatened;
		int i;
		int largest = 0;
		int numUnits;
		int temp;
		
		Threats[0] = 0;
		getOwned();
		getThreats();
		mostThreatened = 0;
		
		// loop to find most threatened country
		for(i = 1; i < GameData.NUM_COUNTRIES; ++i){
			if(owned[i]){
				if(Threats[i] > Threats[mostThreatened]){
					mostThreatened = i;
				}
			}
		}
		
		getConnected(mostThreatened);
		largest = connected.get(0);
		
		// loop to find connected country with most units
		for(i = 1; i < connected.size(); ++i){
			if(board.getNumUnits(connected.get(i)) > board.getNumUnits(largest)){
				largest = connected.get(i);
			}
		}
		
		numUnits = board.getNumUnits(largest);
		
		if(numUnits < 2){
			command = "skip";
		}
		else if((temp = numUnits - 3) > 0){
			command = GameData.COUNTRY_NAMES[mostThreatened] + " " + GameData.COUNTRY_NAMES[largest] + " " + temp;
		}
		else{
			temp = numUnits / 2;
			command = GameData.COUNTRY_NAMES[mostThreatened] + " " + GameData.COUNTRY_NAMES[largest] + " " + temp;
		}
		
		return(command);
	}
*/
}
