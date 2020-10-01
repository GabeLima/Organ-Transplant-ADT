// Gabriello Lima, 112803276, R01
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

public class TransplantDriver{
	public static final String DONOR_FILE = "donors.txt";
	public static final String RECIPIENT_FILE = "recipients.txt";
	/* This method sorts a given list by the number of connections it has
	 * in an adjacency matrix. Works in tangent with NumConnectionsComparator 
	 * Class
	 * 
	 * 
	 */
	public ArrayList<Patient> sortByNumberConnections(ArrayList<Patient> tempList) {
		ArrayList<Patient> list = new ArrayList<Patient>();
		for(int i = 0 ; i < tempList.size(); i ++) {
			list.add(tempList.get(i));
		}
		NumConnectionsComparator NCCObj = new NumConnectionsComparator();
		
		for(int j = 0; j <list.size(); j ++) {
			for(int i = j + 1; i <list.size(); i ++) {
				if(NCCObj.compare(list.get(j), list.get(i)) > 0) {
					Patient tempPatient = list.get(i);
					list.set(i, list.get(j));
					list.set(j, tempPatient);
				}
			}
		}
		return list;
	}
	/* This method sorts a given list by the BloodType it has
	 * in an adjacency matrix. Works in tangent with BloodTypeComparator 
	 * Class
	 * 
	 * 
	 */
	public ArrayList<Patient> sortByBloodType(ArrayList<Patient> tempList) {
		ArrayList<Patient> list = new ArrayList<Patient>();
		for(int i = 0 ; i < tempList.size(); i ++) {
			list.add(tempList.get(i));
		}
		BloodTypeComparator BTObj = new BloodTypeComparator();
		for(int j = 0; j <list.size(); j ++) {
			for(int i = j + 1; i <list.size(); i ++) {
				if(BTObj.compare(list.get(j), list.get(i)) > 0) {
					Patient tempPatient = list.get(i);
					list.set(i, list.get(j));
					list.set(j, tempPatient);
				}
			}
		}
		return list;
	}
	/* This method sorts a given list by the Organ it has
	 * in an adjacency matrix. Works in tangent with OrganComparator 
	 * Class
	 * 
	 * 
	 */
	public ArrayList<Patient> sortByOrgan(ArrayList<Patient> tempList) {
		ArrayList<Patient> list = new ArrayList<Patient>();
		for(int i = 0 ; i < tempList.size(); i ++) {
			list.add(tempList.get(i));
		}
		OrganComparator OObj = new OrganComparator();
		for(int j = 0; j <list.size(); j ++) {
			for(int i = j + 1; i <list.size(); i ++) {
				if(OObj.compare(list.get(j), list.get(i)) > 0) {
					Patient tempPatient = list.get(i);
					list.set(i, list.get(j));
					list.set(j, tempPatient);
				}
			}
		}
		return list;
	}
	
	/*
	 * It is important to note that each exception is handled with an according
	 * print statement detailing the error which occured, hence all exceptions
	 * merely print said message.
	 * 
	 * 
	 * 
	 * 
	 */
	public static void main (String[] args) {
		Scanner stdin = new Scanner(System.in);
		boolean quitFlag = false;
		TransplantGraph TGObj = new TransplantGraph();
		try {
	        FileInputStream file = new FileInputStream("data.obj");
	        ObjectInputStream fin  = new ObjectInputStream(file);        
	        TGObj = (TransplantGraph) fin.readObject();
	        fin.close();    
	        System.out.println("Loading data from transplant.obj... ");
	    } catch(IOException e) {
	    	System.out.println("Loading in new files");
			TGObj = new TransplantGraph(DONOR_FILE, RECIPIENT_FILE);
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		while(!quitFlag) {
			System.out.println("Menu:\r\n" + 
					"    (LR) - List all recipients\r\n" + 
					"    (LO) - List all donors\r\n" + 
					"    (AO) - Add new donor\r\n" + 
					"    (AR) - Add new recipient\r\n" + 
					"    (RO) - Remove donor\r\n" + 
					"    (RR) - Remove recipient\r\n" + 
					"    (SR) - Sort recipients\r\n" + 
					"    (SO) - Sort donors\r\n" + 
					"    (Q) - Quit");
			System.out.print("Enter option: ");
			try {
				String selection = stdin.next().toLowerCase();
				switch(selection) {
					case("lr"):
						TGObj.printAllRecipients();
						break;
					case("lo"):
						TGObj.printAllDonors();
						break;
					case("ao"):
						System.out.print("Please enter the organ donor name: ");
						String name = stdin.next() + stdin.nextLine();
						System.out.print("Please enter the organs " + name + " is donating: ");
						String organs = stdin.next() + stdin.nextLine();
						System.out.print("Please enter the blood type of " + name + ": ");
						String bt = stdin.next() + stdin.nextLine();
						System.out.print("Please enter the age of " + name + ": ");
						int age = stdin.nextInt();
						TGObj.addDonor(new Patient(TGObj.getDonorSize(), name, age, organs, bt, true));
						System.out.println("The organ donor with ID " + (TGObj.getDonor().get(TGObj.getDonorSize() - 1).getID()) +  " was successfully added to the donor list!");
						break;
					case("ar"):
						System.out.print("Please enter the new recipient's name: ");
						name = stdin.next() + stdin.nextLine();
						System.out.print("Please enter the recipient's blood type: ");
						bt = stdin.next() + stdin.nextLine();
						System.out.print("Please enter the recipients age: ");
						age = stdin.nextInt();
						System.out.print("Please enter the organ needed: ");
						organs = stdin.next() + stdin.nextLine();
						TGObj.addRecipient(new Patient(TGObj.getRecipientsSize(), name, age, organs, bt, false));
						System.out.println(name + " is now on the organ transplant waitlist!");
						break;
					case("ro"):
						System.out.print("Please enter the name of the organ donor to remove: ");
						name = stdin.next() + stdin.nextLine();
						TGObj.removeDonor(name);
						break;
					case("rr"):
						System.out.print("Please enter the name of the recipient to remove: ");
						name = stdin.next() + stdin.nextLine();
						TGObj.removeRecipient(name);
						break;
					case("sr"):
						String option = "";
						while(!option.contains("q")) {
							System.out.println("\t(I) Sort by ID\n\t" + 
									"(N) Sort by Number of Donors\n\t" + 
									"(B) Sort by Blood Type \n\t" + 
									"(O) Sort by Organ Needed\n\t" + 
									"(Q) Back to Main Menu\n\t");
							System.out.print("Please enter an option: ");
							option = (stdin.next() + stdin.nextLine()).toLowerCase().replaceAll(" ", "");
							TGObj.sortRecipients(option);
						}
						System.out.println("Returning to main menu.");
						break;
					case("so"):
						option = "";
						while(!option.contains("q")) {
							System.out.println("\t(I) Sort by ID\n\t" + 
								"(N) Sort by Number of Recipients\n\t" + 
								"(B) Sort by Blood Type \n\t" + 
								"(O) Sort by Organ Donated\n\t" + 
								"(Q) Back to Main Menu\n\t");
							System.out.print("Please enter an option: ");
							option = (stdin.next() + stdin.nextLine()).toLowerCase().replaceAll(" ", "");
							TGObj.sortDonors(option);
						}
						System.out.println("Returning to main menu.");
						break;					
					case("q"):	
						quitFlag = true;
						try {
							FileOutputStream file = new FileOutputStream("data.obj");
						    ObjectOutputStream fout = new ObjectOutputStream(file);
						    fout.writeObject(TGObj);
						    fout.close();
						    System.out.println("Writing data to transplant.obj...");
						}catch(IOException ex) {
							System.out.println("Could not serialize TransplantGraph");
						}
						break;
					}
				}catch(IOException e) {
					System.out.println(e.getMessage());
				} catch (ParseException e) {
					System.out.println(e.getMessage());
				}catch(NumberFormatException e){
			        System.out.println("Please provide correct input");
					stdin.next();
				}catch (InputMismatchException e) {
					System.out.println("Please provide correct input");
					stdin.next();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
		}
		stdin.close();
	}
}