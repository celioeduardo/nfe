Ext.define('nfe.view.configuracao.ConfiguracaoModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.configuracao',

    stores:{
    	modosOperacao:{
            autoLoad: true,
            fields: ['id', 'name'],
            data: [
       			{"id":"NORMAL", "name":"Normal"},
       			{"id":"FS_DA", "name":"FS-DA"},
       			{"id":"SVC", "name":"SVC"}
       	    ]
    	},
    	ambientes:{
    		autoLoad: true,
            fields: ['id', 'name'],
            data: [
       			{"id":"HOMOLOGACAO", "name":"Homologação"},
       			{"id":"PRODUCAO", "name":"Produção"}
       	    ]
    	}
    },

    formulas:{
        modoOperacaoSelecionado: {
            bind: {
            	modoOperacao: '{modoOperacao}'
            },

            get: function (data) {
                return data.modoOperacao;
            }
        },
        ambienteSelecionado: {
            bind: {
            	ambiente: '{ambiente}'
            },

            get: function (data) {
                return data.ambiente;
            }
        }
    }
});