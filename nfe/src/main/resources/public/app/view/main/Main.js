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
            allowToggle: false,
            items: [{
                icon: null,
                glyph: 72,
                scale: 'large',
                text: 'PENDENTES'
            }, {
                text: 'AUTORIZADAS',
                icon: null,
                glyph: 72,
                scale: 'large',
                menu: [
                    { text: 'Menu Item 1' },
                    { text: 'Menu Item 2' },
                    { text: 'Menu Item 3' }
                ]
            }, {
                xtype: 'splitbutton',
                text: 'Eventos',
                icon: null,
                glyph: 72,
                scale: 'large',
                menu: [
                    { text: 'Cancelamento' },
                    { text: 'Inutilização' },
                    { text: 'CC-e' }
                ]
            }]
        }]
    },{
        region: 'center',
        xtype: 'tabpanel',
        items:[{
        	title: 'Notas',
        	xtype:'notas-pendentes'
        }]
    },{
        xtype: 'panel',
        bind: {
            title: '{name}'
        },
        region: 'east',
        html: '<ul>'+
                '<li>Notas enviadas: 1999</li>' +
                '<li>Notas Processadas: 1800</li>' +
                '<li>Validade Certificado: 31/02/2015</li>' +
                '<li>wwww</li>' +
            '</ul>',
        width: 250,
        split: true,
        tbar: [{
            text: 'Button',
            handler: 'onClickButton'
        }]
    },]
});
