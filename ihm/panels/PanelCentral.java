package ihm.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JDialog;

import controleur.Controleur;
import metier.Carre;
import metier.Cercle;
import metier.Forme;
import metier.Ligne;

public class PanelCentral extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    private Controleur ctrl;
    private Point pointA, pointB;
    private JDialog dialogTexte;
    private String texte;
    private PanelChoisirTexte panelChoisirTexte;

    private static final long serialVersionUID = 1L;

    public PanelCentral(Controleur ctrl) {
        this.ctrl = ctrl;

        // Dimension dimEcran = Toolkit.getDefaultToolkit().getScreenSize();
        // this.setPreferredSize(new Dimension((int)dimEcran.getWidth(), 800));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        this.dialogTexte = null;
        this.texte = null;

        this.pointA = new Point();
        this.pointB = new Point();

        this.addMouseMotionListener(this);
        this.addMouseListener(this);

    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4));


        /* Redessiner tous les carrés déjà présent dans l'ArrayList */
        for (Forme c : this.ctrl.getMetier().getAlFormes()) {
            if (c instanceof Carre) {
                Carre carre = (Carre) c;
                g2d.setColor(carre.getCouleur());

                g2d.drawRect(carre.getXA(), carre.getYA(), carre.getWidth(), carre.getHeight());
            }

            if (c instanceof Cercle) {
                Cercle cercle = (Cercle) c;
                g2d.setColor(cercle.getCouleur());
                g2d.drawOval(cercle.getXA(), cercle.getYA(), cercle.getWidth(), cercle.getHeight());
            }

            if (c instanceof Ligne) {
                Ligne ligne = (Ligne) c;
                g2d.setColor(ligne.getCouleur());
                g2d.drawLine(ligne.getXA(), ligne.getYA(), ligne.getXB(), ligne.getYB());
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (SwingUtilities.isLeftMouseButton(e)) {
            
            this.pointA = new Point((int) e.getX(), (int) e.getY());
            int xA, yA;
            xA = (int) this.pointA.getX();
            yA = (int) this.pointA.getY();     

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        this.pointB = new Point((int) e.getX(), (int) e.getY());

        int xA = (int) this.pointA.getX();
        int yA = (int) this.pointA.getY();
        int xB = (int) this.pointB.getX();
        int yB = (int) this.pointB.getY(); 
        if (SwingUtilities.isLeftMouseButton(e)) {
            switch (this.ctrl.getForme()) {
                case "Carre":
                    if (xB - xA > 0 && yB - yA > 0)
                        ctrl.addCarre(xA, yA, xB-xA, yB-yA);
                    if (xB - xA > 0 && yB - yA < 0)
                        ctrl.addCarre(xA, yB, xB-xA, yA-yB);
                    if (xB - xA < 0 && yB - yA > 0)
                        ctrl.addCarre(xB, yA, xA-xB, yB-yA);
                    if (xB - xA < 0 && yB - yA < 0)
                        ctrl.addCarre(xB, yB, xA-xB, yA-yB);
                    this.repaint();
                    break;

                case "Rond":
                    if (xB - xA > 0 && yB - yA > 0)
                        ctrl.addCercle(xA, yA, xB-xA, yB-yA);
                    if (xB - xA > 0 && yB - yA < 0)
                        ctrl.addCercle(xA, yB, xB-xA, yA-yB);
                    if (xB - xA < 0 && yB - yA > 0)
                        ctrl.addCercle(xB, yA, xA-xB, yB-yA);
                    if (xB - xA < 0 && yB - yA < 0)
                        ctrl.addCercle(xB, yB, xA-xB, yA-yB);
                    this.repaint();
                    break;



                case "Texte":
                    ImageIcon icon = new ImageIcon("./donnees/logo.png");
                    // String texte = (String) JOptionPane.showInputDialog(null,"Entrez votre texte
                    // :\n","Texte à afficher",JOptionPane.QUESTION_MESSAGE,icon,null,"");
                    this.dialogTexte = new JDialog();
                    // this.panelChoisirTexte = new PanelChoisirTexte(this.ctrl,
                    // this.lstJoueurs.get(cpt));

                    this.dialogTexte.setSize(400, 200);
                    this.dialogTexte.setLocation(200, 50);
                    this.dialogTexte.setResizable(false);
                    // this.dialogTexte.add(this.panelInfosJoueur);
                    this.dialogTexte.pack();
                    this.dialogTexte.setVisible(true);

                    /* Permet de detecter la fermeture de la fenêtre de dialogue */
                    /*
                     * this.dialogTexte.addWindowListener(new WindowListener()
                     * {
                     * public void windowClosing (WindowEvent e) {}
                     * public void windowOpened (WindowEvent e) {}
                     * public void windowClosed (WindowEvent e) {}
                     * public void windowIconified (WindowEvent e) {}
                     * public void windowDeiconified(WindowEvent e) {}
                     * public void windowActivated (WindowEvent e) {}
                     * public void windowDeactivated(WindowEvent e) { this.dialogTexte.dispose(); }
                     * });
                     */
                    // g.setFont(new Font("TimesRoman", Font.BOLD, 16));
                    if (texte != null && texte != "" && texte != " ")
                        // g2d.drawString(texte, xA, yA);
                    break;
                case "Ligne":
                    this.ctrl.addLigne(xA, yA, xB, yB);

                case " ":
                    System.out.println("Aucune forme sélectionné");
                    break;

                default:
                    System.out.println("Choix incorrect");
                    break;
            }
            this.repaint();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    public void mouseDragged(MouseEvent e) {
        this.pointB = new Point((int) e.getX(), (int) e.getY());
        repaint();
    }

    // On n'utilisera pas les méthodes ci-dessous
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }
}