import java.util.Comparator;

public class BloodTypeComparator implements Comparator<Patient>{

	@Override
	public int compare(Patient o1, Patient o2) {
		BloodType p1 = o1.getBloodType();
		BloodType p2 = o2.getBloodType();
		return p1.getBloodType().compareTo(p2.getBloodType());
	}

}
