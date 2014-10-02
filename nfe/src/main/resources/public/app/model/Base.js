Ext.define('nfe.model.Base', {
    extend: 'Ext.data.Model',

    schema: {
        namespace: 'nfe.model',  // generate auto entityName

        proxy: {     // Ext.util.ObjectTemplate
            type: 'rest',
            reader: {
                type: 'json',
                rootProperty: 'rows'
            }
        }
    }
});