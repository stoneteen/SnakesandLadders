package application;

import java.util.Arrays;

public abstract class BoardPlace {
	
	private int start;
	private int end;
	
	public BoardPlace(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public abstract String SetColor();
	
	public abstract String active();
	
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}

	

}
