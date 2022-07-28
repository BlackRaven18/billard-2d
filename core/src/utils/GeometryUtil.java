package utils;

import com.badlogic.gdx.math.Vector2;

public final class GeometryUtil {
    public GeometryUtil(){}

    public static LineEquation getEquationOfLine(Vector2 point1, Vector2 point2){
        float a = (point1.y - point2.y) / (point1.x - point2.x);
        float b = point1.y - (a * point1.x);

        return new LineEquation(a, b);
    }

    public static  float getAngleBetweenTwoLines(float a1, float a2){

        float deg, formula;

        formula = (a1 - a2) / (1 + a1 * a2);
        deg = (float)Math.toDegrees(Math.atan(formula));

        if(Float.isNaN(deg)){
            deg = -90;
        }

        return deg;
    }

    public static float getVectorLength(Vector2 v1, Vector2 v2){
        return (float)Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2));

    }
}
