package utils;

import com.badlogic.gdx.Gdx;
import com.billard.Application;

import static com.billard.Application.WORLD_VIRTUAL_PIXEL_HEIGHT;
import static com.billard.Application.scaleY;

public class Utils {

    public static int getMouseX(){
        return (int)(Gdx.input.getX() * Application.scaleX);
    }

    public static int getMouseY(){
        return (int)(WORLD_VIRTUAL_PIXEL_HEIGHT * scaleY - Gdx.input.getY() * scaleY);
    }


}
