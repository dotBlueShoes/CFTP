package cftp.utility;

public class Shapes {

    public static Boolean isSphere (int x, int y, int z, int radius) {
        return Math.pow(x - radius, 2) + Math.pow(y - radius, 2) + Math.pow(z - radius, 2) <= Math.pow(radius, 2);
    }

    public static float sphereDistance (int x, int y, int z, int center) {
        float dx = x - center;
        float dy = y - center;
        float dz = z - center;

        return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

}
