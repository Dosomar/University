package Objectos;

import java.io.Serializable;
import java.util.Comparator;

public class CompareObjects implements Serializable, Comparator<AbstractObject> {

	private static final long serialVersionUID = 1L;
	
	public int compare(AbstractObject a, AbstractObject b) {

		if (a.getLevel() < b.getLevel()) {
			return 1;
		} else if (a.getLevel() == b.getLevel()) {
			return 0;
		} else {
			return -1;
		}

	}
}
