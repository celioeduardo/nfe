document.title = 'NF-e';

Ext.define('nfe.view.main.Header', {
    extend: 'Ext.Container',

    xtype: 'app-header',

    title: document.title,
    cls: 'app-header',
    height: 52,

    layout: {
        type: 'hbox',
        align: 'middle'
    },

    items: [/*{
        xtype: 'component',
        cls: 'app-header-logo'
    },{
        xtype: 'component',
        cls: 'app-header-title',
        html: document.title,
        flex: 1
    },*/{
		xtype:'filialcombo',
		store:Ext.create('nfe.store.EmpresaFilialStore', {storeId: 'EmpresaFilialStore' }),
		bind:{
			value: '{filial}'
		},
		listeners: {
			select: 'onSelectFilial'
		},
        width:350,
        emptyText: 'Selecione uma Empresa/Filial...'
    },{
        xtype: 'displayfield',
        fieldLabel: 'Ambiente' ,
        labelAlign: 'right',
        reference: 'labelAmbiente',
        bind:{
        	value:'{ambiente}',
        	hidden:'{!ambienteHomologacao}'
        },
        renderer: function(valor){
        	 if (valor == 'HOMOLOGACAO')
             	return 'Homologação';
             else if (valor == 'PRODUCAO')
                 return 'Produção';
             else 
                 return 'Não definido';
        }
    },{
    	xtype: 'displayfield',
    	fieldLabel: 'Modo Operação',
    	labelAlign: 'right',
    	bind:{
    		value: '{modoOperacao}',
            hidden:'{modoOperacaoNormal}'	
    	},
    	renderer: function(valor){
        	 if (valor == 'NORMAL') return 'Normal';
             else if (valor == 'FS_DA') return 'FS-DA';
             else if (valor == 'SVC') return 'SVC';
             else return 'Não definido';
        }
    },{
    	xtype: 'displayfield',
    	fieldLabel: 'Contingência',
    	labelAlign: 'right',
    	bind:{
    		value: '{dataHoraContingencia} - {justificativaContingencia}',
    		hidden: '{!dataHoraContingencia}'
    	}
    },{
		xtype:'component',
		flex: 1
    },{           
        xtype: 'combobox',
        queryMode: 'local',
        forceSelection: true, 
        autoLoadOnValue: true,
        valueField: 'notistaId', 
        displayField: 'nome', 
        emptyText: 'Selecione Notista...',
        width:350,
        bind:{
            store: '{notistas}',
            value: '{notista}'
        },
        triggers: {
            limpar: {
                cls: 'x-form-clear-trigger',
                handler: function(ctl){
                ctl.setValue('');
                }
            }
        }
    },{
        xtype: 'component',
        id: 'app-header-username',
        cls: 'app-header-title',
        html: 'teste@hadrion.com.br',
        listeners: {
            click: 'onClickEnviar',
            element: 'el'
        },
        margin: '0 10 0 0'
    },{
		xtype:'button',
		text: 'sair',
		handler:'onSair',
		style: 'btn btn-lg btn-primary btn-block'
    }]
});

/*{
        xtype: 'component',
        cls: 'app-header-title',
        html: 'Empresa/Filial',
        flex: 1
    },*/