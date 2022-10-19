import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class Accueil extends JFrame{

    // méthode qui affiche la fenêtre d'accueil du jeu et qui lance le jeu
    public void afficherAccueil(){
        // création de la fenêtre d'accueil
        JFrame fenetreAccueil = new JFrame();
        JPanel panel = new JPanel(new GridBagLayout());
        fenetreAccueil.setTitle("Snake");
        fenetreAccueil.setSize(1000, 1000);
        fenetreAccueil.setLocationRelativeTo(null);
        fenetreAccueil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetreAccueil.setVisible(true);

        // on crée un label qui affiche le titre du jeu
        JLabel titre = new JLabel("Snake");
        titre.setFont(new Font("Arial", Font.BOLD, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        // on affiche le titre au centre de la fenêtre en haut de la fenêtre
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.weightx = 1;
        panel.add(titre, gbc);


        // on crée un bouton pour lancer le jeu
        JButton boutonLancer = new JButton("Lancer le jeu");
        // on le place en dessous du titre
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.weightx = 1;
        boutonLancer.setPreferredSize(new java.awt.Dimension(300, 300));        
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);
        panel.add(boutonLancer, gbc);
    
        // on crée un bouton pour quitter le jeu
        JButton boutonQuitter = new JButton("Quitter le jeu");
        // on le place en dessous du bouton lancer le jeu
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.weightx = 1;
        // on aggrandit un peu les boutons
        boutonQuitter.setPreferredSize(new java.awt.Dimension(300, 300));
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);
        panel.add(boutonQuitter, gbc);

        fenetreAccueil.add(panel);

        // si on clique sur le bouton pour lancer le jeu, on lance le jeu
        boutonLancer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // on ferme la fenêtre d'accueil
                fenetreAccueil.dispose();
                // on lance le jeu
                GamePanel gamePanel = new GamePanel();
                gamePanel.startGame();
            }
        });

        // si on clique sur le bouton pour quitter le jeu, on quitte le jeu
        boutonQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // on ferme la fenêtre d'accueil
                fenetreAccueil.dispose();
                // on quitte le jeu
                System.exit(0);
            }
        });        
    }
}
