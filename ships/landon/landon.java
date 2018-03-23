package ships.landon;
import asteroidsfw.*;
import asteroidsfw.ai.*;

public class landon implements ShipMind {
  private ShipControl control;

  public void init(ShipControl control) {
    this.control = control;
  }

  public void think(Perceptions percepts, double delta) {
    control.thrustForward(true);
    control.rotateLeft(true);
    control.shooting(true);
  }
}
