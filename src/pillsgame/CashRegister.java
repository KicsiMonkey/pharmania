package pillsgame;

public class CashRegister {
	private Integer total=0;
	private Integer thisTransaction=0;
	public CashRegister(int total, int last) {
		this.total=total;
		this.thisTransaction=last;
	}
	public void putPill(Pill p, PillBag bag) {
		bag.addPill(p);
		thisTransaction+=p.getPrice();
	}
	public void removePill(Pill p, PillBag bag) {
		bag.removePill(p);
		thisTransaction-=p.getPrice();
	}
	public int pay(Customer customer, PillBag bag) {
		int x;
		if(customer.isValid()) {
			if(customer.isServedRight(bag)) {
				total+=thisTransaction;
				x=1; 				
			} else {
				x=0;
			}
		} else {
			if(total<5000) {
				total=0;
			} else {
				total-=5000;
			}
			x=-1;
		}
		bag.clear();
		bag.size=0;
		thisTransaction=0;
		return x; // 1: served a valid customer correctly,
				  // 0: served a valid customer incorrectly,
				  //-1: served an invalid customer
	}
	public Integer getTotal() {
		return total;
	}
	public String totalString() {
		return amountToString(total);
	}
	public Integer getThisTransaction() {
		return thisTransaction;
	}
	public String thisTransactionString() {
		return amountToString(thisTransaction);
	}
	private String amountToString(Integer a) {
		Integer thousands=a/1000;
		Integer theRest=a%1000;
		String restString=new String(theRest.toString());
		if(theRest<100 && thousands!=00) {
			if(theRest<10) {
				restString=new String("00"+theRest);
			} else {
				restString=new String("0"+theRest);
			}
		}
		return new String((thousands!=0?thousands:"")+" "+restString);
	}
}
