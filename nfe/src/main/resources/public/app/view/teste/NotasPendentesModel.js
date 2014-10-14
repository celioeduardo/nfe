Ext.define('nfe.view.teste.NotasPendentesModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.notas-pendentes-teste',

    stores: {
        notasPendentes: {
            idProperty: 'notaFiscalId',
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
        notasAutorizadas: {
            idProperty: 'notaFiscalId',
            fields:['numero', 'valor', 'emissao'],
            data:[
                {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001781","numero" : 204810,"serie" : 2,"modelo" : "55","emissao" : "08/01/14","dataHora" : "08/09/14","tipo" : "SAIDA","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 10999.23,"publicoNome":'HADRION SISTEMAS INTEGRADOS'},
                {"NotaFiscalId" : "02931ADEA95F0FC5E050007F01001782","numero" : 204811,"serie" : 2,"modelo" : "55","emissao" : "08/02/14","dataHora" : "08/09/14","tipo" : "SAIDA","localDestino" : "INTERNA","finalidade" : "NORMAL","consumidorFinal" : false,"valor": 10999,"publicoNome":'RICARDO'}
            ]
        }

    }    
});