/*
 * Class that holds sets for training and testing, includes methods for control of a set
 */

import java.util.ArrayList;
import java.util.Random;


public class PrepNetwork {

    //Array that hold the trained result
     ArrayList<double[][]> trainD = new ArrayList<>();


     //Utilized by the training data at each iteration to raise the results
    public PrepNetwork improvement() {
        if(32 <= trainD.size()) {
            PrepNetwork set = new PrepNetwork();
            if (32 <= 0 || 0 >= trainD.size()) {
                throw new IllegalArgumentException(
                        "\nn must be > 0\nminVal must be < maxVal\n");
            }
            Integer[] ran = new Integer[35];
            Random rand = new Random();
            for (int i = 0; i < 35; i++) {
                Integer result =
                        (Integer) (rand.nextInt(trainD.size() - 0) + 0);
                ran[i] = (result);
            }
            Integer[] ids =  ran;
            // System.out.println("This is some ids" + Arrays.toString(ids));
            for(Integer i:ids) {
                set.trainD.add(new double[][]{trainD.get(i)[0], trainD.get(i)[1]});
               // set.provideInfo(this.getInput(i),this.getOutput(i));
            }
            return set;
        }else return this;
    }


}
