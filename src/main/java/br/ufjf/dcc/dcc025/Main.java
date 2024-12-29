package br.ufjf.dcc.dcc025;
import javax.swing.JOptionPane;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String nome = JOptionPane.showInputDialog("Digite seu nome: ");
        JOptionPane.showMessageDialog(null,"Bem-vindo, " + nome + "!");

        String option = JOptionPane.showInputDialog("Forma de começar o jogo: \n (1) Gerar um jogo aleatório \n (2) Definir o próprio jogo");
        switch (option) {
            case "1": String sortNumber = JOptionPane.showInputDialog("Quantos números você deseja sortear?");
            break;
            case "2": JOptionPane.showMessageDialog(null,"Definindo seu próprio jogo! (digitar no console)");
            break;
            default: JOptionPane.showMessageDialog(null,"Opção inválida!");
            break;
        }



        }
    }
