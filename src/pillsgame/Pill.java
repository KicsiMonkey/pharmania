package pillsgame;

import java.awt.Image;

public class Pill {
	private String name;
	private Integer price;
	private Image img;
	
	public Pill(String name, int price, Image img) {
		this.name=name;
		this.price=price;
		this.img=img;
	}
	public String getName() {
		return name;
	}
	public Integer getPrice() {
		return price;
	}
	public String priceToString() {
		Integer thousands=price/1000;
		Integer theRest=price%1000;
		String restString=new String(theRest.toString());
		if(theRest<100) {
			if(theRest<10) {
				restString=new String("00"+theRest);
			} else {
				restString=new String("0"+theRest);
			}
		}
		return new String((thousands!=0?thousands:"")+" "+restString);
	}
	public Image getImage() {
		return img;
	}
	public boolean isEqual(Pill p) {
		return (name==p.name);
	}
}
