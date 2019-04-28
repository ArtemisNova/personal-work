package Main;

/**
 * Created by scott on 06/10/17.
 */
public class OperatorTuple {

    private String[] operators = new String[2];
    private int numWins;
    private int numRoundsPlayed;

    public OperatorTuple(String operator1, String operator2){
        operators[0] = operator1.replaceAll("\\s+", "");
        operators[1] = operator2.replaceAll("\\s+", "");
    }

    public String getOperator(int index){
        if(index > -1 && index < 2){
            return operators[index];
        }
        else{
            return "ERROR: Index out of range.";
        }
    }

    public void countRoundPlayed(){
        numRoundsPlayed++;
    }

    public void countWin(){
        numWins++;
    }

    public int getNumRoundsPlayed(){
        return numRoundsPlayed;
    }

    public double getWinRate(){
        double ratio = 0.0;

        if(numRoundsPlayed > 0) {
            ratio = (double)numWins / (double)numRoundsPlayed;
        }

        return ratio;
    }

    public boolean compareTuple(OperatorTuple other){
        //System.out.println(this);
        //System.out.println(other);
        boolean equivalent = false;
        if(operators[0].equalsIgnoreCase(other.getOperator(0))){
            if(operators[1].equalsIgnoreCase(other.getOperator(1))){
                equivalent = true;
            }
        }
        else if(operators[0].equalsIgnoreCase(other.getOperator(1))){
            if(operators[1].equalsIgnoreCase(other.getOperator(0))){
                equivalent = true;
            }
        }
        //System.out.println(equivalent);
      return equivalent;
    }

    public String toString(){
        return operators[0] + ", " + operators[1] + ",\tNumber of Wins: " + numWins + ",\tNumber of Rounds Played: " +
                numRoundsPlayed + ",\tWin Rate: " + getWinRate();
    }
}
