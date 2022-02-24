package Solver;

import Objects.Element;

import java.awt.*;
import java.util.ArrayList;

public class Solver {
    public ArrayList<Path> activePaths = new ArrayList<Path>();
    public ArrayList<Path> completedPaths = new ArrayList<Path>();
    public Path path = null;


    Point a, b;
    Element[][] array;

    public Solver(Element[][] bools, Point a, Point b) {
        this.a = a;
        this.b = b;
        this.array = bools;
        array[0][0].tracked = true;
        activePaths.add(new Path(this, a));
    }

    public void itterate() {
        for(int i = 0; i<activePaths.size(); i++) {
            Path p = activePaths.get(i);
            p.move();
        }
        for(int i = 0; i<activePaths.size(); i++) {
            Path p = activePaths.get(i);
            if (p.foundB())
                path = p;
        }
        for(int i = 0; i<completedPaths.size(); i++) {
            Path p = completedPaths.get(i);
            if(p.foundB())
                path = p;
            else completedPaths.remove(p);
        }
    }

}
