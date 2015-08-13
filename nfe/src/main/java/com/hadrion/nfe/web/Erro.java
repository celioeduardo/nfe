package com.hadrion.nfe.web;

class Erro {
		protected String detalhe;
		protected String erro;
		protected String classe;
		protected final boolean success = false;

		public Erro(){
			super();
		}
		
		public Erro(Exception ex){
			super();
			this.erro = ex.getMessage();
			this.detalhe = ex.getLocalizedMessage();
			this.classe = ex.getClass().getName();
		}
		
}
