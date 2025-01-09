package lab4;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		createSerializedTeams();
		// Serialized file path
		String rosterFilePath = "roster.ser";

		// Step 1: Deserialize the roster of teams
		List<Team> teams = deserializeTeams(rosterFilePath);

		if (teams == null) {
			System.err.println("Failed to deserialize the teams.");
			return;
		}

		// Step 2: Calculate power scores for each team using threads
		calculateTeamScoresWithThreads(teams);

		// Step 3: Rank teams by total power score
		rankTeamsByPower(teams);

		// Step 4: Write rankings to a file
		writeRankingsToFile("team_rankings.txt", teams);

		System.out.println("Team rankings have been saved to team_rankings.txt!");
	}

	// Deserialize the roster of teams from the file
	@SuppressWarnings("unchecked")
	private static List<Team> deserializeTeams(String filePath) {
		List<Team> teams = null;
		try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(filePath))) {
			teams = (List<Team>) oos.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return teams;
	}

	// Calculate the power scores for each team using threads
    private static void calculateTeamScoresWithThreads(List<Team> teams) {
        ExecutorService executor = Executors.newFixedThreadPool(teams.size());

        for (Team team : teams) {
            executor.submit(() -> {
                team.calculateTotalPower();
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	// Rank teams by total power score
	private static void rankTeamsByPower(List<Team> teams) {
		teams.sort(Comparator.comparingInt(Team::getTotalPower).reversed());
	}

	// Write team rankings to a file
	private static void writeRankingsToFile(String fileName, List<Team> teams) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			int rank = 1;
			for (Team team : teams) {
				writer.write(rank + ". " + team.getName() + ": " + team.getTotalPower() + "\n");
				rank++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Helper function to create serialized team file (for instructor use only)
	private static void createSerializedTeams() {
		List<Team> teams = new ArrayList<Team>();

		teams.add(
				new Team("TeamA", new Mage("Alice", 8, 4, 3), new Warrior("Bob", 6, 7, 3), new Rogue("Eve", 5, 6, 7)));
		teams.add(
				new Team("TeamB", new Mage("Tom", 7, 5, 6), new Warrior("Jerry", 8, 4, 5), new Rogue("Anna", 6, 7, 5)));
		teams.add(new Team("TeamC", new Mage("Lara", 9, 3, 4), new Warrior("Bruce", 7, 6, 3),
				new Rogue("Clint", 5, 8, 6)));
		teams.add(new Team("TeamD", new Mage("Diana", 10, 2, 5), new Warrior("Arthur", 9, 5, 4),
				new Rogue("Barry", 6, 9, 5)));
		teams.add(new Team("TeamE", new Mage("Natasha", 8, 4, 6), new Warrior("Steve", 9, 7, 2),
				new Rogue("Tony", 6, 8, 7)));

		serializeTeams("roster.ser", teams);
	}

	private static void serializeTeams(String filePath, List<Team> teams) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
			oos.writeObject(teams);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
