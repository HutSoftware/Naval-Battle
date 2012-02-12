package br.navalbattle.mockcomunnication;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import br.navalbattle.mainclasses.Controladora;
import br.navalbattle.mainclasses.PosicionamentoDosNavios;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class TCPConnectionClient extends Activity {
	Socket socket = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			socket = new Socket("10.0.2.2", 5000);
			gerenciaConexao(true);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized void gerenciaConexao(boolean tomouIniciativa) {
		Controladora app = (Controladora) getApplication();
		try {

			app.setSaida(socket.getOutputStream());
			app.setEntrada(socket.getInputStream());
			if (tomouIniciativa) {
				int comeco = 0;

				app.getSaida().write(comeco);
			}
			Intent intent = new Intent(TCPConnectionClient.this,
					PosicionamentoDosNavios.class);
			startActivity(intent);

		} catch (IOException e) {
		}
		/*
		 * AlertDialog.Builder ad = new AlertDialog.Builder(
		 * TCPConnectionClient.this);
		 * 
		 * ad.setTitle("Conexaõ feita com sucesso!");
		 * ad.setMessage("Conexão feita com sucesso!");
		 * ad.setPositiveButton("OK!", new OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface arg0, int arg1) {
		 * Intent intent = new Intent(TCPConnectionClient.this,
		 * PosicionamentoDosNavios.class); startActivity(intent);
		 * 
		 * }
		 * 
		 * }); ad.show();
		 */

	}

}
