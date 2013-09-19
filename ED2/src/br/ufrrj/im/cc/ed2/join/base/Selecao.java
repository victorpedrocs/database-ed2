package br.ufrrj.im.cc.ed2.join.base;

import java.util.ArrayList;
import java.util.Collection;

public class Selecao implements Iterator{
	
	private Relacao relacao;
	private String object;
	private String column;
	private Tupla tupla;
	
	public Selecao(Relacao rel, String object, String column){
		this.relacao = rel;
		this.object = object;
		this.column = column;
	}

	@Override
	public Iterator open() {
		this.relacao.open();
		return this.relacao;
	}

	@Override
	public Iterator next() {
		this.tupla = new Tupla();
		
		while((tupla = (Tupla) relacao.next()) != null){
			if(tupla.getValorCampo(column) == this.object)
				return tupla;
		}
		
		return null;
	}

	@Override
	public Iterator close() {
		relacao.close();
		return null;
	}

	@Override
	public int custo() {
		return relacao.getNumeroLinhas();
	}
	
	

}
