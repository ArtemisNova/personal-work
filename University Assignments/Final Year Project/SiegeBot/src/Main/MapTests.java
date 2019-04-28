package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import static Main.Constants.*;

public class MapTests {
    private FileInputStream dataFile;
    private String currentMap = null;
    private String currentWinner = null;
    private String currentObjLocation = null;
    private Hashtable<String, double[]> winRates = new Hashtable<>();
    private ArrayList<Object[]> mapStats = new ArrayList<>();
    private ArrayList<Object[]> locationStats = new ArrayList<>();

    public MapTests(String dataPath) throws FileNotFoundException {
        dataFile = new FileInputStream(new File(dataPath));
    }

    public void loadWinRates(String filePath) throws FileNotFoundException {
        FileInputStream data = new FileInputStream(new File(filePath));
        Scanner scanner = new Scanner(data);

        while(scanner.hasNextLine()){
            String current = scanner.nextLine();
            String[] splitLine = current.split(",");
            double defender = Double.parseDouble(splitLine[1].split(":")[1].replace("%",""));
            double attacker = Double.parseDouble(splitLine[2].split(":")[1].replace("%",""));
            winRates.put(splitLine[0], new double[]{defender, attacker});
        }
    }

    // method to grab 10 lines from a stripped dataFile, which will be a full round of 5v5
    private void getFullRound(Scanner scanner){
        String[] currentRoundStats = new String[sizeOfTeam * 2];

        for(int i = 0; i < currentRoundStats.length && scanner.hasNextLine(); i++){
            currentRoundStats[i] = scanner.nextLine();
        }

        currentMap = currentRoundStats[0].split(",")[map];
        currentWinner = currentRoundStats[0].split(",")[winningRole];
        currentObjLocation = currentRoundStats[0].split(",")[objectiveLocation];
    }

    private void makePrediction(){
        double[] currentWinRates = winRates.get(currentMap);
        String predictedWinner;

        if(currentWinRates[0] > currentWinRates[1])
            predictedWinner = "Defender";
        else if(currentWinRates[1] > currentWinRates[0])
            predictedWinner = "Attacker";
        else{
            System.out.println("WOT");
            return;
        }

        if(predictedWinner.equalsIgnoreCase(currentWinner))
            mapStats.add(new Object[]{currentMap, true, Math.abs(currentWinRates[0] - currentWinRates[1])});
        else
            mapStats.add(new Object[]{currentMap, false, Math.abs(currentWinRates[0] - currentWinRates[1])});

        currentWinRates = winRates.get(currentObjLocation);

        if(currentWinRates[0] > currentWinRates[1])
            predictedWinner = "Defender";
        else if(currentWinRates[1] > currentWinRates[0])
            predictedWinner = "Attacker";
        else{
            System.out.println("WOT");
            return;
        }

        if(predictedWinner.equalsIgnoreCase(currentWinner))
            locationStats.add(new Object[]{currentMap, true, Math.abs(currentWinRates[0] - currentWinRates[1])});
        else
            locationStats.add(new Object[]{currentMap, false, Math.abs(currentWinRates[0] - currentWinRates[1])});

    }

    private void sortData(){

        ArrayList<Object[]> tempList = new ArrayList<>();
        int highestValueIndex;
        System.out.println("Map Stats sorting Begin...");
        while(!mapStats.isEmpty()){
            int i;
            highestValueIndex = 0;
            for(i = 0; i < mapStats.size(); i++){
                if((double)mapStats.get(i)[2] >= (double)mapStats.get(highestValueIndex)[2]) {
                    highestValueIndex = i;
                }
            }
            tempList.add(0, mapStats.remove(highestValueIndex));
        }
        mapStats = tempList;
        tempList = new ArrayList<>();
        System.out.println("Location Stats sorting Begin...");
        while(!locationStats.isEmpty()){
            int i;
            highestValueIndex = 0;
            for(i = 0; i < locationStats.size(); i++){
                if((double)locationStats.get(i)[2] >= (double)locationStats.get(highestValueIndex)[2]) {
                    highestValueIndex = i;
                }
            }
            tempList.add(0, locationStats.remove(highestValueIndex));
        }
        locationStats = tempList;
    }


    private void evaluatePrediction(int N){
        double correctMapPrediction = 0.0, correctLocationPrediction = 0.0;
        double totalRounds = 0.0, correctRounds = 0.0;
        Object[] current;

        for(int i = 0; i < N; i++){
            current = mapStats.get(i);
            if((boolean)current[1]){
               correctRounds++;
            }
            totalRounds++;
        }

        correctMapPrediction = (correctRounds / totalRounds) * 100.0;
        System.out.println("Top " + N + " for Maps yields " + correctMapPrediction + "% correct predictions");
        totalRounds = 0.0;
        correctRounds = 0.0;

        for(int i = 0; i < N; i++){
            current = locationStats.get(i);
            if((boolean)current[1]){
                correctRounds++;
            }
            totalRounds++;
        }

        correctLocationPrediction = (correctRounds / totalRounds) * 100.0;
        System.out.println("Top " + N + " for Objective Location yields " + correctLocationPrediction + "% correct predictions");
    }


    private void executeMapTests(){
        Scanner dataScanner = new Scanner(dataFile);

        while(dataScanner.hasNextLine()){
            getFullRound(dataScanner);
            makePrediction();
        }

        sortData();
        evaluatePrediction(100);
        evaluatePrediction(500);
        evaluatePrediction(1000);
        evaluatePrediction(5000);
        evaluatePrediction(10000);

    }


    public static void main(String[] args){

        MapTests test;
        try{
            test = new MapTests("./src/Resources/stripped_file.csv");
            test.loadWinRates("./src/Resources/output.txt");
        }catch(FileNotFoundException e) {
            System.out.println("File not found.");
            return;
        }

        try {
            test.executeMapTests();
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Rounds have not been stripped or data file has not been sorted\nPlease sort the data using a program such as excel or libre calc\nSort by matchid and then round num");
        }
    }
}
