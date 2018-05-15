package prog05;

import java.util.EmptyStackException;
import java.util.List;
import java.util.ArrayList;

/** Implementation of the interface StackInt<E> using a List.
*   @author vjm
*/

public class ListStack<E> implements StackInt<E> {
  // Data Fields
  /** Storage for stack. */
  List<E> theData;

  /** Initialize theData to an empty List. */
  public ListStack() {
    theData = new ArrayList<E>();
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {
    theData.add(obj);
    return obj;
  }

  /**** EXERCISE ****/
  public E peek (){
	  /** Returns the object at the top of the stack without removing it.
      post: The stack remains unchanged.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
	  if(empty()){
		  throw new EmptyStackException();
	  }
	  return theData.get(theData.size()-1);
	  
  }
  
  public E pop(){
	  if(empty()){
		  throw new EmptyStackException();
	  }
	  
	  
	  return theData.remove(theData.size()-1);
  }
  

@Override
public boolean empty() {
	// TODO Auto-generated method stub
	if(theData.isEmpty())
		return true;
	else
		return false;
}
  

}
