package com.billard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import utils.B2DBodyBuilder;
import utils.B2DConstants;
import utils.TiledObjectUtil;

import static utils.B2DConstants.PPM;

public class Application extends ApplicationAdapter {

	public static final String APP_TITLE = "Billard 2D";
	public static final float APP_VERSION = 0.1f;
	public static final int APP_FPS = 60;

	public static final int WORLD_PIXEL_WIDTH = 1280;
	public static final int WORLD_PIXEL_HEIGHT = 720;
	public static final int WORLD_VIRTUAL_PIXEL_WIDTH = 1280;
	public static final int WORLD_VIRTUAL_PIXEL_HEIGHT = 720;
	public static int scale = (WORLD_VIRTUAL_PIXEL_WIDTH * WORLD_VIRTUAL_PIXEL_HEIGHT) / (WORLD_PIXEL_WIDTH * WORLD_PIXEL_HEIGHT);
	public static final float WORLD_WIDTH = WORLD_PIXEL_WIDTH / PPM; //in meter
	public static final float WORLD_HEIGHT = WORLD_PIXEL_HEIGHT / PPM; //in meter

	private final boolean DEBUG = false;

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

	//managers
	AssetManager assetManager;

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
		player = B2DBodyBuilder.createCircle(world, 16, 16.2f, 1, false);

		createBills();

		player.applyForceToCenter(1000 * 2, 0, false);

		// creating batch
		batch = new SpriteBatch();

		//loading tiled map and creating tiled map renderer
		map = new TmxMapLoader().load("Maps/table.tmx");
		tmr = new OrthogonalTiledMapRenderer(map, B2DConstants.MPP);

		TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision-layer").getObjects());

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

		batch.end();

		b2dr.render(world, camera.combined);


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

		//creating bills
		B2DBodyBuilder.createCircle(world, 40, 16, 1, false);

		B2DBodyBuilder.createCircle(world, 41.1f, 16.5f, 1, false);
		B2DBodyBuilder.createCircle(world, 41.1f, 15.5f, 1, false);

		B2DBodyBuilder.createCircle(world, 42.2f, 15, 1, false);
		B2DBodyBuilder.createCircle(world, 42.2f, 16, 1, false);
		B2DBodyBuilder.createCircle(world, 42.2f, 17, 1, false);

		B2DBodyBuilder.createCircle(world, 43.3f, 14.5f, 1, false);
		B2DBodyBuilder.createCircle(world, 43.3f, 15.5f, 1, false);
		B2DBodyBuilder.createCircle(world, 43.3f, 16.5f, 1, false);
		B2DBodyBuilder.createCircle(world, 43.3f, 17.5f, 1, false);

		B2DBodyBuilder.createCircle(world, 44.4f, 14, 1, false);
		B2DBodyBuilder.createCircle(world, 44.4f, 15, 1, false);
		B2DBodyBuilder.createCircle(world, 44.4f, 16, 1, false);
		B2DBodyBuilder.createCircle(world, 44.4f, 17, 1, false);
		B2DBodyBuilder.createCircle(world, 44.4f, 18, 1, false);
	}


}
