import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class Config {

	private JFrame frame;
	private JTextField txtNovoMin;
	private JTextField txtNovoMax;
	private TesteMetodo principal;

	public Config(TesteMetodo principal) {
        this.principal = principal;
        initialize();
    }
	private void initialize() {
		 frame = new JFrame();
	        frame.setBounds(100, 100, 300, 200);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.getContentPane().setLayout(null);

	        JLabel lblMin = new JLabel("Novo Min:");
	        lblMin.setBounds(30, 30, 70, 20);
	        frame.getContentPane().add(lblMin);

	        txtNovoMin = new JTextField(principal.getTxtMin().getText());
	        txtNovoMin.setBounds(110, 30, 100, 20);
	        frame.getContentPane().add(txtNovoMin);

	        JLabel lblMax = new JLabel("Novo Max:");
	        lblMax.setBounds(30, 60, 70, 20);
	        frame.getContentPane().add(lblMax);

	        txtNovoMax = new JTextField(principal.getTxtMax().getText());
	        txtNovoMax.setBounds(110, 60, 100, 20);
	        frame.getContentPane().add(txtNovoMax);

	        JButton btnSalvar = new JButton("Salvar");
	        btnSalvar.setBounds(80, 100, 100, 30);
	        frame.getContentPane().add(btnSalvar);
	        
	        JComboBox<String> comboBox = new JComboBox<String>();
	        comboBox.setBounds(228, 105, 29, 21);
	        frame.getContentPane().add(comboBox);
	        comboBox.addItem("olá");
	        comboBox.addItem("Tchau");
	        btnSalvar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                principal.getTxtMin().setText(txtNovoMin.getText());
	                principal.getTxtMax().setText(txtNovoMax.getText());
	                frame.dispose();
	            }
	        });

	        frame.setVisible(true);
	}
	}
