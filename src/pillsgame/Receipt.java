package pillsgame;

import java.util.HashMap;
import java.util.Iterator;
///////////////////////////////////////PILL LISTET FELTÖLTENI!!!!!
public class Receipt {
	private HashMap<Pill, Integer> pillList;
	private String patientName;
	private String signature;
	private Boolean stamp;
	
	public Receipt(String patientName, String signature, Boolean stamp) {
		pillList=new HashMap<Pill, Integer>();
		this.patientName=patientName;
		this.signature=signature;
		this.stamp=stamp;
	}
	public Boolean isValid() {
		return (stamp && signature.equals(patientName));
	}
	public long getPrice() {
		long totalPrice=0;
		Iterator<Pill> it=pillList.keySet().iterator();
		while(it.hasNext()) {
			Pill pill=it.next();
			totalPrice+=(pillList.get(pill))*(pill.getPrice());
		}
		return totalPrice;
	}
	public HashMap<Pill, Integer> getPillList() {
		return pillList;
	}
	public void addPill(Pill p) {
		if(pillList.containsKey(p)) {
			pillList.replace(p, pillList.get(p)+1);
		} else {
			pillList.put(p, 1);
		}
	}
	public String getPatientName() {
		return patientName;
	}
	public String getSignature() {
		return signature;
	}
	public Boolean isStamped() {
		return stamp;
	}
	//PillList getPillList
	
}
