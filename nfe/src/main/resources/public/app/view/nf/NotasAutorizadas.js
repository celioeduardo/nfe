Ext.define('nfe.view.nf.NotasAutorizadas', {
    extend: 'Ext.grid.Panel',
    //requires:['Ext.grid.Panel','nfe.model.NotaFiscal'],
    xtype: 'notas-autorizadas',
    controller: 'notas-autorizadas',
    viewConfig: {
        enableTextSelection: true
    },
    viewModel: {
        type: 'notas-autorizadas'
    },
    tbar:[{
        xtype:'button',
        text:'Atualizar',
        handler: 'onClickAtualizar'
    },{
    	xtype: 'numberfield',
        fieldLabel: 'NF',
        reference: 'filtronf',
    	maxLength: 6
    }],
    bbar:[{
    	xtype: 'pagingtoolbar', 
		bind:{
			store: '{notasAutorizadas}'
		}, 
		displayInfo: true
    }],
    bind:{
        store: '{notasAutorizadas}',
        selection: '{notasSelecionadas}'
    },
    //selType: 'checkboxmodel',
    hideHeaders: true,
    columns: [
          {
              text: 'Número',
              width: 310,
              sortable: false,
              hideable: false,
              renderer: 'rendererNumero',
              dataIndex: 'numero'

          },{
              text: 'Valor',
              xtype:'numbercolumn',
              width: 150,                
              format:'0.00',
              dataIndex: 'total',
              renderer: 'rendererValor',
              align: 'right'
          },{
              text: 'Autorização',
              xtype:'datecolumn',
              //format:'d/m/Y H:i:s',
              width: 150,
              renderer:'rendererAutorizacao',
              dataIndex: 'dataHoraAutorizacao',
              align: 'center'
          },{
              width: 105,
              xtype: 'widgetcolumn',
              widget: {
            	  text:'danfe',
                  width: 90,
                  xtype: 'button',
                  handler:'imprimirDanfe'
              }
          },{
              width: 105,
              xtype: 'widgetcolumn',
              widget: {
            	  text:'e-mail',
                  width: 90,
                  xtype: 'button',
                  handler: 'enviarEmail'
              }
          },{
              width: 105,
              xtype: 'widgetcolumn',
              widget: {
            	  text:'cancelar',
                  width: 90,
                  xtype: 'button',
                  handler: 'cancelar'
              }
          },{
              width: 105,
              xtype: 'widgetcolumn',
              widget: {
            	  text:'cc-e',
                  width: 90,
                  xtype: 'button',
                  handler: 'onAbrirCartaCorrecao'
              }
          },{
              text: 'Correção',
              dataIndex: 'cceCorrecao',
              renderer: 'rendererCceCorrecao',
              cellWrap: true,
              flex: 2
          },{
        	  text: 'Observação',
        	  dataIndex: 'msgDescricao',
        	  renderer: 'rendererObservacao',
        	  cellWrap: true,
        	  flex: 2
          }
    ]/*,
    dockedItems: [{
		dock: 'bottom', xtype: 'pagingtoolbar', 
		bind:{
			store: '{notasAutorizadas}'
		}, 
		displayInfo: true
	}]*/
});
