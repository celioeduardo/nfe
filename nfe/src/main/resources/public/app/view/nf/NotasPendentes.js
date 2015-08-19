Ext.define('nfe.view.nf.NotasPendentes', {
    extend: 'Ext.grid.Panel',
    requires:['Ext.grid.Panel','nfe.model.NotaFiscal'],
    xtype: 'notas-pendentes',
    controller: 'notas-pendentes',
    viewConfig: {
        enableTextSelection: true
    },
    viewModel: {
        type: 'notas-pendentes'/*,
        session : true*/
    },
    tbar:[{
        xtype:'button',
        text:'Enviar',
        handler: 'onClickEnviar'
    },{
        xtype:'button',
        text:'Atualizar',
        handler: 'onClickAtualizar'
    }],
    bind:{
        store: '{notasPendentes}',
        selection: '{notasSelecionadas}'
    },
    //selType: 'checkboxmodel',
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
            width: 105,
            xtype: 'widgetcolumn',
            widget: {
            	xtype: 'button',
            	width: 90,
                handler:'imprimirDanfe'
            },
	        onWidgetAttach: 'rendererDanfe' 
        },{
            text: 'Observação',
            dataIndex: 'msgDescricao',
            renderer: 'rendererObservacao',
            cellWrap: true,
            flex: 2
        }
    ]
});
