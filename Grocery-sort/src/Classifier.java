import java.util.ArrayList;
import java.util.Arrays;

public  class Classifier {
	double[][] probTrues; //probTrues[class][feature]
	int numC;
	int numF;
	boolean trained;
	
	public Classifier(Controller.types t){
		numC = 14;
		numF = 9;
	}
	public void trainIndependant(ArrayList<Sample> trainers) {	
		
		probTrues = new double[numC][numF];
		double[] classCount = new double[numC];
		double[] classSum = new double[numC];
		
		for (int samp=0; samp<trainers.size(); samp++){
			for (int c=0; c<numC; c++){
				classCount[trainers.get(samp).getTrueClass()-1]++; //count the # of sample of each class
			}	
		}
		
		for (int f=0; f<numF; f++){ //for each feature
			for (int j=0; j<trainers.size(); j++){ //for each test sample
				classSum[trainers.get(j).getTrueClass()-1] += trainers.get(j).getFeature(f);
			}
			
			for (int c=0; c<numC; c++){
				probTrues[c][f] = classSum[c]/classCount[c];
				classSum[c] = 0;
			}
		}

//		System.out.println("feature "+i+", c1: "+c1_probTrues[i]+", c2: "+c2_probTrues[i]+", c3: "+c3_probTrues[i]);
		trained = true;
	}
	


	public double testSet(ArrayList<Sample> testList){ //tests each sample, returns accuracy
		double accuracy, results =0;
		
		for (int i=0; i<testList.size(); i++){
			results += testSample(testList.get(i));
		}		
		
		accuracy = results / testList.size();
//		System.out.println(accuracy*100+"% accuracy");
		return accuracy;
	}
	
	private int testSample(Sample s){ //classifies a sample, returns 1 if sorted correctly, else 0 (-1 if err)
		double[] probC = new double[numC];
		Arrays.fill(probC, 1);
		
		if (!trained){
			System.out.println("untrained classifier");
		}
		
		for (int c=0; c<numC; c++){
			for (int f=0; f<numF; f++){
				if (s.getFeature(f) == 1){
					probC[c] *= probTrues[c][f];
				} else {
					probC[c] *= (1 - probTrues[c][f]);
				}
			}	
		}
		
		int maxIndex =0;
		for (int i=0; i<numC; i++){
			if(probC[i] > probC[maxIndex]){
				maxIndex = i;
			}
		}
		s.sort(maxIndex+1); // sort into max prob class
		
//		System.out.println(s+"    classified as "+s.getSortedClass());
		
		return (s.getSortedClass()==s.getTrueClass())?1:0; 
	}

};
