package Objects;

import java.awt.*;

public class Element {
    public boolean northWall;
    public boolean eastWall;
    public boolean southWall;
    public boolean westWall;
    public boolean tracked = false;

    public void lowerAll() {
        northWall = false;
        eastWall = false;
        southWall = false;
        westWall = false;
    }

    public void raiseAll() {
        northWall = true;
        eastWall = true;
        southWall = true;
        westWall = true;
    }

    public void paint(Graphics g, int x, int y, int size) {
        if(northWall)
            g.drawLine(x*size, y*size, x*size + size, y*size);
        if(eastWall)
            g.drawLine((x*size) + size, (y*size), (x*size) + size, (y*size) + size);
        if(southWall)
            g.drawLine(x*size, y*size+size, x*size + size, y*size+size);
        if(westWall)
            g.drawLine(x*size, y*size, x*size, y*size + size);
    }

    public boolean canEnter(Point pA, Point pB) {
        int xDiff = pB.x - pA.x;
        int yDiff = pB.y - pA.y;

        if(xDiff == 1 && !westWall) return true;//left
        else if(xDiff == -1 && !eastWall) return true;//right
        else if(yDiff == -1 && !southWall) return true;//down
        else if(yDiff == 1 && !northWall) return true;//up
        else return false;
    }

    public boolean canExit(Point pA, Point pB) {
        int xDiff = pB.x - pA.x;
        int yDiff = pB.y - pA.y;

        if(xDiff == 1 && !eastWall) return true;//left
        else if(xDiff == -1 && !westWall) return true;//right
        else if(yDiff == -1 && !northWall) return true;//down
        else if(yDiff == 1 && !southWall) return true;//up
        else return false;
    }

}
