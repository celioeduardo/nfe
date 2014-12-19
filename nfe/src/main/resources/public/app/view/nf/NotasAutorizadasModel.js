Ext.define('nfe.view.nf.NotasAutorizadasModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.notas-autorizadas',

    stores:{
    	notasAutorizadas:{
    		model: 'NotaFiscal',
    		autoLoad: true,
            proxy:{
                url:'notas_fiscais/autorizadas_resumo',
                type: 'rest',
                extraParams:{
                	"nao_impressa":"{nao_impressa}",
                    "empresa":"{empresa}",
                    "filial":"{filial}"
                },
                reader: {
                    type: 'json',
                    rootProperty: 'rows'
                }
            }
    	}
    }
    
});