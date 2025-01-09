package lab4;

public class Warrior extends Character{

	private static final long serialVersionUID = 1L;

	public Warrior(String name, int intelligence, int strength, int dexterity) {
		super(name, intelligence, strength, dexterity);
	}

	@Override
	public int calculatePower() {
		return 5*getStrength() + 3*getDexterity() + getIntelligence();
	}
	
	
	
}
