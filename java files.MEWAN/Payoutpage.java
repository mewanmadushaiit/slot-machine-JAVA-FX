package CourceWork02;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Payoutpage extends Display{

	public void tbl() {
		Static st=new Static();
		
		
StackPane secondlay=new StackPane();
		Reel reel=new Reel();

		int redseven=reel.symArr.get(0).getValue();
		int bell=reel.symArr.get(1).getValue();
		int watermelon=reel.symArr.get(2).getValue();
		int plum=reel.symArr.get(3).getValue();
		int lemon=reel.symArr.get(4).getValue();
		int cherry=reel.symArr.get(5).getValue();
		
		
		double p2=((0.028*redseven)+(0.028*bell)+(0.028*watermelon)+(0.028*plum)+(0.028*lemon)+(0.028*cherry));
		double p3=((0.005*redseven)+(0.005*bell)+(0.005*watermelon)+(0.005*plum)+(0.005*lemon)+(0.005*cherry));
		double tot=p2+p3;
		double pre=tot*100;
		
		HBox payhb= new HBox();
		ImageView imgone=new ImageView("images/values.png");
		imgone.setPreserveRatio(true);
       imgone.setFitWidth(1000);
	   payhb.getChildren().add(imgone);
        
	    VBox payvb=new VBox();
        
        Label totprob=new Label();
        totprob.setText("Total Probability= "+ String.valueOf(tot));
        
        
        Label proba2=new Label();
        proba2.setText("Two match Probability= "+ String.valueOf(p2));
       
        
        Label proba3=new Label();
        proba3.setText("Three match Probability= "+ String.valueOf(p3));
        
        Label presantage=new Label();
        presantage.setText("pay out wining presantage= "+ String.valueOf(pre)+"%");
  
        
        
///////////////line chart for payout  
        //for line chart
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Spin number");
        //creating the chart
         LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
        
        lineChart.setTitle("Pay out Each ");
        //defining a series
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("BET on each game");
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Pay out ");
      
        for (Iterator<Entry<Double, Double>> iterator = st.grph.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry m = iterator.next();
			
			series1.getData().add(new XYChart.Data(m.getKey(),m.getValue()));
		}
       
        
        
       
        
        lineChart.getData().addAll(series1,series2);

       
        payhb.setAlignment(Pos.BOTTOM_CENTER);
        payvb.getChildren().addAll(proba2,proba3,totprob,presantage,lineChart);
		
        StackPane layout=new StackPane();
         
        layout.getChildren().addAll(payvb,payhb);
        
		Scene secScene=new Scene(layout,1000,700);
		
		Stage secStage=new Stage();
		secStage.setTitle("Statictics");
		secStage.setScene(secScene);
		
		secStage.show();
		
	}

}
