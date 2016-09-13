package org.usfirst.frc.team2415.robot;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * 
 * @author Jabari
 *
 */
public class DataAnalyzer {
	
	/**
	 * Takes in a list of values and calculates the mean of them.
	 * @param vals
	 * @return <b>double</b> - mean of all values in list vals
	 */
	public static double mean(ArrayList<Double> vals){
    	double sum = 0;
    	for (Iterator<Double> it = vals.iterator(); it.hasNext();){
    		sum += it.next();
    	}
    	return sum/vals.size();
    }
	
	/**
	 * Takes in a list of values and calulates the standard deviation of them.
	 * Go here for a simple explanation of standard devation:
	 * http://www.mathsisfun.com/data/standard-deviation-formulas.html. Essentially,
	 * this calculation tells you on average how much your values vary from the mean.
	 * @param vals
	 * @return <b>double</b> - standard deviation of all values in list vals
	 */
    public static double stdDeviation(ArrayList<Double> vals){
    	double m = mean(vals), sum = 0;
    	for (Iterator<Double> it = vals.iterator(); it.hasNext();){
    		sum += Math.pow(it.next() - m, 2);
    	}
    	return sum/vals.size();
    }
    
    /**
     * Takes in a list of values and calculates the standard error of them.
     * Go here for a simple explanaton of standard error:
     * http://mathworld.wolfram.com/StandardError.html. Essentially, this calculation can
     * be used to tell you how much error, on average you have from the mean. In this case its
     * used to identify significant change in data. If the change in data is low, so is
     * standard error.
     * @param vals
     * @return <b>double</b> - standard error of all values in list vals
     */
    public static double stdError(ArrayList<Double> vals){
    	return stdDeviation(vals)/Math.sqrt(vals.size());
    }
}
