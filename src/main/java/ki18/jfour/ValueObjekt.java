package ki18.jfour;

public class ValueObjekt {

	private double fatherTotal = 0;

	private double value = 0;

	private int total = 0;

	private double wins = 0;

	@Override
	public String toString() {
		return "ValueObjekt [fatherTotal=" + fatherTotal + ", value=" + value + ", total=" + total + ", wins=" + wins
				+ ", expansionTerm=" + expansionTerm + ", winavg=" + winavg + "]";
	}

	private double expansionTerm = 0;

	private double winavg = 0;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getWins() {
		return wins;
	}

	public void setWins(double wins) {
		this.wins = wins;
	}

	public double getExpansionTerm() {
		return expansionTerm;
	}

	public void setExpansionTerm(double expansionTerm) {
		this.expansionTerm = expansionTerm;
	}

	public double getWinavg() {
		return winavg;
	}

	public void setWinavg(double winavg) {
		this.winavg = winavg;
	}

	public double getFatherTotal() {
		return fatherTotal;
	}

	public void setFatherTotal(double fatherTotal) {
		this.fatherTotal = fatherTotal;
	}

}
