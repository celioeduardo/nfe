Ext.define('nfe.model.Lote', {
    extend: 'nfe.model.Base',
    idProperty: 'loteId',
    
    proxy: {
        url : 'lote/pendentes'
    },

    obterRetorno:function(ids,success){
        
        Ext.Ajax.request({
            url:'lotes/processar_retorno',
            method:'POST',
            jsonData:Ext.encode(ids),
            success:success
        });
    }


});