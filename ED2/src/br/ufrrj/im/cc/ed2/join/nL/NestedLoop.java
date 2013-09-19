package br.ufrrj.im.cc.ed2.join.nL;
import br.ufrrj.im.cc.ed2.index.arvoreB.BTreeOLD;
import br.ufrrj.im.cc.ed2.join.base.Iterator;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;

public class NestedLoop implements Iterator {
	private Relacao relacaoConstrucao;
	private Relacao relacaoPoda;
	private Relacao relacaoAux;
	
	public NestedLoop(String relacao1, String relacao2) {
		this.relacaoConstrucao = new Relacao(relacao1);
		this.relacaoPoda = new Relacao(relacao2);
	}
	
	public NestedLoop(String relacao1, String relacao2, String relacao3) {
		this.relacaoConstrucao = new Relacao(relacao1);
		this.relacaoPoda = new Relacao(relacao2);
		this.relacaoAux = new Relacao(relacao3);
	}

	public Iterator open(String texto, int tipo) {
		Tupla tupla1,tupla2;
		
		relacaoConstrucao.open();
		while ((tupla1 = (Tupla) relacaoConstrucao.next()) != null) {
			if(tupla1.getValoresRelacao().get(1).equals(texto)){
				String idRelacao1 = (String) tupla1.getValoresRelacao().get(0);
				
				relacaoPoda.open();
				if(tipo ==1){
					while ((tupla2 = (Tupla) relacaoPoda.next()) != null) {
						if(tupla2.getValoresRelacao().get(4).equals(idRelacao1)){
							System.out.println(tupla2.getValoresRelacao().get(1));
						}
					}
				}
				else if(tipo ==2){
					while ((tupla2 = (Tupla) relacaoPoda.next()) != null) {
						if(tupla2.getValoresRelacao().get(3).equals(idRelacao1)){
							System.out.println(tupla2.getValoresRelacao().get(1));
						}
					}
				}
				else if(tipo==3){
					
					
				}
				else{
					while ((tupla2 = (Tupla) relacaoPoda.next()) != null) {
						if(tupla2.getValoresRelacao().get(3).equals(idRelacao1)){
							System.out.println(tupla2.getValoresRelacao().get(1));
						}
					}
				}
				relacaoPoda.close();
			}
		}
		
		relacaoConstrucao.close();
		
		return this;
	}

	public Iterator next() {
		
		return null;
	}

	public Iterator close() {
		//fecha as duas relations
		return null;
	}

	@Override
	public Iterator open() {
		// TODO Auto-generated method stub
		return null;
	}

	public void open(String descCategoria, String nomeAutor, String nomeEditora) {
		Tupla tupla1,tupla2,tupla3;
		String idRelacao1=null;
		String idRelacao2=null;
		String idRelacao3=null;
		
		relacaoConstrucao.open();
		while ((tupla1 = (Tupla) relacaoConstrucao.next()) != null) {
			if(tupla1.getValoresRelacao().get(1).equals(descCategoria)){
				idRelacao1 = (String) tupla1.getValoresRelacao().get(0);
				
				relacaoPoda.open();
				while ((tupla2 = (Tupla) relacaoPoda.next()) != null) {
					String nE = (String) tupla2.getValoresRelacao().get(2);
					idRelacao2=(String) tupla2.getValoresRelacao().get(3);
					if(idRelacao1.equals(idRelacao2) && nE.equals(nomeEditora)){
						idRelacao3=(String) tupla2.getValoresRelacao().get(4);
						
						relacaoAux.open();
						while ((tupla3 = (Tupla) relacaoAux.next()) != null) {
							String nA=(String) tupla3.getValoresRelacao().get(1);
							String idAutor = (String) tupla3.getValoresRelacao().get(0);
							if(idRelacao3.equals(idAutor) && nA.equals(nomeAutor)){
								System.out.println(tupla2.getValoresRelacao().get(1));
							}
						}
						
					}
				}
			}
			
		}
		relacaoAux.close();
		relacaoPoda.close();
		relacaoConstrucao.close();
	}

	@Override
	public int custo() {
		// TODO Auto-generated method stub
		return 0;
	}
}
