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
        'nfe.view.main.Header'
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
        /*region: 'north',
        xtype: 'panel',
        layout: 'border',
        height: 100,    
        title: 'Nota Fiscal Eletrônica - 3.10',
        html:'<div style="font-size:large;font-weight: bold;">Empresa: COOPADAP - Cooperativa do Alto Paranaíba</div>'+
             '<div style="padding: 2px 0px 0px 0px; font-weight: 400;font-size: 16px;line-height: 22px; font-family:Arial">Filial: 1 - Matriz</div>'+
             '<hr style="margin: 2px">'+
             '<div style="font-size: small;font-color=gray;color: yellow;font-style: italic;">Usuário - Kent Bek</div>',
        minHeight: 75,
        maxHeight: 150   */
        id: 'app-header',
        xtype: 'app-header',
        region: 'north'
    },{
        xtype: 'panel',
        region: 'west',
        html: '<ul>'+
                '<li>Notas enviadas: 1999</li>' +
                '<li>Notas Processadas: 1800</li>' +
                '<li>Validade Certificado: 31/02/2015</li>' +
                '<li>wwww</li>' +
            '</ul>',
        width: 200,
        split: true,
        items: [{
            xtype: 'segmentedbutton',
            vertical: true,
            type: 'vbox',
            align: 'left',
            width: 200,
            items: [{
                scale: 'large',
                text: 'Pendentes'
            },{
                scale: 'large',
                text: 'Autorizadas'
            },{
                scale: 'large',
                text: 'Cancelamento' 
            },{
                scale: 'large',
                text: 'Inutilização'
            },{
                scale: 'large',
                text: 'CC-e'
            },{
                scale: 'large',
                text: 'Ambiente'                        
            }]
        }]
    },{
        region: 'center',
        xtype: 'tabpanel',
        items:[{
        	title: 'Notas',
        	xtype:'notas-pendentes'
        }]
    }]
});
