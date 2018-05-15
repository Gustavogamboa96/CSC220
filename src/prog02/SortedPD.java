package prog02;

import java.io.*;

/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {

	/** Add an entry or change an existing entry.
    @param name The name of the person being added or changed
    @param number The new number to be assigned
    @return The old number or, if a new entry, null
	 */
	public String addOrChangeEntry (String name, String number) {
		System.out.println("current array");
		for(int i = 0; i<size; i++)
			System.out.println(theDirectory[i].getName());
		
		System.out.println("adding" + name);
		
		int index = find(name);
		System.out.println("index" + index);
		
		modified = true;
		if (index >= 0) {
			String oldNumber = theDirectory[index].getNumber();
			theDirectory[index].setNumber(number);
			return oldNumber;
		} else {
			if (size >= theDirectory.length)
				reallocate();
			//size++;
			
			for(int i = size-1; i >= (-index-1); i--){
				theDirectory[i+1] = theDirectory[i];
			}
			
			
			size++;
			System.out.println("adding " + name + "to position" + (-index-1));
			theDirectory[-index-1] = new DirectoryEntry(name, number);

			return null;
		}
	}

	/** Find an entry in the directory.
    @param name The name to be found
    @return the index of the entry with that name or, if it is not
    there, (-insert_index - 1), where insert_index is the index
    where should be added
	 */
	protected int find (String name) {
		if (size == 0)
			return -1;
		
		int first = 0;
		int last = size-1;
		int middle;
		String name1;

		while(first<=last){
			middle = (last + first)/2;
			name1 = theDirectory[middle].getName();
			if(name.compareTo(name1) > 0){
				first = middle+1;
				//System.out.println("first is middle + 1 " + first);
				//middle = (last - first)/2;
			}else if(name.compareTo(name1)<0){
				last = middle - 1;
				//System.out.println("last is middle - 1");
				//middle = (last - first)/2;
			}else{
				return middle;
			}
			
		}
		System.out.println("first " + first);
	    return -first-1;
	
	}

	/** Remove an entry from the directory.
@param name The name of the person to be removed
@return The current number. If not in directory, null is
returned
	 */
	public String removeEntry (String name) {
		int index = find(name);
		if (index >= 0) {
			DirectoryEntry entry = theDirectory[index];
			for(int i = index ; i<size-1 ; i++)
				theDirectory[i] = theDirectory[i+1];
				

			size--;
			modified = true;
			return entry.getNumber();
		}
		else
			return null;
	}

}
