package com.billard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import utils.B2DBodyBuilder;
import utils.B2DConstants;
import utils.TiledObjectUtil;

import static java.lang.Float.NaN;
import static utils.B2DConstants.PPM;
import static utils.B2DObjectUtil.*;
import static utils.Utils.*;

@SuppressWarnings("FieldCanBeLocal")

public class Application extends ApplicationAdapter {

	public static final String APP_TITLE = "Billard 2D";
	public static final float APP_VERSION = 0.1f;
	public static final int APP_FPS = 30;

	public static final int WORLD_PIXEL_WIDTH = 1280;
	public static final int WORLD_PIXEL_HEIGHT = 720;
	public static final int WORLD_VIRTUAL_PIXEL_WIDTH = 1280;
	public static final int WORLD_VIRTUAL_PIXEL_HEIGHT = 720;
	public static int scale = (WORLD_VIRTUAL_PIXEL_WIDTH * WORLD_VIRTUAL_PIXEL_HEIGHT) / (WORLD_PIXEL_WIDTH * WORLD_PIXEL_HEIGHT);
	public static final float WORLD_WIDTH = WORLD_PIXEL_WIDTH / PPM; //in meter
	public static final float WORLD_HEIGHT = WORLD_PIXEL_HEIGHT / PPM; //in meter

	private final boolean DEBUG = true;

	// camera
	private OrthographicCamera camera;

	// tiled map
	private OrthogonalTiledMapRenderer tmr;
	private TiledMap map;

	//debuger
	private Box2DDebugRenderer b2dr;

	//world
	private World world;

	//player
	private Body player;

	//batches
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	private Texture billardStick;
	private Sprite billardStickSprite;

	//managers
	private AssetManager assetManager;

	private float angle;

	@Override
	public void create () {

		//creating world
		world = new World(new Vector2(0, 0), false);

		//creating debug renderer
		b2dr = new Box2DDebugRenderer();

		// creating camera
		camera = new OrthographicCamera(Application.WORLD_WIDTH, Application.WORLD_HEIGHT);
		camera.setToOrtho(false, Application.WORLD_WIDTH, Application.WORLD_HEIGHT);
		camera.update();

		//creating player
		player = B2DBodyBuilder.createCircle(world, 16, 16.2f, 0.5f, false);

		createBills();

		//player.applyForceToCenter(1000 * 2, 0, false);

		// creating batch
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		//loading tiled map and creating tiled map renderer
		map = new TmxMapLoader().load("Maps/table.tmx");
		tmr = new OrthogonalTiledMapRenderer(map, B2DConstants.MPP);

		TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision-layer").getObjects());

		billardStick = new Texture("Images/billard-stick-2.png");

		billardStickSprite = new Sprite(billardStick);
		billardStickSprite.setOrigin(0,0);
		angle = 0;
		billardStickSprite.setRotation(angle);
		billardStickSprite.setPosition(getBodyXInUnits(player),
				getBodyYInUnits(player));
		billardStickSprite.setSize(billardStick.getWidth() / PPM, billardStick.getHeight() / PPM);



		assetManager = new AssetManager();


	}


	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		camera.update();

		tmr.setView(camera);
		tmr.render();


		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//batch.draw(billardStick, getBodyXInUnits(player), getBodyYInUnits(player), billardStick.getWidth() / PPM, billardStick.getHeight() / PPM);
		//batch.draw()
		billardStickSprite.draw(batch);

		batch.end();

		drawAndRotateStick(shapeRenderer, player);


		if(DEBUG) {
			b2dr.render(world, camera.combined);
		}


	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.update();
	}

	@Override
	public void dispose () {
		world.dispose();
		b2dr.dispose();
		batch.dispose();
		tmr.dispose();
		map.dispose();

	}

	public void update(float delta){
		world.step(1 / 60f, 6, 2);

		tmr.setView(camera);

		inputUpdate(delta);
		camera.update();
	}

	public void inputUpdate(float delta){
		int horizontalForce = 0;

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			horizontalForce -= 1;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			horizontalForce += 1;

		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			player.applyForceToCenter(0, 300, false);

		}

		//player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
	}

	private void createBills(){

		float radius = 0.5f;

		//creating bills
		B2DBodyBuilder.createCircle(world, 40, 16, radius, false);

		B2DBodyBuilder.createCircle(world, 41.1f, 16.5f, radius, false);
		B2DBodyBuilder.createCircle(world, 41.1f, 15.5f, radius, false);

		B2DBodyBuilder.createCircle(world, 42.2f, 15, radius, false);
		B2DBodyBuilder.createCircle(world, 42.2f, 16, radius, false);
		B2DBodyBuilder.createCircle(world, 42.2f, 17, radius, false);

		B2DBodyBuilder.createCircle(world, 43.3f, 14.5f, radius, false);
		B2DBodyBuilder.createCircle(world, 43.3f, 15.5f, radius, false);
		B2DBodyBuilder.createCircle(world, 43.3f, 16.5f, radius, false);
		B2DBodyBuilder.createCircle(world, 43.3f, 17.5f, radius, false);

		B2DBodyBuilder.createCircle(world, 44.4f, 14, radius, false);
		B2DBodyBuilder.createCircle(world, 44.4f, 15, radius, false);
		B2DBodyBuilder.createCircle(world, 44.4f, 16, radius, false);
		B2DBodyBuilder.createCircle(world, 44.4f, 17, radius, false);
		B2DBodyBuilder.createCircle(world, 44.4f, 18, radius, false);
	}

	private void drawAndRotateStick(ShapeRenderer renderer, Body body){

		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.setColor(Color.WHITE);

		//TODO: revise points
		renderer.line(getBodyXInPixels(body), getBodyYInPixels(body),  getBodyXInPixels(body) - (getMouseX() - getBodyXInPixels(body)),
				WORLD_PIXEL_HEIGHT - getMouseY() - 2 * (WORLD_PIXEL_HEIGHT - Gdx.input.getY() - getBodyYInPixels(body)));

		renderer.end();

		//TODO: TEST
		Vector2 line1 = new Vector2(0, 324);
		Vector2 line2 = getEquationOfLine(new Vector2(320, 324), new Vector2(getMouseX(), WORLD_PIXEL_HEIGHT - getMouseY()));


		//angle += 0.5 % 360;

		angle = getAngleBetweenTwoLines(line1, line2);




		// II quater
		if(getMouseX() < getBodyXInPixels(body)){
			if(WORLD_PIXEL_HEIGHT - getMouseY() > getBodyYInPixels(body)){
				angle = -90 - (90 - angle);
			}else {
				angle = -180 + angle;
			}
		}

		angle = 180 + (-angle);
		System.out.println("Angle = " + angle);

		billardStickSprite.setRotation(angle);
	}

	private Vector2 getEquationOfLine(Vector2 point1, Vector2 point2){
		float a = (point1.y - point2.y) / (point1.x - point2.x);
		float b = point1.y - (a * point1.x);

		return new Vector2(a, b);
	}

	private float getAngleBetweenTwoLines(Vector2 line1, Vector2 line2){

		double a1 = line1.x;
		double a2 = line2.x;

		double formulae = (a1 - a2) / (1 + a1 * a2);
		float deg = (float)Math.toDegrees(Math.atan(formulae));

		if(Float.isNaN(deg)){
			deg = -90;
		}
//
//		//deg = deg < 0? -deg : 90 + deg;
//
//		if(deg < 0){
//			deg = -deg;
//		}




//		deg = deg > 0 ? 360 - deg : 0 - deg;

		return deg;

	}






}
