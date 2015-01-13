Ext.define('nfe.view.configuracao.Configuracao', {
    extend: 'Ext.form.Panel',
    xtype: 'tela-configuracao',
    
    title: 'Configuração',
    
    controller: 'configuracao',
    viewModel: {
        type: 'configuracao'
    },
    bodyPadding: 10,
    items:[{
    	xtype: 'combo', 
    	queryMode: 'local',
		forceSelection: true, 
		valueField:'id',
		displayField: 'name',
    	fields: ['id', 'name'],
		//publishes: ['value'],
		bind:{
			store: '{modosOperacao}',
			value: '{modoOperacaoSelecionado}'
		},
		fieldLabel: 'Modo Operação', 
		allowBlank:false
    },{
    	xtype: 'combo', 
    	queryMode: 'local',
		forceSelection: true, 
		valueField:'id',
		displayField: 'name',
		fields: ['id', 'name'],
    	//publishes: ['value'],
		bind:{
			store: '{ambientes}',
			value: '{ambienteSelecionado}'
		},
		fieldLabel: 'Ambiente', 
		allowBlank:false
    }]
});