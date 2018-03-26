package asteroidsfw.java2d

trait BulletGraphics extends GraphicsObject {
  this: Bullet =>
  def render() {
    Java2dGraphics.graphics.setColor(parent.asInstanceOf[ShipGraphics].color)
    Java2dGraphics.graphics.fillOval(pos.x.toInt - radius, pos.y.toInt - radius, 2 * radius, 2 * radius)
    Java2dGraphics.setDefaultColor()
  }
}
