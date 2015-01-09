Ext.define('nfe.view.lote.LotesPendentesModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.lotes-pendentes',

    data: {	
        
    },

    stores:{
    	lotesPendentes:{
    		model: 'Lote',
    		autoLoad: true,
            proxy:{
                url:'lotes/pendentes',
                type: 'rest',
                extraParams:{
                	"ambiente": "{ambiente}",
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