
package CourceWork02;

import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.image.Image;

public class Reel extends Display  {

	//create image objects and get each image as a image object
	Image seven=new Image("/Images/redseven.png");
	Image bell=new Image("/Images/bell.png");
	Image watermelon=new Image("/Images/watermelon.png");
	Image plum=new Image("/Images/plum.png");
	Image lemon=new Image("/Images/lemon.png");
	Image cherry=new Image("/Images/cherry.png");
	
	
	
	ArrayList<Symbol> symArr=new ArrayList<>();
	
	//this boolean for
	//in my thread i used two booleans it for run window and spin reel when one
	//of this boolean false my thread is not running
	
	static boolean isSpin;

	  public boolean isSpin() {
		return isSpin;
	}

	  public void setSpin(boolean isSpin) {
		Reel.isSpin = isSpin;
	}

	public ArrayList<Symbol> getSymArr() {
		return symArr;
	}
	  
	

	public Reel() {
		
		
		
		super();
		
		//create symbole class objects and pass values in to symarr inside the reel constructor
		Symbol sym1=new Symbol();
		sym1.setImage(seven);
		sym1.setValue(7);
		
		Symbol sym2=new Symbol();
		sym2.setImage(bell);
		sym2.setValue(6);

		Symbol sym3=new Symbol();
		sym3.setImage(watermelon);
		sym3.setValue(5);
		
		Symbol sym4=new Symbol();
		sym4.setImage(plum);
		sym4.setValue(4);
		
		Symbol sym5=new Symbol();
		sym5.setImage(lemon);
		sym5.setValue(3);
		
		Symbol sym6=new Symbol();
		sym6.setImage(cherry);
		sym6.setValue(2);
		
		
		symArr.add(sym1);
		symArr.add(sym2);
		symArr.add(sym3);
		symArr.add(sym4);
		symArr.add(sym5);
		symArr.add(sym6);
	}

	public void spin(){
		Collections.shuffle(symArr);
	}
	
	
	
	

}
