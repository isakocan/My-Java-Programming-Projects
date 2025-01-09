package lab4;

import java.io.Serializable;

public class Team implements Serializable {

	private static final long serialVersionUID = 1L;
	private int totalPower;
	private String name;
	private Warrior warrior;
	private Rogue rogue;
	private Mage mage;
	
	public Team(String name, Mage mage, Warrior warrior, Rogue rogue) {
		this.name = name;
		this.warrior = warrior;
		this.rogue = rogue;
		this.mage = mage;
	}

	public int getTotalPower() {
		return totalPower;
	}

	public String getName() {
		return name;
	}
	
	public void calculateTotalPower() {
		this.totalPower = warrior.calculatePower() + rogue.calculatePower() + mage.calculatePower();
	}
	
	
}
