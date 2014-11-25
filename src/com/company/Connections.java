package com.company;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Connections extends  JFrame{

    public Connection getConnectiondataBase() throws SQLException {
        Connection dbConnection = null;
        String username = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/hospital";
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            dbConnection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }





    public JTable SelectTest()
    {
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();
        String sql = "SELECT * FROM doctors";

        try (Connection dbConnection = getConnectiondataBase(); // Java SE 7 has try-with-resources
             Statement stmt = dbConnection.createStatement(); //cРѕР·РґР°РЅРёРµ РІС‹СЂР°Р¶РµРЅРёСЏ
             ResultSet rs = stmt.executeQuery(sql)) // РњРµС‚РѕРґ executeQuery РЅРµРѕР±С…РѕРґРёРј РґР»СЏ Р·Р°РїСЂРѕСЃРѕРІ, СЂРµР·СѓР»СЊС‚Р°С‚РѕРј РєРѕС‚РѕСЂС‹С… СЏРІР»СЏРµС‚СЃСЏ РѕРґРёРЅ РµРґРёРЅСЃС‚РІРµРЅРЅС‹Р№ РЅР°Р±РѕСЂ Р·РЅР°С‡РµРЅРёР№, С‚Р°РєРёС… РєР°Рє Р·Р°РїСЂРѕСЃРѕРІ SELECT.
        {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            //  Get column names
            for (int i = 1; i <= columns; i++)
            {
                columnNames.add( md.getColumnName(i) );
            }

            //  Get row data
            while (rs.next())
            {
                ArrayList row = new ArrayList(columns);
                for (int i = 1; i <= columns; i++)
                {
                    row.add( rs.getObject(i) );
                }
                data.add( row );
            }
        }
        catch (SQLException e)
        {
            System.out.println( e.getMessage() );
        }

        Vector columnNamesVector = new Vector();
        Vector dataVector = new Vector();

        for (int i = 0; i < data.size(); i++)
        {
            ArrayList subArray = (ArrayList)data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++)
            {
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }

        for (int i = 0; i < columnNames.size(); i++ )
            columnNamesVector.add(columnNames.get(i));

        //  Create table with database data
        JTable table = new JTable(dataVector, columnNamesVector)
        {
            public Class getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }
                return Object.class;
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        return table;
    }


    public  void addGoods(String surname, String name, String specislization) throws  SQLException {
        String insertTableSQL = "INSERT INTO doctors"
                + "(id_doctor, surname, name, specialization)  VALUES"
                + " (?, ?, ?, ?)";
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnection = getConnectiondataBase();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setNull(1, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3,name);
            preparedStatement.setString(4,specislization );
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }
    public void deleteGoods (int row) throws SQLException{
        Connection dbConnection = null;
        PreparedStatement statement = null;

        String sql = "delete from doctors WHERE  id_doctor =?";
        try {
            dbConnection = getConnectiondataBase();
            statement = dbConnection.prepareStatement(sql);
            statement.setInt(1,row);
            statement.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }
}
