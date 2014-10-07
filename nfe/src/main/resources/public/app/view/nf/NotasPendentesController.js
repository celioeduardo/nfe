Ext.define('nfe.view.main.NotasPendentesController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.MessageBox'
    ],

    alias: 'controller.notas-pendentes',

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
    	   //console.log(this.lookupReference('gridNf').getSelection());
            var s = this.getView().getSelection();
            var temp = '';
            for (var i = 0; i < s.length; i++) {
                temp = temp + s[i].getId() + ',';
            };
            alert(temp);
    	}
    }
});
