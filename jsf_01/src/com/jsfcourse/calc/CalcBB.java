package com.jsfcourse.calc;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@RequestScoped
//@SessionScoped
public class CalcBB {
	private String kwota;
	private String procent;
	private String ileLat;
	private Double wynik;
	private Double wzor;
	private Double wzorLata;

	@Inject
	FacesContext ctx;

	
	public String getKwota() {
		return kwota;
	}

	public void setKwota(String kwota) {
		this.kwota = kwota;
	}

	public String getProcent() {
		return procent;
	}

	public void setProcent(String Procent) {
		this.procent = procent;
	}
	
	public String getIleLat() {
		return ileLat;
	}

	public void setIleLat(String ileLat) {
		this.ileLat = ileLat;
	}

	public Double getwynik() {
		return wynik;
	}

	public void setwynik(Double wynik) {
		this.wynik = wynik;
	}

	public boolean wzorKredyt() {
		try {
			double kwota = Double.parseDouble(this.kwota);
			double procent = Double.parseDouble(this.procent);
			double ileLat = Double.parseDouble(this.ileLat);
			
			wzor = 1+((procent/100)/12);
			wzorLata = ileLat * 12;
			
			
			wynik = kwota * Math.pow(wzor, wzorLata) * (wzor-1)/((Math.pow(wzor, wzorLata))-1);
			wynik = (double) Math.round(wynik);

			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana prawidłowo", null));
			return true;
		} catch (Exception e) {
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów", null));
			return false;
		}
	}

	// Go to "showwynik" if ok
	public String calc() {
		if (wzorKredyt()) {
			return "showwynik";
		}
		return null;
	}

	// Put wynik in messages on AJAX call
	public String calc_AJAX() {
		if (wzorKredyt()) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "wynik: " + wynik, null));
		}
		return null;
	}

	public String info() {
		return "info";
	}
}