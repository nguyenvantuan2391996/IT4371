/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author tuannguyen
 */
public class Admin {
    private String matkhau;
    private String message;
    
    public Admin() {
    }

    public Admin(String matkhau, String message) {
        this.matkhau = matkhau;
        this.message = message;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
