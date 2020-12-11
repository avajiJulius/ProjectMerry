package com.merry.game.models;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.merry.game.utils.Constants.PPM;
import static com.merry.game.utils.HeroActionTexture.*;

public class Hero extends ObjectBody{

    private static final int WIDTH = 5;
    private static final int HEIGHT = 15;

    private TextureRegion texture;
    private TextureAtlas atlas;


    public Hero(World world, TextureAtlas atlas, Vector2 position) {
        super(world, position);
        this.atlas = atlas;
        setTextureRegion(walk05);
    }

    public Hero(World world, TextureAtlas atlas, float x, float y) {
        super(world, new Vector2(x, y));
        this.atlas = atlas;
        setTextureRegion(walk05);
    }

    public TextureRegion getTextureRegion() {
        return texture;
    }

    public void setTextureRegion(String regionName) {
        this.texture = atlas.findRegion(regionName);
    }


    @Override
    public void setBoxSize(PolygonShape shape) {
        shape.setAsBox(WIDTH / PPM, HEIGHT / PPM);
    }

}
