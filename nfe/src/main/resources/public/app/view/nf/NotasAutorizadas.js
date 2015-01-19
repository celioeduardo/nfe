Ext.define('nfe.view.nf.NotasAutorizadas', {
    extend: 'Ext.grid.Panel',
    //requires:['Ext.grid.Panel','nfe.model.NotaFiscal'],
    xtype: 'notas-autorizadas',
    controller: 'notas-autorizadas',
    viewConfig: {
        enableTextSelection: true
    },
    viewModel: {
        type: 'notas-autorizadas'/*,
        session : true*/
    },
    tbar:[/*{
        xtype:'button',
        text:'Enviar',
        handler: 'onClickEnviar'
    },*/{
        xtype:'button',
        text:'Atualizar',
        handler: 'onClickAtualizar'
    }],
    bind:{
        store: '{notasAutorizadas}',
        selection: '{notasSelecionadas}'
    },
    selType: 'checkboxmodel',
    //hideHeaders: true,
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
              text:'Danfe',
              width: 50,
              dataIndex: 'notaFiscalId',
              //flex: 1,
              renderer: 'rendererDanfe',
              align: 'center'
          },{
              //text: 'e-mail',
              width: 105,
              xtype: 'widgetcolumn',
              //dataIndex: 'notaFiscalId',
              widget: {
            	  text:'e-mail',
                  width: 90,
                  xtype: 'button',
                  //icon: '../shared/icons/fam/feed_add.png',
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
