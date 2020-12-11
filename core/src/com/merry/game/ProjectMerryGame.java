package com.merry.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.merry.game.models.Hero;
import com.merry.game.models.Platform;

import java.util.List;

import static com.merry.game.utils.Constants.PPM;

public class ProjectMerryGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private Box2DDebugRenderer renderer;

	private World world;
	private Body hero;
	private Body platform;
	
	@Override
	public void create () {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width/2, height/2);

		world = new World(new Vector2(0, -10 ), false);
		renderer = new Box2DDebugRenderer();

		hero = createBox(16,16, 2 , 10, true);
		platform = createBox(32,16, 0 , 0, false);


	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		update(deltaTime);


		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render(world, camera.combined.scl(PPM));
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width/ 2, height / 2);
	}

	private void update(float deltaTime) {
		world.step(1/60f, 6,2);
		inputUpdate(deltaTime);
		cameraUpdate(deltaTime);
	}

	private void inputUpdate(float deltaTime) {
		int horizontalForce = 0;

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalForce += 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalForce -= 1;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			hero.applyForceToCenter(0, 200, false);
		}

		hero.setLinearVelocity(horizontalForce * 5, hero.getLinearVelocity().y);

	}

	@Override
	public void dispose () {
		renderer.dispose();
		world.dispose();
	}

	public void cameraUpdate(float deltaTime) {
		Vector3 position = camera.position;
		position.x = hero.getPosition().x * PPM;
		position.y = hero.getPosition().y * PPM;
		camera.position.set(position);

		camera.update();
	}

	public Body createBox(int width, int height, float x, float y, boolean isDynamic) {
		Body body;
		BodyDef def = new BodyDef();
		if(isDynamic) {
			def.type = BodyDef.BodyType.DynamicBody;
		} else {
			def.type = BodyDef.BodyType.StaticBody;
		}
		def.position.set(x / PPM, y/PPM);
		def.fixedRotation = true;

		body = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / PPM, height / PPM);

		body.createFixture(shape, 1.0f);
		shape.dispose();
		return body;
	}
}
