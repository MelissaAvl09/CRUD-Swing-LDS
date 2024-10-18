package Modelo;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class PersonaDAO {
    conexion conectar = new conexion(); 
    Connection con; 
    PreparedStatement ps;
    ResultSet rs; 

  
    public List<Persona> listar() {
        List<Persona> datos = new ArrayList<>();
        String sql = "SELECT Id_Empleado, NombresEmpleado, Correo, Telefono FROM Persona.Empleados"; 
        
        try {
            con = conectar.getConnection(); 
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(); 
            
           
            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getInt("Id_Empleado"));
                p.setNombre(rs.getString("NombresEmpleado")); 
                p.setCorreo(rs.getString("Correo")); 
                p.setTelefono(rs.getString("Telefono")); 
                datos.add(p); 
            }
        } catch (SQLException e) {
            System.out.println("Error SQL al listar: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return datos;
    }

    // Método para agregar una nueva persona a la base de datos.
    public int Agregar(Persona p) {
    String sql = "INSERT INTO Persona.Empleados (DUI_Empleado, ISSS_Empleado, NombresEmpleado, ApellidosEmpleado, FechaNacEmpleado, Telefono, Correo, Id_Cargo, Id_Direccion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    try {
        con = conectar.getConnection(); 
        ps = con.prepareStatement(sql); 
        
        // Establecer valores predeterminados para los campos que no se manejan en la vista
        ps.setString(1, "00000000-0"); 
        ps.setInt(2, 123456789); 
        ps.setString(3, p.getNombre()); 
        ps.setString(4, "Apellido Genérico"); 
        ps.setDate(5, java.sql.Date.valueOf("1990-01-01")); 
        ps.setString(6, p.getTelefono()); 
        ps.setString(7, p.getCorreo()); 
        ps.setInt(8, 1); 
        ps.setInt(9, 1); 
        
        ps.executeUpdate(); // Ejecutar la inserción
    } catch (SQLException e) {
        System.out.println("Error SQL al agregar: " + e.getMessage());
        return 0; 
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    return 1;
}

    public int Editar(Persona p) {
    String sql = "UPDATE Persona.Empleados SET NombresEmpleado=?, Correo=?, Telefono=? WHERE Id_Empleado=?";
    
    try {
        con = conectar.getConnection(); 
        ps = con.prepareStatement(sql);
        
       
        ps.setString(1, p.getNombre());   
        ps.setString(2, p.getCorreo());   
        ps.setString(3, p.getTelefono()); 
        ps.setInt(4, p.getId());          
        
        ps.executeUpdate(); 
    } catch (SQLException e) {
        System.out.println("Error SQL al editar: " + e.getMessage());
        return 0; 
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    return 1; 
}

    public void Eliminar(int id) {
    String sql = "DELETE FROM Persona.Empleados WHERE Id_Empleado=?";
    
    try {
        con = conectar.getConnection(); 
        ps = con.prepareStatement(sql); 
        
       
        ps.setInt(1, id); 
        
        ps.executeUpdate(); 
    } catch (SQLException e) {
        System.out.println("Error SQL al eliminar: " + e.getMessage());
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
    
    public List<Persona> buscar(String criterio, String valor) {
    List<Persona> datos = new ArrayList<>();
    String sql = "";

    if (criterio.equals("Id_Empleado")) {
        
        sql = "SELECT Id_Empleado, NombresEmpleado, Correo, Telefono FROM Persona.Empleados WHERE " + criterio + " = ?";
    } else {
       
        sql = "SELECT Id_Empleado, NombresEmpleado, Correo, Telefono FROM Persona.Empleados WHERE " + criterio + " LIKE ?";
    }

    try {
        con = conectar.getConnection();
        ps = con.prepareStatement(sql);
        
     
        if (criterio.equals("Id_Empleado")) {
            ps.setInt(1, Integer.parseInt(valor)); 
        } else {
            ps.setString(1, "%" + valor + "%"); 
        }
        
        rs = ps.executeQuery();
        
        while (rs.next()) {
            Persona p = new Persona();
            p.setId(rs.getInt("Id_Empleado"));
            p.setNombre(rs.getString("NombresEmpleado"));
            p.setCorreo(rs.getString("Correo"));
            p.setTelefono(rs.getString("Telefono"));
            datos.add(p);
        }
    } catch (SQLException e) {
        System.out.println("Error SQL al buscar: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    return datos;
}




}
