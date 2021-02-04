package edu.gwu.cs.ai.csp;

public class DomainValue {

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
}
