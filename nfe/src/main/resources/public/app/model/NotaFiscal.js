Ext.define('nfe.model.NotaFiscal', {
    extend: 'nfe.model.Base',
    idProperty: 'notaFiscalId',
    fields: ['numero',
             'serie',
		    {name: 'emissao', type: 'date'},
		    'valor',
		    'publicoTipo',
		    'publicoCodigo',
		    'publicoNome',
		    'tipo'],
    proxy: {
        url : 'notas_fiscais/pendentes_autorizacao'
    }

});