Ext.define('nfe.model.NotaFiscal', {
    extend: 'nfe.model.Base',
    idProperty: 'notaFiscalId',
    fields: ['numero',
             'serie',
		    {name: 'emissao', type: 'date'},
		    {name: 'dataHoraAutorizacao', type: 'date'},
		    {name: 'dataHoraCancelamento', type: 'date'},
		    'valor',
		    'publicoTipo',
		    'publicoCodigo',
		    'publicoNome',
		    'tipo',
		    'tipoEmissao',
            'filial'],
    
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
            timeout: 120000,
            jsonData:Ext.encode([data]),
            success:success,
            failure:failure,
            callback:callback,
            scope:scope
        });
    },
    
    
    statics: {
    	enviarEmail:function(notaFiscalId,success,failure,callback,scope){
    		Ext.Ajax.request({
    			url:'notas_fiscais/enviar_email',
    			method:'GET',
    			params:{'notaFiscalId':notaFiscalId},
    			success:success,
    			failure:failure,
    			callback:callback,
    			scope:scope
    		});
    	},
    	cancelar:function(notaFiscalId,justificativa,success,failure,callback,scope){
    		Ext.Ajax.request({
    			url:'notas_fiscais/cancelar',
    			method:'POST',
    			jsonData:Ext.encode({
	            	'notaFiscalId':notaFiscalId,
	            	'justificativa':justificativa
	            }),
    			success:success,
    			failure:failure,
    			callback:callback,
    			scope:scope
    		});
    	},
    	registrarCce:function(notaFiscalId,correcao,success,failure,callback,scope){
    		Ext.Ajax.request({
    			url:'notas_fiscais/registrar_cce',
    			method:'POST',
    			jsonData:Ext.encode({
	            	'notaFiscalId':notaFiscalId,
	            	'correcao':correcao
	            }),
    			success:success,
    			failure:failure,
    			callback:callback,
    			scope:scope
    		});
    	},
        getTipoEmissao: function(tipoEmissao) {
            return this.prototype.tipos[tipoEmissao];
        }
    },
    
    tipos: {
        'NORMAL': 'Normal',
        'FS_DA': 'FS-DA',
        'SVC_RS': 'SVC-RS',
        'SVC_AN': 'SVC-AN'
    }

});
