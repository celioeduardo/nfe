Ext.define('nfe.view.inutilizacao.InutilizacoesPendentesController', {
	extend : 'Ext.app.ViewController',

	requires : [ 'Ext.window.MessageBox' ],

	alias : 'controller.inutilizacoes-pendentes',

	onClickAtualizar : function() {
		this.getViewModel().getStore('inutilizacoesPendentes').load();
	},

	rendererNumero : function(numero, metadata, rec) {
		var numero = "";
		if (rec.get('numeroInicial') != rec.get('numeroFinal'))
			numero = 'De ' + rec.get('numeroInicial') + ' a '
					+ rec.get('numeroFinal');
		else
			numero = 'Número ' + rec.get('numeroInicial');

		return numero + ' - Série :' + rec.get('serie');
	},
	rendererObservacao : function(valor, metadata, rec) {
		if (rec.get('msgDescricao') == null)
			return '';
		return rec.get('msgCodigo') + ' - ' + rec.get('msgDescricao');
	},
	onInutilizar : function(btn) {
		var rec = btn.getWidgetRecord(), me = this;

		Ext.Msg.confirm('Inutilizar', 'Confirma Inutilização?',
				function(result) {
					var me = this;
					if (result === 'yes') {
						me.getView().mask('Inutilizando...');
						nfe.model.Inutilizacao.inutilizar(rec.get('inutilizacaoId'),
						function() {
							me.getView().focus(); // Bug de focus no ExtJS - precisa ter essachamada..
							me.getViewModel()
									.getStore('inutilizacoesPendentes')
									.reload();
							me.fireViewEvent('inutilizacaoHomologada');
						}, null, function() {
							me.getView().unmask();
						});
					}
				}, this);
	}
});
