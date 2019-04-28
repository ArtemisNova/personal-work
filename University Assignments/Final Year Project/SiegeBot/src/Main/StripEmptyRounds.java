package Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static Main.Constants.*;


/**
 * Created by scott on 08/10/17.
 *
 * Class to strip rounds from the data set which don't have a
 * full set of 10 players
 */
public class StripEmptyRounds {

    public void StripRounds(String input) throws FileNotFoundException{
        FileInputStream dataSet = new FileInputStream(input);
        PrintWriter output = new PrintWriter(strippedFile);
        
        ArrayList<String> operatorsInRound = new ArrayList<>();
        Scanner dataScanner = new Scanner(dataSet);
        int currentRound = 0;
        long currentGame = 0;
        int numRounds = 0;
        int numFullRounds = 0;

        while(dataScanner.hasNextLine()){
            String line = dataScanner.nextLine();
            String[] splitLine = line.split(",|;");

            if(currentGame == 0 && currentRound == 0){
                currentGame = Long.parseLong(splitLine[matchID]);
                currentRound = Integer.parseInt(splitLine[roundNum]);
            }

            if(Long.parseLong(splitLine[matchID]) == currentGame) {
                if (Integer.parseInt(splitLine[roundNum]) != currentRound) {
                    // save data lines if 5 v 5 round
                    if (operatorsInRound.size() == sizeOfTeam * 2) {
                        numFullRounds++;

                        for (String lineToWrite : operatorsInRound) {
                            output.println(lineToWrite);
                        }
                    }
                    numRounds++;

                    currentRound = Integer.parseInt(splitLine[roundNum]);
                    operatorsInRound = new ArrayList<>();
                }
            }
            else{
                currentGame = Long.parseLong(splitLine[matchID]);
                if (operatorsInRound.size() == sizeOfTeam * 2) {
                    numFullRounds++;

                    for (String lineToWrite : operatorsInRound) {
                        output.println(lineToWrite);
                    }
                }
                numRounds++;

                currentRound = Integer.parseInt(splitLine[roundNum]);
                operatorsInRound = new ArrayList<>();
            }

            operatorsInRound.add(line);
        }

        try {
            dataSet.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        output.flush();
        output.close();

        try{
            dataSet.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }

        double x = (double)numFullRounds;
        double y = (double)numRounds;
        System.out.println("Number of Rounds: " + numRounds + ", Number of Full Rounds: " + numFullRounds);
        System.out.println("Percentage Rounds Eliminated: " + (100.0 - x / y * 100.0));
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter file name in resource folder: ");
        String input = in.nextLine();
        StripEmptyRounds test = new StripEmptyRounds();
        try {
            test.StripRounds(input);
        }
        catch(FileNotFoundException e){
           System.out.println("ERROR: File not Found");
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Data file has not been sorted\nPlease sort the data using a program such as excel or libre calc\nSort by matchid and then round num");
        }
    }
}
