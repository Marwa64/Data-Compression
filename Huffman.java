import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Node implements Comparable<Node> {
	Integer data;
	String character;
	Node left;
	Node right;
	String code;
	
	public Integer getData() {
		return data;
	}
	@Override
	public int compareTo(Node n) {
		return this.getData().compareTo(n.getData());
	}
}

public class Huffman {
	private static int frequency(char letter, String text) {
		Integer count = 0;
		for (int i = 0; i < text.length(); i++) {
			count += text.charAt(i) == letter ? 1 : 0;
		}
		return count;
	}
	
	private static Node constructTree(List<Node> baseCopy) {
		Node root = new Node();
		Collections.sort(baseCopy);
		
		while (baseCopy.size() > 1) {
			root = new Node();
			root.character = baseCopy.get(0).character + "," + baseCopy.get(1).character;
			root.data = baseCopy.get(0).data + baseCopy.get(1).data;
			
			root.left = baseCopy.get(0);
			root.right = baseCopy.get(1);
			
			baseCopy.remove(1);
			baseCopy.remove(0);
			baseCopy.add(root);
			
			Collections.sort(baseCopy);
		}
		/*for (int i = 0; i < baseCopy.size(); i++) {
			System.out.println(baseCopy.get(i).character + " " + baseCopy.get(i).data);
		}
		System.out.println("-----------------------------------");*/
		
		return root;
	}
	
	private static String generateCode(String letter, Node root) {
		String output = "";
		Node current = root;
		
		while (true) {
			if (current.left != null && current.left.character.contains(letter)) {
				current = current.left;
				output += "0";
				continue;
			}
			if (current.right != null && current.right.character.contains(letter)) {
				current = current.right;
				output += "1";
				continue;
			}
			return output;
		}
	}
	
    public static boolean compress(String inputPath, String outputPath) throws IOException{
    	String text = Files.readString(Path.of(inputPath));
    	//String text = inputPath;
    	String foundLetters="", output = "";
    	
    	ArrayList<Node> baseNodes = new ArrayList<Node>();  
    	
		for (int i = 0; i < text.length(); i++) {
			char letter = text.charAt(i);
			if (foundLetters.contains(Character.toString(letter))) {
				continue;
			}
			foundLetters += letter;
			Node newNode = new Node();
			newNode.character = Character.toString(letter);
			newNode.data = frequency(letter, text);
			newNode.left = null;
			newNode.right = null;
			
			baseNodes.add(newNode);
		}
		
		List<Node> baseCopy = new ArrayList<Node>(baseNodes);
		
		Node root = constructTree(baseCopy);
		for (int i = 0; i < baseNodes.size(); i++) {
			output += baseNodes.get(i).character + generateCode(baseNodes.get(i).character, root);
		}
		output += "/";
		for (int i = 0; i < text.length(); i++) {
			output += generateCode(Character.toString(text.charAt(i)), root);
		}
    	
	    try {
	        FileWriter myWriter = new FileWriter(outputPath + "\\CompressedFile_Huffman.txt");
	        myWriter.write(output);
	        myWriter.close();
	        return true;
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	        return false;
	      }
		
		/*System.out.println(output);
		return true;*/
     }
    
    
    public static boolean decompress(String inputPath, String outputPath) throws IOException{
    	String text = Files.readString(Path.of(inputPath));
    	//String text = inputPath;
    	String output = "";
    	boolean tableSection = true;
    	
    	ArrayList<Node> table = new ArrayList<Node>();
    	Node newNode = new Node();
    	String nextLetter = "";
    	
    	for (int i = 0; i < text.length(); i++) {
    		if (tableSection) {
    			if (text.charAt(i) == '/') {
       				if (newNode.character != null) {
    					table.add(newNode);
    				}
    				tableSection = false;
    				continue;
    			}
    			
    			if (text.charAt(i) != '0' && text.charAt(i) != '1') {
    				if (newNode.character != null) {
    					table.add(newNode);
    				}
    				newNode = new Node();
    				newNode.character = Character.toString(text.charAt(i));
    				newNode.code = "";
    			} else {
    				newNode.code += text.charAt(i);
    			}
    		} else {
    			nextLetter += text.charAt(i);
    			for (int j = 0; j < table.size(); j++) {
    				if (nextLetter.equals(table.get(j).code)) {
    					output += table.get(j).character;
    					nextLetter = "";
    					break;
    				}
    			}
    		}
    	}
    	
	    try {
	        FileWriter myWriter = new FileWriter(outputPath + "\\DecompressedFile_Huffman.txt");
	        myWriter.write(output);
	        myWriter.close();
	        return true;
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	        return false;
	      }
	    
		/*System.out.println(output);
		return true;*/
     }
    
	/*public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter text: ");
		String input = in.nextLine();
		
		System.out.println("Result: " + decompress(input, ""));
	}*/
}
