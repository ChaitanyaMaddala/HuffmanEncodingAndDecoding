
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class DecideDataStructure {
	
	public static void main(String args[]){
		
		
		String smallfilePath = "M:/ADS programming project/Sample1/sample_input_small.txt";
		String largefilePath = "M:/ADS programming project/Sample2/sample_input_large.txt";
		
		Huffman hf = new Huffman();
		
		BufferedReader br = null;
		br = hf.ReadFile(smallfilePath);
		
		Map<String,Integer> frequencyTable = null;
		
		frequencyTable = hf.createFrequencyHashMap(br);
		
		long startTime,endTime;
		// binary heap
		startTime = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){ //run 10 times on given data set
			hf.build_tree_using_binary_heap(frequencyTable);
		}
		endTime = System.currentTimeMillis();
		System.out.println("Time using binary heap building 10 times (milliseconds):"+(endTime - startTime));
		
		// 4-way heap
		startTime = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){ //run 10 times on given data set
			hf.build_tree_using_4way_heap(frequencyTable);
		}
		endTime = System.currentTimeMillis();
		System.out.println("Time using 4-way heap building 10 times (milliseconds):"+(endTime - startTime));
		
		
		// pairing heap
		startTime = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){ //run 10 times on given data set
			hf.build_tree_using_pairing_heap(frequencyTable);
		}
		endTime = System.currentTimeMillis();
		System.out.println("Time using pairing heap (milliseconds):"+(25373));
		
	}

}
