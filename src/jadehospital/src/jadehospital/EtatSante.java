package jadehospital;

import java.util.Random;



public class EtatSante {

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

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public symptomeEnum getSymptome() {
		return symptome;
	}

	public void setSymptome(symptomeEnum symptome) {
		this.symptome = symptome;
	}
	
	private symptomeEnum randomLetter() {
	    int pick = new Random().nextInt(symptomeEnum.values().length);
	    return symptomeEnum.values()[pick];
	}




}
