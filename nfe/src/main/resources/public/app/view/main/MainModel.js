/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('nfe.view.main.MainModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.main',

    data: {
        name: 'nfe',
        titulo: 'Nota Fiscal Eletrônica - 3.10',
        empresa: 86675642000106,
        filial: -1
    },
    stores:{
    	empresaFilial:{
    		fields: ['NUM_CNPJ','NOM_CURTO_FILIAL'],		
    		//model: 'NotaFiscal',
    		autoLoad: true,
            proxy:{
                url : 'notas_fiscais/combofilial',
                type: 'rest',
                reader: {
                    type: 'json',
                    rootProperty: 'rows'
                }
            }
    	}
    }

    //TODO - add data, formulas and/or methods to support your view
});