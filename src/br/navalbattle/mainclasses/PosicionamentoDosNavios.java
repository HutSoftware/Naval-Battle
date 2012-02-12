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

 * Classe na qual se posiciona os navios 
 * 
 * PD09
 * @author:Fabio Takahashi Tanniguchi
 * @author:Fernando H.S. Goncalves
 * @author:Jean Lucas Tuchapski
 * @author:Luciano Padua Sabença
 */

package br.navalbattle.mainclasses;

import br.navalbattle.boats.Barco;
import br.navalbattle.boats.CacaMinas;
import br.navalbattle.boats.Encouracado;
import br.navalbattle.boats.Fragata;
import br.navalbattle.boats.PortaAvioes;
import br.navalbattle.boats.Submarino;
import br.navalbattle.resources.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PosicionamentoDosNavios extends Activity implements View.OnTouchListener,View.OnLongClickListener
{
	
	private boolean direcaoPadrao = true; //direcaoPadrao = true = Vertical
									 	   //direcaoPadrao = false = Horizontal
	private boolean mudouSentido = false;
	private Spinner barcos;
	Button btnMudarSentido;
	Display display ;
	private int widthTela;
	float scaleWidth;
    float scaleHeight;
	
	private TableLayout layoutPrincipal,layout;
	
	static CustomImageView[][] campo = new CustomImageView[10][10];//Estatico pra outra classe poder acessar
																  // e obter o posicionamento dos navios
																 // Static não eh banal
	Barco barco = null; /* Parte genial do codigo. Olhe mais pra baixo e entenda o pq*/	
	
	String []vetBarcos = new String[]
    {"CacaMinas","Encouracado","Fragata","Porta-aviões","Submarino"};
	//this.getResources().getStringArray(R.array.str_boats);
	char indiceBarco;


		
    public void onCreate(Bundle savedInstanceState) 
    {
    	//LPS Começo da região pra desenhar o tabuleiro...
        super.onCreate(savedInstanceState);
                        
        layoutPrincipal = new TableLayout(this);
        
        TextView texto1 = new TextView(this);
        texto1.setText(R.string.str_setbarcos);
        
        layoutPrincipal.addView(texto1);
        layout = new TableLayout(this);
        layoutPrincipal.addView(layout);
        
        display = getWindowManager().getDefaultDisplay();
        widthTela= (int)display.getWidth()/2;
        scaleWidth = ((float) widthTela) / 540;
        scaleHeight = scaleWidth ; 
        
        ScrollView scroll = new ScrollView(this);
        
        TableRow linha = new TableRow(this);
         
        


        for(int i = -1;i<10;i++)
        {
        	TextView txt = new TextView(this);
        	if(i == -1)
        		txt.setText(" ");
        	else
        		txt.setText("" +i);
        	
        	Drawable d = this.getResources().getDrawable(R.drawable.campo);
        	
        	txt.setWidth(d.getBounds().width());
        	linha.addView(txt);
        }
        
        char c ='A';
        
         

        
		for(int i = 0; i<10; i++)
		{
			layout.addView(linha);
			linha= new TableRow(this);
			TextView txt = new TextView(this);
			String str =""+ c;
			txt.setText(str);
			linha.addView(txt);
			c++;
	
			for(int j = 0; j < 10;j++)
			{
				CustomImageView civ = new CustomImageView(this);
				
		        if(scaleWidth != 1)
		        	civ.imagem = redimensionar(R.drawable.campo);
		        else
		        	civ.imagem = new BitmapDrawable(BitmapFactory.decodeResource(getResources(),R.drawable.campo));
		        civ.setImageDrawable(civ.imagem);
				civ.setCordenadas(new Coordenadas(i,j));
				civ.setOnTouchListener(this);
				civ.setOnClickListener(null);
				civ.setOnLongClickListener(this);
				linha.addView(civ);
				PosicionamentoDosNavios.campo[i][j] = civ;
			}
		}
		
		linha = new TableRow(this);
		//LPS Acabei de desenhar o tabuleiro
		barcos = new Spinner(this);
		ArrayAdapter<String> array = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,vetBarcos);
		barcos.setAdapter(array);
		
		
		
		barcos.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				//TODO arg0.getAdapter().getItem() => Com base nisso é possivel extrair o item e então eu comparo no if pra checar qual o tipo do barco :D
				//((String)arg0.getAdapter().getItem(pos)).index
				if (pos > -1 && pos < arg0.getCount())
				{
					indiceBarco = vetBarcos[pos].toUpperCase().charAt(0);
					barco = null;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//LUCIANO DEIXAR SEM FAZER NADA!
				
			}			
		});
		
		linha.addView(barcos);

		btnMudarSentido = new Button(this);
		btnMudarSentido.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				direcaoPadrao = !direcaoPadrao;
				mudouSentido = true;
				if(barco!=null)
				{				
					barco.setOrientacaoVertical(direcaoPadrao);
				}
			}
		});
		
		btnMudarSentido.setText(R.string.str_changeorietation);
		linha.addView(btnMudarSentido);
		layoutPrincipal.addView(linha);
		scroll.addView(layoutPrincipal);
		this.setContentView(scroll);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    
	@Override
	protected void onResume() 
	{		
		if(((Controladora)this.getApplication()).acabouJogo)
			this.finish();			
		
		super.onRestart();
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		
		int x = ((CustomImageView)arg0).getCordenadas().getLinha(),
			y =((CustomImageView)arg0).getCordenadas().getColuna();

		int [][] vet;
		//LPS Crio o barco...
		if(barco == null)
		{
			switch(indiceBarco){
				case 'C':
					barco = new CacaMinas(x,y,direcaoPadrao);
					break;
				case 'E':
					barco = new Encouracado(x,y,direcaoPadrao);
					break;
				case 'F':
					barco = new Fragata(x,y,direcaoPadrao);
					break;
				case 'P':
					barco = new PortaAvioes(x,y,direcaoPadrao);
					break;
				case 'S':
					barco = new Submarino(x,y,direcaoPadrao);
					break;
			}
		}
		else
		{
			if(mudouSentido)
			{				
				barco.setOrientacaoVertical(!direcaoPadrao);
			}
			vet = barco.getPosicoesOcupadas();
			if(mudouSentido)
			{
				mudouSentido = false;
				barco.setOrientacaoVertical(direcaoPadrao);
			}
			
			//Limpo As posicoes anteriores
			for(int i = 0;i<vet.length;i++)
					campo[vet[i][0]][vet[i][1]].setImageDrawable(campo[vet[i][0]][vet[i][1]].imagem);
			

			barco.setxCentral(x);
			barco.setyCentral(y);
		}

		vet = barco.getPosicoesOcupadas();
		for(int i= 0;i<vet.length && barco!=null;i++)
			if((vet[i][0] < 0 || vet[i][0] > 9) || (vet[i][1] < 0 || vet[i][1] > 9) )
			{
				barco = null;

				AlertDialog.Builder dialogo = new AlertDialog.Builder(PosicionamentoDosNavios.this);
				dialogo.setMessage(R.string.str_error1);
				dialogo.setNeutralButton("OK!", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();						
					}
				});
				dialogo.show();
			}
		
		for(int i = 0;i<vet.length && barco!= null;i++)
		{
			campo[vet[i][0]][vet[i][1]].setImageDrawable(redimensionar(vet[i][2]));
		}

		return false;
	}
	
	public BitmapDrawable redimensionar(int idImagem)
	{
		Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
                idImagem);
        
		int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();

        
        
        
       
        // calculate the scale - in this case = 0.4f
        
        
        // createa matrix for the manipulation
        Matrix matrix = new Matrix();
        
        
        BitmapDrawable bmd = null;
        // resize the bit map

   	 	matrix.postScale(scaleWidth, scaleHeight);
 
       	 // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
                          width, height, matrix, true);
   
        // make a Drawable from Bitmap to allow to set the BitMap
        // to the ImageView, ImageButton or what ever
        bmd = new BitmapDrawable(resizedBitmap);
        return bmd;
		
	}

	@Override
	public boolean onLongClick(View v) {
		//LPS Serve pra ter certeza que o barco está instanciado
		if(barco != null)
		{
			int vet[][] = barco.getPosicoesOcupadas();
			boolean podeColocar = true;
			
			for(int i= 0;i<vet.length && podeColocar;i++)
			{	
							
				if((vet[i][0] < 0 || vet[i][0] > 9) || (vet[i][1] < 0 || vet[i][1] > 9) )
				{
					podeColocar=false;
					barco = null;
				}
			}
			if(!podeColocar)
			{
				AlertDialog.Builder dialogo = new AlertDialog.Builder(PosicionamentoDosNavios.this);
				dialogo.setMessage(R.string.str_error1);
				dialogo.setNeutralButton("OK!", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();						
					}
				});
				dialogo.show();
			}
			
			else
				for(int i= 0;i<vet.length && podeColocar;i++)
				{			
					if(!campo[vet[i][0]][vet[i][1]].isVazio())
					{
						podeColocar = false;
						
						AlertDialog.Builder dialogo = new AlertDialog.Builder(PosicionamentoDosNavios.this);
						dialogo.setMessage(R.string.str_error2);
						dialogo.setNeutralButton("OK!", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();						
							}
						});
						dialogo.show();
					}
				}

			
			if(podeColocar)
			{
				for(int i = 0;i<vet.length;i++)
				{
					campo[vet[i][0]][vet[i][1]].setVazio(false);
					campo[vet[i][0]][vet[i][1]].setIdAntesDoClick(vet[i][2]);
					campo[vet[i][0]][vet[i][1]].imagem = redimensionar(vet[i][2]);
				}

				barco = null;
								
				//Sabença => Tirei os itens do Spinner!
				int pos = barcos.getSelectedItemPosition();
				String [] aux = new String[vetBarcos.length-1];
				for(int i = 0,indice2 = 0;i<vetBarcos.length;i++)
					if(i!=pos)
					{
						aux[indice2] = vetBarcos[i];
						indice2++;
					}
				
				ArrayAdapter<String> array = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,aux);
				barcos.setAdapter(array);
				vetBarcos = aux;
				
				
				if(vetBarcos.length == 0)
				{
					barcos.setVisibility(View.INVISIBLE);
					
					btnMudarSentido.setText(R.string.str_startgame);
					btnMudarSentido.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent intent = new Intent(PosicionamentoDosNavios.this,Principal.class);
							startActivity(intent);
						}
					});
					
				}
			}
		}
		return false;
	}
}
