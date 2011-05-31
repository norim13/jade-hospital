package jadehospital;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;



public class EtatSante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum symptomeEnum{MAUX_DE_TETE, GRIPPE, APPENDICITE};
	private int etat;
	private int lower = 0;
	private int higher = 10;
	private symptomeEnum symptome;
	
	public EtatSante(){
		this.symptome=this.randomLetter();
		this.etat=(int)(Math.random() * (higher-lower)) + lower;
	}
	public EtatSante(symptomeEnum s,int e){
		this.symptome=s;
		this.etat=e;
	}

	public int getEtat() {
		return etat;
	}
	
	public String etatAsString() {
		return String.format("%d", etat);
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public symptomeEnum getSymptome() {
		return symptome;
	}
	
	public String symptomeAsString() {
		return symptome.toString();
	}

	public void setSymptome(symptomeEnum symptome) {
		this.symptome = symptome;
	}
	
	private symptomeEnum randomLetter() {
	    int pick = new Random().nextInt(symptomeEnum.values().length);
	    return symptomeEnum.values()[pick];
	}

	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		//always perform the default de-serialization first
		aInputStream.defaultReadObject();
	}

	private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
		//perform the default serialization for all non-transient, non-static fields
		aOutputStream.defaultWriteObject();
	}

}
