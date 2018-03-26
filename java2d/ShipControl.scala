package asteroidsfw.java2d

import java.awt.event._

import asteroidsfw.Ship

trait ShipControl {
  this: Ship =>
  object keys extends KeyAdapter {
    override def keyPressed(e: KeyEvent) {
      e.getKeyCode match {
        case KeyEvent.VK_UP => thrustForward(true)
        case KeyEvent.VK_DOWN => thrustBackward(true)
        case KeyEvent.VK_LEFT => rotateLeft(true)
        case KeyEvent.VK_RIGHT => rotateRight(true)
        case KeyEvent.VK_SPACE => shooting(true)
        case _ =>
      }
    }

    override def keyReleased(e: KeyEvent) {
      e.getKeyCode match {
        case KeyEvent.VK_UP => thrustForward(false)
        case KeyEvent.VK_DOWN => thrustBackward(false)
        case KeyEvent.VK_LEFT => rotateLeft(false)
        case KeyEvent.VK_RIGHT => rotateRight(false)
        case KeyEvent.VK_SPACE => shooting(false)
        case _ =>
      }
    }
  }

  Java2dGraphics.canvas.addKeyListener(keys)
}
