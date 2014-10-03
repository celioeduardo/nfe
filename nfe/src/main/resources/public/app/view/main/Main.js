/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('nfe.view.main.Main', {
    extend: 'Ext.container.Container',
    requires:['Ext.grid.Panel','nfe.model.NotaFiscal'],
    xtype: 'app-main',
    
    controller: 'main',
    viewModel: {
        type: 'main'
    },

    layout: {
        type: 'border'
    },

    items: [{
        xtype: 'panel',
        bind: {
            title: '{name}'
        },
        region: 'west',
        html: '<ul><li>wwww</li></ul>',
        width: 150,
        split: true,
        tbar: [{
            text: 'Button',
            handler: 'onClickButton'
        }]
    },{
        region: 'center',
        xtype: 'tabpanel',
        items:[{
        	title: 'Notas',
        	xtype:'grid',
            selModel: {
                selType: 'checkboxmodel',
                mode: 'MULTI'
            },
            tbar:[{
                xtype:'button',
                text:'Enviar'
            }],
            autoLoad:true,
            store: {
                model: 'NotaFiscal'
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
        }]
    }]
});
