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
    }],
    bind:{
        store: '{notasAutorizadas}',
        selection: '{notasSelecionadas}'
    },
    selType: 'checkboxmodel',
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
              dataIndex: 'valor',
              renderer: 'rendererValor',
              align: 'right'
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
              text: 'Observação',
              dataIndex: 'msgDescricao',
              renderer: 'rendererObservacao',
              cellWrap: true,
              flex: 2
          }
    ]
});
