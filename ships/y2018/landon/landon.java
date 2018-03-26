package ships.y2018.landon;

import asteroidsfw.Vector2d;
import asteroidsfw.ai.*;

public class landon implements ShipMind {
    private ShipControl control;

    private enum State {
        HUNT,
        FLEE,
        DEAD
    }

    private State currentState;
    private AsteroidPerception closestAsteroid;
    private Vector2d initialPosition;

    public void init(ShipControl control) {
        this.control = control;
        this.currentState = State.HUNT;
        this.initialPosition = control.pos();
    }

    public void think(Perceptions percepts, double delta) {
        state(percepts);
    }

    private void state(Perceptions perceptions) {
        whereToLook(perceptions);
        switch (currentState) {
            case HUNT:
                hunt(perceptions);
                checkShouldFlee();
                shouldShoot();
                checkIsDead();
                break;
            case FLEE:
                shouldShoot();
                checkShouldHunt();
                checkIsDead();
                break;
            case DEAD:
                checkIsDead();
                break;
        }
    }

    private void checkIsDead() {
        if (control.respawnIn() > 0 && ((currentState == State.HUNT) || currentState == State.FLEE)) {
            currentState = State.DEAD;
        } else if (control.respawnIn() < 0 && currentState == State.DEAD) {
            currentState = State.HUNT;
        }
    }

    private void hunt(Perceptions percepts) {
        findClosestAsteroid(percepts.asteroids());
        checkShouldFlee();
    }

    private void whereToLook(Perceptions percepts) {
        findClosestAsteroid(percepts.asteroids());
        final double angleAsteroid = angleToTarget(control.pos(), closestAsteroid.pos());
        final double heading = calculateHeading(control.direction());

        final double difference = angleAsteroid - heading;

        control(difference);

    }

    private void checkShouldFlee() {
        if (distanceToTarget(control.pos(), closestAsteroid.pos()) < 85) {
            currentState = State.FLEE;
        }
    }

    private void checkShouldHunt() {
        if (distanceToTarget(control.pos(), closestAsteroid.pos()) > 85) {
            currentState = State.HUNT;
        }
    }

    private void shouldShoot() {
        final double angleToTarget = angleToTarget(control.pos(), closestAsteroid.pos());
        final double heading = calculateHeading(control.direction());
        final double difference = angleToTarget - heading;

        if ((distanceToTarget(control.pos(), closestAsteroid.pos()) <= 200) && Math.abs(difference) < 0.40) {
            control.shooting(true);
        } else {
            control.shooting(false);
        }
    }

    private void control(double difference) {
        if (currentState == State.HUNT) {
            control.thrustBackward(false);
            control.thrustForward(true);
        } else if (currentState == State.FLEE) {
            control.thrustForward(false);
            control.thrustBackward(true);
        }

        if (difference < 0) {
            control.rotateLeft(true);
            control.rotateRight(false);
        } else {
            control.rotateLeft(false);
            control.rotateRight(true);
        }
    }

    private void findClosestAsteroid(AsteroidPerception[] asteroids) {
        double closest = Double.MAX_VALUE;
        for (AsteroidPerception a : asteroids) {
            final double dist = distanceToTarget(control.pos(), a.pos());
            if (dist < closest) {
                closest = dist;
                closestAsteroid = a;
            }
        }
    }

    private double distanceToTarget(Vector2d currentPosition, Vector2d target) {
        Vector2d vector = target.$minus(currentPosition);

        double a = vector.x();
        double b = vector.y();

        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));

    }

    private double angleToTarget(Vector2d currentPosition, Vector2d target) {
        Vector2d vector = target.$minus(currentPosition);

        final double x = vector.x();
        final double y = vector.y();

        final double angleFacing = Math.atan2(y, x);

        final double degrees = Math.toDegrees(angleFacing);

        return degrees < 0.0 ? degrees + 360 : degrees;
    }

    private double calculateHeading(Vector2d vector) {
        final double x = vector.x();
        final double y = vector.y();

        final double angleFacing = Math.atan2(y, x);

        final double degrees = Math.toDegrees(angleFacing);

        return degrees < 0.0 ? degrees + 360 : degrees;
    }
}
