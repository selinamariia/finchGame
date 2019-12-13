import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

public class FinchGameEdited
{
	//class variable to store names
	public static ArrayList<String> playerNames = new ArrayList<String>();

	public static void main(String[] args) 
	{
	/**
	 * first initialise all finch robots to be used in the game, the array list to store the game sequence
	 * and the array list to store the user input, the amount of lives given and the variable rounds which
	 * is the same as the size of the game sequence array
	 */
	Finch redFinch = new Finch();
	Finch yellowFinch = new Finch();
	Finch greenFinch = new Finch();
	
	ArrayList<Integer> generateList = new ArrayList<Integer>();
	ArrayList<Integer> userList = new ArrayList<Integer>();

	int lives = 3;
	int rounds = 0;
	
	//option to be a multi-player game, scanner stores player names in an array to be accessed
	//later
	
	Scanner playerInfo = new Scanner (System.in);
	System.out.println("How many players? (1 to 4)");
	yellowFinch.saySomething("how many players?");
	int playerNum = playerInfo.nextInt();
	yellowFinch.saySomething("input player names");
	System.out.print("Input player names: ");
		
	for(int countNum= (playerNum+1); countNum>0; countNum--)
	{
		String name = playerInfo.nextLine();
		playerNames.add(name);
	}
	playerNames.remove(0);
	System.out.println(playerNames);
	playerInfo.close();
	/**
	 * next check if the game is 'on', using a finch robot as a switch. While the robot is upside down the game
	 * is off
	 */
	while(yellowFinch.isFinchUpsideDown()==false)
	{
		/**
		 * the game runs as long as the player has more than 0 lives left
		 */
			while(lives>0)
			{
				/**
				 * call on the method to begin a new round, this method will output the lives remaining and 
				 * player's score, then begin generating the game sequence by getting a random number from a
				 * method and adding this number into our game sequence list
				 */
				generateList.add(getRandomInt(1, 3));
				startRound(yellowFinch, lives);
				rounds = generateList.size();
				
				//print out list to check its contents
				System.out.println(generateList);
				
				/**
				 * next we call on the method to read the game sequence array, a for loop will get each element
				 * from the list and pass this into the method to blink the colour of the corresponding finch robot
				 */
				
				for(int i = 0; i <rounds; i++)
				{
					int listNum = generateList.get(i);
					readFinchArray(yellowFinch, redFinch, greenFinch, listNum);
				}
				
				/**
				 * now we reset the user input list so that the player can start the round from the beginning and
				 * the finch instructs the first player to repeat the pattern they just saw, this player needs to
				 * return the same sequence and then the next player's turn commences
				 */
				for(int countNumb = (playerNum-1); countNumb>=0; countNumb--)
				{
					userList.clear();
					String s1 = playerNames.get(countNumb);
					System.out.println("Now repeat the pattern " + s1 + "!");
					yellowFinch.saySomething("now repeat the pattern player " + s1 + "!");
					yellowFinch.sleep(2000);
				
					/**
					 * next we have to read the user input, to do this we first make sure that the program knows to not
					 * gather more input than the amount of rounds so no errors occur by using a for loop.
					 * then call on a method to check which finch has been tapped by the player and store this information
					 * inside the user input array list
					 */
					
					int finchTapped = 0;
					for (int inputCount = 0; inputCount < rounds; inputCount ++)
					{
						userList.add(checkUserInput(yellowFinch, redFinch, greenFinch, finchTapped));
					}
					
					//print list to check
					System.out.println(userList);
					
					/**
					 * once we've gathered the user input, we need to check it against the game sequence to determine
					 * whether the round was won. To do this we use a for loop to access the element at the first index
					 * of both lists then compare them to each other.
					 * If the elements match then the loop moves on to the next element until we've checked all, if the
					 * elements do not match then the loop ends and a life is removed.  
					 */
					
					for (int checkIndex = 0; checkIndex <rounds; checkIndex ++)
					{
						int userListInput = userList.get(checkIndex);
						int generateListInput = generateList.get(checkIndex);
						
						if(userListInput != generateListInput)
						{
							checkIndex = rounds;
							lives --;
						}
						else
						{
							checkIndex ++;
						}
					}
				}
			}
			
			
			//once all lives are lost the game is over at this point the program will output the final score and 
			//then the finch robots will quit
			System.out.println("Game over! Your score is " +(rounds - 3));
			yellowFinch.saySomething("game over. Your score is " + (rounds - 3));
			yellowFinch.sleep(3000);
		
			yellowFinch.quit();
			redFinch.quit();
			greenFinch.quit();
		}
	}
	/**
	 * this method is used to signal that a new round has started
	 * @param myFinch
	 * @param lives
	 */
	public static void startRound(Finch myFinch, int lives)
	{
		System.out.println("Lives remaining: " + lives);
		myFinch.saySomething("you have" + lives + "lives remaining ");
		myFinch.sleep(7000);
	}
	/**
	 * this method generates a random number between 1-4, which are given as min and max parameters, each time a new round begins
	 * @param min
	 * @param max
	 * @return random number
	 */
	private static int getRandomInt(int min, int max)
	{		
		Random r = new Random();
		return r.nextInt((max-min)+1) + min;
	}
	
	 /**
	 * this method gets the randomly generated numbers (1-4) stored in the generated array and blinks the corresponding
	 * finch to play the sequence. Takes in as parameters every finch robot connected and the index.
	 * @param yellowFinch
	 * @param redFinch
	 * @param greenFinch
	 * @param blueFinch
	 * @param index
	 */
	public static void readFinchArray (Finch yellowFinch, Finch redFinch, Finch greenFinch, int listNum)
	{

		if(listNum == 1)
		{
			yellowFinch.setLED(255, 150, 50, 500);
			finchWait(yellowFinch);
		}
		else if(listNum == 2)
		{
			redFinch.setLED(255, 0, 0, 500);
			finchWait(redFinch);
		}
		else if(listNum == 3)
		{
			greenFinch.setLED(0, 255, 0, 500);
			finchWait(greenFinch);
		}
		else
		{
			System.out.println("Error");
		}
		
	}
	/**
	 * this method gets the assigned number (1-4) of the finch that has been tapped and returns this to the main method where it
	 * is stored in an array to be checked as well as start a timer that waits 5 seconds for user input before automatically quitting
	 * The parameters are every finch robot and if that finch is tapped it returns the value associated with that robot. 
	 * @param yellowFinch
	 * @param redFinch
	 * @param greenFinch
	 * @param finchTapped
	 * @return
	 */
	public static int checkUserInput(Finch yellowFinch, Finch redFinch, Finch greenFinch, int finchTapped)
	{
		int isTapped = 0;
		long currentTime = System.currentTimeMillis();
		long timeAfterTimer = currentTime +5000;

		while((System.currentTimeMillis()<timeAfterTimer) && isTapped == 0) 
		{
			if(yellowFinch.isObstacle() == true)
			{
				finchTapped = 1;
				yellowFinch.setLED(255, 150, 50, 1000);
				isTapped++;
			}
			else if(redFinch.isObstacle()== true)
			{
				finchTapped = 2;
				redFinch.setLED(255, 0, 0, 1000);
				isTapped++;
			}
			else if(greenFinch.isObstacle()==true)
			{
				finchTapped = 3;
				greenFinch.setLED(0, 255, 0, 1000);
				isTapped++;
			}
			
		}
		return finchTapped;
	}
	
	/**
	 * this method makes the finch robots pause between flashes so the sequence of colours appears in a clearer way
	 * takes as parameter the finch that is called on to blink, and makes this finch sleep
	 */
	public static void finchWait(Finch myFinch)
	{
		int finchWait = 0;
		long currentTime = System.currentTimeMillis();
		long timeAfterTimer = currentTime +5000;
		while((System.currentTimeMillis()<timeAfterTimer) && finchWait == 0)
		{
			myFinch.sleep(800);
			finchWait ++;
		}
	}
	
}


