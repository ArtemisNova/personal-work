package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import static Main.Constants.*;

public class MapWinRates {

    private FileInputStream dataFile;
    private PrintWriter statWriter;
    private String currentMap = null;
    private String currentWinner = null;
    private String currentObjLocation = null;
    private String currentGameMode = null;
    private ArrayList<String[]> mapStats = new ArrayList<>();
    private ArrayList<String[]> objLocationStats = new ArrayList<>();

    public MapWinRates(String dataPath, String output) throws FileNotFoundException {
        dataFile = new FileInputStream(new File(dataPath));
        statWriter = new PrintWriter(new File(output));
    }

    private void getFullRound(Scanner scanner){
        String[] currentRoundStats = new String[sizeOfTeam * 2];

        for(int i = 0; i < currentRoundStats.length && scanner.hasNextLine(); i++){
            currentRoundStats[i] = scanner.nextLine();
        }

        currentMap = currentRoundStats[0].split(",|;")[map];
        currentWinner = currentRoundStats[0].split(",|;")[winningRole];
        currentObjLocation = currentRoundStats[0].split(",|;")[objectiveLocation];
        currentGameMode = currentRoundStats[0].split(",|;")[2];
    }

    private void recordRoundStats(){
        String[] mapResult = {currentMap, currentWinner};
        String[] locationResult = {currentObjLocation, currentWinner};
        mapStats.add(mapResult);
        objLocationStats.add(locationResult);
    }

    private void calculateWinRates(){
        Hashtable<String, Integer[]> winRates = new Hashtable<>();

        for(String[] current: mapStats){
            String map = current[0];
            String winner = current[1];
            if(winRates.get(map) == null){
                Integer[] teams = {0,0};
                if(winner.equalsIgnoreCase("Defender"))
                    teams[0] = 1;
                else
                    teams[1] = 1;
                winRates.put(map, teams);
            } else{
                Integer[] teams = winRates.get(map);
                if(winner.equalsIgnoreCase("Defender"))
                    teams[0] += 1;
                else
                    teams[1] += 1;
                winRates.put(map, teams);
            }
        }

        for(String[] current: objLocationStats){
            String objLocation = current[0];
            String winner = current[1];
            if(winRates.get(objLocation) == null){
                Integer[] teams = {0,0};
                if(winner.equalsIgnoreCase("Defender"))
                    teams[0] = 1;
                else
                    teams[1] = 1;
                winRates.put(objLocation, teams);
            } else{
                Integer[] teams = winRates.get(objLocation);
                if(winner.equalsIgnoreCase("Defender"))
                    teams[0] += 1;
                else
                    teams[1] += 1;
                winRates.put(objLocation, teams);
            }
        }

        Enumeration<String> keys = winRates.keys();
        while(keys.hasMoreElements()){
            String current = keys.nextElement();
            Integer[] currentData = winRates.get(current);
            int totalRounds = currentData[0] + currentData[1];
            double defenderWinRate = ((double)currentData[0] / (double)totalRounds) * 100;
            double attackerWinRate = 100.0 - defenderWinRate;
            writeToFile(current, defenderWinRate, attackerWinRate, totalRounds);
        }
    }

    private void writeToFile(String name, double defenderWinRate, double attackerWinRate, int numRounds){
       statWriter.write(name + ", Defender: " + defenderWinRate +
               "%, Attacker: " + attackerWinRate + "%, total num rounds: " + numRounds + "\n");

    }

    private void executeMapWinRates(){
        Scanner dataScanner = new Scanner(dataFile);
        while(dataScanner.hasNextLine()){
          getFullRound(dataScanner);
          recordRoundStats();
        }

        calculateWinRates();
        statWriter.flush();
        statWriter.close();
        dataScanner.close();
    }


    public static void main(String[] args){

        String dataFile, output;
        Scanner in = new Scanner(System.in);
        MapWinRates test;

        System.out.print("Enter Data Path: ");
        dataFile = in.nextLine();
        System.out.print("Enter output path: ");
        output = in.nextLine();

        try{
           test = new MapWinRates(dataFile, output);
        }catch(Exception e){
            System.out.println("ERROR: Incorrect file path entered");
            return;
        }

        try {
            test.executeMapWinRates();
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Rounds have not been stripped or data file has not been sorted\nPlease sort the data using a program such as excel or libre calc\nSort by matchid and then round num");
        }
    }
}
