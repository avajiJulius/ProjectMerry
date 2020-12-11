package com.merry.game.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;

import static com.merry.game.utils.Constants.*;

public class Enemy extends Character{

    public Enemy(TextureAtlas atlas, World world) {
        super(atlas, world, ENEMY, WALL, HERO);
    }
}
