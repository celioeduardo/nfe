Ext.define('nfe.model.Lote', {
    extend: 'nfe.model.Base',
    idProperty: 'loteId',
    
    proxy: {
        url : 'lote/pendentes'
    },

    obterRetorno:function(ids,success,failure,callback,scope){
        
        Ext.Ajax.request({
            url:'lotes/processar_retorno',
            method:'POST',
            timeout: 120000,
            jsonData:Ext.encode(ids),
            success:success,
            failure:failure,
            callback:callback,
            scope:scope
        });
    }


});