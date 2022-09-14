import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
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

public class GamePanel {
   // on crée une variable de type JFrame
   public static JFrame fenetre;
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


   // <----------- On crée la fenêtre de jeu ----------->

   // méthode qui permet de créer une grille de jeu
   protected void grille() {
      fenetre = new JFrame("Snake");
      // méthode qui permet de redessiner la grille de jeu avec GridLayout
      JPanel panneau = new JPanel(new GridLayout(25, 25, 1, 1));
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

      rand = new Random();
      // on ajoute une pomme aléatoire sur la grille de jeu
      appleX = rand.nextInt(xCoordCase);
      appleY = rand.nextInt(yCoordCase);
      // on change la couleur de la case qui contient la pomme
      panneau.getComponent(appleX + appleY * xCoordCase).setBackground(Color.RED);

      // <---------- Serpent ---------->
      
      // on ajoute le serpent sur la grille de jeu avec notre méthode creerSerpent()
      creerSerpent(panneau);

      fenetre.add(panneau);
      fenetre.setSize(WIDTH, HEIGHT);
      fenetre.pack();
      fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      fenetre.setVisible(true);
   }

   // <----------------------------------------------------->


   // méthode qui créer le serpent avec un JLabel
   public void creerSerpent(JPanel panneau) {
      // on crée une boucle qui permet de créer le serpent
      for (int i = 0; i < bodyParts; i++) {
         // on positionne le serpent aléatoirement sur la grille de jeu
         x[i] = rand.nextInt(xCoordCase);
         y[i] = rand.nextInt(yCoordCase);
         // on ajoute les coordonnées du serpent dans la file
         coordSerp.add(new Point(x[i], y[i]));
         // on change la couleur de la case qui contient le serpent
         panneau.getComponent(x[i] + y[i] * xCoordCase).setBackground(Color.GREEN);
      }
   }

   // méthode qui permet de créer une pomme
   public void newApple(){
      // création d'un nombre aléatoire
      Random random = new Random();
      // coordonnées de la pomme
      appleX = random.nextInt((int) (WIDTH / TAILLE_CASE)) * TAILLE_CASE;
      appleY = random.nextInt((int) (HEIGHT / TAILLE_CASE)) * TAILLE_CASE;   
      
      // on génère une nouvelle pomme si elle est sur le serpent
      for (int i = bodyParts; i > 0; i--) {
         if ((x[i] == appleX) && (y[i] == appleY)) {
            newApple();
         }
      }
   }


   // <----------- On s'occupe ici des fleches directionnelles et de la direction du serpent ----------->


   // méthode qui écoute les flèches directionnelles du clavier avec notre classe Direction
   public int ecouteDirectionSerpent(KeyEvent e) {
      int key = e.getKeyCode();
      if ((key == KeyEvent.VK_LEFT) && direction != Direction.EST) {
         direction = Direction.OUEST;
      }
      if ((key == KeyEvent.VK_RIGHT) && direction != Direction.OUEST) {
         direction = Direction.EST;
      }
      if ((key == KeyEvent.VK_UP) && direction != Direction.SUD) {
         direction = Direction.NORD;
      }
      if ((key == KeyEvent.VK_DOWN) && direction != Direction.NORD) {
         direction = Direction.SUD;
      }
      // on renvoie key
      return key;
   }


   // méthode qui permet de faire bouger le serpent
   public void directionSerpent() {
      // on vérifie si le jeu est en cours
      if (running) {
         // on vérifie si la direction du serpent est vers le nord
         if (direction == Direction.NORD) {
            // on déplace le serpent vers le nord
            ((LinkedList<Point>) coordSerp).addFirst(new Point(((LinkedList<Point>) coordSerp).getFirst().x, ((LinkedList<Point>) coordSerp).getFirst().y - 1));
            // on supprime la queue du serpent
            ((LinkedList<Point>) coordSerp).removeLast();
         }
         // on vérifie si la direction du serpent est vers le sud
         if (direction == Direction.SUD) {
            // on déplace le serpent vers le sud
            ((LinkedList<Point>) coordSerp).addFirst(new Point(((LinkedList<Point>) coordSerp).getFirst().x, ((LinkedList<Point>) coordSerp).getFirst().y + 1));
            // on supprime la queue du serpent
            ((LinkedList<Point>) coordSerp).removeLast();
         }
         // on vérifie si la direction du serpent est vers l'est
         if (direction == Direction.EST) {
            // on déplace le serpent vers l'est
            ((LinkedList<Point>) coordSerp).addFirst(new Point(((LinkedList<Point>) coordSerp).getFirst().x + 1, ((LinkedList<Point>) coordSerp).getFirst().y));
            // on supprime la queue du serpent
            ((LinkedList<Point>) coordSerp).removeLast();
         }
         // on vérifie si la direction du serpent est vers l'ouest
         if (direction == Direction.OUEST) {
            // on déplace le serpent vers l'ouest
            ((LinkedList<Point>) coordSerp).addFirst(new Point(((LinkedList<Point>) coordSerp).getFirst().x - 1, ((LinkedList<Point>) coordSerp).getFirst().y));
            // on supprime la queue du serpent
            ((LinkedList<Point>) coordSerp).removeLast();
         }
      }
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
         newApple();
      }
   }

   // méthode qui permet de vérifier si le serpent a touché un mur ou c'est touché lui-même
   public void verifierCollision() {
      // on vérifie si le serpent a touché un mur
      if (((LinkedList<Point>) coordSerp).getFirst().x < 0) {
         running = false;
      }
      if (((LinkedList<Point>) coordSerp).getFirst().x > xCoordCase) {
         running = false;
      }
      if (((LinkedList<Point>) coordSerp).getFirst().y < 0) {
         running = false;
      }
      if (((LinkedList<Point>) coordSerp).getFirst().y > yCoordCase) {
         running = false;
      }
      // on vérifie si le serpent s'est touché lui-même
      if (coordSerp.size() > bodyParts) {
         for (int i = bodyParts; i < coordSerp.size(); i++) {
            if (((LinkedList<Point>) coordSerp).getFirst().equals(((LinkedList<Point>) coordSerp).get(i))) {
               running = false;
            }
         }
      }
      // si le jeu n'est pas en cours
      if (!running) {
         // on affiche un message
         JOptionPane.showMessageDialog(null, "Game Over");
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
         running = false;
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
      // on crée la grille de jeu
      grille();      
      // on crée une nouvelle pomme
      newApple();
      // on lance le timer
      timer.scheduleAtFixedRate(new TimerTask(){
         @Override
         public void run(){
            // on vérifie si le jeu est en cours
            if(running){


            }
         }
      }, 0, 100);

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
