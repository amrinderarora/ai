package edu.gwu.cs.ai.csp;

public class DomainValue implements Comparable<DomainValue>, Cloneable {

    private final String value;

    public DomainValue(String vArg) {
        this.value = vArg;
    }

    public DomainValue(int iVal) {
        this.value = String.valueOf(iVal);
    }

    public String getValue() {
        return value;
    }

	@Override
	public int compareTo(DomainValue other) {
		return this.value.compareTo(other.value);
	}
	
	@Override
	public DomainValue clone() throws CloneNotSupportedException {
		return (DomainValue) super.clone();
	}
}
