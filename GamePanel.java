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
import java.lang.Thread;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;


public class GamePanel extends JPanel{
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
   // classe qui permet de déplacer le serpent
   public int bodyParts = 6;
   // pommme mangée
   public int applesEaten = 0;
   // score a atteindre
   public static int applesToEat = 10;
   // le score actuel
   public int score = 0;
   public Queue<Point> xCoordSerpent;
   public Queue<Point> yCoordSerpent;
   public static Random rand;
   // on crée une variable pour contenir les coordonnées des cases
   public static int xMilieuCase;
   public static int yMilieuCase;
   // file Queue<E> qui contient les coordonnées des cases du serpent avec Point(x,y)
   public static Queue<Point> coordSerp;
   // on crée une seconde file pour pouvoir déplacer le serpent
   public static Queue<Point> coordSerp2;
   // on crée une file pour les pommes
   public static Queue<Point> filePomme;
   // variable qui permet de connaitre la tete du serpent en fonction de coordSerp
   public static Point teteSerpent;
   public TimerTask tache;
   public JLabel caseGrille;
   public Run commencer;



   // constructeur de la classe GamePanel
   public GamePanel(){
      filePomme = new LinkedList<Point>();
      rand = new Random();
      fenetre = new JFrame("Snake");
      coordSerp = new LinkedList<Point>();
      coordSerp2 = new LinkedList<Point>();
      panneau = new JPanel();
      caseGrille = new JLabel();
      xCoordSerpent = new LinkedList<Point>();
      yCoordSerpent = new LinkedList<Point>();
      commencer = new Run();
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
      fenetre.setLayout(new GridLayout(WIDTH / TAILLE_CASE, HEIGHT / TAILLE_CASE));
      panneau.setLayout(new GridLayout(25, 25, 1, 1));
      // méthode qui permet de redessiner la grille de jeu avec GridLayout
      // on crée une bordure autour de la grille de jeu
      panneau.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
      // on crée une boucle qui permet de créer 25 lignes et 25 colonnes
      for (int i = 0; i < 25; i++) {
         for (int j = 0; j < 25; j++) {
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
      nouvellePomme(panneau);

      // <---------- Serpent ---------->
      creerSerpent(panneau);

      
      // on ajoute le serpent sur la grille de jeu avec notre méthode creerSerpent()
      //creerSerpent(panneau);

      // on ajoute le panneau à la fenêtre
      fenetre.setSize(WIDTH, HEIGHT);
      fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      fenetre.setVisible(true);
   }

   // <----------------------------------------------------->


   // méthode qui créer le serpent avec un JLabel
   public void creerSerpent(JPanel panneau) {
      xMilieuCase = 12;
      yMilieuCase = 12;
      // on ajoute les coordonnées du JLabel au centre du panneau à la file
      coordSerp.add(new Point(xMilieuCase, yMilieuCase));
      // on récupère le JLabel au centre du panneau
      caseGrille = (JLabel) panneau.getComponent((xMilieuCase * 25) + yMilieuCase);
      // on change la couleur du JLabel au centre du panneau 6 fois en partant du centre tout en ajoutant les coordonnées du JLabel à la file coordSerp
      for(int j = 0; j < PommeManger; j++) {
         caseGrille.setBackground(Color.GREEN);
         coordSerp.add(new Point(caseGrille.getX(), caseGrille.getY()));
         caseGrille = (JLabel) panneau.getComponent((xMilieuCase * 25) + yMilieuCase);
      }
   }
   

   // méthode qui permet de créer une pomme dans un JLabel de la grille de jeu aléatoirement
   public void nouvellePomme(JPanel panneau) {
      // on ajoute une pomme aléatoire sur la grille de jeu
      appleX = rand.nextInt(25);
      appleY = rand.nextInt(25);
      filePomme.add(new Point(appleX, appleY));
      // on récupère un Jlbael aléatoirement dans la grille de jeu
      caseGrille = (JLabel) panneau.getComponent((appleX * 25) + appleY);
      // on verifie que la pomme ne se trouve pas sur le serpent et sur une case déjà occupée
      if (caseGrille.getBackground() == Color.GREEN || caseGrille.getBackground() == Color.RED) {
         nouvellePomme(panneau);
      } else {
         // on change la couleur du JLabel aléatoire en rouge
         caseGrille.setBackground(Color.RED);
      }
   }


   // <----------- On s'occupe ici des fleches directionnelles et de la direction du serpent ----------->

   EcouteDirectionSerpent ecouteDirectionSerpent = new EcouteDirectionSerpent();
   



   // méthode qui permet de faire avancer le serpent en fonction de la file_de_directions
   public void avancerSerpent(){
      // on recupere la file de directions
      Queue<Direction> fileDeDirections =  ecouteDirectionSerpent.getFile_de_directions();
      // On verifie que la file de directions n'est pas vide
      if(!fileDeDirections.isEmpty()){
         // on verifie la direction du serpent et on avance en conséquence
         if(fileDeDirections.peek() == Direction.NORD){
            // on avance le serpent le JLabel au nord du point d'origine en lui ajoutant 1 à z et en retirant ce qui est à 1 × z
            caseGrille =  (JLabel) panneau.getComponent((int) (((((LinkedList<Point>) coordSerp).get(coordSerp.size() - 1).getX() * (HEIGHT / TAILLE_CASE))  - (HEIGHT / TAILLE_CASE)) + ((LinkedList<Point>) coordSerp).get(coordSerp.size()  - 1).getY()));
            // on verifie que le JLabel au nord du point d'origine ne rentre pas dans une case déjà contenant le serpent
            if(caseGrille.getBackground() != Color.GREEN){
               caseGrille.setBackground(Color.GREEN);
               coordSerp.add(new Point(caseGrille.getX(), caseGrille.getY()));
            }
         }
         if(fileDeDirections.peek() == Direction.SUD){
            caseGrille = (JLabel) panneau.getComponent((int) (((((LinkedList<Point>) coordSerp).get(coordSerp.size() - 1).getX() * (HEIGHT / TAILLE_CASE)) + (HEIGHT / TAILLE_CASE)) + ((LinkedList<Point>) coordSerp).get(coordSerp.size()  - 1).getY()));
            if(caseGrille.getBackground() != Color.GREEN){
               caseGrille.setBackground(Color.GREEN);
               coordSerp.add(new Point(caseGrille.getX(), caseGrille.getY()));
            }
         }
         if(fileDeDirections.peek() == Direction.OUEST){
            caseGrille = (JLabel) panneau.getComponent((int) ((((LinkedList<Point>) coordSerp).get(coordSerp.size() - 1).getX() * (HEIGHT / TAILLE_CASE))  + ((LinkedList<Point>) coordSerp).get(coordSerp.size() - 1).getY()) - 1);
            if(caseGrille.getBackground() != Color.GREEN){
               caseGrille.setBackground(Color.GREEN);
               coordSerp.add(new Point(caseGrille.getX(), caseGrille.getY()));
            }
         }
         if(fileDeDirections.peek() == Direction.EST){
            caseGrille = (JLabel) panneau.getComponent((int) ((((LinkedList<Point>) coordSerp).get(coordSerp.size() - 1).getX() * (HEIGHT / TAILLE_CASE))  + ((LinkedList<Point>) coordSerp).get(coordSerp.size() - 1).getY()) + 1);
            if(caseGrille.getBackground() != Color.GREEN){
               caseGrille.setBackground(Color.GREEN);
               coordSerp.add(new Point(caseGrille.getX(), caseGrille.getY()));
            }
         }
      }else{
         // si la file de direction est vide alors le serpent continue sur son axe Y en avançant tout droit
         caseGrille =  (JLabel) panneau.getComponent((int) (((((LinkedList<Point>) coordSerp).get(coordSerp.size() - 1).getX() * (HEIGHT / TAILLE_CASE)) + (HEIGHT / TAILLE_CASE)) + ((LinkedList<Point>) coordSerp).get(coordSerp.size()  - 1).getY()));
         if(caseGrille.getBackground() != Color.GREEN){
            caseGrille.setBackground(Color.GREEN);
            coordSerp.add(new Point(caseGrille.getX(), caseGrille.getY()));
         }
      }
   }
   
   // <-------------------------------------------------------------------------------------------------------------------------------------------->


   // <----------------------- On s'occupe ici de la collision du serpent avec les murs et si il a manger une pomme ----------------------->

   // méthode qui permet de vérifier si le serpent a mangé une pomme
   public void verifierMangerPomme() {
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
      if (((LinkedList<Point>) coordSerp).getFirst().x < 0 || ((LinkedList<Point>) coordSerp).getFirst().x > xMilieuCase || ((LinkedList<Point>) coordSerp).getFirst().y < 0 || ((LinkedList<Point>) coordSerp).getFirst().y > yMilieuCase) {
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
      JLabel scoreLabel = new JLabel();
      // on affiche le score
      scoreLabel.setText("Score : " + score);
   }

   // méthode qui lance le jeu
   public void startGame(){  
      grille();
      timer = new Timer();
      setRunning(true);
      commencer.run();
      // on implique le timer a se niveau
      timer.scheduleAtFixedRate(tache, 0, 1000 / 10);
   }


   public void repaint() {
      // on redessine le panneau
      panneau.repaint();
   }
}