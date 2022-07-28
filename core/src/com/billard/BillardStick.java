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
import static utils.Utils.*;

public class BillardStick {

    private Sprite billardStickSprite;
    private float angle;
    private final float angleAdjustment = 7;
    private final float  linearBasicUnit = 1;

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

    public void manageBillardStick(Body ball){
        rotateStick(ball);
        hitBall(ball);
    }

    private void rotateStick(Body ball) {

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
        LineEquation line1 = new LineEquation(0, getBodyYInPixels(ball));

        // line from center of the white ball to mouse position
        LineEquation line2 = GeometryUtil.getEquationOfLine(new Vector2(getBodyXInPixels(ball), getBodyYInPixels(ball)),
                new Vector2(getMouseX(), getMouseY()));


        angle = GeometryUtil.getAngleBetweenTwoLines(line1.getA(), line2.getA());


        //quaters adjustment
        if(getMouseX() <= getBodyXInPixels(ball)){

            if(getMouseY() > getBodyYInPixels(ball)){
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
            if(getMouseY() > getBodyYInPixels(ball)){
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

    private void hitBall(Body ball){
        float directionXForce, directionYForce;
        float vecLenght;
        float normalizationValue;
//        float adjustment = 1.19f;
        float power = 100;


        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            // making the ball to move to specific direction but using only the 'power' variable to define how fast
            Vector2 ballPosition = new Vector2(getBodyXInPixels(ball), getBodyYInPixels(ball));
            vecLenght = GeometryUtil.getVectorLength(ballPosition, getMousePoint());

            // calculating the value which will help in normalization the forces to make the ball move only by the specific unit
            // we want to only use 'power' parameter to say how fast tha ball should go
            normalizationValue = linearBasicUnit / vecLenght;

            directionXForce = (getMouseX() - getBodyXInPixels(ball)) * normalizationValue;
            directionYForce = (getMouseY() - getBodyYInPixels(ball)) * normalizationValue;

            ball.applyForceToCenter(directionXForce * power, directionYForce  *  power, false);
        }

    }

    public void drawStick(SpriteBatch batch, Body ball){
        billardStickSprite.setPosition(getBodyXInUnits(ball), getBodyYInUnits(ball));
        billardStickSprite.draw(batch);
    }



}


