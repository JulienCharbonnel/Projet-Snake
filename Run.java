import java.util.TimerTask;


public class Run extends TimerTask{

   public GamePanel gamePanel;

   // contsructeur run
   public Run(GamePanel jeu) {
      gamePanel = jeu;
   }

   @Override
   public void run() {
      boolean running = gamePanel.getRunning();
      // on vérifie si le jeu est en cours
      if(running){
         gamePanel.avancerSerpent();
         gamePanel.raffraichir();
      }
      else{
         gamePanel.stopGame();
      }
   }
   
}
