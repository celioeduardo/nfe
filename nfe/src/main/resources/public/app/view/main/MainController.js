/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('nfe.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.window.MessageBox'
    ],

    alias: 'controller.main',

    onSelectFilial: function (combo, record){
    	var vm = this.getViewModel();
    	
    	vm.setData({filial:record.get('id')});
    	
    	Ext.toast({
    		title: 'Troca de Filial',
    		//ui: 'navigation',
    		html: record.get('id') + ' - ' + record.get('nome'),
    		align: 't',
    		bodyPadding: 10,
    		width:350
    	});
    	
    	nfe.model.Filial.load(record.get('id'),{
    		success: function(r, operation) {
    	        vm.set('modoOperacao',r.get('modoOperacao'));
    	        vm.set('ambiente',r.get('ambiente'));
    	    }
    	});
    },
    
    notasPendentesEnviadas: function(){
    	this.getView().down('lotes-pendentes')
    		.getViewModel().getStore('lotesPendentes')
    		.reload();
    },
    
    obtidoRetornoLotes: function(){
    	this.getView().down('notas-pendentes')
	    	.getViewModel().getStore('notasPendentes')
	    	.reload();
    	
    	this.getView().down('notas-autorizadas')
	    	.getViewModel().getStore('notasAutorizadas')
	    	.reload();
    },
    
    rendererAmbiente: function(ambiente, metadata, rec){
    	console.log(ambiente);
    	return 'teste';
        if (ambiente == 'HOMOLOGACAO')
        	return 'Homologação';
        else if (ambiente == 'PRODUCAO')
        	return 'Produção';
        else return '';
    }

});
