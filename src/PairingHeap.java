import java.util.Iterator;
import java.util.Map;    
 
class PairingHeap
{
    private HuffmanNode root; 
    private HuffmanNode [ ] treeArray = new HuffmanNode[ 5 ];
 
    public PairingHeap( ){
        root = null;
     }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public void makeEmpty() {
        root = null;
    }
    
   
    public HuffmanNode insertIntoPairingHeap(HuffmanNode newElement)
    {
    	HuffmanNode newNode = new HuffmanNode(newElement.data, newElement.freq);
        if (root == null)
            root = newNode;
        else
            root = compareAndLink(root, newNode);
        return root;
    }
    /* Function compareAndLink */
    private HuffmanNode compareAndLink(HuffmanNode first, HuffmanNode second)
    {
        if (second == null)
            return first;
 
        if (second.freq < first.freq)
        {
            /* Attach first as leftmost child of second */
            second.prev = first.prev;
            first.prev = second;
            first.nextSibling = second.leftChild;
            if (first.nextSibling != null)
                first.nextSibling.prev = first;
            second.leftChild = first;
            return second;
        }
        else
        {
            /* Attach second as leftmost child of first */
            second.prev = first;
            first.nextSibling = second.nextSibling;
            if (first.nextSibling != null)
                first.nextSibling.prev = first;
            second.nextSibling = first.leftChild;
            if (second.nextSibling != null)
                second.nextSibling.prev = second;
            first.leftChild = second;
            return first;
        }
    }
    private HuffmanNode combineSiblings(HuffmanNode firstSibling)
    {
        if( firstSibling.nextSibling == null )
            return firstSibling;
        /* Store the subtrees in an array */
        int numSiblings = 0;
        for ( ; firstSibling != null; numSiblings++)
        {
            treeArray = doubleIfFull( treeArray, numSiblings );
            treeArray[ numSiblings ] = firstSibling;
            /* break links */
            firstSibling.prev.nextSibling = null;  
            firstSibling = firstSibling.nextSibling;
        }
        treeArray = doubleIfFull( treeArray, numSiblings );
        treeArray[ numSiblings ] = null;
        /* Combine subtrees two at a time, going left to right */
        int i = 0;
        for ( ; i + 1 < numSiblings; i += 2)
            treeArray[ i ] = compareAndLink(treeArray[i], treeArray[i + 1]);
        int j = i - 2;
        /* j has the result of last compareAndLink */
        /* If an odd number of trees, get the last one */
        if (j == numSiblings - 3)
            treeArray[ j ] = compareAndLink( treeArray[ j ], treeArray[ j + 2 ] );
        /* Now go right to left, merging last tree with */
        /* next to last. The result becomes the new last */
        for ( ; j >= 2; j -= 2)
            treeArray[j - 2] = compareAndLink(treeArray[j-2], treeArray[j]);
        return treeArray[0];
    }
    private HuffmanNode[] doubleIfFull(HuffmanNode [ ] array, int index)
    {
        if (index == array.length)
        {
        	HuffmanNode [ ] oldArray = array;
            array = new HuffmanNode[index * 2];
            for( int i = 0; i < index; i++ )
                array[i] = oldArray[i];
        }
        return array;
    }
    /* Delete min element */
    public HuffmanNode deleteMin( )
    {
        if (isEmpty( ))
            return null;
        HuffmanNode x = root;
        
        if (root.leftChild != null)
        	root = combineSiblings( root.leftChild );
        	
        return x;
    }
    /* inorder traversal */
    public void inorder()
    {
        inorder(root);
    }
    private void inorder(HuffmanNode r)
    {
        if (r != null)
        {
            inorder(r.leftChild);
            System.out.print(r.data +":"+r.freq);
            inorder(r.nextSibling);
        }
    }
    
    public HuffmanNode build_tree_using_pairing_heap(Map<String, Integer> frequencyTable) 
    {
    	Iterator it = frequencyTable.entrySet().iterator();
    	HuffmanNode root = null;
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        root = insertIntoPairingHeap(new HuffmanNode(pair.getKey().toString(),(int)pair.getValue()));
	        //it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    return root;
    }
}
 