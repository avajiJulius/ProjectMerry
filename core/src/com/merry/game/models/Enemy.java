package com.merry.game.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.merry.game.builders.BodyBuilder;

import static com.merry.game.utils.Constants.*;
import static com.merry.game.utils.HeroActionTexture.walk05;

public class Enemy {

    private static final int WIDTH = 5;
    private static final int HEIGHT = 15;

    private TextureRegion texture;
    private TextureAtlas atlas;
    private BodyBuilder bodyBuilder, sensorBuilder;
    private WeldJointDef jointDef;
    private Body body, sensor;

    public Enemy(TextureAtlas atlas, World world, boolean isSensor) {
        bodyBuilder = new BodyBuilder(world, this);
        sensorBuilder = new BodyBuilder(world, this);
        jointDef = new WeldJointDef();
        body = buildBody();
        sensor = buildSensor();
        join();
        world.createJoint(jointDef);
        this.atlas = atlas;
        setTextureRegion(walk05);
    }

    public void getDamage() {
        body.setLinearDamping(3.0f);
    }

    public Body getEnemyBody() {
        return body;
    }

    private Body buildBody() {
        bodyBuilder.setBodyDef(new Vector2(20, 10), true);
        bodyBuilder.setShapeSize(WIDTH, HEIGHT);
        bodyBuilder.setFixtureDef(ENEMY, WALL , (short) 0, false);
        return bodyBuilder.build();
    }
    private Body buildSensor() {
        sensorBuilder.setBodyDef(new Vector2(20, 10), true);
        sensorBuilder.setShapeSize(WIDTH, HEIGHT);
        sensorBuilder.setFixtureDef(ENEMY, HERO, (short) 0, true);
        return sensorBuilder.build();
    }

    private void join() {
        jointDef.bodyA = body;
        jointDef.bodyB = sensor;
    }

    public TextureRegion getTextureRegion() {
        return texture;
    }

    public void setTextureRegion(String regionName) {
        this.texture = atlas.findRegion(regionName);
    }


}
