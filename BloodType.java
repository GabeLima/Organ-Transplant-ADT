// Gabriello Lima, 112803276, R01
import java.io.Serializable;

public class BloodType implements Serializable{
	private String bloodType;
	
	public BloodType() {
		
	}
	public BloodType(String newBloodType) throws Exception {
		bloodType = newBloodType;
		//System.out.println("This is bloodtype:" + bloodType);
		checkType();
	}
	public String getBloodType() {
		return bloodType;
	}
	public static boolean isCompatible(BloodType recipient, BloodType donor) {
		//System.out.println("This is the bloodtype of recipient:" + recipient.getBloodType());
		//System.out.println("This is the bloodtype of donor:" + donor.getBloodType());
		if(recipient.getBloodType().equals("O")) {
			return donor.getBloodType().equals("O");
		}
		else if(recipient.getBloodType().equals("A")) {
			return donor.getBloodType().equals("O") || donor.getBloodType().equals("A");
		}
		else if(recipient.getBloodType().equals("B")) {
			return donor.getBloodType().equals("O") || donor.getBloodType().equals("B");
		}
		else if(recipient.getBloodType().equals("AB")) {
			return true;
		}
		return false;
	}
	public void checkType() throws Exception {
		if(bloodType.equals("O") || bloodType.equals("A") || bloodType.equals("B") || bloodType.equals("AB")) {
			
		}
		else
			throw new Exception("Invalid Bloodtype, please enter either \"O\" or \"A\" or \"B\" or \"AB\" ");
	}
	//public void setBloodType() {
	//	bloodType = bloodType;
	//}
}
