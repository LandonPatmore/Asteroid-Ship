package asteroidsfw.java2d

import java.awt.Color

import asteroidsfw.{GraphicsObject, Ship}

trait ShipGraphics extends GraphicsObject {
  this: Ship =>

  val color: java.awt.Color

  def render() {
    val dir = direction * 10
    val top = pos + dir
    val left = pos + (dir rotate 2.4)
    val right = pos + (dir rotate -2.4)
    Java2dGraphics.graphics.setColor(new Color(0,1,0,1))
    Java2dGraphics.graphics.drawOval(pos.x.toInt, pos.y.toInt, this.radius, this.radius);
    Java2dGraphics.graphics.setColor(color)
    Java2dGraphics.graphics.drawPolygon(Array(top.x.toInt, left.x.toInt, right.x.toInt), Array(top.y.toInt, left.y.toInt, right.y.toInt), 3)
    Java2dGraphics.drawString(score.toString)
    Java2dGraphics.setDefaultColor()
  }
}
