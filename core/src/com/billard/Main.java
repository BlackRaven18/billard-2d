package com.billard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import utils.Constants;

import static utils.Constants.PPM;

public class Main extends ApplicationAdapter {

	private final boolean DEBUG = false;

	private OrthographicCamera camera;
	private StretchViewport viewport;

	private OrthogonalTiledMapRenderer tmr;
	private TiledMap map;

	private Box2DDebugRenderer b2dr;
	private World world;
	private Body player;

	private SpriteBatch batch;

	@Override
	public void create () {

		//creating world
		world = new World(new Vector2(0, 0), false);

		//creating debug renderer
		b2dr = new Box2DDebugRenderer();

		// creating camera
		camera = new OrthographicCamera(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
		camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
		camera.update();

		//creating viewport, camera configuration
		viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

		//creating player
		player = createCircle(2, 100, 30, false);

		// creating batch
		batch = new SpriteBatch();

		//loading tiled map and creating tiled map renderer
		map = new TmxMapLoader().load("Maps/table.tmx");
		tmr = new OrthogonalTiledMapRenderer(map, Constants.MPP);
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
		//camera.setToOrtho(false, width/2, height/2);
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
			player.applyForceToCenter(0, 50, false);

		}

		player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
	}


	public Body createCircle(int x, int y, float radius, boolean isStatic){
		Body pBody;
		BodyDef def = new BodyDef();

		if(isStatic) {
			def.type = BodyDef.BodyType.StaticBody;
		}
		else {
			def.type = BodyDef.BodyType.DynamicBody;
		}

		def.position.set(x / PPM, y / PPM);
		def.fixedRotation = true;
		def.linearDamping = 1f;
		pBody = world.createBody(def);

		CircleShape shape = new CircleShape();
		shape.setRadius(radius / 2 / PPM);


		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit
		pBody.createFixture(fixtureDef);
		shape.dispose();

		return pBody;
	}

}
