Ext.define('nfe.view.teste.NotasPendentes', {
    extend: 'Ext.grid.Panel',
    requires:['Ext.grid.Panel'],
    xtype: 'notas-pendentes-teste',
    controller: 'notas-pendentes-teste',
    viewModel: {
        type: 'notas-pendentes-teste'
    },
    selModel: {
        selType: 'checkboxmodel',
        mode: 'MULTI'
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
    //reference: 'gridNf',
    layout: 'fit',
    //hideHeaders: true,
    columns: [{
        text: 'Número',
        width: 300,
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
        text: 'Observação',
        flex: 1
    }]
});
