/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('nfe.view.main.Main', {
    extend: 'Ext.container.Container',
    requires:['Ext.grid.Panel',
        'nfe.model.NotaFiscal',
        'Ext.layout.container.Border',
        'nfe.view.main.Header',
        'nfe.view.configuracao.Configuracao'
    ],
    xtype: 'app-main',
    
    controller: 'main',
    viewModel: {
        type: 'main'
    },

    layout: {
        type: 'border'
    },

    items: [{
        id: 'app-header',
        xtype: 'app-header',
        region: 'north'
    },{
        header: {
            title: {
                text: 'Coopadap/Filial 53',
                textAlign: 'right'
            }
        },        
        region: 'center',
        xtype: 'tabpanel',
        ui: 'navigation',
        tabPosition: 'left',
        tabRotation: 0,
        tabBar: {
            border: false
        },
        defaults: {
            bodyPadding: 2
        },
        items:[{
            xtype:'notas-pendentes'
        }

        /*{
            title: 'Pendentes',
            xtype:'notas-pendentes-teste',
            glyph: 0xf064,
            reference: 'gridPendentes',
            hideHeaders: true,
            selModel: {
                selType: 'checkboxmodel',
                mode: 'MULTI'
            },            
            bind:{
                store: '{notasPendentes}'
            }
        }*/,{
            title: 'Autorizadas',
            xtype:'notas-pendentes-teste',
            glyph: 0xf087,
            reference: 'gridAutorizada',
            selModel: {
                selType: 'checkboxmodel',
                mode: 'SINGLE'
            },            
            bind:{
                store: '{notasAutorizadas}'
            }
        },{
            title: 'Cancelamento',
            glyph: 0xf00d
        },{
            title: 'Inutilização',
            glyph: 0xf088
        },{
            title: 'CC-e',
            glyph: 0xf003
        },{
            xtype:'tela-configuracao'
        }]
    }]
});
