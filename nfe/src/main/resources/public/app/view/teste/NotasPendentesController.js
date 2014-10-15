Ext.define('nfe.view.teste.NotasPendentesController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.MessageBox'
    ],

    alias: 'controller.notas-pendentes-teste',

    aspas: function (s) {
        return "'" + s + "'";
    },

    onClickEnviar: function () {

        var s = this.getView().getSelection();

        if (s.length==0){
            Ext.Msg.alert('Selecionar','Nenhum item selecionado!');
        } else {
            Ext.Msg.confirm('Confirmar', 'Enviar Notas?', 
                function (choice) {
                    if (choice == 'yes') {                        
                        var temp = '';
                        for (var i = 0; i < s.length; i++) {
                            temp = temp + "'" + s[i].get('NotaFiscalId') + "'" + ',';
                        };
                        temp = temp.substr(0,temp.length-1);
                    }
                    Ext.Msg.alert('Selecionadas',temp);
                });
        }
    },

    onClickAtualizar: function (){
        //console.log("url proxy:" + this.getStore().load({url:'newUrl'});
        //    console.log("url proxy:" + JSON.stringify(this.getStore().getProxy()));
        //this.getViewModel().getStore('notasPendentes').getModel().getProxy().url = "notas_fiscais/pendentes_autorizacao";
        //this.getViewModel().getStore('notasPendentes').reload();
        var local = this.getViewModel().getStore('notasPendentes');
        local.filter("NotaFiscalId" , "02931ADEA95F0FC5E050007F01001788");
        local.reload();
    },

    onClickFiltrar: function (){
        var local = this.getViewModel().getStore('notasPendentes');
        var s = this.getView().getSelection();
        for (var i = 0; i < s.length; i++) {
            local.filter('NotaFiscalId' , s[i].get('NotaFiscalId'));
        };

        //local.reload();
    },

    rendererNumero: function(numero, metadata, rec){
        var nf = rec.get('serie') != null ? numero+'/'+rec.get('serie') : numero,
            es = rec.get('tipo') == 'E' ? 'entrada' : 'saída';

        return Ext.String.format(
            '<div style="font-size:x-large;font-weight: bold;">{0}</div>'+
            '<div style="padding: 2px 0px 0px 0px; font-weight: 400;font-size: 18px;line-height: 22px; font-family:Arial">{1}</div>'+
            '<hr style="margin: 2px">'+
            '<div style="font-size: small;font-color=gray;color: gray;font-style: italic;">{2} - emitida em {3}</div>',
            nf,
            rec.get('publicoNome'),
            es,
            Ext.util.Format.date(rec.get('emissao'),'d/m/Y'));
    },

    rendererValor: function(valor, metadata, rec){
        return Ext.String.format(
            '<div style="font-size: large;font-weight: bold;font-style: italic;margin-top: 20px;">{0}</div>',
            "R$ " + Ext.util.Format.number(valor,'0,000.00'));
    }

});
