package main.firefighters.AssignmentOptimizer;

import java.util.Iterator;


public class AssignmentIterator implements Iterator<FirefighterAssignment> {

    private final AssignmentOptimizer assignmentOptimizer;

    public AssignmentIterator(AssignmentOptimizer assignmentOptimizer) {
        this.assignmentOptimizer = assignmentOptimizer;
    }


    @Override
    public boolean hasNext() {
        return assignmentOptimizer.fireCanBeExtinguished();
    }

    @Override
    public FirefighterAssignment next() {
        return assignmentOptimizer.findOptimalAssignment();
    }
}
