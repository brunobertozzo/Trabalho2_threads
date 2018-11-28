package trabalho_2_threads.controlador;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import trabalho_2_threads.modelo.Token;
import trabalho_2_threads.modelo.Timer;
import trabalho_2_threads.tela.Jogo;
import trabalho_2_threads.tela.Menu;
import trabalho_2_threads.util.Constantes;

public class Controlador {
	private static final Controlador controlador = new Controlador();
	private Menu menu;
	private Jogo jogo;
	private int tokensInativos;
;	private ArrayList<Token> tokens;
	private List<JButton> livres;
	private List<JButton> ocupados;
	private Timer timer;
	private boolean novaPartida = true;
	private int index = 0;
	private boolean livre = true;
	
	public Controlador() {
            menu = new Menu();
            jogo = new Jogo();
            tokens = new ArrayList<>();
            timer = new Timer();
            livres = jogo.getButtons();
            ocupados = new ArrayList<>();
            
            for (int i = 0; i < 5; i++) {
                tokens.add(new Token(0));
            }
	}

	public static Controlador getControlador() {
            return controlador;
	}

	private void init() {
            limparTela();
            for (int i = 0; i < 5; i++) {
                int pos = (int) (Math.random() * livres.size());
                JButton ocupada = livres.get(pos);
                livres.remove(ocupada);
                ocupados.add(ocupada);
                jogo.addToken(ocupada);
            }
	}

        public void jogar(String dificuldade) {
            jogo.setDificuldade(dificuldade);
            jogo.pack();
            
            switch(dificuldade) {
                case Constantes.DIFICULDADE_FACIL:
                    startThreads(50, 2000);
                    break;
                case Constantes.DIFICULDADE_MEDIO:
                    startThreads(25, 1000);
                    break;
                case Constantes.DIFICULDADE_DIFICIL:
                    startThreads(10, 500);
                    break;
            }
        }

	public void start() {
            menu.pack();
            menu.setVisible(true);
	}

	private synchronized void startThreads(int tempo, float velocidade) {
            init();
            System.out.println(tokens.size());
            for (Token token : tokens) {
                if (token.getAtivo()) {
                    token.setVelocidade(velocidade);
                    token.start();
                } else {
                    token.setVelocidade(velocidade);
                    token.setAtivo(true);
                    new Thread(token).start();
                }
            }
            
            timer.setTimeSec(tempo);
            if (timer.getAlive()) {
                timer.start();
            } else {
                timer.setAlive(true);
                new Thread(timer.getCurrentThread()).start();
            }
            timer.pack();
            timer.setVisible(true);
            jogo.pack();
            jogo.setVisible(true);
	}

	public synchronized void movimentaTokens() {
            if(livre) {
                livre = false;
                int pos = (int) (Math.random() * livres.size());
                JButton ocupada = livres.get(pos);
                livres.remove(ocupada);
                ocupados.add(ocupada);
                JButton livre = ocupados.get(0);
                livres.add(livre);
                ocupados.remove(livre);
                jogo.removeToken(livre);
                jogo.addToken(ocupada);
                this.livre = true;
            }
	}

	public synchronized void exitGame() {
            mataTodasThreads();
            limpaTokens();
            novaPartida = false;
            index = 0;
            if (tokensInativos >= 5) {
                    jogo.mostraMensagem("Você ganhou!");
            } else {
                    jogo.mostraMensagem("Você perdeu!");
            }
            tokensInativos = 0;
            limparTela();
	}
	
	private synchronized void limpaTokens() {
            livres = jogo.getButtons();
            ocupados = new ArrayList<>();
	}

	private synchronized void mataTodasThreads() {
            for (Token token : tokens) {
                    token.setAtivo(false);
            }
	}

	public synchronized void tratarClique(JButton b) {
            if(livre) {
                    livre = false;
                    for (JButton button : ocupados) {
                            Rectangle bounds = button.getBounds();
                            if (bounds.equals(b.getBounds())) {
                                    tokensInativos += 1;
                                    ocupados.remove(button);
                                    livres.add(button);
                                    jogo.removeToken(button);
                                    tokens.get(index).setAtivo(false);
                                    index++;
                                    break;
                            }
                    }
                    livre = true;
            }
            if (index >= 5) {
                    exitGame();
            }
	}

	private void limparTela() {
            jogo.limparTela();
            jogo.setVisible(false);
            menu.setVisible(true);
	}

	public boolean isNovaPartida() {
            return novaPartida;
	}

	public void setNovaPartida(boolean novaPartida) {
            this.novaPartida = novaPartida;
	}
	
	public int getTokens() {
            return tokensInativos;
	}
}
