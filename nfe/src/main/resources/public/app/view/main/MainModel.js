/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('nfe.view.main.MainModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.main',

    data: {
        name: 'nfe',
        titulo: 'Nota Fiscal Eletrônica - 3.10',
        empresa: undefined,//'86675642000106',//coopercam
        filial: undefined,//'53-86675642000106',
        ambiente: undefined,
        modoOperacao: undefined,        
        notista: '',
        usuario: 'hadrion' 
    },
    stores:{
    	empresaFilial:{
    		fields: ['filialId','cnpj','nome','empresaId'],	
    		autoLoad: true,
            proxy:{
                url : 'filial/obter',
                type: 'ajax',
                reader: {
                    type: 'json',
                    rootProperty: 'rows'
                }
            }
    	},
    	notistas:{
    		model: 'Notista',
    		autoLoad: true
    	}
    },
    formulas: {
    	ambienteHomologacao: {
            bind: {
            	ambiente: '{ambiente}'
            },

            get: function (data) {
                return data.ambiente == 'HOMOLOGACAO';
            }
        },
        modoOperacaoNormal: {
            bind: {
            	modoOperacao: '{modoOperacao}'
            },

            get: function (data) {
                return data.modoOperacao == 'NORMAL';
            }
        },
        mensagemContingencia: {
            bind: {
            	dataHoraContingencia: '{dataHoraContingencia}',
            	justificativaContingencia: '{justificativaContingencia}'
            },

            get: function (data) {
                return Ext.Date.format(data.dataHoraContingencia,'d/m/Y H:i') +
                	' - ' + data.justificativaContingencia;
            }
        }
    }
});
