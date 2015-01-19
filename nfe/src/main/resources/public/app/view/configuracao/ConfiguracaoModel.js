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
        aplicarConfiguracao: {
            bind: {
            	modoOperacaoAtual: '{modoOperacao}',
            	modoOperacaoConfig: '{modoOperacaoConfig.value}',
            	dataContingenciaAtual: '{dataContingencia}',
            	dataContingenciaConfig: '{dataContingenciaConfig.value}',
            	horaContingenciaAtual: '{horaContingencia}',
            	horaContingenciaConfig: '{horaContingenciaConfig.value}',
            	justificativaContingenciaAtual: '{justificativaContingencia}',
            	justificativaContingenciaConfig: '{justificativaContingenciaConfig.value}'
            },

            get: function (data) {
            	var result = 
            		((data.modoOperacaoAtual != data.modoOperacaoConfig) ||
            		(data.dataContingenciaAtual != data.dataContingenciaConfig) ||
            		(data.horaContingenciaAtual != data.horaContingenciaConfig) ||
            		(data.justificativaContingenciaAtual != data.justificativaContingenciaConfig));
            	
            	return result;
            }
        },
        contingenciaHabilitada: {
            bind: {
            	modoOperacaoEscolhido: '{modoOperacaoConfig.value}'
            },

            get: function (data) {
            	return data.modoOperacaoEscolhido == 'SVC' || data.modoOperacaoEscolhido == 'FS_DA';
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