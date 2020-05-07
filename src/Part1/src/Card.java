import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class Card {
/*
how to create a card:
JSONObject jo = new JSONObject();
jo.put("name","CounterSpell");
jo.put("manacost",6);
jo.put("heroclass","neutral");
jo.put("type","spell");
jo.put("description","reverse your enemies latest move.");
Map m = new LinkedHashMap(2);
m.put("class","Counter");
m.put("value",1);
jo.put("spell",m);
PrintWriter pw = new PrintWriter("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\SpellCards\\CounterSpell.json");
pw.write(jo.toJSONString());
pw.flush();
 */
/* json files for cards at (C:\\Users\\asus\\Desktop\\apProject\\DataModels\\Cards)
 files include:
 manacost,heroclass,name,type
 weapon cards include: attack,durability
 minions include: attack,health
 some cards include: description
 Object obj = new JSONParser().parse(new FileReader("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\MageCards\\FireBall.json"));
 JSONObject jo = (JSONObject) obj;
 String type = (String) jo.get("type");
 System.out.println(type);
 System.out.println(jo.get("heroclass"));
 System.out.println(jo.get("name"));
 System.out.println(jo.get("manacost"));
 System.out.println(jo.get("description"));
 Map m = (Map) jo.get("spell");
System.out.println(m.get("class"));*/
	//An arrayList to hold all cards.
	static ArrayList<Card> allCards = new ArrayList<Card>();
	//common fields.
	String heroclass;
	long manacost;
	String type;
	String name;
	File cardFile;
	//constructor with file as input.
	public Card(File f) {
		cardFile = f;
	}
	//a method to read and parse a card from .json file.
	public abstract Card ParseCard(File f) throws FileNotFoundException, IOException, ParseException;
	//a method to initiate all cards at the start of the game.
	public static void initCards() throws FileNotFoundException, IOException, ParseException {
		Card c = new SpellCard(new File("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\MageCards\\FireBall.json"));
		File[] SpellCards = new File("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\SpellCards").listFiles();
		for(File f:SpellCards) allCards.add(new SpellCard(c.cardFile).ParseCard(f));
		File[] MinionCards = new File("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\MinionCards").listFiles();
		for(File f:MinionCards) allCards.add(new MinionCard(c.cardFile).ParseCard(f));
		File[] WeaponCards = new File("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\WeaponCards").listFiles();
		for(File f:WeaponCards) allCards.add(new WeaponCard(c.cardFile).ParseCard(f));
	}
	// method to print a cards stats.
	public abstract void PrintCard();

}
//subclass spellCards
class SpellCard extends Card {
	//specific fields.
	String description;
	Map<?, ?> spell;

	public SpellCard(File f) {
		super(f);
	}

	@Override
	public Card ParseCard(File f) throws FileNotFoundException, IOException, ParseException {
		SpellCard c = new SpellCard(f);
		FileReader fr = new FileReader(f);
		Object obj = new JSONParser().parse(fr);
		JSONObject jo = (JSONObject) obj;
		c.heroclass = (String) jo.get("heroclass");
		c.manacost = (long) jo.get("manacost");
		c.name = (String) jo.get("name");
		c.description = (String) jo.get("description");
		c.type = (String) jo.get("type");
		c.spell = (Map<?, ?>) jo.get("spell");
		fr.close();
		return c;
	}

	@Override
	public void PrintCard() {
		System.out.println("name: " + this.name);
		System.out.println("description: " + this.description);
		System.out.println("type: " + this.type);
		System.out.println("heroclass: " + this.heroclass);
		System.out.println("spell: ");
		System.out.println(this.spell.toString());

	}
}

class WeaponCard extends Card {
	long durability;
	long attack;

	public WeaponCard(File f) {
		super(f);
	}

	@Override
	public Card ParseCard(File f) throws FileNotFoundException, IOException, ParseException {
		WeaponCard c = new WeaponCard(f);
		Object obj = new JSONParser().parse(new FileReader(f));
		JSONObject jo = (JSONObject) obj;
		c.heroclass = (String) jo.get("heroclass");
		c.manacost = (long) jo.get("manacost");
		c.name = (String) jo.get("name");
		c.attack = (long) jo.get("attack");
		c.type = (String) jo.get("type");
		c.durability = (long) jo.get("durability");
		return c;
	}

	@Override
	public void PrintCard() {
		System.out.println("name: " + this.name);
		System.out.println("type: " + this.type);
		System.out.println("heroclass: " + this.heroclass);
		System.out.println("attack: " + this.attack);
		System.out.println("durability: " + this.durability);
	}

}

class MinionCard extends Card {
	long attack;
	long health;

	public MinionCard(File f) {
		super(f);
	}

	public Card ParseCard(File f) throws FileNotFoundException, IOException, ParseException {
		MinionCard c = new MinionCard(f);
		Object obj = new JSONParser().parse(new FileReader(f));
		JSONObject jo = (JSONObject) obj;
		c.heroclass = (String) jo.get("heroclass");
		c.manacost = (long) jo.get("manacost");
		c.name = (String) jo.get("name");
		c.attack = (long) jo.get("attack");
		c.type = (String) jo.get("type");
		c.health = (long) jo.get("health");
		return c;
	}

	@Override
	public void PrintCard() {
		System.out.println("name: " + this.name);
		System.out.println("type: " + this.type);
		System.out.println("heroclass: " + this.heroclass);
		System.out.println("attack: " + this.attack);
		System.out.println("health: " + this.health);
	}

}