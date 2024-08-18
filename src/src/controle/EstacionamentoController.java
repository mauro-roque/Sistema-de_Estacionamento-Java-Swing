package impacta.ead.estacionamento.controle;

import java.time.LocalDateTime;
import java.util.List;

import impacta.ead.estacionamento.negocio.Movimentacao;
import impacta.ead.estacionamento.negocio.Vaga;
import impacta.ead.estacionamento.negocio.Veiculo;
import impacta.ead.estacionamento.persistencia.DAOEstacionamento;
import impacta.ead.estacionamento.utilitario.EstacionamentoUtil;

/**
 * Coordena todos os fluxos dos casos de uso do sistema
 * 
 * @author technoedition
 *
 */
public class EstacionamentoController {
	
	/**
	 * A partir dos dados do veiculo informados pelo operador realiza
	 * o fluxo de entrada do veículo no estacionamento registrando
	 * a movimentacao gerada.
	 * 
	 * @param placa Placa do veiculo
	 * @param marca Marca do veiculo
	 * @param modelo Modelo do veiculo
	 * @param cor Cor do veiculo
	 * @throws EstacionamentoException Quando estacionamento estiver lotado.
	 * @throws VeiculoException Quando o padrão da placa for invalido.
	 */
	public void processarEntrada(String placa, String marca, 
			String modelo, String cor) 
					throws EstacionamentoException, VeiculoException{
		//verificar se o estacionamento está lotado
		if(!Vaga.temVagaLivre()){
			throw new EstacionamentoException("Estacionamento lotado!");
		}
		
		//verificar o padrão de string da placa
		if(!EstacionamentoUtil.validarPadraoPlaca(placa)){
			throw new VeiculoException("Placa informada inválida!");
		}
		//criar uma instancia do veiculo
		Veiculo veiculo = new Veiculo(placa,marca,modelo,cor);
		//criar a movimentacao vinculando o veiculo e com data de entrada corrente
		Movimentacao movimentacao = new Movimentacao(veiculo,LocalDateTime.now());
		//registrar na base de dados a informacao
		DAOEstacionamento dao = new DAOEstacionamento();
		dao.criar(movimentacao);
		//atualizar o numero vagas ocupadas
		Vaga.entrou();
		//fim
	}
	
	/**
	 * A partir de uma placa de veiculo informada, realiza todo o 
	 * fluxo de saída de veículo do estacionamento
	 * 
	 * @param placa Placa do veiculo que estiver saindo
	 * 
	 * @return Uma instância de movimentação com dados atualizados de valor e data/hora saída
	 * @throws VeiculoException Quando a placa estiver incorreta
	 * @throws EstacionamentoException Quando o veiculo com a placa 
	 * informada não é localizado no estacionamento.
	 */
	public Movimentacao processarSaida(String placa) 
			throws VeiculoException, EstacionamentoException{
		
		//validar a placa
		if(!EstacionamentoUtil.validarPadraoPlaca(placa)){
			throw new VeiculoException("Placa inválida!");
		}
		
		//Buscar a movimentacao aberta baseada na placa
		DAOEstacionamento dao = new DAOEstacionamento();
		Movimentacao movimentacao = dao.buscarMovimentacaoAberta(placa);
		
		if(movimentacao == null){
			throw new EstacionamentoException("Veículo não encontrado!");
		}
				
		//Fazer o calculo do valor a ser pago
		movimentacao.setDataHoraSaida(LocalDateTime.now());
		EstacionamentoUtil.calcularValorPago(movimentacao);
		
		//Atualizar os dados da movimentacao
		dao.atualizar(movimentacao);
		
		//Atualizar o status da vaga
		Vaga.saiu();
		
		return movimentacao;
	}
	
	/**
	 * Realiza o fluxo de emissão de relatório de faturamento
	 * baseado num mês e ano informados.
	 * 
	 * @param data Data (mês e ano) de emissão desejado
	 * 
	 * @return Lista de movimentações que atendem ao filtro
	 */
	public List<Movimentacao> emitirRelatorio(LocalDateTime data){
		DAOEstacionamento dao = new DAOEstacionamento();
		return dao.consultarMovimentacoes(data);
	}

	public int inicializarOcupadas() {
		DAOEstacionamento dao = new DAOEstacionamento();
		return dao.getOcupadas();
	}

}
