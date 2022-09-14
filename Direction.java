/**
 * La classe <code>Direction</code> est utilisÃ©e pour signifier une orientation possible
 * parmi les quatre points cardinaux.
 *  
 * @version 1.1
 * @author Luc Hernandez
 */
public class Direction {

   /**
    * Constante pointant vers le nord (c'est Ã  dire vers le haut de l'Ã©cran).
    */
   public static final Direction NORD = new Direction(+0, -1);
   
   /**
    * Constante pointant vers le sud (c'est Ã  dire vers le bas de l'Ã©cran).
    */
   public static final Direction SUD = new Direction(+0, +1);
   
   /**
    * Constante pointant vers l'est (c'est Ã  dire vers la droite de l'Ã©cran).
    */
   public static final Direction EST = new Direction(+1, +0);
   
   /**
    * Constante pointant vers l'ouest (c'est Ã  dire vers la gauche de l'Ã©cran).
    */
   public static final Direction OUEST = new Direction(-1, +0);
   
   /**
    * Composante horizontale de la direction (-1, 0 ou 1).
    */
   private int decalageX;
   
   /**
    * Composante verticale de la direction (-1, 0 ou 1).
    */
   private int decalageY;
   
   /**
    * Constructeur uniquement destinÃ© Ã  la crÃ©ation des constantes publiques.
    *
    * @param x l'abcisse (-1, 0 ou 1)
    * @param y l'ordonnÃ©e (-1, 0 ou 1)
    */
   private Direction(int x, int y) {
     this.decalageX = x;
     this.decalageY = y;
   }
   
   /**
    * Renvoie la composante horizontale de la direction.
    *
    * @return la composante horizontale de la direction (-1, 0 ou 1)
    */
   public int getDecalageX() {
     return this.decalageX;
   }
   
   /**
    * Renvoie la composante verticale de la direction.
    *
    * @return la composante verticale de la direction (-1, 0 ou 1)
    */
   public int getDecalageY() {
     return this.decalageY;
   }
   
   /**
    * Renvoie la direction produite par un quart de tour dans le sens horaire.
    *
    * @return la nouvelle direction
    */
   public Direction quartDeTour() {
     if (this == Direction.NORD)
       return Direction.EST;
     else if (this == Direction.EST)
       return Direction.SUD;
     else if (this == Direction.SUD)
       return Direction.OUEST;
     else // if (this == Direction.OUEST)
       return Direction.NORD;
   }
 
   /**
    * Renvoie la direction produite par un quart de tour dans le sens anti-horaire.
    *
    * @return la nouvelle direction
    */
   public Direction quartDeTourAnti() {
     if (this == Direction.NORD)
       return Direction.OUEST;
     else if (this == Direction.EST)
       return Direction.NORD;
     else if (this == Direction.SUD)
       return Direction.EST;
     else // if (this == Direction.OUEST)
       return Direction.SUD;
   }
 
 }