package com.hadrion.nfe.port.adapters.portal.inutilizacao;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.custommonkey.xmlunit.XMLAssert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.RetornoInutilizacao;
import com.hadrion.util.DataUtil;
import com.hadrion.util.xml.XmlUtil;

public class RetornoInutilizacaoDeserializadorTest {
	
	public static final String XML_ENVIO ="<nfeDadosMsg>\n" + 
			"  <inutNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"2.00\">\n" + 
			"    <infInut Id=\"ID31158667564200010655001000000100000000100\">\n" + 
			"      <tpAmb>2</tpAmb>\n" + 
			"      <xServ>INUTILIZAR</xServ>\n" + 
			"      <cUF>31</cUF>\n" + 
			"      <ano>15</ano>\n" + 
			"      <CNPJ>86675642000106</CNPJ>\n" + 
			"      <mod>55</mod>\n" + 
			"      <serie>1</serie>\n" + 
			"      <nNFIni>100</nNFIni>\n" + 
			"      <nNFFin>100</nNFFin>\n" + 
			"      <xJust>Justificativa de inutilização</xJust>\n" + 
			"    </infInut>\n" + 
			"    <Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" + 
			"      <SignedInfo xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" + 
			"        <CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" />\n" + 
			"        <SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" />\n" + 
			"        <Reference URI=\"#ID31158667564200010655001000000100000000100\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" + 
			"          <Transforms xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" + 
			"            <Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" />\n" + 
			"            <Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" />\n" + 
			"          </Transforms>\n" + 
			"          <DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" />\n" + 
			"          <DigestValue xmlns=\"http://www.w3.org/2000/09/xmldsig#\">pmvOwnYDhcwrF/7vC51rm/LN97c=</DigestValue>\n" + 
			"        </Reference>\n" + 
			"      </SignedInfo>\n" + 
			"      <SignatureValue xmlns=\"http://www.w3.org/2000/09/xmldsig#\">RNLSElK3NAoRtlCkQGtgH2bMdhUItmC888Epwoex4UMRg9M4Kz/9KdA9ePePQHvrzBmeLASjjC1S N8hAvtzVJpMobGfo+4zdULPVOne/xBVVDet2crxkeZ7cq6FDvQZvvVP+lahgsPaG3Ojxo6HGsGxr 5zepJs7MV1sHGIPOGyDcmKMHZjVjf2+/3zoTAwdgnzXILWiI9tc4CRaS2++BP39R+kwXYN/ywH/z l+72PnVXosinm/ll1tn+8pS79cer6EmKiOkGown3alsHtVbEdQMO5QTKdA4sziPqECJAm1dyokep sZJoClCXWPiiK1kWComIvD3HHT8iaCb6lYuGrw==</SignatureValue>\n" + 
			"      <KeyInfo xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" + 
			"        <X509Data xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" + 
			"          <X509Certificate xmlns=\"http://www.w3.org/2000/09/xmldsig#\">MIIIDDCCBfSgAwIBAgIINjt3COjyYVUwDQYJKoZIhvcNAQELBQAwdTELMAkGA1UEBhMCQlIxEzAR BgNVBAoTCklDUC1CcmFzaWwxNjA0BgNVBAsTLVNlY3JldGFyaWEgZGEgUmVjZWl0YSBGZWRlcmFs IGRvIEJyYXNpbCAtIFJGQjEZMBcGA1UEAxMQQUMgU0VSQVNBIFJGQiB2MjAeFw0xNDA0MTUxNzIy MDBaFw0xNTA0MTUxNzIyMDBaMIHvMQswCQYDVQQGEwJCUjELMAkGA1UECBMCTUcxFTATBgNVBAcT DFNBIE8gR09UQVJETzETMBEGA1UEChMKSUNQLUJyYXNpbDE2MDQGA1UECxMtU2VjcmV0YXJpYSBk YSBSZWNlaXRhIEZlZGVyYWwgZG8gQnJhc2lsIC0gUkZCMRYwFAYDVQQLEw1SRkIgZS1DTlBKIEEx MRMwEQYDVQQLEwpBUiBLUllQVE9OMUIwQAYDVQQDEzlDT09QRVJBVElWQSBBR1JPUEVDVUFSSUEg RE8gQUxUTyBQQVJBTkFJQkE6ODY2NzU2NDIwMDAxMDYwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAw ggEKAoIBAQCKVvSWPy4I1f1K1WhyZxK6sLPPCWU3Uz4KiwKtUdh0gJro/UWzK/EkgmKaUOp1T0+L 5gBzFpSKYrbxiHesbNfUbrqSqo0gbvepW/mkayWQ1KnIvv9M5OqS/K3ahACGn8dUGqaquZPCYiVP 70u1Dm7emY8avxQguMAKles4su/6Zh6gNpQGFpBErsW+bMPpRVWKEuhDk1vfJgY+wpYnMoS5cQH0 0QuLrULH6B48DpGmE7dT2RAhsELxFJAVe2PGLWLS+XWlfxb127ZUShbwUStk60odg4RXSwPg6NOg 8MmKnf7ZeLA2Cpu4eY9POirENGWb2+0PGj+0l3rR52iti8WTAgMBAAGjggMjMIIDHzCBmQYIKwYB BQUHAQEEgYwwgYkwSAYIKwYBBQUHMAKGPGh0dHA6Ly93d3cuY2VydGlmaWNhZG9kaWdpdGFsLmNv bS5ici9jYWRlaWFzL3NlcmFzYXJmYnYyLnA3YjA9BggrBgEFBQcwAYYxaHR0cDovL29jc3AuY2Vy dGlmaWNhZG9kaWdpdGFsLmNvbS5ici9zZXJhc2FyZmJ2MjAJBgNVHRMEAjAAMB8GA1UdIwQYMBaA FLKgxD1GnnzIhWwIHhAylGVGcEFzMHEGA1UdIARqMGgwZgYGYEwBAgENMFwwWgYIKwYBBQUHAgEW Tmh0dHA6Ly9wdWJsaWNhY2FvLmNlcnRpZmljYWRvZGlnaXRhbC5jb20uYnIvcmVwb3NpdG9yaW8v ZHBjL2RlY2xhcmFjYW8tcmZiLnBkZjCB8wYDVR0fBIHrMIHoMEqgSKBGhkRodHRwOi8vd3d3LmNl cnRpZmljYWRvZGlnaXRhbC5jb20uYnIvcmVwb3NpdG9yaW8vbGNyL3NlcmFzYXJmYnYyLmNybDBE oEKgQIY+aHR0cDovL2xjci5jZXJ0aWZpY2Fkb3MuY29tLmJyL3JlcG9zaXRvcmlvL2xjci9zZXJh c2FyZmJ2Mi5jcmwwVKBSoFCGTmh0dHA6Ly9yZXBvc2l0b3Jpby5pY3BicmFzaWwuZ292LmJyL2xj ci9TZXJhc2EvcmVwb3NpdG9yaW8vbGNyL3NlcmFzYXJmYnYyLmNybDAOBgNVHQ8BAf8EBAMCBeAw HQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMEMIG8BgNVHREEgbQwgbGBGURJUkVUT1JJQUBD T09QQURBUC5DT00uQlKgIQYFYEwBAwKgGBMWTUFSQ0VMTyBBS0lISVJPIE1PUklUQaAZBgVgTAED A6AQEw44NjY3NTY0MjAwMDEwNqA9BgVgTAEDBKA0EzIzMTAxMTk3Mzk3MzIyMzkzNjAwMDAwMDAw MDAwMDAwMDAwMDAyMjgzNDA4NDZTU1BTUKAXBgVgTAEDB6AOEwwwMDAwMDAwMDAwMDAwDQYJKoZI hvcNAQELBQADggIBAK7DiLbmqOhdflZK/XykK1VfDFBE4Qed//idl/TtXm6CNT2CY2G5WTUKU619 mc53Q80ANzu41GV4QW9JHxwURpsignUnEkFZIhWRowNdU1Ru075ujW593yeyiS7QPKZqykfOEh7G +nk355nEeOfsM0EbjFp8D21a3puoLJZpvAMBLX7qBUtEkdZug/186vDy+RV0dQJprabap9w5p+vT 2OmbzWFZ5IF4SYlZEh6yd9Oh9oB8AaTxs94nQid/WJqaGs1566WOhzaRvWoqBM6yQVNy4TedF57s m8lV1hQHkZg84MvQRjtpb/e/ldnMDCV4QGPwsAOLo2zF+d9kbpJCqALv085D8vYhaEW1+ItDRJlh /kK3yfTgUQj0ShVPcST+FeNOw9Ugcf+bWSrCd4i2pS9wbwa36HEI6Fauq5M1dk/BqqbnGHb1m1dd raT5vgaVp/pwO47OPUSIvdPGbzfbAicZ+E+RtU14zmgjhSuWkyMXEjhhV2/WatLGAEDDnTBQWDLx RwwAD7tEILwkwY33zOSdpxYAgtT0ipHRZXZyid86YR5STdCq5EcHiKkDRJQMKg58f3gx7v7rjzIE dTOO5zqKFdFU5aw1gbgExRD/p0vlFO+VkR+sJI6O1Y2eiOT7r7vX//PxbzOe95fnmb7uixaf8t74 V2EmfPv1II/j3NfJ</X509Certificate>\n" + 
			"        </X509Data>\n" + 
			"      </KeyInfo>\n" + 
			"    </Signature>\n" + 
			"  </inutNFe>\n" + 
			"</nfeDadosMsg>";
	
	public static final String XML_RETORNO_OK = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
			"<nfeInutilizacaoNF2Result xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2\">\n" + 
			"  <retInutNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" versao=\"2.00\">\n" + 
			"    <infInut>\n" + 
			"      <tpAmb>2</tpAmb>\n" + 
			"      <verAplic>13_2_55</verAplic>\n" + 
			"      <cStat>102</cStat>\n" + 
			"      <xMotivo>Inutilizacao de numero homologado</xMotivo>\n" + 
			"      <cUF>31</cUF>\n" + 
			"      <ano>15</ano>\n" + 
			"      <CNPJ>86675642000106</CNPJ>\n" + 
			"      <mod>55</mod>\n" + 
			"      <serie>1</serie>\n" + 
			"      <nNFIni>100</nNFIni>\n" + 
			"      <nNFFin>100</nNFFin>\n" + 
			"      <dhRecbto>2015-02-03T17:54:36</dhRecbto>\n" + 
			"      <nProt>131150045959856</nProt>\n" + 
			"    </infInut>\n" + 
			"  </retInutNFe>\n" + 
			"</nfeInutilizacaoNF2Result>";
	
	RetornoInutilizacaoDeserializador deserializador;
	
	@Test
	public void deserializarEventoHomologado() throws SAXException, IOException{
		deserializador = new RetornoInutilizacaoDeserializador(XmlUtil.parseXml(XML_ENVIO),XML_RETORNO_OK);
		RetornoInutilizacao retornoInutilizacao = deserializador.deserializar();
		
		XMLAssert.assertXMLEqual(XML_ENVIO,retornoInutilizacao.xmlEnvio());
		XMLAssert.assertXMLEqual(XML_RETORNO_OK,retornoInutilizacao.xmlRetorno());
		
		assertEquals(
			new RetornoInutilizacao(
					new NumeroProtocolo("131150045959856"), 
					new Mensagem(102,"Inutilizacao de numero homologado"), 
					DataUtil.dataHora("03/02/2015 17:54:36"),
					XML_ENVIO,
					XML_RETORNO_OK),
			retornoInutilizacao);
	}
	
	
}
