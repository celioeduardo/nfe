Ext.define('nfe.view.nf.NotasPendentes', {
    extend: 'Ext.grid.Panel',
    requires:['Ext.grid.Panel','nfe.model.NotaFiscal'],
    xtype: 'notas-pendentes',
    controller: 'notas-pendentes',
    id:'notas-pendentes',
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

    hideHeaders: true,
    columns: [
        {
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
        }
    ]
});
