package asteroidsfw.java2d

import asteroidsfw._

object Java2dFactory extends GameObjectFactory {
  def ship(aiControlled: Boolean, shipColor: String): Ship = 
    if (aiControlled)
      new Ship with ShipGraphics { val color = java.awt.Color.decode(shipColor) }
    else
      new Ship with ShipGraphics with ShipControl { val color = java.awt.Color.decode(shipColor) }
  def spaceStation: SpaceStation = new SpaceStation with SpaceStationGraphics
  def bullet(parent: Ship, pos: Vector2d, v: Vector2d): Bullet = new Bullet(parent, pos, v) with BulletGraphics
  def smallAsteroid(pos: Vector2d, v: Vector2d): Asteroid = new SmallAsteroid(pos, v) with AsteroidGraphics 
  def mediumAsteroid(pos: Vector2d, v: Vector2d): Asteroid = new MediumAsteroid(pos, v) with AsteroidGraphics 
  def largeAsteroid(pos: Vector2d, v: Vector2d): Asteroid = new LargeAsteroid(pos, v) with AsteroidGraphics 
}
