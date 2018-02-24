package CourceWork02;

import javafx.scene.image.Image;

	

public class Symbol implements ISymbol {
	
	static boolean isSpin;

	  public boolean isSpin() {
		return isSpin;
	}

	  public void setSpin(boolean isSpin) {
		Symbol.isSpin = isSpin;
	}
	private Image image;
	private int value;

	public Symbol(int i, Image img) {
		this.image=img;
		this.value=i;
	}

	public Symbol() {
		
	}

	@Override
	public void setImage(Image image) {
		this.image=image;
		
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void setValue(int v) {
		this.value=v;
		
		
	}

	@Override
	public int getValue() {
		
		return value;
	}
      
}
