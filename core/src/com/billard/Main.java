package com.billard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import static utils.Constants.PPM;

public class Main extends ApplicationAdapter {

	private final boolean DEBUG = false;

	private OrthographicCamera camera;

	private Box2DDebugRenderer b2dr;
	private World world;
	private Body player, platform;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w/2, h/2);

		world = new World(new Vector2(0, -9.8f), false);
		b2dr = new Box2DDebugRenderer();


		//player = createBox(2, 100, 32, 32, false);
		player = createCircle(2, 100, 20, false);
		createCircle(2, 150, 20, false);
		createCircle(2, 150, 20, false);
		createCircle(2, 150, 20, false);

		createBox(0, 0, 200, 200, false);

		platform = createBox(0, 0, 200, 32, true);
	}


	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		b2dr.render(world, camera.combined.scl(PPM));

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.setToOrtho(false, width/2, height/2);
	}

	@Override
	public void dispose () {
		world.dispose();
		b2dr.dispose();

	}

	public void update(float delta){
		world.step(1 / 60f, 6, 2);

		inputUpdate(delta);
		cameraUpdate(delta);
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
			player.applyForceToCenter(0, 150, false);

		}

		player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
	}

	public void cameraUpdate(float delta){
		Vector3 position  = camera.position;

		position.x = player.getPosition().x * PPM;
		position.y = player.getPosition().y * PPM;
		camera.position.set(position);

		camera.update();
	}

	public Body createBox(int x, int y, int width, int height, boolean isStatic){
		Body pBody;
		BodyDef def = new BodyDef();

		if(isStatic) {
			def.type = BodyDef.BodyType.StaticBody;
		}
		else {
			def.type = BodyDef.BodyType.DynamicBody;
		}

		def.position.set(x / PPM, y / PPM);
		def.fixedRotation = false;
		pBody = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/ 2 / PPM, height / 2 / PPM);

		pBody.createFixture(shape, 1.0f);
		shape.dispose();

		return pBody;
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
