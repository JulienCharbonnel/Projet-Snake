import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Point;


public class GamePanel{
   // on crée une variable de type JFrame
   public static JFrame fenetre;
   public JPanel panneau;
   // taille de la grille de jeu
   public final static int WIDTH = 1900;
   public final static int HEIGHT = 1080;
   // taille d'une case
   public static final int TAILLE_CASE = 25;
   // nombre de cases
   public static final int NOMBRES_CASES = (WIDTH * HEIGHT) / TAILLE_CASE;
   // tableau qui contient les coordonnées de chaque case
   public static final int[] x = new int[NOMBRES_CASES];
   public static final int[] y = new int[NOMBRES_CASES];
   // nombre de pommes mangées
   public static int PommeManger = 6;
   // coordonnées de la pomme
   public int appleX;
   public int appleY;
   // direction du serpent
   public Direction direction;
   // délai entre chaque déplacement du serpent
   public static int DELAY = 175;
   // timer qui permet de déplacer le serpent
   public static Timer timer;
   // booléen qui permet de savoir si le jeu est en cours ou non
   public volatile boolean running;
   // classe qui permet de déplacer le serpent
   public int croissance;
   // pommme mangée
   public int applesEaten = 0;
   // le score actuel
   public int score;
   public static Random rand;
   // on crée une variable pour contenir les coordonnées des cases
   public static int xMilieuCase;
   public static int yMilieuCase;
   // file Queue<E> qui contient les coordonnées des cases du serpent avec Point(x,y)
   public static Queue<Point> coordSerp;
   // on crée une file pour les pommes
   public static Queue<Point> filePomme;
   // variable qui permet de connaitre la tete du serpent en fonction de coordSerp
   public static Point teteSerpent;
   public TimerTask tache;
   public JLabel caseGrille;
   public Run commencer;
   private EcouteDirectionSerpent ecouteDirectionSerpent;
   Queue<Direction> fileDeDirections;
   Queue<Direction> fileDeDirectionPrecedente;
   private Score scoreJoueur;

   // constructeur de la classe GamePanel
   public GamePanel(){
      filePomme = new LinkedList<Point>();
      rand = new Random();
      fenetre = new JFrame("Snake");
      coordSerp = new LinkedList<Point>();
      panneau = new JPanel();
      caseGrille = new JLabel();
      fileDeDirections = new LinkedList<Direction>();
      ecouteDirectionSerpent = new EcouteDirectionSerpent(fileDeDirections);
      fenetre.addKeyListener(ecouteDirectionSerpent);
      direction = Direction.NORD;
      croissance = 0;
      running = true;
      score = 0;
      scoreJoueur = new Score();
   }


   // <--------------- Getter et setter --------------->

   // on récupère les coordonnées du serpent
   public static Queue<Point> getCoordSerp() {
      return coordSerp;
   }
   // on récupère les coordonnées de la tête du serpent
   public static Point getCoordTete() {
      return teteSerpent;
   }
   // on récupère les coordonnées de la pomme
   public static Point getCoordPomme() {
      return new Point(xMilieuCase, yMilieuCase);
   }
   // on récupère les coordonnées de la queue du serpent
   public static Point getCoordQueue() {
      return ((LinkedList<Point>) coordSerp).peekLast();
   }
   // on créer un setter pour les coordonnées du serpent
   public static void setCoordSerp(Queue<Point> coordSerp) {
      GamePanel.coordSerp = coordSerp;
   }
   // on créer un setter pour les coordonnées de la pomme
   public static void setCoordPomme(Point coordPomme) {
      xMilieuCase = (int) coordPomme.getX();
      yMilieuCase = (int) coordPomme.getY();
   }
   // on créer un setter pour les coordonnées de la tête du serpent
   public static void setCoordTete(Point coordTete) {
      teteSerpent = coordTete;
   }
   // getter et setter pour la direction du serpent
   public Direction getDirection() {
      return direction;
   }
   public void setDirection(Direction direction) {
      this.direction = direction;
   }
   // méthode qui permet de lancer le running
   public void setRunning(boolean running) {
      this.running = running;
   }
   // méthode qui permet de récupérer le running
   public boolean getRunning() {
      return running;
   }

   // <--------------------------------------------------------->


   // <----------- On crée la fenêtre de jeu ----------->

   // méthode qui permet de créer une grille de jeu
   protected void grille() {
      panneau.setLayout(new GridLayout(TAILLE_CASE, TAILLE_CASE));
      // méthode qui permet de redessiner la grille de jeu avec GridLayout
      // on crée une bordure autour de la grille de jeu
      panneau.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
      // on crée une boucle qui permet de créer 25 lignes et 25 colonnes
      for (int i = 0; i < TAILLE_CASE; i++) {
         for (int j = 0; j < TAILLE_CASE; j++) {
            caseGrille = new JLabel();           
            caseGrille.setOpaque(true);
            caseGrille.setPreferredSize(new Dimension(25, 25));
            // on donne une couleur de fond à chaque case
            caseGrille.setBackground(Color.BLACK);
            // on ajoute le JLabel au panneau
            panneau.add(caseGrille);
         }
      }

      // <---------- Pomme ---------->
      // on crée une pomme
      nouvellePomme();

      // <---------- Serpent ---------->
      creerSerpent();

      
      // on ajoute le serpent sur la grille de jeu avec notre méthode creerSerpent()
      //creerSerpent(panneau);

      // on ajoute le panneau à la fenêtre
      fenetre.add(panneau);
      fenetre.setSize(WIDTH, HEIGHT);
      fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      fenetre.setVisible(true);
   }

   // <----------------------------------------------------->


   // méthode qui créer le serpent avec un JLabel
   public void creerSerpent() {
      xMilieuCase = 12;
      yMilieuCase = 12;
      // on change la couleur du JLabel au centre du panneau 6 fois en partant du centre tout en ajoutant les coordonnées du JLabel à la file coordSerp
      for(int j = 0; j < 6; j++) {
         caseGrille = (JLabel) panneau.getComponent(((yMilieuCase - j) * TAILLE_CASE + xMilieuCase));
         caseGrille.setBackground(Color.GREEN);
         teteSerpent = new Point(xMilieuCase , yMilieuCase - j);
         coordSerp.add(teteSerpent);
      }
   }
   

   // méthode qui permet de créer une pomme dans un JLabel de la grille de jeu aléatoirement
   public void nouvellePomme() {
      // on ajoute une pomme aléatoire sur la grille de jeu
      appleX = rand.nextInt(25);
      appleY = rand.nextInt(25);
      // on récupère un Jlbael aléatoirement dans la grille de jeu
      caseGrille = (JLabel) panneau.getComponent((appleY * 25) + appleX);
      // on verifie que la pomme ne se trouve pas sur le serpent et sur une case déjà occupée
      if (caseGrille.getBackground() == Color.GREEN || caseGrille.getBackground() == Color.RED) {
         nouvellePomme();
      } else {
         // on change la couleur du JLabel aléatoire en rouge
         filePomme.add(new Point(appleX, appleY));
         caseGrille.setBackground(Color.RED);
      }
   }


   // <----------- On s'occupe ici des fleches directionnelles et de la direction du serpent ----------->

   
   // méthode qui permet de vérifier si le serpent a mangé une pomme
   public void verifierMangerPomme() {
      // on vérifie si la tete du serpent est sur la pomme
      if (teteSerpent.getX() == appleX && teteSerpent.getY() == appleY) {
         // on ajoute une case au serpent
         croissance++;
         // on génère une nouvelle pomme
         nouvellePomme();
         int score = scoreJoueur.getScore();
         scoreJoueur.setScore(score + 1);
      }
   }

   // méthode qui permet de vérifier si le serpent a touché un mur
   public boolean verifierCollisionMur() {
      // on vérifie si le serpent a touché un mur
      if(teteSerpent.x < 0 || teteSerpent.x >= TAILLE_CASE ||
         teteSerpent.y < 0 || teteSerpent.y >= TAILLE_CASE){
         stopGame();
         return false;
      }
      return true;
   }

   public boolean verifierCollisionCoorp(){
      // on vérifie si le serpent s'est touché lui-même
      if (coordSerp.contains(teteSerpent)) {
         stopGame();
         return false;
      }
      return true;
   }

   

   // méthode qui permet de faire avancer le serpent en fonction de la file_de_directions
   public void avancerSerpent(){
      // On verifie que la file de directions n'est pas vide
      if(!fileDeDirections.isEmpty()){
         // on récupère la direction dans la file de direction
         Direction nouvelleDirection = fileDeDirections.poll();
         // on vérifie que la direction est différente de la direction opposée
         if(nouvelleDirection != this.direction.demiTour()){
            direction = nouvelleDirection;
         }
      }
      // on ajoute la tete du serpent à la file des coordonnées du serpent
      teteSerpent = new Point(teteSerpent.x + direction.getDecalageX(), teteSerpent.y + direction.getDecalageY());
      // on vérifie si le serpent s'est touché lui-même ou s'il a touché un mur
      if(verifierCollisionMur() && verifierCollisionCoorp()){
         verifierMangerPomme();
         coordSerp.add(teteSerpent);
         // on recupere le JLabel de la case juste devant la tête du serpent
         caseGrille = (JLabel) panneau.getComponent((teteSerpent.y * TAILLE_CASE) + teteSerpent.x);
         caseGrille.setBackground(Color.GREEN);
         if(croissance == 0){
            // on recupere la dernière case du serpent
            Point lastCase = coordSerp.remove();
            // on recupere le JLabel de la dernière case du serpent
            caseGrille = (JLabel) panneau.getComponent((lastCase.y * TAILLE_CASE) + lastCase.x);
            caseGrille.setBackground(Color.BLACK);
         }else{
            croissance--;
         }
      }  
   }
      
   // <-------------------------------------------------------------------------------------------------------------------------------------------->


   // <----------- On s'occupe ici de l'affichage du score et des conditions de victoire ----------->

   // méthode qui permet d'arrêter le jeu
   public void stopGame(){
      // on arrête le jeu
      running = false;
      // on arrête le timer
      timer.cancel();
      // on affiche une pop-up pour dire que le joueur a perdu
      JOptionPane.showMessageDialog(null, "Vous avez perdu !" + "\n" + "Votre score est de : " + scoreJoueur.getScore());
      relancerUneNouvellePartie();
   }

   // méthode qui permet de relancer une nouvelle partie
   public void relancerUneNouvellePartie(){
      // on demande au joueur s'il veut rejouer
      int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous rejouer ?", "Rejouer ?", JOptionPane.YES_NO_OPTION);
      // si le joueur veut rejouer
      if(reponse == JOptionPane.YES_OPTION){
         Accueil accueil = new Accueil();
         accueil.afficherAccueil();
         // on ferme la fenêtre de jeu
         fenetre.dispose();
      }else{
         // on ferme la fenêtre de jeu
         fenetre.dispose();
      }
   }


   // méthode qui lance le jeu
   public void startGame(){  
      grille();
      commencer = new Run(this);
      timer = new Timer();
      setRunning(true);
      timer.scheduleAtFixedRate(commencer, 0, 160);
   }

   public void raffraichir(){
      fenetre.repaint();
   }
}