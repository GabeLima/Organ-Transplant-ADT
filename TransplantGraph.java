import java.io.Serializable;
import java.util.ArrayList;
import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.Scanner;

public class TransplantGraph implements Serializable{
	private ArrayList<Patient> donor;
	private ArrayList<Patient> recipients;
	private boolean connections[][] = new boolean[MAX_PATIENTS][MAX_PATIENTS];
	public static final int MAX_PATIENTS = 100;
	
	public TransplantGraph() {
		
	}
	/*
	 * This constructor initializes this class file by calling buildFromFiles()
	 */
	public TransplantGraph(String donorFile, String recipientFile) {
		TransplantGraph var = buildFromFiles(donorFile, recipientFile);
		donor = var.getDonor();
		recipients = var.getRecipients();
		createConnections();
	}
	/*
	 * This constructor initializes this class file by catching returns from
	 * buildFromFiles(). Used in tangent with the above constructor.
	 */
	public TransplantGraph(ArrayList<Patient> newDonor, ArrayList<Patient> newRec) {
		donor = newDonor;
		recipients = newRec;
		createConnections();
	}
	/* This method reads the files from which donors and recipients are 
	 * derived from and adds them to the ArrayLists of donors and patients
	 * 
	 * 
	 * 
	 */
	public static TransplantGraph buildFromFiles(String donorFile, String recipientFile) {
		ArrayList<Patient> tempDonor = new ArrayList<Patient>(MAX_PATIENTS);
		ArrayList<Patient> tempRecipients = new ArrayList<Patient>(MAX_PATIENTS);
		try {
		      File newFile = new File(donorFile);
		      Scanner reader = new Scanner(newFile);
		        //ID, NAME, AGE, ORGAN, BLOODTYPE
		        int ID = 0;
		        String name = null;
		        int age = 0;
		        String organ = null;
		        String bloodType = null;
		      while (reader.hasNextLine()) {
		    	  String tempString = "";
		    	  String data = reader.nextLine() + ",";
		       // BloodType bt = null;
			        int iteration = 0;
		        for(int i = 0; i < data.length(); i ++) {
		        	if(data.charAt(i) != ',') {
			        	tempString += data.charAt(i);
		        	}
		        	else {
		        		iteration++;
		        		if(iteration == 1) {
		        			ID = Integer.parseInt(tempString);
		        		}
		        		else if (iteration == 2) {
		        			name = tempString;
		        		}
						else if (iteration == 3) {
							tempString = tempString.replaceAll(" ", "");
							age = Integer.parseInt(tempString);
		        		}
						else if (iteration == 4) {
							organ = tempString;
						}
						else if (iteration == 5) {
							tempString = tempString.replaceAll(" ", "");
							bloodType = tempString;
						}
		        		tempString ="";
		        	}
		        }
	        	tempDonor.add(new Patient(ID, name, age, organ, bloodType, true));
		      }
		      reader.close();
		      newFile = new File(recipientFile);
		      reader = new Scanner(newFile);
		      ID = 0;
		      name = null;
		      age = 0;
		      organ = null;
		      bloodType = null;
		      while (reader.hasNextLine()) {
		        String data = reader.nextLine() + ",";
		        String tempString = "";
		        int iteration = 0;
		        for(int i = 0; i < data.length(); i ++) {
		        	if(data.charAt(i) != ',') {
			        	tempString += data.charAt(i);
		        	}
		        	else {
		        		iteration++;
		        		if(iteration == 1) {
		        			ID = Integer.parseInt(tempString);
		        		}
		        		else if (iteration == 2) {
		        			name = tempString;
		        		}
						else if (iteration == 3) {
							tempString = tempString.replaceAll(" ", "");
							age = Integer.parseInt(tempString);
		        		}
						else if (iteration == 4) {
							organ = tempString;
						}
						else if (iteration == 5) {
							tempString = tempString.replaceAll(" ", "");
							bloodType = tempString;
						}
		        		tempString ="";
		        	}
		        }
			      tempRecipients.add(new Patient(ID, name, age, organ, bloodType, false));
		      }
		      reader.close();
		      return new TransplantGraph(tempDonor, tempRecipients);
		    } catch (FileNotFoundException e) {
		      System.out.println("File was unable to be found.");
		      e.printStackTrace();
		    } catch(Exception e) {
		    	System.out.println(e.getMessage() + ", could not add.");
			      e.printStackTrace();
		    }
			return new TransplantGraph(tempDonor, tempRecipients);
	}
	/* This method adds patients to the recipients list and creates connections
	 * in tangent with the createConnections() method
	 * 
	 * 
	 */
	public void addRecipient(Patient patient) throws Exception {
		if(recipients.size() != MAX_PATIENTS) {
			recipients.add(patient);
			createConnections();
		}
		else
			System.out.println("Recipients have already reached their maximum "
			  + "size, cannot add this patient.");
	}
	
	/* This method adds patients to the donor list and creates connections
	 * in tangent with the createConnections() method
	 * 
	 * 
	 */
	public void addDonor(Patient patient) throws Exception {
		if(donor.size() != MAX_PATIENTS) {
			donor.add(patient);
			createConnections();
		}
		else
			System.out.println("Donor has already reached their maximum size, "
			  + "cannot add this patient.");
	}
	/* This method creates connections between donors and recipients and 
	 * determines whether or not a donor and a recipient are compatible 
	 * in tangent with the BloodType class
	 * 
	 * @param donorString
	 * Used to determine the connectionString in the patient class
	 * @param recipientString
	 * Used to determine the connectionString in the patient class
	 * 
	 * 
	 */
	public void createConnections() {
		for (int i = 0; i < donor.size(); i++) {
			donor.get(i).resetConnections();
		}
		for(int k = 0; k< recipients.size(); k++) {
			recipients.get(k).resetConnections();
		}
		String recipientString[] = new String[recipients.size()];
		for(int i = 0; i < recipients.size(); i ++) {
			recipientString[i] = "";
		}
		for (int row = 0; row< donor.size(); row++) { 
			String donorString = "";
			for(int column = 0; column < recipients.size(); column ++) {
				if(BloodType.isCompatible(recipients.get(column).getBloodType(), donor.get(row).getBloodType()) && donor.get(row).getOrgan().toLowerCase().replaceAll(" ", "").equals(recipients.get(column).getOrgan().toLowerCase().replaceAll(" ", ""))) {
					connections[row][column] = true;
					donor.get(row).incrementConnections();
					recipients.get(column).incrementConnections();
					if(donorString != "") {
						donorString += ", " + column;
					}
					else
						donorString += column;
					if(recipientString[column] != "") {
						recipientString[column] += ", " + row;
					}
					else
						recipientString[column] += row;
				}
				else {
					connections[row][column] = false;
				}
					recipients.get(column).setConnectionString(recipientString[column]);
			}
			donor.get(row).setConnectionString(donorString);
		}
		
	}
	/* This method is used when a donor or recipient is removed from the 
	 * donor or recipient list respectively. It alters the adjacency matrix
	 * and calls createConnections() to update the numeric value 
	 * of said connections
	 * 
	 * 
	 */
	public void removeConnections(int number, boolean isRow) {
		if(isRow) {
			for(int column = 0; column < recipients.size(); column++) { 
				for(int row = number; row < donor.size(); row++) { 
					connections[row][column] = connections[row + 1][column];
				}
			}
		}
		else {
			for(int row = 0; row < donor.size(); row++) {
				for(int column = number; column < recipients.size(); column++) { 
					connections[row][column] = connections[row][column + 1];
				}
			}
		}
		updateIDS();
		createConnections();
	}
	/* This method is used to make sure the ID's are kept in check with their
	 * index, unless they are being moved for various sorting reasons
	 * 
	 */
	public void updateIDS() {
		for(int i = 0; i < donor.size(); i++) {
			donor.get(i).setID(i);
		}
		for(int i = 0; i < recipients.size(); i++) {
			recipients.get(i).setID(i);
		}
	}
	/* This method is used to remove a patient from the recipient list.
	 * 
	 * @param nameFound
	 * keeps track of whether or not the designated patient is found in the 
	 * recipient list
	 * 
	 * 
	 */
	public void removeRecipient(String name) {
		boolean nameFound = false;
		for(int i = 0; i < recipients.size(); i ++) {
			if(recipients.get(i).getName().replaceAll(" ", "").equals(name.replaceAll(" ", ""))) {
				recipients.remove(i);
				removeConnections(i, false);
				System.out.println(name + " was removed from the organ transplant waitlist.");
				nameFound = true;
				break;
			}
		}
		if(!nameFound) {
			System.out.println(name + " could not be found in the organ transplant waitlist.");
		}
	}
	/* This method is used to remove a patient from the donor list.
	 * 
	 * @param nameFound
	 * keeps track of whether or not the designated patient is found in the 
	 * donor list
	 * 
	 * 
	 */
	public void removeDonor(String name) {
		boolean nameFound = false;
		for(int i = 0; i < donor.size(); i ++) {
			if(donor.get(i).getName().replaceAll(" ", "").equals(name.replaceAll(" ", ""))) {
				donor.remove(i);
				removeConnections(i, true);
				System.out.println(name + " was removed from the organ donor list.");
				nameFound = true;
				break;
			}
		}
		if(!nameFound) {
			System.out.println(name + " could not be found in the organ donor list.");
		}
	}
	/*
	 * This method is used to print all recipients in the recipient list.
	 * It is used in tangent with the createConnections() method as well as
	 * the space() methods in order to keep it readable upon printing.
	 * 
	 */
	public void printAllRecipients() {
		createConnections();
		System.out.println("Index | Recipient Name    | Age | Organ Needed  | "
		  + "Blood Type | Donor ID\r\n" + "===================================="
		  + "====================================");
		for(int i = 0; i < recipients.size(); i ++) {
			String tempString = "  ";
			tempString += i + space(i, 4);
			tempString += "| " + recipients.get(i).getName() + space(recipients.get(i).getName(), 18);
			tempString += "| " + recipients.get(i).getAge() + space(recipients.get(i).getAge(), 4);
			tempString += "|  " + recipients.get(i).getOrgan() + space(recipients.get(i).getOrgan() ,13);
			tempString += "|     " + recipients.get(i).getBloodType().getBloodType() + space(recipients.get(i).getBloodType().getBloodType(), 7);
			tempString += "| ";
			tempString += recipients.get(i).getConnectionString();
			System.out.println(tempString);
			/*
			 * int numDonors = 0; for(int row = 0; row < donor.size(); row++) {
			 * if(connections[row][i] && numDonors > 0) { tempString += ", " +
			 * donor.get(row).getID(); numDonors++; } else if (connections[row][i]) {
			 * tempString += donor.get(row).getID(); numDonors++; }
			 * 
			 * } System.out.println(tempString);
			 */
		}
	}
	/*
	 * This method is used to print all donors in the donor list.
	 * It is used in tangent with the createConnections() method as well as
	 * the space() methods in order to keep it readable upon printing.
	 * 
	 */
	public void printAllDonors() {
		createConnections();
		System.out.println("Index | Donor Name        | Age | Organ Donated | "
		  + "Blood Type | Recipient IDs\r\n" + "=============================="
		  + "===============================================");
		for(int i = 0; i < donor.size(); i ++) { 
			String tempString = "  ";
			tempString += i + space(i, 4);
			tempString += "| " + donor.get(i).getName() + space(donor.get(i).getName(), 18);
			tempString += "| " + donor.get(i).getAge() + space(donor.get(i).getAge(), 4);
			tempString += "|  " + donor.get(i).getOrgan() + space(donor.get(i).getOrgan() ,13);
			tempString += "|     " + donor.get(i).getBloodType().getBloodType() + space(donor.get(i).getBloodType().getBloodType(), 7);
			tempString += "| ";
			tempString += donor.get(i).getConnectionString();
			System.out.println(tempString);
			/*
			 * int numDonors = 0; for(int column = 0; column < recipients.size(); column++)
			 * { if(connections[i][column] && numDonors > 0) { tempString += ", " +
			 * recipients.get(column).getID(); numDonors++; } else if
			 * (connections[i][column]) { tempString += recipients.get(column).getID();
			 * numDonors++; }
			 * 
			 * }
			 */
			//System.out.println(tempString);
		}
	}
	
	/*
	 * This method is used to print all donors in a MODIFIED donor list.
	 * It is used in tangent with the sortDonors() method as well as
	 * the space() methods in order to keep it readable upon printing.
	 * 
	 */
	public void printListDonors(ArrayList<Patient> list) {
		System.out.println("Index | Donor Name        | Age | Organ Donated | "
				  + "Blood Type | Recipient IDs\r\n" + "=============================="
				  + "===============================================");
				for(int i = 0; i < list.size(); i ++) {
					String tempString = "  ";
					tempString += list.get(i).getID() + space(list.get(i).getID(), 4); 
					tempString += "| " + list.get(i).getName() + space(list.get(i).getName(), 18);
					tempString += "| " + list.get(i).getAge() + space(list.get(i).getAge(), 4);
					tempString += "|  " + list.get(i).getOrgan() + space(list.get(i).getOrgan() ,13);
					tempString += "|     " + list.get(i).getBloodType().getBloodType() + space(list.get(i).getBloodType().getBloodType(), 7);
					tempString += "| ";
					tempString += list.get(i).getConnectionString();
					System.out.println(tempString);
				}
	}
	/*
	 * This method is used to print all recipients in a MODIFIED recipient list.
	 * It is used in tangent with the sortDonors() method as well as
	 * the space() methods in order to keep it readable upon printing.
	 * 
	 */
	public void printListRecipients(ArrayList<Patient> list) {
		System.out.println("Index | Recipient Name    | Age | Organ Needed  | "
		  + "Blood Type | Donor ID\r\n" + "===================================="
		  + "====================================");
		for(int i = 0; i < list.size(); i ++) {
			String tempString = "  ";
			tempString += list.get(i).getID() + space(list.get(i).getID(), 4); 
			tempString += "| " + list.get(i).getName() + space(list.get(i).getName(), 18);
			tempString += "| " + list.get(i).getAge() + space(list.get(i).getAge(), 4);
			tempString += "|  " + list.get(i).getOrgan() + space(list.get(i).getOrgan() ,13);
			tempString += "|     " + list.get(i).getBloodType().getBloodType() + space(list.get(i).getBloodType().getBloodType(), 7);
			tempString += "| ";
			tempString += list.get(i).getConnectionString();
			System.out.println(tempString);
		}
	}
	
	/* These three space() methods are used to determine necessary spacing
	 * upon printing.
	 * 
	 * 
	 */
	public String space(String s, int x) {
		String tempString = "";
		for(int i = 0; i < x - ((String) s).length(); i ++) {
			tempString += " ";
		}
		return tempString;
	}
	public String space(int s, int x) {
		int length = String.valueOf(s).length();
		String tempString = "";
		for(int i = 0; i < x - length; i ++) {
			tempString += " ";
		}
		return tempString;
	}
	public String space(Double s, int x) {
		int length = String.valueOf(s).length();
		String tempString = "";
		for(int i = 0; i < x - length; i ++) {
			tempString += " ";
		}
		return tempString;
	}
	public int getDonorSize() {
		return donor.size();
	}
	public int getRecipientsSize() {
		return recipients.size();
	}
	public ArrayList<Patient> getDonor() {
		return donor;
	}
	public ArrayList<Patient> getRecipients() {
		return recipients;
	}
	
	/* This method is used to sort the donor list in a variety of ways by
	 * calling the appropriate method in TransplantDriver which utilizes
	 * the appropriate class to sort accordingly.
	 * 
	 */
	public void sortDonors(String input) {
		TransplantDriver TDObj = new TransplantDriver();
		if(input.equals("i")) {
			printAllDonors();
		}
		else if(input.equals("n")) {
			ArrayList<Patient> tempList = TDObj.sortByNumberConnections(donor);
			printListDonors(tempList);
		}
		else if(input.equals("b")) {
			ArrayList<Patient> tempList = TDObj.sortByBloodType(donor);
			printListDonors(tempList);
		}
		else if(input.equals("o")) {
			ArrayList<Patient> tempList = TDObj.sortByOrgan(donor);
			printListDonors(tempList);
		}
	}
	/* This method is used to sort the recipient list in a variety of ways by
	 * calling the appropriate method in TransplantDriver which utilizes
	 * the appropriate class to sort accordingly.
	 * 
	 */
	public void sortRecipients(String input) {
		TransplantDriver TDObj = new TransplantDriver();
		if(input.equals("i")) {
			printAllRecipients();
		}
		else if(input.equals("n")) {
			ArrayList<Patient> tempList = TDObj.sortByNumberConnections(recipients);
			printListRecipients(tempList);
		}
		else if(input.equals("b")) {
			ArrayList<Patient> tempList = TDObj.sortByBloodType(recipients);
			printListRecipients(tempList);
		}
		else if(input.equals("o")) {
			ArrayList<Patient> tempList = TDObj.sortByOrgan(recipients);
			printListRecipients(tempList);
		}
	}
}
