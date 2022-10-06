import java.util.TimerTask;

public class Run extends TimerTask{

   GamePanel gamePanel;

   

   @Override
   public void run() {
      // on vérifie si le jeu est en cours
      if(gamePanel.getRunning()){
            gamePanel.ecouteDirectionSerpent();
            gamePanel.avancerSerpent();
            gamePanel.verifierMangerPomme();
            gamePanel.verifierCollision();
            gamePanel.checkVictory();
            gamePanel.calculateScore();
            gamePanel.updateScore();
            gamePanel.repaint();
      }
      else{
         gamePanel.stopGame();
      }

   }
   
}
