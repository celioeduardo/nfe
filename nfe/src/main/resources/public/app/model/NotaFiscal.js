Ext.define('nfe.model.NotaFiscal', {
    extend: 'nfe.model.Base',
    fields: ['numero','serie','tipo',
    {name: 'emissao', type: 'date'},
    'valor'],

    proxy: {
        url : 'notas_fiscais/pendentes_autorizacao'
    }

});