import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
    //Creating instance of DeepNeuralNetwork
    public static DeepNN net = new DeepNN();


    public static void main(String[] args) throws FileNotFoundException {
            //Read the data from file
            PrepNetwork data = readDataFromFile();
            //Train the network to learn from the training dataset
            //with 400 iterations
                for(int i= 0; i < 400; i++){
                    //Initialize the neural network with the data
                    net.initNetwork(data);
                    System.out.println("Data being trained : " + i + "/" + 400);
                }
                //Read data from dataset2 file & preform feedForward
                PrepNetwork examine = validateNetwork();
                getfinalOutput(net, examine);
    }


    //Gets the data from the train
    public static PrepNetwork  readDataFromFile() throws FileNotFoundException {
        PrepNetwork netData = new PrepNetwork();
        String filepath = "Train.csv";
        File file = new File(filepath);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String row = sc.nextLine();
                int lastElement = row.lastIndexOf(',');
                int label = Integer.parseInt(row.substring(lastElement + 1, lastElement + 2));
                String newLine = row.substring(0, lastElement);
                String[] nextRow = newLine.split(",");
                double[] rowCache = new double[nextRow.length];
                for (int i = 0; i < nextRow.length; i++) {
                    rowCache[i] = (int) Double.parseDouble(nextRow[i]);
                }
                double[] reuslt = new double[10];
                reuslt[label] = (int) 1d;
                netData.trainD.add(new double[][]{rowCache, reuslt});
            }
        } finally {
            sc.close();
        }
        return netData;
    }

    //Gets the data from Test
    public static PrepNetwork validateNetwork() throws FileNotFoundException {
        String filepath = "Test.csv";
        File file = new File(filepath);
        Scanner sc = null;
        PrepNetwork data = new PrepNetwork();
        try{
            sc = new Scanner(file);
            //Do while there is new line.
            while(sc.hasNextLine()){
                //Read in and split the line
                String row = sc.nextLine();

                int lastElement = row.lastIndexOf(',');
                int label = Integer.parseInt(row.substring(lastElement+1, lastElement +2));
                String newLine = row.substring(0,lastElement);
                String[] nextRow = newLine.split(",");
                //Build an array to add to a set
                double[] rowCache = new double[nextRow.length];
                for(int i = 0; i<nextRow.length; i++){
                    rowCache[i] = Double.parseDouble(nextRow[i]);
                }
                double[] reuslt = new double[10];
                reuslt[label] = (int) 1d;
               // data.provideInfo(rowCache, reuslt);
                data.trainD.add(new double[][]{rowCache, reuslt});
            }}finally {
            sc.close();
        }
        return data;
    }


    //Get the final output from the network by performing feedforward
    // and print the result
    public static void getfinalOutput(DeepNN net, @NotNull PrepNetwork set){
        int correct = 0;
        for(int i = 0; i < set.trainD.size(); i++){
            double[] optData = net.Feedforward(set.trainD.get(i)[0]);
            double[] desiredData = set.trainD.get(i)[1];
            int opt = 0;
            if ( optData == null || optData.length == 0 ) opt = -1;
            for ( int d = 1; d < optData.length; d++ )
            {
                if ( optData[d] > optData[opt] ) opt = d;
            }
            double output = opt;
            int dOpt = 0;
            if ( desiredData == null || desiredData.length == 0 ) dOpt = -1; // null or empty

            for ( int e = 1; e < desiredData.length; e++ )
            {
                if ( desiredData[e] > desiredData[dOpt] ) dOpt = e;
            }
            double desiredOutput = dOpt;
            //print the result
            System.out.println("Prediction : " + output + " Result  is : " + desiredOutput);
            if(output == desiredOutput){
                correct ++;
            }
        }
        System.out.println(correct + "/" + set.trainD.size());
        System.out.println("Accuracy: " + (double)((double)correct * 100 / (double)set.trainD.size()) );


    }


}
