Ext.define('nfe.model.Filial', {
    extend: 'nfe.model.Base',
    idProperty: 'filialId',
    fields:['nome','modoOperacao','ambiente'],
    proxy: {
    	type: 'ajax',
        url : 'filial/obter',
        reader: {
            type: 'json',
            rootProperty: ''
        }
    }
});