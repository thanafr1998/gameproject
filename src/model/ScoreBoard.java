package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class ScoreBoard extends MySubScene{
	
	private static List<Pair<String, Integer>> playerDataList;
	public static Pane root;
	private static Canvas canvas;
	private static GraphicsContext gc;
	private static final int HALLOFFAMENUMBER = 5;
	
	public ScoreBoard(){
		super();
		root = (Pane) super.getRoot();
		canvas = new Canvas(500,300);
		gc = canvas.getGraphicsContext2D();
		playerDataList = new ArrayList<Pair<String, Integer>>();
		
		readScore();
		fillText();

		root.getChildren().addAll(canvas);
		
	}
	
	public static void fillText(){
		for(int i = 0;i < HALLOFFAMENUMBER && i < playerDataList.size();i++){
			gc.fillText("Rank "+(i+1)+": "+playerDataList.get(i).getKey()+" "+playerDataList.get(i).getValue(), 434, 218+84*i);
		}
	}
	
	public static void save() throws Exception{      
			PrintWriter fw = new PrintWriter("res/score.txt");
			BufferedWriter bw = new BufferedWriter(fw);
	        for(int i = 0;i < playerDataList.size();i++)
	        {
	        	bw.write(playerDataList.get(i).getKey()+":"+playerDataList.get(i).getValue());
	        	bw.newLine();
	        }
	        bw.close();
	        fw.close();
		
    }
	
	public static int getSize() {
		return playerDataList.size();
	}
	
	private static void addList(String playerName, int score) {
		playerDataList.add(new Pair<String, Integer>(playerName, score));
		sortList();
	}
	
	public static int getRank(String playerName, int score) {
		int rank = playerDataList.size();
		for(int i = playerDataList.size()-1;i>=0&&score>playerDataList.get(i).getValue();i--){
			rank = i;
		}
		addList(playerName, score);
		return rank+1;
	}
	
	private static void sortList() {
		Collections.sort(playerDataList, new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(final Pair<String, Integer> pair1, final Pair<String, Integer> pair2) {
            	if (pair1.getValue() > pair2.getValue()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
	}
	
	public static void readScore() {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File("res/score.txt")))) {
	        String line;
	        while ((line = reader.readLine()) != null)
	        {
	        	int colonIndex = line.lastIndexOf(":");
	        	String playerName = line.substring(0, colonIndex);
	        	int score = Integer.parseInt(line.substring(colonIndex+1, line.length()));
	        	addList(playerName, score);
	        }
	        sortList();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
