Ext.define('nfe.view.configuracao.ConfiguracaoController', {
    extend: 'Ext.app.ViewController',
    alias:'controller.configuracao',
    requires:['nfe.model.Filial'],
    
    onAplicarConfiguracao: function (){
    	if (!this.getView().isValid()){
    		Ext.Msg.alert('Atenção', 'Dados inválidos');
    		return;
    	}
    	
    	var vm = this.getViewModel(),
    		dataHoraContingencia = null;
    	
    	
    	if (vm.get('modoOperacaoConfig').value == 'NORMAL'){
        	this.lookupReference('dataContingenciaConfig').setValue(null);
        	this.lookupReference('horaContingenciaConfig').setValue(null);
        	this.lookupReference('justificativaContingenciaConfig').setValue(null);
    	} else {
    		var hora = vm.get('horaContingenciaConfig').value;
    		dataHoraContingencia = vm.get('dataContingenciaConfig').value;
    		dataHoraContingencia.setHours(hora.getHours());
    		dataHoraContingencia.setMinutes(hora.getMinutes());
    	}
    	
    	nfe.model.Filial.alterarModoOperacao(
			vm.get('filial'),
			vm.get('modoOperacaoConfig').value,
			dataHoraContingencia,
			vm.get('justificativaContingenciaConfig').value,
			function(){
				vm.getParent().set('modoOperacao',vm.get('modoOperacaoConfig').value);
		    	vm.getParent().set('dataContingencia',vm.get('dataContingenciaConfig').value);
		    	vm.getParent().set('horaContingencia',vm.get('horaContingenciaConfig').value);
		    	vm.getParent().set('justificativaContingencia',vm.get('justificativaContingenciaConfig').value);
				console.log('Sucesso: '+vm.get('modoOperacaoConfig').value);
				console.log(dataHoraContingencia);
			});
    },
    
    carregarConfiguracoes: function(){
    	var vm = this.getViewModel(),
    		modoOperacao = vm.get('modoOperacao');
    	this.lookupReference('modoOperacaoConfig').setValue(vm.getParent().get('modoOperacao'));
    	this.lookupReference('dataContingenciaConfig').setValue(vm.getParent().get('dataContingencia'));
    	this.lookupReference('horaContingenciaConfig').setValue(vm.getParent().get('horaContingencia'));
    	this.lookupReference('justificativaContingenciaConfig').setValue(vm.getParent().get('justificativaContingencia'));
    },
    
    onCancelar: function(){
    	this.carregarConfiguracoes();
    },
    
    validarModoOperacao: function(){
    	console.log('asdfasdfa');
    	return 'abcdef';
    }
});
