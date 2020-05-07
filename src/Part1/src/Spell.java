public abstract class Spell {
	int value;
	public Spell(int value) {
		this.value = value;
	}

}

//class damagespell extends Spell {
//	public damagespell(int value) {
//		super(value);
//	}
//
//	// spell that deals damage to a minion
//	public void drawspell(MinionCard m) {
//		m.health -= value;
//	}
//
//}
//class HeroDamageSpell extends Spell{
//
//	public HeroDamageSpell(int value) {
//		super(value);
//	}
//	public void drawspell(Hero m) {
//		m.health -= value;
//	}
//}
//// spell that transform minion to a 1/1
//class MinionTransformSpell extends Spell {
//	public MinionTransformSpell(int value) {
//		super(value);
//	}
//
//	public void drawspell(MinionCard m) {
//		m.health = 1;
//		m.attack = 1;
//	}
//}
//
//// spell that gives you a weapon with +2/+2
//class WeaponGainSpell extends Spell {
//	public WeaponGainSpell(int value) {
//		super(value);
//	}
//
//	public void drawspell(WeaponCard w,Hero h) {
//			h.deck.addCard(w);
//	}
//}
//class counter extends Spell{
//
//	public counter(int value) {
//		super(value);
//	}
//}
//class vision extends Spell{
//
//	public vision(int value) {
//		super(value);
//	}
//}
//class theft extends Spell{
//
//	public theft(int value) {
//		super(value);
//	}
//}