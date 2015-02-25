Ext.define('nfe.view.nf.CartaCorrecao', {
    extend: 'Ext.window.Window',
    xtype: 'cce',
    controller: 'cce',
    viewModel: {
        type: 'cce'
    },
    bind:{
    	title: 'Registrar Carta de Correção da Nota Fiscal: {nf.numero}'
    },
    bodyPadding: 5,
    width: 450,
    items: [{
    	xtype:'form',
    	layout: 'anchor',
        defaults: {
            anchor: '100%'
        },
        items: [{
        	xtype:'textarea',
            fieldLabel: 'Correção',
            emptyText:'informe a correção contendo no mínimo 15 e no máximo 1000 caracteres.',
            grow: true,
            minLength: 15,
	    	maxLength: 1000,
            height: 200,
            bind:{
            	value:'{correcao}'
            },
            allowBlank: false
        }],

        buttons: [{
            text: 'Registar',
            formBind: true,
            disabled: true,
            handler: 'onRegistrarCartaCorrecao'
        }]
	}]
});
