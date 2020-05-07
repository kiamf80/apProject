import java.io.IOException;
import java.util.ArrayList;

public class Store {
	// you can buy neutral and hero cards if you have enough mana
	static ArrayList<Card> CardsinStore = (ArrayList<Card>) Card.allCards.clone();
	public static ArrayList<Card> buyableCards(Player p) {
		ArrayList<Card> buyableCards = new ArrayList<Card>();
		for (Card c : Card.allCards) {
			if (c.heroclass.equals(p.currentHero.name) || c.heroclass.equals("neutral")) {
				buyableCards.add(c);
			}
		}
		return buyableCards;
	}

	// you can sell cards that are not in your deck
	public static ArrayList<Card> sellableCards(Player p) {
		ArrayList<Card> sellableCards = new ArrayList<Card>();
		for (Card c : p.allCards) {
			if (!p.currentHero.deck.deck.contains(c)) {
				sellableCards.add(c);
			}
		}
		return sellableCards;
	}
	//method to sell a card
	public static void sell(Player p, Card c) throws IOException {
		if (Store.sellableCards(p).contains(c)) {
			p.allCards.remove(c);
			p.currentHero.deck.deck.remove(c);
			CardsinStore.add(c);
			p.setwallet(p.getwallet() + c.manacost);
			CLI.updateLog("SELL-CARD");
		} else {
			System.out.println("you can't sell this card");
		}
	}
	//function to buy a card from store
	public static void buy(Player p, Card c) throws IOException {
		if (Store.buyableCards(p).contains(c)) {
			if (p.getwallet() > c.manacost && p.allCards.size() < 20) {
				p.allCards.add(c);
				p.setwallet(p.getwallet() - c.manacost);
				CardsinStore.remove(CardsinStore.indexOf(c));
				CLI.updateLog("BUY-CARD");
			} else {
				System.out.println("not enough mana in wallet");
			}
		} else {
			System.out.println("you can't buy this card");
		}

	}
}
