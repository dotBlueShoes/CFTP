package cftp.utility;

public class Shapes {

    public static Boolean isSphere (int x, int y, int z, int radius) {
        return Math.pow(x - radius, 2) + Math.pow(y - radius, 2) + Math.pow(z - radius, 2) <= Math.pow(radius, 2);
    }

}
