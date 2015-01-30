package com.hadrion.nfe.port.adapters.portal.evento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoEvento;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoLoteEvento;
import com.hadrion.util.DataUtil;
import com.hadrion.util.xml.XmlUtil;

public class RetornoEventoDeserializadorTest {
	
	public static final String XML_ENVIO ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
			"<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">\n" + 
			"  <envEvento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">\n" + 
			"    <idLote>1</idLote>\n" + 
			"    <evento versao=\"1.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">\n" + 
			"      <infEvento Id=\"ID1101103114128667564200010655002000204886100000001110\">\n" + 
			"        <cOrgao>31</cOrgao>\n" + 
			"        <tpAmb>2</tpAmb>\n" + 
			"        <CNPJ>86675642000106</CNPJ>\n" + 
			"        <chNFe>31141286675642000106550020002048861000000011</chNFe>\n" + 
			"        <dhEvento>2015-01-30T17:36:40-02:00</dhEvento>\n" + 
			"        <tpEvento>110110</tpEvento>\n" + 
			"        <nSeqEvento>10</nSeqEvento>\n" + 
			"        <verEvento>1.00</verEvento>\n" + 
			"        <detEvento versao=\"1.00\">\n" + 
			"          <descEvento>Carta de Correcao</descEvento>\n" + 
			"          <xCorrecao>Teste de Carta de Correção</xCorrecao>\n" + 
			"          <xCondUso>A Carta de Correção é disciplinada pelo § 1º-A do art. 7º do Convênio S/N, de 15 de dezembro de 1970 e pode ser utilizada para regularização de erro ocorrido na emissão de documento fiscal, desde que o erro não esteja relacionado com: I - as variáveis que determinam o valor do imposto tais como: base de cálculo, alíquota, diferença de preço, quantidade, valor da operação ou da prestação; II - a correção de dados cadastrais que implique mudança do remetente ou do destinatário; III - a data de emissão ou de saída.</xCondUso>\n" + 
			"        </detEvento>\n" + 
			"      </infEvento>\n" + 
			"      <Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" + 
			"        <SignedInfo>\n" + 
			"          <CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" />\n" + 
			"          <SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" />\n" + 
			"          <Reference URI=\"#ID1101103114128667564200010655002000204886100000001110\">\n" + 
			"            <Transforms>\n" + 
			"              <Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" />\n" + 
			"              <Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" />\n" + 
			"            </Transforms>\n" + 
			"            <DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" />\n" + 
			"            <DigestValue>3Pt34ObHIcF+jYJZc4eryCfU1gk=</DigestValue>\n" + 
			"          </Reference>\n" + 
			"        </SignedInfo>\n" + 
			"        <SignatureValue>Ck34Nda0w7t1xakMYOGzCx9zVuY2o/kV/XkPz6XkFE+bie1+qSquyaOdXL/OjjD6q1R0Tkz9P6mD u1BM9lfq91iJ9EPt3/2D9DoPGlPjMF5fdKm7DKGwRzGWuf5IxUjLHMtbULGVvWkQHhCe4yyqTuaD HGPVxfgFRNNeSCwvg4iomOiL5bfQiVPaqKs1bLgaGC2fFB2xhAgoVsqsaO/ZzIED7OYE3jxFEl00 R709ODfPBnrZ56D84thMDAB7g5MrGRCHz4UEUl00xsmewU4N6mrVM/ijO6EPErKjP3gVVZWhLzWX qZ4eN7W7SjKV094K8Piv4sOM84MsHFUl6N4SYQ==</SignatureValue>\n" + 
			"        <KeyInfo>\n" + 
			"          <X509Data>\n" + 
			"            <X509Certificate>MIIIDDCCBfSgAwIBAgIINjt3COjyYVUwDQYJKoZIhvcNAQELBQAwdTELMAkGA1UEBhMCQlIxEzAR BgNVBAoTCklDUC1CcmFzaWwxNjA0BgNVBAsTLVNlY3JldGFyaWEgZGEgUmVjZWl0YSBGZWRlcmFs IGRvIEJyYXNpbCAtIFJGQjEZMBcGA1UEAxMQQUMgU0VSQVNBIFJGQiB2MjAeFw0xNDA0MTUxNzIy MDBaFw0xNTA0MTUxNzIyMDBaMIHvMQswCQYDVQQGEwJCUjELMAkGA1UECBMCTUcxFTATBgNVBAcT DFNBIE8gR09UQVJETzETMBEGA1UEChMKSUNQLUJyYXNpbDE2MDQGA1UECxMtU2VjcmV0YXJpYSBk YSBSZWNlaXRhIEZlZGVyYWwgZG8gQnJhc2lsIC0gUkZCMRYwFAYDVQQLEw1SRkIgZS1DTlBKIEEx MRMwEQYDVQQLEwpBUiBLUllQVE9OMUIwQAYDVQQDEzlDT09QRVJBVElWQSBBR1JPUEVDVUFSSUEg RE8gQUxUTyBQQVJBTkFJQkE6ODY2NzU2NDIwMDAxMDYwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAw ggEKAoIBAQCKVvSWPy4I1f1K1WhyZxK6sLPPCWU3Uz4KiwKtUdh0gJro/UWzK/EkgmKaUOp1T0+L 5gBzFpSKYrbxiHesbNfUbrqSqo0gbvepW/mkayWQ1KnIvv9M5OqS/K3ahACGn8dUGqaquZPCYiVP 70u1Dm7emY8avxQguMAKles4su/6Zh6gNpQGFpBErsW+bMPpRVWKEuhDk1vfJgY+wpYnMoS5cQH0 0QuLrULH6B48DpGmE7dT2RAhsELxFJAVe2PGLWLS+XWlfxb127ZUShbwUStk60odg4RXSwPg6NOg 8MmKnf7ZeLA2Cpu4eY9POirENGWb2+0PGj+0l3rR52iti8WTAgMBAAGjggMjMIIDHzCBmQYIKwYB BQUHAQEEgYwwgYkwSAYIKwYBBQUHMAKGPGh0dHA6Ly93d3cuY2VydGlmaWNhZG9kaWdpdGFsLmNv bS5ici9jYWRlaWFzL3NlcmFzYXJmYnYyLnA3YjA9BggrBgEFBQcwAYYxaHR0cDovL29jc3AuY2Vy dGlmaWNhZG9kaWdpdGFsLmNvbS5ici9zZXJhc2FyZmJ2MjAJBgNVHRMEAjAAMB8GA1UdIwQYMBaA FLKgxD1GnnzIhWwIHhAylGVGcEFzMHEGA1UdIARqMGgwZgYGYEwBAgENMFwwWgYIKwYBBQUHAgEW Tmh0dHA6Ly9wdWJsaWNhY2FvLmNlcnRpZmljYWRvZGlnaXRhbC5jb20uYnIvcmVwb3NpdG9yaW8v ZHBjL2RlY2xhcmFjYW8tcmZiLnBkZjCB8wYDVR0fBIHrMIHoMEqgSKBGhkRodHRwOi8vd3d3LmNl cnRpZmljYWRvZGlnaXRhbC5jb20uYnIvcmVwb3NpdG9yaW8vbGNyL3NlcmFzYXJmYnYyLmNybDBE oEKgQIY+aHR0cDovL2xjci5jZXJ0aWZpY2Fkb3MuY29tLmJyL3JlcG9zaXRvcmlvL2xjci9zZXJh c2FyZmJ2Mi5jcmwwVKBSoFCGTmh0dHA6Ly9yZXBvc2l0b3Jpby5pY3BicmFzaWwuZ292LmJyL2xj ci9TZXJhc2EvcmVwb3NpdG9yaW8vbGNyL3NlcmFzYXJmYnYyLmNybDAOBgNVHQ8BAf8EBAMCBeAw HQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMEMIG8BgNVHREEgbQwgbGBGURJUkVUT1JJQUBD T09QQURBUC5DT00uQlKgIQYFYEwBAwKgGBMWTUFSQ0VMTyBBS0lISVJPIE1PUklUQaAZBgVgTAED A6AQEw44NjY3NTY0MjAwMDEwNqA9BgVgTAEDBKA0EzIzMTAxMTk3Mzk3MzIyMzkzNjAwMDAwMDAw MDAwMDAwMDAwMDAyMjgzNDA4NDZTU1BTUKAXBgVgTAEDB6AOEwwwMDAwMDAwMDAwMDAwDQYJKoZI hvcNAQELBQADggIBAK7DiLbmqOhdflZK/XykK1VfDFBE4Qed//idl/TtXm6CNT2CY2G5WTUKU619 mc53Q80ANzu41GV4QW9JHxwURpsignUnEkFZIhWRowNdU1Ru075ujW593yeyiS7QPKZqykfOEh7G +nk355nEeOfsM0EbjFp8D21a3puoLJZpvAMBLX7qBUtEkdZug/186vDy+RV0dQJprabap9w5p+vT 2OmbzWFZ5IF4SYlZEh6yd9Oh9oB8AaTxs94nQid/WJqaGs1566WOhzaRvWoqBM6yQVNy4TedF57s m8lV1hQHkZg84MvQRjtpb/e/ldnMDCV4QGPwsAOLo2zF+d9kbpJCqALv085D8vYhaEW1+ItDRJlh /kK3yfTgUQj0ShVPcST+FeNOw9Ugcf+bWSrCd4i2pS9wbwa36HEI6Fauq5M1dk/BqqbnGHb1m1dd raT5vgaVp/pwO47OPUSIvdPGbzfbAicZ+E+RtU14zmgjhSuWkyMXEjhhV2/WatLGAEDDnTBQWDLx RwwAD7tEILwkwY33zOSdpxYAgtT0ipHRZXZyid86YR5STdCq5EcHiKkDRJQMKg58f3gx7v7rjzIE dTOO5zqKFdFU5aw1gbgExRD/p0vlFO+VkR+sJI6O1Y2eiOT7r7vX//PxbzOe95fnmb7uixaf8t74 V2EmfPv1II/j3NfJ</X509Certificate>\n" + 
			"          </X509Data>\n" + 
			"        </KeyInfo>\n" + 
			"      </Signature>\n" + 
			"    </evento>\n" + 
			"  </envEvento>\n" + 
			"</nfeDadosMsg>";
	
	public static final String XML_EVENTO_REGISTRADO = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
			"<nfeRecepcaoEventoResult xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">\n" + 
			"  <retEnvEvento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">\n" + 
			"    <idLote>1</idLote>\n" + 
			"    <tpAmb>2</tpAmb>\n" + 
			"    <verAplic>13_2_55</verAplic>\n" + 
			"    <cOrgao>31</cOrgao>\n" + 
			"    <cStat>128</cStat>\n" + 
			"    <xMotivo>Lote de Evento Processado.</xMotivo>\n" + 
			"    <retEvento versao=\"1.00\">\n" + 
			"      <infEvento Id=\"ID131150045789855\">\n" + 
			"        <tpAmb>2</tpAmb>\n" + 
			"        <verAplic>13_2_55</verAplic>\n" + 
			"        <cOrgao>31</cOrgao>\n" + 
			"        <cStat>135</cStat>\n" + 
			"        <xMotivo>Evento registrado e vinculado a NF-e.</xMotivo>\n" + 
			"        <chNFe>31141286675642000106550020002048861000000011</chNFe>\n" + 
			"        <tpEvento>110110</tpEvento>\n" + 
			"        <xEvento>CARTA DE CORRECAO</xEvento>\n" + 
			"        <nSeqEvento>10</nSeqEvento>\n" + 
			"        <CNPJDest>99999999000191</CNPJDest>\n" + 
			"        <dhRegEvento>2015-01-30T17:52:28-02:00</dhRegEvento>\n" + 
			"        <nProt>131150045789855</nProt>\n" + 
			"      </infEvento>\n" + 
			"    </retEvento>\n" + 
			"  </retEnvEvento>\n" + 
			"</nfeRecepcaoEventoResult>";
	
	public static final String XML_EVENTO_PROCESSADO = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
			"<nfeRecepcaoEventoResult xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">\n" + 
			"  <retEnvEvento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">\n" + 
			"    <idLote>1</idLote>\n" + 
			"    <tpAmb>2</tpAmb>\n" + 
			"    <verAplic>13_2_55</verAplic>\n" + 
			"    <cOrgao>31</cOrgao>\n" + 
			"    <cStat>128</cStat>\n" + 
			"    <xMotivo>Lote de Evento Processado.</xMotivo>\n" + 
			"    <retEvento versao=\"1.00\">\n" + 
			"      <infEvento Id=\"ID131150045789855\">\n" + 
			"        <tpAmb>2</tpAmb>\n" + 
			"        <verAplic>13_2_55</verAplic>\n" + 
			"        <cOrgao>31</cOrgao>\n" + 
			"        <cStat>135</cStat>\n" + 
			"        <xMotivo>Evento registrado e vinculado a NF-e.</xMotivo>\n" + 
			"        <chNFe>31141286675642000106550020002048861000000011</chNFe>\n" + 
			"        <tpEvento>110110</tpEvento>\n" + 
			"        <xEvento>CARTA DE CORRECAO</xEvento>\n" + 
			"        <nSeqEvento>10</nSeqEvento>\n" + 
			"        <CNPJDest>99999999000191</CNPJDest>\n" + 
			"        <dhRegEvento>2015-01-30T17:52:28-02:00</dhRegEvento>\n" + 
			"        <nProt>131150045789855</nProt>\n" + 
			"      </infEvento>\n" + 
			"    </retEvento>\n" + 
			"  </retEnvEvento>\n" + 
			"</nfeRecepcaoEventoResult>";
	
	public static final String XML_EVENTO_REJEITADO = 
			"<nfeRecepcaoEventoResult xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">\n" + 
			"  <retEnvEvento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">\n" + 
			"    <idLote />\n" + 
			"    <tpAmb>2</tpAmb>\n" + 
			"    <verAplic>SVC_2.1.1</verAplic>\n" + 
			"    <cOrgao>31</cOrgao>\n" + 
			"    <cStat>588</cStat>\n" + 
			"    <xMotivo>Rejeicao: Nao e permitida a presenca de caracteres de edicao no inicio/fim da mensagem ou entre as tags da mensagem</xMotivo>\n" + 
			"  </retEnvEvento>\n" + 
			"</nfeRecepcaoEventoResult>";
	
	RetornoLoteEventoDeserializador deserializador;
	
	@Test
	public void deserializarEventoHomologado(){
		deserializador = new RetornoLoteEventoDeserializador(XmlUtil.parseXml(XML_ENVIO),XML_EVENTO_REGISTRADO);
		RetornoLoteEvento retornoLoteEvento = deserializador.deserializar();
		assertEquals(
			new RetornoLoteEvento(new Mensagem(128,"Lote de Evento Processado."), 
				new RetornoEvento(
						new ChaveAcesso("31141286675642000106550020002048861000000011"),
						110110,
						"CARTA DE CORRECAO",
						new Mensagem(135,"Evento registrado e vinculado a NF-e."), 
						DataUtil.dataHora("30/01/2015 17:52:28", "GMT-02:00"),
						new NumeroProtocolo("131150045789855"))),
			retornoLoteEvento);
		System.out.println(retornoLoteEvento.retornoDaNota(new ChaveAcesso("31141286675642000106550020002048861000000011")).xmlEnvio());
		System.out.println(retornoLoteEvento.retornoDaNota(new ChaveAcesso("31141286675642000106550020002048861000000011")).xmlRetorno());
		assertNotNull(retornoLoteEvento.retornoDaNota(new ChaveAcesso("31141286675642000106550020002048861000000011")).xmlEnvio());
		assertNotNull(retornoLoteEvento.retornoDaNota(new ChaveAcesso("31141286675642000106550020002048861000000011")).xmlRetorno());
	}
	@Test
	public void deserializarOk(){
		deserializador = new RetornoLoteEventoDeserializador(null,XML_EVENTO_PROCESSADO);
		assertEquals(
				new RetornoLoteEvento(new Mensagem(128,"Lote de Evento Processado."), 
						new RetornoEvento(
								new ChaveAcesso("31141286675642000106550020002048171000000014"),
								110111,
								"CANCELAMENTO",
								new Mensagem(220,"Rejeicao: Prazo de Cancelamento Superior ao Previsto na Legislacao"), 
								DataUtil.dataHora("27/01/2015 17:47:36", "GMT-02:00"),
								null)),
								deserializador.deserializar());
	}
	
	@Test
	public void deserializarNok(){
		deserializador = new RetornoLoteEventoDeserializador(null,XML_EVENTO_REJEITADO);
		assertEquals(
				new RetornoLoteEvento(new Mensagem(588,"Rejeicao: Nao e permitida a presenca de caracteres de edicao no inicio/fim da mensagem ou entre as tags da mensagem")),
				deserializador.deserializar());
	}
}
