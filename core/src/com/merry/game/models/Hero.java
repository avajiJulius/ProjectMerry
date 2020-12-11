package com.merry.game.models;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.merry.game.BodyBuilder;

import static com.merry.game.utils.Constants.*;
import static com.merry.game.utils.HeroActionTexture.*;

public class Hero {

    private static final int WIDTH = 5;
    private static final int HEIGHT = 15;

    private TextureRegion texture;
    private TextureAtlas atlas;
    private BodyBuilder builder;



    public Hero(TextureAtlas atlas, World world) {
        this.builder = new BodyBuilder(world);
        builder.setBodyDef(new Vector2(2, 10), true);
        builder.setShapeSize(WIDTH, HEIGHT);
        builder.setFixtureDef(HERO, WALL, (short) 0);
        this.atlas = atlas;
        setTextureRegion(walk05);
    }

    public Body getHeroBody() {
        return builder.build();
    }

    public TextureRegion getTextureRegion() {
        return texture;
    }

    public void setTextureRegion(String regionName) {
        this.texture = atlas.findRegion(regionName);
    }


}
