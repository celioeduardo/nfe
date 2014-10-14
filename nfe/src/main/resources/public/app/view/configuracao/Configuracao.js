Ext.define('nfe.view.configuracao.Configuracao', {
    extend: 'Ext.container.Container',
    requires:['Ext.grid.Panel',
              'Ext.data.JsonStore',
              'Ext.grid.property.Grid'],
    xtype: 'tela-configuracao',
    title: 'Configuração',
    initComponent: function() {        
        var me = this;
        var data = {
          "COD_EMPRESA" : 1,
          "COD_FILIAL" : 90,
          "FLG_AMBIENTE" : "1",
          "NUM_PORTA" : 8080,
          "NOM_HOST" : "TESTE",
          "END_ENVIO_NFE" : "TESTE",
          "FLG_MODO_OPER" : "5",
          "NOM_SERIE_AT" : "2",
          "DAT_CONTING" : "2014-09-22 10:21:00",
          "DSC_CONTING" : "TESTES CHAMADO #2247",
          "NUM_VERSAO" : 2,
          "NOM_SER_AT_SAIDA" : "2",
          "FLG_CTL_AVERB" : "N",
          "NOM_EMAIL_SEG" : "",
          "NOM_REMET_EMAIL" : "",
          "DSC_REMET_EMAIL" : "",
          "FLG_CANC_EVT" : "S"
        };

        /*this.myDataStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: true,
            fields: ['key', 'value' ],
            data: data,
            proxy: {    
                type: 'rest',
                reader: {
                    type: 'json',
                    rootProperty: 'rows'
                }
            }                
        });
        */
        me.items = [{
                xtype: 'propertygrid',
                id: 'propGrid',
                source: data
            }
        ];
        this.callParent();
    }
});