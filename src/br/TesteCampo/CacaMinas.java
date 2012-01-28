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

import android.graphics.drawable.Drawable;



public class CacaMinas extends Barco{


		
	Drawable imagem;
	
	
	public CacaMinas(int x1,int y1, boolean direcao)
	{
		super(x1, y1, direcao);
	}	
	
	public int[][] getPosicoesOcupadas()
	{
		int[][] ret = new int[2][3];
		if(direcaoPadrao)
		{					
			ret[0][0] = xCentral;
			ret[0][1] = yCentral;
			ret[0][2] = R.drawable.caca_minas_ver_0; 
			
			ret[1][0] = xCentral + 1;
			ret[1][1] = yCentral;
			ret[1][2] = R.drawable.caca_minas_ver_1; 
		}
		else
		{			
			ret[0][0] = xCentral;
			ret[0][1] = yCentral;
			ret[0][2] = R.drawable.caca_minas_hor_0;
						
			ret[1][0] = xCentral;
			ret[1][1] = yCentral + 1;
			ret[1][2] = R.drawable.caca_minas_hor_1;
		}
		
		return ret;			
	}
}
