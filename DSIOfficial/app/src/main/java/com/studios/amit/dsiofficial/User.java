package com.studios.amit.dsiofficial;

/**
 * Created by Amit on 12-08-2017.
 */

public class User {

    private static String userName;
    private static String password;
    private static String USN;
    private static String Email;
    private static String phoneNum;
    private static Boolean isLoggedin = false;
    private static Boolean isAdmin = false;
    private static String year;
    private static String classChosen;

    public static String getClassChosen() {
        return classChosen;
    }

    public static void setClassChosen(String classChosen) {
        User.classChosen = classChosen;
    }

    public static String getYear() {
        return year;
    }

    public static void setYear(String year) {
        User.year = year;
    }

    public static Boolean getIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(Boolean isAdmin) {
        User.isAdmin = isAdmin;
    }

    public static Boolean getIsLoggedin() {
        return isLoggedin;
    }

    public static void setIsLoggedin(Boolean isLoggedin) {
        User.isLoggedin = isLoggedin;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String getUSN() {
        return USN;
    }

    public static void setUSN(String USN) {
        User.USN = USN;
    }

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String email) {
        Email = email;
    }

    public static String getPhoneNum() {
        return phoneNum;
    }

    public static void setPhoneNum(String phoneNum) {
        User.phoneNum = phoneNum;
    }

    public static void removeAllCredentials(){

        Email = "";
        userName = "";
        phoneNum = "";
        USN = "";
        password = "";
    }
}
