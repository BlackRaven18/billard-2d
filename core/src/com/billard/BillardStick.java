package com.billard;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

import static utils.B2DConstants.PPM;
import static utils.B2DObjectUtil.getBodyXInUnits;
import static utils.B2DObjectUtil.getBodyYInUnits;

public class BillardStick {

    private Sprite billardStickSprite;

    public BillardStick(Body ball){
        loadAndInitiateStick(ball);

    }

    private void loadAndInitiateStick(Body ball){
        billardStickSprite = MyAssetManager.getInstance().getBillardStickSprite();
//        billardStickSprite.setOrigin(0,0);
//        billardStickSprite.setRotation(0);
        billardStickSprite.setPosition(getBodyXInUnits(ball), getBodyYInUnits(ball));
        billardStickSprite.setSize(billardStickSprite.getWidth() / PPM, billardStickSprite.getHeight() / PPM);
    }
}


