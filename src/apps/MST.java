package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	
		/* COMPLETE THIS METHOD */
		PartialTreeList L = new PartialTreeList(); //1
		
		for (Vertex v : graph.vertices) //Step 2.1
		{
			PartialTree T = new PartialTree(v); //Step 2.2
			
			Vertex.Neighbor neigh = v.neighbors;
			
			MinHeap<PartialTree.Arc> P = T.getArcs(); 
		
			while(neigh != null)
			{
				PartialTree.Arc newArc = new PartialTree.Arc(v, neigh.vertex, neigh.weight);
				
				P.insert(newArc); //2.4
				
				if (neigh.next == null)
				{
					break;
				}
				
				neigh = neigh.next;
				
				if (neigh.vertex == v)
				{
					neigh = neigh.next;
				}
			}
			
			L.append(T); //2.5
		}
		
		return L;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		/* COMPLETE THIS METHOD */
		ArrayList<PartialTree.Arc> finalMST = new ArrayList<PartialTree.Arc>();
		
		int count = ptlist.size();
		
		while (count > 1) //when = 1, we found MST
		{
			PartialTree PTX = ptlist.remove(); //3
			
			MinHeap<PartialTree.Arc> PQX = PTX.getArcs();
			
			PartialTree.Arc priority = PQX.deleteMin(); //4
			Vertex v1 = PTX.getRoot();
			Vertex v2 = priority.v2;
			
			if (v1 == v2|| v1 == v2.parent) //5
			{
				priority = PQX.deleteMin();
				v2 = priority.v2.parent;
			}
			
			finalMST.add(priority); //6
			
			PartialTree PTY = ptlist.removeTreeContaining(v2); //7
			
			if (PTY == null)
			{
				continue;
			} 
			
			PTY.getRoot().parent = PTX.getRoot(); //8
			PTY.merge(PTX);
			ptlist.append(PTY);
			
			count--;
			//if count still > 1, we perform step 9, which just repeats the process
		}

		return finalMST;
	}
}
