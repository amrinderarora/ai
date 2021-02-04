package edu.gwu.cs.ai.csp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class Variable {

    private String name;

    private Set<DomainValue> values = new TreeSet<>();

    public Variable(String name) {
        this.name = name;
    }

    public Variable(int name) {
        this.name = String.valueOf(name);
    }

    public Variable(String name, Collection<DomainValue> valuesArg) {
        this.name = name;
        this.values.addAll(valuesArg);
    }

    public Variable(String name, DomainValue[] valuesArg) {
        this.name = name;
        this.values.addAll(Arrays.asList(valuesArg));
    }

    public String getName() {
        return name;
    }

    public Collection<DomainValue> getPossibleValues() {
        return values;
    }

    public void setName(String name) {
        this.name = name;
    }
}
