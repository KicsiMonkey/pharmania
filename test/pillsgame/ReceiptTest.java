package pillsgame;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ReceiptTest {
	Receipt r;
	
	@Before
	public void setUp() throws Exception {
		r=new Receipt("Tester Bob", "Tester Bob", false);
	}

	@Test
	public void testIsValid() {
		assertEquals(false, r.isValid());
	}

	@Test
	public void testGetPrice() {
		assertEquals(0, r.getPrice());
	}

	@Test
	public void testGetPillList() {
		assertEquals(0, r.getPillList().size());
		
	}

	@Test
	public void testAddPill() {
		Pill p=new PillGenerator().generate();
		r.addPill(p);
		assertTrue(r.getPillList().containsKey(p));
	}

	@Test
	public void testGetPatientName() {
		assertEquals("Tester Bob", r.getPatientName());
	}

	@Test
	public void testGetSignature() {
		assertEquals("Tester Bob", r.getSignature());
	}

	@Test
	public void testIsStamped() {
		assertFalse(r.isStamped());
	}

}
