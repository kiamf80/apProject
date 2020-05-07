import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.transform.stream.StreamSource;

public class CLI {
	//Player field
	static Player p1;
	//funtion that executes commands from console.
	public static void ExecuteCommand(String input) throws FileNotFoundException, IOException, ParseException {
		// log out
		if (input.equals("exit")) {
			CLI.updateLog("LOG-OUT");
			p1 = null;
			System.out.println("logged out");
			CLI.enterGame();
		}
		// log out and stop program
		else if (input.equals("exit-a")) {
			CLI.updateLog("LOG-OUT");
			p1 = null;
			System.exit(0);
		}
		// deletes current player
		else if (input.equals("delete-player")) {
			System.out.println("password:");
			Scanner scan = new Scanner(System.in);
			String pw = scan.nextLine();
			if (BCrypt.checkpw(pw, p1.getPasswordHash())) {
				p1.delPlayer();
				CLI.updateLog("delete");
				p1 = null;
				System.out.println("Player deleted");
				CLI.enterGame();
			} else {
				System.out.println("Wrong PassWord");
			}
		}
		// goes to collection.
		else if (input.equals("collections")) {
			Collectionscl.Excom();
		}
		// goes to store
		else if (input.equals("store")) {
			Storecl.excm();
		}
		// shows your balance
		else if (input.equals("wallet")) {
			System.out.println(p1.getwallet());
		}
		// shows all commands
		else if (input.equals("hearthstone--help")) {
			System.out.println("available commands are: ");
			System.out.println("exit: log out");
			System.out.println("exit-a: log out and stop program");
			System.out.println("delete-player: deletes current player");
			System.out.println("collections: shows cards in collection.");
			System.out.println("store: goes to store");
			System.out.println("ls-a-heros: shows all heroes");
			System.out.println("ls-m-heros: shows current hero");
			System.out.println("select [Card name]: select a hero");
			System.out.println("ls-a-cards: shows all current cards");
			System.out.println("ls-m-cards: shows cards in deck");
			System.out.println("ls-n-cards: shows cards that you can add to your deck");
			System.out.println("add [Card name]: adds card to deck");
			System.out.println("remove [Card name]: removes card from deck");
			System.out.println("buy [Card name]: buy card");
			System.out.println("sell [Card name]: sell card");
			System.out.println("wallet: shows your balance");
			System.out.println("ls-s: shows cards you can sell");
			System.out.println("ls-b: shows cards you can buy");
			System.out.println("hearthstone--help: shows all commands");
			System.out.println("stat [card name]: shows cards stats");
			CLI.updateLog("HELP");
		} else {
			System.out.println(input);
			System.out.println("undefined command.");
			CLI.updateLog("ERROR:UNDEFINED-COMMAND");
		}
		p1.updateProfile();

	}

	public static void enterGame() throws FileNotFoundException, IOException, ParseException {
		System.out.println("already have an account?(y/n/e)");
		Scanner scan = new Scanner(System.in);
		String s = "";
		try {
			s = scan.next(".").charAt(0) + "";
		}catch(InputMismatchException e) {
			System.out.println("invalid input!");
		}
		if (s.equals("y")) {
			System.out.println("Username:");
			System.out.println("PassWord:");
			String u = scan.nextLine();
			u = scan.nextLine();
			String p = scan.nextLine();
			Player temp = Player.login(u, p);
			if (temp == null) {
				CLI.enterGame();
			} else {
				p1 = temp;
				CLI.updateLog("SIGN-IN");
			}
		} else if (s.equals("n")) {
			System.out.println("Username:");
			System.out.println("PassWord:");
			String u = scan.nextLine();
			u = scan.nextLine();
			String p = scan.nextLine();
			Player temp = Player.signup(u, p);
			if (temp == null) {
				CLI.enterGame();
			} else {
				p1 = temp;
				p1.updateProfile();
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(p1.Log, true)));
				pw.println("USER:" + p1.username);
				pw.println("CREATED-AT:" + new Timestamp(System.currentTimeMillis()));
				pw.println("PASSWORD:" + p1.getPasswordHash());
				pw.println(" ");
				pw.flush();
				pw.close();
			}
		} else if(s.equals("e")) {
			System.exit(0);
		}
		else {
			System.out.println("try again.");
			CLI.enterGame();
		}
	}

	public static void updateLog(String s) throws IOException {
		if (s.equals("delete")) {
			Scanner br = new Scanner(new FileReader(p1.Log));
			File temp = new File("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\Players\\Log\\temp.txt");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(temp, true)));
			int i = 0;
			while (br.hasNextLine()) {
				if (i == 3) {
					pw.println("DELETED-AT:" + new Timestamp(System.currentTimeMillis()));
				}
				pw.println(br.nextLine());
				i++;
			}
			pw.flush();
			pw.close();
			br.close();
			p1.Log.delete();
			temp.renameTo(p1.Log);
		} else {
			PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(p1.Log, true)));
			FileReader fr = new FileReader(p1.Log);
			pw1.println(s + ":" + new Timestamp(System.currentTimeMillis()) + System.lineSeparator());
			pw1.flush();
			pw1.close();
			fr.close();
		}

	}

}
class Collectionscl extends CLI{
	public static void Excom() throws IOException {
		System.out.println("available command are:");
		System.out.println("ls-a-cards");
		System.out.println("ls-m-cards");
		System.out.println("ls-n-cards");
		System.out.println("add [Card name]");
		System.out.println("remove [Card name]");
		System.out.println("stat [card name]");
		System.out.println("ls-a-heros");
		System.out.println("ls-m-heros");
		System.out.println("select [Card name]");
		System.out.println("back");
		Scanner scan = new Scanner(System.in);
		String input = "a";
		while (!input.equals("back")){
			input = scan.nextLine();
			if(input.equals("back")){
				break;
			}
			// shows all heros.
			if (input.equals("ls-a-heros")) {
				for(Hero h:Hero.allHeroes){
					System.out.println(h.name);
				}
				CLI.updateLog("LIST");
			}
			// shows current hero
			else if (input.equals("ls-m-heros")) {
				CLI.updateLog("LIST");
				System.out.println(p1.currentHero.name);
			}
			// select a hero
			else if (input.startsWith("select")) {
				try {
					for (Hero h : Hero.allHeroes) {
						if (h.name.equals(input.substring(7))) {
							p1.currentHero = h;
							System.out.println("Hero selected.");
						}
					}
				}catch (StringIndexOutOfBoundsException e){
					System.out.println("try again");
					CLI.updateLog("StringIndexOutOfBounds");
				}
				CLI.updateLog("SELECT");
			}
			// shows all current cards
			else if (input.equals("ls-a-cards")) {
				System.out.println("your cars are:");
				for (Card c : p1.allCards) {
					System.out.println(c.name);
				}
				CLI.updateLog("LIST");
			}
			// shows cards in deck
			else if (input.equals("ls-m-cards")) {
				System.out.println("the cards in your deck are:");
				for (Card c : p1.currentHero.deck.deck) {
					System.out.println(c.name);
				}
				CLI.updateLog("LIST");
			}
			// shows cards that you can add to your deck
			else if (input.equals("ls-n-cards")) {
				System.out.println("you can add these cards to your deck:");
				for (Card c : p1.allCards) {
					if (p1.currentHero.deck.deck.contains(c) == false) {
						System.out.println(c.name);
					}
				}
				CLI.updateLog("LIST");
			}
			// adds card to deck
			else if (input.startsWith("add")) {
				try{
					String s = input.substring(4);
					for (Card c : Card.allCards) {
						if (c.name.equals(s)) {
							p1.addToDeck(c);
							CLI.updateLog("ADD");
							break;
						}
					}
				}catch (StringIndexOutOfBoundsException e){
					System.out.println("try again");
					CLI.updateLog("StringIndexOutOfBounds");
				}
			}
			// removes card from deck
			else if (input.startsWith("remove")) {
				try {
					String s = input.substring(7);
					for (Card c : p1.currentHero.deck.deck) {
						if (c.name.equals(s)) {
							CLI.updateLog("REMOVE");
							p1.removeFromDeck(c);
							break;
						}
					}
				}catch (StringIndexOutOfBoundsException e){
					System.out.println("try again");
					CLI.updateLog("StringIndexOutOfBounds");
				}
			}
			//shows cards stats
			else if (input.startsWith("stat")) {
				try {
					String s = input.substring(5);
					for (Card c : Card.allCards) {
						if (c.name.equals(s)) {
							c.PrintCard();
							break;
						}
					}
				}catch (StringIndexOutOfBoundsException e){
					System.out.println("try again");
					CLI.updateLog("StringIndexOutOfBounds");
				}
				CLI.updateLog("STAT");
			}
			else {
				System.out.println(input);
				System.out.println("undefined command.");
				CLI.updateLog("ERROR:UNDEFINED-COMMAND");
			}
			p1.updateProfile();
		}
	}

}
class Storecl extends CLI{
	public static void excm() throws IOException {
		System.out.println("available commands are:");
		System.out.println("buy [Card name]");
		System.out.println("sell [Card name]");
		System.out.println("ls-s");
		System.out.println("ls-b");
		System.out.println("back");
		Scanner scan = new Scanner(System.in);
		String input = "temp";
		while (!input.equals("back")){
			input = scan.nextLine();
			if(input.equals("back")){
				break;
			}
			// buy card
			if (input.startsWith("buy")) {
				try {
					String s = input.substring(4);
					for (Card c : Card.allCards) {
						if (c.name.equals(s)) {
							Store.buy(p1, c);
							break;
						}
					}
				}catch (StringIndexOutOfBoundsException e){
					System.out.println("try again");
					CLI.updateLog("StringIndexOutOfBounds");
				}
				CLI.updateLog("BUY");
			}
			// sell card
			else if (input.startsWith("sell")) {
				try {
					String s = input.substring(5);
					for (Card c : Card.allCards) {
						if (c.name.equals(s)) {
							Store.sell(p1, c);
							break;
						}
					}
				}catch (StringIndexOutOfBoundsException e){
					System.out.println("try again");
					CLI.updateLog("StringIndexOutOfBounds");
				}
				CLI.updateLog("SELL");
			}
			// shows cards you can sell
			else if (input.equals("ls-s")) {
				System.out.println("you can sell these cards:");
				for (Card c : Store.sellableCards(p1)) {
					System.out.println(c.name);
				}
				CLI.updateLog("LIST");
			}
			// shows cards you can buy
			else if (input.equals("ls-b")) {
				System.out.println("you can buy these cards");
				for (Card c : Store.buyableCards(p1)) {
					System.out.println(c.name);
				}
				CLI.updateLog("ADD");
			}else {
				System.out.println(input);
				System.out.println("undefined command.");
				CLI.updateLog("ERROR:UNDEFINED-COMMAND");
			}
		}
	}
}