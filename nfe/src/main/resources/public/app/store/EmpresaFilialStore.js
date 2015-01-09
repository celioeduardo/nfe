Ext.define('nfe.store.EmpresaFilialStore',{
	extend: 'Ext.data.TreeStore',
	model:	'nfe.model.EmpresaFilial',
	rootVisible:false,
	autoLoad: true,
    proxy: {
        type: 'ajax',
        url : 'filial/empresa_filial',
        reader: {
            type: 'json'
        }
    }
});