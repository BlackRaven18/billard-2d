package com.billard;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public final class MyAssetManager {

    private static MyAssetManager instance;

    public final AssetManager manager = new AssetManager();
    private final TextureAtlas textureAtlas;

    private  MyAssetManager(){
        loadTextures();
        textureAtlas = manager.get("Images/spritesheet.txt");

    }

    public static MyAssetManager getInstance(){
        if(instance == null){
            instance = new MyAssetManager();
        }

        return instance;
    }

    public void loadTextures(){
        manager.load("Images/spritesheet.txt", TextureAtlas.class);
        manager.finishLoading();
    }

    public Sprite getBillardStickSprite(){
        return textureAtlas.createSprite("billard-stick-2.png");
    }




}
