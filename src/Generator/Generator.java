package Generator;

import Objects.Element;
import java.util.Random;

public class Generator {

    Random rn = new Random();

    public Orientation chooseOrientation(int width, int height) {
        if(height > width)
            return Orientation.HORIZONTAL;
        else if(width > height)
            return Orientation.VERTICAL;
        else {
            if(rn.nextBoolean())
                return Orientation.HORIZONTAL;
            else return Orientation.VERTICAL;
        }

    }

    public void divide(Element[][] array, int x, int y, int width, int height) {

        if(width < 2 || height < 2)return;

        if(chooseOrientation(width, height) == Orientation.HORIZONTAL) {

            //splitting section
            int splitAt = rn.nextInt((height - 1) + 1);
            for(int i = 0; i<width; i++) {
                array[x + i][y + splitAt].southWall = true;
            }

            //placing an exit
            array[x + rn.nextInt(width)][y + splitAt].southWall = false;

            //recursion
            divide(array, x, y + 1 + splitAt, width, height - splitAt - 1);
            divide(array, x, y, width, splitAt);

        } else {

            //splitting section
            int splitAt = rn.nextInt((width - 1) + 1);
            for(int i = 0; i<height; i++) {
                array[x + splitAt][y + i].westWall = true;
            }

            //placing an exit
            array[x + splitAt][y + rn.nextInt(height)].westWall = false;

            //recursion
            divide(array, x + 1 + splitAt, y, width - splitAt - 1, height);
            divide(array, x, y, splitAt, height);
        }
    }

}
