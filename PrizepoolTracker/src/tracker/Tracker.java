package tracker;

import java.awt.Graphics;
import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tracker extends GUI {

	private static URL dota2;
	private static final String historyPath = "history.txt";
	private static final String url = "http://www.dota2.com/international/compendium/";
	private static Pattern prizeRegex = Pattern.compile("\\<div id=\\\"PrizePoolText\\\"\\>\\$(?<prize>[0-9,]+)\\</div\\>");
	public Tracker(){
		super();
	}


	public static void main(String[] args)throws Exception{
		dota2 =  new URL(url);
		new Tracker();
	}


	@Override
	public void onButtonUpdate() {
		String output = "Error";
		try{
			BufferedReader br = new BufferedReader(
					new InputStreamReader(dota2.openStream()));
			while(br.ready()){
				String next = br.readLine();
				Matcher m = prizeRegex.matcher(next);
				if(m.find()){
					output = "$"+m.group(1);
				}			
			}
		}catch(IOException e){
			output = "Error occured, try again later";
		}
		this.setText(output);
	}
	
	/**
	 * Appends the given input to the file located at 'historyPath' with today's date 
	 * 
	 * @param input data to append
	 */
	public void appendHistory(String input){
		//TODO create this method
	}

	@Override
	public void render(Graphics g) {
		g.drawString("test setestes", 50, 50);

	}
}
