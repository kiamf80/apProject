import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static java.lang.Math.toIntExact;

public abstract class Hero {
	long health;
	Deck deck = new Deck();
	String name;
	Map heroPower;
	Map specialPower;
	static ArrayList<Hero> allHeroes = new ArrayList<>();
	public static void initHero() throws IOException, ParseException {
		File[] files = new File("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\Heroes").listFiles();
		for (File f : files) {
			FileReader fr = new FileReader(f);
			JSONParser jp = new JSONParser();
			Object obj = jp.parse(fr);
			JSONObject jo = (JSONObject) obj;
			if(jo.get("name").equals("Mage")) {
				Mage.parseHero(jo);
			}
			else if(jo.get("name").equals("WarLock")) {
				WarLock.parseHero(jo);
			}
			else if(jo.get("name").equals("Rogue")) {
				Rogue.parseHero(jo);
			}

		}
	}
}

class Mage extends Hero {
	public static void parseHero(JSONObject jo) {
		Mage m = new Mage();
		m.health = (long) jo.get("health");
		m.name = (String) jo.get("name");
		JSONArray ja = (JSONArray) jo.get("deck");
		for(int i = 0;i<ja.size();i++){
			m.deck.addCard(Card.allCards.get(toIntExact((Long)ja.get(i))));
		}
		m.heroPower = (Map) jo.get("HeroPower");
		Hero.allHeroes.add(m);
	}
}

class WarLock extends Hero {
	public static void parseHero(JSONObject jo) {
		WarLock m = new WarLock();
		m.health = (long) jo.get("health");
		m.name = (String) jo.get("name");
		JSONArray ja = (JSONArray) jo.get("deck");
		for(int i = 0;i<ja.size();i++){
			m.deck.addCard(Card.allCards.get(toIntExact((Long)ja.get(i))));
		}
		m.heroPower = (Map) jo.get("HeroPower");
		Hero.allHeroes.add(m);
	}
}

class Rogue extends Hero {
	public static void parseHero(JSONObject jo) {
		Rogue m = new Rogue();
		m.health = (long) jo.get("health");
		m.name = (String) jo.get("name");
		JSONArray ja = (JSONArray) jo.get("deck");
		for(int i = 0;i<ja.size();i++){
			m.deck.addCard(Card.allCards.get(toIntExact((Long)ja.get(i))));
		}
		m.heroPower = (Map)jo.get("HeroPower");
		Hero.allHeroes.add(m);
	}
}

class Deck {
	ArrayList<Card> deck = new ArrayList<Card>();

	// function to add card to deck
	public void addCard(Card c) {
		// max deck size is 15 and max number of same cards in one deck is 2
		if (deck.size() < 15 && deck.lastIndexOf(c) - deck.indexOf(c) == 0) {
			this.deck.add(c);
		}
	}

	public void PrintDeck() {
		for (Card c : deck) {
			c.PrintCard();
			System.out.println();
		}
	}

	public Card getCard(String CardName) {
		for (Card c : deck) {
			if (c.name == CardName) {
				return c;
			}
		}
		return null;
	}

}