
public class Sample {
	public static enum produce_names {banana, apricot, strawberry, apple, orange, grape, pear, pineapple, kiwi, carrot, tomato, cucumber, celery, potato};
	public static enum feature_names {WEIGHT, X, Y, Z};
	public static enum fruit_colours {YELLOW, ORANGE, BROWN, RED, GREEN};
	
	private int true_class;
	private int sorted_class;
	public produce_names type;
	private int num_features, num_classes;
	private int[] features_binary;
	private double[] features_detailed;
	private Sample.fruit_colours col;
	
	public Sample(int[] new_features, int nc, double[] detF){
		features_binary = new int[new_features.length-1];
		num_features = new_features.length-1;
		num_classes = nc;
		
		//assign class and features
		true_class = new_features[0];
		sorted_class = -1; //unsorted
		for (int i=0; i<new_features.length-1; i++){ 
			features_binary[i] = new_features[i+1];
		}
		
		if (features_binary[4] == 1){
			col = fruit_colours.YELLOW;
		} else if (features_binary[5] == 1){
			col = fruit_colours.ORANGE;
		} else if (features_binary[6] == 1){
			col = fruit_colours.BROWN;
		} else if (features_binary[7] == 1){
			col = fruit_colours.RED;
		} else if (features_binary[8] == 1){
			col = fruit_colours.GREEN;
		}
		
		features_detailed = new double[detF.length-1];
		for (int i=0; i<detF.length-1; i++){ 
			features_detailed[i] = detF[i+1];
		}
		
		if (true_class == 1){
			type = produce_names.banana;
		} else if (true_class == 2) {
			type = produce_names.apricot;
		} else if (true_class == 3) {
			type = produce_names.strawberry;
		} else if (true_class == 4) {
			type = produce_names.apple;
		} else if (true_class == 5) {
			type = produce_names.orange;
		} else if (true_class == 6) {
			type = produce_names.grape;
		} else if (true_class == 7) {
			type = produce_names.pear;
		} else if (true_class == 8) {
			type = produce_names.pineapple;
		} else if (true_class == 9) {
			type = produce_names.kiwi;
		} else if (true_class == 10) {
			type = produce_names.carrot;
		} else if (true_class == 11) {
			type = produce_names.tomato;
		} else if (true_class == 12) {
			type = produce_names.cucumber;
		} else if (true_class == 13) {
			type = produce_names.celery;
		} else if (true_class == 14) {
			type = produce_names.potato;
		}
		
	}
	public void setColour(Sample.fruit_colours c){
		col = c;
	}
	public String getColour(){
		return col.toString();
	}
	public void sort(int c){ // sort into class n
		sorted_class = c;
			
	}
	public double getDetFeature(int n){
		return features_detailed[n];
	}
	public int getFeature(int n) {
		return features_binary[n];
	}
	public int getNumClases(){
		return num_classes;
	}
	public int getNumFeatures(){
		return num_features;
	}
	public int getTrueClass(){
		return true_class;
	}
	public int getSortedClass(){
		return sorted_class;
	}
	public String toString(){
		String s="";
		s+="class: "+true_class;
//		s+=" colour: "+ col;
		for (int i=0; i<features_binary.length; i++){
			s+=", f"+(i+1)+": "+features_binary[i];
		}
				
		return s;
	}

};
