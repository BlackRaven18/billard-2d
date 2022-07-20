package utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TiledObjectUtil {
    public static void parseTiledObjectLayer(World world, MapObjects objects){
        for(MapObject object : objects){
            Shape shape;

            System.out.println(object.toString());

            if(object instanceof PolylineMapObject){
                System.out.println("tutaj");
                shape = createPolyLine((PolylineMapObject) object);
            } else{
                continue;
            }

            Body body;
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }

    }

    private static ChainShape createPolyLine(PolylineMapObject polyline){
        float[] verticies = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVerticies = new Vector2[verticies.length / 2];

        for(int i = 0; i < worldVerticies.length; i++){
            worldVerticies[i] = new Vector2(verticies[i * 2] / B2DConstants.PPM, verticies[i * 2 + 1] / B2DConstants.PPM);
        }


        ChainShape cs = new ChainShape();
        cs.createChain(worldVerticies);
        return cs;
    }
}
