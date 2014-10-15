Ext.define('nfe.view.cancelamento.Cancelamento', {
    extend: 'Ext.container.Container',
    requires:['Ext.grid.Panel',
              'Ext.data.JsonStore',
              'Ext.grid.property.Grid',
              'Ext.window.Toast'],
    xtype: 'tela-cancelamento',
    controller: 'cancelamento',
    viewModel: {
        type: 'cancelamento'
    },    
    title: 'title :Cancelamento',
    items: [{
      xtype: 'combobox',
      displayField: 'NOM_CURTO_FILIAL', 
      editable: false, 
      //queryMode: 'local', 
      valueField: 'NUM_CNPJ', 
      bind:{
        store: '{cancelamento}'
      },
      listeners: {
          select: function(combo, record, index) {
            Ext.toast({
              //title: 'Trocando Empresa...aguarde',
              ui: 'navigation',
              html: combo.getValue() + ' - ' + combo.getRawValue(),
              align: 't',
              bodyPadding: 10,
              width:350
            });
          }
      }
      /*
      store: { 
          fields: ['NUM_CNPJ', 'NOM_CURTO_FILIAL'], 
   
          data: [{
              "NUM_CNPJ" : 86675642000106,
              "NOM_CURTO_FILIAL" : "REGISTRO AUTOMATICO"
            }, {
              "NUM_CNPJ" : 86675642000106,
              "NOM_CURTO_FILIAL" : "CAFE"
            }, {
              "NUM_CNPJ" : 86675642000106,
              "NOM_CURTO_FILIAL" : "CAFE TERCEIROS"
            }, {
              "NUM_CNPJ" : 86675642000106,
              "NOM_CURTO_FILIAL" : "CAFE **ARAX\u00C1**"
            }, {
              "NUM_CNPJ" : 86675642000106,
              "NOM_CURTO_FILIAL" : "HORTI FRUTI"
            }, {
              "NUM_CNPJ" : 86675642000882,
              "NOM_CURTO_FILIAL" : "LAVADOR - COOPADAP"
            }, {
              "NUM_CNPJ" : 86675642000963,
              "NOM_CURTO_FILIAL" : "COOPADAP RURAL"
            }, {
              "NUM_CNPJ" : 86675642000106,
              "NOM_CURTO_FILIAL" : "GRAOS"
            }, {
              "NUM_CNPJ" : 86675642000700,
              "NOM_CURTO_FILIAL" : "EERP-OUTRAS CULTURAS"
            }, {
              "NUM_CNPJ" : 86675642000700,
              "NOM_CURTO_FILIAL" : "ESTACAO EXPERIMENTAL"
            }, {
              "NUM_CNPJ" : 86675642000297,
              "NOM_CURTO_FILIAL" : "UND. ALHO"
            }, {
              "NUM_CNPJ" : 86675642000106,
              "NOM_CURTO_FILIAL" : "LOJA INSUMOS"
            }]
 
      }*/
    }] 
});