package pillsgame;

import java.util.ArrayList;
import java.util.Iterator;

public class Customer {
	private String name;
	private Receipt receipt;
	
	public Customer(ArrayList<Pill> pillList) {
		this.name=NameGenerator.generate();
		String signature=name;
		if(Math.random()<=0.20) {
			signature=NameGenerator.generate();
		}
		Boolean stamp=true;
		if (Math.random()<=0.25) {
			stamp=false;
		}
		this.receipt=new Receipt(name, signature, stamp);
		for(int i=0; i<=((Double)(Math.random()*9)).intValue(); i++) {
			this.receipt.addPill(pillList.get(((Double)(Math.random()*6)).intValue()));
		}
	}
	public Boolean isValid() {
		return receipt.isValid();
	}
	/*public void pay(CashRegister cashRegister, PillBag bag) {
		if(bag.isEqual(receipt.pillList)) {
				cashRegister.pay(receipt, isValid());
		}
	}*/
	public Boolean isServedRight(PillBag b) {
		if(b.isEqual(receipt.getPillList())) {
			return true;
		}
		return false;
	}
	public String getReceiptName() {
		return name;
	}
	public String getReceiptSignature() {
		return receipt.getSignature();
	}
	public Boolean isReceiptStamped() {
		return receipt.isStamped();
	}
	public String getReceiptPillListHtml() {
		StringBuilder builder=new StringBuilder(200);
		builder.append("<html><body>");
		Iterator<Pill> it=receipt.getPillList().keySet().iterator();
		while(it.hasNext()) {
			Pill pill=it.next();
			builder.append(new String(pill.getName().toUpperCase()+" "+(receipt.getPillList().get(pill))+"x<br>"));
		}
		builder.append("</body></html>");
		return builder.toString();
	}
}