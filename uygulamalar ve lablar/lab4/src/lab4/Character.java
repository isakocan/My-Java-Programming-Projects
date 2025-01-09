package lab4;

import java.io.Serializable;

public abstract class Character implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int intelligence;
	private String name;
	private int strength;
	private int dexterity;
	
	public Character(String name, int intelligence, int strength, int dexterity) {
		super();
		this.intelligence = intelligence;
		this.name = name;
		this.strength = strength;
		this.dexterity = dexterity;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public String getName() {
		return name;
	}

	public int getStrength() {
		return strength;
	}

	public int getDexterity() {
		return dexterity;
	}
	
	public abstract int calculatePower();
	
	
}
