import java.util.ArrayList;
import java.util.Iterator;

public class DTNode {
	int f_id;
	public static int numFeatures;
	public int treeClass;
	public ArrayList<Sample> temp = new ArrayList<Sample>();
	public ArrayList<Integer> usedFeatures = new ArrayList<Integer>();
	public DTNode[] nodeChildren = new DTNode[2];  //the next feature to check [0] if this is false, [1] if this is true
	public int[]	classChildren = new int[2];	   //if there are no more features sort into class at [0] if false, [1] if true
	
	public DTNode(int feature_id, int treeC, ArrayList<Integer> usedF){
		f_id = feature_id;
		usedFeatures.addAll(usedF);
		usedFeatures.add(f_id);
		treeClass = treeC;
	}
	
	public void buildChildren(ArrayList<Sample> list){
		ArrayList<Sample> temp0 = new ArrayList<Sample>();
		ArrayList<Sample> temp1 = new ArrayList<Sample>();
		
		for (int s=0; s<list.size(); s++){
			if (list.get(s).getFeature(f_id) == 0){
				temp0.add(list.get(s));
			} else {
				temp1.add(list.get(s));
			}
		}
		nodeChildren[0] = getNextNode(temp0, treeClass, usedFeatures); //create child[0] then build its children
		if (nodeChildren[0] != null){
			nodeChildren[0].buildChildren(temp0);
		} else {
			classChildren[0] = buildClassChildren(temp0);
		}
		nodeChildren[1] = getNextNode(temp1, treeClass, usedFeatures); //create child[1] then build its children
		if (nodeChildren[1] != null){
			nodeChildren[1].buildChildren(temp1);
		} else {
			classChildren[1] = buildClassChildren(temp1);
		}
	}
	private int buildClassChildren(ArrayList<Sample> list){
		ArrayList<Sample> listTrue = new ArrayList<Sample>();  //list of the samples where class matches
		ArrayList<Sample> listFalse = new ArrayList<Sample>();
		for (int s=0; s<list.size(); s++){ //count the the number of samples part of treeClass (true)
			if (list.get(s).getTrueClass() == treeClass){
				listTrue.add(list.get(s));
			} else {
				listFalse.add(list.get(s));
			}
		}
		if(listTrue.size() > listFalse.size()) { //"yes"
//			System.out.println("found leaf yes");
			return treeClass;
		} else { //"no"
//			System.out.println("found leaf no");
			return -1;
		}

	}
	
	public DTNode getNextNode(ArrayList<Sample> list, int treeClass, ArrayList<Integer> usedF){
		ArrayList<Sample> list_f0 = new ArrayList<Sample>();  
		ArrayList<Sample> list_f1 = new ArrayList<Sample>();
		double entropy_s;
		int bestFeature= getUnused(usedF);
		double[] gain	 = new double[numFeatures]; //gain for each feature for comparison
		
		if (bestFeature == -1) { // there are no unused variables remaining
			return null;
		}
		//entropy for set
		entropy_s = calcEntropy(list, treeClass);
		
		for (int f=0; f<numFeatures; f++){ //entropy for all features
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
			if(gain[i] > gain[bestFeature] && !usedF.contains(i)){
				bestFeature = i;
			}
		}
//		System.out.println(bestFeature);
		
		return new DTNode(bestFeature, treeClass, usedF);
	}
	
	private int getUnused(ArrayList<Integer> used){

		for (int f=0; f<numFeatures; f++){
			if (!used.contains(f)){
				return f;
			}
		}
		return -1;
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
	
	public void pushSamples(){
		Iterator<Sample> itr = temp.iterator();
		Sample currSample;
		while(itr.hasNext()){  //for each sample on this node, push it down/classify it then remove from list
			currSample = itr.next();
			moveDown(currSample);
//			temp.remove(currSample);
		}
		if(nodeChildren[0] != null){
			nodeChildren[0].pushSamples();
		}
		if(nodeChildren[1] != null){
			nodeChildren[1].pushSamples();
		}
	}
	
	private void moveDown(Sample s){
		int val = s.getFeature(f_id);
		
		if (nodeChildren[val] != null) {  //if there is a child that way move the sample to that child
			//move to child
			nodeChildren[val].temp.add(s);
		} else {						//otherwise classify it
			s.sort(classChildren[val]);
		}
	}
	public void printTree(){
		System.out.println(this);
		String s="Children: ";
		if(nodeChildren[0] != null){
			s+= nodeChildren[0];
		} else {
			s+= "class "+classChildren[0];
		}
		s+=" | ";
		if(nodeChildren[1] != null){
			s+= nodeChildren[1];
		} else {
			s+= "class "+classChildren[1];
		}
		System.out.println(s);
		
		if(nodeChildren[0] != null){
			nodeChildren[0].printTree();
		}
		if(nodeChildren[1] != null){
			nodeChildren[1].printTree();
		}
	}
	
	public String toString(){
		String S="Node for feature "+f_id;
		
		return S;
	}
};
