Ext.define('nfe.view.configuracao.Configuracao', {
    extend: 'Ext.form.Panel',
    xtype: 'tela-configuracao',
    requires:['Ext.form.FieldSet',
              'Ext.form.field.Time'],
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
    	reference: 'modoOperacaoConfig',
		publishes: ['value'],
		bind:{
			store: '{modosOperacao}'
		},
		fieldLabel: 'Modo Operação', 
		allowBlank:false,
		validator:'validarModoOperacao'
    },{
    	xtype:'fieldset',
    	title:'Contingência',
		bind:{
			hidden:'{!contingenciaHabilitada}'
		},
    	items:[{
    		xtype:'fieldcontainer',
    		layout: 'hbox',
	    	items:[{
		    	xtype: 'datefield', 
		    	reference: 'dataContingenciaConfig',
				publishes: ['value'],
				allowBlank: false,
				bind:{
		    		disabled:'{!contingenciaHabilitada}'
		    	}
//				bind:{
//					allowBlank:'{aplicarConfiguracao}'
//				}
	    	},{
		    	xtype: 'timefield', 
		    	reference: 'horaContingenciaConfig',
		    	format:'H:i',
				publishes: ['value'],
				allowBlank: false,
				bind:{
		    		disabled:'{!contingenciaHabilitada}'
		    	}
//				bind:{
//					allowBlank:'{aplicarConfiguracao}'
//				}
	    	}]
	    	},{
		    	xtype: 'textarea', 
		    	reference: 'justificativaContingenciaConfig',
		    	emptyText:'Justificativa',
		    	width: 300,
		    	minLength: 15,
		    	maxLength: 256,
				publishes: ['value'],
				allowBlank: false,
				bind:{
		    		disabled:'{!contingenciaHabilitada}'
		    	}
//				bind:{
//					allowBlank:'{aplicarConfiguracao}'
//				}
    	}]
    },{
    	xtype:'button',
    	text: 'Aplicar',
    	handler:'onAplicarConfiguracao',
    	formBind:true,
    	disabled:true,
    	bind:{
    		disabled:'{!aplicarConfiguracao}'
    	}
    },{
    	xtype:'button',
    	text: 'Cancelar',
    	handler:'onCancelar',
    	disabled:true,
    	bind:{
    		disabled:'{!aplicarConfiguracao}'
    	}
    }/*,{
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
    }*/]
});