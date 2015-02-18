Ext.define('nfe.model.Inutilizacao', {
    extend: 'nfe.model.Base',
    idProperty: 'notaFiscalId',
    fields: ['numeroInicial',
             'numeroFinal',
             'serie',   
		    {name: 'dataHoraHomologacao', type: 'date'},
		    'justificativa',
		    'msgCodigo',
		    'msgDescricao',
            'filialId'],
    
    proxy: {
        url : 'inutilizacao/pendentes'
    },

    statics: {
    	inutilizar:function(inutilizacaoId,success,failure,callback,scope){
    		Ext.Ajax.request({
    			url:'inutilizacao/inutilizar',
    			method:'POST',
    			timeout: 120000,
    			jsonData:Ext.encode({
	            	'inutilizacaoId':inutilizacaoId
	            }),
    			success:success,
    			failure:failure,
    			callback:callback,
    			scope:scope
    		});
    	}
    }

});
