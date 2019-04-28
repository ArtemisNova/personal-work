package Main;

import java.util.ArrayList;

/**
 * Created by scott on 06/10/17.
 */
public class TupleList {

    private ArrayList<OperatorTuple> tuples = new ArrayList<OperatorTuple>();

    public void addTuple(OperatorTuple tuple){
        tuples.add(tuple);
    }

    public int getTupleIndex(OperatorTuple input){
        int index;
        if(tuples.size() == 0){
            index = -1;
        }
        else {
            for (index = 0; index < tuples.size(); index++) {
                if (tuples.get(index).compareTuple(input)) {
                    break;
                }
            }

            if (index == tuples.size()) {
                index = -1;
            }
        }

        if(index == -1){
            System.out.println(input);
        }
        return index;
    }

    public OperatorTuple pop(int index){
          return tuples.remove(index);
    }

    public void updateRoundsPlayed(int index){
        tuples.get(index).countRoundPlayed();
    }

    public void updateRoundsWon(int index){
        tuples.get(index).countWin();
    }

    public OperatorTuple getTuple(int index){
        return tuples.get(index);
    }

    public int size(){
        return tuples.size();
    }
}
