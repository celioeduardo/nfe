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
        'Ext.layout.container.Border',
        'nfe.view.main.Header'
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
            layout: {
                align: 'stretchmax'
            },
            title: {
                text: 'Coopadap/Filial 53',
                textAlign: 'right'
            }//,glyph: 61
        },        
        region: 'center',
        /*height: 400,
        width: 600,*/
        layout:'fit',
        xtype: 'tabpanel',
        ui: 'navigation',
        tabPosition: 'left',
        tabRotation: 0,
        tabBar: {
            border: false
        },

        defaults: {
            //textAlign: 'left',
            bodyPadding: 0//,            width: 20
        },

        items:[/*{
        	title: 'Notas',
            glyph: 72,
        	xtype:'notas-pendentes'
        },*/ {
            title: 'Pendentes',
            layout: 'fit',
            xtype:'grid',
            tbar:[{
                    xtype:'button',
                    text:'Enviar',
                    handler: 'onClickEnviar'
                },{
                    xtype:'button',
                    text:'Atualizar',
                    handler: 'onClickAtualizar'
                }
            ],            
            selModel: {
                selType: 'checkboxmodel',
                mode: 'MULTI'
            },
            store: {
                fields:['numero', 'valor', 'emissao'],
                data:[
                    {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001781","numero" : 204810,"serie" : 2,"modelo" : "55","emissao" : "08/01/14","dataHora" : "08/09/14","tipo" : "SAIDA","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 10999.23,"publicoNome":'HADRION SISTEMAS INTEGRADOS'},
                    {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001782","numero" : 204811,"serie" : 2,"modelo" : "55","emissao" : "08/02/14","dataHora" : "08/09/14","tipo" : "SAIDA","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 10999,"publicoNome":'RICARDO'},
                    {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001783","numero" : 204812,"serie" : 2,"modelo" : "55","emissao" : "08/03/14","dataHora" : "08/09/14","tipo" : "entrada","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 100.23,"publicoNome":'CELIO'},
                    {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001784","numero" : 204813,"serie" : 2,"modelo" : "55","emissao" : "08/04/14","dataHora" : "08/09/14","tipo" : "entrada","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 200.23,"publicoNome":'EDUARDO'},
                    {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001785","numero" : 204814,"serie" : 2,"modelo" : "55","emissao" : "08/05/14","dataHora" : "08/09/14","tipo" : "SAIDA","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 300.00,"publicoNome":'THIAGO'},
                    {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001786","numero" : 204815,"serie" : 2,"modelo" : "55","emissao" : "08/06/14","dataHora" : "08/09/14","tipo" : "SAIDA","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 10999.00,"publicoNome":'MELISSA'},
                    {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001787","numero" : 204816,"serie" : 2,"modelo" : "55","emissao" : "08/07/14","dataHora" : "08/09/14","tipo" : "entrada","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 599.00,"publicoNome":'LEANDRO'},
                    {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001788","numero" : 204817,"serie" : 2,"modelo" : "55","emissao" : "08/08/14","dataHora" : "08/09/14","tipo" : "SAIDA","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 33.00,"publicoNome":'ALEXANDRE'}
                ]
            },
            hideHeaders: true,
            columns: [{
                text: 'Número',
                width: 300,
                sortable: false,
                hideable: false,
                renderer: 'rendererNumero',
                dataIndex: 'numero'

            },{
                text: 'Valor',
                xtype:'numbercolumn',
                width: 150,                
                format:'0.00',
                dataIndex: 'valor',
                renderer: 'rendererValor',
                align: 'right'
            },{
                text: 'Observação',
                flex: 1
            }]
        },{
            title: 'Autorizadas',
            layout: 'fit',
            xtype:'grid',
            tbar:[{
                    xtype:'button',
                    text:'Cancelar',
                    handler: 'onClickCancelar'
                }
            ],            
            selModel: {
                selType: 'checkboxmodel',
                mode: 'MULTI'
            },
            store: {
                fields:['numero', 'valor', 'emissao'],
                data:[
                    {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001781","numero" : 104810,"serie" : 2,"modelo" : "55","emissao" : "08/01/14","dataHora" : "08/09/14","tipo" : "SAIDA","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 887.23,"publicoNome":'HADRION SISTEMAS INTEGRADOS'},
                    {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001782","numero" : 304811,"serie" : 2,"modelo" : "55","emissao" : "08/02/14","dataHora" : "08/09/14","tipo" : "SAIDA","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 1236,"publicoNome":'RICARDO'}
                ]
            },
            columns: [{
                text: 'Número',
                width: 300,
                sortable: false,
                hideable: false,
                renderer: 'rendererNumero',
                dataIndex: 'numero'

            },{
                text: 'Valor',
                xtype:'numbercolumn',
                width: 150,                
                format:'0.00',
                dataIndex: 'valor',
                renderer: 'rendererValor',
                align: 'right'
            },{
                text: 'Autorizada',
                dataIndex:'emissao',
                flex: 1
            }]
        },{
            title: 'Cancelamento'
        },{
            title: 'Inutilização'
        },{
            title: 'CC-e'
        },{
            title: 'Ambiente',
            xtype:'form',
            bodyPadding: 5,
            width: 350,

            layout: 'anchor',
            defaults: {
                anchor: '100%'
            },

            defaultType: 'textfield',
            items: [{
                fieldLabel: 'Homologação',
                name: 'first',
                allowBlank: false
            },{
                fieldLabel: 'Produção',
                name: 'last',
                allowBlank: false
            }],

            buttons: [{
                text: 'Salvar',
                handler: function() {
                    this.up('form').getForm().reset();
                }
            }, {
                text: 'Cancelar',
                formBind: true, //only enabled once the form is valid
                disabled: true,
                handler: function() {
                    var form = this.up('form').getForm();
                    if (form.isValid()) {
                        form.submit({
                            success: function(form, action) {
                               Ext.Msg.alert('Success', action.result.msg);
                            },
                            failure: function(form, action) {
                                Ext.Msg.alert('Failed', action.result.msg);
                            }
                        });
                    }
                }
            }]
        }]
    }]
});
