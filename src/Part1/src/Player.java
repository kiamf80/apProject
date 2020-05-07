import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mindrot.jbcrypt.BCrypt;

public class Player {
	String username;
	String passwordHash;
	ArrayList<Hero> playerHero = new ArrayList<Hero>();
	Hero currentHero;
	File proFile;
	File Log;
	long wallet = 50;
	ArrayList<Card> allCards = new ArrayList<Card>();
	static HashSet<Player> Players = new HashSet<Player>();

	private Player(String username, String pw) throws FileNotFoundException, IOException, ParseException {
		this.username = username;
		this.passwordHash = pw;
		Random rand = new Random();
		int r = rand.nextInt(Hero.allHeroes.size());
		this.playerHero.addAll(Hero.allHeroes);
		currentHero = this.playerHero.get(r);
		for (Hero h:playerHero) {
			for(Card c: h.deck.deck){
				this.allCards.add(c);
			}
		}
		proFile = new File("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\Players\\Profile\\" + username + ".json");
		Log = new File("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\Players\\Log\\" + username + ".txt");
		Players.add(this);
	}

	public static Player signup(String username, String pw) throws FileNotFoundException, IOException, ParseException {
		boolean flag = true;
		for (Player p : Players) {
			if (p.username.equals(username)){
				System.out.println("player with this username already exists.");
				flag = false;
				break;
			}
		}
		if (flag) {
			Player newplayer = new Player(username, BCrypt.hashpw(pw,BCrypt.gensalt()));
			File[] files = new File("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\Players\\Log").listFiles();
			for (File f : files) {
				if (f.getAbsolutePath()
						.equals("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\Players\\Log\\" + username + ".txt")) {
					newplayer.Log.delete();
					newplayer.Log = new File("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\Players\\Log\\" + username + ".txt");
					break;
				}
			}
			System.out.println("account created succesfully.");
			Players.add(newplayer);
			return newplayer;
		}
		return null;
	}

	public static Player login(String username, String pw) {
		for (Player p : Players) {
			if (p.username.equals(username) && BCrypt.checkpw(pw,p.getPasswordHash())) {
				System.out.println("Logged in succesfuly.");
				return p;
			}
		}
		System.out.println("login failed");
		return null;
	}

	String getPasswordHash() {
		return this.passwordHash;
	}

	public void delPlayer() {
		this.proFile.setWritable(true);
		this.proFile.delete();
		Players.remove(this);
	}

	public void updateProfile() throws FileNotFoundException {
		JSONObject jo = new JSONObject();
		jo.put("username", this.username);
		jo.put("password", this.getPasswordHash());
		jo.put("wallet", this.wallet);
		jo.put("log", this.Log.getAbsolutePath());
		JSONArray heroes = new JSONArray();
		for(Hero h:this.playerHero){
			LinkedHashMap m = new LinkedHashMap();
			m.put("heroname", h.name);
			m.put("health", h.health);
			JSONArray ja = new JSONArray();
			for (Card c : h.deck.deck) {
				ja.add(c.name);
			}
			m.put("deck", ja);
			heroes.add(m);
		}
		jo.put("heroes",heroes);
		JSONArray ja = new JSONArray();
		for (Card c : this.allCards) {
			ja.add(c.name);
		}
		jo.put("allCards", ja);
		PrintWriter pw = new PrintWriter(proFile);
		pw.write(jo.toJSONString());
		pw.flush();
		pw.close();
		System.gc();
	}

	public static void initPlayers() throws FileNotFoundException, IOException, ParseException {
		File[] files = new File("C:\\Users\\asus\\Desktop\\apProject\\DataModels\\Players\\Profile").listFiles();
		for (File f : files) {
			FileReader fr = new FileReader(f);
			JSONParser jp = new JSONParser();
			Object obj = jp.parse(fr);
			JSONObject jo = (JSONObject) obj;
			Player p = new Player((String) jo.get("username"), (String) jo.get("password"));
			p.allCards.clear();
			for(Hero h:p.playerHero){
				h.deck.deck.clear();
			}
			p.wallet = (long) jo.get("wallet");
			JSONArray ja = (JSONArray) jo.get("allCards");
			for (int i = 0; i < ja.size(); i++) {
				String s = (String) ja.get(i);
				for (Card c : Card.allCards) {
					if (c.name.equals(s)) {
						p.allCards.add(c);
					}
				}
			}
			JSONArray m = (JSONArray) jo.get("heroes");
			for(int i=0;i<m.size();i++){
				Map temp = (Map)m.get(i);
				for(Hero h:Hero.allHeroes){
					if(h.name.equals(temp.get("heroname"))){
						h.health = (long) temp.get("health");
						p.playerHero.add(h);
						ja.clear();
						ja = (JSONArray) temp.get("deck");
						for (int j = 0; j < ja.size(); j++) {
							String s = (String) ja.get(j);
							for (Card c : Card.allCards) {
								if (c.name.equals(s)) {
									h.deck.deck.add(c);
								}
							}
						}
						break;
					}
				}
			}
			p.currentHero = p.playerHero.get(0);
			fr.close();
			fr = null;
		}
	}

	public void addToDeck(Card c) {
		if (allCards.contains(c)) {
			currentHero.deck.deck.add(c);
		}
	}

	public void removeFromDeck(Card c) {
		if (currentHero.deck.deck.contains(c)) {
			currentHero.deck.deck.remove(c);
		}
	}

	// getter setter methods
	public long getwallet() {
		return wallet;
	}

	public void setwallet(long l) {
		this.wallet = l;
	}
}
