package com.merry.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.merry.game.utils.Constants.PPM;

public class BodyBuilder {

    private World world;
    private BodyDef bodyDef;
    private PolygonShape shape;
    private FixtureDef fixtureDef;

    public BodyBuilder(World world) {
        this.world = world;
        this.bodyDef = new BodyDef();
        this.shape = new PolygonShape();
        this.fixtureDef = new FixtureDef();
    }

    public Body build() {
        Fixture fixture = world.createBody(bodyDef).createFixture(fixtureDef);
        shape.dispose();

        return fixture.getBody();
    }

    public void setBodyDef(Vector2 position, boolean isDynamic) {
        if(isDynamic) {
            this.bodyDef.type = BodyDef.BodyType.DynamicBody;
        } else {
            this.bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        this.bodyDef.position.set(position.x / PPM, position.y /PPM);
        this.bodyDef.fixedRotation = true;
    }

    public void setShapeSize(float width, float height) {
        shape.setAsBox(width / PPM, height / PPM);
    }

    public void setFixtureDef(short object, short collisionObject, short groupIndex) {
        this.fixtureDef.shape = shape;
        this.fixtureDef.density = 1.0f;
        this.fixtureDef.filter.categoryBits = object;
        this.fixtureDef.filter.maskBits = collisionObject;
        this.fixtureDef.filter.groupIndex = groupIndex;
    }


}
