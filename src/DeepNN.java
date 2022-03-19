

public class DeepNN {
    //Constructing Deep Neural Network
    public static int i = 1;
    public static int d = 0;
    public  int NS = 4;
    private double[][] result;


    //Weights for the network with 3 layers
    private double[][][] WH;
    private double[][]  produceError;
    int[] NZ;
    private double[][] neuronSummed;
    //Value used to direct activation Function result
    private double[][] b;

    //Build a network with 64 nodes in input layer, 30 in 1st hidden layer, 20 in 2nd hidden layer and 10 output
    public DeepNN() {
       NZ = new int[]{64,35,25,10};
         //Index of the first layer is 0
        result = new double[NS][];
        initializeWeights();
    }



    //Starts the network with 400 cycles
    public void initNetwork(PrepNetwork config) {
        for (int i = 0; i < 400; i++) {
            //used for larger arrays
            PrepNetwork data = config.improvement();
            trainNetwork(data);
        }
    }


    //Sends network data to be trained
    private void trainNetwork(PrepNetwork data) {
        for (int b = 0; b < 32; b++) {
            networktrain(data.trainD.get(b)[0], data.trainD.get(b)[1]);
        }
    }

    //Trains the network to the given data and output
    public void networktrain(double[] input, double[] target) {
        initOutput();
        inintError();
        initTotalSum();
        initaddbias();
        Feedforward(input);
        backpropoGation(target);
        applyGradientOnWeights();
    }


    //FeedForward func moves forward with input neron into the next
    // layer by processing large amount of neurons in each layer node
    public double[] Feedforward(double[] d) {
        result[0] = d;
        moveforward();
        return result[3];
    }

    //Iterate trough every neuron in its specified layer
    private void moveforward() {
        for ( i = 1 ;i < 4; i++) {
            for (d = 0; d < NZ[i]; d++) {
                activationFunction();
            }
        }
    }
    //Sums each node in the layer  and performs Sigmoid activation function
    private void activationFunction() {
        double t = 0;
        for (int e = 0; e < NZ[i - 1]; e++) {
            //Add contains:  each node of the network times by their
            //weights
            t += result[i - 1][e] * WH[i][d][e];
         }
        t += b[i][d];
        result[i][d] = 1D / (1 + Math.exp(-t));
        neuronSummed[i][d] = result[i][d] * (1 - result[i][d]);
    }



    //Moves back words in the network from the output layer towards the
    //input layer
    public void backpropoGation(double[] target) {
         findErrorHiddenError(target);
         calculateGradient();
    }

    //Calculate the error value of each hidden layer node  of the network while moving backwards
    public void findErrorHiddenError(double[] target){
        for (int neuron = 0; neuron < NZ[4 - 1]; neuron++) {
            //Gets the error value
            produceError[3][neuron] = (result[3][neuron] - target[neuron]) * neuronSummed[3][neuron];
        }
    }

    //Calculates the gradient which is needed to perform gradient descent to reduce the loss from each node
    private void calculateGradient() {
        for (int layer = 2; layer > 0; layer--) {
            for (int neuron = 0; neuron < NZ[layer]; neuron++) {
                double sum = 0;
                for (int nextNeuron = 0; nextNeuron < NZ[layer + 1]; nextNeuron++) {
                    sum += WH[layer + 1][nextNeuron][neuron] * produceError[layer + 1][nextNeuron];
                }
                produceError[layer][neuron] = sum * neuronSummed[layer][neuron];
            }
        }
    }
    //Utilizes the gradient to perform gradeint descent
    public void applyGradientOnWeights() {
        for (int i = 1; i < NS; i++) {
            gradientDescent(i);
        }
    }
    //optimize the weight of each node  to remove the errorValue of each output,hidden, input layer node
    private void gradientDescent(int i ) {
        for (int d = 0; d < NZ[i]; d++) {
            //learning rate times  by the derivative of the loss
            double finalWeight = -0.001 * produceError[i][d];
            b[i][d] += finalWeight;
            for (int previousNeuron = 0; previousNeuron < NZ[i - 1]; previousNeuron++) {
                WH[i][d][previousNeuron] += finalWeight * result[i - 1][previousNeuron];
            }
        }
    }


    //Initialize the weights
    private void initializeWeights() {
        WH = new double[NS][][];
        b = new double[NS][];
        produceError = new double[NS][];
        neuronSummed = new double[NS][];
        for (int i =1; i< 4; i++){
            double[][] netData = new double[NZ[i]][NZ[i - 1]];
            for(int d = 0; d < NZ[i]; d++){
                double[]  secondArray  = new double[NZ[i - 1]];
                for(int e = 0; e < NZ[i - 1]; e++){
                    secondArray[e] = Math.random()*(1 - -1) + -1;
                }
                netData[d] = secondArray;
            }
            WH[i] =netData;

        }
    }

    //Initialize the result array that
    //Hold the neuron 
    private void initOutput(){
        for (int i = 0; i < 4; i++){
            result[i] = new double[NZ[i]];
        }
    }
    //Initialize the errorValue of each layer
    private void inintError(){
        for (int i = 0; i < 4; i++){
            produceError[i] = new double[NZ[i]];
        }
    }
    //Initialize the sum each neuron in a 
    //Particular layer
    private void initTotalSum(){
        for (int i = 0; i < 4; i++){
            neuronSummed[i] = new double[NZ[i]];
        }
    }
    //Initialize the bias value that gets
    //added to  activation function
    private void initaddbias(){
        for (int i = 0; i < 4; i++){
            //Create an array of specified range
            double[] biasData = new double[NZ[i]];
            //Iterate through the array an fill it with random value withing
            //the specified range
            for(int index = 0; index < NZ[i]; index++){
                biasData[index] = Math.random()*(0.7 - -0.1) + -0.1;
            }
            b[i] = biasData;
        }
    }




}
