package lab4;

public class Mage extends Character{

	private static final long serialVersionUID = 1L;

	public Mage(String name, int intelligence, int strength, int dexterity) {
		super(name, intelligence, strength, dexterity);
	}

	@Override
	public int calculatePower() {
		return 5*getIntelligence() + 2*getDexterity() + getStrength();
	}

}
