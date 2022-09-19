public class Snake {

   public static GamePanel grilleJeux;

   public static void main(String[] args){
      // On apelle notre Classe GamePanel
      grilleJeux = new GamePanel();
      // on lance le jeu
      grilleJeux.run();
   }
}