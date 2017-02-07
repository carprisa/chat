package carlos.sockets;

import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	public static void main (String[] args){
		
		MarcoServidor mimarco = new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class MarcoServidor extends JFrame implements Runnable{
	
	private JTextArea areatexto;
	
	public MarcoServidor(){
		
		setBounds(1200,300,200,350);
		
		JPanel milamina = new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto = new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		Thread mihilo = new Thread(this);   // Se abre un hilo cada vez que se se ejecuta el constructor
		
		mihilo.start();   // se inicia el hilo
		
	}

	@Override
	public void run() {    // viene de la implementación de Runable (para generar hilos)
		// TODO Auto-generated method stub
		
		// System.out.print("Estoy a la escucha");   // es lo que se ejecuta
		
		try {
			ServerSocket servidor = new ServerSocket(9999);    // Se pone a la escucha en el 9999
			
			while(true){
			
			Socket misocket = servidor.accept();   // acepta la conexión de tipo socket
			
			DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());   // va a haber un flujo de datos que usa como medio de transporte misocket.getIn...
			
			String mensaje_texto = flujo_entrada.readUTF();
			
			areatexto.append("\n" + mensaje_texto);
			
			misocket.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
