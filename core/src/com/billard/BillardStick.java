package com.billard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import utils.GeometryUtil;
import utils.LineEquation;

import static com.billard.Application.WORLD_VIRTUAL_PIXEL_HEIGHT;
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

        // current angle
        float prevAngle;
        prevAngle = angle;

//        renderer.begin(ShapeRenderer.ShapeType.Line);
//        renderer.setColor(Color.WHITE);
//
//        //TODO: revise points
//        renderer.line(getBodyXInPixels(body), getBodyYInPixels(body),  getBodyXInPixels(body) - (getMouseX() - getBodyXInPixels(body)),
//                getMouseY() - 2 * (WORLD_VIRTUAL_PIXEL_HEIGHT - Gdx.input.getY() - getBodyYInPixels(body)));
//
//        renderer.end();
//
        // horizontal line crossing center of the white ball
        LineEquation line1 = new LineEquation(0, getBodyYInPixels(body));

        // line from center of the white ball to mouse position
        LineEquation line2 = GeometryUtil.getEquationOfLine(new Vector2(getBodyXInPixels(body), getBodyYInPixels(body)),
                new Vector2(getMouseX(), getMouseY()));


        angle = GeometryUtil.getAngleBetweenTwoLines(line1.getA(), line2.getA());


        //quaters adjustment
        if(getMouseX() <= getBodyXInPixels(body)){

            if(getMouseY() > getBodyYInPixels(body)){
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
            if(getMouseY() > getBodyYInPixels(body)){
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


        //lerp
        //a + (b - a) * lerp;
        if(angle + prevAngle + angleAdjustment * 2 < 360 + 180) {
            angle = prevAngle + (angle - prevAngle) * 0.2f;
        }

        billardStickSprite.setRotation(angle);
    }

    public void hitBall(Body ball){
        float forceX, forceY;
        float power = 1.19f;

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            forceX = getMouseX() - getBodyXInPixels(ball);
            forceY = getMouseY() - getBodyYInPixels(ball);

//            Vector2 impulse = new Vector2(50, 50);
//            Vector2 point = new Vector2(getBodyXInUnits(ball), getBodyYInUnits(ball));
//            ball.applyLinearImpulse(impulse, point, false);
            ball.applyForceToCenter(forceX * power, forceY * power, false);
        }

    }

    public void drawStick(SpriteBatch batch, Body ball){
        billardStickSprite.setPosition(getBodyXInUnits(ball), getBodyYInUnits(ball));
        billardStickSprite.draw(batch);
    }

    public void calculateDistance(Body ball){
//        double length;
//        length = Math.sqrt(Math.pow(getMouseX() - getBodyXInPixels(ball), 2) + Math.pow(getMouseY() - Application.WORLD_VIRTUAL_PIXEL_HEIGHT - getBodyYInPixels(ball), 2));
//
//        System.out.println("MOUSE X = " + getMouseX());
//        System.out.println("MOUSE Y = " + getMouseY());
//
//        System.out.println("BALL X = " + getBodyXInPixels(ball));
//        System.out.println("BALL Y = " + (Application.WORLD_VIRTUAL_PIXEL_HEIGHT - getBodyYInPixels(ball)));
//
//
//        System.out.println("LENGTH = " + length);

//        System.out.println("BODY X = " + getBodyXInPixels(ball));
//        System.out.println("BODY Y = " + getBodyYInPixels(ball));
//
//        System.out.println("MOUSE X = " + getMouseX());
//        System.out.println("MOUSE Y = " + getMouseY());
//        System.out.println("CONV MOUSE Y = " + (getMouseY()));
//        System.out.println("SCALE = " + Application.scale);
    }


}


