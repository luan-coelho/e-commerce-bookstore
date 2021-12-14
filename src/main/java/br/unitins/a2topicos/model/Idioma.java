package br.unitins.a2topicos.model;

public enum Idioma {
	
	PORTUGUES(1, "Português"), 
	INGLES(2, "Inglês"),
	ESPANHOL(3,"Espanhol");

	private int id;
	private String label;

	Idioma(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public static Idioma valueOf(int id) {
		for (Idioma idioma : Idioma.values()) {
			if (idioma.getId() == id)
				return idioma;
		}
		return null;
	}

}
