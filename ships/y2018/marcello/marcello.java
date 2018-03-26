package ships.y2018.marcello;

/**
 * Created by Marcello395 on 3/23/18.
 */

import asteroidsfw.Vector2d;
import asteroidsfw.ai.AsteroidPerception;
import asteroidsfw.ai.Perceptions;
import asteroidsfw.ai.ShipControl;
import asteroidsfw.ai.ShipMind;


public class marcello implements ShipMind {
    ShipControl control;
    double highHazardValue = 1200;
    double rotationalDisplacementCorrection = 0.3141592;

    public void init(ShipControl shipControl) {
        this.control = shipControl;

    }

    public void think(Perceptions perceptions, double d) {
        AsteroidPerception asteroidPerception = null;
        double closestAsteroid = Double.MAX_VALUE;
        for (AsteroidPerception surroundingAsteroids : perceptions.asteroids()) {
            double pointToAsteroid = this.control.pos().$minus(surroundingAsteroids.pos()).sqLength();
            if (pointToAsteroid >= closestAsteroid) continue;
            closestAsteroid = pointToAsteroid;
            asteroidPerception = surroundingAsteroids;
        }

        if (asteroidPerception != null) {
            Vector2d asteroidPosition = asteroidPerception.pos().$minus(this.control.pos());
            Vector2d shipDetectionVector = this.control.v().$plus(asteroidPosition.normalize().$times(120));
            double rangeofDistance = asteroidPosition.length() / shipDetectionVector.length() * this.rotationalDisplacementCorrection;
            Vector2d asteroidPointer = new Vector2d(asteroidPosition.x() + asteroidPerception.v().x() * rangeofDistance, asteroidPosition.y() + asteroidPerception.v().y() * rangeofDistance);
            double trackDirection = this.control.direction().cross(asteroidPointer);

            pointAtAsteroid(trackDirection);

            for (AsteroidPerception potentialShots : perceptions.asteroids()) {
                double distanceFromAsteroid = shipDetectionVector.$minus(this.control.pos()).length() * (double) potentialShots.radius();
                if (closestAsteroid >= 0.0 && distanceFromAsteroid >= closestAsteroid) continue;
                asteroidPerception = potentialShots;
                closestAsteroid = distanceFromAsteroid;
                control.shooting(true);
            }

            correctCollision(asteroidPerception, asteroidPointer, trackDirection);
        }
    }

    public double potentialCollision(AsteroidPerception asteroidPerception) {
        if (asteroidPerception == null) {
            return 0.0;
        }
        double xPosition = asteroidPerception.pos().x() - this.control.pos().x();
        double yPosition = asteroidPerception.pos().y() - this.control.pos().y();
        double xVelocity = asteroidPerception.v().x() - this.control.v().x();
        double yVelocity = asteroidPerception.v().y() - this.control.v().y();
        return xPosition * xVelocity + yPosition * yVelocity;
    }

    public boolean checkCollision(AsteroidPerception collidingAsteroid, Vector2d distanceToAsteroid) {
        return this.potentialCollision(collidingAsteroid) < 0.0 && distanceToAsteroid.sqLength() < this.highHazardValue * (double) collidingAsteroid.radius();
    }

    public void pointAtAsteroid(double directionToPoint){
        if (directionToPoint < 0.0) {
            this.control.rotateLeft(true);
            this.control.rotateRight(false);
        } else {
            this.control.rotateLeft(false);
            this.control.rotateRight(true);
        }
    }

    public void correctCollision(AsteroidPerception collidingAsteroid, Vector2d collidingAsteroidPosition, double directionToPoint ){
        if (this.checkCollision(collidingAsteroid, collidingAsteroidPosition)) {
            if (directionToPoint > 70.0 || directionToPoint < -70.0) {
                this.control.thrustForward(true);
                this.control.thrustBackward(false);
            } else {
                this.control.thrustBackward(true);
                this.control.thrustForward(false);
            }
        } else {
            this.control.thrustForward(true);
            this.control.thrustBackward(false);
        }
    }
}
