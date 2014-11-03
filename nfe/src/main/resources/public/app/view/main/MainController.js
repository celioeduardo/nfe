/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('nfe.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.MessageBox'
    ],

    alias: 'controller.main',

    onChangeFilial: function (combo) {
        //Ext.Msg.alert('empresa',this.getViewModel().getData().empresa);
        //me.getViewModel().getData().filial=combo.getValue();
        //this.getViewModel().setData({filial:combo.getValue()});
        //Ext.getCmp('notas-pendentes').getStore('notasPendentes').load();
        Ext.getCmp('notas-pendentes').getStore('notasPendentes').load({
            params:{
                'filial': combo.getValue()
            } 
        });
        Ext.toast({
            title: 'Trocando Empresa...aguarde',
            //ui: 'navigation',
            html: combo.getValue() + ' - ' + combo.getRawValue(),
            align: 't',
            bodyPadding: 10,
            width:350
        });

    }

/*
    onClickButton: function () {
        Ext.Msg.confirm('Confirm', 'Are you sure?', 'onConfirm', this);
    },

    onConfirm: function (choice) {
        if (choice === 'yes') {
            //
        }
    },
    
    onClickEnviar: function () {
    	Ext.Msg.confirm('Confirmar', 'Enviar Notas?', 'enviarNotas', this);
    },
    
    enviarNotas: function (choice) {
    	if (choice === 'yes') {
    		//pegar selecao, enviar para controler 
    		
    	}
    },
    
    rendererNumero: function(numero, metadata, rec){
        var nf = rec.get('serie') != null ? numero+'/'+rec.get('serie') : numero,
            es = rec.get('tipo') == 'E' ? 'entrada' : 'saída';

        return Ext.String.format(
            '<div style="font-size:x-large;font-weight: bold;">{0}</div>'+
            '<div style="padding: 2px 0px 0px 0px; font-weight: 400;font-size: 18px;line-height: 22px; font-family:Arial">{1}</div>'+
            '<hr style="margin: 2px">'+
            '<div style="font-size: small;font-color=gray;color: gray;font-style: italic;">{2} - emitida em {3}</div>',
            nf,
            rec.get('publicoNome'),
            es,
            Ext.util.Format.date(rec.get('emissao'),'d/m/Y'));
    },

    rendererValor: function(valor, metadata, rec){
        return Ext.String.format(
            '<div style="font-size: large;font-weight: bold;font-style: italic;margin-top: 20px;">{0}</div>',
            "R$ " + Ext.util.Format.number(valor,'0,000.00'));
    }*/
});
