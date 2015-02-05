Ext.define('nfe.view.inutilizacao.InutilizacoesHomologadas', {
	extend : 'Ext.grid.Panel',
	xtype : 'inutilizacoes-homologadas',
	controller : 'inutilizacoes-homologadas',
	title: 'Homologadas',
	viewConfig : {
		enableTextSelection : true
	},
	viewModel : {
		type : 'inutilizacoes-homologadas'
	},
	tbar : [ {
		xtype : 'button',
		text : 'Atualizar',
		handler : 'onClickAtualizar'
	} ],
	bind : {
		store : '{inutilizacoesHomologadas}',
		selection : '{inutilizacoesSelecionadas}'
	},
	hideHeaders : true,
	columns : [{
		text : 'Número',
		width : 200,
		sortable : false,
		hideable : false,
		renderer : 'rendererNumero'
	},{
		text : 'Justificativa',
		width : 310,
		dataIndex : 'justificativa',
		cellWrap : true
	},{
		text : 'Observação',
		dataIndex : 'msgDescricao',
		renderer : 'rendererObservacao',
		cellWrap : true,
		flex : 2
	},{
		text: 'Homologada em',
		xtype:'datecolumn',
		width: 150,
		renderer:'rendererHomologacao',
		dataIndex: 'dataHoraHomologacao',
		align: 'center'
	}]
});
