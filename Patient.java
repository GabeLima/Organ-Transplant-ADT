// Gabriello Lima, 112803276, R01
import java.io.Serializable;

public class Patient implements Serializable, Comparable{
	private String name;
	private String organ;
	private int age;
	private BloodType bloodType;
	private int ID = -1;
	private boolean isDonor;
	private int numConnections;
	private String connectionString = "";
	/* Excess Variable Explanations:
	 * 
	 * @param isDonor
	 * Keeps track whether or not this patient is a donor
	 * 
	 * @param numConnections
	 * Keeps track of the number of connections this patient has in the 
	 * adjacency matrix in TransplantGraph
	 * 
	 * @param connectionString
	 * Keeps track of the ID connections this patient has in the adjacency
	 * matrix in String format
	 * 
	 */
	public Patient() {
		
	}
	/*
	 * This is a constructor used to initialize everything and anything I 
	 * deemed necessary for a patient to have
	 * 
	 */
	public Patient(int newID, String newName, int newAge, String newOrgan, String newBloodType, boolean isDonorOrNot) throws Exception {
		ID = newID;
		name = newName;
		age = newAge;
		organ = newOrgan;
		bloodType = new BloodType(newBloodType);
		isDonor = isDonorOrNot;
	}
	public int compareTo(Object o) {
		return 0;
		
	}
	public String toString() {
		return "";
		
	}
	public BloodType getBloodType() {
		return bloodType;
	}
	public String getName() {
		return name;
	}
	public int getID() {
		return ID;
	}
	public void setID(int i) {
		ID = i;
	}
	public int getAge() {
		return age;
	}
	public String getOrgan() {
		return organ;
	}
	public void resetConnections() {
		numConnections = 0;
	}
	public void incrementConnections() {
		numConnections++;
	}
	public int getConnections() {
		return numConnections;
	}
	public void setConnectionString(String s) {
		connectionString = s;
	}
	public String getConnectionString() {
		return connectionString;
	}
}
