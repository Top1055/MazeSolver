import Generator.Generator;
import Objects.Maze;
import Solver.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Panel extends JPanel implements ActionListener{

    public static final int GRID_WIDTH = 100;
    public static final int GRID_HEIGHT = 100;
    public static final int ELEMENT_SIZE = 10;
    public static final int SPEED = 1;
    public static final boolean REPEAT = false;

    Maze maze;
    Point start = new Point(0, 0);
    Point end = new Point(GRID_WIDTH-1, GRID_HEIGHT-1);
    Solver solver = null;
    ArrayList<Timer> timers = new ArrayList<>();
    int timersCount = 5;

    public Panel() {
        setPreferredSize(new Dimension(GRID_WIDTH*ELEMENT_SIZE, GRID_HEIGHT*ELEMENT_SIZE));
        maze = new Maze(0, GRID_WIDTH, GRID_HEIGHT);
        new Generator().divide(maze.grid, 0, 0, GRID_WIDTH, GRID_HEIGHT);
        repaint();
        setFocusable(true);
        for(int i = 0; i<timersCount; i++)
            timers.add(new Timer(SPEED, this));
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.VK_PRINTSCREEN)
                    screenShot();
                if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    maze.reset();
                    solver = new Solver(maze.grid, start, end);
                    for(Timer t: timers)
                        t.start();
                }
                else if(keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    for(Timer t: timers)
                        t.stop();
                    solver = null;
                }
                else if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                    maze = new Maze(0, GRID_WIDTH, GRID_HEIGHT);
                    new Generator().divide(maze.grid, 0, 0, GRID_WIDTH, GRID_HEIGHT);
                }

                repaint();
            }
        });
    }

    @Override public void paint(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.fillRect(-10, -10, GRID_WIDTH*ELEMENT_SIZE+100, GRID_HEIGHT*ELEMENT_SIZE+100);
        if(start != null) {
            g.setColor(new Color(0, 255, 0));
            g.fillRect(start.x * ELEMENT_SIZE, start.y * ELEMENT_SIZE, ELEMENT_SIZE, ELEMENT_SIZE);
        }
        if(end != null) {
            g.setColor(new Color(255, 0, 0));
            g.fillRect(end.x * ELEMENT_SIZE, end.y * ELEMENT_SIZE, ELEMENT_SIZE, ELEMENT_SIZE);
        }
        if(solver != null) {
            g.setColor(new Color(0, 0, 255));
            for(Path path: solver.activePaths)
                for(Point p: path.points)
                    g.fillRect(p.x*ELEMENT_SIZE, p.y*ELEMENT_SIZE, ELEMENT_SIZE, ELEMENT_SIZE);
            g.setColor(new Color(255, 100, 0));

            for(Path path: solver.completedPaths)
                for(Point p: path.points)
                    g.fillRect(p.x*ELEMENT_SIZE, p.y*ELEMENT_SIZE, ELEMENT_SIZE, ELEMENT_SIZE);
            g.setColor(new Color(0, 255, 0));

            if(solver.path != null)
                for(Point p: solver.path.points)
                    g.fillRect(p.x*ELEMENT_SIZE, p.y*ELEMENT_SIZE, ELEMENT_SIZE, ELEMENT_SIZE);
        }

        g.setColor(new Color(0));
        for(int x = 0; x< GRID_WIDTH; x++)
            for(int y = 0; y<GRID_HEIGHT; y++) {
                maze.grid[x][y].paint(g, x, y, ELEMENT_SIZE);
            }

    }

    public void screenShot() {
        setSize(getPreferredSize());
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        printAll(g);
        g.dispose();
        try {
            ImageIO.write(image, "png", new File("screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(solver.path == null)
            solver.itterate();
        if (solver.path != null) {
            if(REPEAT) {
                maze = new Maze(0, GRID_WIDTH, GRID_HEIGHT);
                new Generator().divide(maze.grid, 0, 0, GRID_WIDTH, GRID_HEIGHT);
                solver = new Solver(maze.grid, start, end);
            } else {
                for (Timer t : timers)
                    t.stop();
                solver.activePaths = new ArrayList<>();
                solver.completedPaths = new ArrayList<>();
            }
        }
        repaint();
    }
}
