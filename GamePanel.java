import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Point;
import java.lang.Thread;

public class GamePanel {
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
   Direction direction = Direction.EST;
   // délai entre chaque déplacement du serpent
   public static int DELAY = 75;
   // timer qui permet de déplacer le serpent
   public static Timer timer;
   // booléen qui permet de savoir si le jeu est en cours ou non
   public boolean running = false;
   // classe qui permet de déplacer le serpent
   public int bodyParts = 6;
   // pommme mangée
   public int applesEaten = 0;
   // score a atteindre
   public static int applesToEat = 10;
   // le score actuel
   public int score = 0;
   public static Random rand;
   // on crée une variable pour contenir les coordonnées des cases
   public static int xCoordCase = 0;
   public static int yCoordCase = 0;
   // file Queue<E> qui contient les coordonnées des cases du serpent avec Point(x,y)
   public static Queue<Point> coordSerp = new LinkedList<Point>();


   // <--------------- Getter et setter --------------->

   // on récupère les coordonnées du serpent
   public static Queue<Point> getCoordSerp() {
      return coordSerp;
   }
   // on récupère les coordonnées de la tête du serpent
   public static Point getCoordTete() {
      return coordSerp.peek();
   }
   // on récupère les coordonnées de la pomme
   public static Point getCoordPomme() {
      return new Point(xCoordCase, yCoordCase);
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
      xCoordCase = (int) coordPomme.getX();
      yCoordCase = (int) coordPomme.getY();
   }
   // on créer un setter pour les coordonnées de la tête du serpent
   public static void setCoordTete(Point coordTete) {
      ((LinkedList<Point>) coordSerp).addFirst(coordTete);
   }
   // getter et setter pour la direction du serpent
   public Direction getDirection() {
      return direction;
   }
   public void setDirection(Direction direction) {
      this.direction = direction;
   }

   // <--------------------------------------------------------->


   // <----------- On crée la fenêtre de jeu ----------->

   // méthode qui permet de créer une grille de jeu
   protected void grille() {
      fenetre = new JFrame("Snake");
      panneau = new JPanel();
      panneau.setLayout(new GridLayout(25, 25, 1, 1));
      // méthode qui permet de redessiner la grille de jeu avec GridLayout
      // on crée une bordure autour de la grille de jeu
      panneau.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
      // on crée une boucle qui permet de créer 25 lignes et 25 colonnes
      for (int i = 0; i < 25; i++) {
         for (int j = 0; j < 25; j++) {
            // on créee un JLabel pour chaque case
            JLabel caseGrille = new JLabel();
            xCoordCase = i;
            yCoordCase = j;
            caseGrille.setOpaque(true);
            caseGrille.setPreferredSize(new Dimension(25, 25));
            // on donne une couleur de fond à chaque case
            caseGrille.setBackground(Color.BLACK);
            // on ajoute le JLabel au panneau
            panneau.add(caseGrille);
         }
      }
      /** // on centre le panneau au milieu de la fenêtre avec GridBagConstraint
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.gridwidth = 1;
      gbc.gridheight = 1;
      gbc.weightx = 1;
      gbc.weighty = 1;
      gbc.anchor = GridBagConstraints.CENTER;

      */

      // <---------- Pomme ---------->
      // on crée une pomme
      nouvellePomme(panneau);

      // <---------- Serpent ---------->
      creerSerpent(panneau);

      
      // on ajoute le serpent sur la grille de jeu avec notre méthode creerSerpent()
      //creerSerpent(panneau);

      // on ajoute le panneau à la fenêtre
      fenetre.add(panneau);
      fenetre.setSize(WIDTH, HEIGHT);
      fenetre.pack();
      fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      fenetre.setVisible(true);
   }

   // <----------------------------------------------------->


   // méthode qui créer le serpent avec un JLabel
   public void creerSerpent(JPanel panneau) {
      // on prend 6 JLabel existant au milieu du panneau et on les ajoute à la file
      for (int i = 0; i < PommeManger; i++) {
         // on récupère les coordonnées du JLabel au centre du panneau
         xCoordCase = 12;
         yCoordCase = 12;
         // on ajoute les coordonnées du JLabel au centre du panneau à la file
         coordSerp.add(new Point(xCoordCase, yCoordCase));
         // on récupère le JLabel au centre du panneau
         JLabel caseGrille = (JLabel) panneau.getComponent(12 * 25 + 12);
         // on change la couleur du JLabel au centre du panneau 6 fois en partant du centre
         for(int j = 0; j < PommeManger; j++) {
            caseGrille.setBackground(Color.GREEN);
            xCoordCase++;
            coordSerp.add(new Point(xCoordCase, yCoordCase));
            caseGrille = (JLabel) panneau.getComponent(xCoordCase * 25 + yCoordCase);

            // on écoute les touches du clavier pour déplacer le serpent avec notre méthode ecouteDirectionSerpent()
            ecouteDirectionSerpent();
         }

      }
   }
   

   // méthode qui permet de créer une pomme dans un JLabel de la grille de jeu aléatoirement
   public void nouvellePomme(JPanel panneau) {
      rand = new Random();
      // on ajoute une pomme aléatoire sur la grille de jeu
      appleX = rand.nextInt(xCoordCase);
      appleY = rand.nextInt(yCoordCase);
      // on change la couleur de la case qui contient la pomme
      panneau.getComponent(appleX + appleY * xCoordCase).setBackground(Color.RED);
   }


   // <----------- On s'occupe ici des fleches directionnelles et de la direction du serpent ----------->


   // méthode qui écoute les flèches directionnelles du clavier avec notre classe Direction
   public int ecouteDirectionSerpent() {
      int key = 0;
      if ((key == KeyEvent.VK_LEFT) && direction != Direction.EST) {
         setDirection(Direction.EST);
      }
      if ((key == KeyEvent.VK_RIGHT) && direction != Direction.OUEST) {
         setDirection(Direction.OUEST);
      }
      if ((key == KeyEvent.VK_UP) && direction != Direction.SUD) {
         setDirection(Direction.SUD);
      }
      if ((key == KeyEvent.VK_DOWN) && direction != Direction.NORD) {
         setDirection(Direction.NORD);
      }
      // on renvoie key
      return key;
   }


   // méthode qui permet de faire avancer le serpent en fonction de la direction
   public void directionSerpent(JPanel panneau) {
      // on récupère la tête du serpent
      Point coordTete = ((LinkedList<Point>) coordSerp).getFirst();
      // on récupère la queue du serpent
      Point coordQueue = ((LinkedList<Point>) coordSerp).getLast();
      // on récupère le JLabel de la tête du serpent
      JLabel caseGrille = (JLabel) panneau.getComponent(coordTete.x + coordTete.y * xCoordCase);
      // on récupère le JLabel de la queue du serpent
      JLabel caseGrilleQueue = (JLabel) panneau.getComponent(coordQueue.x + coordQueue.y * xCoordCase);
      // on change la couleur du JLabel de la tête du serpent
      caseGrille.setBackground(Color.GREEN);
      // on change la couleur du JLabel de la queue du serpent
      caseGrilleQueue.setBackground(Color.BLACK);
      // on récupère la direction du serpent avec notre méthode getDirection()
      Direction direction = getDirection();
      // on fait avancer le serpent en fonction de la direction
      if(direction == Direction.EST) {
         coordTete.x--;
      }
      if(direction == Direction.OUEST) {
         coordTete.x++;
      }
      if(direction == Direction.SUD) {
         coordTete.y--;
      }
      if(direction == Direction.NORD) {
         coordTete.y++;
      }
      // on ajoute la tête du serpent à la file
      ((LinkedList<Point>) coordSerp).addFirst(coordTete);
      // on supprime la queue du serpent à la file
      ((LinkedList<Point>) coordSerp).removeLast();
   }


   // <-------------------------------------------------------------------------------------------------------------------------------------------->



   // <----------------------- On s'occupe ici de la collision du serpent avec les murs et si il a manger une pomme ----------------------->

   // méthode qui permet de vérifier si le serpent a mangé une pomme
   public void mangerPomme() {
      // on vérifie si le serpent a mangé une pomme
      if (((LinkedList<Point>) coordSerp).getFirst().equals(new Point(appleX, appleY))) {
         // on ajoute une case au serpent
         bodyParts++;
         // on génère une nouvelle pomme
         nouvellePomme(panneau);
      }
   }

   // méthode qui permet de vérifier si le serpent a touché un mur ou c'est touché lui-même
   public void verifierCollision() {
      // on vérifie si le serpent a touché un mur
      if (((LinkedList<Point>) coordSerp).getFirst().x < 0 || ((LinkedList<Point>) coordSerp).getFirst().x > xCoordCase || ((LinkedList<Point>) coordSerp).getFirst().y < 0 || ((LinkedList<Point>) coordSerp).getFirst().y > yCoordCase) {
         // on arrête le jeu
         stopGame();
      }
      // on vérifie si le serpent s'est touché lui-même
      if (((LinkedList<Point>) coordSerp).contains(((LinkedList<Point>) coordSerp).getFirst())) {
         // on arrête le jeu
         stopGame();
      }
   }


   // <---------------------------------------------------------------------------------------------->



   // <----------- On s'occupe ici de l'affichage du score et des conditions de victoire ----------->

   // méthode qui permet de mettre à jour le score
   public void updateScore() {
      // on met à jour le score
      score = bodyParts - 6;
      JLabel scoreLabel = new JLabel("Score : " + score);
      // on affiche le score
      scoreLabel.setText("Score : " + score);
   }

   // méthode qui calcule la condition de victoire
   public void checkVictory(){
      // si le nombre de pommes mangées est égal au nombre de pommes à manger
      if(applesEaten == applesToEat){
         // on arrête le jeu
         stopGame();
      }
   }

   // méthode qui calcule la condition de défaite
   public void checkDefeat(){
      // si le nombre de pommes mangées est inférieur au nombre de pommes à manger
      if(applesEaten < applesToEat){
         // on arrête le jeu
         stopGame();
      }
   }

   // méthode qui calcule le score
   public void calculateScore(){
      // on calcule le score
      score = applesEaten * 10;
   }

   // méthode qui permet de commencer le jeu
   public void startGame(){
      grille();
   }

   // méthode qui permet d'arrêter le jeu
   public void stopGame(){
      // on arrête le timer
      timer.cancel();
      // on ferme la fenêtre
      System.exit(0);
   }

   // méthode qui permet de savoir si le jeu est en cours
   public boolean isRunning(){
      return running;
   }

   // <----------------------------------------------->

}
