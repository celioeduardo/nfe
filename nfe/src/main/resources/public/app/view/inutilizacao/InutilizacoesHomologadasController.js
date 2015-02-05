Ext.define('nfe.view.inutilizacao.InutilizacoesHomologadasController', {
	extend : 'Ext.app.ViewController',

	requires : [ 'Ext.window.MessageBox' ],

	alias : 'controller.inutilizacoes-homologadas',

	onClickAtualizar : function() {
		this.getViewModel().getStore('inutilizacoesHomologadas').load();
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
    rendererHomologacao: function(dataHoraHomologacao, metadata, rec){
    	return Ext.String.format(
    			'<div style="font-size: 12px;font-style: normal;margin-top: 2px;">{0}</div>',
    			"homologada em<br>" + Ext.Date.format(dataHoraHomologacao,'d/m/Y H:i:s') +
    			"<br>"+rec.get('numeroProtocolo'));
    }
});
