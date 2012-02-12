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


 * Classe que definara as posices que a fragata ocupara
 * Pode servir de modelo para todas as outras classe de barcos, Mas lembre sempre que a classe
 * DEVER� HERDAR DE BARCO

 * PD09
 * @author:Luciano Padua Sabença
 */

package br.navalbattle.boats;

import br.navalbattle.resources.R;

public class Fragata extends Barco{

	
	public Fragata(int x1,int y1, boolean direcao)
	{
		super(x1, y1, direcao);
	}
	
	
	public int[][] getPosicoesOcupadas()
	{
		int[][] ret = new int[3][3];
		if(direcaoPadrao)
		{
			int x2 = xCentral;
			x2--;
			ret[0][0] = x2;
			ret[0][1] = yCentral;
			ret[0][2] = R.drawable.fragata_ver_0;
			x2++;
			x2++;
			
			ret[1][0] = xCentral;
			ret[1][1] = yCentral;
			ret[1][2] = R.drawable.fragata_ver_1;
			
			ret[2][0] = x2;
			ret[2][1] = yCentral;
			ret[2][2] = R.drawable.fragata_ver_2;
		}
		else
		{
			int y2 = yCentral;
			y2--;
			ret[0][0] = xCentral;
			ret[0][1] = y2;
			ret[0][2] = R.drawable.fragata_hor_0;
			
			ret[1][0] = xCentral;
			ret[1][1] = yCentral;
			ret[1][2] = R.drawable.fragata_hor_1;
			
			y2++;
			y2++;
			ret[2][0] = xCentral;
			ret[2][1] = y2 ;
			ret[2][2] = R.drawable.fragata_hor_2;			
		}
		
		return ret;
			
	}
	


}
