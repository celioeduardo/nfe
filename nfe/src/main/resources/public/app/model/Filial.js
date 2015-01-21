Ext.define('nfe.model.Filial', {
    extend: 'nfe.model.Base',
    idProperty: 'filialId',
    fields:['nome','modoOperacao','ambiente',{
    	name:'dataHoraContingencia', type:'date'
    }],
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
	            	'dataHoraContingencia':Ext.Date.format(dataHoraContingencia,'Y-m-d\\TH:i:s.uP'),
	            	'justificativaContingencia':justificativaContingencia
	            }),
	            success:success
	        });
	    }
    }
});