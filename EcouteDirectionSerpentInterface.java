// on recrée notre propre interface EcouteDirectionSerpentInterface héritant de KeyListener

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public interface EcouteDirectionSerpentInterface extends KeyListener{
   public void keyPressed(KeyEvent e);
   public void keyReleased(KeyEvent e);
   public void keyTyped(KeyEvent e);
}
