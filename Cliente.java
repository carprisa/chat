package carlos.sockets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;




class MarcoCliente extends JFrame{  // es el marco de la ventana
	
	public MarcoCliente(){
		
		setBounds(600,300,400,100);  // establece los limites
		
		LaminaMarcoCliente milamina = new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
				
	}
}

class LaminaMarcoCliente extends JPanel{
	
	private JTextField campo1;
	private JButton miboton;
	
	public LaminaMarcoCliente(){
		
		JLabel texto = new JLabel("CLIENTE");
		
		add(texto); //añade a JPanel
		
		campo1 = new JTextField(20);
		
		add(campo1);
		
		miboton = new JButton("Enviar");
		
		EnviaTexto mievento = new EnviaTexto(); // se crea el evento
		
		miboton.addActionListener(mievento);	// se ejecuta la acción
		
		add(miboton);
	}
	
	private class EnviaTexto implements ActionListener{   // se crea un evento que reacciones a la pulsación de un botón

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//System.out.println(campo1.getText());  Para que al pulsar en el botón aparezcan los datos del campo de texto campo1
			
			try {
				Socket misocket = new Socket("192.168.1.13", 9999);
				
				DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());
				
				flujo_salida.writeUTF(campo1.getText());  // escribe en el flujo lo que hay en el campo1
				
				flujo_salida.close();  // se cierra el flujo de datos
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
			
			
		}
		
	}
}

public class Cliente {

	public static void main(String[] args) {
		
		MarcoCliente mimarco = new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // para que salga al cerrar
		
	}
	
}
