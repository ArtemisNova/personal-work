package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static Main.Constants.*;

/**
 * Created by scott on 09/11/17.
 */
public class PredictRounds {

    private FileInputStream statsFile;
    private FileInputStream dataFile;
    private HashMap<String, Double> operatorWinRates;
    private ArrayList<Object[]> rounds;

    public PredictRounds(String statsFile, String dataFile){
        try {
            this.statsFile = new FileInputStream(new File("./src/Resources/" + statsFile));
            this.dataFile = new FileInputStream(new File("./src/Resources/" + dataFile));
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        operatorWinRates = new HashMap<>();
        rounds = new ArrayList<>();
    }

    public void getWinRates(){
        Scanner stats = new Scanner(statsFile);
        String currentLine, operatorName, winRate;
        String[] splitLine;
        
        while(stats.hasNextLine()){
            currentLine = stats.nextLine();
            if(currentLine.equals("Defenders Singleton:") || currentLine.equals("Attackers Singleton:")){
                stats.nextLine(); // strip empty line
                // loop through operators and get their win rates
                for(int i = 0; i < numOfOperatorsPerSide; i++){
                    currentLine = stats.nextLine();
                    splitLine = currentLine.split(":");
                    operatorName = (splitLine[0].split(","))[0];
                    winRate = splitLine[splitLine.length-1].replaceAll(" ", "");
                    operatorWinRates.put(operatorName, Double.parseDouble(winRate));
                }
            }
        }
    }

    public void runPrediction(){
        Scanner data = new Scanner(dataFile);
        int numPredictions = 0;
        int numCorrectPredictions = 0;
        boolean correctPrediction;
        String operatorName, winner, currentTeam, predictedWinner;
        String[] splitLine;
        ArrayList<String> defenders, attackers;
        double defenderScore, attackerScore, accuracy;

        defenders = new ArrayList<>();
        attackers = new ArrayList<>();

        while(data.hasNextLine()){
            splitLine = (data.nextLine()).split(",");
            operatorName = splitLine[operator].replaceAll(" ", "");
            currentTeam = splitLine[playerRole].replaceAll(" ", "");

            if(currentTeam.equals("Defender")){
                defenders.add(operatorName);
            }
            else{
               attackers.add(operatorName);
            }
            //System.out.println(attackers.size() + " " + defenders.size());
            if((attackers.size() == sizeOfTeam && defenders.size() == sizeOfTeam)){
                winner = splitLine[winningRole];
                defenderScore = 0.0;
                attackerScore = 0.0;

                for(int i = 0; i < sizeOfTeam; i++){
                    defenderScore += operatorWinRates.get(defenders.get(i));
                    attackerScore += operatorWinRates.get(attackers.get(i));
                }
                
                if(attackerScore > defenderScore){
                    predictedWinner = "Attacker";
                }
                else if(attackerScore < defenderScore){
                    predictedWinner = "Defender";
                }
                else{ // coin toss if probabilities are the same
                    Random rand = new Random();
                    int coinToss = rand.nextInt(1);

                    if(coinToss < 1){
                        predictedWinner = "Defender";
                    }
                    else{
                        predictedWinner = "Attacker";
                    }
                } 

                if((correctPrediction = winner.equals(predictedWinner))){
                    numCorrectPredictions += 1;
                }
                Object[] round = {correctPrediction, Math.abs(defenderScore - attackerScore)};
                rounds.add(round);
                numPredictions += 1;
                attackers = new ArrayList<>();
                defenders = new ArrayList<>();
            }
        }
        accuracy = (double)numCorrectPredictions / (double)numPredictions * 100.0;
        System.out.println("Num Rounds: " + numPredictions + "\nNum Correct Predictions: " + numCorrectPredictions
        + "\nAccuracy: " + accuracy + "%");
    }

    public void sortRounds(){
        ArrayList<Object[]> localSortedRounds = new ArrayList<>();
        double currentHighestWinRate = 0.0;
        int highestIndex = 0, i = 0;

        while(rounds.size() > 0){
            for(Object[] current: rounds){
                if((double)current[1] > currentHighestWinRate){
                    currentHighestWinRate = (double)current[1];
                    highestIndex = i;
                }
                i++;
            }
            localSortedRounds.add(rounds.remove(highestIndex));
            i = 0;
            highestIndex = 0;
            currentHighestWinRate = 0.0;
        }
        rounds = localSortedRounds;
        System.out.println("Done, " + rounds.size());
    }

    public void getPredictionForTopN(int N){
        int numCorrect = 0;
        double correctPredictions;

        for(int i = 0; i < N; i++){
            if((boolean)rounds.get(i)[0]){
                numCorrect++;
            }
        }
        correctPredictions = (double)numCorrect / (double)N * 100;

        System.out.println("Top " + N + " rounds gives " + correctPredictions + "% correct predictions");
    }

    public static void main(String[] args){

        String statsFile;
        String dataFile;
        Scanner in = new Scanner(System.in);
        PredictRounds run;

        System.out.println("Enter name of stats file: ");
        statsFile = in.nextLine();
        System.out.println("Enter the name of the sample data file: ");
        dataFile = in.nextLine();
        run = new PredictRounds(statsFile, dataFile);
        try {
            run.getWinRates();
            run.runPrediction();
            run.sortRounds();
            run.getPredictionForTopN(100);
            run.getPredictionForTopN(500);
            run.getPredictionForTopN(1000);
            run.getPredictionForTopN(5000);
            run.getPredictionForTopN(10000);
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Rounds have not been stripped or data file has not been sorted\nPlease sort the data using a program such as excel or libre calc\nSort by matchid and then round num");
        }

    }
}
