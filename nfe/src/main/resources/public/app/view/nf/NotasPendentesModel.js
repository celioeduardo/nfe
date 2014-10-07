Ext.define('nfe.view.main.NotasPendentesModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.notas-pendentes',

    data: {	
        titulo: 'NF-e 3.1'
    },

    stores:{
    	notasPendentes:{
    		model: 'NotaFiscal',
    		autoLoad: true
    	}
    }
    
});