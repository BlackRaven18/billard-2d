package com.billard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import utils.LineEquation;
import utils.LinesManager;

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

    public void drawAndRotateStick(ShapeRenderer renderer, Body body){

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);

        //TODO: revise points
        renderer.line(getBodyXInPixels(body), getBodyYInPixels(body),  getBodyXInPixels(body) - (getMouseX() - getBodyXInPixels(body)),
                WORLD_PIXEL_HEIGHT - getMouseY() - 2 * (WORLD_PIXEL_HEIGHT - Gdx.input.getY() - getBodyYInPixels(body)));

        renderer.end();

        //TODO: TEST
        LineEquation line1 = new LineEquation(0, 324);
        LineEquation line2 = LinesManager.getEquationOfLine(new Vector2(320, 324), new Vector2(getMouseX(), WORLD_PIXEL_HEIGHT - getMouseY()));


        angle = LinesManager.getAngleBetweenTwoLines(line1.getA(), line2.getA());


        // II quater
        if(getMouseX() < getBodyXInPixels(body)){

            if(WORLD_PIXEL_HEIGHT - getMouseY() > getBodyYInPixels(body)){
                angle = -90 - (90 - angle);
            }else {
                angle = -180 + angle;
            }
        }

        angle = 180 + (-angle);

        //adjustment

        angle -= angleAdjustment;
        billardStickSprite.setRotation(angle);
    }

    public void drawStick(SpriteBatch batch){
        billardStickSprite.draw(batch);
    }
}


