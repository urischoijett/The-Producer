import java.util.ArrayList;
import java.util.Collections;

public class Controller {
		public static enum types {MISC};
		public Gui ui;
		public DTClassifier bestSorter;
		public ArrayList<Sample> miscList;
		Misc_Sample_Factory miscFact = new Misc_Sample_Factory();
		
		public Controller(){
			//bayesian
//			miscList = miscFact.getList();		
//			crossTrain(miscList, 5, types.MISC);
			
			//dt
			miscList = miscFact.getList();
			crossTrainDT(miscList, 10, types.MISC);

//			printSet(miscList);
			
		}

		private void crossTrain(ArrayList<Sample> list, int x, types t){ //x-fold cross validations
			
			Classifier[] sorters = new Classifier[x];  //one classifier for each training set
			ArrayList<Sample> trainingSet = new ArrayList<Sample>();
			ArrayList<Sample> testingSet  = new ArrayList<Sample>();
			
			double results =0, accuracy;
			Collections.shuffle(list);
			for (int i=0; i<x; i++){ //5-fold cross validation
				
				//set up sets
				trainingSet.clear();
				testingSet.clear();
				
				for (int s =0; s<list.size(); s++){ //add each element to training or testing set
					if (s%x == i) {
						testingSet.add(list.get(s));
					} else {
						trainingSet.add(list.get(s));
					}
				}
				
				//train
				sorters[i] = new Classifier(t);
				sorters[i].trainIndependant(list); 
				
				//test
				results += sorters[i].testSet(testingSet);
			}
			accuracy = results/x;
//			System.out.println("Average of "+accuracy*100+"% accuracy for "+t);
		}
		
		private void crossTrainDT(ArrayList<Sample> list, int x, types t){ //x-fold cross validations
			
			DTClassifier[] sorters = new DTClassifier[x];  //one classifier for each training set
			ArrayList<Sample> trainingSet = new ArrayList<Sample>();
			ArrayList<Sample> testingSet  = new ArrayList<Sample>();
			
			double results =0, accuracy=0, bestAccuracy=0;
			Collections.shuffle(list);
			for (int i=0; i<x; i++){ //5-fold cross validation
				
				//set up sets
				trainingSet.clear();
				testingSet.clear();
				
				for (int s =0; s<list.size(); s++){ //add each element to training or testing set
					if (s%x == i) {
						testingSet.add(list.get(s));
					} else {
						trainingSet.add(list.get(s));
					}
				}
				
				//train
				sorters[i] = new DTClassifier(t);
				sorters[i].buildTree(trainingSet); 
				
				//test
				accuracy = sorters[i].testSet(testingSet);
				
				if (accuracy > bestAccuracy){
					bestSorter = sorters[i];
				}
			}

		}
		
		private void printSet(ArrayList<Sample> set){
			for (int i=0; i<set.size(); i++){
				System.out.println(set.get(i));
			}
		}

};
