package de.oo2.tank.server.util;

import java.util.ArrayList;
import java.util.List;

/**
 * **********************************************************************
 * Compilation:  javac MarkovChainTemperature.java
 * Execution:    java MarkovChainTemperature
 * <p/>
 * Computes the expected time to go from state N-1 to state 0
 * <p/>
 * Data taken from Glass and Hall (1949) who distinguish 7 states
 * in their social mobility study:
 * <p/>
 * 1. Professional, high administrative
 * 2. Managerial
 * 3. Inspectional, supervisory, non-manual high grade
 * 4. Non-manual low grade
 * 5. Skilled manual
 * 6. Semi-skilled manual
 * 7. Unskilled manual
 * <p/>
 * See also Happy Harry, 2.39.
 * <p/>
 * ***********************************************************************
 */


public class MarkovChainTemperature {

    public Float[] getTemperatures() {

        List<Float> temperatures = new ArrayList<Float>();

        // the state transition matrix
        double[][] transition = {
                {0.386, 0.147, 0.202, 0.062, 0.140, 0.047, 0.016},
                {0.107, 0.267, 0.227, 0.120, 0.207, 0.052, 0.020},
                {0.035, 0.101, 0.188, 0.191, 0.357, 0.067, 0.061},
                {0.021, 0.039, 0.112, 0.212, 0.431, 0.124, 0.061},
                {0.009, 0.024, 0.075, 0.123, 0.473, 0.171, 0.125},
                {0.000, 0.103, 0.041, 0.088, 0.391, 0.312, 0.155},
                {0.000, 0.008, 0.036, 0.083, 0.364, 0.235, 0.274}
        };

        int N = 7;                        // number of states
        int state = N - 1;                // current state
        // int steps = 0;                    // number of transitions

        // run Markov chain
        while (state > 0) {

            // steps++;
            double r = Math.random();
            double sum = 0.0;

            // float bias = ((15.0f / N) * state) + (float) Math.random() ;
            float bias = ((15.0f / N) * state) + (float) Math.random() ;
            // bias = bias * 2.9f;
            temperatures.add(15 + bias);

            // determine next state
            for (int j = 0; j < N; j++) {
                sum += transition[state][j];
                if (r <= sum) {
                    state = j;
                    break;
                }
            }

        }

        return temperatures.toArray(new Float[temperatures.size()]);
    }

}
