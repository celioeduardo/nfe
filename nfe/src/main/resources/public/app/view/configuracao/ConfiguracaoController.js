Ext.define('nfe.view.configuracao.ConfiguracaoController', {
    extend: 'Ext.app.ViewController',
    alias:'controller.configuracao',
    onClickListar: function (){
    	var propGrid = Ext.getCmp('propGrid');
        var s = this.getViewModel().getStore('config').data.items[0].data;
        var source={};
        s.each(function(record) {
            source[record.get('key')] = record.get('value');
        });
        propGrid.setSource(source);
    }
});
