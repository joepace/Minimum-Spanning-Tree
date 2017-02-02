package apps;
import structures.*;
import java.io.*;
import java.util.ArrayList;



public class Driver {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graph graph = new Graph("graph2.txt"); 
		
		PartialTreeList start = MST.initialize(graph);
		
		ArrayList<PartialTree.Arc> completeMST = MST.execute(start);
		
		System.out.println(completeMST.toString());
	
	}

}
