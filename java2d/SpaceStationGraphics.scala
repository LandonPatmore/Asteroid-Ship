package asteroidsfw.java2d

import asteroidsfw.{GraphicsObject, SpaceStation}

trait SpaceStationGraphics extends GraphicsObject {
  this: SpaceStation =>
  def render() {
    Java2dGraphics.graphics.setColor(java.awt.Color.green)
    Java2dGraphics.graphics.drawOval(pos.x.toInt - radius, pos.y.toInt - radius, 2 * radius, 2 * radius)
    Java2dGraphics.setDefaultColor()
  }
}
