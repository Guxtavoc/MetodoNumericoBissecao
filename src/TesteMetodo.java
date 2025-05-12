import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class TesteMetodo {

	private JFrame frame;
	private JTextField entradaExpressao;
	private JTextField txtMin;
	private JTextField txtMax;
	private JTextArea textResultados;
	private final JLabel txtRaiz = new JLabel("0");
	private JTextField txtPart;
	private JTextField txtErro;
	private ArrayList<Double> raizes = new ArrayList<Double>();
	
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
		calculo.setFont(new Font("Arial", Font.BOLD, 14));
		calculo.setBounds(56, 129, 114, 21);
		frame.getContentPane().add(calculo);

		textResultados = new JTextArea();
		textResultados.setLineWrap(true);
		textResultados.setEditable(false);
		textResultados.setBounds(56, 160, 383, 358);
		frame.getContentPane().add(textResultados);

		JButton gerarGrafico = new JButton("Grafico");
		
		gerarGrafico.addActionListener(new ActionListener() {//Botão do grafico
			public void actionPerformed(ActionEvent arg0) {
				gerarGrafico(entradaExpressao.getText(), 
						Double.parseDouble(txtMin.getText()),
						Double.parseDouble(txtMax.getText()),
						raizes
						);
			}
		});

		gerarGrafico.setFont(new Font("Arial", Font.BOLD, 14));
		gerarGrafico.setBounds(325, 129, 114, 21);
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
		txtPart.setText("15");
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
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textResultados.setText("");
				txtErro.setText("0.00001");
				txtPart.setText("10");
				txtMin.setText("0");
				txtMax.setText("4");
			}
		});
		btnReset.setFont(new Font("Arial", Font.BOLD, 14));
		btnReset.setBounds(190, 130, 114, 21);
		frame.getContentPane().add(btnReset);

		calculo.addActionListener(new ActionListener() {//Botão do calculo.
			public void actionPerformed(ActionEvent arg0) {

				particiona(raizes);
				//calcularBissesao();

			}
		});
	}
	
	public ArrayList<Double> calcularBissesao(double min, double max, ArrayList<Double> raizes) {
		Expression exp = new ExpressionBuilder(entradaExpressao.getText()).variable("x").build();
		double erro =  Double.parseDouble(txtErro.getText());
		//ArrayList<Double> raizes = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			
			exp.setVariable("x", min);
			double fMin = exp.evaluate();
			exp.setVariable("x", max);
			double fMax = exp.evaluate();			
			
			double pm = (min + max) / 2;
			exp.setVariable("x", pm);
			double fPm = exp.evaluate();
			textResultados.append(String.format("Iteração: %d f(%.6f) = %.6f\n", i + 1, pm, fPm));
			if (Math.abs(fPm) < erro) { //comparando o MODULO da raiz de f(pm) com o erro
				txtRaiz.setText("" + pm); //Criando uma label invisivel para receber o valor da raiz
				raizes.add(pm);
				textResultados.append("Raiz aproximada encontrada: " + pm + "\n");
				break;
			}
			if (fMin * fPm < 0) {
			    max = pm;
			    fMax = fPm;
			} else {
			    min = pm;
			    fMin = fPm;
			}
		}
		return raizes;
	}
	
	
	public void particiona(ArrayList<Double> raizes) {
		raizes.clear();
		Expression exp = new ExpressionBuilder(entradaExpressao.getText()).variable("x").build(); 
		Double min = Double.parseDouble(txtMin.getText());
		Double max = Double.parseDouble(txtMax.getText());
		int quantidade = Integer.parseInt(txtPart.getText());
		Double particionamento = (max-min)/quantidade;
		for (int i = 0; i < quantidade; i++) {
			double a = min + (i*particionamento);
			double b = a + particionamento;
			exp.setVariable("x", a);
			double fa = exp.evaluate();
			exp.setVariable("x", b);
			double fb = exp.evaluate();
			if(fa * fb < 0) {
				//Existe raiz
				calcularBissesao(a,b,raizes);
			}
		}
	}

	public void gerarGrafico(String expressao, double limiteMin, double limiteMax, ArrayList<Double> raizes) {
		XYSeries series = new XYSeries("f(x)"); //"vetor" que receberá os pontos x e y
		XYSeries raizSeries = new XYSeries("Raiz Aproximada");

		Expression exp = new ExpressionBuilder(expressao).variable("x").build(); 

		for (double x = limiteMin; x <= limiteMax; x += 0.1) { //Modificando o valor de x para gerar o grafico 
			exp.setVariable("x", x);
			double y = exp.evaluate();
			series.add(x, y); //Criando os pares ordenados de X e Y
		}

		//Calculando o valor e criando os par ordenado do ponto(s) da raiz
		for (double raiz : raizes) {
			exp.setVariable("x", raiz);
			double yRaiz = exp.evaluate();
			raizSeries.add(raiz, yRaiz);
		}
		
		//Criando o dataset da função e raiz
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		dataset.addSeries(raizSeries);

		//definindo o grafico
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Gráfico da Função", //Nome
				"X", //Eixo horizontal
				"Y", //Eixo vertical
				dataset, //dataset com os pares ordenados
				PlotOrientation.VERTICAL, //Oritanção do eixo Y
				true, //Exibir legenda
				false, //Exibir tooltips
				false //Gerar link
				);

		//Setando o rederer 
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.BLUE); // f(x)
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesPaint(1, Color.RED); // ponto raiz
		renderer.setSeriesLinesVisible(1, false);
		renderer.setSeriesShape(1, new Ellipse2D.Double(-4, -4, 8, 8)); // circulo vermelho
		plot.setRenderer(renderer);

		//Criando o chartPanel e o atribuindo a um novo frame
		ChartPanel chartPanel = new ChartPanel(chart);
		JFrame chartFrame = new JFrame("Gráfico da função");
		chartFrame.setContentPane(chartPanel);
		chartFrame.pack();
		chartFrame.setLocationRelativeTo(null);
		chartFrame.setVisible(true);
	}
}
