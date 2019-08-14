package pillsgame;

public class NameGenerator {
	static String[] firstNames={"Abigail", "Alexander", "Charlotte", "Emily", "Emma", "Ethan", "Jacob", "James", "Michael", "William"};
	static String[] lastNames={"Smith", "Jones", "Williams", "Taylor", "Brown", "Evans", "Wilson", "Green", "Lewis", "Cooper"};
	public static String generate() {
		int first=((Double)(Math.random()*10)).intValue();
		int last=((Double)(Math.random()*10)).intValue();
		return new String(firstNames[first]+" "+lastNames[last]);
	}
}
