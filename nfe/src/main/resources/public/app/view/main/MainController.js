/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 */
Ext.define('nfe.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.window.MessageBox'
    ],

    alias: 'controller.main',
    
    onAfterRender: function(){
    	var runner = new Ext.util.TaskRunner(),
			task = runner.start({
				scope:this,
				run: this.verificarModoOperacao,
				interval: 10000 //10 segundos
			});
    },
    
    verificarModoOperacao: function(){
    	var vm = this.getViewModel();
    	
    	if (!vm.get('filial')) return;
    	
    	nfe.model.Filial.load(vm.get('filial'),{
    		scope:this,
    		success: this.verificarModoOperacaoDaFilial
    	});
    },
    
	verificarModoOperacaoDaFilial: function(r, operation) {
		var vm = this.getViewModel(),
			alterou = false;

		if (vm.get('modoOperacao') != r.get('modoOperacao'))
			alterou = true;

//		TRATAR DATA HORA CONTINGÊNCIA
//		if (vm.get('dataHoraContingencia') != r.get('dataHoraContingencia'))
//			alterou = true;

		if (vm.get('justificativaContingencia') != r.get('justificativaContingencia'))
			alterou = true;

		if (alterou){ 

			console.log('alterou modoOperacao');		

			Ext.toast({
				title: 'Modo de Operação',
				html: 'Alterado Modo de Operação da Filial ' + r.get('nome'),
				align: 't',
				bodyPadding: 10,
				width:350
			});

			this.carregarDadosFilial(vm.get('filial'));
		}

	},
    
    onSelectFilial: function (combo, record){
    	var me = this,
            vm = this.getViewModel(),
            filialId = record.get('id');
    	
    	vm.set('filial',filialId);
    	
    	me.carregarDadosFilial(filialId,function(){
    		me.fireEvent('filialTrocada');
    	});
        
        Ext.toast({
            title: 'Troca de Filial',
            html: record.get('id') + ' - ' + record.get('nome'),
            align: 't',
            bodyPadding: 10,
            width:350
        });
    },
    
    onModoOperacaoAlterado: function(){
    	var vm = this.getViewModel();
    	this.carregarDadosFilial(vm.get('filial'));
    },
    
    carregarDadosFilial: function(filialId,fnSuccess){
    	
    	var me = this,
        	vm = this.getViewModel();
    	
    	nfe.model.Filial.load(filialId,{
    		scope:this,
    		success: function(r, operation) {
    	        
    			vm.set('modoOperacao',r.get('modoOperacao'));
    	        vm.set('ambiente',r.get('ambiente'));
    	        vm.set('empresa',r.get('empresaId'));
    	        vm.set('dataHoraContingencia',r.get('dataHoraContingencia'));
    	        vm.set('justificativaContingencia',r.get('justificativaContingencia'));
    	        
    	        if (fnSuccess)
    	        	fnSuccess.call();
    	        
                me.atualizarTela();
    	    }
    	});
    },
    
    notasPendentesEnviadas: function(){
    	vm = this.getViewModel();
    	
    	if (!vm.get('filial')) return;
    	
    	this.getView().down('lotes-pendentes')
    		.getViewModel().getStore('lotesPendentes')
            .reload();
    },
    
    obtidoRetornoLotes: function(){
    	if (!vm.get('filial')) return;

    	this.getView().down('notas-pendentes')
	    	.getViewModel().getStore('notasPendentes')
	    	.reload();
    	
    	this.getView().down('notas-autorizadas')
	    	.getViewModel().getStore('notasAutorizadas')
	    	.reload();
    },
    
    atualizarTela: function(){
        this.notasPendentesEnviadas();
        this.obtidoRetornoLotes();
    }

});
