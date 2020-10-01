// Gabriello Lima, 112803276, R01
import java.util.Comparator;

public class NumConnectionsComparator implements Comparator<Patient>{

	@Override
	public int compare(Patient o1, Patient o2) {
		int num1 = o1.getConnections();
		int num2 = o2.getConnections();
		return num1-num2;
	}
	
}