package baxtree.btr;
import java.util.Arrays;

/**
 * @author xibai
 *
 */
public class MyFunctions {

	/**
	 * This method sorts an int array
	 * @param arrayOfInt The int array to sort
	 * @return The sorted int array
	 */
	public static int[] sort(int[] arrayOfInt){//increase
		Arrays.sort(arrayOfInt);
		return arrayOfInt;		
	 }
	
	/**
	 * This method sorts a double array
	 * @param arrayOfDouble The double array to sort
	 * @return the sorted double array
	 */
	public static double[] sort(double[] arrayOfDouble){//increase
		Arrays.sort(arrayOfDouble);
		return arrayOfDouble;
	 }
	
	/**
	 * This method returns the average from an int array
	 * @param arrayOfInt The int array
	 * @return The average (double type)
	 */
	public static double average(int[] arrayOfInt){	
		double sum = 0;
		for(int i : arrayOfInt) sum += i;
		return sum/arrayOfInt.length;
	}
	
	/**
	 * This method returns the average from a double array
	 * @param arrayOfDouble The double array
	 * @return The average (double type)
	 */
	public static double average(double[] arrayOfDouble){
		double sum = 0;
		for(double i : arrayOfDouble) sum += i;
		return sum/arrayOfDouble.length;
	}
	
    /**
     * This method computes the lenght of the longest common sequence from 2 strings
     * @param x First string
     * @param y Second string
     * @return The lenght of the LCS
     */
	public static int LCSLenght(String x, String y)
	{
		int M = x.length();
        int N = y.length();

        // opt[i][j] = length of LCS of x[i..M] and y[j..N]
        int[][] opt = new int[M+1][N+1];

        // compute length of LCS and all subproblems via dynamic programming
        for (int i = M-1; i >= 0; i--) {
            for (int j = N-1; j >= 0; j--) {
                if (x.charAt(i) == y.charAt(j))
                    opt[i][j] = opt[i+1][j+1] + 1;
                else 
                    opt[i][j] = Math.max(opt[i+1][j], opt[i][j+1]);
            }
        }
        
        // return the higest value from the array
        return opt[0][0];
	}
	
	public static void main(String args[]){
		String s1 = "jaabcc";
		String s2 = "aabcjb";
		System.out.println(LCSLenght(s1,s2));
	}
}
