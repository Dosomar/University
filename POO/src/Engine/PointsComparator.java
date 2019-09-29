package Engine;


import java.util.Comparator;

public class PointsComparator implements Comparator<Points> {

	public int compare(Points a, Points b) {

		if (a.getPontos() > b.getPontos()) {
			return 1;
		} else if (a.getPontos() == b.getPontos()) {
			return 0;
		} else {
			return -1;
		}

	}
}
