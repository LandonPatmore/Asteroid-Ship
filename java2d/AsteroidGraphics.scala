package asteroidsfw.java2d

import asteroidsfw.{Asteroid, GraphicsObject}

trait AsteroidGraphics extends GraphicsObject {
  this: Asteroid =>
  def render() {
     Java2dGraphics.graphics.drawOval(pos.x.toInt - radius, pos.y.toInt - radius, 2 * radius, 2 * radius)
  }
}
