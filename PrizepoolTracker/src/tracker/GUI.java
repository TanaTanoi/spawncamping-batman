package tracker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;






import javax.swing.*;
/**
 * Abstract interface to control the GUI interface itself
 * 
 * @author Tana Tanoi
 *
 */
public abstract class GUI {

	/*Frame size variables*/
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 250;

	/*Graphics pane variables*/
	private static final Dimension GRAPH_DIM = new Dimension(350,150);
	private static final Font GRAPH_FONT = new Font("Sans-Serif", Font.PLAIN, 12);
	private JComponent graph;
	

	/*Text field variables*/
	private static final Dimension VALUE_DIM = new Dimension(FRAME_WIDTH,50);
	JTextField value;
	
	/*Update button variables*/
	private static final Dimension UPDATE_DIM = new Dimension(100,FRAME_HEIGHT);
	
	/*Abstract methods*/
	public abstract void onButtonUpdate();
	
	
	
	public abstract void render(Graphics g);
	
	public Graphics getGraphics(){
		return graph.getGraphics();
	}
	public void setText(String input){
		value.setText(input);
	}
	
	private JFrame frame;

	public GUI(){
		initialise();}
	
	@SuppressWarnings("serial")
	private void initialise() {
		
		/*Set up the frame*/
		frame = new JFrame();
		frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.LINE_AXIS));
		frame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*Set up the drawing component for the graph*/
		graph = new JComponent(){
			protected void paintComponent(Graphics g) {
				//render(g);
			}
		};
		graph.setSize(GRAPH_DIM);
		graph.setMinimumSize(GRAPH_DIM);
		graph.setMaximumSize(GRAPH_DIM);
		graph.setPreferredSize(GRAPH_DIM);
		graph.setVisible(true);
		graph.setFont(GRAPH_FONT);
		
		/* Set up the text field to contain the value itself*/
		value = new JTextField();
		value.setSize(VALUE_DIM);
		value.setMaximumSize(VALUE_DIM);
		value.setMinimumSize(VALUE_DIM);
		value.setVisible(true);
		
		/*Combine the ouput components together*/
		JPanel output = new JPanel(new BorderLayout());
		output.setSize(new Dimension(GRAPH_DIM.width,GRAPH_DIM.height+VALUE_DIM.height));
		output.add(value,BorderLayout.SOUTH);
		output.add(graph,BorderLayout.NORTH);
		
		/*Set up the update button*/
		JButton updateButton = new JButton("Update");
		
		updateButton.setPreferredSize(UPDATE_DIM);
		updateButton.setMaximumSize(UPDATE_DIM);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				onButtonUpdate();
		}});
		
		frame.add(output);
		frame.add(updateButton);
		frame.pack();
		frame.setVisible(true);
		
	}
}
