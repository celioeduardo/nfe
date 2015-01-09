Ext.define('nfe.view.lote.LotesPendentesController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.window.MessageBox'
    ],

    alias: 'controller.lotes-pendentes',

    onClickObterRetorno: function () {

        var s = this.getView().getSelection();

        var ids = [];
        for (var i = 0; i < s.length; i++) {
            ids.push(s[i].get('loteId'));
        }
        
        var model = new nfe.model.Lote();
        var me = this;
        var grid = this.getView();
        
        grid.getView().mask('Obtendo Retorno...');
        
        model.obterRetorno(ids,function(){
            me.getViewModel().getStore('lotesPendentes').reload();
            me.fireViewEvent('obtidoRetornoLotes');
        },
        null,
        function(){
        	grid.getView().unmask();
        });

    },

    onClickAtualizar: function (){
        this.getViewModel().getStore('lotesPendentes').reload();
    }

});
