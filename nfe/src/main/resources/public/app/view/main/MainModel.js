/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('nfe.view.main.MainModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.main',

    data: {
        name: 'nfe',
        titulo: 'Nota Fiscal Eletrônica - 3.10',
        empresa: undefined,
        filial: undefined,
        ambiente: undefined,
        modoOperacao: 'NORMAL',        
        notista: '',
        usuario: undefined
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
    }
});
