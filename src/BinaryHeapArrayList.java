
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class BinaryHeapArrayList {
	    
	    private List<HuffmanNode> binaryHeap;
	    
	    //Constructor
	    public BinaryHeapArrayList() {
	    	binaryHeap = new ArrayList<>();
	    }

	    private int getParent(int n) {	return n == 0 ? -1 : (n - 1) >>> 1; }
	    private int left(int n) { return n * 2 + 1; }
	    private int right(int n) { return n * 2 + 2; }
	    public int size() { return binaryHeap.size(); }

	    private int minChildIndex(int n) {
	        if (left(n) > binaryHeap.size() - 1) return -1;
	        if (right(n) > binaryHeap.size() - 1) return left(n);
	        return binaryHeap.get(left(n)).freq <= binaryHeap.get(right(n)).freq ? left(n) : right(n);
	    }

	    
	    public void insertIntoBinaryHeap(HuffmanNode newElement) {
	    	binaryHeap.add(newElement);
	    	int newElementIndex = binaryHeap.size() - 1;
	    	int parentIndex = getParent(newElementIndex);
	        while (parentIndex != -1 && (binaryHeap.get(parentIndex).freq > binaryHeap.get(newElementIndex).freq)) {
	            swap(parentIndex, newElementIndex);
	            newElementIndex = parentIndex;
	            parentIndex = getParent(newElementIndex);
	        }
	    }

	   
	    public HuffmanNode removeMin() {
	        if (binaryHeap.size() == 0){
	        	return null;
	        }

	        HuffmanNode result = binaryHeap.get(0);
	        binaryHeap.set(0, binaryHeap.get(binaryHeap.size() - 1));
	        binaryHeap.remove(binaryHeap.size() - 1);
	        
	        int n = 0;
	        int minChildIndex = minChildIndex(n);
	        while (minChildIndex != -1 && binaryHeap.get(minChildIndex).freq < binaryHeap.get(n).freq) {
	            swap(minChildIndex, n);
	            n = minChildIndex;
	            minChildIndex = minChildIndex(n);
	        }
	        return result;
	    }

	    public boolean isHeap() {
	        for (int i = 1; i < binaryHeap.size(); ++i) {
	            if (getParent(i) >= 0) {
	                if (binaryHeap.get(getParent(i)).freq > binaryHeap.get(i).freq) {
	                    return false;
	                }
	            }
	        }
	        return true;
	    }

	  
	    private void swap(int i, int j) {
	        HuffmanNode tmp = binaryHeap.get(i);
	        binaryHeap.set(i, binaryHeap.get(j));
	        binaryHeap.set(j, tmp);
	    }

	    
	    public List<HuffmanNode> build_tree_using_binary_heap(Map<String, Integer> frequencyTable){
	    	Iterator it = frequencyTable.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        insertIntoBinaryHeap(new HuffmanNode(pair.getKey().toString(),(int)pair.getValue()));
		        //it.remove(); // avoids a ConcurrentModificationException
		    }
		    return binaryHeap;   
	    }    
}
