package asteroidsfw.java2d

import java.awt._
import java.awt.event._
import javax.swing.JFrame

import asteroidsfw.{Game, GraphicsSubSystem}

object Java2dGraphics extends GraphicsSubSystem {
  val container = new JFrame("Test Frame")
  val canvas = new Canvas()
  val panel = container.getContentPane
  panel.setPreferredSize(new Dimension(Game.hRes, Game.vRes))
  panel.setLayout(null)
  canvas.setBounds(0, 0, Game.hRes, Game.vRes)
  panel.add(canvas)
  canvas.setIgnoreRepaint(true)
  container.pack
  container.setResizable(false)
  container.setVisible(true)
  canvas.addKeyListener(mainKeys)
  canvas.requestFocus
  canvas.createBufferStrategy(2)
  val strategy = canvas.getBufferStrategy
  var graphics: Graphics = _

  object mainKeys extends KeyAdapter {
    override def keyPressed(e: KeyEvent) {
      e.getKeyCode match {
        case KeyEvent.VK_Q => Game.quitting = true
        case KeyEvent.VK_P => {
          Game.mainClock.paused = !Game.mainClock.paused
          Game.elapsedClock.paused = !Game.elapsedClock.paused
        }
        case KeyEvent.VK_C => Game.period = Math.MAX_INT
        case _ =>
      }
    }
  }

  def setDefaultColor() {
    graphics.setColor(Color.red)
  }

  private var textPos = 0
  def drawString(text: String) {
    graphics.drawString(text, 5, textPos)
    textPos += graphics.getFont.getSize + 5
  }

  override def update(dt: Double) {
    graphics = strategy.getDrawGraphics
    graphics.setColor(Color.black)
    graphics.fillRect(0, 0, Game.hRes, Game.vRes)
    setDefaultColor()
    super.update(dt)
    textPos = graphics.getFont.getSize + 5
    graphics.drawString(Game.elapsedClock.time.toLong.toString, Game.hRes / 2, graphics.getFont.getSize + 5)
    graphics.dispose
    strategy.show
  }

  override def shutdown() {
    container.dispose()
  }
}
