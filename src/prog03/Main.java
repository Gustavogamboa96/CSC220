package prog03;
import prog02.UserInterface;

import javax.swing.JOptionPane;

import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
	/** Use this variable to store the result of each call to fib. */
	public static double fibN;

	/** Determine the time in microseconds it takes to calculate the
      n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time for the call to fib(n) in microseconds
	 */
	public static double time (Fib fib, int n) {
		// Get the starting time in nanoseconds.
		long start = System.nanoTime();

		// Calculate the n'th Fibonacci number.
		// Store the result in fibN.
		fibN = fib.fib(n);


		// Get the ending time in nanoseconds.  Use long, as for start.
		long end = System.nanoTime();

		// Uncomment the following for a quick test.
		// System.out.println("start " + start + " end " + end);

		// Return the difference between the end time and the
		// start time divided by 1000.0 to convert to microseconds.
		return (end -start)/1000.0 ;
	}

	/** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
	 */
	public static double averageTime (Fib fib, int n, long ncalls) {
		double totalTime = 0;

		// Add up the total call time for calling time (above) ncalls times.
		// Use long instead int in your declaration of the counter variable.
		for(int count = 0; count< ncalls; count++){
			totalTime += time(fib, n);

		}


		// Return the average time.
		return totalTime/ncalls ; 
	}

	/** Determine the time in microseconds it takes to to calculate
      the n'th Fibonacci number accurate to three significant figures.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
	 */
	public static double accurateTime (Fib fib, int n) {
		// Get the time using the time method above.
		double acctime = time(fib, n);

		// Since it is not very accurate, it might be zero.  If so set it to 0.1
		if (acctime == 0)
			acctime = 0.1;


		long ncalls = 1;
		// Calculate the number of calls to average over that will give
		// three figures of accuracy.  You will need to "cast" it to long
		// by putting   (long)   in front of the formula.
		ncalls = (long) (1000000 / (acctime*acctime)); 

		// If ncalls is 0, increase it to 1.
		if (ncalls == 0)
			ncalls = 1;
		System.out.println("ncalls " + ncalls);

		// Get the accurate time using averageTime.
		return averageTime(fib, n, ncalls);
	}


	static void labExperiments () {
		// Create (Exponential time) Fib object and test it.
		Fib efib = new LogFib();
		System.out.println(efib);
		for (int i = 0; i < 11; i++)
			System.out.println(i + " " + efib.fib(i));

		// Determine running time for n1 = 20 and print it out.
		int n1 = 20;
		double time1 = averageTime(efib, n1, 1000);
		//System.out.println("n1 " + n1 + " time1 " + time1);
		long ncalls = (long) (1000000/time1*time1);
		time1 = averageTime(efib, n1, ncalls);
		//System.out.println("n1 " + n1 + " time1 " + time1 + "/n ncalls");

		//System.out.println("n1 " + n1 + " time1 " + accurateTime(efib, n1));

		// Calculate constant:  time = constant times O(n).
		double c = time1 / efib.o(n1);
		System.out.println("c " + c);

		// Estimate running time for n2=30.
		int n2 = 30;
		double time2est = c * efib.o(n2);
		System.out.println("n2 " + n2 + " estimated time " + time2est);

		// Calculate actual running time for n2=30.
		double time2 = averageTime(efib, n2, 100);
		System.out.println("n2 " + n2 + " actual time " + time2);
		ncalls = (long) (1000000/time2*time2);
		if(ncalls == 0)
			ncalls = 1;
		time2 = averageTime(efib, n2, ncalls);
		//System.out.println("n2 " + n2 + " time2 " + time2);
		//System.out.println("n2 " + n2 + " time2" + accurateTime(efib, n2));

		int n3 = 100;
		double time3est = c * efib.o(n3);
		double exp = Math.pow(10, 13) * 3.154;
		System.out.println("n3 " + n3 + " estimated time " + time3est + " which is " + (time3est/(exp))) ;   
	}

	private static UserInterface ui = new GUI();

	static void hwExperiments (Fib fib) {
		double c = -1;  // -1 indicates that no experiments have been run yet.

		while (true) {
			int n = -1; // -1 indicates number not known yet.
			// Ask the user for an integer n.
			while(n<0){
			try{
			String in = ui.getInfo("Enter n: ");
			if(in != null)
				n = Integer.parseInt(in);
			else
				return;
			
			}
			catch(NumberFormatException e){
				ui.sendMessage("java.lang.NumberFormatException: For input string "+ n +" Try again.");
				
				continue;
			}
			if(n <0 )
				ui.sendMessage(n +" is not positive. Try again.");
				
			}
				
			// Return if the user cancels.
			// Deal with bad inputs:  not positive, not an integer.

			// Estimated running time. -1 indicates no estimate
			double tEst = -1;

			// If this not the first experiment, estimate the running time for
			// that n using the value of the constant from the last time.
			if(c != -1){
				tEst = c * fib.o(n);
				ui.sendMessage("Estimated time for fib("+n+") is "+tEst+" µs.");
			}

			// Display the estimate.
			

			// ADD LATER: If it is greater than an hour, ask the user for
			// confirmation before running the experiment.
			// If not, the "continue;" statement will skip the experiment.
			
			String[] yesno = {"Yes","No"};
			if(tEst > 3599999999.9){
				int o = JOptionPane.showOptionDialog(null,"The estimated time is more than an hour, do you still want to proceed?", "Input", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, yesno, yesno[1]);
				if(o == 1)
					continue;
				
			}
			
			// Now, calculate the (accurate) running time for that n.
			double tAcc = accurateTime(fib, n);
			// Calculate the constant c.
			c = tAcc / fib.o(n);
			// Display fib(n) and the actual running time.
			ui.sendMessage("fib("+n+")= "+fib.fib(n)+" in "+ tAcc + " µs.");
			if (tEst != -1)
				ui.sendMessage("Estimate had " + (int) (100 * (tEst - tAcc) / tAcc) + "% error.");
		}
	}
	
	static void hwExperiments () {
		// In a loop, ask the user which implementation of Fib or exit,
		// and call hwExperiments (above) with a new Fib of that type.
		String[] commands = {"ExponentialFib", "LinearFib", "LogFib", "ConstantFib", "EXIT"};
		
		while(true){
			int c = ui.getCommand(commands);
			switch(c){
			case 0:
				
				hwExperiments(new ExponentialFib());
				break;
			case 1:
				
				hwExperiments(new LinearFib());
				break;
			case 2:
			 
				hwExperiments(new LogFib());
				break;
			case 3:
				
				hwExperiments(new ConstantFib());
				break;
			case 4:
				return;


			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main (String[] args) {
		labExperiments();
		hwExperiments();
		
	}
}
