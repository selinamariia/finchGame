import java.util.ArrayList;
import java.util.Scanner;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

public class FinchGame1 {
	public static ArrayList<String> playerNames = new ArrayList<String>();

	public static void main(String[] args) 
	{
		Finch switchFinch = new Finch();
		Scanner playerInfo = new Scanner (System.in);
		System.out.println("How many players? (1 to 4)");
		int playerNum = playerInfo.nextInt();
		System.out.print("Input player names: ");
			
		for(int countNum= (playerNum+1); countNum>0; countNum--)
		{
			String name = playerInfo.nextLine();
			playerNames.add(name);
		}
		
		playerNames.remove(0);
		System.out.println(playerNames);
		
		for(int countNumb = (playerNum-1); countNumb>=0; countNumb--)
		{
			String s1 = playerNames.get(countNumb);
			System.out.println("Now repeat the pattern " + s1 + "!");
			switchFinch.saySomething("now repeat the pattern player " + s1 + "!");
			switchFinch.sleep(2000);
		}
		
		playerInfo.close();
		
	}

	
}
