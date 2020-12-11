package com.merry.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.merry.game.BodyBuilder;

import static com.merry.game.utils.Constants.*;

public class Platform {

    private BodyBuilder builder;

    public Platform(World world, int width, int height) {

        this.builder = new BodyBuilder(world);
        builder.setBodyDef(new Vector2(0, 0), false);
        builder.setShapeSize(width, height);
        builder.setFixtureDef(WALL, HERO, (short) 0);
    }

    public Body getPlatformBody() {
        return builder.build();
    }
}
