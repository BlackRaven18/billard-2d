package utils;

import com.badlogic.gdx.physics.box2d.*;

import static utils.B2DConstants.PPM;

public class B2DBodyBuilder {

    public static Body createCircle(World world, float x, float y, float radius, boolean isStatic){
        Body pBody;
        BodyDef def = new BodyDef();


        def.type = isStatic? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        def.position.set(x, y);
        def.fixedRotation = true;
        def.linearDamping = 1f;
        pBody = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / 2);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
        pBody.createFixture(fixtureDef);
        shape.dispose();

        return pBody;
    }
}
