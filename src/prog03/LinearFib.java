package prog03;

public class LinearFib implements Fib {
	public double fib (int n) {
		int a, b;
		  a = 0;
		  b = 1;
		  int tmp = 0;
		  if(n == 1){
			  return 0;
		  }else if (n == 2){
			  return 1;
		  }else{
		  for(int i = 2; i <= n; i++){
			  tmp = a+b;
			  a = b;
			  b = tmp;
		  }
		  
		  
		  
		  return tmp;
		  }
	}

	/** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
	 */
	public double o (int n) {

		return n;
	}
}
