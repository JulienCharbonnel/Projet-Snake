import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
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
import java.awt.event.KeyListener;


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
   public Direction direction;
   // délai entre chaque déplacement du serpent
   public static int DELAY = 75;
   // timer qui permet de déplacer le serpent
   public static Timer timer;
   // booléen qui permet de savoir si le jeu est en cours ou non
   public boolean running = false;
   public int numeroCase = 0;
   // classe qui permet de déplacer le serpent
   public int bodyParts = 6;
   // pommme mangée
   public int applesEaten = 0;
   // score a atteindre
   public static int applesToEat = 10;
   // le score actuel
   public int score = 0;
   public static Queue<Point> xCoordSerpent = new LinkedList<Point>();
   public static Queue<Point> yCoordSerpent = new LinkedList<Point>();
   public static Random rand;
   // on crée une variable pour contenir les coordonnées des cases
   public static int xCoordCase = 0;
   public static int yCoordCase = 0;
   // file Queue<E> qui contient les coordonnées des cases du serpent avec Point(x,y)
   public static Queue<Point> coordSerp = new LinkedList<Point>();
   // variable qui permet de connaitre la tete du serpent en fonction de coordSerp
   public static Point teteSerpent = coordSerp.peek();
   public JLabel caseGrille;
   public TimerTask tache;


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

   // <--------------------------------------------------------->


   // <----------- On crée la fenêtre de jeu ----------->

   // méthode qui permet de créer une grille de jeu
   protected void grille() {
      caseGrille = new JLabel();
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
      caseGrille = new JLabel();
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
      // on écoute les touches du clavier
      fenetre.addKeyListener(new KeyListener() {
         @Override
         public void keyPressed(KeyEvent e) {
            // on récupère la touche du clavier
            int touche = e.getKeyCode();
            // on vérifie si la touche du clavier est une flèche directionnelle
            if (touche == KeyEvent.VK_UP) {
               // on change la direction du serpent
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
         @Override
         public void keyReleased(KeyEvent e) {
         }
         @Override
         public void keyTyped(KeyEvent e) {
         }
      });
      return 0;
   }



   // méthode qui permet de faire avancer le serpent en fonction de la direction
   public void avancerSerpent(JPanel panneau) { 
      caseGrille = new JLabel();  
      // on récupère les coordonnées de la tête du serpent
      Point coordTete = getCoordTete();
      // en fonction de la direction du serpent on fait avancer le serpent dans la grille jusqu'à ce que la direction change
      while(direction == Direction.NORD || direction != Direction.SUD || direction != Direction.OUEST || direction != Direction.EST) {
         // on récupère le JLabel de la case au dessus de la tête du serpent 
         caseGrille = (JLabel) panneau.getComponent(coordTete.x + (coordTete.y - 1) * xCoordCase);
         // on ajoute les coordonnées de la case au dessus de la tête du serpent à la file
         coordSerp.add(new Point(coordTete.x, coordTete.y - 1));
         // on change la couleur du JLabel de la case au dessus de la tête du serpent
         caseGrille.setBackground(Color.GREEN);
         // on récupère les coordonnées de la tête du serpent
         coordTete = getCoordTete();
         // on supprime la dernière case du serpent
         coordSerp.remove();
         while(direction == Direction.SUD || direction != Direction.NORD || direction != Direction.OUEST || direction != Direction.EST) {
            caseGrille = (JLabel) panneau.getComponent(coordTete.x + (coordTete.y + 1) * xCoordCase);
            caseGrille.setBackground(Color.GREEN);
            coordSerp.add(new Point(coordTete.x, coordTete.y + 1));
            coordTete = getCoordTete();
            coordSerp.remove();
            while(direction == Direction.OUEST || direction != Direction.NORD || direction != Direction.SUD || direction != Direction.EST) {
               caseGrille = (JLabel) panneau.getComponent(coordTete.x - 1 + coordTete.y * xCoordCase);
               caseGrille.setBackground(Color.GREEN);
               coordSerp.add(new Point(coordTete.x - 1, coordTete.y));
               coordTete = getCoordTete();
               coordSerp.remove();
               while(direction == Direction.EST || direction != Direction.NORD || direction != Direction.SUD || direction != Direction.OUEST) {
                  caseGrille = (JLabel) panneau.getComponent(coordTete.x + 1 + coordTete.y * xCoordCase);
                  caseGrille.setBackground(Color.GREEN);
                  coordSerp.add(new Point(coordTete.x + 1, coordTete.y));
                  coordTete = getCoordTete();
                  coordSerp.remove();
               }
            }
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

   // méthode qui calcule la condition de victoire
   public void checkVictory(){
      // si le nombre de pommes mangées est égal au nombre de pommes à manger
      if(applesEaten == applesToEat){
         // on arrête le jeu
         victoire();
      }
   }

   // méthode qui affiche un message de victoire et qui arrête le jeu
   public void victoire() {
      // on arrête le jeu
      stopGame();
      // on affiche un message de victoire
      JOptionPane.showMessageDialog(null, "Vous avez gagné !");
   }

   // méthode qui permet d'arrêter le jeu
   public void stopGame(){
      // on arrête le jeu
      running = false;
      // on arrête le timer
      timer.cancel();
      // on affiche le score
      JOptionPane.showMessageDialog(null, "Votre score est de " + score + " points");
   }

   // méthode qui calcule le score
   public void calculateScore(){
      // on calcule le score
      score = applesEaten * 10;
   }

   // méthode qui permet de mettre à jour le score
   public void updateScore() {
      // on met à jour le score
      score = bodyParts - 6;
      JLabel scoreLabel = new JLabel("Score : " + score);
      // on affiche le score
      scoreLabel.setText("Score : " + score);
   }

   // méthode qui lance le jeu
   public void run(){  
      // on lance le timer
      timer.schedule(tache, 0, 100); {
         // on lance le jeu
         setRunning(true);
         if(running == true){
            grille();
            ecouteDirectionSerpent();
            avancerSerpent(panneau);
            mangerPomme();
            verifierCollision();
            checkVictory();
            calculateScore();
            updateScore();
         }
         else{
            // on arrête le jeu
            stopGame();
         }
      }
   }

   // <----------------------------------------------->

}