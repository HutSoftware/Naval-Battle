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

 * Clase que herda de imageView na qual foram adicionadas coisas que me interressariam 
 * (Coordenadas,id das imagens e listener proprio).
 * 
 *
 *  @author:Luciano Padua Sabença
 */
package br.TesteCampo;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView {

	

	private Coordenadas cordenadas =  null;
	
	private boolean jaClicado = false;
	
	public boolean isJaClicado() {
		return jaClicado;
	}
	public void setJaClicado(boolean jaClicado) {
		this.jaClicado = jaClicado;
	}

	BitmapDrawable imagem;
	
	private int idAntesDoClick,idDpsDoClick,idOnTouch;
	private boolean vazio= true;


	public boolean isVazio() {
		return vazio;
	}

	public void setVazio(boolean vazio) {
		this.vazio = vazio;
	}

	public int getIdAntesDoClick() {
		return idAntesDoClick;
	}

	public void setIdAntesDoClick(int idAntesDoClick) {
		this.idAntesDoClick = idAntesDoClick;
	}

	public int getIdDpsDoClick() {
		return idDpsDoClick;
	}

	public void setIdDpsDoClick(int idDpsDoClick) {
		this.idDpsDoClick = idDpsDoClick;
	}

	public Coordenadas getCordenadas() {
		return cordenadas;
	}

	public void setCordenadas(Coordenadas cordenadas) {
		this.cordenadas = cordenadas;
	}

	public CustomImageView(CustomImageView c) {
		this(c.getContext(),c.idAntesDoClick,c.idDpsDoClick);
		this.cordenadas = c.cordenadas;
		this.vazio = c.vazio;
		this.idOnTouch = c.idOnTouch;
		this.imagem = c.imagem;
		this.setImageDrawable(this.imagem);
		
	
	}
	public CustomImageView(Context context) {
		this(context,R.drawable.campo,R.drawable.campo_bombardeado);
	
	
	}

	public CustomImageView(Context context,int idImgAntes,int idImgdps) {
		super(context);
		this.idAntesDoClick = idImgAntes;
		this.idDpsDoClick = idImgdps;
		this.setImageResource(idImgAntes);
	}

	
	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	
	}

	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	
	}
}
