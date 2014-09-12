package com.hadrion.nfe.port.adapters.xml.nf;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.dominio.modelo.nf.Exportacao;
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.FormaPagamento;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Presenca;
import com.hadrion.nfe.dominio.modelo.nf.Processo;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.informacao.Informacao;
import com.hadrion.nfe.dominio.modelo.nf.item.Cide;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalRetirada;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Quantidade;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class NotaFiscalConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return NotaFiscal.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		NotaFiscal nf = (NotaFiscal) source;
		writer.startNode("ide");
		convert("cUF",nf.emitente().endereco().municipio().uf(),writer,context);
		convert("cNF",nf.chaveAcesso().codigo(),writer,context);
		convert("natOp",nf.naturezaOperacao(),writer,context);
		convert("indPag",nf.formaPagamento(),writer,context);
		convert("mod",nf.modelo(),writer,context);
		convert("serie",nf.serie(),writer,context);
		convert("nNF",nf.numero(),writer,context);
		convert("dEmi",nf.emissao(),writer,context);
		convert("dSaiEnt",nf.dataHora(),writer,context);
		convert("tpNF",nf.tipoOperacao(),writer,context);
		convert("idDest",nf.localDestino(),writer,context);
		convert("cMunFG",nf.municipioFatoGerador().codigo(),writer,context);
		//TODO Definir Tipo de Impressão do DANFE
		convert("tpImp",1,writer,context);
		//TODO Definir Tipo de Emissão do DANFE
		convert("tpEmis",1,writer,context);
		//TODO Digito Verificador da Chave de Acesso
		convert("cDV",0,writer,context);
		//TODO Ambiente
		convert("tpAmb",2,writer,context);
		convert("finNFe",nf.finalidade(),writer,context);
		convert("indFinal",nf.consumidorFinal() ? 1 : 0, writer,context);
		convert("indPres",nf.presenca(), writer,context);
		convert("procEmi",nf.processo(), writer,context);
		//TODO verProc - Informar a versão do aplicativo emissor
		convert("verProc","1.0", writer,context);
		//TODO dhCont - Data e Hora de entrada em contingência
		//TODO xJust - Justificativa de entrada em contingência
		//TODO referencias - Referências
		writer.endNode();
		
		convert("emit",nf.emitente(), writer,context);
		convert("dest",nf.destinatario(), writer,context);
		convert("retirada",nf.localRetirada(), writer,context);
		convert("entrega",nf.localEntrega(), writer,context);
		
		int i = 1;
		for (Item item : nf.itens()) {
			writer.startNode("det");
			writer.addAttribute("nItem", String.valueOf(i));
			context.convertAnother(item);
			writer.endNode();
			i++;
		}
		
		writer.startNode("total");
		writer.startNode("ICMSTot");
		convert("vBC",nf.totalBaseCalculoIcms(),writer,context);
		convert("vICMS",nf.totalIcms(),writer,context);
		convert("vICMSDeson",Dinheiro.ZERO,writer,context);
		convert("vBCST",nf.totalBaseCalculoIcmsSt(),writer,context);
		convert("vST",nf.totalIcmsSt(),writer,context);
		convert("vProd",nf.totalProdutos(),writer,context);
		convert("vFrete",nf.totalFrete(),writer,context);
		convert("vSeg",nf.totalSeguro(),writer,context);
		convert("vDesc",nf.totalDesconto(),writer,context);
		convert("vII",Dinheiro.ZERO,writer,context);
		convert("vIPI",Dinheiro.ZERO,writer,context);
		convert("vPIS",nf.totalPis(),writer,context);
		convert("vCOFINS",nf.totalCofins(),writer,context);
		convert("vOutro",nf.outrasDespesasAcessorias(),writer,context);
		convert("vNF",nf.total(),writer,context);
		convert("vTotTrib",nf.totalValorAproximadoTributos(),writer,context);
		writer.endNode();
		writer.endNode();
		
		writer.startNode("transp");
		context.convertAnother(nf.transporte());
		writer.endNode();
		
		convertIf("cobr",nf.cobranca(),writer,context);
		
		writer.startNode("infAdic");
		if (nf.informacaoFisco() != null)
			convertIf("infAdFisco", nf.informacaoFisco().texto(), writer, context);
		if (nf.informacaoContribuinte() != null)
			convertIf("infCpl", nf.informacaoContribuinte().texto(), writer, context);
		writer.endNode();
		
		convertIf("exporta",nf.exportacao(),writer,context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		NotaFiscalId notaFiscalId=new NotaFiscalId("1");
		ChaveAcesso chaveAcesso=null;
		String naturezaOperacao=null;
		FormaPagamento formaPagamento=null;
		Modelo modelo=null;
		Serie serie=null;
		Long numero=null;
		Date emissao=null;
		Date dataHora=null;
		TipoOperacao tipoOperacao=null;
		LocalDestino localDestino=null;
		Municipio municipioFatoGerador=null;
		boolean consumidorFinal=false;
		Finalidade finalidade=null;
		Presenca presenca=null;
		Processo processo=null;
		Set<Referencia> referencias=null;
		Emitente emitente=null;
		Destinatario destinatario=null;
		LocalRetirada localRetirada=null;
		LocalEntrega localEntrega=null;
		List<Item> itens=null;
		Transporte transporte=null;
		Cobranca cobranca=null;
		Informacao informacaoFisco=null;
		Informacao informacaoContribuinte=null;
		Exportacao exportacao=null;
		
		//<infNFe>
		//<ide>
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				if ("cUF".equals(reader.getNodeName())) 
					//<cUF>31</cUF>
					municipioFatoGerador = (Municipio) context.convertAnother(reader.getValue(), Municipio.class);
				if ("cNF".equals(reader.getNodeName())) {
					//<cNF>3</cNF>
					chaveAcesso = (ChaveAcesso) context.convertAnother(reader.getValue(), ChaveAcesso.class);
			/*			<natOp>VENDA DE PRODUTOS ADQ. TERCEIROS</natOp>
						<indPag>1</indPag>
						<mod>55</mod>
						<serie>1</serie>
						<nNF>19936</nNF>
						<dEmi>2013-10-25</dEmi>
						<dSaiEnt>2013-10-25</dSaiEnt>
						<tpNF>1</tpNF>
						<idDest>1</idDest>
						<cMunFG>3111606</cMunFG>
						<tpImp>1</tpImp>
						<tpEmis>1</tpEmis>
						<cDV>0</cDV>
						<tpAmb>2</tpAmb>
						<finNFe>1</finNFe>
						<indFinal>0</indFinal>
						<indPres>9</indPres>
						<procEmi>0</procEmi>
						<verProc>1.0</verProc>
					</ide>
					<emit>
						<CNPJ>16832651000420</CNPJ>
						<xNome>COOPERATIVA DOS CAF. DE CAMPOS GERAIS E CAMPO DO MEIO LTDA</xNome>
						<xFant>COOPERCAM</xFant>
						<enderEmit>
							<xLgr>R JOSE PEDRO DE ARAUJO</xLgr>
							<nro>448</nro>
							<xBairro>DIST. CORREGO DO OURO</xBairro>
							<cMun>3111606</cMun>
							<xMun>CAMPOS GERAIS</xMun>
							<UF>MG</UF>
							<CEP>37163000</CEP>
							<cPais>1058</cPais>
							<xPais>BRASIL</xPais>
							<fone>3538535044</fone>
						</enderEmit>
						<IE>1163598340341</IE>
						<CRT>3</CRT>
					</emit>
					<dest>
						<CPF>72014253668</CPF>
						<xNome>DENISIO DONIZETE TEODORO</xNome>
						<enderDest>
							<xLgr>ESTRADA CORREGO DO OURO PARA FAMA KM 02</xLgr>
							<nro>S/N</nro>
							<xBairro>ZONA RURAL</xBairro>
							<cMun>3111606</cMun>
							<xMun>CAMPOS GERAIS</xMun>
							<UF>MG</UF>
							<CEP>37160000</CEP>
							<cPais>1058</cPais>
							<xPais>BRASIL</xPais>
							<fone>3599716587</fone>
						</enderDest>
						<indIEDest>1</indIEDest>
						<IE>0012134380047</IE>
						<ISUF>123456</ISUF>
						<email>hadrion@hadrion.com.br</email>
					</dest>
					<retirada>
						<CNPJ>86675642000106</CNPJ>
						<xLgr>RODOVIA BR 020 KM 449</xLgr>
						<nro>S/N</nro>
						<xCpl>F.ASA BRAN</xCpl>
						<xBairro>RODA VELHA</xBairro>
						<cMun>2928901</cMun>
						<xMun>SAO DESIDERIO</xMun>
						<UF>BA</UF>
					</retirada>
					<entrega>
						<CNPJ>86675642000106</CNPJ>
						<xLgr>RODOVIA BR 020 KM 449</xLgr>
						<nro>S/N</nro>
						<xCpl>F.ASA BRAN</xCpl>
						<xBairro>RODA VELHA</xBairro>
						<cMun>2928901</cMun>
						<xMun>SAO DESIDERIO</xMun>
						<UF>BA</UF>
					</entrega>
					<det nItem="1">
						<prod>
							<cProd>00000000000000006152</cProd>
							<cEAN>12345678</cEAN>
							<xProd>SOJA EM GRAOS DEPOSITO</xProd>
							<NCM>31052000</NCM>
							<NVE>ABCDEF</NVE>
							<EXTIPI>123</EXTIPI>
							<CFOP>5102</CFOP>
							<uCom>SC</uCom>
							<qCom>30.0000</qCom>
							<vUnCom>59.7716666667</vUnCom>
							<vProd>1793.15</vProd>
							<cEANTrib>12345678</cEANTrib>
							<uTrib>SC</uTrib>
							<qTrib>30.0000</qTrib>
							<vUnTrib>59.7716666667</vUnTrib>
							<vFrete>100.00</vFrete>
							<vSeg>99.00</vSeg>
							<vDesc>88.00</vDesc>
							<vOutro>77.00</vOutro>
							<indTot>1</indTot>
							<detExport>
								<nDraw>12345678901</nDraw>
								<exportInd>
									<nRE>123456789012</nRE>
									<chNFe>29140600891206000310550010000110017000481161</chNFe>
									<qExport>50.1234</qExport>
								</exportInd>
							</detExport>
							<comb>
								<cProdANP>123456789</cProdANP>
								<qTemp>568.1234</qTemp>
								<UFCons>35</UFCons>
								<CIDE>
									<qBCProd>500.78</qBCProd>
									<vAliqProd>18.00</vAliqProd>
									<vCIDE>90.14</vCIDE>
								</CIDE>
							</comb>			
						</prod>
						<imposto>
							<vTotTrib>0.00</vTotTrib>
							<ICMS>
								<ICMS51>
									<orig>0</orig>
									<CST>51</CST>
									<modBC>3</modBC>
									<pRedBC>0.00</pRedBC>
									<vBC>1000.00</vBC>
									<pICMS>0.00</pICMS>
									<vICMSOp>0.00</vICMSOp>
									<pDif>0.00</pDif>
									<vICMSDif>0.00</vICMSDif>
									<vICMS>0.00</vICMS>
								</ICMS51>
							</ICMS>
							<PIS>
								<PISOutr>
									<CST>99</CST>
									<vBC>0.00</vBC>
									<pPIS>0.00</pPIS>
									<vPIS>0.00</vPIS>
								</PISOutr>
							</PIS>
							<COFINS>
								<COFINSOutr>
									<CST>99</CST>
									<vBC>0.00</vBC>
									<pCOFINS>0.00</pCOFINS>
									<vCOFINS>0.00</vCOFINS>
								</COFINSOutr>
							</COFINS>
						</imposto>
						<infAdProd>Informação Adicional</infAdProd>
					</det>
					<total>
						<ICMSTot>
							<vBC>1000.00</vBC>
							<vICMS>0.00</vICMS>
							<vICMSDeson>0.00</vICMSDeson>
							<vBCST>0.00</vBCST>
							<vST>0.00</vST>
							<vProd>1793.15</vProd>
							<vFrete>100.00</vFrete>
							<vSeg>99.00</vSeg>
							<vDesc>88.00</vDesc>
							<vII>0.00</vII>
							<vIPI>0.00</vIPI>
							<vPIS>0.00</vPIS>
							<vCOFINS>0.00</vCOFINS>
							<vOutro>77.00</vOutro>
							<vNF>1981.15</vNF>
							<vTotTrib>0.00</vTotTrib>
						</ICMSTot>
					</total>
					<transp>
					  <modFrete>1</modFrete>
					  <transporta>
					    <CPF>57133239191</CPF>
					    <xNome>JAIR FRIZON</xNome>
					    <xEnder>RUA CASEMIRO DE ABREU 256</xEnder>
					    <xMun>RIBEIRAO PRETO</xMun>
					    <UF>35</UF>
					  </transporta>
					  <veicTransp>
					    <placa>KEP2310</placa>
					    <UF>52</UF>
					  </veicTransp>
					  <vol>
					    <qVol>37220</qVol>
					    <esp>KG</esp>
					    <pesoL>37220.000</pesoL>
					    <pesoB>37220.000</pesoB>
					  </vol>
					</transp>
					<cobr>
						<fat>
							<nFat>DCO-19936</nFat>
							<vOrig>1732.50</vOrig>
							<vDesc>0.50</vDesc>
							<vLiq>1732.00</vLiq>
						</fat>
						<dup>
							<nDup>DCO-19936/1</nDup>
							<dVenc>2013-10-28</dVenc>
							<vDup>1732.00</vDup>
						</dup>
					</cobr>
					<infAdic>
						<infAdFisco>ICMS DIFERIDO CONFORME ITEM 25 , PARTE 1 DO ANEXO II, ARTIGO 8 DO DECRETO 43.080/2002.</infAdFisco>
						<infCpl>FAVOR EFETUAR A RETIRADA NO PRAZO MÁXIMO DE 24 HORAS</infCpl>
					</infAdic>
					<exporta>
						<UFSaidaPais>35</UFSaidaPais>
						<xLocExporta>LOCAL DE EXPORTACAO</xLocExporta>
						<xLocDespacho>LOCAL DE DESPACHO</xLocDespacho>
					</exporta>
				</infNFe>*/
				}
				reader.moveUp();
			}
			reader.moveUp();
		}	
			return new NotaFiscal(notaFiscalId, chaveAcesso, naturezaOperacao, 
				formaPagamento, modelo, serie, numero, emissao, dataHora, 
				tipoOperacao, localDestino, municipioFatoGerador, consumidorFinal, 
				finalidade, presenca, processo, referencias, emitente, 
				destinatario, localRetirada, localEntrega, itens, transporte, 
				cobranca, informacaoFisco, informacaoContribuinte, exportacao);
	}

}
