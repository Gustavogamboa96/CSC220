package prog05;

import java.util.EmptyStackException;

import prog02.DirectoryEntry;

/** Implementation of the interface StackInt<E> using an array.
*   @author vjm
*/

public class ArrayStack<E> implements StackInt<E> {
  // Data Fields
  /** Storage for stack. */
  E[] theData;

  /** Index to top of stack. */
  int top = -1; // initially -1 because there is not top

  private static final int INITIAL_CAPACITY = 100;

  /** Construct an empty stack with the default initial capacity. */
  public ArrayStack () {
    theData = (E[])new Object[INITIAL_CAPACITY];
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {
    top++;

    if (top == theData.length)
      reallocate();

    theData[top] = obj;
    return obj;
  }

  /** Returns the object at the top of the stack and removes it.
      post: The stack is one item smaller.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E pop () {
    if (empty())
      throw new EmptyStackException();
    else{
    	E tmp = theData[top];
    	top--;
    	return tmp;
    
    }
    /**** EXERCISE ****/

  }

  /** Returns the object at the top of the stack without removing it.
      post: The stack remains unchanged.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E peek () {
	  if (empty())
	    throw new EmptyStackException();
    /**** EXERCISE ****/
	  return theData[top];
  }

  /**** EXERCISE ****/
  public void reallocate(){
	  E[] theNewData = (E[])new Object[2 * theData.length];
	   //newTheData = (E[]) newTheData[];
		    System.arraycopy(theData, 0, theNewData, 0, theData.length);
		    theData = theNewData;
  }
  
  public boolean empty(){
	  if(top < 0)
		  return true;
	  else
		  return false;
  }
}
