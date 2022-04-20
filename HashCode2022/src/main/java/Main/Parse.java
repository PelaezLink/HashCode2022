/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Objects.Persona;
import Objects.Tarea;
import Objects.Skill;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

public class Parse {
  public static void main(String[] args) throws IOException {
    try {
      File myObj = new File("C:\\Users\\luisp\\Desktop\\HashCode2022\\src\\main\\java\\Main\\e_exceptional_skills.in.txt");
      Scanner myReader = new Scanner(myObj);
      List<Persona> personas = new ArrayList<>();
      List<Tarea> tareas = new ArrayList<>();
      String data = myReader.nextLine();
      String[] starts = data.split(" ");
      int contributors = Integer.valueOf(starts[0]);
      int projects = Integer.valueOf(starts[1]);
      while (myReader.hasNextLine()) {
        data = myReader.nextLine();
        if (contributors > 0) {
          starts = data.split(" ");
          Persona persona = new Persona(starts[0]);
          personas.add(persona);
          int skills = Integer.valueOf(starts[1]);
          for (int i = 0; i < skills; i++) {
            data = myReader.nextLine();
            starts = data.split(" ");
            persona.addSkill(starts[0], Integer.valueOf(starts[1]));
          }
          contributors--;
        } else if (projects > 0) {
          starts = data.split(" ");
          Tarea tarea = new Tarea(starts[0], Integer.valueOf(starts[1]), Integer.valueOf(starts[2]), Integer.valueOf(starts[3]));
          tareas.add(tarea);
          int skills = Integer.valueOf(starts[4]);
          for (int i = 0; i < skills; i++) {
            data = myReader.nextLine();
            starts = data.split(" ");
            tarea.addSkill(starts[0], Integer.valueOf(starts[1]));
          }
          projects--;
        }
      }
      myReader.close();
      
      
      FileWriter myWriter = new FileWriter("C:\\Users\\luisp\\Desktop\\HashCode2022\\src\\main\\java\\Main\\result.txt");
      
      
      
      
      int tiempo = 0;
      List<Persona> personasRep = new ArrayList<Persona>(personas);
      List<Tarea> tareasPosibles = new ArrayList<Tarea>();
      List<Tarea> resultado = new ArrayList<Tarea>();
      do {
          tareasPosibles = new ArrayList<Tarea>();
          System.out.print("|");
          for (Tarea tarea : tareas) {
              if (!tarea.posible(personas).isEmpty() && tarea.userRep(personasRep)) {
                  tareasPosibles.add(tarea);
              }
          }
          int max = 0;
          Tarea laMejor = tareasPosibles.get(0);
          for (Tarea tarea : tareasPosibles) {
              int puntuacion = tarea.puntuacion(tiempo);
              if (puntuacion > max) {
                  laMejor = tarea;
                  max = puntuacion;
              }
          }
          resultado.add(laMejor);
          personasRep = laMejor.personasPosibles;
          tiempo += laMejor.duracion;
          tareas.remove(laMejor);
          laMejor.ajustarSkill();
          
          
          /*System.out.print(laMejor.nombre);
          System.out.print("\n");
          for(Persona p : laMejor.personasPosibles) {
              System.out.print(p.nombre);
              System.out.print(" ");
          }
          System.out.print("\n");*/
      } while(tareasPosibles.size() > 1);
      
      
      
      myWriter.write(""+resultado.size());
      myWriter.write("\n");
      for (Tarea t : resultado) {
          myWriter.write(t.nombre);
          myWriter.write("\n");
          for(Persona p : t.personasPosibles) {
              myWriter.write(p.nombre);
              myWriter.write(" ");
          }
          myWriter.write("\n");
      }
      
      
      myWriter.close();
      
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
