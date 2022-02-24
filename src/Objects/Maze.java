package Objects;

public class Maze {
    public Element[][] grid;
    int width;
    int height;

    public Maze(int initMode, int width, int height) {
        this.width = width;
        this.height = height;
        switch(initMode) {
            case 0:
                empty();
            break;

            case 1:
                full();
            break;
        }
    }

    private void empty() {
        grid = new Element[width][height];
        for(int x = 0; x<width; x++)
            for(int y = 0; y<height; y++) {
                grid[x][y] = new Element();
                grid[x][y].lowerAll();
            }
    }
    private void full() {
        grid = new Element[width][height];
        for(int x = 0; x<width; x++)
            for(int y = 0; y<height; y++) {
                grid[x][y] = new Element();
                grid[x][y].raiseAll();
            }
    }

    public void reset() {
        for(int x = 0; x<width; x++)
            for(int y = 0; y<height; y++)
                grid[x][y].tracked = false;
    }

}
