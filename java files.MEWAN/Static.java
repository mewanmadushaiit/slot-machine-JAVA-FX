	package CourceWork02;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Static extends Display {
	
	 static Map<Double,Double> grph=new HashMap<>();//this hash map get spin time and bet on each spin
	 static Map<Double,Double> grph2=new HashMap<>();//this got spin and win amount of coins
	
	/*
	 * Static page use to display some statictics of player mainly here display avgscore
	 * avg bet 
	 * avg win and lose
	 * also those data put in a pichart and line chart pichart display avg win and lose
	 * line char display each game how many coins are bet and how many coins are won in that game
	 * more of values get from super class of Display
	 * also i'm using collection type hashmap to get values and keys according to well manner*/
	 
	 public void name() {
		
		BorderPane statbpane=new BorderPane();
		Label lblnew=new Label("Statictics");
	
		VBox nhb=new VBox();
		nhb.setSpacing(20);
		
			HBox v1=new HBox();
		Label lblwins=new Label("WON TIMES--->");
		Text  txtwin=new Text(String.valueOf(winCount));
			v1.getChildren().addAll(lblwins,txtwin);
			
		HBox v2=new HBox();
		Label lbllost=new Label("LOST TIMES--->");
		Text  txtlost=new Text(String.valueOf(lostCount));
		v2.getChildren().addAll(lbllost,txtlost);
			
		HBox v3=new HBox();
		Label lblavgwin=new Label("Winning presentage--->");
		
		float win= (float)((winCount/spinTime)*100);
		System.out.println(win);
		Text  txtavgwin=new Text(String.valueOf(win+"%"));
		v3.getChildren().addAll(lblavgwin,txtavgwin);
		
		HBox v4=new HBox();
		Label lblavglost=new Label("losssing presentage--->");
		float lost=(float)((lostCount/spinTime)*100);
		Text  txtavglost=new Text(String.valueOf(lost+"%"));
		v4.getChildren().addAll(lblavglost,txtavglost);
		
		HBox v5=new HBox();
		Label lblavgbet=new Label("BET presentage--->");
		float bet=(float)((fullbet/spinTime));
		Text  txtavgbet=new Text(String.valueOf(bet+" COINS PER BET"));
		v5.getChildren().addAll(lblavgbet,txtavgbet);
		
		HBox v6=new HBox();
		Label totalwin=new Label("Total win coins--->");
		
		Text  txttotlwin=new Text(String.valueOf(earncoins+" COINS"));
		v6.getChildren().addAll(totalwin,txttotlwin);
		
		Button savestatic=new Button();
		savestatic.setText("Save Statictics");
		savestatic.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				 String e="path is not found";	
				try {
                    Date date = new Date();
                    SimpleDateFormat path=new SimpleDateFormat("yyyy-MM-dd HH-mm");

                    File file = new File("C:\\Users\\madus\\workspace\\yr2oop\\src\\CourceWork02\\" + path.format(date) + ".txt");
                    
                    BufferedWriter out = new BufferedWriter(new FileWriter(file));
                    
                    
                    System.out.println("");
                    float win= (float)((winCount/spinTime)*100);//win avg
                    float lost=(float)((lostCount/spinTime)*100);//lost avg
                    float bet=(float)((fullbet/spinTime));//cons per bet
                    
                    out.write("Won times="+winCount+"\n");
                    out.write("Lost times="+lostCount+"\n");
                    out.write("Won avg="+win+"%"+"\n");
                    out.write("Lost avg="+lost+"%"+"\n");
                    out.write("bet avg="+bet +" coins per bet"+"\n"+"\n");
                    	
                    out.write("\n Your betting status\n");
                    for( Map.Entry<Double, Double> a : grph.entrySet() )
                    	  out.write( "Spin= " + a.getKey() + "  You bet= " + a.getValue()+ " coins" +"\n");
                    
                    	out.write("\n Your earnning status\n");
                   
                    for( Map.Entry<Double, Double> b : grph2.entrySet() )
                    	  out.write( "Spin= " + b.getKey() + "  You earn= " + b.getValue()+ " coins" +"\n");
                    
                    out.close();
                } catch (IOException e1) {
                    System.out.println(e);
                }
			        
		            
				
			}
			
		});
		
		
		nhb.getChildren().addAll(lblnew,v1,v2,v3,v4,v5,v6);
		System.out.println(win);
		
		VBox n2hb=new VBox();
		
		
		//for piechart
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("wins", (float)((winCount/spinTime)*100)),
                new PieChart.Data("lost", (float)((lostCount/spinTime)*100))
                );
        final PieChart chart = new PieChart(pieChartData);
		
        //for line chart
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Spin number");
        //creating the chart
         LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
         
         
         
        lineChart.setTitle("Bet of each ");
        //defining a series
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("BET on each game");
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("COIN earn on each game");
        //populating the series with data
        //get data from hashmap collection and add those values into chart
        for(Map.Entry m:grph.entrySet()){
        series1.getData().add(new XYChart.Data(m.getKey(),m.getValue()));
        }
        for(Map.Entry m2:grph2.entrySet()){
         series2.getData().add(new XYChart.Data(m2.getKey(),m2.getValue()));
        }
        
        
       
        
        lineChart.getData().addAll(series1,series2);
        
       
        n2hb.getChildren().addAll(chart,lineChart,savestatic);
        //n3hb.getChildren().addAll(series);
        
		
		StackPane secondlay=new StackPane();
		
		secondlay.getChildren().addAll(nhb,n2hb);
		
		secondlay.setAlignment(n2hb, Pos.TOP_LEFT);
		
		Scene secScene=new Scene(secondlay,1000,700);
		
		Stage secStage=new Stage();
		secStage.setTitle("Statictics");
		secStage.setScene(secScene);
		
		secStage.show();
		
		
		
		
	}



}
