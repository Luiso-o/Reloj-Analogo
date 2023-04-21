package RelojAnalogo;

	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.Point;
	import java.util.Calendar;
	import java.util.GregorianCalendar;

	public class RelojAnalogico extends JPanel implements Runnable {
	 
		//Ejeculatble
		public static void main(String[] args) {
			
	        JFrame frame = new JFrame("Reloj Analógico");
	        frame.setPreferredSize(new Dimension(220,250)); 
	        RelojAnalogico reloj = new RelojAnalogico();
	        frame.add(reloj);
	        frame.pack();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
	        frame.setResizable(false);
	        frame.setLocationRelativeTo(null);
	        reloj.start();
	    }			
				
		//Constantes  
	    private Thread hilo;
	    private int horas, minutos, segundos;
	    private boolean funcionando = true;
	    
	    //Constructor
	    public RelojAnalogico() {
	        setBackground(Color.white);
	        setDoubleBuffered(true);
	    }
	    
	    //El reloj empieza a funcionar
	    public void start() {
	        if (hilo == null) {
	            hilo = new Thread(this);
	            hilo.start();
	        }
	        funcionando = true;
	    }
	    
	    //El reloj se detiene
	    public void stop() {
	        funcionando = false;
	    }
	    
	    //El reloj seguira fincionando mientras esté en true
	    public void run() {
	        while (funcionando) {
	            actualizarHora();
	            repaint();
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {}
	        }
	    }
	    
	    //Actualizamos con la Hora del ordenador
	    private void actualizarHora() {
	        Calendar calendario = new GregorianCalendar();
	        horas = calendario.get(Calendar.HOUR_OF_DAY);
	        minutos = calendario.get(Calendar.MINUTE);
	        segundos = calendario.get(Calendar.SECOND);
	    }
	    
	    //usamos Graphics para crear las manecillas en diferentes zonas del reloj en determinados momentos
	    private void dibujarManecilla(Graphics g, double angulo, int largo) {
	        Point centro = new Point(getWidth() / 2, getHeight() / 2);
	        int x = (int) (centro.x + Math.sin(angulo) * largo);
	        int y = (int) (centro.y - Math.cos(angulo) * largo);
	        g.drawLine(centro.x, centro.y, x, y);
	    }

	    //creamos el circulo y las horas
	    public void paint(Graphics g) {
	        super.paint(g);

	        // dibujar el círculo del reloj
	        g.setColor(Color.black);
	        g.drawOval(10, 10, 180, 180);

	        // dibujar las marcas de las horas
	        for (int i = 0; i < 12; i++) {
	            double angulo = i * 2 * Math.PI / 12;
	            int x1 = (int) (Math.sin(angulo) * 80 + 100);
	            int y1 = (int) (-Math.cos(angulo) * 80 + 100);
	            int x2 = (int) (Math.sin(angulo) * 70 + 100);
	            int y2 = (int) (-Math.cos(angulo) * 70 + 100);
	            g.drawLine(x1, y1, x2, y2);
	        }

	        // dibujar las manecillas
	        double anguloHoras = (horas + minutos / 60.0) * 2 * Math.PI / 12;
	        dibujarManecilla(g, anguloHoras, 50);
	        double anguloMinutos = (minutos + segundos / 60.0) * 2 * Math.PI / 60;
	        dibujarManecilla(g, anguloMinutos, 70);
	        double anguloSegundos = segundos * 2 * Math.PI / 60;
	        dibujarManecilla(g, anguloSegundos, 80);
	    }

	}


