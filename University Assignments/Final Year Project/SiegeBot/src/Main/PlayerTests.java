package Main;

import com.sun.xml.internal.fastinfoset.tools.PrintTable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

import static Main.Constants.*;

public class PlayerTests {

    /* three arrays, one for each test
     * pos 0 = boolean for correct prediction
     * pos 1 = absolute difference between team values
     */

    private boolean uncertainRound = false;
    private ArrayList<Object[]> winRateTestData = new ArrayList<>();
    private ArrayList<Object[]> eloCategoricalTestData = new ArrayList<>();
    private ArrayList<Object[]> clearLevelTestData = new ArrayList<>();
    private ArrayList<Object[]> highestEloTestData = new ArrayList<>();
    private Hashtable<String, Double> attackWinRates = new Hashtable<>();
    private Hashtable<String, Double> defendWinRates = new Hashtable<>();
    private Hashtable<String, Integer> eloRankValues = new Hashtable<>();
    private Hashtable<String, Integer> currentAttackers = new Hashtable<>();
    private Hashtable<String, Integer> currentDefenders = new Hashtable<>();
    private FileInputStream dataFile;
    private int totalDefenderLevel = 0, totalAttackerLevel = 0;
    private int defenderEloNum = 0, attackerEloNum = 0;
    private String winner;

    public PlayerTests(String dataPath){
        try{
            dataFile = new FileInputStream(dataPath);
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < ranks.length; i++){
            eloRankValues.put(ranks[i], i);
        }
    }

    // method to grab 10 lines from a stripped dataFile, which will be a full round of 5v5
    private String[] getFullRound(Scanner scanner){
        String[] currentRoundStats = new String[sizeOfTeam * 2];

        for(int i = 0; i < currentRoundStats.length && scanner.hasNextLine(); i++){
            currentRoundStats[i] = scanner.nextLine();
        }
        return currentRoundStats;
    }

    private void loadWinRates(String dataPath){
        Scanner data = null;
        try {
            data = new Scanner(new File(dataPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int mode = -1;

        while(data.hasNextLine()){
            String line = data.nextLine();
            if(line.equalsIgnoreCase("ATTACKER STATS:")) {
                mode = 0;
                continue;
            }
            if(line.equalsIgnoreCase("DEFENDER STATS:")) {
                mode = 1;
                continue;
            }

            String[] splitLine = line.split("//");
            String key = splitLine[0].replaceAll(" ", "");
            Double value = Double.parseDouble(splitLine[2]) / Double.parseDouble(splitLine[1]);
            if(mode == 0)
                attackWinRates.put(key,value);
            if(mode == 1)
                defendWinRates.put(key,value);
        }
    }

    private void parseRound(String[] roundData){

        currentDefenders = new Hashtable<>();
        currentAttackers = new Hashtable<>();
        for(String rank: ranks){
            currentAttackers.put(rank, 0);
            currentDefenders.put(rank, 0);
        }
        totalDefenderLevel = 0;
        totalAttackerLevel = 0;
        defenderEloNum = 0;
        attackerEloNum = 0;

        for(String line: roundData){
            String[] splitLine = line.split(",");
            String current = splitLine[playerRole];
            winner = splitLine[winningRole];
            int eloRank = eloRankValues.get(splitLine[skillRank]);
            int level = Integer.parseInt(splitLine[clearanceLevel]);

            if(current.equalsIgnoreCase("Defender")){
                defenderEloNum += eloRank;
                totalDefenderLevel += level;
                if(currentDefenders.get(splitLine[skillRank]) == null)
                    currentDefenders.put(splitLine[skillRank], 1);
                else {
                    int temp = currentDefenders.get(splitLine[skillRank]) + 1;
                    currentDefenders.put(splitLine[skillRank], temp);
                }
            }
            else{
                attackerEloNum += eloRank;
                totalAttackerLevel += level;
                if(currentAttackers.get(splitLine[skillRank]) == null)
                    currentAttackers.put(splitLine[skillRank], 1);
                else {
                    int temp = currentAttackers.get(splitLine[skillRank]) + 1;
                    currentAttackers.put(splitLine[skillRank], temp);
                }
            }
        }

        if(currentDefenders.get("Unranked") > 0 || currentAttackers.get("Unranked") > 0)
            uncertainRound = true;
    }

    private Object[] winRateTest(){
        String defenderKey = "", attackerKey = "";
        double currentDefenderWinRate = 0.0, currentAttackerWinRate = 0.0;
        Object[] results = new Object[2];
        boolean correctPrediction;

        // loop to create a team entry into the stats dictionary
        for(String rank: ranks){
            if(currentDefenders.get(rank) > 0)
                defenderKey += rank + ":" + currentDefenders.get(rank) + ",";

            if(currentAttackers.get(rank) > 0)
                attackerKey += rank + ":" + currentAttackers.get(rank) + ",";
        }

        try {
            currentDefenderWinRate = defendWinRates.get(defenderKey);
        }catch(NullPointerException e){
            System.out.println(defenderKey);
        }
        try{
            currentAttackerWinRate = attackWinRates.get(attackerKey);
        }
        catch(NullPointerException e){
            System.out.println(defenderKey);
        }

        if(currentAttackerWinRate > currentDefenderWinRate){
            correctPrediction = ("Attacker".equalsIgnoreCase(winner));
        }
        else if(currentDefenderWinRate > currentAttackerWinRate){
            correctPrediction = ("Defender".equalsIgnoreCase(winner));
        }else{
            System.out.println("ERROR: winRatesTest()");
            correctPrediction = false;
        }

        results[0] = correctPrediction;
        results[1] = Math.abs(currentAttackerWinRate - currentDefenderWinRate);

        return results;
    }

    private Object[] highestEloTest(){
        Object[] results = new Object[2];
        boolean correctPrediction = false;
        double highestAttackerRank = 0, highestDefenderRank = 0;
        String prediction;

        for(String rank: ranks){
            if(currentAttackers.get(rank) > 0)
                highestAttackerRank = eloRankValues.get(rank);
            if(currentDefenders.get(rank) > 0)
                highestDefenderRank = eloRankValues.get(rank);
        }

        if(highestAttackerRank > highestDefenderRank)
            prediction = "Attacker";
        else if(highestDefenderRank > highestAttackerRank)
            prediction = "Defender";
        else
            return null;

        if(prediction.equalsIgnoreCase(winner))
            correctPrediction = true;

        results[0] = correctPrediction;
        results[1] = Math.abs(highestAttackerRank - highestDefenderRank);
        return results;

    }

    private Object[] categoricalEloTest(){
        Object[] results = new Object[2];
        boolean correctPrediction;

        if(defenderEloNum > attackerEloNum)
            correctPrediction = ("Defender".equalsIgnoreCase(winner));
        else if(attackerEloNum > defenderEloNum)
            correctPrediction = ("Attacker".equalsIgnoreCase(winner));
        else
            return null;

        results[0] = correctPrediction;
        results[1] = (double)Math.abs(defenderEloNum - attackerEloNum);
        return results;
    }

    private Object[] clearanceLevelTest(){
        Object[] results = new Object[2];
        boolean correctPrediction;

        if(totalDefenderLevel > totalAttackerLevel)
            correctPrediction = ("Defender".equalsIgnoreCase(winner));
        else if(totalAttackerLevel > totalDefenderLevel)
            correctPrediction = ("Attacker".equalsIgnoreCase(winner));
        else
            return null;

        results[0] = correctPrediction;
        results[1] = (double)Math.abs(totalDefenderLevel - totalAttackerLevel);
        return results;
    }

    private void sortTestData(){

        ArrayList<Object[]> tempList = new ArrayList<>();
        int highestValueIndex;
        System.out.println("Win Rate Sort Begin:");
        /*
        while(!winRateTestData.isEmpty()){
            int i;
            highestValueIndex = 0;
            for(i = 0; i < winRateTestData.size(); i++){
                if((double)winRateTestData.get(i)[1] >= (double)winRateTestData.get(highestValueIndex)[1]) {
                    highestValueIndex = i;
                }
            }
            tempList.add(0, winRateTestData.remove(highestValueIndex));
        }*/
        winRateTestData = tempList;
        tempList = new ArrayList<>();
        System.out.println("Elo Rank Sort Begin:");
        while(!eloCategoricalTestData.isEmpty()){
            int i;
            highestValueIndex = 0;
            for(i = 0; i < eloCategoricalTestData.size(); i++){
                if((double)eloCategoricalTestData.get(i)[1] >= (double)eloCategoricalTestData.get(highestValueIndex)[1]) {
                    highestValueIndex = i;
                }
            }
            tempList.add(0, eloCategoricalTestData.remove(highestValueIndex));
        }
        eloCategoricalTestData = tempList;
        tempList = new ArrayList<>();
        System.out.println("Clearance Level Sort Begin:");
        while(!clearLevelTestData.isEmpty()){
            int i;
            highestValueIndex = 0;
            for(i = 0; i < clearLevelTestData.size(); i++){
                if((double)clearLevelTestData.get(i)[1] >= (double)clearLevelTestData.get(highestValueIndex)[1]) {
                    highestValueIndex = i;
                }
            }
            tempList.add(0, clearLevelTestData.remove(highestValueIndex));
        }
        clearLevelTestData = tempList;
        tempList = new ArrayList<>();
        System.out.println("Highest Elo Rank Sort Begin: ");
        while(!highestEloTestData.isEmpty()){
            int i;
            highestValueIndex = 0;
            for(i = 0; i < highestEloTestData.size(); i++){
                if((double)highestEloTestData.get(i)[1] >= (double)highestEloTestData.get(highestValueIndex)[1]) {
                    highestValueIndex = i;
                }
            }
            tempList.add(0, highestEloTestData.remove(highestValueIndex));
        }
        highestEloTestData = tempList;
    }

    public void getPredictionForTopN(int N, ArrayList<Object[]> rounds){
        int numCorrect = 0;
        double correctPredictions;

        if(rounds.size() < N){
            System.out.println("Array not big enough");
            return;
        }

        for(int i = 0; i < N; i++){
            if((boolean)rounds.get(i)[0]){
                numCorrect++;
            }
        }
        correctPredictions = ((double)numCorrect / (double)N) * 100;

        System.out.println("Top " + N + " rounds gives " + correctPredictions + "% correct predictions");
    }

    private void executeEloTests(String outputFile){
        PrintWriter writer;
        Scanner dataScanner = new Scanner(dataFile);
        try{
            writer = new PrintWriter(outputFile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        loadWinRates("./src/Resources/elo_stats.txt");

        while(dataScanner.hasNextLine()){
            String[] round = getFullRound(dataScanner);
            parseRound(round);

            if(uncertainRound){
                uncertainRound = false;
                System.out.println("Round Skipped");
                continue;
            }
            Object[] temp;
            //if((temp = winRateTest()) != null)
              //  winRateTestData.add(temp);
            if((temp = categoricalEloTest()) != null)
                eloCategoricalTestData.add(temp);
            if((temp = highestEloTest()) != null)
                highestEloTestData.add(temp);
            if((temp = clearanceLevelTest()) != null)
                clearLevelTestData.add(temp);
        }
        System.out.println("Sorting begin:");
        sortTestData();

        /*System.out.println("\nWin Rate:");
        getPredictionForTopN(100, winRateTestData);
        getPredictionForTopN(500, winRateTestData);
        getPredictionForTopN(1000, winRateTestData);
        getPredictionForTopN(5000, winRateTestData);
        getPredictionForTopN(10000, winRateTestData);        */
        System.out.println("\nCategorical Elo Ranking:");
        getPredictionForTopN(100, eloCategoricalTestData);
        getPredictionForTopN(500, eloCategoricalTestData);
        getPredictionForTopN(1000, eloCategoricalTestData);
        getPredictionForTopN(5000, eloCategoricalTestData);
        getPredictionForTopN(10000, eloCategoricalTestData);

        System.out.println("\nClearance Level:");
        getPredictionForTopN(100, clearLevelTestData);
        getPredictionForTopN(500, clearLevelTestData);
        getPredictionForTopN(1000, clearLevelTestData);
        getPredictionForTopN(5000, clearLevelTestData);
        getPredictionForTopN(10000, clearLevelTestData);

        System.out.println("\nHighest Elo Rank:");
        getPredictionForTopN(100, highestEloTestData);
        getPredictionForTopN(500, highestEloTestData);
        getPredictionForTopN(1000, highestEloTestData);
        getPredictionForTopN(5000, highestEloTestData);
        getPredictionForTopN(10000, highestEloTestData);

    }

    public static void main(String[] args){
        System.out.println("Enter datafile path:");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        PlayerTests tests = new PlayerTests(filePath);
        try {
            tests.executeEloTests("./src/Resources/player_tests.txt");
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Rounds have not been stripped or data file has not been sorted\nPlease sort the data using a program such as excel or libre calc\nSort by matchid and then round num");
        }
    }
}
