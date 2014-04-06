package com.wow.doge.services;

public class DogeAlias {

	private final String associationPath;
	private final String alias;

	public DogeAlias(String associationPath, String alias) {
		super();
		this.associationPath = associationPath;
		this.alias = alias;
	}

	public String getAssociationPath() {
		return associationPath;
	}

	public String getAlias() {
		return alias;
	}

	@Override
	public String toString() {
		return "DogeAlias [associationPath=" + associationPath + ", alias=" + alias + "]";
	}

}
