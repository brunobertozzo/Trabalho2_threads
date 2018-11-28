package trabalho_2_threads.modelo;

import trabalho_2_threads.controlador.Controlador;

public class Token extends Thread {
    private float velocidade;
    private boolean ativo;

    public Token(float velocidade) {
        this.velocidade = velocidade;
        this.ativo = true;
    }

    public float getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(float velocidade) {
        this.velocidade = velocidade;
    }

    @Override
    public void run() {
        System.out.println(currentThread().getName() + " iniciou!");
        while(ativo) {
            Controlador.getControlador().movimentaTokens();
            try {
                Thread.sleep((long) velocidade);
            } catch (InterruptedException e) {
                setAtivo(false);
            }
        }
        System.out.println(currentThread().getName() + " terminou!");
    }

    public void setAtivo(boolean dead) {
        this.ativo = dead;
    }

    public boolean getAtivo() {
        return ativo;
    }
}
