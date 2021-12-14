package br.unitins.a2topicos.model;

public enum Sexo {
	
	MASCULINO(1, "Masculino"), FEMININO(2, "Feminino");

	private int id;
	private String label;

	Sexo(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public static Sexo valueOf(int id) {
		for (Sexo sexo : Sexo.values()) {
			if (sexo.getId() == id)
				return sexo;
		}
		return null;
	}

}
