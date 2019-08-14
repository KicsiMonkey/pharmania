package pillsgame;

import java.util.HashMap;

public class PillBag extends HashMap<Pill, Integer>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//public HashMap<Pill, Integer> content=new HashMap<Pill, Integer>();

	public Integer size=0;
	
	public boolean isEqual(HashMap<Pill, Integer> pillList) {
		boolean b=true;
		b=(this.size()==pillList.size());
		System.out.println("HASONLÍTOK!!");
		for (Entry<Pill, Integer> entry : this.entrySet()) {		//végigiterálunk a párosításokon és
			Pill p=entry.getKey();
			if (pillList.containsKey(p)) {
				b=(entry.getValue()==pillList.get(p));
			} else {
				b=false;
			}
		}
		System.out.println(b);
		return b;
	}
	public void addPill(Pill p) {
		if(containsKey(p)) {
			this.replace(p, this.get(p)+1);
			System.out.println("Seen this already! Now its "+this.get(p));
		} else {
			this.put(p, 1);
			System.out.println("Something new!!");
		}
		size++;
	}
	public void removePill(Pill p) {
		if(containsKey(p)) {
			if(this.get(p)-1!=0) {
				this.replace(p, this.get(p)-1);
			} else {
				this.remove(p);
			}
			size--;
		}
	}
}
