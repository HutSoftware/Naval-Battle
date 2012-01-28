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

	 * @author:Pedro Gabriel Naponoceno
*/
package br.TesteCampo;

public class Encouracado extends Barco{


		

	public Encouracado(int x1,int y1, boolean direcao)
	{
		super(x1, y1, direcao);
	}	
	
	public int[][] getPosicoesOcupadas()
	{
		int[][] ret = new int[4][3];
		if(direcaoPadrao)
		{					
			ret[0][0] = xCentral - 1;
			ret[0][1] = yCentral;			
			ret[0][2] = R.drawable.encouracado_ver_0;  
			
			ret[1][0] = xCentral;
			ret[1][1] = yCentral;			
			ret[1][2] = R.drawable.encouracado_ver_1;
			
			ret[2][0] = xCentral + 1;
			ret[2][1] = yCentral;
			ret[2][2] = R.drawable.encouracado_ver_2; 
			
			ret[3][0] = xCentral + 2;
			ret[3][1] = yCentral;
			ret[3][2] = R.drawable.encouracado_ver_3; 
		}
		else
		{			
			ret[0][0] = xCentral;
			ret[0][1] = yCentral - 1;
			ret[0][2] = R.drawable.encouracado_hor_0;
			
			ret[1][0] = xCentral;
			ret[1][1] = yCentral;
			ret[1][2] = R.drawable.encouracado_hor_1;
			
			ret[2][0] = xCentral;
			ret[2][1] = yCentral + 1;
			ret[2][2] = R.drawable.encouracado_hor_2;
			
			ret[3][0] = xCentral;
			ret[3][1] = yCentral + 2;
			ret[3][2] = R.drawable.encouracado_hor_3;
		}
		
		return ret;			
	}
}
