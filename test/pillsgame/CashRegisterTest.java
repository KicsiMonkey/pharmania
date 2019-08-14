package pillsgame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CashRegisterTest {
	CashRegister c;
	PillBag pb;
	
	@Before
	public void setUp() throws Exception {
		c=new CashRegister(0, 0);
		pb=new PillBag();
	}
	@Test
	public void testPutPill() {
		Pill p=new PillGenerator().generate();
		c.putPill(p, pb);
		assertEquals(p.getPrice(), c.getThisTransaction());
	}
	@Test
	public void testRemovePill() {
		Pill p=new PillGenerator().generate();
		c.putPill(p,  pb);
		c.removePill(p, pb);
		assertEquals(new Integer(0), c.getThisTransaction());
	}

}
