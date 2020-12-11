package com.merry.game.models;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;

import static com.merry.game.utils.Constants.*;

public class Hero extends Character {


    public Hero(TextureAtlas atlas, World world) {
        super(atlas, world, HERO, WALL, ENEMY);
    }
}
