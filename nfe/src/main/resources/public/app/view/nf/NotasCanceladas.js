Ext.define('nfe.view.nf.NotasCanceladas', {
    extend: 'Ext.grid.Panel',
    xtype: 'notas-canceladas',
    controller: 'notas-canceladas',
    viewModel: {
        type: 'notas-canceladas'
    },
    tbar:[{
        xtype:'button',
        text:'Atualizar',
        handler: 'onClickAtualizar'
    }],
    bbar:[{
    	xtype: 'pagingtoolbar', 
		bind:{
			store: '{notasCanceladas}'
		}, 
		displayInfo: true
    }],
    bind:{
        store: '{notasCanceladas}'
    },
    columns: [{
          text: 'Número',
          width: 350,
          sortable: false,
          hideable: false,
          renderer: 'rendererNumero',
          dataIndex: 'numero'

      },{
          text: 'Valor',
          xtype:'numbercolumn',
          width: 150,                
          format:'0.00',
          dataIndex: 'valor',
          renderer: 'rendererValor',
          align: 'right'
      },{
          text: 'Cancelamento',
          xtype:'datecolumn',
          width: 150,
          renderer:'rendererCancelamento',
          dataIndex: 'dataHoraCancelamento',
          align: 'center'
      },{
          text: 'Observação',
          dataIndex: 'msgDescricao',
          renderer: 'rendererObservacao',
          cellWrap: true,
          flex: 2
      }]
});
