document.title = 'Nota Fiscal Eletrônica 3.10';

Ext.define('nfe.view.main.Header', {
    extend: 'Ext.Container',

    xtype: 'app-header',

    title: document.title,
    cls: 'app-header',
    height: 52,

    layout: {
        type: 'hbox',
        align: 'middle'
    },

    items: [{
        xtype: 'component',
        cls: 'app-header-logo'
    },{
        xtype: 'component',
        cls: 'app-header-title',
        html: document.title,
        flex: 1
    },{
        xtype: 'component',
        cls: 'app-header-title',
        html: 'Empresa/Filial',
        flex: 1
    },{
        xtype: 'component',
        id: 'app-header-username',
        cls: 'app-header-title',
        html: 'USERNAME',
        listeners: {
            click: 'onClickEnviar',
            element: 'el'
        },
        margin: '0 10 0 0'
    }]
});
