package Solver;

import java.awt.*;
import java.util.ArrayList;

public class Path {
    Solver m;
    public ArrayList<Point> points = new ArrayList<Point>();

    public Path(Solver m, Point p) {
        this.m = m;
        points.add(p);
    }
    public Path(Solver m, Point p, Path path) {
        this.m = m;
        for(int i = 0; i<path.points.size()-1; i++) {
            this.points.add(path.points.get(i));
        }
        this.points.add(p);
    }

    public void move() {
        Point p = points.get(points.size()-1);
        ArrayList<Point> moves = new ArrayList<Point>();
        try {
            if (!m.array[p.x + 1][p.y].tracked &&
                    m.array[p.x + 1][p.y].canEnter(p, new Point(p.x + 1, p.y)) &&
                    m.array[p.x][p.y].canExit(p, new Point(p.x + 1, p.y))) {
                moves.add(new Point(p.x + 1, p.y));
                m.array[p.x + 1][p.y].tracked = true;
            }
        } catch(Exception e) {}

        try {
            if (!m.array[p.x - 1][p.y].tracked &&
                    m.array[p.x - 1][p.y].canEnter(p, new Point(p.x - 1, p.y)) &&
                    m.array[p.x][p.y].canExit(p, new Point(p.x - 1, p.y))) {
                moves.add(new Point(p.x - 1, p.y));
                m.array[p.x - 1][p.y].tracked = true;
            }
        } catch(Exception e) {}

        try {
            if (!m.array[p.x][p.y + 1].tracked &&
                    m.array[p.x][p.y + 1].canEnter(p, new Point(p.x, p.y + 1)) &&
                    m.array[p.x][p.y].canExit(p, new Point(p.x, p.y + 1))) {
                moves.add(new Point(p.x, p.y + 1));
                m.array[p.x][p.y + 1].tracked = true;
            }
        } catch(Exception e) {}

        try {
            if (!m.array[p.x][p.y - 1].tracked &&
                    m.array[p.x][p.y - 1].canEnter(p, new Point(p.x, p.y - 1)) &&
                    m.array[p.x][p.y].canExit(p, new Point(p.x, p.y - 1))) {
                moves.add(new Point(p.x, p.y - 1));
                m.array[p.x][p.y - 1].tracked = true;
            }
        } catch(Exception e) {}

        if(moves.size() == 0) {
            m.activePaths.remove(this);
            m.completedPaths.add(this);
        } else {
            boolean found = false;
            for (Point alt : moves) {
                if(alt.x == m.b.x && alt.y == m.b.y) {
                    points.add(alt);
                    m.path = this;
                    m.activePaths.remove(this);
                    found = true;
                }
            }
            if(!found)
                points.add(moves.remove(0));
            for (Point alt : moves) {
                m.activePaths.add(new Path(m, alt, this));
            }
        }
    }

    public boolean foundB() {
        Point p = points.get(points.size()-1);
        if(p.x == m.b.x && p.y == m.b.y)
            return true;
        else return false;
    }

}
