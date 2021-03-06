package carlos.sockets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;




class MarcoCliente extends JFrame{  // es el marco de la ventana
	
	public MarcoCliente(){
		
		setBounds(600,300,400,600);  // establece los limites
		
		LaminaMarcoCliente milamina = new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
				
	}
}

/*
 * La clase que crea el panel
 */

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	private JTextField campo1, nick, ip;
	private JTextArea campochat;
	private JButton miboton;
	
	public LaminaMarcoCliente(){
		
		nick = new JTextField(5);
		add(nick);
		
		JLabel texto = new JLabel("-CHAT-");
		
		ip = new JTextField(8);
		add(ip);
		

		
		add(texto); //añade a JPanel
		
		campochat = new JTextArea(12, 20);  //los parámetros son las coordenadas
		
		add(campochat);
		
		campo1 = new JTextField(20);
		
		add(campo1);
		
		miboton = new JButton("Enviar");
		
		EnviaTexto mievento = new EnviaTexto(); // se crea el evento
		
		miboton.addActionListener(mievento);	// se ejecuta la acción
		
		add(miboton);
		
		Thread mihilo = new Thread(this);  // para que constantemente esté escuchando como servidor (ServerSocket)
		
		mihilo.start();
	}
	
	private class EnviaTexto implements ActionListener{   // se crea un evento que reacciones a la pulsación de un botón

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//System.out.println(campo1.getText());  Para que al pulsar en el botón aparezcan los datos del campo de texto campo1
			
			campochat.append(campo1.getText());  //para que aparezca en el JTextArea del cliente sus propias frases
			
			try {
				Socket misocket = new Socket("192.168.1.13", 9999);
				
				PaqueteEnvio datos = new PaqueteEnvio();
				
				// damos  valores a los atributos del objeto de clase PaqueteEnvio
				datos.setNick(nick.getText());
				datos.setIp(ip.getText());
				datos.setMensaje(campo1.getText());
				
				ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());   // con esto se envían paquetes, en vez de datos, como en el DataOutputStream
				
				paquete_datos.writeObject(datos);
				
				misocket.close();
				
				/*DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());
				
				flujo_salida.writeUTF(campo1.getText());  // escribe en el flujo lo que hay en el campo1
				
				flujo_salida.close();  // se cierra el flujo de datos
				
				*/
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
			
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try{
			
			ServerSocket servidor_cliente = new ServerSocket(9090);
			
			Socket cliente;
			
			PaqueteEnvio paquete_recibido;
			
			while(true){
				
				cliente = servidor_cliente.accept();
				
				ObjectInputStream flujo_entrada = new ObjectInputStream(cliente.getInputStream());
				
				paquete_recibido = (PaqueteEnvio) flujo_entrada.readObject();
				
				campochat.append("\n" + paquete_recibido.getNick() + ": " + paquete_recibido.getMensaje());
			}
			
			
		}catch(Exception e){
			
			System.out.println(e.getMessage());
		}
		
		
		
	}

}


class PaqueteEnvio implements Serializable {  // todo objeto que se envía por la red ha de ser serializable
	
	private String nick, ip, mensaje;
	
	public PaqueteEnvio(){}
	
	/* No es necesario este contructor
	 public PaqueteEnvio (String nick, String ip, String mensaje){
	 
		this.nick = nick;
		this.ip = ip;
		this.mensaje = mensaje;
	}
	*/

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	

}


public class Cliente {

	public static void main(String[] args) {
		
		MarcoCliente mimarco = new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // para que salga al cerrar
		
	}
	
}