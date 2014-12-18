/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('nfe.view.main.Main', {
    extend: 'Ext.container.Container',
    requires:['Ext.grid.Panel',
        'nfe.model.NotaFiscal',
        'nfe.view.cancelamento.CancelamentoModel',
        'Ext.layout.container.Border',
        'nfe.view.main.Header',
        'nfe.view.configuracao.Configuracao'
    ],
    xtype: 'app-main',
    
    controller: 'main',
    viewModel: {
        type: 'main'
    },

    layout: {
        type: 'border'
    },

    items: [{
        id: 'app-header',
        xtype: 'app-header',
        region: 'north'
    },{
        header: {
            heigth:2000,
            title: {
                    text: 'Empresa/Filial: ',
                    textAlign: 'right'
            },   
            items:[{           
                xtype: 'combobox',
                displayField: 'NOM_CURTO_FILIAL', 
                //scope       : this,
                editable: false, 
                //queryMode: 'local', 
                //flex: 1,
                //forceSelection: true,
                emptyText: 'Selecione uma Empresa/Filial...',
                //selectOnFocus: true,                
                width:350,
                valueField: 'NUM_CNPJ', 
                bind:{
                    store: '{empresaFilial}'
                },
                //handler: 'onClickEnviar',
                listeners: {
                    select: 'onChangeFilial'/*function(combo, record, index) {
                        onChangeFilial();

                        Ext.toast({
                            title: 'Trocando Empresa...aguarde',
                            ui: 'navigation',
                            html: combo.getValue() + ' - ' + combo.getRawValue(),
                            align: 't',
                            bodyPadding: 10,
                            width:350
                        });
                    }*/
                }
            }]        
        },   
        region: 'center',
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
                /*region:'center',
                xtype:'notas-pendentes',
                width: 250
            },{
                region:'east',
                xtype:'notas-pendentes'
            },{
                region:'south',
                xtype:'notas-pendentes',
                heigth: 150*/
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
                //html: '<h2>Main Page</h2><p>This is where the main content would go</p>'
            },{
                title: 'Lotes Pendentes',
                region:'east',
                xtype:'lotes-pendentes',
                floatable: false,
                margin: '5 0 0 0',
                width: 500,
                minWidth: 100,
                maxWidth: 500,
                listeners:{
                	obtidoRetornoLotes: 'obtidoRetornoLotes'
				}
            },{
                title: 'Autorizadas',
                region: 'south',
                xtype:'notas-pendentes',
                height: '50%',
                minHeight: 20,
                maxHeight: 300
            }]
        },
        /*{
            title: 'Pendentes',
            xtype:'notas-pendentes-teste',
            glyph: 0xf064,
            reference: 'gridPendentes',
            hideHeaders: true,
            selModel: {
                selType: 'checkboxmodel',
                mode: 'MULTI'
            },            
            bind:{
                store: '{notasPendentes}'
            }
        },*/{
            title: 'Autorizadas old',
            xtype:'notas-pendentes-teste',
            glyph: 0xf087,
            reference: 'gridAutorizada',
            selModel: {
                selType: 'checkboxmodel',
                mode: 'SINGLE'
            },            
            bind:{
                store: '{notasAutorizadas}'
            }
        },{
            title: 'Cancelamento',
            xtype: 'tela-cancelamento',
            glyph: 0xf00d
        },{
            title: 'Inutilização',
            glyph: 0xf088
        },{
            title: 'CC-e',
            glyph: 0xf003
        },{
            xtype:'tela-configuracao'
        }]
    }]
});
