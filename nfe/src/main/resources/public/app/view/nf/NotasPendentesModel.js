Ext.define('nfe.view.nf.NotasPendentesModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.notas-pendentes',

    data: {	
        titulo: 'NF-e 3.1'
    },

    stores:{
    	notasPendentes:{
    		model: 'NotaFiscal',
    		autoLoad: true,
            autoSync: true,
            proxy:{
                url:'notas_fiscais/pendentes_autorizacao_resumo',
                type: 'ajax',
                extraParams:{
                	"ambiente":"{ambiente}",
                    "empresa":"{empresa}",
                    "filial":"{filial}",
                    "notista":"{notista}"
                },
                reader: {
                    type: 'json',
                    rootProperty: 'rows'
                }
            }
    	}
    }
    
});