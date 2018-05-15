package prog06;

import java.io.File;
import java.util.*;
import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;
import javax.swing.JOptionPane;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;

public class WordPath {
	static UserInterface ui = new GUI();
	private static class Node{
		String word;
		Node previous;
		
		Node(String word, Node previous){
			this.word =word;
			this.previous = previous;
		}
		
		public String getWord(){
			return word;
		}
	}
	
	public static void main(String[] args){
		WordPath game = new WordPath();
		String filename = ui.getInfo("Enter Dictionary name: ");
		game.loadDictionary(filename);
		//static UserInterface ui = new GUI();
		
		//List<Node> nodes = new ArrayList<Node>();
		
		
		String[] commands = { "Human plays.", "Computer plays." };
		
		String start = ui.getInfo("Enter starting word");
		
		
		String target = ui.getInfo("Enter target word");
		
		int choice = ui.getCommand(commands);
		
		if(choice==0)
			game.play(start, target);
		else
			return;
			//game.solve(start, target);
		
	}		
	
	void play(String start, String target){
		String next =null;
		while(true){
			ui.sendMessage("Starting word is "+start+"\nand target word is "+target);
			next =ui.getInfo("Enter next word: ");

			if(!oneDegree(next, start)){
				ui.sendMessage("Your word is not different only by 1 letter");
				play(start, target);
			}else{
				start = next;
			}
			if(start.equals(target)){
				ui.sendMessage("You win");
				return;
			}
		}
	}
	
	static boolean oneDegree(String start, String target){
		//if(start.length() != target.length()){
		//	return false;
		//}
		int count = 0;
		for (int i = start.length(); i >0;i--){
			if(start.charAt(i) != target.charAt(i)){
				count++;
			}
		}
		if(count > 1){
			
			return false;
		}else{
			return true;
		}
		
	}
	List<Node> nodes = new ArrayList<Node>();
	
	boolean loadDictionary(String file){
		try{
			Scanner io = new Scanner(new File(file));
			while(io.hasNextLine()){
				String wrd = io.nextLine();
				Node item = null;
				Node prev = item.previous; 
				item = new Node(wrd, prev);
				nodes.add(item);
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public Node find(String word){
		if(nodes.isEmpty()){
			return null;
		}else{
		for(Node g : nodes){
			if(word.equals(g.getWord())){
				return g;
			}
			
		}
		}
		return null;
		
	}
	
}
