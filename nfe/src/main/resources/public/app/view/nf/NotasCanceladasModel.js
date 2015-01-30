Ext.define('nfe.view.nf.NotasCanceladasModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.notas-canceladas',
    stores:{
        notasCanceladas:{
            model: 'NotaFiscal',
            autoLoad: true,
            proxy:{
                url:'notas_fiscais/canceladas_resumo',
                type: 'ajax',
                extraParams:{
                    "ambiente":"{ambiente}",
                    "empresa":"{empresa}",
                    "filial":"{filial}",
                    "notista":"{notista}"
                },
                reader: {
                    type: 'json',
                    rootProperty: 'rows'
                }
            }
        }
    }
    
});