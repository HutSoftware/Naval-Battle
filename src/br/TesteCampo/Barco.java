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

 * Classe que servira de base para todos os Navios, obviamente adequando-os as suas proprias
 * caracteristicas(tamanho, basicamente)
 * 
 * 
 * @author:Luciano Padua Sabenca
 */
package br.TesteCampo;



public abstract class Barco {
	protected int xCentral,yCentral;
	protected boolean direcaoPadrao;

	public Barco(int x, int y, boolean direcao)
	{
		this.xCentral = x;
		this.yCentral =y;
		this.direcaoPadrao = direcao;
	
	}
	public int getxCentral() {
		return xCentral;
	}



	public void setxCentral(int xCentral) {
		this.xCentral = xCentral;
	}



	public int getyCentral() {
		return yCentral;
	}



	public void setyCentral(int yCentral) {
		this.yCentral = yCentral;
	}



	public boolean isOrientacaoVertical() {
		return direcaoPadrao;
	}



	public void setOrientacaoVertical(boolean direcao) {
		this.direcaoPadrao = direcao;
	}
	
	
	//Metodo que devera ser implementado na classe que vc herdar
	//Sera expecifica de cada navio, basicamente pq seus tamanhos variam.
	public abstract int[][] getPosicoesOcupadas();


}
