Ext.define('nfe.view.lote.LotesPendentesModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.lotes-pendentes',

    data: {	
        
    },

    stores:{
    	lotesPendentes:{
    		model: 'Lote',
    		autoLoad: true,
            autoSync: true,
            proxy:{
                url:'lotes/pendentes',
                type: 'ajax',
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