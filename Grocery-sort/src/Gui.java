import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Gui extends JFrame{
	private Controller c;
	private JPanel contentPane;
	private Sample dispSamp;
	//contents
	private JLabel		fruitPic		= new JLabel("");
	
	private JLabel		colourLabel		= new JLabel("Colour: ");
	private JLabel		weightLabel		= new JLabel("Weight(g): ");
	private JLabel		widthLabel		= new JLabel("Width (cm): ");
	private JLabel		heightLabel		= new JLabel("Height(cm): ");
	private JLabel		lengthLabel		= new JLabel("Length(cm): ");
	
	private JLabel		colourVal		= new JLabel("");
	private JLabel		weightVal		= new JLabel("");
	private JLabel		widthVal		= new JLabel("");
	private JLabel		heightVal		= new JLabel("");
	private JLabel		lengthVal		= new JLabel("");
	
	private JLabel		classLabel		= new JLabel("This is a(n):");
	private JLabel		classVal		= new JLabel("6");
	
	private JButton 	 nextButton 		= new JButton("Next Sample");
	private JButton 	 sortButton 		= new JButton("Sort");
	
	public Gui(Controller ctrl){
		c = ctrl;
		setResizable(false);
		setSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel(new GridBagLayout());
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		GridBagConstraints c = new GridBagConstraints();
		
		getNextSample();
		
		//display panel
		c.gridx      = 1;
		c.gridy      = 0;
		c.gridheight = 3;
		c.gridwidth  = 3;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 2;
		c.anchor     = GridBagConstraints.CENTER;
		contentPane.add(fruitPic, c);
		
		//info
		c.gridx      = 0;
		c.gridy      = 3;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.CENTER;
		contentPane.add(colourLabel, c);
		
		c.gridx      = 1;
		c.gridy      = 3;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.WEST;
		contentPane.add(colourVal, c);
		
		c.gridx      = 0;
		c.gridy      = 4;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.CENTER;
		contentPane.add(weightLabel, c);
		
		c.gridx      = 1;
		c.gridy      = 4;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.WEST;
		contentPane.add(weightVal, c);
		
		c.gridx      = 2;
		c.gridy      = 3;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.CENTER;
		contentPane.add(widthLabel, c);
		
		c.gridx      = 3;
		c.gridy      = 3;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.WEST;
		contentPane.add(widthVal, c);
		
		c.gridx      = 2;
		c.gridy      = 4;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.CENTER;
		contentPane.add(heightLabel, c);
		
		c.gridx      = 3;
		c.gridy      = 4;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.WEST;
		contentPane.add(heightVal, c);
		
		c.gridx      = 2;
		c.gridy      = 5;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.CENTER;
		contentPane.add(lengthLabel, c);
		
		c.gridx      = 3;
		c.gridy      = 5;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.WEST;
		contentPane.add(lengthVal, c);
		
		c.gridx      = 4;
		c.gridy      = 3;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.SOUTH;
		contentPane.add(classLabel, c);
		
		c.gridx      = 4;
		c.gridy      = 4;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor     = GridBagConstraints.CENTER;
		contentPane.add(classVal, c);
		
		//next button
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	nextButtonHandler();}	
		});
		c.gridx      = 4;
		c.gridy      = 0;
		c.gridheight = 1;
		c.gridwidth  = 2;
		c.weightx    = 0;
		c.weighty    = 0.5;
		c.fill       = 2;
		c.anchor     = GridBagConstraints.CENTER;
		contentPane.add(nextButton, c);
		
		
		//sort button
		sortButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	sortButtonHandler();}	
		});
		c.gridx      = 4;
		c.gridy      = 1;
		c.gridheight = 1;
		c.gridwidth  = 2;
		c.weightx    = 0;
		c.weighty    = 0.5;
		c.fill       = 2;
		c.anchor     = GridBagConstraints.CENTER;
		contentPane.add(sortButton, c);
		

		setVisible(true);
	}
	
	private void getNextSample(){
		int randInt = ThreadLocalRandom.current().nextInt(0, c.miscList.size() + 1);
		dispSamp = c.miscList.get(randInt);
		
		colourVal.setText(dispSamp.getColour());
		weightVal.setText(dispSamp.getDetFeature(0)+"");
		widthVal.setText(dispSamp.getDetFeature(1)+"");
		heightVal.setText(dispSamp.getDetFeature(2)+"");
		lengthVal.setText(dispSamp.getDetFeature(3)+"");
		classVal.setText("");
		
		randInt = ThreadLocalRandom.current().nextInt(1, 6);
		String imageFile = "pics/";
		imageFile += dispSamp.type;
		imageFile += randInt;
		imageFile +=".jpg";
		
		//TODO resize image
		ImageIcon ic = createImageIcon(imageFile);
		
		Image im = ic.getImage();
		Image scaledIm = im.getScaledInstance(300,  300, java.awt.Image.SCALE_SMOOTH);
		
		ic = new ImageIcon(scaledIm);
		fruitPic.setIcon(ic);;
		

	}
	
	private ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	
	private void nextButtonHandler(){
		getNextSample();
	}
	
	private void sortButtonHandler(){
		c.bestSorter.testSample(dispSamp);
		classVal.setText(""+dispSamp.type);
	}
	
	private void update(){
		
	}
	
	
	
};
