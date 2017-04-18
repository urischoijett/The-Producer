import java.util.ArrayList;
import java.util.Arrays;

public class DTClassifier {
	DTNode[] roots;
	int numC;
	int numF;
	
	public DTClassifier(Controller.types t){
		numC = 14;
		numF = 9;
		DTNode.numFeatures = numF;
		roots = new DTNode[numC];
		
	}
	
	public void testSample(Sample s){
		
		ArrayList<Sample> nextTestList = new ArrayList<Sample>();
		nextTestList.add(s);
		
		for (int i=0; i<roots.length; i++){
			nextTestList.clear();

			if (s.getSortedClass() < 1){
				nextTestList.add(s);
			}

			
			roots[i].temp.addAll(nextTestList);
			roots[i].pushSamples();
		}
	}
	
	public double testSet(ArrayList<Sample> testList){
		double accuracy, results =0;
		ArrayList<Sample> nextTestList = new ArrayList<Sample>();
		
		for (int i=0; i<roots.length; i++){
			nextTestList.clear();
			for (int s=0; s<testList.size(); s++){
				if (testList.get(s).getSortedClass() < 1){
					nextTestList.add(testList.get(s));
				}
			}
			
			roots[i].temp.addAll(nextTestList);
			roots[i].pushSamples();
		}
		
		
		
		for (int i=0; i<testList.size(); i++){ // how many were sorted correctly
			if (testList.get(i).getTrueClass()==testList.get(i).getSortedClass()){
				results++;
			}
			
		}	
		accuracy = results / testList.size();
		System.out.println(accuracy*100+"% accuracy");
		return accuracy;
	}
	
	public void buildTree(ArrayList<Sample> list){
		DTNode temp;
		
		for (int c=1; c<numC+1; c++ ){
			ArrayList<Integer> usedF = new ArrayList<Integer>();
			roots[c-1] = getNextNode(list, c, usedF);
		
			roots[c-1].buildChildren(list);		
			
		}
//		roots[0].printTree();
	}
	
	public DTNode getNextNode(ArrayList<Sample> list, int treeClass, ArrayList<Integer> usedF){
		ArrayList<Sample> list_f0 = new ArrayList<Sample>();  
		ArrayList<Sample> list_f1 = new ArrayList<Sample>();
		double entropy_s;
		int bestFeature=0;
		double[] gain	 = new double[numF]; //gain for each feature for comparison
		
		
		//entropy for set
		entropy_s = calcEntropy(list, treeClass);
		
		for (int f=0; f<numF; f++){ //entropy for all features
			list_f0.clear();
			list_f1.clear();
			
			for (int s=0; s<list.size(); s++){
				if (list.get(s).getFeature(f)==1){
					list_f1.add(list.get(s));
				} else {
					list_f0.add(list.get(s));
				}
			}
			gain[f] = Math.abs(entropy_s - calcEntropy(list_f0, treeClass) - calcEntropy(list_f1, treeClass)); 
			
		}
		for (int i=0; i<gain.length; i++){
			if(gain[i] > gain[bestFeature]){
				bestFeature = i;
			}
		}
		
		return new DTNode(bestFeature, treeClass, usedF);
	}
	
	
	private double calcEntropy(ArrayList<Sample> list, int treeClass){
		ArrayList<Sample> listTrue = new ArrayList<Sample>();  //list of the samples where class matches
		ArrayList<Sample> listFalse = new ArrayList<Sample>();
		double entropyS;
		double tempe1, tempe2, tempe3;
		double tempe4, tempe5, tempe6;
		
		for (int s=0; s<list.size(); s++){ //count the the number of samples part of treeClass (true)
			if (list.get(s).getTrueClass() == treeClass){
				listTrue.add(list.get(s));
			} else {
				listFalse.add(list.get(s));
			}
		}
		tempe1 = (double)listTrue.size() / (double)list.size();
		tempe2 = (Math.log(((double)listTrue.size()/(double)list.size()))/Math.log(2));
		tempe3 = tempe1*tempe2;
		tempe4 = (double)listFalse.size() / (double)list.size();
		tempe5 = (Math.log(((double)listFalse.size()/(double)list.size()))/Math.log(2));
		tempe6 = tempe3*tempe4;
		entropyS = -tempe3 - tempe6;
		
		return entropyS;
	}
};
