package pillsgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreTable {
	public List<HighScoreEntry> highScores=new ArrayList<HighScoreEntry>();
	public Boolean add(String player, int score) {
		HighScoreEntry hse=new HighScoreEntry(player, score);
		highScores.add(hse);
		Collections.sort(highScores);
		if(highScores.size()>10) {
			for(int i=10; i<highScores.size(); i++) {
				highScores.remove(i);
			}
		}
		return highScores.contains(hse);
	}
	public String toHtml() {
		StringBuilder builder=new StringBuilder();
		builder.append("<html><body>");
		Integer k=1;
		for(HighScoreEntry e:highScores) {
			int ln=e.getName().length();
			Integer thousands=e.getScore()/1000;
			Integer theRest=e.getScore()%1000;
			String restString=new String(theRest.toString());
			if(theRest<100 && thousands!=0) {
				if(theRest<10) {
					restString=new String("00"+theRest);
				} else {
					restString=new String("0"+theRest);
				}
			}
			String scoreString=new String((thousands!=0?thousands+" ":"")+restString);
			int ls=scoreString.length();
			builder.append(k+". "+e.getName());
			for(int i=0; i<(26-ln-ls-(k.toString().length()))-" Ft".length(); i++) {
				builder.append('.');
			}
			builder.append(scoreString+" Ft<br>");
			k++;
		}
		builder.append("</body></html>");
		return builder.toString();
	}
}
