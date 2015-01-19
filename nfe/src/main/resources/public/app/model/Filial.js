Ext.define('nfe.model.Filial', {
    extend: 'nfe.model.Base',
    idProperty: 'filialId',
    fields:['nome','modoOperacao','ambiente'],
    proxy: {
    	type: 'ajax',
        url : 'filial/obter',
        reader: {
            type: 'json',
            rootProperty: ''
        }
    },
    
    statics: {
	    alterarModoOperacao:function(filialId,modoOperacao,
	    		dataHoraContingencia,justificativaContingencia, 
	    		success){
	        Ext.Ajax.request({
	            url:'filial/alterar_modo_operacao',
	            method:'POST',
	            jsonData:Ext.encode({
	            	'filialId':filialId,
	            	'modoOperacao':modoOperacao,
	            	'dataHoraContingencia':dataHoraContingencia,
	            	'justificativaContingencia':justificativaContingencia
	            }),
	            success:success
	        });
	    }
    }
});