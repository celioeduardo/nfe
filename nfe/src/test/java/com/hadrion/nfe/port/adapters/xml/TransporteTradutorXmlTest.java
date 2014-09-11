package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.transporte.ModalidadeFrete;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Placa;
import com.hadrion.nfe.dominio.modelo.nf.transporte.TipoVeiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Volume;
import com.hadrion.nfe.tipos.Cpf;

public class TransporteTradutorXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<transp>\r\n" + 
			"	<modFrete>1</modFrete>\r\n" + 
			"	<transporta>\r\n" + 
			"		<CPF>57133239191</CPF>\r\n" + 
			"		<xNome>JAIR FRIZON</xNome>\r\n" + 
			"		<xEnder>RUA CASEMIRO DE ABREU 256</xEnder>\r\n" + 
			"		<xMun>RIBEIRAO PRETO</xMun>\r\n" + 
			"		<UF>35</UF>\r\n" + 
			"	</transporta>\r\n" + 
			"	<veicTransp>\r\n" + 
			"		<placa>KEP2310</placa>\r\n" + 
			"		<UF>52</UF>\r\n" + 
			"	</veicTransp>\r\n" + 
			"	<vol>\r\n" + 
			"		<qVol>37220</qVol>\r\n" + 
			"		<esp>KG</esp>\r\n" + 
			"		<pesoL>37220.000</pesoL>\r\n" + 
			"		<pesoB>37220.000</pesoB>\r\n" + 
			"	</vol>\r\n" + 
			"</transp>";
	
	private Transporte transporte;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("transp", Transporte.class);
	}
	
	@Test
	public void serializar(){
		transporte = new Transporte(
			ModalidadeFrete.DESTINATARIO_REMETENTE, 
			new Transportador(
					null, new Cpf(57133239191L), "JAIR FRIZON", null, 
					new Endereco(
							"RUA CASEMIRO DE ABREU", 
							"256", null, null, 
							new Municipio(3543402,"RIBEIRAO PRETO", Uf.SP), 
							Pais.BRASIL, null, null)), 
					null, 
			new Veiculo(TipoVeiculo.VEICULO,new Placa(Uf.GO,"KEP2310"),null, null), 
			new Volume(37220, "KG", null, null, 37220.0, 37220.0, null));
		assertXMLEquals(XML,toXML(transporte));
	}
	
	@Test
	public void deserializar(){
		transporte = new Transporte(
			ModalidadeFrete.DESTINATARIO_REMETENTE, 
			new Transportador(
					null, new Cpf(57133239191L), "JAIR FRIZON", null, 
					new Endereco(
							"RUA CASEMIRO DE ABREU 256", 
							null, null, null, 
							new Municipio(0,"RIBEIRAO PRETO", Uf.SP), 
							null, null, null)), 
					null, 
			new Veiculo(null,new Placa(Uf.GO,"KEP2310"),null, null), 
			new Volume(37220, "KG", null, null, 37220.0, 37220.0, null));
		assertEquals(transporte,fromXML(XML));
	}
	
	@Test
	public void enderecoTransportador(){
		String enderecoComMaisDe60Posicoes = 
				"RUA CASEMIRO DE ABREU, 256, BAIRRO: VILA SEIXAS, COMPLEMENTO XXXXXXXXX";
		String endereco = StringUtils.substring(enderecoComMaisDe60Posicoes,0,60);
		assertEquals(60,endereco.length());
		assertEquals(
				"RUA CASEMIRO DE ABREU, 256, BAIRRO: VILA SEIXAS, COMPLEMENTO",
				endereco);
	}
	
}
