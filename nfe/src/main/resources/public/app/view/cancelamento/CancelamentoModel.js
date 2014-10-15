Ext.define('nfe.view.cancelamento.CancelamentoModel', {
    extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.cancelamento',
    stores:{
    	cancelamento:{
    		fields: ['NUM_CNPJ','NOM_CURTO_FILIAL'],		
    		//model: 'NotaFiscal',
    		autoLoad: true,
            proxy:{
                url : 'notas_fiscais/combofilial',
                type: 'rest',
                reader: {
                    type: 'json',
                    rootProperty: 'rows'
                }
            }
    	}
    }
});