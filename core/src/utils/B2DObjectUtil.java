package utils;

import com.badlogic.gdx.physics.box2d.Body;

public class B2DObjectUtil {

    public static float getCircleRadius(Body body){
        return body.getFixtureList().get(0).getShape().getRadius();
    }

    public static float getBodyXInUnits(Body body){
        return body.getPosition().x;
    }

    public static float getBodyYInUnits(Body body){
        return body.getPosition().y;
    }

    public static float getBodyXInPixels(Body body){
        return body.getPosition().x * B2DConstants.PPM;
    }

    public static float getBodyYInPixels(Body body){
        return body.getPosition().y * B2DConstants.PPM;
    }



}
