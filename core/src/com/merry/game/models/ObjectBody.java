package com.merry.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.merry.game.utils.Constants.PPM;

public abstract class ObjectBody {

    private World world;
    private Vector2 position;

    public ObjectBody(World world, Vector2 position) {
        this.world = world;
        this.position = position;
    }

    public Body createBox(boolean isDynamic) {
        BodyDef def = new BodyDef();
        if(isDynamic) {
            def.type = BodyDef.BodyType.DynamicBody;
        } else {
            def.type = BodyDef.BodyType.StaticBody;
        }
        def.position.set(position.x / PPM, position.y /PPM);
        def.fixedRotation = true;

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        setBoxSize(shape);
        body.createFixture(shape, 1.0f);
        shape.dispose();

        return body;
    }

    public abstract void setBoxSize(PolygonShape shape);
}
