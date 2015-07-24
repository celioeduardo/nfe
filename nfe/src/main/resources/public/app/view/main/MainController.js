
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
    	var vm = this.getViewModel(),
    		runner = new Ext.util.TaskRunner(),
			task = runner.start({
				scope:this,
				run: this.verificarModoOperacao,
				interval: 120000 //2 minutos
			});
    	
    	Ext.Ajax.request({
			url:'usuario_logado',
			method:'GET',
			success:function(data){
				vm.set('usuario',data.responseText)
			},
			scope:this
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
    	vm.notify();
    	
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
    
    onNotaFiscalCancelada: function(){
    	 this.atualizarStore(this.getView().down('#autorizadas')
	    	.getViewModel().getStore('notasAutorizadas'));
    },
    onInutilizacaoHomologada: function(){
    	 this.atualizarStore(this.getView().down('#inutilizacoes-homologadas')
	    	.getViewModel().getStore('inutilizacoesHomologadas'));
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
    	
    	 this.atualizarStore(this.getView().down('lotes-pendentes')
    		.getViewModel().getStore('lotesPendentes'));
    },
    
    obtidoRetornoLotes: function(){
    	vm = this.getViewModel();
    	
    	if (!vm.get('filial')) return;

    	 this.atualizarStore(this.getView().down('notas-pendentes')
	    	.getViewModel().getStore('notasPendentes'));
    	
    	this.onAtualizarAutorizadas();
    },
    
    onAtualizarAutorizadas: function(){
    	vm = this.getViewModel();
    	
    	 this.atualizarStore(this.getView().down('notas-autorizadas')
	    	.getViewModel().getStore('notasAutorizadas'));
    },
    
    
    atualizarTela: function(){
        this.notasPendentesEnviadas();
        this.obtidoRetornoLotes();
        
        this.atualizarStore(this.getView()
        	.down('#inutilizacoes-homologadas')
    		.getViewModel()
    		.getStore('inutilizacoesHomologadas'));
        
        this.atualizarStore(this.getView()
	    	.down('#inutilizacoes-pendentes')
			.getViewModel()
			.getStore('inutilizacoesPendentes'));
    },
    
    atualizarStore:function(store){
    	if (store)
    		store.load();
    },
    
    onSair: function(btn){
    	Ext.Ajax.request({
            url:'/logout',
            method:'POST',
            success:function(){
            	window.location = '/logout';
            },
            failure:function(form, action){
                if(action.failureType == 'server'){
                    obj = Ext.util.JSON.decode(action.response.responseText);
 
                    Ext.Msg.alert('Falha no login', obj.errors.reason);
                }else{
                    Ext.Msg.alert('Atenção!', 'Servidor de autenticação não acessível: ' + action.response.responseText);
 
                }
                login.getForm().reset();
            }
        });
    }
    
    

});
