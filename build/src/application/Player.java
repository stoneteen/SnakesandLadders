package application;

public class Player {
	private int position=1;
	private String userName;
	private boolean AI=false;
	
	public Player(String userName) {
		this.userName = userName; 
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPosition() {
		return position;
	}

	public void updatePos(int x) {
		position=x;
	}
	
	public void SetAI(boolean ai) {
		AI=ai;
	}
	public boolean GetAI() {
		return AI;
	}
	
	}
