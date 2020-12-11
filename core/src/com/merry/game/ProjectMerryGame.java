package com.merry.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.merry.game.listeners.AttackListener;
import com.merry.game.models.Enemy;
import com.merry.game.models.Hero;
import com.merry.game.models.Platform;

import static com.merry.game.utils.Constants.*;

public class ProjectMerryGame extends ApplicationAdapter {


	private static final float SCALE = 2.0f;
	private OrthographicCamera camera;
	private Box2DDebugRenderer renderer;

	private World world;
	private Body heroBody, enemyBody;
	private Hero hero, heroSensor;

	private SpriteBatch batch;
	private TextureAtlas atlas;
	private TextureRegion heroRegion, enemyRegion;


	@Override
	public void create () {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width/SCALE, height/SCALE);

		batch = new SpriteBatch();
		atlas = new TextureAtlas("main.pack");

		world = new World(new Vector2(0f, -10f), false);
		world.setContactListener(new AttackListener());
		renderer = new Box2DDebugRenderer();

		hero  = new Hero(atlas, world);
		heroBody = hero.getBody();
		heroRegion = hero.getTextureRegion();

		Enemy enemy = new Enemy(atlas, world);
		enemyBody = enemy.getBody();
		enemyRegion = enemy.getTextureRegion();

		Platform platform = new Platform(world, 1000, 32);
		platform.getPlatformBody();
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		update(deltaTime);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(enemyRegion, enemyBody.getPosition().x * PPM - (enemyRegion.getRegionWidth() / 1.7f), enemyBody.getPosition().y * PPM - (enemyRegion.getRegionHeight()) / 2.4f);
		batch.draw(heroRegion, heroBody.getPosition().x * PPM - (heroRegion.getRegionWidth() / 1.7f), heroBody.getPosition().y * PPM - (heroRegion.getRegionHeight()) / 2.4f);
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
			hero.applyForceToCenter(0 ,80, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			hero.evade();
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
