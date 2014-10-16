Ext.define('nfe.view.nf.NotasPendentesController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.MessageBox'
    ],

    alias: 'controller.notas-pendentes',

    onClickEnviar: function () {

        if (this.getView().getSelection().length==0){
            Ext.Msg.alert('Selecionar','Nenhum item selecionado!');
        } else {
            Ext.Msg.confirm('Confirmar', 'Enviar Notas?', 'enviarNotas', this);
        }
    },

    enviarNotas: function (choice) {
        if (choice === 'yes') {
            var s = this.getView().getSelection();

            var temp = '';
            for (var i = 0; i < s.length; i++) {
                temp = temp + aspas(s[i].getId()) + ',';
            };
            temp = temp.substr(0,temp.length-1);
            alert(temp);
        }
    },

    aspas: function (s) {
        return "'" + s + "'";
    },

    onClickAtualizar: function (){
        //console.log("url proxy:" + this.getStore().load({url:'newUrl'});
            //pendentes_autorizacao_selecionadas
        //    console.log("url proxy:" + JSON.stringify(this.getStore().getProxy()));
        this.getViewModel().getStore('notasPendentes').load(
            {"params":{
                "notafiscalid":"03F79B1D8D397592E050007F01005CC8"
            }
        });
        //this.getViewModel().getStore('notasPendentes').reload();
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
