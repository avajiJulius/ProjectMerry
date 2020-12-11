package com.merry.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.merry.game.utils.Constants.PPM;

public class Platform {
    private World world;
    private Body body;
    private Vector2 position;


    public Platform(World world) {
        this.world = world;
        this.position = new Vector2(0,0);
    }

    public float getXPosition() {
        return position.x * PPM;
    }

    public float getYPosition() {
        return position.y * PPM;
    }

    public Body getPlatformBody() {
        return body;
    }

    public Body createPlatformBody() {
        Body body = world.createBody(createBodyDefinition());
        setFixtureTo(body);
        return body;
    }

    private BodyDef createBodyDefinition() {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(position);
        def.fixedRotation = true; //test for false

        return def;
    }
    private Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32 / PPM, 16 / PPM);
        return shape;
    }

    private void setFixtureTo(Body body) {
        Shape shape = createShape();
        body.createFixture(shape, 1.0f);
        shape.dispose();
    }

}
