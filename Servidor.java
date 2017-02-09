package carlos.sockets;

import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
			
			String nick, ip, mensaje;
			
			PaqueteEnvio paquete_recibido;
			
			while(true){
			
			Socket misocket = servidor.accept();   // acepta toda conexión de tipo socket que llega al servidor
			
			ObjectInputStream paquete_datos = new ObjectInputStream(misocket.getInputStream());
			
			paquete_recibido = (PaqueteEnvio) paquete_datos.readObject();  //se hace un casting para que paquete_datos sea de Clase PaqueteEnvio
			
			nick = paquete_recibido.getNick();
			
			ip = paquete_recibido.getIp();
			
			mensaje = paquete_recibido.getMensaje();
			
			/*DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());   // va a haber un flujo de datos que usa como medio de transporte misocket.getIn...
			
			String mensaje_texto = flujo_entrada.readUTF();
			
			areatexto.append("\n" + mensaje_texto);*/
			
			areatexto.append("\n" + nick + ": " + mensaje + " para " + ip);
			
			/*
			 * Creamos un cliente para que el servidor reenvíe un mensaje al cliente al que va dirigido
			 */
			
			Socket enviaDestinatario = new Socket(ip, 9090);
			
			ObjectOutputStream paquete_reenvio= new ObjectOutputStream(enviaDestinatario.getOutputStream());
			
			paquete_reenvio.writeObject(paquete_recibido);
			
			paquete_reenvio.close();
			
			enviaDestinatario.close();
			
			misocket.close();
			}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
