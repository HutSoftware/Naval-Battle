/*
 * Batalha Naval Android via Bluetooth
 * Copyright (C) 2011  Luciano P. Sabença, Fabio T. Tanniguchi ,Fernando H.S. Goncalves,Jean L. Tuchapski,Pedro G. Naponoceno	

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.

 * NOS DEFENDEMOS SOFTWARE LIVRE!

 * @author:Luciano Padua Sabenca
*/
	
package br.navalbattle.mainclasses;

import java.io.*;

import android.app.Application;

public class Controladora extends Application {
	
	
	OutputStream saida  = null;
	InputStream  entrada = null;
	
	boolean acabouJogo = false;
	
	public OutputStream getSaida() {
		return saida;
	}

	public void setSaida(OutputStream saida) {
		this.saida = saida;
	}

	public InputStream getEntrada() {
		return entrada;
	}

	public void setEntrada(InputStream entrada) {
		this.entrada = entrada;
	}

	public boolean isAcabouJogo() {
		return acabouJogo;
	}

	public void setAcabouJogo(boolean acabouJogo) {
		this.acabouJogo = acabouJogo;
	}

	public void finalizarJogo()
	{
	
		try 
		{
			if(saida != null )				
				saida.close();				
			if(entrada != null)	
				entrada.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
	
	/* @Override
	public void onTerminate() {
		if(saida != null )
			try {
				saida.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(entrada != null)
			try {
				entrada.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		super.onTerminate();
	}*/
	

	public Controladora() 
	{
		
	}

}
