package com.merry.game.models;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.merry.game.utils.Constants.PPM;

public class Hero {

    private World world;
    private Body body;
    private Vector2 position;


    public Hero(World world) {
        this.world = world;
        this.position = new Vector2(0,10);
        body = createHeroBody();
    }

    public float getXPosition() {
        return position.x * PPM;
    }

    public float getYPosition() {
        return position.y * PPM;
    }

    public Body getHeroBody() {
        return body;
    }

    public Body createHeroBody() {
        Body body = world.createBody(createBodyDefinition());
        setFixtureTo(body);
        return body;
    }

    private BodyDef createBodyDefinition() {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(position);
        def.fixedRotation = true; //test for false

        return def;
    }
    private Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16 / PPM, 16 / PPM);
        return shape;
    }
    
    private void setFixtureTo(Body body) {
        Shape shape = createShape();
        body.createFixture(shape, 1.0f);
        shape.dispose();
    }
}
