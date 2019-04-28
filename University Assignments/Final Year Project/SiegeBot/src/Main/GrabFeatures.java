package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;

import static Main.Constants.*;

public class GrabFeatures {

    private FileInputStream dataFile;
    private PrintWriter outputWriter;
    private Hashtable<String, Double> operatorWinRates;
    private int highestDefenderElo = 0, highestAttackerElo = 0;
    private int top2DefenderElo = 0, top2AttackerElo = 0;
    private int totalDefenderElo = 0, totalAttackerElo = 0;
    private double totalDefenderWinRate = 0.0, totalAttackerWinRate = 0.0;
    private String[] defenderElos, attackerElos;
    private String[] currentRoundStats = new String[sizeOfTeam * 2];
    private boolean voidRound = false;
    private Hashtable<String, Integer> eloValues = new Hashtable<>();
    private String winner, objLocation;

    public GrabFeatures(String dataPath, String outputPath) throws FileNotFoundException {
        dataFile = new FileInputStream(new File(dataPath));
        outputWriter = new PrintWriter(new File(outputPath));
        operatorWinRates = new Hashtable<>();
        for(int i = 0; i < ranks.length; i++){
            eloValues.put(ranks[i], i);
        }
    }

    public void loadWinRates(){
        FileInputStream winRateData;
        Scanner scanner;

        try{
            winRateData = new FileInputStream(new File("./src/Resources/win_rates.txt"));
            scanner = new Scanner(winRateData);
        }catch(FileNotFoundException e){
            System.out.println("Win rate data not found.");
            return;
        }

        while(scanner.hasNextLine()){
            String current = scanner.nextLine();
            String[] splitLine = current.split(",|;");
            String operatorName = splitLine[0];
            Double winRate = Double.parseDouble(splitLine[3].split(":")[1]);
            operatorWinRates.put(operatorName, winRate);
        }
    }

    // method to grab 10 lines from a stripped dataFile, which will be a full round of 5v5
    private void getFullRound(Scanner scanner){
        currentRoundStats = new String[sizeOfTeam*2];
        for(int i = 0; i < currentRoundStats.length && scanner.hasNextLine(); i++){
            currentRoundStats[i] = scanner.nextLine();
        }
        winner = currentRoundStats[0].split(",|;")[winningRole];
        objLocation = currentRoundStats[0].split(",|;")[objectiveLocation].replace(" ", "_");
    }


    private void recordWinRates(){
        totalAttackerWinRate = 0.0;
        totalDefenderWinRate = 0.0;

        for(String current: currentRoundStats){
            String[] splitLine = current.split(",");
            String currentOperator = splitLine[operator];

            if(currentOperator.contains("RESERVE") && splitLine[playerRole].equals("Defender"))
                currentOperator += "-D";
            if(splitLine[playerRole].equals("Defender"))
                totalDefenderWinRate += operatorWinRates.get(currentOperator);
            else
                totalAttackerWinRate += operatorWinRates.get(currentOperator);
        }
    }

    private void recordElos(){
        defenderElos = new String[5];
        attackerElos = new String[5];
        int i = 0, j = 0;
        for(String current: currentRoundStats) {
            String eloRank = current.split(",")[skillRank];
            if (eloRank.equalsIgnoreCase("Unranked")) {
                voidRound = true;
                break;
            }
            String currentTeam = current.split(",")[playerRole];

            if(currentTeam.equalsIgnoreCase("Defender"))
                defenderElos[i++] = eloRank;
            else if(currentTeam.equalsIgnoreCase("Attacker"))
                attackerElos[j++] = eloRank;
        }
    }

    private void findHighestElo(){
        highestAttackerElo = 0;
        highestDefenderElo = 0;
        for(int i = 0; i < sizeOfTeam; i++){
            int currentDef = eloValues.get(defenderElos[i]);
            int currentAtk = eloValues.get(attackerElos[i]);

            if(currentDef > highestDefenderElo)
                highestDefenderElo = currentDef;

            if(currentAtk > highestAttackerElo)
                highestAttackerElo = currentAtk;
        }
        //System.out.println(highestDefenderElo + ", " + highestAttackerElo + ", " + voidRound);
    }

    private void findTop2Elo(){
        int topDef = 0, secondDef = 0, topAtk = 0, secondAtk = 0;
        top2DefenderElo = 0;
        top2AttackerElo = 0;

        for(int i = 0; i < sizeOfTeam; i++){
            int currentDef = eloValues.get(defenderElos[i]);
            int currentAtk = eloValues.get(attackerElos[i]);

            if(currentDef > topDef) {
                secondDef = topDef;
                topDef = currentDef;
            }
            else if(currentDef > secondDef)
                secondDef = currentDef;

            if(currentAtk > topAtk) {
                secondAtk = topAtk;
                topAtk = currentAtk;
            }
            else if(currentAtk > secondAtk)
                secondAtk = currentAtk;
        }

        top2DefenderElo = topDef + secondDef;
        top2AttackerElo = topAtk + secondAtk;
    }

    private void calculateTotalElo(){
        totalAttackerElo = 0;
        totalDefenderElo = 0;

        for(int i = 0; i < sizeOfTeam; i++){
            totalAttackerElo += eloValues.get(attackerElos[i]);
            totalDefenderElo += eloValues.get(defenderElos[i]);
        }
    }

    private int highestEloDelta(){
        return highestDefenderElo - highestAttackerElo;
    }

    private int secondEloDelta(){
        return top2DefenderElo - top2AttackerElo;
    }

    private int totalEloDelta(){
        calculateTotalElo();
        return totalDefenderElo - totalAttackerElo;
    }

    private int winRateDelta(){

        //System.out.println(totalDefenderWinRate + ", " + totalAttackerWinRate);
        int temp = (int)((totalDefenderWinRate - totalAttackerWinRate) *100.0);
        //System.out.println(temp);
        return temp;
    }

    private void writeToFile(String line){
        outputWriter.write(line);
    }

    private void setupOutputFile(){
        writeToFile("@RELATION defender\n");

        String attributes = "@ATTRIBUTE e1delta INTEGER\n";
        attributes += "@ATTRIBUTE e2delta INTEGER\n";
        attributes += "@ATTRIBUTE e5delta INTEGER\n";
        attributes += "@ATTRIBUTE winratedelta INTEGER\n";
        attributes += "@ATTRIBUTE objlocation {";

        String[] objArray = clubHouseObjectiveLocations;
        for(int i = 0; i < objArray.length; i++){
            String current = objArray[i].replace("'", "");
            current = current.replace(" ", "_");
            attributes += current;
            if(i != (objArray.length-1))
                attributes += ",";
        }

        attributes += "}\n";
        attributes += "@ATTRIBUTE result {W,L}\n";
        attributes += "@DATA\n";
        writeToFile(attributes);
    }

    private void execute(){
        setupOutputFile();
        Scanner dataScanner = new Scanner(dataFile);
        String outcome;
        loadWinRates();

        while(dataScanner.hasNextLine()){
            getFullRound(dataScanner);
            recordElos();
            if(voidRound){
                voidRound = false;
                continue;
            }
            recordWinRates();
            findHighestElo();
            findTop2Elo();
            int eloDelta = highestEloDelta();
            if(eloDelta == 0)
                continue;
            int secondEloDelta = secondEloDelta();
            int totalEloDelta = totalEloDelta();
            if(totalEloDelta == 0)
                continue;
            int winRateDelta = winRateDelta();
            if(winner.equalsIgnoreCase("Defender"))
                outcome = "W";
            else{
                outcome = "L";
            }
            writeToFile(eloDelta + "," + secondEloDelta + "," + totalEloDelta + "," + winRateDelta + "," + objLocation.replace("'", "") + "," + outcome + "\n");

        }

        outputWriter.flush();
        outputWriter.close();
        dataScanner.close();
    }

    public static void main(String[] args){
        GrabFeatures test;

        try{
            test = new GrabFeatures("./src/Resources/stripped_file.csv", "./src/Resources/data.arff");
        }catch(FileNotFoundException e){
            System.out.println("File doesn't exist");
            return;
        }

        try{
            test.execute();
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Rounds have not been stripped or data file has not been sorted\nPlease sort the data using a program such as excel or libre calc\nSort by matchid and then round num");
        }
    }

}
