Ext.define('nfe.view.lote.LotesPendentesController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.MessageBox'
    ],

    alias: 'controller.lotes-pendentes',

    onClickObterRetorno: function () {

        var s = this.getView().getSelection();

        var ids = [];
        for (var i = 0; i < s.length; i++) {
            ids.push(s[i].get('loteId'));
        }
        
        var model = new nfe.model.Lote();
        model.obterRetorno(ids,function(){
            this.getViewModel().getStore('lotesPendentes').reload();    
        });

    },

    onClickAtualizar: function (){
        this.getViewModel().getStore('lotesPendentes').reload();
    }

});
