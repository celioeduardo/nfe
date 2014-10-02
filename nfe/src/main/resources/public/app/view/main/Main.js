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
        html: '<ul><li>wwww loba loba boooommmmm!!!!</li></ul>',
        width: 250,
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
          	    text: 'Número',
                width: 100,
                sortable: false,
                hideable: false,
                dataIndex: 'numero'
            },{
                text: 'Série',
                width: 150,
                dataIndex: 'serie'
            },{
                text: 'Emissão',
                xtype: 'datecolumn',
                format: 'd/m/Y',
                flex: 1,
                dataIndex: 'emissao'
            },{
                text: 'Valor',
                xtype:'numbercolumn',
                flex: 1,
                format:'0.00',
                dataIndex: 'valor'
            }]
        }]
    }]
});
