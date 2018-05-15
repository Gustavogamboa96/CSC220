package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Tower {
  static UserInterface ui = new GUI();

  static public void main (String[] args) {
    int n = getInt("How many disks?");
    if (n <= 0)
      return;
    Tower tower = new Tower(n);

    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    if (c == 0)
      tower.play();
    else
      tower.solve();
  }

  /** Get an integer from the user using prompt as the request.
   *  Return 0 if user cancels.  */
  static int getInt (String prompt) {
    while (true) {
      String number = ui.getInfo(prompt);
      if (number == null)
        return 0;
      try {
        return Integer.parseInt(number);
      } catch (Exception e) {
        ui.sendMessage(number + " is not a number.  Try again.");
      }
    }
  }

  int nDisks;
  StackInt<Integer>[] pegs = (StackInt<Integer>[]) new ArrayStack[3];

  Tower (int nDisks) {
    this.nDisks = nDisks;
    for (int i = 0; i < pegs.length; i++)
      pegs[i] = new ArrayStack<Integer>();

    // EXERCISE: Initialize game with pile of nDisks disks on peg 'a' (pegs[0]).
    for(int i = nDisks; i >0;i--){
    	pegs[0].push(i);
    }
  }

  void play () {
    while (!(pegs[0].empty() && pegs[1].empty())) {
      displayPegs();
      String move = getMove();
      int from = move.charAt(0) - 'a';
      int to = move.charAt(1) - 'a';
      move(from, to);
    }

    ui.sendMessage("You win!");
  }

  String stackToString (StackInt<Integer> peg) {
    StackInt<Integer> helper = new ArrayStack<Integer>();

    // String to append items to.
    String s = "";
    if(!peg.empty()){
    // EXERCISE:  append the items in peg to s from bottom to top.
    while(!peg.empty()){
    	helper.push(peg.pop());
    	//peg.pop();
    }
    while(!helper.empty()){
    	
    	s += Integer.toString(helper.peek());
    	peg.push(helper.pop());
    	//helper.pop();
    }
    }
    return s;
    
  }

  void displayPegs () {
    String s = "";
    for (int i = 0; i < pegs.length; i++) {
      char abc = (char) ('a' + i);
      s = s + abc + stackToString(pegs[i]);
      if (i < pegs.length-1)
        s = s + "\n";
    }
    ui.sendMessage(s);
  }

  String getMove () {
    String[] moves = { "ab", "ac", "ba", "bc", "ca", "cb" };
    return moves[ui.getCommand(moves)];
  }

  void move (int from, int to) {
    // EXERCISE:  move one disk form pegs[from] to pegs[to].
    // Don't allow illegal moves.  Send a warning message instead, like:
    // Cannot place 2 on top of 1.  Use ui.sendMessage().
	  if(pegs[from].empty())
		  ui.sendMessage("Can't move a disk from an empty peg");
	  else if(pegs[to].empty())
		  pegs[to].push(pegs[from].pop());
	  else if(pegs[from].peek() > pegs[to].peek())
		  ui.sendMessage("Illegal move");
	  else	  
		  pegs[to].push(pegs[from].pop());
	  

  }

  static String[] pegNames = { "a", "b", "c" };

  
  
  // EXERCISE:  create Goal class.
  class Goal {
    // Data.
	  int howMany;
	  int fromPeg;
	  int toPeg;
	  
    // Constructor.
    public Goal(int howMany, int fromPeg, int toPeg){
    	this.howMany= howMany;
    	this.fromPeg = fromPeg;
    	this.toPeg = toPeg;
    	
    	
    	
    }
    public int getHowMany(){
		return howMany;
	}
    public int getFromPeg(){
    	return fromPeg;
    }
    public int getToPeg(){
    	return toPeg;
    }
    
    
    public String toString () {
      // Convert to String and return it.
    	String goals = "Move "+howMany+" disk(s) from " +pegNames[fromPeg]+" to "+ pegNames[toPeg]+".\n";
    	
      return goals; //Right.
    }
  }
  


  // EXERCISE:  display contents of a stack of goals
  void displayGoals (StackInt<Goal> goals) {
	  StackInt<Goal> tmp = new ArrayStack<Goal>();
	  String stackGoals ="";
	 while(!goals.empty()){
		Goal newGoal = goals.pop();
		stackGoals+= newGoal.toString();
	 	tmp.push(newGoal);
	 }
	 while(!tmp.empty())
		 goals.push(tmp.pop());
	 
	 ui.sendMessage(stackGoals);
	 }
  
  void solve () {
    // EXERCISE
	  
	  StackInt<Goal> goalStack = new ArrayStack<Goal>();
	  Goal goal = new Goal(nDisks, 0, 2);
	  goalStack.push(goal);
	  displayGoals(goalStack);

	  while(!goalStack.empty()){
		  Goal bGoal = goalStack.pop();		  
		  int h = bGoal.getHowMany();
		  int f = bGoal.getFromPeg();
	      int t = bGoal.getToPeg();
			if(h!=1){			   
		      int e = 0;
		      int ch = (t + f);
		      if(ch==1)
		    	  e = 2;
		      else if(ch==3)
		    	  e =0;
		      else
		    	  e=1;
			  
			  Goal subgoal1 = new Goal(h-1, f, e);
			  Goal subgoal2 = new Goal(1, f, t);
			  Goal subgoal3 = new Goal(h-1, e, t);
			  goalStack.push(subgoal3);
			  goalStack.push(subgoal2);
			  goalStack.push(subgoal1);
			  
			  displayGoals(goalStack);
			  
		  }else{
			  move(f,t);
			  displayPegs();
		  }
			
	  }
	  
  }        
}
