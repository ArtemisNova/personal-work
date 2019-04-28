package Main;

import com.sun.deploy.util.SyncFileAccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

import static Main.Constants.*;

public class EloRankingStats {

    private Hashtable<String, Integer> currentAttackers;
    private Hashtable<String, Integer> currentDefenders;
    private Hashtable<String, Integer[]> defenderStats;
    private Hashtable<String, Integer[]> attackerStats;
    private FileInputStream dataFile;


    public EloRankingStats(String dataPath){
        try {
            dataFile = new FileInputStream(dataPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        defenderStats = new Hashtable<>();
        attackerStats = new Hashtable<>();
    }

    // method to grab 10 lines from a stripped dataFile, which will be a full round of 5v5
    private String[] getRoundStats(Scanner scanner){
        String[] currentRoundStats = new String[sizeOfTeam * 2];

        for(int i = 0; i < currentRoundStats.length && scanner.hasNextLine(); i++){
            currentRoundStats[i] = scanner.nextLine();
        }
        return currentRoundStats;
    }

    /*
     *  Method to record which team wins from specific Elo rankings
     */
    private void recordStats(String[] stats){

        currentDefenders = new Hashtable<>();
        currentAttackers = new Hashtable<>();
        for(String rank: ranks){
            currentAttackers.put(rank, 0);
            currentDefenders.put(rank, 0);
        }
        String winner = "";

        // loop to parse stats from the data lines
        for(String current: stats){
            String[] splitLine = current.split(",|;");
            String team = splitLine[playerRole];
            String eloRank = splitLine[skillRank];
            winner = splitLine[winningRole];

            if(team.equalsIgnoreCase("Defender")) {
                if(currentDefenders.get(eloRank) == null)
                    currentDefenders.put(eloRank, 1);
                else {
                    int temp = currentDefenders.get(eloRank) + 1;
                    currentDefenders.put(eloRank, temp);
                }
            }
            else {
                if (currentAttackers.get(eloRank) == null)
                    currentAttackers.put(eloRank, 1);
                else {
                    int temp = currentAttackers.get(eloRank) + 1;
                    currentAttackers.put(eloRank, temp);
                }
            }
        }

        String defenderKey = "";
        String attackerKey = "";
        // pos 0 holds rounds played, pos 1 holds rounds won
        Integer[] defenderOutcome = {1,0};
        Integer[] attackerOutcome = {1,0};

        if(winner.equalsIgnoreCase("Defender"))
            defenderOutcome[1] = 1;
        else
            attackerOutcome[1] = 1;


        // loop to create a team entry into the stats dictionary
        for(String rank: ranks){
            if(currentDefenders.get(rank) > 0)
                 defenderKey += rank + ":" + currentDefenders.get(rank) + ",";

            if(currentAttackers.get(rank) > 0)
                attackerKey += rank + ":" + currentAttackers.get(rank) + ",";
        }

        if(defenderStats.get(defenderKey) != null){
            Integer[] currentStats = defenderStats.get(defenderKey);
            Integer[] newStats = {currentStats[0] + defenderOutcome[0], currentStats[1] + defenderOutcome[1]};
            defenderStats.put(defenderKey, newStats);
        }
        else{
            defenderStats.put(defenderKey, defenderOutcome);
        }

        if(attackerStats.get(attackerKey) != null){
            Integer[] currentStats = attackerStats.get(attackerKey);
            Integer[] newStats = {currentStats[0] + attackerOutcome[0], currentStats[1] + attackerOutcome[1]};
            attackerStats.put(attackerKey, newStats);
        }
        else{
            attackerStats.put(attackerKey, attackerOutcome);
        }

        //System.out.println("ATTACK: " + attackerKey + "\nDEFENCE: " + defenderKey);

    }

    public void writeToFile(PrintWriter writer){
        String attackerStart = "ATTACKER STATS:\n";
        String defenderStart = "DEFENDER STATS:\n";
        Enumeration<String> attackerKeys = attackerStats.keys();
        Enumeration<String> defenderKeys = defenderStats.keys();

        writer.write(attackerStart);
        while(attackerKeys.hasMoreElements()){
            String temp = attackerKeys.nextElement();
            Integer[] values = attackerStats.get(temp);
            temp += " // " + values[0] + " // " + values[1] + "\n";
            writer.write(temp);
        }

        writer.write(defenderStart);
        while(defenderKeys.hasMoreElements()){
            String temp = defenderKeys.nextElement();
            Integer[] values = defenderStats.get(temp);
            temp += " // " + values[0] + " // " + values[1] + "\n";
            writer.write(temp);
        }
        writer.close();
    }

    /*
     * execute method to call upon the other methods to gather the stats
     */
    public void executeEloStats(String filePath){
        Scanner dataScanner = new Scanner(dataFile);
        PrintWriter writer;
        try {
            writer = new PrintWriter(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        /* Main loop of the class
         * Will grab the round stats, record
         */
        while(dataScanner.hasNextLine()){
            String[] stats = getRoundStats(dataScanner);
            recordStats(stats);
        }

        writeToFile(writer);
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        EloRankingStats test = new EloRankingStats(path);
        test.executeEloStats("./src/Resources/elo_stats.txt");

    }
}
