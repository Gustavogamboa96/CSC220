package prog12;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;



public class Newgle2 implements SearchEngine {


 public HardDisk<PageFile> pageDisk = new HardDisk<PageFile>();
 public PageTrie page2index = new PageTrie();
 public HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>();
 public WordTable word2index = new WordTable();

 Long indexPage(String url) {
  Long index = pageDisk.newFile();
  PageFile pageFile = new PageFile(index, url);
  
  pageDisk.put(index, pageFile);
  page2index.put(url, index);
  return index;
 }

 Long indexWord(String word) {
  Long index = wordDisk.newFile();
  List<Long> wordFile = new ArrayList<Long>();
  wordDisk.put(index, wordFile);
  word2index.put(word, index);
  return index;
 }

 @Override
 public void gather(Browser browser, List<String> startingURLs) {
  // TODO Auto-generated method stub
  page2index.read(pageDisk);
  word2index.read(wordDisk);

  System.out.println("pageDisk\n"+pageDisk);
  System.out.println("page2index\n" +page2index);
  System.out.println("wordDisk\n"+wordDisk);
  System.out.println("word2index\n"+word2index);
  
 }
 
 public class PageComparator implements Comparator<Long> {


	  public int compare(Long o1, Long o2) {

	 return pageDisk.get(o1).getRefCount() - pageDisk.get(o2).getRefCount();

	}


 private boolean moveForward(long[] currentPageIndices, Iterator<Long>[] pageIndexIterators) {
  long biggest = currentPageIndices[0];
  for (int i = 1; i < currentPageIndices.length; i++) {
   if (currentPageIndices[i] > biggest) {
    biggest = currentPageIndices[i];
   }
  }

  if (allEqual(currentPageIndices)) {
   biggest++;
  }

  for (int i = 0; i < currentPageIndices.length; i++) {
   if (currentPageIndices[i] != biggest) {
    if (!pageIndexIterators[i].hasNext()) {
     return false;
    } else {
     currentPageIndices[i] = pageIndexIterators[i].next();
    }
   }
  }
  return true;

 }

 
  
 private boolean allEqual(long[] array) {

  for (int i = 1; i < array.length; i++) {
   if (array[i] != array[0]) {
    return false;
   }
  }
  return true;

 }

 public String[] search(List<String> keyWords, int numResults) {
 
  Iterator<Long>[] pageIndexIterators = new Iterator[keyWords.size()];

  long[] currentPageIndices = new long[keyWords.size()];

  PriorityQueue<Long> bestPageIndices = new PriorityQueue<Long>(numResults, new PageComparator());

  
  for (int i = 0; i < keyWords.size(); i++) {
	  
   if (!word2index.containsKey(keyWords.get(i))) {
	   
    return new String[0];

   }
   pageIndexIterators[i] = wordDisk.get(word2index.get(keyWords.get(i))).iterator();
  }

  while (moveForward(currentPageIndices, pageIndexIterators)) {
   if (allEqual(currentPageIndices)) {
	   
	   
    long pageIndex = currentPageIndices[0];
    
    int priority = pageDisk.get(pageIndex).getRefCount();

    if (bestPageIndices.size() < numResults|| pageDisk.get(pageIndex).getRefCount() > pageDisk.get(bestPageIndices.peek()).getRefCount()) {
     if (bestPageIndices.size() == numResults) {
    	 	bestPageIndices.poll();
     }
     	bestPageIndices.offer(pageIndex);
    }
   }
  }

  String[] results = new String[bestPageIndices.size()];
  for (int i = bestPageIndices.size() - 1; i >= 0; i--) {
   results[i] = pageDisk.get(bestPageIndices.poll()).url;
  }
  return results;
 }

 



 }



}