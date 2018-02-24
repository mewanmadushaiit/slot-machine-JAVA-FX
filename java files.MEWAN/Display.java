package CourceWork02;

import java.util.Optional;
import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;

public class Display extends Application {

	static boolean isRunning = true;
    static boolean win;
	public boolean getRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	static double winCount;
	static double lostCount;
	static double spinTime;
	static double fullbet;

	static Reel rel1 = new Reel();
	static Reel rel2 = new Reel();
	static Reel rel3 = new Reel();

	static ImageView imageDisOne;
	static ImageView imageDisTwo;
	static ImageView imageDisThree;

	static int creditlvl, betlvl,earncoins;
	

	static Label lbldescredit = new Label();
	static Label lblbet_area = new Label();
	static Label lbl_status = new Label();

	static Text cointxt = new Text();

	static Thread t1 = new Thread();// create 3 threads
	static Thread t2 = new Thread();
	static Thread t3 = new Thread();

	static boolean rel1ok;//when this true means reel is stop
	static boolean rel2ok;
	static boolean rel3ok;
	
	static boolean betmaxdone;
	static int show_credit;

	static int ok;
	Image imagewin = new Image(getClass().getResourceAsStream("/images/win.png"));
	Image imagelost = new Image(getClass().getResourceAsStream("/images/lose.png"));
	ImageView back = new ImageView("/images/back.png");

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		/**/
		rel1ok=true;
		rel2ok=true;
		rel3ok=true;
		betmaxdone=false;
		
		primaryStage.setTitle("Slotmachine");

		HBox displayhb = addHBOX();// layout type im using top of buttons

		VBox displayvb = addVBox();// layout type im using areas

		HBox spinhb = spinHbox();

		HBox alertcoin = new HBox();
		alertcoin.setAlignment(Pos.CENTER);
		cointxt.setFont(Font.font(null, FontWeight.BOLD, 25));
		alertcoin.getChildren().addAll(cointxt);

		BorderPane bpane = new BorderPane();

		bpane.setTop(displayhb);// add all thing according to layout
		bpane.setLeft(displayvb);

		bpane.setCenter(spinhb);
		bpane.setBottom(alertcoin);

		// change element inside the windowwhen windows resizing auto matically
		// inside elements change
		bpane.heightProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				double height = (double) newValue;
				Display.imageDisOne.setFitHeight(height / 4);
				Display.imageDisTwo.setFitHeight(height / 4);
				Display.imageDisThree.setFitHeight(height / 4);
				displayhb.setPrefHeight(height / 4.5);
				displayvb.setPrefHeight(height / 4.5);
			}

		});

		bpane.widthProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				double width = (double) newValue;
				Display.imageDisOne.setFitWidth(width / 4.5);
				Display.imageDisTwo.setFitWidth(width / 4.5);
				Display.imageDisThree.setFitWidth(width / 4.5);
				displayhb.setPrefWidth(width / 4);
				displayvb.setPrefWidth(width / 4);
			}

		});

		Scene scene = new Scene(bpane, 1200, 800); // window size

		primaryStage.setScene(scene);
		primaryStage.show();// show fx window
		primaryStage.setOnCloseRequest(e -> Platform.exit());// display close
																// shutdown
																// every window
		// up to this show window items

	}

	/*
	 * METHODE spinHbox three reels include inside this hbox this iclude with
	 * also styles get first showing image by shuffeld symArr
	 * 
	 */
	private HBox spinHbox() {

		HBox hbspin = new HBox();

		hbspin.setSpacing(10);
		hbspin.setPadding(new Insets(160, 70, 100, 70));

		rel1.spin();
		imageDisOne = new ImageView(rel1.getSymArr().get(0).getImage());
		imageDisOne.setFitHeight(150);
		imageDisOne.setFitWidth(150);
		imageDisOne.setOnMouseClicked(e -> stopSpin(rel1));// when click on
															// image its stop
															// the reel spin and
															// call methode
															// stopspin
		imageDisOne.setEffect(new DropShadow(20, Color.BLACK));

		rel2.spin();
		imageDisTwo = new ImageView(rel2.getSymArr().get(1).getImage());
		imageDisTwo.setFitHeight(150);
		imageDisTwo.setFitWidth(150);
		imageDisTwo.setOnMouseClicked(e -> stopSpin(rel2));
		imageDisTwo.setEffect(new DropShadow(20, Color.BLACK));

		rel3.spin();
		imageDisThree = new ImageView(rel3.getSymArr().get(2).getImage());
		imageDisThree.setFitHeight(150);
		imageDisThree.setFitWidth(150);
		imageDisThree.setOnMouseClicked(e -> stopSpin(rel3));
		imageDisThree.setEffect(new DropShadow(20, Color.BLACK));

		hbspin.getChildren().addAll(imageDisOne, imageDisTwo, imageDisThree);
		return hbspin;
	}

	/*
	 * METHODE addVbox left coner menu lbl bet area,text lbl credit area,text
	 * button statictics include styles
	 **/

	private VBox addVBox() {
		creditlvl = 10;
		betlvl = 0;

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(35);
		vbox.setStyle("-fx-background-color: #C4C4C4;");

		VBox inside1 = new VBox();
		inside1.setAlignment(Pos.CENTER);
		inside1.setSpacing(10);
		DropShadow ds = new DropShadow();// text effect
		ds.setOffsetY(4.0f);
		ds.setColor(Color.WHITE);
		Text credit = new Text();
		credit.setEffect(ds);
		credit.setCache(true);
		credit.setFill(Color.BLACK);
		credit.setText("CREDIT LEVEL");
		credit.setFont(Font.font(null, FontWeight.BOLD, 25));

		lbldescredit.setPrefSize(200, 50);
		lbldescredit.setStyle("-fx-background-color: #F2F0F7;");
		lbldescredit.setText(String.valueOf(creditlvl));
		lbldescredit.setFont(Font.font(25));
		inside1.getChildren().addAll(credit, lbldescredit);

		VBox inside2 = new VBox();// sepaatly add 2 labels and finally both vbox
									// add to one vbox

		inside2.setSpacing(10);
		inside2.setAlignment(Pos.CENTER);

		Text bet = new Text();
		bet.setEffect(ds);
		bet.setCache(true);
		bet.setFill(Color.BLACK);
		bet.setText("BET AREA");
		bet.setFont(Font.font(null, FontWeight.BOLD, 25));

		lblbet_area.setPrefSize(200, 50);
		lblbet_area.setStyle("-fx-background-color: #F2F0F7;");
		lblbet_area.setText(String.valueOf(betlvl));// this label show value of
													// betlvl
		lblbet_area.setFont(Font.font(25));

		inside2.getChildren().addAll(bet, lblbet_area);

		lbl_status.setPrefSize(200, 180);
		lbl_status.setStyle("-fx-background-color: #F2F0F7;");
		lbl_status.setFont(Font.font(35));

		Button btnstatic = new Button("Statistic");
		btnstatic.setPrefSize(100, 50);
		btnstatic.setStyle("-fx-background-radius: 15,15,15;-fx-font-size: 18px;-fx-text-fill: #311c09;");
		/*
		 * set on mouse click to open new window when statictics button click it
		 * opens new window
		 */
		btnstatic.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				Static st = new Static();
				st.name();

			}
		});

		vbox.getChildren().addAll(inside1, inside2, lbl_status, btnstatic);
		return vbox;
	}

	/*
	 * top pannel of button all button include inside hbox
	 */
	private HBox addHBOX() {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);

		hbox.setSpacing(45);
		hbox.setStyle("-fx-background-color: #336699;-fx-border-color: #C4C4C4; -fx-border-width: 8px;");

		Button btnaddcoin = new Button("Add Coin");// add coin to total credit
		btnaddcoin.setOnAction(e -> add_coin());
		btnaddcoin.setPrefSize(150, 50);
		btnaddcoin.setStyle("-fx-background-radius: 15,15,15;-fx-font-size: 25px;-fx-text-fill: #311c09;");

		Button btnbetone = new Button("Bet One");// bet one coin to bet area
													// when it click another
													// time bet area increase by
													// one bet
		btnbetone.setOnAction(e -> bet_onecoin());
		btnbetone.setPrefSize(150, 50);
		btnbetone.setStyle("-fx-background-radius: 15,15,15;-fx-font-size: 25px;-fx-text-fill: #311c09;");

		Button btnbetmax = new Button("Bet Max");// this for add 3 coins in one
													// click
		btnbetmax.setOnAction(e -> bet_max());
		btnbetmax.setPrefSize(150, 50);
		btnbetmax.setStyle("-fx-background-radius: 15,15,15;-fx-font-size: 25px;-fx-text-fill: #311c09;");

		Button btnreset = new Button("RESET");// set to default values
		btnreset.setOnAction(e -> reset());
		btnreset.setPrefSize(100, 50);
		btnreset.setStyle("-fx-background-radius: 15,15,15;");
		
		Button paytable = new Button(" Pay Table");// set to default values
		paytable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Payoutpage pout=new Payoutpage();
				
				pout.tbl();
				
			}
			
		
		});
		paytable.setPrefSize(100, 50);
		paytable.setStyle("-fx-background-radius: 15,15,15;");

		Button btnspin = new Button("SPIN");// click it call to spin methode and
											// start to spin reel
		btnspin.setOnAction(e -> spin());
		btnspin.setPrefSize(150, 100);
		btnspin.setStyle(
				"-fx-background-color: #ecebe9,rgba(0,0,0,0.05),   linear-gradient(#dcca8a, #c7a740),  linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),  linear-gradient(#f6ebbe, #e6c34d);"
						+ "-fx-background-insets: 0,9 9 8 9,9,10,11;  " + " -fx-background-radius: 50; "
						+ "-fx-padding: 15 30 15 30;"

						+ "-fx-font-size: 25px;-fx-text-fill: #311c09;"
						+ " -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");

		hbox.getChildren().addAll(btnaddcoin, btnbetone, btnbetmax, btnspin, btnreset,paytable);

		return hbox;
	}

	/*
	 * METHODE stopSpin this methode to stop 3 reels get each click from
	 * previous and check wether it is which reel or not and stop its thread
	 */

	private Object stopSpin(Reel rel) {
		if(rel1ok==false || rel2ok==false || rel3ok==false){//when reel are not running user can able to click image view to avoid this at least run one 
			//reel codition that means it's already spin time
		if (rel == rel1) {
			rel1ok = true;
			rel1.t1.stop();
			System.out.println("a1");

		}
		// t1.stop();
		else if (rel == rel2) {
			rel2ok = true;
			rel2.t2.stop();

			System.out.println("a2");
		} else if (rel == rel3) {
			rel3ok = true;
			rel3.t3.stop();
			System.out.println("a3");
		}
		/*// if stop all the 3 reel and
		 check thebet vlues with current images
		 */		
		
		if (rel1ok == true && rel3ok == true && rel2ok == true) {
			System.out.println("aaaa");
//			rel1ok = false;
//			rel2ok = false;
//			rel3ok = false;
			function(rel);
		}
		
		}else {
			Alert spinfirst = new Alert(AlertType.INFORMATION);
			spinfirst.setTitle("Spin first");
			spinfirst.setContentText("Click spin");
			spinfirst.showAndWait();
		}
		return null;
		// stop methode call here the stop current reel

	}

	/*
	 * 12) If two or three symbols in the three reels are the same the game is
	 * won. Compute the amount of money that are won and add to the credit.
	 * Implement this Function methode for it simply check with each first and
	 * third,first and second,second and third if on of these things get correct
	 * call other methode
	 */
	private void function(Reel rel) {
		
		if (Display.imageDisOne.getImage().impl_getUrl().equals(Display.imageDisTwo.getImage().impl_getUrl())) {
			lbl_status.setGraphic(new ImageView(imagewin));// status set to win
															// image
			winBet1(); // this first functionality is correct call bet1
			ok++;
			// System.out.println("a1");//this for check
			fullbet += betlvl;
			winCount++;
            win=true;
		} else if (Display.imageDisOne.getImage().impl_getUrl()
				.equals(Display.imageDisThree.getImage().impl_getUrl())) {
			winBet2();
			lbl_status.setGraphic(new ImageView(imagewin));
			ok++;
			winCount++;
			fullbet += betlvl;
			win=true;
			System.out.println("a2");
		} else if (Display.imageDisTwo.getImage().impl_getUrl()
				.equals(Display.imageDisThree.getImage().impl_getUrl())) {
			// System.out.println("a3");
			lbl_status.setGraphic(new ImageView(imagewin));
			ok++;
			winCount++;
			fullbet += betlvl;
			win=true;
			winBet3();
		} else if (ok == 0) {
			cointxt.setText("Not earn coins tryagain!!");
			lbl_status.setGraphic(new ImageView(imagelost));
			lostCount++;
			fullbet += betlvl;
			show_credit = 0;
			win=false;
		}

		Static st = new Static(); // create oject from static, this is to send
									// data to hashmap and put status inside
									// pichart and line chart

		spinTime++;// each spin this variable increase

		st.grph.put(spinTime, (double) (betlvl*0.90));
		st.grph2.put(spinTime, (double) show_credit);

		betlvl = 0;// after win or lose bet area set to 0
		ok = 0;
		setOutPut(creditlvl, betlvl);// pass parameters to calculate final
										// credit level

	
		
	}
	// in case here i used each reel image url and divide by some symbols and
	// get last value of url that means it gives image name

	public static String getLastBitFromUrl(final String url) {// this for create
																// metode to
																// divide url
																// and get last
																// value of
																// image
		return url.replaceFirst(".*/([^/?]+).*", "$1");
	}

	// then pass this last value of url and each of it get some unic value
	private int valueGenarator(String str3ok) {// this for check its image and
												// give values for it
		int valimage = 0;
		switch (str3ok) {
		case ("redseven.png"):
			valimage = 7;
			break;
		case ("bell.png"):
			valimage = 6;
			break;
		case ("watermelon.png"):
			valimage = 5;
			break;
		case ("plum.png"):
			valimage = 4;
			break;
		case ("lemon.png"):
			valimage = 3;
			break;
		case ("cherry.png"):
			valimage = 2;
			break;
		}

		return valimage;

	}

	// this is 3r condition of win
	private void winBet3() {
		String str3 = imageDisTwo.getImage().impl_getUrl();// get image url
		String str3ok = getLastBitFromUrl(str3); // past to this metode and get
													// last value of url

		valueGenarator(str3ok);// and also sent it to set value to image get
								// value from each image
		// System.out.println(valueGenarator(str3ok));//checking
		show_credit = betlvl * (valueGenarator(str3ok));

		cointxt.setText(String.valueOf("You win!!!! " + show_credit + " coins"));//set cointxt this	 shows in bottom of window 
		int fromvalue=betlvl * (valueGenarator(str3ok));
		earncoins+=fromvalue;
		creditlvl += (fromvalue);// add betwin coins to
															// credit level
		
		setOutPut(creditlvl, betlvl);// set final out put
		// System.out.println("won3");//checking
	}

	// this is 2nd condition of win
	private void winBet2() {
		String str2 = imageDisOne.getImage().impl_getUrl();
		String str2ok = getLastBitFromUrl(str2);

		show_credit = betlvl * (valueGenarator(str2ok));
		cointxt.setText(String.valueOf("You win!!!! " + show_credit + " coins"));
		int fromvalue1=betlvl * (valueGenarator(str2ok));
		earncoins+=fromvalue1;
		creditlvl += fromvalue1;
		setOutPut(creditlvl, betlvl);
		// System.out.println("won2");
	}

	// this is 1st condition of win
	private void winBet1() {
		String str1 = imageDisOne.getImage().impl_getUrl();
		String str1ok = getLastBitFromUrl(str1);

		show_credit = betlvl * (valueGenarator(str1ok));
		cointxt.setText(String.valueOf("You win!!!! " + show_credit + " coins"));
		int fromvalue2=betlvl * (valueGenarator(str1ok));
		earncoins+=fromvalue2;
		creditlvl += fromvalue2;
		setOutPut(creditlvl, betlvl);
		// System.out.println("won1");

	}

	/*
	 * This is METHODE spin when spin button click navigate programme to this
	 * methode and start 3 threads to each reel but here check first are there
	 * any bet or not .if there are nor bet alert it to user
	 */
	private Object spin() {

		if (betlvl == 0) {
			Alert nobet = new Alert(AlertType.INFORMATION);
			nobet.setTitle("Insert bet!!!");
			nobet.setContentText("Plz bet somecoins!!");
			nobet.showAndWait();

		} else {
			if(rel1ok==true && rel2ok==true && rel3ok==true){//you can only run all three reels are only stoped
			cointxt.setText("");
			lbl_status.setGraphic(null);
			t1 = new Thread(new JavaThread(imageDisOne));// pass java thread
															// oject to thread
															// with imageviwe
															// one
			rel1.setSpin(true);// also set reel object to true because thread
								// run only if boolean is true
			t1.start();// thread start

			t2 = new Thread(new JavaThread(imageDisTwo));
			rel2.setSpin(true);
			t2.start();

			t3 = new Thread(new JavaThread(imageDisThree));
			rel3.setSpin(true);
			t3.start();
			
			rel1ok=false;
			rel2ok=false;
			rel3ok=false;
			
			
			}else {
				Alert stopspin = new Alert(AlertType.INFORMATION);
				stopspin.setTitle("oops!!");
				stopspin.setContentText("First stop three reels click on each reel");
				stopspin.showAndWait();
			}
		}

		return null;
	}

	// this is to set programme into default values before it done programme it
	// self ask from user to confirm their action
	private Object reset() {
		Alert reset = new Alert(AlertType.CONFIRMATION);

		reset.setTitle("reset");
		reset.setContentText("Do you want to reset");

		ButtonType ok = new ButtonType("OK");
		ButtonType cancel = new ButtonType("CANCEL", ButtonData.CANCEL_CLOSE);

		reset.getButtonTypes().setAll(ok, cancel);
		Optional<ButtonType> result = reset.showAndWait();

		if (result.get() == ok) {
			creditlvl = creditlvl+betlvl;
			betlvl = 0;
			setOutPut(creditlvl, betlvl);

		}

		return null;
	}

	// one time it bet 3coins
	private Object bet_max() {
		if(betmaxdone==false){
		if (creditlvl > 2) {
			betlvl += 3;
			creditlvl = creditlvl - 3;
			setOutPut(creditlvl, betlvl);
			betmaxdone=true;
		} else {
			Alert lowMoney = new Alert(AlertType.INFORMATION);
			lowMoney.setTitle("Low Money");
			lowMoney.setContentText("You have not enough money");
			lowMoney.showAndWait();
		}
		}else{
			
			Alert betmaxone = new Alert(AlertType.INFORMATION);
			betmaxone.setTitle("You alredy done bet max");
			betmaxone.setContentText("You can only one max bet per spin");
			betmaxone.showAndWait();
		}
		return null;
	}

	// one time it bet one coin
	private Object bet_onecoin() {
		if (creditlvl > 0) {
			betlvl += 1;
			creditlvl = creditlvl - 1;
			setOutPut(creditlvl, betlvl);
		} else {
			Alert lowMoney = new Alert(AlertType.INFORMATION);
			lowMoney.setTitle("Low Money");
			lowMoney.setContentText("You have not enough money");
			lowMoney.showAndWait();

		}
		return null;
	}

	private Object add_coin() {
		creditlvl += 1;
		setOutPut(creditlvl, betlvl);
		return null;
	}

	// always final out put shows in these static labes
	private void setOutPut(int creditlvl2, int betlvl2) {

		Display.lbldescredit.setText(String.valueOf(creditlvl2));
		Display.lblbet_area.setText(String.valueOf(betlvl2));
		betmaxdone=false;
	}

}
