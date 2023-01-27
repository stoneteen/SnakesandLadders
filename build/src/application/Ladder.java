package application;

public class Ladder extends BoardPlace{

	public Ladder(int start, int end) {
		super(start, end);
	}


	@Override
	public String SetColor() {
		// TODO Auto-generated method stub
		return "ORANGE";
	}

	@Override
	public String active() {
		// TODO Auto-generated method stub
		
		return " have a stair to climb :)";
	}
	



}