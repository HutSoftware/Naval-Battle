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

 * Classe que contem os tabuleiros e que gerencia as conexoes bluetooth, enviando, recebendo dados e atualizando os campos 
 * de acordo com eles
 * 
 * PD09
 * @author:Fabio Takahashi Tanniguchi 
 * @author:Fernando H.S. Goncalves	  	
 * @author:Luciano Padua Sabença
 * 
 */
package br.navalbattle.mainclasses;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.navalbattle.resources.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends Activity implements View.OnClickListener{

	
	private  CustomImageView[][]  campo = new CustomImageView[10][10];  
	private  CustomImageView[][]  campoUsuario = new  CustomImageView[10][10] ; 
	private boolean suaVez;
	private InputStream entrada;
	private OutputStream saida;
	private int lin,col;
	private Handler handler = new Handler();
	private Thread t;
	boolean acertou = false;
	
	Display display ;
	private int widthTela;
	float scaleWidth;
    float scaleHeight;
	
	private int restantesSeus  = 17;
	//LUCIANO Quando chegar a .0 quer dizer que acabou o jogo
 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{		
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) 
	    {
	    	((Controladora)this.getApplication()).finalizarJogo();	
			Principal.this.finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}

	
	@Override
   public void onCreate(Bundle savedInstanceState) {
		
    
        super.onCreate(savedInstanceState);
        
        ScrollView scroll = new ScrollView(this);
        
        display = getWindowManager().getDefaultDisplay();
        widthTela= (int)display.getWidth()/2;
        scaleWidth = ((float) widthTela) / 540;
        scaleHeight = scaleWidth ; 
                
        
        TableRow linhaPrincipal = new TableRow(this);
        
        TableLayout layoutPrincipal = new TableLayout(this);
        
        TextView texto1 = new TextView(this),
        		 texto2 = new TextView(this);
        TableRow linhaTextos = new TableRow(this);
        linhaTextos.addView(texto1);
        linhaTextos.addView(texto2);
        texto1.setText(R.string.str_boardopponent);
        texto1.setWidth(widthTela);
        texto2.setText(R.string.str_yourboard);
        layoutPrincipal.addView(linhaTextos);
        
        
        TableLayout layout = new TableLayout(this);
        linhaPrincipal.addView(layout);
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
        layout.addView(linha);
        
        

        char c ='A';
		for(int i = 0; i<10; i++)
		{
			linha= new TableRow(this);
			TextView txt = new TextView(this);
			String str =""+ c;
			txt.setText(str);
			linha.addView(txt);
			c++;
		
			for(int j = 0; j < 10;j++)
			{
				CustomImageView civ = new CustomImageView(this);
				
				civ.imagem = redimensionar(R.drawable.campo);
				
				civ.setImageDrawable(civ.imagem);
				
				civ.setCordenadas(new Coordenadas(i,j));
				civ.setOnClickListener(this);
				linha.addView(civ);
				campo[i][j] = civ;
			}
			layout.addView(linha);
		}
		
		TableLayout layoutCampoUsuario = new TableLayout(this);
				
		linha = new TableRow(this);
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
		layoutCampoUsuario.addView(linha);
        

		c ='A';
		for(int i = 0; i<10; i++)
		{
			
			linha= new TableRow(this);
			TextView txt = new TextView(this);
			String str =""+ c;
			txt.setText(str);
			linha.addView(txt);
			c++;
		
			for(int j = 0; j < 10;j++)
			{
				
				campoUsuario[i][j] = new CustomImageView(PosicionamentoDosNavios.campo[i][j]);//Pegar o campo com as posi??es dos navios
				linha.addView(campoUsuario[i][j]);
				
			}
			layoutCampoUsuario.addView(linha);
		 }
		
		
		linhaPrincipal.addView(layoutCampoUsuario);
		layoutPrincipal.addView(linhaPrincipal);
		scroll.addView(layoutPrincipal);
		this.setContentView(scroll);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		Controladora controladora = (Controladora)this.getApplication();
		saida = controladora.saida;
		entrada = controladora.entrada;
		try 
		{
			int codigoInicial = entrada.read();
			if(codigoInicial == 0)
			{
				int codAux = 1;
				
				saida.write(codAux);
				suaVez = false;
				

			}
			else
				suaVez =  true;
			
			
			//Sabença => NÃO MEXER ! PERIGO!
			t = new Thread(new Runnable()
			{
				byte[] bytesEntrada = new byte[4],vetSaida = new byte[4];
				boolean acabou= false;
				@Override
				public void run() 
				{
					while(true)
					{
						try 
						{							
							entrada.read(bytesEntrada);
							if(bytesEntrada[0] == 0) //recebe jogada
							{
								//LPS QUER DIZER QUE O OUTRO JOGADOR JOGOU
								//LPS VERIFICO SE ACERTOU ALGUM NAVIO E RESPONDE
								if(campoUsuario[bytesEntrada[1]][bytesEntrada[2]].isVazio())
								{
									vetSaida[0] = 1;
									vetSaida[1] = bytesEntrada[1];
									vetSaida[2] = bytesEntrada[2];
									vetSaida[3] = 0;
									
								}
								else
								{
									acertou = true;
									restantesSeus--;
									
									if(restantesSeus != 0)
									{
										vetSaida[0] = 1;
										vetSaida[1] = bytesEntrada[1];
										vetSaida[2] = bytesEntrada[2];
										vetSaida[3] = 1;
									}
									else
									{										
																
										vetSaida[0] = 2;
										vetSaida[1] = bytesEntrada[1];
										vetSaida[2] = bytesEntrada[2];
										vetSaida[3] = 1;
										acabou = true;
										
																		
										
															  
									}
								}
								
								saida.write(vetSaida);
								lin = bytesEntrada[1];
								col = bytesEntrada[2];
								
								//LUCIANO MOSTRAR PRA ESSE USUARIO AONDE O OUTRO JOGOU
								handler.post(new Runnable(){					
									@Override
									public void run() 
									{
										if(acertou)
										{
											acertou = false;
											campoUsuario[lin][col].imagem = redimensionar(R.drawable.atingido);
										}
										else
											campoUsuario[lin][col].imagem = redimensionar(R.drawable.campo_bombardeado);
										
										campoUsuario[lin][col].setImageDrawable(campoUsuario[lin][col].imagem);
										
									}
									
								});
								
								suaVez = true;
								
								if(acabou)
								{
									handler.post(new Runnable(){
										
										@Override
										public void run() {
											AlertDialog.Builder alerta = new AlertDialog.Builder(Principal.this);
											alerta.setMessage("Você perdeu :( ,tente de novo depois!\nFeito por:\nLuciano Pádua Sabença\n" +
													"Fábio Takahashi Tanniguchi\n Fernando H.S. Gonçalves\n Jean Lucas Tuchapski"+
													"\nPedro Gabriel Naponoceno");
											alerta.setPositiveButton("Finalizar jogo",new OnClickListener(){

												@Override
												public void onClick(DialogInterface arg0,int arg1) {
															Principal.this.finish();
														}
														
													});
									
											}
									});
								}
							}
							else
								if(bytesEntrada[0] == 1) //Recebeu resposta se onde ele jogou já tem alguma coisa
								{
									//LPS Atualizo o campo...
									handler.post(new Runnable(){
											
											@Override
											public void run() {
												if(bytesEntrada[3] == 0)
												{
													campo[bytesEntrada[1]][bytesEntrada[2]].imagem = redimensionar(R.drawable.campo_bombardeado);
													campo[bytesEntrada[1]][bytesEntrada[2]].setImageDrawable(campo[bytesEntrada[1]][bytesEntrada[2]].imagem);
													
												}
												else
												{
													campo[bytesEntrada[1]][bytesEntrada[2]].imagem = redimensionar(R.drawable.barco_meio);
													campo[bytesEntrada[1]][bytesEntrada[2]].setImageDrawable(campo[bytesEntrada[1]][bytesEntrada[2]].imagem);
												}
											}
										});
								}
								else //ganha o jogo
								{
									handler.post(new Runnable(){
										
										@Override
										public void run() {
											AlertDialog.Builder alerta = new AlertDialog.Builder(Principal.this);
											alerta.setMessage(R.string.str_youwin + R.string.str_about);
											alerta.setPositiveButton(R.string.str_endgame,new OnClickListener(){

												@Override
												public void onClick(DialogInterface arg0,int arg1) {
															Principal.this.finish();
														}
														
													});
											alerta.show();
													
										}
									});
								}
							
							if(acabou)
								handler.post(new Runnable(){
									
									@Override
									public void run() {
										AlertDialog.Builder alerta = new AlertDialog.Builder(Principal.this);
										alerta.setMessage(R.string.str_youlose+R.string.str_about);
										alerta.setPositiveButton(R.string.str_endgame ,new OnClickListener(){

											@Override
											public void onClick(DialogInterface arg0,int arg1) 
											{
												((Controladora)Principal.this.getApplication()).acabouJogo = true;	
												Principal.this.finish();												
											}
													
												});
										alerta.show();
								
										}
								});
						}
						catch (IOException e) {
							
							e.printStackTrace();
						}
						
						
					}
					
				}
				
			});
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		
		
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
	protected void onResume() {
		// LUCIANO Auto-generated method stub
		super.onResume();
		t.start();
		
	}





	@Override
	protected void onPause() {
		// LUCIANO Auto-generated method stub
		super.onPause();
		t.stop();
	}





	@Override
	protected void onStop() {
		// LUCIANO Auto-generated method stub
		super.onStop();
		try{
			entrada.close();
			saida.close();
		}
		catch(Exception e)
		{}
	}





	//Sabença => NÃO MEXER !
	@Override
	public void onClick(View arg0) {
		
		if(suaVez)
		{
		    CustomImageView civClicado = (CustomImageView)arg0;
		   
		    if(!civClicado.isJaClicado())
		    {
		    	civClicado.setJaClicado(true);
		    
			    try 
			    {	  				
					byte[] vetBytes = new byte[3];
					vetBytes[0] = 0 ;//BYTE DE CONTROLE => QUER DIZER QUE SÂO COORDENADAS
					vetBytes[1] = (byte)civClicado.getCordenadas().getLinha();//
					vetBytes[2] = (byte)civClicado.getCordenadas().getColuna();
					saida.write(vetBytes);
								
					suaVez = false;					
				} 
			    catch (IOException e) 
			    {
					e.printStackTrace();
				}
		    }
		
		}
		else
			Toast.makeText(Principal.this, R.string.str_itsnotyourturn, Toast.LENGTH_LONG).show();
	    
		
	}
}