package com.merry.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.merry.game.utils.Constants.PPM;

public class Platform extends ObjectBody{

    private int width;
    private int height;

    public Platform(World world, Vector2 position, int width, int height) {
        super(world, position);
        this.width = width;
        this.height = height;
    }

    public Platform(World world, float x, float y, int width, int height) {
        super(world, new Vector2(x, y));
        this.width = width;
        this.height = height;
    }

    @Override
    public void setBoxSize(PolygonShape shape) {
        shape.setAsBox(width / PPM, height / PPM);
    }
}
