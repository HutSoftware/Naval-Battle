package br.navalbattle.mockcomunnication;
/*
 * The classes from this packet it's oly for tests
 * Soon, more instructions about how to use it
 * @author: Luciano P. Sabença(lucianosabenca@gmail.com)
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

import br.navalbattle.resources.*;
import br.navalbattle.mainclasses.Controladora;
import br.navalbattle.mainclasses.PosicionamentoDosNavios;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TCPComunicationServer extends Activity {
	
	private static int DISCOVERY_REQUEST = 1;
	private BluetoothAdapter bluetooth;
	private ListView lst;
	private Button btnProcurar;
	private Set<BluetoothDevice> pairedDevices;
	Thread bluetoothServer;
	Handler handler = new Handler();

	Socket socketBT = null;

	@Override
	protected void onResume() {
		if (((Controladora) this.getApplication()).isAcabouJogo()) {
			((Controladora) this.getApplication()).finalizarJogo();
			this.finish();

			((Controladora) this.getApplication()).setAcabouJogo( false);
		}

		super.onRestart();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		try {
			ServerSocket server = new ServerSocket(6000);

			Socket socket = server.accept();

			synchronized (this) {
				if (socket != null)
					socketBT = socket;
			}
			
			System.out.println("ALO MUNDO ");
			server.close();
			gerenciaConexao(false);
			
			

		} catch (Exception e) {
			Toast.makeText(TCPComunicationServer.this,
					"Ocorreu um erro. Por favor, reinicie o app!",
					Toast.LENGTH_LONG).show();
		}

	}

	public synchronized void gerenciaConexao(boolean tomouIniciativa) {
		Controladora app = (Controladora) getApplication();
		try {

			app.setSaida(socketBT.getOutputStream());
			app.setEntrada(socketBT.getInputStream());
			if (tomouIniciativa) {
				int comeco = 0;

				app.getSaida().write(comeco);
			}
			
			Intent intent = new Intent(TCPComunicationServer.this,
					PosicionamentoDosNavios.class);
			startActivity(intent);


		} catch (IOException e) {
			System.out.print("Eerroe");
		}
		/*AlertDialog.Builder ad = new AlertDialog.Builder(
				TCPComunicationServer.this);

		ad.setTitle("Conexaõ feita com sucesso!");
		ad.setMessage("Conexão feita com sucesso!");
		ad.setPositiveButton("OK!", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(TCPComunicationServer.this,
						PosicionamentoDosNavios.class);
				startActivity(intent);

			}

		});
		ad.show();*/

	}

}