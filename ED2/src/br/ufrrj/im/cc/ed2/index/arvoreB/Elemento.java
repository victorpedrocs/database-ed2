package br.ufrrj.im.cc.ed2.index.arvoreB;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Elemento implements ElementoArvoreB{

	private int chave;

	public Elemento (int chave) { this.chave = chave; }
	  
	public int compara (ElementoArvoreB it) {
		Elemento item = (Elemento) it;
	    if (this.chave < item.chave) return -1;
	    else if (this.chave > item.chave) return 1;
	    return 0;
	}
	  
	public void alteraChave (Object chave) {
		Integer ch = (Integer) chave; this.chave = ch.intValue ();
	}
	  
	public Object recuperaChave () { return new Integer (this.chave); }
	  
	public String toString () { return "" + this.chave; }
	  
	public void gravaArq (RandomAccessFile arq) throws IOException {
	    arq.writeInt (this.chave);
	}
	  
	public void leArq (RandomAccessFile arq) throws IOException {
	    this.chave = arq.readInt ();
	}
	 
	public static int tamanho () { return 4; /* @{\it 4 bytes}@ */ }

}
