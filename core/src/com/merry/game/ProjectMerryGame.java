package com.merry.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.merry.game.models.Hero;
import com.merry.game.models.Platform;


import static com.merry.game.utils.Constants.PPM;

public class ProjectMerryGame extends ApplicationAdapter {


	private static final float SCALE = 2.0f;
	private OrthographicCamera camera;
	private Box2DDebugRenderer renderer;

	private World world;
	private Body heroBody, platformBody;
	private Hero hero;

	private SpriteBatch batch;
	private TextureAtlas atlas;
	private TextureRegion region;


	@Override
	public void create () {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width/SCALE, height/SCALE);

		batch = new SpriteBatch();

		atlas = new TextureAtlas("main.pack");

		world = new World(new Vector2(0f, -10f), false);
		renderer = new Box2DDebugRenderer();

		hero  = new Hero(world, atlas, 2, 10);
		region = hero.getTextureRegion();
		Platform platform = new Platform(world, 0, 0, 32,16);
		heroBody = hero.createBox(true);
		platform.createBox(false);

	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		update(deltaTime);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(region , heroBody.getPosition().x * PPM - (region.getRegionWidth() / 1.7f), heroBody.getPosition().y * PPM - (region.getRegionHeight()) / 2.4f);
		batch.end();

		renderer.render(world, camera.combined.scl(PPM));
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width/ SCALE, height / SCALE);
	}

	private void update(float deltaTime) {
		world.step(1/60f, 6,2);
		inputUpdate();
		cameraUpdate();
		batch.setProjectionMatrix(camera.combined);
	}

	private void inputUpdate() {
		int horizontalForce = 0;

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalForce += 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalForce -= 1;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			heroBody.applyForceToCenter(0, 100, true);
		}


		heroBody.setLinearVelocity(horizontalForce * 5, heroBody.getLinearVelocity().y);

	}

	@Override
	public void dispose () {
		renderer.dispose();
		world.dispose();
		batch.dispose();
	}

	public void cameraUpdate() {
		Vector3 position = camera.position;
		position.x = heroBody.getPosition().x * PPM;
		position.y = heroBody.getPosition().y * PPM;
		camera.position.set(position);

		camera.update();
	}

}
