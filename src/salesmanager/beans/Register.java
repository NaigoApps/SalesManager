/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans;

/**
 *
 * @author Lorenzo
 */
public class Register {
    private int code;
    private int month;
    private int year;
    private RegisterMovement[] movements;

    public Register() {
        this.code = -1;
    }

    
    
    public Register(int month, int year, RegisterMovement[] movements) {
        this.code = -1;
        this.month = month;
        this.year = year;
        this.movements = movements;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public RegisterMovement[] getMovements() {
        return movements;
    }

    public void setMovements(RegisterMovement[] movements) {
        this.movements = movements;
    }

    @Override
    public String toString() {
        return (month+1) + "/" + year;
    }
    
    
    
    
}
