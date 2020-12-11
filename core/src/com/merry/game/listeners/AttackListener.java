package com.merry.game.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.merry.game.models.Enemy;

public class AttackListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(isValidForContact(fa, fb) && isHostile(fa, fb)) {
            if(fa.getUserData() instanceof Enemy) {
                System.out.println("Hit");
            } else {
                System.out.println("Hit2");
            }
        }
    }



    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(isValidForContact(fa, fb) && isHostile(fa, fb)) {
            System.out.println("End");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean isHostile(Fixture fa, Fixture fb) {
        if((fa.getFilterData().categoryBits - fb.getFilterData().categoryBits) == (short) (2 | -2)) {
            return true;
        }
        return false;
    }

    private boolean isValidForContact(Fixture fa, Fixture fb) {
        if(fa == null || fb == null) return false;
        if(fa.getUserData() == null || fb.getUserData() == null) return false;
        return true;
    }
}
