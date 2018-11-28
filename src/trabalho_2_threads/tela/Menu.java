package trabalho_2_threads.tela;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import trabalho_2_threads.controlador.Controlador;
import trabalho_2_threads.util.Constantes;

public class Menu extends JFrame {
    
    private String[] dificuldades = new String[] {
        Constantes.DIFICULDADE_FACIL,
        Constantes.DIFICULDADE_MEDIO,
        Constantes.DIFICULDADE_DIFICIL
    };
 
    private JComboBox<String> dificuldadesCombo = new JComboBox<>(dificuldades);
    
    private JButton buttonJogar;
    private JLabel labelDificuldade;
    private JLabel logo;

    private ActionListener l = new ActionListener() {
        public void actionPerformed(final ActionEvent e) {
            String selectedItem = (String) dificuldadesCombo.getSelectedItem();
            setVisible(false);
            Controlador.getControlador().jogar(selectedItem);
            repaint();
        }
    };

    public Menu() {
        config();
    }

    private void config() {
        setTitle(Constantes.TITULO_JOGO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttonJogar = new JButton(Constantes.JOGAR);
        labelDificuldade = new JLabel(Constantes.JOGO_DIFICULDADE);
        logo = new JLabel("");
        
        setPreferredSize(new Dimension(430, 430));
        setLayout(null);
        
        add(buttonJogar);
        add(dificuldadesCombo);
        add(labelDificuldade);
        add(logo);
        
        labelDificuldade.setBounds(120, 150, 155, 35);
        dificuldadesCombo.setBounds(120, 180, 140, 40);
        buttonJogar.setBounds(120, 230, 140, 40);
        buttonJogar.addActionListener(l);
        
        logo.setIcon(new javax.swing.ImageIcon("imagens/logo.png"));
        logo.setBounds(40, 0, 300, 120);
    }
}
