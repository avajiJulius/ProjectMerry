package com.merry.game.models;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.*;
import com.merry.game.builders.BodyBuilder;

import java.util.concurrent.TimeUnit;

import static com.merry.game.utils.Constants.*;
import static com.merry.game.utils.HeroActionTexture.*;

public class Hero {

    private static final int WIDTH = 5;
    private static final int HEIGHT = 15;

    private TextureRegion texture;
    private TextureAtlas atlas;
    private BodyBuilder bodyBuilder, sensorBuilder;
    private Body body, sensor;
    private WeldJointDef jointDef;

    public Hero(TextureAtlas atlas, World world) {
        bodyBuilder = new BodyBuilder(world, this);
        sensorBuilder = new BodyBuilder(world, this);
        body = buildBody();
        sensor = buildSensor();
        jointDef = new WeldJointDef();
        join();
        world.createJoint(jointDef);
        this.atlas = atlas;
        setTextureRegion(walk05);
    }

    private Body buildBody() {
        bodyBuilder.setBodyDef(new Vector2(2, 10), true);
        bodyBuilder.setShapeSize(WIDTH, HEIGHT);
        bodyBuilder.setFixtureDef(HERO, WALL , (short) 0, false);
        return bodyBuilder.build();
    }
    private Body buildSensor() {
        sensorBuilder.setBodyDef(new Vector2(2, 10), true);
        sensorBuilder.setShapeSize(WIDTH, HEIGHT);
        sensorBuilder.setFixtureDef(HERO, ENEMY, (short) 0, true);
        return sensorBuilder.build();
    }

    public Body getHeroBody() {
        return body;
    }


    private void join() {
        jointDef.bodyA = body;
        jointDef.bodyB = sensor;
    }


    public void evade() {
        new Thread(() -> {
            Filter filter = body.getFixtureList().get(0).getFilterData();
            filter.maskBits = WALL;
            this.body.getFixtureList().get(0).setFilterData(filter);

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            filter.maskBits = (short) (WALL | ENEMY);
            this.body.getFixtureList().get(0).setFilterData(filter);
        }).start();


    }

    public TextureRegion getTextureRegion() {
        return texture;
    }

    public void setTextureRegion(String regionName) {
        this.texture = atlas.findRegion(regionName);
    }


    public void applyForceToCenter(int forceX, int forceY, boolean isWake) {
        body.applyForceToCenter(forceX, forceY, isWake);
        sensor.applyForceToCenter(forceX, forceY, isWake);
    }
}
