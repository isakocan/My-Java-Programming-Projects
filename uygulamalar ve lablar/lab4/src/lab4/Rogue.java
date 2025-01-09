package lab4;

public class Rogue extends Character{

	private static final long serialVersionUID = 1L;

	public Rogue(String name, int intelligence, int strength, int dexterity) {
		super(name, intelligence, strength, dexterity);
	}

	@Override
	public int calculatePower() {
		return 5*getDexterity() + 2*getStrength() + 2*getIntelligence();
	}

}
