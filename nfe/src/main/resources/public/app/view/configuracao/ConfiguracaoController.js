Ext.define('nfe.view.configuracao.ConfiguracaoController', {
    extend: 'Ext.app.ViewController',
    alias:'controller.configuracao',
    requires:['nfe.model.Filial'],
    
    onAplicarConfiguracao: function (){
    	if (!this.getView().isValid()){
    		Ext.Msg.alert('Atenção', 'Dados inválidos');
    		return;
    	}
    	
    	var me = this,
    		vm = this.getViewModel(),
    		dataHoraContingencia = null;
    	
    	if (vm.get('modoOperacaoConfig').value == 'NORMAL'){
        	this.lookupReference('dataContingenciaConfig').setValue(null);
        	this.lookupReference('horaContingenciaConfig').setValue(null);
        	this.lookupReference('justificativaContingenciaConfig').setValue(null);
    	} else {
    		var hora = this.lookupReference('horaContingenciaConfig').getValue();
    		dataHoraContingencia = this.lookupReference('dataContingenciaConfig').getValue();
    		dataHoraContingencia.setHours(hora.getHours());
    		dataHoraContingencia.setMinutes(hora.getMinutes());
    	}
    	
    	nfe.model.Filial.alterarModoOperacao(
			vm.get('filial'),
			vm.get('modoOperacaoConfig').value,
			dataHoraContingencia,
			vm.get('justificativaContingenciaConfig').value,
			function(){
				me.fireViewEvent('modoOperacaoAlterado');
			});
    },
    
    carregarConfiguracoes: function(){
    	var vm = this.getViewModel(),
    		modoOperacao = vm.get('modoOperacao');
    	this.lookupReference('modoOperacaoConfig').setValue(vm.getParent().get('modoOperacao'));
    	this.lookupReference('dataContingenciaConfig').setValue(vm.getParent().get('dataHoraContingencia'));
    	this.lookupReference('horaContingenciaConfig').setValue(vm.getParent().get('dataHoraContingencia'));
    	this.lookupReference('justificativaContingenciaConfig').setValue(vm.getParent().get('justificativaContingencia'));
    },
    
    onCancelar: function(){
    	this.carregarConfiguracoes();
    }
});
