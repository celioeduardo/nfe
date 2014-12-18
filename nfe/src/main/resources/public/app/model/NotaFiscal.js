Ext.define('nfe.model.NotaFiscal', {
    extend: 'nfe.model.Base',
    idProperty: 'notaFiscalId',
    fields: ['numero',
             'serie',
		    {name: 'emissao', type: 'date'},
		    'valor',
		    'publicoTipo',
		    'publicoCodigo',
		    'publicoNome',
		    'tipo'],
    
    proxy: {
        url : 'notas_fiscais/pendentes_autorizacao_resumo'
    },

    enviarNotas:function(ambiente,notas,success,failure,callback,scope){
        var data = {
            "ambiente" : ambiente,
            "ids":notas
        };

        Ext.Ajax.request({
            url:'notas_fiscais/enviar',
            method:'POST',
            jsonData:Ext.encode([data]),
            success:success,
            failure:failure,
            callback:callback,
            scope:scope
        });
    }


});