package numberdle;

import controladores.ControladorVPrincipal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import recursos.LocalizadorRecursos;
import utilidades.Dialogos;

public class Main extends Application {
	ControladorVPrincipal cVentanaPrincipal;
	static Stage escenarioInfo=null;
	
	@Override
	public void start(Stage escenarioPrincipal) {
		try {
			FXMLLoader cargadorVentanaPrincipal=new FXMLLoader(LocalizadorRecursos.class.getResource("/vistas/VPrincipal.fxml"));
			VBox raiz = cargadorVentanaPrincipal.load();
			ControladorVPrincipal cVentanaPrincipal=cargadorVentanaPrincipal.getController();
			cVentanaPrincipal.setEscenarioPrincipal(escenarioPrincipal);
			
			Scene escena = new Scene(raiz);
			escenarioPrincipal.setTitle("Numberdle");
			escenarioPrincipal.setScene(escena);
			escenarioPrincipal.setOnCloseRequest(e -> confirmarSalida(escenarioPrincipal, e));
			escenarioPrincipal.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void confirmarSalida(Stage escenarioPrincipal, WindowEvent e) {
    	if(Dialogos.mostrarDialogoConfirmacion("Salir", "¿Seguro que desea cerrar la aplicación?", escenarioPrincipal)) {
    		escenarioPrincipal.close();
    		if(escenarioInfo!=null) {
    			escenarioInfo.close();
    		}
    	}
    	else {
    		e.consume();
    	}
	}
	
	public static void setEscenarioInfo(Stage escenario) {
		escenarioInfo=escenario;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
