Ext.define('nfe.view.inutilizacao.InutilizacoesPendentes', {
	extend : 'Ext.grid.Panel',
	xtype : 'inutilizacoes-pendentes',
	controller : 'inutilizacoes-pendentes',
	title: 'Pendentes de Inutilização',
	viewConfig : {
		enableTextSelection : true
	},
	viewModel : {
		type : 'inutilizacoes-pendentes'
	},
	tbar : [ {
		xtype : 'button',
		text : 'Atualizar',
		handler : 'onClickAtualizar'
	} ],
	bind : {
		store : '{inutilizacoesPendentes}',
		selection : '{inutilizacoesSelecionadas}'
	},
	hideHeaders : true,
	columns : [{
		width : 105,
		xtype : 'widgetcolumn',
		widget : {
			text : 'inutilizar',
			width : 90,
			xtype : 'button',
			handler : 'onInutilizar'
		}
	}, {
		text : 'Número',
		width : 200,
		sortable : false,
		hideable : false,
		renderer : 'rendererNumero'
	}, {
		text : 'Justificativa',
		width : 310,
		dataIndex : 'justificativa',
		cellWrap : true
	}, {
		text : 'Observação',
		dataIndex : 'msgDescricao',
		renderer : 'rendererObservacao',
		cellWrap : true,
		flex : 2
	} ]
});
