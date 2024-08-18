package impacta.ead.estacionamento.apresentacao;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import impacta.ead.estacionamento.controle.EstacionamentoController;
import impacta.ead.estacionamento.negocio.Movimentacao;

public class TelaInicialRelatorio extends JFrame implements ActionListener{
	
	private JComboBox cboAno;
	private JComboBox cboMes;
	
	public TelaInicialRelatorio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(600,140));
		setResizable(false);
		setTitle("Filtro do Relat\u00F3rio");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 15, 40));
		
		JLabel lblAno = new JLabel("Ano:");
		lblAno.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(lblAno);
		
		cboAno = new JComboBox();
		cboAno.setModel(new DefaultComboBoxModel(new String[] {"2016", "2015", "2014", "2013", "2012"}));
		cboAno.setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().add(cboAno);
		
		JLabel lblMes = new JLabel("M\u00EAs:");
		lblMes.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(lblMes);
		
		cboMes = new JComboBox();
		cboMes.setModel(new DefaultComboBoxModel(new String[] {"Janeiro", "Fevereiro", "Mar\u00E7o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"}));
		cboMes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().add(cboMes);
		
		JButton btnGerar = new JButton("Gerar");
		btnGerar.addActionListener(this);
		btnGerar.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(btnGerar);
		
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		//recupera do combo o ano e mes escolhido
		int ano = Integer.parseInt( (String) cboAno.getSelectedItem());
		int mes = (Integer) cboMes.getSelectedIndex()+1;
		
		//buscar as movimentacoes do mes e ano informados
		EstacionamentoController controle = new EstacionamentoController();
		LocalDateTime data = LocalDateTime.of(ano, mes,1,0,0);
		List<Movimentacao> movimentacoes = controle.emitirRelatorio(data);
		
		//Exibe a tela de conteudo e faturamento
		TelaResultadoRelatorio relatorio = 
				new TelaResultadoRelatorio(this,movimentacoes,data);
		
		relatorio.setVisible(true);
		dispose();
		
	}

}
