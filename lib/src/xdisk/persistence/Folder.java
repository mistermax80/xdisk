package xdisk.persistence;

public class Folder {

	private int codice;
	private String nome;
	private int dimensione;
	private int parent;
	private boolean prova;

	public Folder() {
		super();
	}

	public Folder(int codice, String nome, int dimensione, int parent, boolean prova) {
		super();
		this.codice=codice;
		this.nome=nome;
		this.dimensione=dimensione;
		this.parent=parent;
		this.prova=prova;
	}


	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice=codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome=nome;
	}

	public int getDimensione() {
		return dimensione;
	}

	public void setDimensione(int dimensione) {
		this.dimensione=dimensione;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent=parent;
	}

	public boolean isProva() {
		return prova;
	}

	public void setProva(boolean prova) {
		this.prova=prova;
	}

	public String toString(){
		String ret="\n*****" +this.getClass()+"*****"+
		"\ncodice: "+this.codice+
		"\nnome: "+this.nome+
		"\ndimensione: "+this.dimensione+
		"\nparent: "+this.parent+
		"\nprova: "+this.prova;
		return ret+"\n******************\n";
	}
}
