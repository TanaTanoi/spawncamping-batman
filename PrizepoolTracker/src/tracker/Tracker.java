package tracker;

import java.awt.Graphics;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tracker extends GUI {
	/*File and URL constants*/
	private static URL dota2;
	private static final String historyPath = "history.txt";
	private static final String url = "http://www.dota2.com/international/compendium/";
	private static Pattern prizeRegex = Pattern.compile("\\<div id=\\\"PrizePoolText\\\"\\>\\$(?<prize>[0-9,]+)\\</div\\>");

	/*A map of date to prize pool where the date is represented by the day of the year*/
	private static Map<Integer,String> history = new HashMap<Integer,String>();
	private static int earliestDay;
	private static int latestDay;

	public Tracker(){
		super();
	}


	public static void main(String[] args)throws Exception{
		dota2 =  new URL(url);
		new Tracker();
	}


	@Override
	public void onButtonUpdate() {
		if(readHistory()){
			
		}
		String output = this.getPrizepool();
		this.addHistory(output);
		this.setText("$"+output);
		writeHistory();
	}
	/**
	 * Reads the history file and places it into the map
	 * @return returns weather or not a file was read
	 */
	public boolean readHistory(){
		history.clear();
		File file = new File(historyPath);
		if(file.exists()){

			try{
				Scanner sc = new Scanner(file);
				while(sc.hasNext()){
					int date = sc.nextInt();
					String prize = sc.next();
					history.put(date, prize);
					earliestDay = Math.min(earliestDay,date);
					latestDay = Math.max(latestDay,date);
				}
				sc.close();
			}catch(IOException e){
				this.setText("No history file exists");
			}
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Appends the given input to the file located at 'historyPath' with today's date 
	 * 
	 * @param input data to append
	 */
	public void addHistory(String input){		
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_YEAR);
		history.put(day,input);
		System.out.printf("Added %s to %d",input,day);
	}
	/**
	 * Writes the currently stored history to a file
	 */
	public void writeHistory(){
		File file = new File(historyPath);


		try{
			//Copy to array and sort the array
			int[] dates = new int[history.keySet().size()];						
			Iterator<Integer> it = history.keySet().iterator();
			for(int i = 0;i<dates.length;i++){
				dates[i] = it.next();
			}
			Arrays.sort(dates);

			PrintWriter pw = new PrintWriter(file,"UTF-8");
			for(int date:dates){
				pw.println(date + " " + history.get(date));
			}
			pw.close();
		}catch(IOException e){
			System.out.println("Error");
		}

	}
	/**
	 * Gets the current, most recent prize pool amount
	 * @return
	 */
	private String getPrizepool(){
		String toReturn = "Error";
		try{
			BufferedReader br = new BufferedReader(
					new InputStreamReader(dota2.openStream()));
			while(br.ready()){
				String next = br.readLine();
				Matcher m = prizeRegex.matcher(next);
				if(m.find()){
					toReturn = m.group(1);
					//addHistory(m.group(1));
				}			
			}

		}catch(IOException e){

			//output = "Error occured, try again later";
		}
		return toReturn;
	}



	@Override
	public void render(Graphics g) {
		g.drawString("test setestes", 50, 50);

	}
}
