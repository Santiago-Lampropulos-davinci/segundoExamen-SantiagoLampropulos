package jdbc;

import java.sql.*;

public class MyJDBC02 {

    public static void main(String[] args) {
        // creo una instancia de la clase Connection afuera del bloque try para usar en el catch
        Connection connection = null;

        try{
            // establezco la connecion con la base de datos jdbc en el puerto 3306 con usuario y contrasenia root
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","root");
            // pongo auto commit en false esto hace que no se comite cada cueri y que se cada vez que lance
            // el comando commit se ejecuta el bloque query
            connection.setAutoCommit(false);

            // hago la sentencia preparada
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO estudiante (dni, nombre, apellido) VALUES (?, ?, ?);");
            // modifico los "?" de la sentencia preparada con mis datos
            preparedStatement.setInt(1,40640217);
            preparedStatement.setString(2,"Santiago");
            preparedStatement.setString(3,"Lampropulos");

            // ejecuto la consulta preparada
            preparedStatement.executeUpdate();

            //traigo los datos de la tabla estudiante
            ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM estudiante");

            // realizo el commit para que se guarden los cambios
            connection.commit();

            //muestro toda la tabla
            while(resultSet.next()){
                System.out.println(resultSet.getString(2)+"\t"+resultSet.getString("nombre")+"\t"+resultSet.getString("apellido"));
            }

        }catch (SQLException exceptionConnection){
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
                //atrapo excepciones
                System.out.println(eFinally);
            }
        }

    }

}
