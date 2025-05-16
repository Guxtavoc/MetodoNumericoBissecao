import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import net.objecthunter.exp4j.ExpressionBuilder;

public class TesteMetodo {

	private JFrame frame;
	private JTextField entradaExpressao;
	private JTextField txtMin;
	private JTextField txtMax;
	private JTextArea textResultados;
	private JTextField txtPart;
	private JTextField txtErro;
	private ArrayList<Double> raizes = new ArrayList<Double>();
	private JComboBox<String> comboMetodo;
	private Calc calc;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TesteMetodo window = new TesteMetodo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TesteMetodo() {
		initialize();
		this.calc = new Calc(TesteMetodo.this);
	}

	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 506, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		entradaExpressao = new JTextField();
		entradaExpressao.setFont(new Font("Arial", Font.BOLD, 16));
		entradaExpressao.setHorizontalAlignment(SwingConstants.CENTER);
		entradaExpressao.setText("x^3 - 6*x^2 + 11*x - 6");
		entradaExpressao.setBounds(56, 30, 383, 26);
		frame.getContentPane().add(entradaExpressao);
		entradaExpressao.setColumns(10);

		JLabel lblExpressao = new JLabel("DIGITE A EXPRESSÃO");
		lblExpressao.setFont(new Font("Arial", Font.BOLD, 14));
		lblExpressao.setHorizontalAlignment(SwingConstants.CENTER);
		lblExpressao.setBounds(171, 10, 156, 13);
		frame.getContentPane().add(lblExpressao);

		JLabel lblLimiteInferior = new JLabel("Limite inferior");
		lblLimiteInferior.setFont(new Font("Arial", Font.BOLD, 14));
		lblLimiteInferior.setBounds(56, 72, 114, 13);
		frame.getContentPane().add(lblLimiteInferior);

		txtMin = new JTextField();
		txtMin.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMin.setHorizontalAlignment(SwingConstants.CENTER);
		txtMin.setText("0");
		txtMin.setBounds(190, 69, 45, 19);
		frame.getContentPane().add(txtMin);
		txtMin.setColumns(10);

		JLabel lblLimiteSuperior = new JLabel("Limite superior");
		lblLimiteSuperior.setFont(new Font("Arial", Font.BOLD, 14));
		lblLimiteSuperior.setBounds(56, 98, 114, 13);
		frame.getContentPane().add(lblLimiteSuperior);

		txtMax = new JTextField();
		txtMax.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMax.setHorizontalAlignment(SwingConstants.CENTER);
		txtMax.setText("4");
		txtMax.setBounds(190, 95, 45, 19);
		frame.getContentPane().add(txtMax);
		txtMax.setColumns(10);

		JButton calculo = new JButton("Calcular");
		calculo.setFont(new Font("Arial", Font.BOLD, 13));
		calculo.setBounds(257, 129, 89, 21);
		frame.getContentPane().add(calculo);

		textResultados = new JTextArea();
		textResultados.setToolTipText("");
		textResultados.setFont(new Font("Arial", Font.PLAIN, 15));
		textResultados.setLineWrap(true);
		textResultados.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(textResultados); //Adicionando o textArea a um scrollPane
		scrollPane.setBounds(56, 160, 383, 358);
		frame.getContentPane().add(scrollPane);

		JButton gerarGrafico = new JButton("Grafico");
		
		gerarGrafico.addActionListener(new ActionListener() {//Botão do grafico
			public void actionPerformed(ActionEvent arg0) {
				if(testeVariaveis()) {
					calc.gerarGrafico(entradaExpressao.getText(), Double.parseDouble(txtMin.getText()), Double.parseDouble(txtMax.getText()),raizes);
				}		
			}
		});

		gerarGrafico.setFont(new Font("Arial", Font.BOLD, 13));
		gerarGrafico.setBounds(356, 129, 83, 21);
		frame.getContentPane().add(gerarGrafico);
		
		JLabel lblParticionamento = new JLabel("Partições");
		lblParticionamento.setFont(new Font("Arial", Font.BOLD, 14));
		lblParticionamento.setBounds(245, 72, 114, 13);
		frame.getContentPane().add(lblParticionamento);
		
		JLabel lblErroPermitido = new JLabel("Erro permitido");
		lblErroPermitido.setFont(new Font("Arial", Font.BOLD, 14));
		lblErroPermitido.setBounds(245, 98, 114, 13);
		frame.getContentPane().add(lblErroPermitido);
		
		txtPart = new JTextField();
		txtPart.setText("1");
		txtPart.setHorizontalAlignment(SwingConstants.CENTER);
		txtPart.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtPart.setColumns(10);
		txtPart.setBounds(369, 69, 70, 19);
		frame.getContentPane().add(txtPart);
		
		txtErro = new JTextField();
		txtErro.setText("0.00001");
		txtErro.setHorizontalAlignment(SwingConstants.CENTER);
		txtErro.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtErro.setColumns(10);
		txtErro.setBounds(369, 95, 70, 19);
		frame.getContentPane().add(txtErro);
		
		JButton btnReset = new JButton("Limpar");
		btnReset.setToolTipText("Resetar os limites, partições e erro aos valores padrão");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textResultados.setText("");
				//txtErro.setText("0.00001");
				//txtPart.setText("15");
				//txtMin.setText("0");
				//txtMax.setText("4");
			}
		});
		btnReset.setFont(new Font("Arial", Font.BOLD, 14));
		btnReset.setBounds(325, 527, 114, 21);
		frame.getContentPane().add(btnReset);
		
		JButton btnNewButton = new JButton("a");
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Config(TesteMetodo.this);
			}
		});
		btnNewButton.setBounds(383, 528, 56, 21);
		frame.getContentPane().add(btnNewButton);
		JComboBox<String> comboMetodo = new JComboBox<>();
		comboMetodo.addItem("Bisseção");
		comboMetodo.addItem("Falsa Posição");
		comboMetodo.setToolTipText("Selecione o método para o calcuulo");
		comboMetodo.setBounds(121, 130, 114, 21);
		frame.getContentPane().add(comboMetodo);
		
		JLabel lblMtodo = new JLabel("Método");
		lblMtodo.setFont(new Font("Arial", Font.BOLD, 14));
		lblMtodo.setBounds(56, 133, 55, 13);
		frame.getContentPane().add(lblMtodo);

		calculo.addActionListener(new ActionListener() {//Botão do calculo.
			public void actionPerformed(ActionEvent arg0) {
				if(testeVariaveis()) {
					calc.particiona(raizes, comboMetodo);
				}
			}
		});
		
	}
	
	public boolean testeVariaveis() {
		try {
			if(entradaExpressao.getText().isEmpty()) {
				textResultados.setText("O campo da expressão não pode ficar vazio!");
				return false;
			}
			new ExpressionBuilder(entradaExpressao.getText()).variable("x").build();
			double min = Double.parseDouble(txtMin.getText());
			double max = Double.parseDouble(txtMax.getText());
			if(min>=max) {
				return false;
			}
			double erro = Double.parseDouble(txtErro.getText());
			double part = Integer.parseInt(txtPart.getText());
			if(erro <= 0 || part <=0) {
				textResultados.setText("Erro ou particionamento invalido!\nO valor do erro ou o Partionamento não podem ser negativos ou 0!");
				return false;
			}
			return true;
		}catch(java.lang.NumberFormatException |net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException e) {
			textResultados.setText("Expressão ou argumentos invalidos!\nTente novamente...");
			return false;
		}
	}

	public double getTxtMin() {
	    return Double.parseDouble(txtMin.getText());
	}
	public double getTxtMax() {
	    return Double.parseDouble(txtMax.getText());
	}
	public JTextField getExpressao() {
	    return entradaExpressao;
	}
	public double getErro() {
		 return Double.parseDouble(txtErro.getText());
	}
	public double getParticao() {
		return Double.parseDouble(txtPart.getText());
	}
	public JComboBox<String> getCombo(){
		return comboMetodo;
	}
	public JTextArea getResultados() {
		return textResultados;
	}
}
