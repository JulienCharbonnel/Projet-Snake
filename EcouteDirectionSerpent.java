// on crée notre propre classe EcouteDirectionSerpent héritant de EcouteDirectionSerpentInterface

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class EcouteDirectionSerpent implements KeyListener{

   // constructeur
   public EcouteDirectionSerpent(){
   }

   @Override
   public void touchePresser(KeyEvent e){
         // on récupère la touche du clavier
         int touche = e.getKeyCode();
         // on vérifie si la touche du clavier est une flèche directionnelle
         if (touche == KeyEvent.VK_UP) {
            direction = Direction.NORD;     
         }
         if (touche == KeyEvent.VK_DOWN) {
            direction = Direction.SUD;
         }
         if (touche == KeyEvent.VK_LEFT) {
            direction = Direction.OUEST;
         }
         if (touche == KeyEvent.VK_RIGHT) {
            direction = Direction.EST;
         }
      }
   }

   @Override
   public void keyPressed(KeyEvent e) {
      // TODO Auto-generated method stub
      
   }
}