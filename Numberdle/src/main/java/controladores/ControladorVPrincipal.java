package controladores;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import numberdle.Main;
import recursos.LocalizadorRecursos;
import utilidades.Dialogos;

public class ControladorVPrincipal {
	Stage escenarioPrincipal;
	Stage escenarioInfo=null;
	List<Integer> solucion;
	List<TextField>[] coleccionIntentos;
	Label[] coleccionLabels;
	int intento=0;
	int indiceColeccionIntentos=-1;
	int indiceIntento=-1;
	
	

	public void setEscenarioPrincipal (Stage escenarioPrincipal) {
		this.escenarioPrincipal=escenarioPrincipal;
	}
	
	@FXML
	public void initialize() {
		coleccionLabels=new Label[] {lbIntento1,lbIntento2,lbIntento3,lbIntento4,lbIntento5};
		asignarColeccionIntentos();
		crearSolucion();
		darFormato();
		for(List<TextField> l : coleccionIntentos) {
			for(TextField t : l) {
				t.setOnKeyPressed(e -> comprobarIndice(e));
				t.textProperty().addListener((ob, ov, nv) -> comprobarReglas());
			}
		}
	}
	
	public void asignarColeccionIntentos() {
		List<TextField> intento0=new ArrayList<>();
			intento0.add(tf00);
			intento0.add(tf01);
			intento0.add(tf02);
			intento0.add(tf03);
			intento0.add(tf04);
			intento0.add(tf05);
			intento0.add(tf06);
		List<TextField> intento1=new ArrayList<>();
			intento1.add(tf10);
			intento1.add(tf11);
			intento1.add(tf12);
			intento1.add(tf13);
			intento1.add(tf14);
			intento1.add(tf15);
			intento1.add(tf16);
		List<TextField> intento2=new ArrayList<>();
			intento2.add(tf20);
			intento2.add(tf21);
			intento2.add(tf22);
			intento2.add(tf23);
			intento2.add(tf24);
			intento2.add(tf25);
			intento2.add(tf26);
		List<TextField> intento3=new ArrayList<>();
			intento3.add(tf30);
			intento3.add(tf31);
			intento3.add(tf32);
			intento3.add(tf33);
			intento3.add(tf34);
			intento3.add(tf35);
			intento3.add(tf36);
		List<TextField> intento4=new ArrayList<>();
			intento4.add(tf40);
			intento4.add(tf41);
			intento4.add(tf42);
			intento4.add(tf43);
			intento4.add(tf44);
			intento4.add(tf45);
			intento4.add(tf46);
		coleccionIntentos=new List[] {intento0,intento1,intento2,intento3,intento4};
	}
	
	public void crearSolucion() {
		List<Integer> listaSolucion=new ArrayList<>();
		for(int i=0;i<7;i++) {
			int numero = (int) Math.floor(Math.random() * (9 - 1 + 1) + 1);
			listaSolucion.add(numero);
		}
		solucion=listaSolucion;
	}
	
	public void darFormato() {
		intento=0;
		for(List<TextField> l : coleccionIntentos) {
			for(TextField t : l) {
				t.clear();
				t.setEditable(false);
				t.setStyle("-fx-text-fill: black");
			}
		}
		for(TextField t : coleccionIntentos[intento]) {
			t.setEditable(true);
		}
		for(Label l : coleccionLabels) {
			l.setStyle("<font-weight>: regular");
		}
		coleccionLabels[intento].setStyle("-fx-font-weight: bold");
	}
	
	public void comprobarIndice(KeyEvent e) {
		indiceColeccionIntentos=-1;
		indiceIntento=-1;
		for(int i=0;i<coleccionIntentos.length&&indiceIntento<0;i++) {
			for(TextField t : coleccionIntentos[i]) {
				if(t.equals(e.getSource())) {
					indiceIntento=coleccionIntentos[i].indexOf(t);
					indiceColeccionIntentos=i;
				}
			}
		}
//		if(e.getCode().equals(KeyCode.ENTER)) {
//			acEnviar(e);
//		}
	}
	
	public void comprobarReglas() {
	    if (!coleccionIntentos[indiceColeccionIntentos].get(indiceIntento).getText().matches("[1-9]*")) {
	    	coleccionIntentos[indiceColeccionIntentos].get(indiceIntento).setText(coleccionIntentos[indiceColeccionIntentos].get(indiceIntento).getText().replaceAll("[^1-9]", ""));
	    }
	    else if (!coleccionIntentos[indiceColeccionIntentos].get(indiceIntento).getText().matches("\\d{1}")) {
	    	coleccionIntentos[indiceColeccionIntentos].get(indiceIntento).setText(coleccionIntentos[indiceColeccionIntentos].get(indiceIntento).getText().replaceFirst("[\\d]", ""));
	    }
	}
	
    @FXML
    void acEnviar(ActionEvent event) {
    	List<String> coloreado=new ArrayList<>();
    	List<Integer> copiaSolucion=new ArrayList<>();
    	try {
    		for(Integer i : solucion) {
        		copiaSolucion.add(i);
        	}
        	for(TextField t : coleccionIntentos[intento]) {
        		int indice=coleccionIntentos[intento].indexOf(t);
        		if(t.getText().equals(String.valueOf(copiaSolucion.get(indice)))) {
        			t.setStyle("-fx-text-fill: green");
        			coloreado.add("si");
        			copiaSolucion.add(indice, 0);
        			copiaSolucion.remove(indice+1);
        		}
        		else if(copiaSolucion.get(indice)%Integer.valueOf(t.getText())==0) {
        			t.setStyle("-fx-text-fill: blue");
        			coloreado.add("si");
        		}
        		else if(Integer.valueOf(t.getText())%copiaSolucion.get(indice)==0) {
        			t.setStyle("-fx-text-fill: purple");
        			coloreado.add("si");
        		}
        		else {
        			coloreado.add("no");
        		}
        	}
        	for(int i=0;i<coloreado.size();i++) {
        		if(coloreado.get(i).equals("no")) {
        			if(copiaSolucion.contains(Integer.valueOf(coleccionIntentos[intento].get(i).getText()))) {
        				coleccionIntentos[intento].get(i).setStyle("-fx-text-fill: orange");
        			} else {
        				coleccionIntentos[intento].get(i).setStyle("-fx-text-fill: red");
        			}
        		}
        	}
        	comprobarGanador();
    	} catch (Exception e) {
    		for(TextField t : coleccionIntentos[intento]) {
    			t.clear();
    			t.setStyle("-fx-text-fill: black");
    		}
    	}
    }
    
    public void comprobarGanador() {
    	String stringSolucion="";
    	String stringIntento="";
    	for(TextField t : coleccionIntentos[intento]) {
    		stringIntento=stringIntento+t.getText();
    	}
    	for(Integer i : solucion) {
    		stringSolucion=stringSolucion+String.valueOf(i);
    	}
    	if(stringSolucion.equals(stringIntento)) {
    		Dialogos.mostrarDialogoInformacion("Conseguido", "¡Felicidades, has resuelto Numberdle!");
    		for(List<TextField> l : coleccionIntentos) {
    			for(TextField t : l) {
    				t.setEditable(false);
    			}
    		}
    		btEnviar.setDisable(true);
    	} else {
    		intento++;
    		if(intento>4) {
    			Dialogos.mostrarDialogoAdvertencia("Fallido", "Vaya, parece que no has sido capaz de resolverlo... La cadena correcta era: "+solucion);
    			btEnviar.setDisable(true);
    		} else {
        		for(List<TextField> l : coleccionIntentos) {
        			for(TextField t : l) {
        				t.setEditable(false);
        			}
        		}
        		for(TextField t : coleccionIntentos[intento]) {
    				t.setEditable(true);
    			}
        		for(Label l : coleccionLabels) {
        			l.setStyle("<font-weight>: regular");;
        		}
        		coleccionLabels[intento].setStyle("-fx-font-weight: bold");
    		}
    	}
    }
    
    @FXML
    void acReiniciar(ActionEvent event) {
		crearSolucion();
		darFormato();
		btEnviar.setDisable(false);
    }
    
    @FXML
    void acSalir(ActionEvent event) {
    	if(Dialogos.mostrarDialogoConfirmacion("Salir", "¿Seguro que desea cerrar la aplicación?", escenarioPrincipal)) {
    		escenarioPrincipal.close();
    		if(escenarioInfo!=null) {
    			escenarioInfo.close();
    		}
    	}
    	else {
    		event.consume();
    	}
    }
    
    @FXML
    void acInformacion(ActionEvent event) {
		try {
			escenarioInfo=new Stage();
			FXMLLoader cargadorVentanaInfo=new FXMLLoader(LocalizadorRecursos.class.getResource("/vistas/VInfo.fxml"));
			VBox raiz = cargadorVentanaInfo.load();
			
			Scene escena = new Scene(raiz);
			escenarioInfo.setTitle("Información");
			escenarioInfo.setScene(escena);
			escenarioInfo.show();
			Main.setEscenarioInfo(escenarioInfo);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private Label lbIntento1;

    @FXML
    private Label lbIntento2;

    @FXML
    private Label lbIntento3;

    @FXML
    private Label lbIntento5;

    @FXML
    private Label lbIntento4;

    @FXML
    private Button btEnviar;
    
	@FXML
    private TextField tf00;

    @FXML
    private TextField tf01;

    @FXML
    private TextField tf02;

    @FXML
    private TextField tf03;

    @FXML
    private TextField tf04;

    @FXML
    private TextField tf05;

    @FXML
    private TextField tf06;

    @FXML
    private TextField tf10;

    @FXML
    private TextField tf11;

    @FXML
    private TextField tf12;

    @FXML
    private TextField tf13;

    @FXML
    private TextField tf14;

    @FXML
    private TextField tf15;

    @FXML
    private TextField tf16;

    @FXML
    private TextField tf20;

    @FXML
    private TextField tf21;

    @FXML
    private TextField tf22;

    @FXML
    private TextField tf23;

    @FXML
    private TextField tf24;

    @FXML
    private TextField tf25;

    @FXML
    private TextField tf26;

    @FXML
    private TextField tf30;

    @FXML
    private TextField tf31;

    @FXML
    private TextField tf32;

    @FXML
    private TextField tf33;

    @FXML
    private TextField tf34;

    @FXML
    private TextField tf35;

    @FXML
    private TextField tf36;

    @FXML
    private TextField tf40;

    @FXML
    private TextField tf41;

    @FXML
    private TextField tf42;

    @FXML
    private TextField tf43;

    @FXML
    private TextField tf44;

    @FXML
    private TextField tf45;

    @FXML
    private TextField tf46;




    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

