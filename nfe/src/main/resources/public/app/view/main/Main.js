Ext.define('nfe.view.main.Main', {
    extend: 'Ext.container.Container',
    requires:['Ext.grid.Panel',
              'nfe.store.EmpresaFilialStore',
        'Ext.form.Label',
        'nfe.model.Filial',
        'nfe.model.NotaFiscal',
        'nfe.model.Notista',
        'nfe.view.nf.NotasCanceladasModel',
        'Ext.layout.container.Border',
        'nfe.view.main.Header',
        'nfe.view.configuracao.Configuracao',
        'nfe.view.main.FilialCombo'
    ],
    xtype: 'app-main',
    listeners:{
    	afterrender: 'onAfterRender'
    },
    
    controller: 'main',
    viewModel: {
        type: 'main'
    },

    layout: {
        type: 'border'
    },

    items: [/*{
        id: 'app-header',
        xtype: 'app-header',
        region: 'north'
    },*/{
        header: {
            heigth:2000,
            layout: 'hbox',
            items:[{
                xtype: 'displayfield',
                fieldLabel: 'Ambiente' ,
                labelAlign: 'right',
                reference: 'labelAmbiente',
                bind:{
                	value:'{ambiente}'
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
            		value: '{modoOperacao}'
            	},
            	renderer: function(valor){
                	 if (valor == 'NORMAL') return 'Normal';
                     else if (valor == 'FS_DA') return 'FS-DA';
                     else if (valor == 'SVC') return 'SVC';
                     else return 'Não definido';
                },
                margin: '10 10 10 10'
            },{
            	xtype: 'displayfield',
            	fieldLabel: 'Contingência',
            	labelAlign: 'right',
            	bind:{
            		value: '{dataHoraContingencia} - {justificativaContingencia}',
            		hidden: '{!dataHoraContingencia}'
            	},
                margin: '10 10 10 10'
            },{
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
                }
            }]        
        },    

        region: 'center' ,
        xtype: 'tabpanel',
        ui: 'navigation',
        tabPosition: 'left',
        tabRotation: 0,
        tabBar: {
            border: false
        },
        defaults: {
            bodyPadding: 2
        },
        items:[{
            title: 'Pendentes',
            glyph: 0xf064,
            layout:{
                type:'border'
            },
            bodyPadding: 0,
            items:[{
                title: 'Pendentes',
                collapsible: false,
                xtype:'notas-pendentes',
                selModel: {
                    selType: 'checkboxmodel',
                    mode: 'MULTI'
                },
                region: 'center',
                margin: '5 0 0 0',
                listeners:{
					notasPendentesEnviadas: 'notasPendentesEnviadas'
				}
            },{
                title: 'Lotes Pendentes',
                region:'east',
                xtype:'lotes-pendentes',
                floatable: false,
                margin: '5 0 0 0',
                width: 200,
                minWidth: 100,
                maxWidth: 500,
                listeners:{
                	obtidoRetornoLotes: 'obtidoRetornoLotes'
				}
            },{
                title: 'Autorizadas',
                region: 'south',
                xtype:'notas-autorizadas',
                height: '50%',
                minHeight: 20,
                maxHeight: 300,
                viewModel:{
                    data:{
                        nao_impressa: true
                    }
                }
            }]
        },{
        	glyph: 0xf087,
        	title: 'Autorizadas',
            layout: 'fit',
            xtype:'notas-autorizadas',
            itemId:'autorizadas',
            margin: '5 0 0 0',
            viewModel:{
                data:{
                    nao_impressa: false
                }
            },
            listeners:{
            	notaFiscalCancelada:'onNotaFiscalCancelada'
            }
            
        },{
            title: 'Canceladas',
            xtype: 'notas-canceladas',
            glyph: 0xf00d,
            layout: 'fit'
        },{
            title: 'Inutilização',
            width:'500',
            heigth:'100',
            //layout: 'fit',
        	items:[{
        		xtype:'filialcombo',
        		bind:{
        			value: '{filial}'
        		},
        		store:Ext.create('nfe.store.EmpresaFilialStore', {storeId: 'EmpresaFilialStore' }),
        		listeners: {
        			select: 'onSelectFilial'
        		}
        	}], 
            glyph: 0xf088
        },{
            title: 'CC-e',
            glyph: 0xf003
        },{
            xtype:'tela-configuracao',
            listeners:{
            	modoOperacaoAlterado:'onModoOperacaoAlterado'
            }
        }]
    }]
});
