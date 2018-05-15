package prog04;

public class SortedDLLPD extends DLLBasedPD{
	
	public String addOrChangeEntry (String name, String number) {
	    modified = true;
	    DLLEntry entry = find(name);
	    if (entry==null) {
	    	entry = new DLLEntry(name, number);
	   	 
		      // Add entry to end of list.
		      // EXERCISE
		      if(tail == null){
		    	  head = entry;
		    	  tail = entry;
		      }else{
		    	tail.setNext(entry);
		    	entry.setPrevious(tail);
		    	tail = entry;
		      }
		      

		      return null;
	    	
	      
	    }else if((entry.getName().compareTo(name) == 0)){
	    	
	    	String oldNumber = entry.getNumber();
		      entry.setNumber(number);
		      return oldNumber;
	    	
		      
	    	
	    	
	    }else if(name.compareTo(entry.getName()) != 0){
	    	System.out.println("BOP");
	    	DLLEntry next = entry.getNext();
	    	DLLEntry prev = entry.getPrevious();
	    	DLLEntry nentry = new DLLEntry(name, number);
	    	nentry.setNext(entry);
	    	entry.setPrevious(nentry);
	    	System.out.println("the new entry: "+entry.getName());
	    	if(prev == null){
	    		head = nentry;
	    	}else{
	    	nentry.setPrevious(prev);
	    	prev.setNext(nentry);
	    	}
	    	
	    	
	    	
	    	return null;
	    }else{
	    	return null;
	    }
	  }
	    
	  /** Find an entry in the directory.
	      @param name The name to be found
	      @return The entry with the same name or the next if it is not there.
	  */
	  protected DLLEntry find (String name) {
	    // EXERCISE
	    // For each entry in the directory.
		  
		  
		  for(DLLEntry tmp = head; tmp != null; tmp = tmp.getNext()){
			  if(tmp.getName().compareTo(name) == 0){
				  System.out.println(tmp.getName());
				  return tmp;
				  
			  }else if(name.compareTo(tmp.getName()) < 0){
				  System.out.println(tmp.getName());
				  return tmp;
			  }
				
			  
		  }
		  
	    // What is the first?  What is the next?  How do you know you got them all?

	      // If this is the entry you want

	        // return it.

	    return null; // Name not found.
	  }
	  
	  /** Remove an entry from the directory.
	      @param name The name of the person to be removed
	      @return The current number. If not in directory, null is
	      returned
	  */
	  public String removeEntry (String name) {
	    // Call find to find the entry to remove.
	    DLLEntry entry = find(name);
	    // If it is not there, forget it!
	    
	    if (entry == null || name.compareTo(entry.getName())!=0)
	      return null;

	    // Get the next entry and the previous entry.
	    // EXERCISE
	    DLLEntry next = entry.getNext();
	    DLLEntry prev = entry.getPrevious();    
	    
	    // Two cases:  previous is null (entry is head) or not
	    // EXERCISE
	    if(prev == null)
	    	head = entry.getNext();
	    else
	    	entry.getPrevious().setNext(next);
	    	
	      
	    // Two cases:  next is null (entry is tail) or not
	    // EXERCISE
	    if(next == null)
	    	tail = entry.getPrevious();
	    else
	    	entry.getNext().setPrevious(prev);
	    	
	    modified = true;
	    return entry.getNumber();
	  }
}
