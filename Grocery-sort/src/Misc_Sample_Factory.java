import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Misc_Sample_Factory {
	
	String file_name = "bin/dataset.csv";
	String splitter  = ",";
	private static double[]   thresholds = {162.2, 6.1, 5.2, 9.3};
		
	
	public ArrayList<Sample> getList(){ //reads from file dataset and returns an arrayList of sample objs
		ArrayList<Sample> produceList = new ArrayList<Sample>();
		BufferedReader reader;
		String line = "";
		String[] entry;
		
		try {
			reader = new BufferedReader(new FileReader(file_name));
			
			while ((line = reader.readLine()) != null){
				entry = line.split(splitter);
				produceList.add(makeSample(entry));
			}
			reader.close();
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
		}
		
		return produceList;
	}
	//TODO make sample properly
	
	private Sample makeSample(String[] data){ //helper*, takes string[] and turns it into double[] to make new samples
		Sample newSample;
		double[] sampleData = new double[5];
		int[] binaryData = new int[10];
		
		for (int i =0; i<5; i++) {
			sampleData[i] = Double.valueOf(data[i]);
		}
		binaryData[0] = (int) sampleData[0];
		
		for (int i=1; i<5; i++){ //for each feature, if the value is greater than the threshold, set to true, else false
			if (sampleData[i] > thresholds[i-1]){
				binaryData[i] = 1; 
			} else {
				binaryData[i] = 0; 
			}
		}
		int temp=0;
		String col = data[5];
		if 		  (col.equals("yellow")) {
			temp =5;
		} else if (col.equals("orange")) {
			temp =6;
		} else if (col.equals("brown")) {
			temp =7;
		} else if (col.equals("red")) {
			temp =8;
		} else if (col.equals("green")) {
			temp =9;
		}
		
		for (int i=5; i<10; i++){
			if (i == temp){
				binaryData[i] = 1;
			} else {
				binaryData[i] = 0;
			}
		}
		
		newSample = new Sample(binaryData, 10, sampleData);
		
		return newSample;
	}
	
	
};