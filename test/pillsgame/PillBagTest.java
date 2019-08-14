package pillsgame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PillBagTest {
	PillBag b;
	@Before
	public void setUp() throws Exception {
		b=new PillBag();
	}

	@Test
	public void testAddPill() {
		Pill p=new PillGenerator().generate();
		b.addPill(p);
		assertTrue(b.containsKey(p));
	}

	@Test
	public void testRemovePill() {
		Pill p=new PillGenerator().generate();
		b.addPill(p);
		b.addPill(p);
		b.removePill(p);
		assertEquals((Integer)1, b.get(p));
	}

}
