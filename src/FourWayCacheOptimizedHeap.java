
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FourWayCacheOptimizedHeap {
 
		    private List<HuffmanNode> fourWayHeap;
		    
		    public FourWayCacheOptimizedHeap() {
		    	fourWayHeap = new ArrayList<>();
		    }

		    private int getParent(int n) {	return n <= 3 ? -1 : n/4 + 2; }
		    private int one(int n) { return 4*(n-2) + 0; }
		    private int two(int n) { return 4*(n-2) + 1; }
		    private int three(int n) { return 4*(n-2) + 2; }
		    private int four(int n) { return 4*(n-2) + 3; }
		    public int size() { return fourWayHeap.size(); }

		    
		    private int minChildIndex(int n) {
		        if (one(n) > fourWayHeap.size() - 1) return -1;
		        else if (two(n) > fourWayHeap.size() - 1) return one(n);
		        else if (three(n) > fourWayHeap.size() - 1) return fourWayHeap.get(one(n)).freq <= fourWayHeap.get(two(n)).freq ? one(n) : two(n);
		        else if (four(n) > fourWayHeap.size() -1) return (fourWayHeap.get(one(n)).freq <= fourWayHeap.get(two(n)).freq) && (fourWayHeap.get(one(n)).freq <= fourWayHeap.get(three(n)).freq) ? one(n) : (fourWayHeap.get(two(n)).freq <= fourWayHeap.get(three(n)).freq) ? two(n) : three(n);
		        else{
		        	// 4 items compare
		        	return (fourWayHeap.get(one(n)).freq <= fourWayHeap.get(two(n)).freq) 
		        			&& (fourWayHeap.get(one(n)).freq <= fourWayHeap.get(three(n)).freq)
		        			&& (fourWayHeap.get(one(n)).freq <= fourWayHeap.get(four(n)).freq) ? one(n) : (fourWayHeap.get(two(n)).freq <= fourWayHeap.get(three(n)).freq)
		        																						&& (fourWayHeap.get(two(n)).freq <= fourWayHeap.get(four(n)).freq) ? two(n) : (fourWayHeap.get(three(n)).freq <= fourWayHeap.get(four(n)).freq) ? three(n) : four(n);
		        }
		    }

		  
		    public void insertInto4wayHeap(HuffmanNode newElement) {
		    	fourWayHeap.add(newElement);
		    	int newElementIndex = fourWayHeap.size() - 1;
		    	int parentIndex = getParent(newElementIndex);
		        while (parentIndex != -1 && (fourWayHeap.get(parentIndex).freq > fourWayHeap.get(newElementIndex).freq)) {
		            swap(parentIndex, newElementIndex);
		            newElementIndex = parentIndex;
		            parentIndex = getParent(newElementIndex);
		        }
		    }

		   
		    public HuffmanNode removeMin() {
		        if (fourWayHeap.size() <= 3){
		        	return null;
		        }

		        HuffmanNode result = fourWayHeap.get(3);
		        fourWayHeap.set(3, fourWayHeap.get(fourWayHeap.size() - 1));
		        fourWayHeap.remove(fourWayHeap.size() - 1);
		        
		        int n = 3;
		        int minChildIndex = minChildIndex(n);
		        while (minChildIndex != -1 && fourWayHeap.get(minChildIndex).freq < fourWayHeap.get(n).freq) {
		            swap(minChildIndex, n);
		            n = minChildIndex;
		            minChildIndex = minChildIndex(n);
		        }
		        return result;
		    }


		   
		    public boolean isHeap() {
		        for (int i = 1; i < fourWayHeap.size(); ++i) {
		            if (getParent(i) >= 0) {
		                if (fourWayHeap.get(getParent(i)).freq > fourWayHeap.get(i).freq) {
		                    return false;
		                }
		            }
		        }
		        return true;
		    }

		    
		    private void swap(int i, int j) {
		        HuffmanNode tmp = fourWayHeap.get(i);
		        fourWayHeap.set(i, fourWayHeap.get(j));
		        fourWayHeap.set(j, tmp);
		    }

		    
		    public List<HuffmanNode> build_tree_using_4way_heap(Map<String, Integer> frequencyTable){
		    	Iterator it = frequencyTable.entrySet().iterator();
		    	fourWayHeap.add(new HuffmanNode("0",0));
		    	fourWayHeap.add(new HuffmanNode("0",0));
		    	fourWayHeap.add(new HuffmanNode("0",0));
		    	while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        
			        insertInto4wayHeap(new HuffmanNode(pair.getKey().toString(),(int)pair.getValue()));
			        //it.remove(); // avoids a ConcurrentModificationException
			    }
			    return fourWayHeap;   
		    }
		    
}
