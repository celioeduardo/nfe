Ext.define('nfe.view.nf.NotasPendentes', {
    extend: 'Ext.grid.Panel',
    requires:['Ext.grid.Panel','nfe.model.NotaFiscal'],
    xtype: 'notas-pendentes',
    controller: 'notas-pendentes',
    viewModel: {
        type: 'notas-pendentes'/*,
        session : true*/
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
        xtype:'textfield',
        bind:{
            value: '{titulo}'
        }
    }],
    reference: 'gridNf',
    bind:{
        title: '{titulo}',
        store: '{notasPendentes}',
        selection: '{notasSelecionadas}'
    },
    columns: [{
        text: 'Emissão',
        xtype: 'datecolumn',
        format: 'd/m/Y',
        width: 100,
        dataIndex: 'emissao'
    },{
        text: 'Tipo',
        width: 50,
        dataIndex: 'tipo'
    },{
        text: 'Número',
        width: 90,
        sortable: false,
        hideable: false,
        dataIndex: 'numero',
        align: 'right'
    },{
        text: 'Série',
        width: 80,
        dataIndex: 'serie',
        align: 'right'
    },{
        text: 'Público',
        width: 250,
        dataIndex: 'publicoNome'
    },{
        text: 'Valor',
        xtype:'numbercolumn',
        width: 100,                
        format:'0.00',
        dataIndex: 'valor',
        align: 'right'
    },{
        text: 'Observação',
        flex: 1
    }]
});
