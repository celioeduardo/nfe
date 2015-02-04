Ext.define('nfe.view.inutilizacao.InutilizacoesHomologadasModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.inutilizacoes-homologadas',

    stores:{
    	inutilizacoesHomologadas:{
    		model: 'Inutilizacao',
    		autoLoad: true,
            proxy:{
                url:'inutilizacao/homologadas',
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