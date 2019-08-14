package pillsgame;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

public class PillGenerator {
	public int nextImgIndex=0;
	public String[] prefixes={"Acou", "Aden", "Bio", "Crani", "Dermo", "Front", "Gyno", "Hydro", "Myco", "Noci"};
	public String[] suffixes={"ad", "ase", "cele", "form", "gen", "id", "ne", "or", "ped", "rin"};
	public ArrayList<Image> images=new ArrayList<Image>();
	public ArrayList<String> names=new ArrayList<String>();
	
	public PillGenerator() {
		readImages("/images/pills/");
		shuffleImages();
	}
	private void readImages(String filePathBeginning) {
		for (Integer i=0; i<40; i++) {
			//images.add(ImageIO.read(getClass().getResource(filePath+i.toString()+".jpg")));
			try {
				images.add(ImageIO.read(getClass().getResource(new String(filePathBeginning+i.toString()+".png"))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void shuffleImages() {
		Collections.shuffle(images);
	}
	public Pill generate() {
		String name="";
		while("".equals(name) || names.contains(name)) {
			int pre=((Double)(Math.random()*10)).intValue();
			int suff=((Double)(Math.random()*10)).intValue();
			name=new String(prefixes[pre]+suffixes[suff]);
		}
		names.add(name);
		int price=(((Double)(Math.random()*4500+1+500)).intValue()/100)*100+90;
		Pill pill=new Pill(name, price, images.get(nextImgIndex));
		nextImgIndex++;
		return pill;
	}
	public ArrayList<Pill> generate(int n) {
		int m=6;
		if (n<m) m=n;	//min
		ArrayList<Pill> pills=new ArrayList<Pill>();
		for(int i=0; i<m; ++i) {
			pills.add(generate());
		}
		return pills;
	}
}
