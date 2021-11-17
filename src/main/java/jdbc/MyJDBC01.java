package jdbc;

import java.sql.*;

public class MyJDBC01 {

    public static void main(String[] args) {
        // creo una instancia de la clase Connection afuera del bloque try para usar en el catch
        Connection connection = null;

        try{
            // establezco la connecion con la base de datos jdbc en el puerto 3306 con usuario y contrasenia root
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","root");
            Statement statement = connection.createStatement();

            //Ejecuto la cueri para que me traiga la tabla estudiantes
            ResultSet resultSet = statement.executeQuery("SELECT * FROM estudiante");

            //recorro cada fila de la tabla y las imprimo con sout
            while(resultSet.next()){
                System.out.println(resultSet.getString(2)+"\t"+resultSet.getString("nombre")+"\t"+resultSet.getString("apellido"));
            }


        }catch (SQLException exceptionConnection){
            //si surge un error lo atrapa
            System.out.println(exceptionConnection);
            try{
                // si se creo la base de datos revierte el commit que se hizo
                if(connection!=null){
                    connection.rollback();
                }
            }catch (SQLException exceptionCarth){
                //atrapo excepciones
                System.out.println(exceptionCarth);
            }


        }finally {
            // el bloque finally se ejecute sin inportar si viene de un catch o del try
            try{
                //si se crea una coneccion con base de datos la cierro
                if(connection!= null){
                    connection.close();
                }
            }catch (SQLException eFinally){
                //atrapo excpeciones
                System.out.println(eFinally);
            }
        }

    }

}
