import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Calc {
	
	private double limiteMin;
	private double limiteMax;
	private double limiteErro;
	private double particao;
	private JTextField expressao;
	private JTextArea textResultados;
	private TesteMetodo main;
	
	public Calc(TesteMetodo main) {
		this.main = main;
	}
	/*
	public double calculaDerivada() {

		Expression exp = new ExpressionBuilder(expressao.getText()).variable("x").build();
		
		return derivada;
	}
	*/
	
	public void particiona(ArrayList<Double> raizes,JComboBox<String> comboMetodo) {
		this.limiteErro = main.getErro();
		this.limiteMin = main.getTxtMin();
		this.limiteMax = main.getTxtMax();
		this.particao = main.getParticao();
		this.expressao = main.getExpressao();
		this.textResultados = main.getResultados();
		raizes.clear();
		Expression exp;
		Double min = limiteMin;
		Double max = limiteMax;
		exp = new ExpressionBuilder(expressao.getText()).variable("x").build();
		int quantidade = (int)particao;
		Double particionamento = (max-min)/quantidade;
		for (int i = 0; i < quantidade; i++) {
			double a = min + (i*particionamento);
			double b = a + particionamento;
			exp.setVariable("x", a);
			double fa = exp.evaluate();
			exp.setVariable("x", b);
			double fb = exp.evaluate();
			if(fa * fb < 0) {
				String metodo = (String) comboMetodo.getSelectedItem();
				if(metodo.equals("Bisseção")) {
					calcularBissesao(a, b, raizes);
				}else {
					calculaFalsaPosicao(a, b, raizes);
				}
			}
		}
	}
	
	public ArrayList<Double> calculaFalsaPosicao(double min, double max, ArrayList<Double> raizes) {
		Expression exp = new ExpressionBuilder(expressao.getText()).variable("x").build();
		double pm = 0, fPm = 0,erro = limiteErro;
		double fMax,fMin,erroRelativo,xAnterior=0;
		int iteracao=0;
		do {
			exp.setVariable("x", max);
			fMax = exp.evaluate();
			exp.setVariable("x", min);
			fMin = exp.evaluate();
			pm = max-((fMax*(min-max))/(fMin-fMax));
			exp.setVariable("x", pm);
			fPm = exp.evaluate();
			iteracao++;
			if(iteracao!=0 && fPm!=0) {
				erroRelativo = Math.abs((pm - xAnterior)/pm) * 100;
			}else {
				erroRelativo = 100;
			}
			if (fMin * fPm < 0) {
			    max = pm;
			    fMax = fPm;
			} else {
				min = pm;
				fMin = fPm;
			}
			xAnterior = pm;
			textResultados.append(String.format("Iteração: %d Xl: %.2f Xu: %.2f Xr: %f Ea: %.1f\n\n" , iteracao+1,min,max,pm,erroRelativo));
			}while(Math.abs(fPm) > erro);
		raizes.add(pm);
		textResultados.append("Raiz aproximada encontrada: " + pm + "\n\n");
		return raizes;
	}
	public ArrayList<Double> calcularBissesao(double min, double max, ArrayList<Double> raizes) {
		Expression exp = new ExpressionBuilder(expressao.getText()).variable("x").build();
		double erro = limiteErro;
		double xAnterior = 0.0f;
		double erroRelativo = 0;
		//double erroAbsoluto = 0;
		for (int i = 0; i < 100; i++) {
			
			exp.setVariable("x", min);
			double fMin = exp.evaluate();
			exp.setVariable("x", max);
			//double fMax = exp.evaluate();			
			
			double pm = (min + max) / 2;
			exp.setVariable("x", pm);
			double fPm = exp.evaluate();
			if(i!=0 && fPm!=0) {
				erroRelativo = Math.abs((pm - xAnterior)/pm) * 100;
				//erroAbsoluto = Math.abs(pm-xAnterior);
			}else {
				erroRelativo = 100;
			}
			textResultados.append(String.format("Iteração: %d Xl: %.2f Xu: %.2f Xr: %f Ea: %.1f\n\n" , i+1,min,max,pm,erroRelativo));
			if (Math.abs(fPm) < erro) { //comparando o MODULO da raiz de f(pm) com o erro
				raizes.add(pm);
				textResultados.append("Raiz aproximada encontrada: " + pm + "\n\n");
				break;
			}
			if (fMin * fPm < 0) {
			    max = pm;
			   // fMax = fPm;
			} else {
			    min = pm;
			    //fMin = fPm;
			}
			xAnterior = pm;
		}
		return raizes;
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
		
		for (double raiz : raizes) {
		    Marker marker = new ValueMarker(raiz); //Criando um marcador para criar a linha entre a raiz e o eixo x
		    marker.setPaint(Color.black);
		    marker.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5.0f, 5.0f}, 0));
		    plot.addDomainMarker(marker); //adicionando ao eixo X
		}
		//Criando o chartPanel e o atribuindo a um novo frame
		ChartPanel chartPanel = new ChartPanel(chart);
		JFrame chartFrame = new JFrame("Gráfico da função");
		chartFrame.setContentPane(chartPanel);
		chartFrame.pack();
		chartFrame.setLocationRelativeTo(null);
		chartFrame.setVisible(true);
	}
}
