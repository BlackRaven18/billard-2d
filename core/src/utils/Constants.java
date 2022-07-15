package utils;

public class Constants {
    public static final float PPM = 80; // PPM = Pixel per Meter
    public static final float MPP = 1 / PPM; // MPP = Meter per Pixel

    public static final int WORLD_PIXEL_WIDTH = 1280;
    public static final int WORLD_PIXEL_HEIGHT = 720;
    public static final float WORLD_WIDTH = WORLD_PIXEL_WIDTH / PPM; //in meter
    public static final float WORLD_HEIGHT = WORLD_PIXEL_HEIGHT / PPM; //in meter
}
