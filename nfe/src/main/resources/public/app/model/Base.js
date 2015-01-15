Ext.define('nfe.model.Base', {
    extend: 'Ext.data.Model',

    schema: {
        namespace: 'nfe.model',  // generate auto entityName

        proxy: {     // Ext.util.ObjectTemplate
            type: 'ajax',
            reader: {
                type: 'json',
                rootProperty: 'rows'
            }
        }
    }
});