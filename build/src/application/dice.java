package application;

public interface dice {
	public static int rool() {
		return (int) Math.floor(Math.random()*(6-1+1)+1);
	}
}
