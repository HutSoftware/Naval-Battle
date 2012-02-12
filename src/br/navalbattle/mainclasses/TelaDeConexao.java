/*
 * Batalha Naval Android via Bluetooth
 * Copyright (C) 2011  Luciano P. Sabença, Fabio T. Tanniguchi ,Fernando H. S. Goncalves,Jean L. Tuchapski,Pedro G. Naponoceno	

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

 * Pega uma lista com os dispositivos pareados e implementa o servidor que aceita conexoes...
 * Classe meio rudimentar ainda. Ha varias coisas que podem ser melhoradas nela. Desculpe-nos, nao deu tempo...  
 * :((
 
 * PD09
 
 * @author:Fabio Takahashi Tanniguchi 
 * @author:Fernando H. S. Goncalves	  	
 * @author:Luciano Padua Sabença
 * 
 */
package br.navalbattle.mainclasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import br.navalbattle.resources.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TelaDeConexao extends Activity implements AdapterView.OnItemClickListener
{
	private static int DISCOVERY_REQUEST = 1;
	private BluetoothAdapter bluetooth;
	private ListView lst;
	private Button btnProcurar;
	private Set<BluetoothDevice> pairedDevices;
	Thread bluetoothServer;
	Handler handler = new Handler();
	
	BluetoothSocket socketBT;
	
	ArrayList<String> lista;
    ArrayAdapter<String> aa;

	BroadcastReceiver bluetoohState = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			
			
			String prevStateExtra = BluetoothAdapter.EXTRA_PREVIOUS_STATE;
			String stateExtra = BluetoothAdapter.EXTRA_STATE;
			int state = intent.getIntExtra(stateExtra, -1);
			int previousState = intent.getIntExtra(prevStateExtra, -1);
			
			
			switch(state)
			{
				case (BluetoothAdapter.STATE_ON):
				{
			
					unregisterReceiver(this);
					listarDispositivos();
					bluetoothServer.start();
					break;
					
				}
				
			}
		}
		
	};
    static final UUID SERIAL_PORT_SERVICE_CLASS_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	
	
    BroadcastReceiver discoveryResult = new BroadcastReceiver()
	{

		@Override
		public void onReceive(Context context, Intent intent) 
		{		
			
			String remoteDeviceName = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
    		
    		BluetoothDevice remoteDevice;
    		remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
    		
    		
    		//faca alguma coisa com o bluetoothdevice
    		if(remoteDeviceName != null)
    			if(!remoteDeviceName.equals("")) //&& !pairedDevices.contains(remoteDevice))
    			{
    				lista.add(remoteDeviceName);
    				lst.setAdapter(aa);
    				    				
    				//pairedDevices.add(remoteDevice);
        		}
		}		
	};
	
	@Override
	protected void onResume() 
	{
		if(((Controladora)this.getApplication()).acabouJogo)
		{
			((Controladora)this.getApplication()).finalizarJogo();
			this.finish();
			
			((Controladora)this.getApplication()).acabouJogo = false;
		}
		
		super.onRestart();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bluetooth = BluetoothAdapter.getDefaultAdapter();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
   
       
        
        
        lst = (ListView)findViewById(R.id.lstDispositivosPareados);        
        lst.setOnItemClickListener(this);
        
    	lista = new ArrayList<String>();
        aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lista);
        
       /* btnProcurar = (Button) findViewById(R.id.btnProcurar);
        btnProcurar.setOnClickListener(new Button.OnClickListener()
        			{
						@Override
						public void onClick(View v) 
						{
							registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));
			        		if(!bluetooth.isDiscovering())
			        			bluetooth.startDiscovery();							
								
			        		
						}
        				
        			}
        			);
        */
            bluetoothServer = new Thread(new Runnable(){

				@Override
				public void run() {
					String nome ="ServerBlue";
					
					try {
						 BluetoothServerSocket btServer = bluetooth.listenUsingRfcommWithServiceRecord(nome, SERIAL_PORT_SERVICE_CLASS_UUID);
						 BluetoothSocket btTemporario = null;
						 btTemporario = btServer.accept();
						 
						 synchronized(this)
						 {
							 if(btTemporario != null)
								 socketBT = btTemporario ;
						 }
						 btServer.close();
						 handler.post(new Runnable(){
	
							@Override
							public void run() {
								gerenciaConexao(false);
								
							}
							 
						 });
						 
						
					} catch (Exception e) {
						Toast.makeText(TelaDeConexao.this, R.string.str_error3, Toast.LENGTH_LONG).show();
					}
				}
        	
        });
        
                

        
        
        
        if (bluetooth == null) 
        {
        	Toast.makeText(this, R.string.str_nobluettoth, Toast.LENGTH_LONG).show();
        }
        else //existe bluetooth
        {
        	if(!bluetooth.isEnabled())
        	{
        		String actionStateChanged = BluetoothAdapter.ACTION_STATE_CHANGED;
        		String enableBT = BluetoothAdapter.ACTION_REQUEST_ENABLE;
        		


        		registerReceiver(bluetoohState, new IntentFilter(actionStateChanged));
        		startActivityForResult(new Intent(enableBT),0);
        		
        	}
        	else //bluetooth esta ativado
        	{   
        		
    	        
    	        bluetoothServer.start();
        		listarDispositivos();
        		
        	}	        
        
        }
        //handler.post(bluetoothServer);
   
    }
    
    public void listarDispositivos()
    {
    	pairedDevices = bluetooth.getBondedDevices();
        
        
        if (pairedDevices.size() > 0) 
        {  
	        
	        
	         for (BluetoothDevice device : pairedDevices) 
	        	 lista.add(device.getName());
	        
	         lst.setAdapter(aa);
        }
    }
    
    @Override 
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	if(requestCode == DISCOVERY_REQUEST)
    	{
    		boolean isDiscoveable = resultCode > 0;
    		int discoverableDuration = resultCode;
    	}
    }
    
    
    public synchronized void gerenciaConexao(boolean tomouIniciativa)
    {
    	 Controladora app = (Controladora)getApplication();
    	 try{
    	
			 app.saida = socketBT.getOutputStream();
			 app.entrada = socketBT.getInputStream();
			 if(tomouIniciativa)
			 {
				 int comeco  = 0 ;
				 
				 app.saida.write(comeco);
			 }
		
    	 }
    	 catch(IOException e){}
    	 AlertDialog.Builder ad = new AlertDialog.Builder(TelaDeConexao.this);
		 
    	 
		 ad.setTitle(R.string.str_connectiondone);
		 ad.setMessage(R.string.str_connectiondone);
		 ad.setPositiveButton("OK!", new OnClickListener()
		 {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(TelaDeConexao.this,PosicionamentoDosNavios.class);
				startActivity(intent);
				
		 }
		 
		 });
		 ad.show();
    	 

    	
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		 
		 String dispClicado = (String)arg0.getItemAtPosition(arg2); 
		 
		
		
			 for (BluetoothDevice device : pairedDevices) 
			 {
			     if(device.getName().equals(dispClicado))
			     {
			    	 
			    	 try 
			    	 {
			    		 socketBT = device.createRfcommSocketToServiceRecord(SERIAL_PORT_SERVICE_CLASS_UUID);
			    		 socketBT.connect();
		    		 	 bluetoothServer.interrupt();//Destruo a Thread que busca fazer a conexão
            			 gerenciaConexao(true);
	            			 
	            		
			    	 } 
			    	 catch (IOException e) 
			    	 {
			    		 Toast.makeText(TelaDeConexao.this, R.string.str_genericerror, Toast.LENGTH_LONG).show();
					 }			
			     }
			 }
	}
}
