Ext.define('nfe.view.nf.CartaCorrecaoController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.window.MessageBox'
    ],

    alias: 'controller.cce',

    onRegistrarCartaCorrecao: function () {
    	Ext.Msg.confirm('Confirmar', 'Registrar Carta de Correção?', 'registrarCartaCorrecao', this);
    },

    registrarCartaCorrecao: function (choice) {
        if (choice === 'yes') {
        	var vm = this.getViewModel();
        	
        	this.fireViewEvent('registrarCartaCorrecao',
        			vm.get('notaFiscalId'),
        			vm.get('correcao'));
        }
    }
});
