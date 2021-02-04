package edu.gwu.cs.ai.csp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class Variable {

    private String name;

    private String value;

    private Set<DomainValue> possibleValues = new TreeSet<>();

    private Integer index;

    public Variable(String nameArg) {
        this.name = nameArg;
    }

    public Variable(int indexArg) {
        this.name = String.valueOf(indexArg);
        this.index = indexArg;
    }

    public Variable(String nameArg, int indexArg) {
        this.name = nameArg;
        this.index = indexArg;
    }

    public Variable(String name, Collection<DomainValue> valuesArg) {
        this.name = name;
        this.possibleValues.addAll(valuesArg);
    }

    public Variable(String name, DomainValue[] valuesArg) {
        this.name = name;
        this.possibleValues.addAll(Arrays.asList(valuesArg));
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Collection<DomainValue> getPossibleValues() {
        return possibleValues;
    }

    public void removePossibleValues(DomainValue dValArg) {
        possibleValues.remove(dValArg);
    }

    public Integer getIndex() {
        return this.index;
    }
}
