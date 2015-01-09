Ext.define('nfe.model.Notista', {
    extend: 'nfe.model.Base',
    idProperty: 'notistaId',
    
    proxy: {
        url : 'notista/notistas'
    }

});