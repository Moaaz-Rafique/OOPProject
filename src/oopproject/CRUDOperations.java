/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopproject;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static oopproject.MainForm.currentUser;
import static oopproject.MainForm.userData;

/**
 *
 * @author s
 */
public class CRUDOperations {

    ConnectionToDB con = new ConnectionToDB();
    Connection con_obj = con.EstablishConnection();
    Statement stmt = null; // insert update delete
    PreparedStatement pstmt = null;//select
    ResultSet res = null;
    String name, pwd;

    public ArrayList<MarriageHall> fetchNarriageHall() {
        ArrayList<MarriageHall> m = new ArrayList<>();
        String sql = "select * from Hall";

        try {
            pstmt = con_obj.prepareStatement(sql);
            res = pstmt.executeQuery();

            while (res.next()) {
                m.add(new MarriageHall(res.getInt("ID"), res.getString("Name"), res.getString("Price"), res.getInt("Capacity"),
                        res.getString("Location"), res.getString("Contact"), new File(res.getString("Image")), res.getInt("Seller")));
                //m.get(m.size()-1).setSeller(getUser(res.getInt("Seller")));
                //getUser(res.getInt("Seller")).getMyMarriageHalls().add(m.get(m.size()-1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            System.out.println("Error in fetch MarriageHall");
        }
        return m;
    }

    public boolean addMarriageHall(MarriageHall m) {
        boolean b = false;
        //sql Syntax of inserting data
        //insert into Table_Name (col1, col2, col3,... col_n) values (var1, var2,var3,..... , var_n)
        String sql = "insert into Hall(Name,Price,Capacity,Location,Contact, Image, Seller) values ('"
                + m.getName() + "','"
                + m.getPrice() + "','"
                + m.getCapacityPeople() + "','"
                + m.getLocation() + "','"
                + m.getContact() + "','"
                + m.getImgP().getAbsolutePath() + "','"
                + currentUser.getId() + "')";
        try {
            stmt = con_obj.createStatement();// to convert above string into compatible sql/database  query

            int res = stmt.executeUpdate(sql);// after excecuting above query the number of record effects is returned so if not 0 means rec is added

            if (res > 0) {
                b = true;
            } else {
                b = false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex); // catching exception in case of expcetion
            System.out.println(ex);
        }
        return b;
    }

    public ArrayList<User> fetchUser() {
        ArrayList<User> m = new ArrayList<>();
        String sql = "select * from User";

        try {
            pstmt = con_obj.prepareStatement(sql);
            res = pstmt.executeQuery();

            while (res.next()) {
                m.add(new User(res.getInt("ID"), res.getString("Name"), res.getString("Password"), res.getString("Contact"), new File(res.getString("Image"))));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            System.out.println("Error in fetch user");
        }
        return m;
    }

    public boolean addUser(User u) {
        u.setId(userData.size());
        boolean b = false;
        //sql Syntax of inserting data
        //insert into Table_Name (col1, col2, col3,... col_n) values (var1, var2,var3,..... , var_n)
        String sql = "insert into User(Name,Password,Contact, Image) values ('"
                + u.getName() + "','"
                + u.getPassword() + "','"
                + u.getContact() + "','"
                + u.getImgP().getAbsolutePath() + "')";
        try {
            stmt = con_obj.createStatement();// to convert above string into compatible sql/database  query

            int res = stmt.executeUpdate(sql);// after excecuting above query the number of record effects is returned so if not 0 means rec is added
            if (res > 0) {
                b = true;
            } else {
                b = false;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex); // catching exception in case of expcetion
        }
        return b;
    }

    public ArrayList<TimeTable> fetchTimeTable() {
        ArrayList<TimeTable> t = new ArrayList<>();
        String sql = "select * from TimeTable";

        try {
            pstmt = con_obj.prepareStatement(sql);
            res = pstmt.executeQuery();

            while (res.next()) {
                t.add(new TimeTable(res.getInt("ID"), res.getInt("Hall"), res.getInt("Buyer"), res.getString("Date")));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            System.out.println(ex);
            System.out.println("Error in fetching timetable");
        }
        return t;
    }

    public boolean addbooking(User currentUser, MarriageHall m, String date) {
        boolean b = false;
        String sql = "insert into TimeTable(Hall, Buyer, Date) values ('"
                + currentUser.getId() + "','"
                + m.getId() + "','"
                + date + "')";
        try {
            stmt = con_obj.createStatement();// to convert above string into compatible sql/database  query
            int res = stmt.executeUpdate(sql);// after excecuting above query the number of record effects is returned so if not 0 means rec is added           
            if (res > 0) {
                b = true;
            } else {
                b = false;
            }
            System.out.println("aaa");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex); // catching exception in case of expcetion
        }
        return b;
    }

    public boolean updateUser(User u) {
        boolean b = false;
        String sql = "Update User set (Name, Password, Contact, Image)=('"
                + u.getName() + "','" + 
                u.getPassword()+ "','"  +
                u.getContact()+ "','" +
                u.getImgP().getAbsolutePath() +
                "') where ID='" + currentUser.getId() + "'";
        try {
            stmt = con_obj.createStatement();
            int rs = stmt.executeUpdate(sql);
            if (rs > 0) {
                b = true;
            } else {
                b = false;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return b;
    }
}


