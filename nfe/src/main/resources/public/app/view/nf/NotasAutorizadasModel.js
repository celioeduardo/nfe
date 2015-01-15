Ext.define('nfe.view.nf.NotasAutorizadasModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.notas-autorizadas',

    stores:{
    	notasAutorizadas:{
    		model: 'NotaFiscal',
    		autoLoad: true,
            autoSync:true,
            proxy:{
                url:'notas_fiscais/autorizadas_resumo',
                type: 'ajax',
                extraParams:{
                	"ambiente":"{ambiente}",
                	"nao_impressa":"{nao_impressa}",
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