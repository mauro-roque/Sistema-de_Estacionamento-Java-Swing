package impacta.ead.estacionamento.apresentacao;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import impacta.ead.estacionamento.negocio.Movimentacao;
import impacta.ead.estacionamento.utilitario.EstacionamentoUtil;

public class TelaResumoPagamento extends JFrame implements ActionListener{
	private JFrame parent;
	public TelaResumoPagamento(Movimentacao movimentacao, JFrame parent) {
		this.parent = parent;
		getContentPane().setFont(new Font("Dialog", Font.BOLD, 12));
		setSize(new Dimension(430, 300));
		setResizable(false);
		setTitle("Resumo de Pagamento");
		getContentPane().setLayout(null);
		
		JLabel lblPlaca = new JLabel("Placa: ");
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlaca.setBounds(110, 47, 46, 14);
		getContentPane().add(lblPlaca);
		
		JLabel lblDataEntrada = new JLabel("Entrada:");
		lblDataEntrada.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataEntrada.setBounds(96, 87, 60, 14);
		getContentPane().add(lblDataEntrada);
		
		JLabel lblDataSaida = new JLabel("Sa\u00EDda:");
		lblDataSaida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataSaida.setBounds(110, 129, 46, 14);
		getContentPane().add(lblDataSaida);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValor.setBounds(110, 172, 46, 14);
		getContentPane().add(lblValor);
		
		String sPlaca = movimentacao.getVeiculo().getPlaca();
		JLabel lblValPlaca = new JLabel(sPlaca);
		lblValPlaca.setFont(new Font("Dialog", Font.BOLD, 12));
		lblValPlaca.setBounds(229, 47, 66, 14);
		getContentPane().add(lblValPlaca);
		
		String sEntrada = EstacionamentoUtil.getDisplayData
				(movimentacao.getDataHoraEntrada());
		JLabel lblValDataEntrada = new JLabel(sEntrada);
		lblValDataEntrada.setFont(new Font("Dialog", Font.BOLD, 12));
		lblValDataEntrada.setBounds(229, 87, 181, 14);
		getContentPane().add(lblValDataEntrada);
		
		String sSaida = EstacionamentoUtil.getDisplayData
				(movimentacao.getDataHoraSaida()); 
		JLabel lblValDataSaida = new JLabel(sSaida);
		lblValDataSaida.setFont(new Font("Dialog", Font.BOLD, 12));
		lblValDataSaida.setBounds(229, 129, 181, 14);
		getContentPane().add(lblValDataSaida);
		
		String sValor = "R$ " + movimentacao.getValor(); 
		JLabel lblValValor = new JLabel(sValor);
		lblValValor.setFont(new Font("Dialog", Font.BOLD, 12));
		lblValValor.setBounds(229, 172, 80, 14);
		getContentPane().add(lblValValor);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(this);
		btnOk.setBounds(152, 235, 89, 23);
		getContentPane().add(btnOk);
		
		setLocationRelativeTo(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		parent.setVisible(true);
		dispose();
	}
}
