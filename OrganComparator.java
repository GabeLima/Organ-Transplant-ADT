// Gabriello Lima, 112803276, R01
import java.util.Comparator;

public class OrganComparator implements Comparator<Patient>{

	@Override
	public int compare(Patient o1, Patient o2) {
		String organ1 = o1.getOrgan().toLowerCase();
		String organ2 = o2.getOrgan().toLowerCase();
		return organ1.compareTo(organ2);
	}
	
}