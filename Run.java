import java.util.TimerTask;

public class Run extends TimerTask{

   public static GamePanel gamePanel;


   @Override
   public void run() {
      boolean running = gamePanel.getRunning();
      // on vérifie si le jeu est en cours
      if(running){
         gamePanel.avancerSerpent();
         gamePanel.verifierMangerPomme();
         gamePanel.verifierCollision();
         gamePanel.checkVictory();
         gamePanel.calculateScore();
         gamePanel.updateScore();
      }
      else{
         gamePanel.stopGame();
      }

   }
   
}
