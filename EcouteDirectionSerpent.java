// on crée notre propre classe EcouteDirectionSerpent héritant de EcouteDirectionSerpentInterface

import java.awt.event.KeyListener;
import java.util.Queue;
import java.awt.event.KeyEvent;

public class EcouteDirectionSerpent implements KeyListener{

   // constructeur
   public EcouteDirectionSerpent(Queue<Direction> directionQueue) {
      this.file_de_directions = directionQueue;
   }

   // Les directions demandées par l'utilisateur sont stockées dans une file
   private Queue<Direction> file_de_directions;

   // getter de file_de_directions
   public Queue<Direction> getFile_de_directions(){
      return this.file_de_directions;
   }


   @Override
   public void keyPressed(KeyEvent e) {
      // on récupère la touche du clavier
      int touche = e.getKeyCode();
      // on vérifie si la touche du clavier est une flèche directionnelle
      if (touche == KeyEvent.VK_UP) {
         file_de_directions.add(Direction.NORD);
      }
      if (touche == KeyEvent.VK_DOWN) {
         file_de_directions.add(Direction.SUD);
      }
      if (touche == KeyEvent.VK_LEFT) {
         file_de_directions.add(Direction.OUEST);
      }
      if (touche == KeyEvent.VK_RIGHT) {
         file_de_directions.add(Direction.EST);
      }
   }      


   @Override
   public void keyReleased(KeyEvent e) {

   }

   @Override
   public void keyTyped(KeyEvent e) {
      
   }
}