package model;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Pair;

public class ScoreBoard extends MySubScene{
	
	private static List<Pair<String, Integer>> playerDataList;
	public static Pane root;
	private static Canvas canvas;
	private static GraphicsContext gc;
	private static final int HIGHSCORE = 5;
	private static double score;
	
	public ScoreBoard() {
		super();
		root = (Pane) super.getRoot();
		canvas = new Canvas(500, 300);
		gc = canvas.getGraphicsContext2D();
		playerDataList = new ArrayList<Pair<String, Integer>>();
		score = 0;
		readScore();
		fillText();
		root.getChildren().addAll(canvas);
		
	}
	
	public static void fillText(){
		gc.setFont(Font.loadFont(ClassLoader.getSystemResource("font/kenvector_future.ttf").toExternalForm(),18));
		for(int i = 0;i < HIGHSCORE && i < playerDataList.size();i++){
			gc.fillText("Rank  "+(i+1)+"     :     "+ playerDataList.get(i).getKey(), 80, 100+40*i);
			gc.fillText("" + playerDataList.get(i).getValue(), 350, 100+40*i);
		}
	}
	
	public static void clearScore() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("res/score.txt");
		writer.println();
		writer.close();
	}
	
	public static void save(){      
		try {
			PrintWriter fw = new PrintWriter(ClassLoader.getSystemResource("res/score.txt").toExternalForm());
			BufferedWriter bw = new BufferedWriter(fw);
	        for(int i = 0;i < playerDataList.size();i++)
	        {
	        	bw.write(playerDataList.get(i).getKey()+":"+playerDataList.get(i).getValue());
	        	bw.newLine();
	        }
	        bw.close();
	        fw.close();
		} catch (IOException e) {
	        e.printStackTrace();
	    }
    }
	
	public static int getSize() {
		return playerDataList.size();
	}
	
	public static void addList(String playerName, int score) {
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
            public int compare(final Pair<String, Integer> o1, final Pair<String, Integer> o2) {
            	if (o1.getValue() > o2.getValue()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
	}
	
	public static void readScore(){
		try (BufferedReader reader = new BufferedReader(new FileReader(new File("res/score.txt")))) {
	        String line;
	        while ((line = reader.readLine()) != null)
	        {
	        	int colonIndex = line.lastIndexOf(":");
	        	if(colonIndex == -1) break;
	        	String playerName = line.substring(0, colonIndex);
	        	int score = Integer.parseInt(line.substring(colonIndex+1, line.length()));
	        	addList(playerName, score);
	        }
	        sortList();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void increaseScore(double point) {
		score += point ; 
	}

	public static double getScore() {
		return score;
	}
}
