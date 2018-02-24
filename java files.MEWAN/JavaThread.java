package CourceWork02;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class JavaThread implements Runnable {
	
	ImageView image;
	int i=0;
	Reel reel=new Reel();
	
	 Symbol nowSymbol;
	
	public JavaThread(ImageView image) {
		super();
		this.image = image;
	}

	@Override
	public void run() {

		while(this.reel.isSpin()==true&& Display.isRunning==true){
			for(Symbol s = null; this.reel.isSpin();i++){
				if(this.reel.isSpin()){
					if(i==reel.getSymArr().size()){
//						i=0;
						nowSymbol=s;
						i=0;
						}
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
							
								image.setImage(reel.getSymArr().get(i).getImage());
								
							
								
							}
						});
						
				}else{
					break;
				}
					
			//thread shedule depend on jvm
			//can garantee threads shedular
			//synchronize/when stop place start on this
				//volataile variable /methode varible methods synchronize one time
			reel.spin();
			try {
			Thread.sleep(250);
		} catch (InterruptedException ex) {
			Logger.getLogger(JavaThread.class.getName()).log(Level.SEVERE, null, ex);
		}
			
		}
		
	}}}


