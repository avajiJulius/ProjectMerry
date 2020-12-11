package com.merry.game.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.merry.game.builders.BodyBuilder;

import java.util.concurrent.TimeUnit;

import static com.merry.game.utils.Constants.*;
import static com.merry.game.utils.HeroActionTexture.walk05;

public abstract class Character {

    private static final int WIDTH = 5;
    private static final int HEIGHT = 15;

    private short self;
    private short bodyInteractor;
    private short sensorInteractor;

    private TextureRegion texture;
    private TextureAtlas atlas;
    private BodyBuilder bodyBuilder, sensorBuilder;
    private Body body, sensor;
    private WeldJointDef jointDef;
    private Character character;


    public Character(TextureAtlas atlas, World world,
                     short self, short bodyInteractor, short sensorInteractor) {

        this.self = self;
        this.bodyInteractor = bodyInteractor;
        this.sensorInteractor = sensorInteractor;

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
        bodyBuilder.setFixtureDef(self, bodyInteractor, (short) 0, false);
        return bodyBuilder.build();
    }
    private Body buildSensor() {
        sensorBuilder.setBodyDef(new Vector2(2, 10), true);
        sensorBuilder.setShapeSize(WIDTH, HEIGHT);
        sensorBuilder.setFixtureDef(self, sensorInteractor, (short) 0, true);
        return sensorBuilder.build();
    }


    private void join() {
        jointDef.bodyA = body;
        jointDef.bodyB = sensor;
    }


//    public void evade() {
//        new Thread(() -> {
//            Filter filter = body.getFixtureList().get(0).getFilterData();
//            filter.maskBits = WALL;
//            this.body.getFixtureList().get(0).setFilterData(filter);
//
//            try {
//                TimeUnit.MILLISECONDS.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            filter.maskBits = (short) (WALL | ENEMY);
//            this.body.getFixtureList().get(0).setFilterData(filter);
//        }).start();
//
//
//    }

    public Body getBody() {
        return body;
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
