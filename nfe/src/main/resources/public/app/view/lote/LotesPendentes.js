Ext.define('nfe.view.lote.LotesPendentes', {
    extend: 'Ext.grid.Panel',
    requires:['Ext.grid.Panel','nfe.model.Lote'],
    xtype: 'lotes-pendentes',
    controller: 'lotes-pendentes',
    viewModel: {
        type: 'lotes-pendentes'
    },
    tbar:[{
        xtype:'button',
        text:'Obter Retorno',
        handler: 'onClickObterRetorno'
    },{
        xtype:'button',
        text:'Atualizar',
        handler: 'onClickAtualizar'
    }],
    bind:{
        store: '{lotesPendentes}',
        selection: '{lotesSelecionados}'
    },
    //selType: 'checkboxmodel',
    //hideHeaders: true,
    columns: [{
        text: 'Lote',
        dataIndex: 'numero',
        renderer:'rendererLote',
        sortable: false,
        hideable: false,
        flex: 1
    }/*,{
        text: 'Recibo',
        dataIndex: 'numeroRecibo',
        sortable: false,
        hideable: false,
        flex: 1
    },{
        text: 'Ambiente',
        dataIndex: 'ambiente',
        sortable: false,
        hideable: false,
        flex: 1
    }*/]
});
