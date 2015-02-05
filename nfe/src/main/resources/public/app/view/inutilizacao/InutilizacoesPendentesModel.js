Ext.define('nfe.view.inutilizacao.InutilizacoesPendentesModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.inutilizacoes-pendentes',

    stores:{
    	inutilizacoesPendentes:{
    		model: 'Inutilizacao',
    		autoLoad: true,
            proxy:{
                url:'inutilizacao/pendentes',
                type: 'ajax',
                extraParams:{
                	"ambiente":"{ambiente}",
                    "filialId":"{filial}"
                },
                reader: {
                    type: 'json'
                }
            }
    	}
    }
    
});