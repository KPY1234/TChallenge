package preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SplitCustomer {
	
	String header;
	
	HashMap<String, ArrayList<String>> SplitCustomerData;
	
	ArrayList<String> customerIDs;
	
	public SplitCustomer(){
		
		customerIDs = new ArrayList<String>();
		
		try {
			SplitCustomerData = getCustomerData();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		try {
			run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private HashMap<String, ArrayList<String>> getCustomerData() throws IOException{
		
		HashMap<String, ArrayList<String>> SplitCustomerData = new HashMap<String, ArrayList<String>>();
		
		BufferedReader br = new BufferedReader(new FileReader("./data/train_data.csv"));
		
		String line = br.readLine();
		
		header = line;
		System.out.println(header);
		
		line = br.readLine();
		
		int count = 0;
		while(line!=null){
			count++;
			
			if(count%100000==0)
				System.out.println(count);
			
//			System.out.println(line);
			
			if(line.charAt(line.length()-1)==',')
				line += "null";
			
//			System.out.println(line);
			
			String[] lineSplit = line.split(",");
			
			for(int i=0;i<lineSplit.length;i++){
				if(lineSplit[i].isEmpty())
					lineSplit[i] = "null";
				
			}
			String newLine = "";
			
			for(int i=0;i<lineSplit.length;i++){
				newLine += lineSplit[i];
				if(i!=lineSplit.length-1)
					newLine += ",";
			}
				
			
			String[] newLineSplit = newLine.split(",");
			
			
			String customerID = newLineSplit[5];
			
			if(!customerIDs.contains(customerID))
				customerIDs.add(customerID);
			
			if(!SplitCustomerData.containsKey(customerID)){
				ArrayList<String> personelDataList = new ArrayList<String>();
				personelDataList.add(newLine);
				SplitCustomerData.put(customerID, personelDataList);
			}else{
				
				ArrayList<String> personelDataList = SplitCustomerData.get(customerID);
				personelDataList.add(newLine);
			}
			
			
			
//			System.out.println(customerID);
//			System.out.println(newLine);
//			System.out.println(lineSplit.length);
			
			line = br.readLine();
		}
		
	
		

		br.close();
		
		return SplitCustomerData;
	}
	
	private void run() throws IOException{
		
		
		for(int i=0;i<customerIDs.size();i++){
			
			String cusID = customerIDs.get(i);
			ArrayList<String> personelDataList = SplitCustomerData.get(cusID);
			
			outputPersonelFile(cusID, personelDataList);
		}
		
		
				
		
		
	}
	
	
	
	private void outputPersonelFile(String cusID, ArrayList<String> personelDataList) throws IOException{
		
		String filePath = "./data/train_data_"+cusID+".csv";
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
		bw.write(header+"\n");
		
		for(int i=0;i<personelDataList.size();i++){
			
			bw.write(personelDataList.get(i)+"\n");
			
		}
		
		bw.close();
	}
	
	public static void main(String[] args) {
		
		
		SplitCustomer sc = new SplitCustomer();
		
	}
	
}
