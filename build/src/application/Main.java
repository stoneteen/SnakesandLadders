package application;
	
import javafx.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/* We are going to be focusing on Model-View-Controller (MVC)
 * 
 * Model - the object representing the data to be manipulated by the controller ??
 * View -  GUI view of the data (reflection) -- GridPane
 * Controller - the code that manipulates the Model (and sometimes the view)
 * 
 */


public class Main extends Application {
	
	int GRID_SIZE = 10;
	int[][] allColors;
	int count = 1;
	int x,y,x1,y1;
	boolean term=false;
	int di;
	dice dice;
	String dimg;
	Rectangle rect = new Rectangle(50,50);
	Image image = new Image(droll());
	ImageView imageView = new ImageView(image);
	ArrayList<BoardPlace> SnakeandLadders = new ArrayList<BoardPlace>();
	
	@Override	
	public void start(Stage primaryStage) throws FileNotFoundException {
		printColors();
		GridPane grid=makegrid();
		
        Scanner input = new Scanner(System.in);
        System.out.println("Welcom to the battle!!!");
		System.out.println("Enter your name:");
		String name=input.nextLine();
		Player player1=new Player(name); 
		System.out.println(player1.getUserName()+"'s Shape is Circle in CYAN!!!");
		Circle p1=new Circle(20,Color.CYAN);
		FindPosition(player1);
		grid.add(p1,x,y);
		
		System.out.println("Pick the Game Mode under:");
		System.out.println("1.Player vs. AI");
		System.out.println("2.Player vs. Player");
		int choice=input.nextInt();
		Player player2 = new Player("Robot");
		if(choice==1) {
			player2.SetAI(true);
		}else if(choice==2) {
			Scanner input2 = new Scanner(System.in);
			System.out.println("Enter 2nd Player name:");
			String name1=input2.nextLine();
			player2.setUserName(name1);
		}
		System.out.println(player2.getUserName()+"'s Shape is Circle in BLUEVIOLET(darkpurple)!!!");
		Circle p2=new Circle(20,Color.BLUEVIOLET);
		FindPosition(player2);
		grid.add(p2,x1,y1);
		
	    //Setting the image view 
		Button button1= new Button("ROLE DICE");
		button1.setGraphic(imageView);
		button1.setOnAction(value ->  {
			dimg=droll();
	        Image image = new Image(dimg);
	        ImageView imageView = new ImageView(image);
	        if(term==false) {
	        term=true;
	        player1.updatePos(player1.getPosition()+di);
	        System.out.println(player1.getUserName()+" ROLL: "+di);
	        System.out.println();
	        checkPosition(player1);
	        FindPosition(player1);
	        GridPane.setConstraints(p1,x,y);
	        Button button= (Button)value.getSource();
	        button.setGraphic(imageView);
	        if(player2.GetAI()==true){
	        	dimg=droll();
	        	term=false;
	        	player2.updatePos(player2.getPosition()+di);
	        	System.out.println(player2.getUserName()+" ROLL: "+di);
		        System.out.println();
		        checkPosition(player2);
		        FindPosition(player2);
		        GridPane.setConstraints(p2,x1,y1);
	        }
	    
	        }
	        else {if(player2.GetAI()==false){
	        	term=false;
	        	player2.updatePos(player2.getPosition()+di);
	        	System.out.println(player2.getUserName()+" ROLL: "+di);
		        System.out.println();
		        checkPosition(player2);
		        FindPosition(player2);
		        GridPane.setConstraints(p2,x1,y1);
		        Button button= (Button)value.getSource();
		        button.setGraphic(imageView);
	        }}
	        });
		 HBox hbox=new HBox(button1);
		
		
		VBox vbox= new VBox(grid,hbox);
		vbox.setAlignment(Pos.CENTER);
		hbox.setAlignment(Pos.CENTER);
		//Go through the model, using its values to initialize the view
		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("Snakes&Ladders");
        primaryStage.show();
		
	}
	
	public GridPane makegrid() {
		
	//snakes:s:37 e:3,s:26 e:10,s47 e:16, s75e:32,s:96 e:77 5 pairs
	int snakeStart[]= {37,26,47,75,96};
	int snakeEnd[]= {3,10,16,32,77};
	//Ladders: s:4 e:56,s:12 e:50,s:22 e:58, s:41 e:79, s:63 e:100
	int ladderStart[]= {4,12,22,41,63};
	int laddersEnd[]= {56,50,58,79,100};
	String color[]= {"BLUE","PURPLE","TEAL","SIENNA","PLUM"};
	 for(int i=0;i<5;i++) {
		 //0 2 4 6 on arraylist would be the snakes
		 //1 3 4 7 on array list would be the ladders
		 Snake snakes=new Snake(snakeStart[i],snakeEnd[i]);
		 Ladder ladders=new Ladder(ladderStart[i],laddersEnd[i]);
		 SnakeandLadders.add(snakes);
		 SnakeandLadders.add(ladders);
	 }
	 
		GridPane grid = new GridPane();
		for(int row = 0; row < GRID_SIZE; row++) {
			for(int col = 0; col < GRID_SIZE; col++) {
				boolean flag=false;
				//My individual Nodes are going to be Rectangles
				Rectangle rect = new Rectangle(48,48);
				Rectangle rect1 = new Rectangle(40,40);
				//set the background color - based on the model's values
				
				//Need to give it a border:
				rect.setStrokeWidth(4);
				rect.setStroke(Color.BLACK);
				rect1.setStrokeWidth(12);
				rect1.setStroke(Color.BLACK);
				
				//Now the Rectangle has a defualt color.
				rect.setFill(Color.WHITE);
				rect1.setFill(Color.WHITE);
				if(allColors[row][col]==1)//start
				rect.setFill(Color.GREEN);
				if(allColors[row][col]==100){//end
				flag=true;
				rect1.setStrokeWidth(12);
				rect1.setStroke(Color.RED);
				rect1.setFill(Color.ORANGE);
				}
				if(allColors[row][col]==63){//end
					rect.setStroke(Color.RED);
					rect.setFill(Color.ORANGE);
					}
				for(int i=0;i<=9;i++) {
					if(allColors[row][col]==SnakeandLadders.get(i).getStart()) {
						if(SnakeandLadders.get(i).SetColor()=="ORANGE"&&allColors[row][col]!=63) {
							rect.setFill(Color.ORANGE);//Ladders
							rect.setStroke(borderColor(color[(int) Math.ceil((i-1)/2)]));
							break;
						}
						else if(SnakeandLadders.get(i).SetColor()=="YELLOW") {
							rect.setStroke(borderColor(color[(int) Math.ceil((i)/2)]));
							rect.setFill(Color.YELLOW);//snakes
							break;
						}
					}
						else if(allColors[row][col]==SnakeandLadders.get(i).getEnd()&&allColors[row][col]!=100) {
							if(SnakeandLadders.get(i).SetColor()=="ORANGE") {
								flag=true;
								rect1.setStroke(borderColor(color[(int) Math.ceil((i-1)/2)]));
								rect1.setFill(Color.ORANGE);//Ladders
								break;
							}
							else if(SnakeandLadders.get(i).SetColor()=="YELLOW") {
								flag=true;
								rect1.setStroke(borderColor(color[(int) Math.ceil((i)/2)]));
								rect1.setFill(Color.YELLOW);//snakes
								break;
							}
						}
					}
				//Add my bit of code that is going to act as the controller.
				if(flag==false)
					grid.add(rect, col, row);
				else
					grid.add(rect1, col, row);
			}
		}
		return grid;
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	private void printColors() {
		allColors = new int[GRID_SIZE][GRID_SIZE];
		//initialize my model
		for(int row =allColors.length-1; row >=0; row--) {
			if(row%2 == 0) 
			{
			for(int col = allColors[0].length - 1; col >=0; col--) 
			{	
				allColors[row][col] =count;
				allColors[row][col]=count++;
				
			}
			}
			else {
				for(int col = 0; col <allColors[0].length; col++) 
				{
					allColors[row][col] =count;
					allColors[row][col] =count++;
				}
			}
		}
		for(int row = 0; row < GRID_SIZE; row++) {
			for(int col = 0; col < GRID_SIZE; col++) {
				System.out.print("\t"+allColors[row][col] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public String droll() 
	{
	di = application.dice.rool();;
	if(di==1) 
	{
		return "C:\\Users\\mIKE\\eclipse-workspace\\build\\diceimg\\Dice_1.png";
			
	} else if(di==2) 
	{
		return"C:\\Users\\mIKE\\eclipse-workspace\\build\\diceimg\\Dice_2.png";
		
	} else if(di==3)
	{ 
		return "C:\\Users\\mIKE\\eclipse-workspace\\build\\diceimg\\Dice_3.png";
	} else if(di==4)
	{
		return "C:\\Users\\mIKE\\eclipse-workspace\\build\\diceimg\\Dice_4.png";
	} else if(di==5)
	{
		return"C:\\Users\\mIKE\\eclipse-workspace\\build\\diceimg\\Dice_5.png";
	}	else if(di==6)
	{
		return "C:\\Users\\mIKE\\eclipse-workspace\\build\\diceimg\\Dice_6.png";
		
	}
	return "This does happen"; 
	}
	
	public void FindPosition(Player p) {
		for(int row = 0; row < GRID_SIZE; row++) {
			for(int col = 0; col < GRID_SIZE; col++) {
				if(p.getPosition()==allColors[row][col]) {
					if(p.GetAI()==true) {
						x1=col;
						y1=row;
					}else{
						x1=col;
						y1=row;
						x=col;
						y=row;
					}
				}
			}
		}
	}
	
	public void checkPosition(Player p) {
		//check if player in snake or ladder
		tp(p);
		//check if player get to the end
		winner(p);
		//check if it over the end then go back how many unit it over like 103 would go 3 units back
		if(p.getPosition()>100) {
			p.updatePos(100-(p.getPosition()-100));
	}
	}
	
	public void tp(Player p) {
		for(int i=0;i<9;i++) {
			if(p.getPosition()==SnakeandLadders.get(i).getStart()){
				System.out.println(p.getUserName()+SnakeandLadders.get(i).active());
				p.updatePos(SnakeandLadders.get(i).getEnd());
				break;
			}
		}
	}
	
	public void winner(Player p) {
		if(p.getPosition()==100) {
			System.out.println(p.getUserName()+" win!!! \\(^O^)/");
			System.exit(0);
	}
	}
	
	public Paint borderColor(String C){
		if(C=="BLUE")
		return Color.BLUE;
		else if(C=="PURPLE")
			return Color.PURPLE;
		else if(C=="TEAL")
			return Color.TEAL;
		else if(C=="SIENNA")
			return Color.SIENNA;
		else if(C=="PLUM")
			return Color.PLUM;
		return null;
	}
}
