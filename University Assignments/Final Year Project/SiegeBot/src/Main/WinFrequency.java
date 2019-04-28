package Main;

import java.io.*;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import static Main.Constants.*;

/**
 * Created by scott on 03/10/17.
 */
public class WinFrequency {

    private Scanner dataScanner = null;
    private String[][] attackerStats;
    private String[][] defenderStats;
    private TupleList attackerTuples;
    private TupleList defenderTuples;
    private String fileName;

    public WinFrequency(String file){
        // arrays to store stats from the data set
        // pos[0] = name, pos[1] = num wins, pos[2] = num rounds played
        attackerStats = new String[Constants.numOfOperatorsPerSide][3];
        defenderStats = new String[Constants.numOfOperatorsPerSide][3];
        attackerTuples = new TupleList();
        defenderTuples = new TupleList();
        file = "./src/Resources/" + file;
        fileName = file;

        try{
            FileInputStream input = new FileInputStream(strippedFile);
            dataScanner = new Scanner(input);
        }
        catch(Exception e){
            System.out.println("DATA FILE NOT FOUND");
            e.printStackTrace();
        }
    }

    // split line up so that it can be used
    public String[] splitNewLine(){
        return dataScanner.nextLine().split(",|;");
    }

    /* Method to compute the possible tuples for attackers and defenders
     * does bubble sort style of computing
     */
    public void generateTuples(){
        for(int i = 0; i < attackingOperators.length; i++){

            for(int j = i+1; j < attackingOperators.length; j++){
                OperatorTuple temp = new OperatorTuple(attackingOperators[i], attackingOperators[j]);
                attackerTuples.addTuple(temp);
            }
        }
        // add tuples for where there is multiple recruits
        attackerTuples.addTuple(new OperatorTuple("SAS-RESERVE", "SAS-RESERVE"));
        attackerTuples.addTuple(new OperatorTuple("SWAT-RESERVE", "SWAT-RESERVE"));
        attackerTuples.addTuple(new OperatorTuple("SPETSNAZ-RESERVE", "SPETSNAZ-RESERVE"));
        attackerTuples.addTuple(new OperatorTuple("GIGN-RESERVE", "GIGN-RESERVE"));
        attackerTuples.addTuple(new OperatorTuple("GSG9-RESERVE", "GSG9-RESERVE"));

        for(int i = 0; i < defendingOperators.length; i++){

            for(int j = i+1; j < defendingOperators.length; j++){
                OperatorTuple temp = new OperatorTuple(defendingOperators[i], defendingOperators[j]);
                defenderTuples.addTuple(temp);
            }
        }
        // add tuples for where there is multiple recruits
        defenderTuples.addTuple(new OperatorTuple("SAS-RESERVE", "SAS-RESERVE"));
        defenderTuples.addTuple(new OperatorTuple("SWAT-RESERVE", "SWAT-RESERVE"));
        defenderTuples.addTuple(new OperatorTuple("SPETSNAZ-RESERVE", "SPETSNAZ-RESERVE"));
        defenderTuples.addTuple(new OperatorTuple("GIGN-RESERVE", "GIGN-RESERVE"));
        defenderTuples.addTuple(new OperatorTuple("GSG9-RESERVE", "GSG9-RESERVE"));
    }


    /* Method to count frequency of wins for each operator in isolation, and tuples of operators
     * Goes through data file once, considering each round (aka a line)
     * Once full round accounted for, it then considers each side's tuples
     * Records num of rounds, num of rounds won per operator and tuple
     */
    public void computeFrequency(){
        String[] line = null;
        ArrayList<String> currentOperators = new ArrayList<>();



        // initialise arrays with operator names
        for(int i = 0; i < numOfOperatorsPerSide; i++){
            attackerStats[i][0] = attackingOperators[i];
            attackerStats[i][1] = "0";
            attackerStats[i][2] = "0";

            defenderStats[i][0] = defendingOperators[i];
            defenderStats[i][1] = "0";
            defenderStats[i][2] = "0";
        }
        
       
        while(dataScanner.hasNextLine()){
            line = splitNewLine();
            String currentOperator = line[operator];
            String currentRole = line[playerRole];
            boolean winner = (currentRole.equals(line[winningRole]));
            


              // recording win/loss
              for(int i = 0; i < numOfOperatorsPerSide; i++){

                  if(currentRole.equals("Defender")){
                      if(currentOperator.equals(defenderStats[i][0])){
                          if(winner){
                              // record win
                              defenderStats[i][1] = Integer.toString(Integer.parseInt(defenderStats[i][1]) + 1);
                          }
                          // record round played
                          defenderStats[i][2] = Integer.toString(Integer.parseInt(defenderStats[i][2]) + 1);
                          currentOperators.add(0, defenderStats[i][0]); // push defenders to the front
                          break;
                      }
                  }
                  else{
                      if(currentOperator.equals(attackerStats[i][0])){
                          if(winner){
                              // record win
                              attackerStats[i][1] = Integer.toString(Integer.parseInt(attackerStats[i][1]) + 1);
                          }
                          // record round played
                          attackerStats[i][2] = Integer.toString(Integer.parseInt(attackerStats[i][2]) + 1);
                          currentOperators.add(attackerStats[i][0]); // push attackers to the back
                          break;
                      }
                  }
              }

              
              // full round accounted for -- can now compute tuple occurences
              if(currentOperators.size() == 10){
                  int numDefenders = 5;
                  for(int i = 0; i < numDefenders; i++){

                      for(int j = i+1; j < numDefenders; j++){

                          OperatorTuple tuple = new OperatorTuple(currentOperators.get(i), currentOperators.get(j));
                          int index = defenderTuples.getTupleIndex(tuple);
                          if(line[winningRole].equals("Defender")){
                              defenderTuples.updateRoundsWon(index);
                          }
                          defenderTuples.updateRoundsPlayed(index);
                      }
                  }

                  for(int i = numDefenders; i < currentOperators.size(); i++){

                      for(int j = i+1; j < currentOperators.size(); j++){
                          OperatorTuple tuple = new OperatorTuple(currentOperators.get(i), currentOperators.get(j));
                          int index = attackerTuples.getTupleIndex(tuple);
                          if(line[winningRole].equals("Attacker")){
                              attackerTuples.updateRoundsWon(index);
                          }
                          attackerTuples.updateRoundsPlayed(index);
                      }
                  }
                  currentOperators = new ArrayList<>(); // reset array list for next round
              }
        }

        sortTuples();
        try {
            writeToFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /* Method to sort the tuples in descending order to have the best performing tuples first
     * Sorts by win rate as well as number of rounds played
     * Removes tuples with less than ten games played as not enough data to get an accurate answer
     */
    public void sortTuples(){
        int tupleCutOff = 50; // num of rounds a tuple must have played to be counted
        TupleList sorted = new TupleList();

        // defender tuples
        while(defenderTuples.size() > 0){
            int bestTupleIndex = 0;

            for(int i = 0; i < defenderTuples.size(); i++){

                if(defenderTuples.getTuple(i).getNumRoundsPlayed() < tupleCutOff){
                    defenderTuples.pop(i);
                    continue;
                }
                if (defenderTuples.getTuple(bestTupleIndex).getWinRate() < defenderTuples.getTuple(i).getWinRate()) {
                    bestTupleIndex = i;
                }
            }

            sorted.addTuple(defenderTuples.pop(bestTupleIndex));
        }

        defenderTuples = sorted;
        sorted = new TupleList();

        // defender tuples
        while(attackerTuples.size() > 0){
            int bestTupleIndex = 0;


            for(int i = 0; i < attackerTuples.size(); i++){
                if(attackerTuples.getTuple(i).getNumRoundsPlayed() < tupleCutOff){
                    attackerTuples.pop(i);
                    continue;
                }
                if (attackerTuples.getTuple(bestTupleIndex).getWinRate() < attackerTuples.getTuple(i).getWinRate()) {
                    bestTupleIndex = i;
                }
            }

            sorted.addTuple(attackerTuples.pop(bestTupleIndex));
        }
        attackerTuples = sorted;


    }

    public void writeToFile() throws IOException {
        int numOfTuplesWanted = 20; // only want top N tuples as there are too many
        String input = null;
        String path = "win_rates.txt";
        path = path.replaceAll(".csv", "");
        File output = new File(path); // where data will be saved
        FileWriter writer = null;
        DecimalFormat format = new DecimalFormat("#.###");

        try{
            writer = new FileWriter(output);
        }
        catch(IOException e){
            e.printStackTrace();
            return;
        }

        writer.write("Rainbow Six: Siege Bot Statistics\n\n");


        // defender singleton
        for(int i = 0; i < defenderStats.length; i++){
            String temp = defenderStats[i][0];
            if(temp.contains("RESERVE")){ temp += "-D";}
            input = temp + ",\t\tWins :" + defenderStats[i][1] + ",\tTotal Rounds played: " + defenderStats[i][2];
            double winRate = Double.parseDouble(defenderStats[i][1]) / Double.parseDouble(defenderStats[i][2]);
            writer.write(input + ",\tWin Rate: " + format.format(winRate) + "\n");
        }

        // defender tuples
        for(int i = 0; i < numOfTuplesWanted; i++){
            writer.write(defenderTuples.getTuple(i).toString() + "\n");
        }

        // attacker singleton
        for(int i = 0; i < attackerStats.length; i++){
            input = attackerStats[i][0] + ",\t\tWins :" + attackerStats[i][1] + ",\tTotal Rounds played: " + attackerStats[i][2];
            double winRate = Double.parseDouble(attackerStats[i][1]) / Double.parseDouble(attackerStats[i][2]);
            writer.write(input + ",\tWin Rate: " + format.format(winRate) + "\n");
        }

        // attacker tuples
        for(int i = 0; i < numOfTuplesWanted; i++) {
            writer.write(attackerTuples.getTuple(i).toString() + "\n");
        }

        writer.flush();
        writer.close();
    }

    public static void main(String[] args){
        String input;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter file name in resource folder: ");
        input = in.nextLine();
        StripEmptyRounds strip = new StripEmptyRounds();

        try {
            strip.StripRounds("./src/Resources/" + input);
        }
        catch(FileNotFoundException e){
            System.out.println("ERROR: File not Found");
        }

        WinFrequency freq = new WinFrequency(input);
        freq.generateTuples();
        freq.computeFrequency();
    }
    
}
