package application;

public class Snake extends BoardPlace{

	public Snake(int start, int end) {
		super(start, end);
		
	}

	@Override
	public String SetColor() {
		// TODO Auto-generated method stub
		return "YELLOW";
	}

	@Override
	public String active() {
		// TODO Auto-generated method stub
		return " encountered a Snake :( ";
	}



}
