package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Adicao;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.ImportacaoItem;
import com.hadrion.util.DataUtil;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ImportacaoItemConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return ImportacaoItem.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ImportacaoItem imp = (ImportacaoItem) source;
		convert("nDI",imp.numero(),writer,context);
		convert("dDI",DataUtil.formatarData(imp.emissao()),writer,context);
		convert("xLocDesemb",imp.localDesembarque(),writer,context);
		convert("UFDesemb",imp.ufDesembarque().toString(),writer,context);
		convert("dDesemb",DataUtil.formatarData(imp.dataDesembarque()),writer,context);
		convert("tpViaTransp",imp.viaTransporte(),writer,context);  
		imp.valorArfmm().ifPresent(v->convert("vAFRMM",v,writer,context));
		convert("tpIntermedio",imp.intermediacao().codigo(),writer,context);
		imp.cnpjTerceiro().ifPresent(c->convert("CNPJ",c,writer,context));
		imp.ufTerceiro().ifPresent(u->convert("UFTerceiro",u.toString(),writer,context));
		
		convert("cExportador",imp.codigoExportador(),writer,context);    
		for (Adicao adicao : imp.obterAdicoes()){
			writer.startNode("adi");
			context.convertAnother(adicao);
			writer.endNode();
		}		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {		
		return null;
		
	}
}