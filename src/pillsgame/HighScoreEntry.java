package pillsgame;

import java.io.Serializable;

public class HighScoreEntry implements Comparable<HighScoreEntry>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String player;
	private int score;
	public HighScoreEntry(String p, int s) {
		player=p;
		score=s;
	}
	public String getName() {
		return player;
	}
	public int getScore() {
		return score;
	}
	@Override
	public int compareTo(HighScoreEntry o) {
		return (-1)*(this.score-o.score);
	}
}
