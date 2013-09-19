package br.ufrrj.im.cc.ed2.index.arvoreB;

import java.util.ArrayList;
import java.util.List;

import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;


public class BTreeOLD<Key extends Comparable<Key>, Value>  {
    private static final int M = 4;    // max children per B-tree node = M-1
    private Node root;             // root of the B-tree
    private int HT;                // height of the B-tree
    private int N;                 // number of key-value pairs in the B-tree
    private Relacao relacaoConstrucao;

    // helper B-tree node data type
    private static final class Node {
        private int m;                             // numero de filhos
        private Entry[] children = new Entry[M];   // vetor de filhos
        private Node(int k) { m = k; }             // cria um no com k filhos
    }

    // internal nodes: only use key and next
    // external nodes: only use key and value
    private static class Entry {
        private Comparable key;
        private Object value;
        private Node next;     // helper field to iterate over array entries
        public Entry(Comparable key, Object value, Node next) {
            this.key   = key;
            this.value = value;
            this.next  = next;
        }
    }

    // constructor
    public BTreeOLD() { root = new Node(0); }
 
    // return number of key-value pairs in the B-tree
    public int size() { return N; }

    // return height of B-tree
    public int height() { return HT; }


    // search for given key, return associated value; return null if no such key
    public Value get(Key key) { return search(root, key, HT); }
    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) return (Value) children[j].value;
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.m; j++) {
                if (j+1 == x.m || less(key, children[j+1].key))
                    return search(children[j].next, key, ht-1);
            }
        }
        return null;
    }


    // insert key-value pair
    // add code to check for duplicate keys
    public void put(Key key, Value value) {
        Node u = insert(root, key, value, HT); 
        N++;
        if (u == null) return;

        // need to split root
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        HT++;
    }


    private Node insert(Node h, Key key, Value value, int ht) {
        int j;
        Entry t = new Entry(key, value, null);

        // external node
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) break;
            }
        }

        // internal node
        else {
            for (j = 0; j < h.m; j++) {
                if ((j+1 == h.m) || less(key, h.children[j+1].key)) {
                    Node u = insert(h.children[j++].next, key, value, ht-1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--) h.children[i] = h.children[i-1];
        h.children[j] = t;
        h.m++;
        if (h.m < M) return null;
        else         return split(h);
    }

    // split node in half
    private Node split(Node h) {
        Node t = new Node(M/2);
        h.m = M/2;
        for (int j = 0; j < M/2; j++)
            t.children[j] = h.children[M/2+j]; 
        return t;    
    }

    // for debugging
    public String toString() {
        return toString(root, HT, "") + "\n";
    }
    private String toString(Node h, int ht, String indent) {
        String s = "";
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s += indent + children[j].key + " " + children[j].value + "\n";
            }
        }
        else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) s += indent + "(" + children[j].key + ")\n";
                s += toString(children[j].next, ht-1, indent + "     ");
            }
        }
        return s;
    }

    // comparison functions - make Comparable instead of Key to avoid casts
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
    
    public  BTreeOLD<String, String> geraArvore(String relacao, String nomeEditora) {
		  this.relacaoConstrucao = new Relacao(relacao);
		  Tupla tupla;
		
		  relacaoConstrucao.open();
		  BTreeOLD<String, String> arvore = new BTreeOLD<String, String>();
		  String arvoreIndice = new String();
		  
		  while ((tupla = (Tupla) relacaoConstrucao.next()) != null) {
			  String nomeLivro = (String) tupla.getValoresRelacao().get(1);
			  String nE = (String) tupla.getValoresRelacao().get(2);
			  
			  if(nE.equals(nomeEditora)){
				  arvoreIndice+=nomeLivro+"\n";
			  }
		  }
		  arvore.put(nomeEditora, arvoreIndice);
		  
		  relacaoConstrucao.close();
		  //System.out.println("tamanho:    " + arvore.size());
		  //System.out.println("altura:  " + arvore.height());
		  //System.out.println(arvore.get("Editora Rural"));
		  //System.out.println(arvore);
		  return arvore;
	  }
}
