Ext.define('nfe.view.lote.LotesPendentesModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.lotes-pendentes',

    data: {	
        
    },

    stores:{
    	lotesPendentes:{
    		model: 'Lote',
    		autoLoad: true,
            // data:[{
            //     "numeroRecibo" : 1,
            //     "ambiente": 'HOMOLOGACAO'
            // },{
            //     "numeroRecibo" : 123456,
            //     "ambiente": 'HOMOLOGACAO'
            // },{
            //     "numeroRecibo" : 987654321,
            //     "ambiente": 'HOMOLOGACAO'
            // }]
            proxy:{
                url:'lotes/pendentes',
                type: 'rest',
                extraParams:{
                    "empresa":"{empresa}",
                    "filial":"{filial}",
                    "ambiente": "HOMOLOGACAO"
                },
                reader: {
                    type: 'json',
                    rootProperty: 'rows'
                }
            }
    	}
    }
    
});