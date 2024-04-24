package edu.gwu.cs.ai.csp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * Models a variable in the constraint satisfaction problem.
 * Variable has a name, an index and a set of possible values (The domain values).
 * The default value is null.
 * 
 * @author Amrinder Arora
 */
public class Variable implements Cloneable {

    private String name;

    private String value;

    private Set<DomainValue> possibleValues = new TreeSet<>();

    private Integer index;

    /** 
     * Stores which values have been checked, and which have not.
     * This is helpful for backtracking algorithms.
     */
    private Set<DomainValue> checkedValues = new TreeSet<>();
    
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

    public Variable(int indexArg, String name, Collection<DomainValue> valuesArg) {
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

    public Set<DomainValue> getCheckedValues() {
		return checkedValues;
	}

	public void setCheckedValues(Set<DomainValue> checkedValues) {
		this.checkedValues = checkedValues;
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
    
    public Variable clone() throws CloneNotSupportedException {
    	Variable var2 = (Variable) super.clone();
    	var2.possibleValues =  new TreeSet<>();
    	for (DomainValue d: this.possibleValues) {
    		var2.possibleValues.add(new DomainValue(d.getValue()));
    	}
    	return var2;
    }
}
