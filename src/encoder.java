
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class encoder {
	BufferedReader br = null;
	static Map<String,String> codeTable = new HashMap<>();
	public BufferedReader ReadFile(String filePath){
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File Not Found");
			System.exit(0);
		}
		return br;
	}
	
	public Map<String,Integer> createFrequencyHashMap(BufferedReader br){
		String currValue = null;
		Map<String,Integer> frequencyTable = new HashMap<String,Integer>();
		int temp;
		try {
			while ((currValue = br.readLine()) != null && !"".equals(currValue)) {
				if(frequencyTable.get(currValue) == null){
					frequencyTable.put(currValue,1);
				}
				else if(currValue != "\n"){
					temp = frequencyTable.get(currValue);
					frequencyTable.put(currValue,++temp);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return frequencyTable;
	}
	
	public Map<String,String> printCodes(HuffmanNode root, String pattern)
	{
	    if (root == null)
	        return null;
	 
	    if (root.data != "$"){
	        codeTable.put(root.data, pattern);
	    }
	    printCodes(root.left, pattern+'0');
	    printCodes(root.right, pattern+'1');
	    return codeTable;
	}
	
	public Map<String,String> constructHuffmanTree4wayHeap(FourWayCacheOptimizedHeap fwh, List<HuffmanNode> heap){
	    HuffmanNode left, right , parent = null; 
	    // Iterate while size of heap doesn't become <5
	    while (heap.size() >= 5)
	    {
	        // Extract the two minimum freq items from min heap
	    	left = fwh.removeMin();  
	    	right = fwh.removeMin();
	    	
	        parent = new HuffmanNode("$", left.freq + right.freq);
	        parent.left = left;
	        parent.right = right;
	        fwh.insertInto4wayHeap(parent);
	    }
	    System.out.println("**** Constructed Huffman Tree ****");
	    return printCodes(parent, "");
	}
	
	
	public Map<String,String> build_tree_using_4way_heap(Map freq_table){
		FourWayCacheOptimizedHeap fwh = new FourWayCacheOptimizedHeap();
		List<HuffmanNode> fourWayHeap = fwh.build_tree_using_4way_heap(freq_table);
		System.out.println("**** Built 4 way Heap Priority Queue ****");
		return constructHuffmanTree4wayHeap(fwh, fourWayHeap);
	}
	
	public void createCodeTableTxt(Map<String,String> codeTable) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		String FILENAME = "code_table.txt";
		try {
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			Iterator it = codeTable.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        bw.write(pair.getKey() + " " + pair.getValue()+"\n");
		        //it.remove(); // avoids a ConcurrentModificationException
		    }
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null){
					fw.close();
					System.out.println("Created Code Table : code_table.txt");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}		
	}
	
public void encode(Map<String,String> codeTable, String filePath){
		
		String currValue = "";
		BufferedReader br = ReadFile(filePath);
		OutputStream out = null;
		try {
			out = new FileOutputStream("encoded.bin");
			StringBuilder contentBuffer = new StringBuilder();
			while ((currValue = br.readLine()) != null) {
				if(codeTable.get(currValue) != null) {
					contentBuffer.append(codeTable.get(currValue));
				}
			}
			String content = contentBuffer.toString();
			BitSet bitSet = new BitSet(content.length());
			
			for(int i = 0; i < content.length(); i++) {
			    if(contentBuffer.charAt(i) == '1') {
			        bitSet.set(i);
			    }
			}
			out.write(bitSet.toByteArray());	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(out!=null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
		
	public static void main(String args[]){
			
		String smallfilePath = "M:/ADS programming project/Sample1/sample_input_small.txt";
		String largefilePath = "M:/ADS programming project/Sample2/sample_input_large.txt";
		encoder encoderObj = new encoder();
		BufferedReader br = null;
		if(args.length == 0 ){
              System.out.println("Please enter the input filepath");
              System.exit(0);
        }

		br = encoderObj.ReadFile(args[0]);
		
		Map<String,Integer> frequencyTable = null;
		
		Map<String,String> codeTable = new HashMap<>();
		
		frequencyTable = encoderObj.createFrequencyHashMap(br);
		
		long startTime,endTime;
		// binary heap
		startTime = System.currentTimeMillis();
		codeTable = encoderObj.build_tree_using_4way_heap(frequencyTable);
		endTime = System.currentTimeMillis();
		System.out.println("Time using binary heap (milliseconds):"+(endTime - startTime));
				
		encoderObj.createCodeTableTxt(codeTable);
		System.out.println("Started encoding.....");
		encoderObj.encode(codeTable,args[0]);		
		System.out.println("**** End : Created encoded file ****");		
	}
}
