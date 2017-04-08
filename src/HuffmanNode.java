

public class HuffmanNode {
	String data;                
	Integer freq;             
	HuffmanNode left, right, one, two, three, four; 
	HuffmanNode leftChild, nextSibling, prev;
   
	HuffmanNode(String data, Integer freq)
	    {
	        left = right = null;
	        this.data = data;
	        this.freq = freq;
	        leftChild = nextSibling = prev = null;
	    }
}
