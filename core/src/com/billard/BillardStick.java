package com.billard;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import utils.GeometryUtil;
import utils.LineEquation;

import static com.billard.Application.WORLD_PIXEL_HEIGHT;
import static utils.B2DConstants.PPM;
import static utils.B2DObjectUtil.*;
import static utils.Utils.getMouseX;
import static utils.Utils.getMouseY;

public class BillardStick {

    private Sprite billardStickSprite;
    private float angle;
    private final float angleAdjustment = 7;

    public BillardStick(Body ball){
        loadAndInitiateStick(ball);

    }

    private void loadAndInitiateStick(Body ball){
        angle = angleAdjustment;
        billardStickSprite = MyAssetManager.getInstance().getBillardStickSprite();
        billardStickSprite.setOrigin(0,0);
        billardStickSprite.setRotation(angle);
        billardStickSprite.setPosition(getBodyXInUnits(ball), getBodyYInUnits(ball));
        billardStickSprite.setSize(billardStickSprite.getWidth() / PPM, billardStickSprite.getHeight() / PPM);
    }

    public void rotateStick(ShapeRenderer renderer, Body body) {


        float prevAngle;
        prevAngle = angle; // current angle

//        renderer.begin(ShapeRenderer.ShapeType.Line);
//        renderer.setColor(Color.WHITE);
//
//        //TODO: revise points
//        renderer.line(getBodyXInPixels(body), getBodyYInPixels(body),  getBodyXInPixels(body) - (getMouseX() - getBodyXInPixels(body)),
//                WORLD_PIXEL_HEIGHT - getMouseY() - 2 * (WORLD_PIXEL_HEIGHT - Gdx.input.getY() - getBodyYInPixels(body)));
//
//        renderer.end();
//
        // horizontal line crossing center of the white ball
        LineEquation line1 = new LineEquation(0, getBodyYInPixels(body));

        // line from center of the white ball to mouse position
        LineEquation line2 = GeometryUtil.getEquationOfLine(new Vector2(320, 324), new Vector2(getMouseX(), WORLD_PIXEL_HEIGHT - getMouseY()));



        angle = GeometryUtil.getAngleBetweenTwoLines(line1.getA(), line2.getA());


        //quaters adjustment
        if(getMouseX() <= getBodyXInPixels(body)){

            if(WORLD_PIXEL_HEIGHT - getMouseY() > getBodyYInPixels(body)){
                //II quater
                angle = 90 + (90 - angle);

                // special case
                if(angle == 270){
                    angle = 90;
                }

            }else {
                // III quater
                angle = 180 - angle;
            }
        }else {
            if(WORLD_PIXEL_HEIGHT - getMouseY() > getBodyYInPixels(body)){
                // I quater
                angle = -angle;

            }else {
                // IV quater
                angle = 270 + (90 - angle);

            }
        }

        //adjustment  caused by texture
        angle -= angleAdjustment;

        // rotating stick mirrored to coursor position
        angle += 180;

        System.out.println("angle = " + angle);


        //lerp
        //a + (b - a) * lerp;
        if(angle + prevAngle + angleAdjustment * 2 < 360 + 180) {
            angle = prevAngle + (angle - prevAngle) * 0.2f;
        }

        billardStickSprite.setRotation(angle);
    }

    public void drawStick(SpriteBatch batch){
        billardStickSprite.draw(batch);
    }
}


