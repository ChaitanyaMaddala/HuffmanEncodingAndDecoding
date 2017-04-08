import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;

class Node{
	int data;
	Node left;
	Node right;
};

public class decoder {
	
	public static Node createNode(int data){
			Node tmp = new Node();
			tmp.data = data;
			tmp.left = null;
			tmp.right = null;
			return tmp;
	}
	
	public BufferedReader ReadFile(String filePath){
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found, please verify");
			System.exit(0);
		}
		return br;
	}
	
	public static Node constructDecodeTree(BufferedReader br){		
		String currValue = "";
		Node root = createNode(-1);
		try {
			while ((currValue = br.readLine()) != null) {
				String[] values = currValue.split(" ");
				String key = values[0];
				String code = values[1];
				int i = 0;
				Node curr = root;
				int codelen = code.length();
				while(i < codelen-1){
					if(code.charAt(i) == '0'){
						if(curr.left == null){
							curr.left = createNode(-1);
						}
						curr = curr.left;
					}
					else if(code.charAt(i) == '1'){
						if(curr.right == null) {
							curr.right = createNode(-1);
						}
						curr = curr.right;
					}
					i++;
				}
				if(code.charAt(i) == '0')
						curr.left = createNode(Integer.parseInt(key));
				else if(code.charAt(i) == '1')
						curr.right = createNode(Integer.parseInt(key));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}
	
	public void decodeEncodedFile(Node root, String encodedFilePath){
		FileWriter fw = null;
		BufferedWriter bw = null;
		FileInputStream fis = null;
		File file = null;
		char c;
		BitSet bitset = null;
		StringBuffer sb = null;
		byte[] fileData = null;
		try {
			String FILENAME = "decoded.txt";
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			file = new File(encodedFilePath);
			fis = new FileInputStream(file);
			fileData = new byte[(int) file.length()];
			fis.read(fileData);
			fis.close();
			
			Node curr = root;
			sb = new StringBuffer();
		
			bitset = new BitSet();
			bitset = BitSet.valueOf(fileData);
		
			int z = 0;
			int len = fileData.length * 8;
			while(z<=len){
				if(bitset.get(z))
					sb.append('1');
				else
					sb.append('0');
				z++;
			}
			
			for(int k=0; k < sb.length(); k++){
				 c = sb.charAt(k);
				 if(curr.left == null && curr.right == null){
					 bw.write(curr.data + "\n");
					 curr = root;
				 }
				 if(c == '0' && curr.left != null)
            		 curr = curr.left;
            	 else if(c == '1' && curr.right != null)
            		 curr = curr.right;
			}			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
		public static void main(String args[]){
			
			decoder decode = new decoder();
			
			String codeTablefilePath = "C:/Users/Chaitanya Maddala/EclipseCodeRepository/Huffman/code_table.txt";
			String encodedFilePath = "encoded.bin";
			if(args.length < 2){
				System.out.println("Please enter Encoded file path and Code Table file path");
			}
			BufferedReader br = decode.ReadFile(args[1]);
			
			Node root = constructDecodeTree(br);
			System.out.println("**** Constructed Decode Tree ****");
			
			System.out.println("Started decoding.....");
			decode.decodeEncodedFile(root, args[0]);
			System.out.println("**** End : Decoded the encoded file ****");
			
			
		}
	
}
