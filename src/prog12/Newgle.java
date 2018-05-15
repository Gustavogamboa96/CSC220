package prog12;

import java.util.List;
import java.util.*;

	public class Newgle implements SearchEngine {
	HardDisk<PageFile> pageDisk = new HardDisk<PageFile>(); 	
	PageTrie page2index = new PageTrie(); 
	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>();
	WordTable word2index = new WordTable();
	
	public void gather(Browser browser, List<String> startingURLs) {
		Queue<Long> PageQueue = new ArrayDeque<Long>();		
		
		for (String url: startingURLs) {
			if (!page2index.containsKey(url)) {
				
				
				PageQueue.offer(indexPage(url));
				
			}
		}
		while (!PageQueue.isEmpty()){
			System.out.println("queue"+" "+ PageQueue);
			long pageIndex = PageQueue.poll();
			
			System.out.println("dequeued"+" "+ pageDisk.get(pageIndex));
			
			if (browser.loadPage(pageDisk.get(pageIndex).url)){
				
				List<String> list = browser.getURLs();
				list = browser.getURLs();
				
				System.out.println("urls " + list);		
				
				Set<Long> indexed = new HashSet<Long>();
				Queue<String> wordQueue = new LinkedList<String>(browser.getWords());
				for (int i = 0; i < list.size(); i++) {
					
					
					String currentUrl = list.get(i);
					
					if (!page2index.containsKey(currentUrl)) {
						
						PageQueue.offer(indexPage(currentUrl));
					}
					
					long index = page2index.get(currentUrl);
					PageFile cur = pageDisk.get(index);
					indexed.add(index);
				}
				for (Long index: indexed){
					
					PageFile current = pageDisk.get(index);
					current.incRefCount();
					System.out.println("inc ref " + current);
				}																							
				List<String> words = browser.getWords();
				Set<Long> wordset = new HashSet<Long>();
				
				System.out.println("words " + words);
			for (String word: words)
			{
				if (!word2index.containsKey(word))
					indexWord(word);
				Long index = word2index.get(word);
				if (!wordset.contains(index)){
					List<Long> listy = wordDisk.get(index);
					listy.add(pageIndex);
					wordset.add(index);
					System.out.println("add page " + index + "(" + word + ")" +   listy  );
				}
			}
			for (String url: list)
				if (!page2index.containsKey(url))
					PageQueue.offer(indexPage(url));
		}
		}
	
		System.out.println("pageDisk\n" + pageDisk);
		System.out.println("page2index\n" + page2index);
		System.out.println("wordDisk\n" + wordDisk);
		System.out.println("word2index\n" + word2index);
	}
	
  
	public String[] search(List<String> keyWords, int numResults) {
		String[] urls = new String[numResults];
		for (String word: keyWords)
		{
			
		}
	  return new String[0];
  }
	public long indexPage(String url) {
		long index = pageDisk.newFile();
		PageFile newPageFile = new PageFile(index, url);
		
		pageDisk.put(index, newPageFile);
		page2index.put(url, index);
		
		System.out.println("indexing page"+" " + newPageFile);
		return index;
	}
	
	public long indexWord(String word) {
		long index = wordDisk.newFile();
		List<Long> referns = new ArrayList<Long>();
		
		wordDisk.put(index, referns);
		word2index.put(word, index);
		
		System.out.println("indexing word " + index + "(" + word + ")" + referns);
		return index;
	}
}
