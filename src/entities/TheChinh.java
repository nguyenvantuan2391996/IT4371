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
public class TheChinh {
    private int mathechinh;
    private String matkhau;
    private String hoten;
    private Float sodu;
    private String message;
    
    public TheChinh() {
    }

    public TheChinh(int mathechinh, String matkhau, String hoten, Float sodu, String message) {
        this.mathechinh = mathechinh;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.sodu = sodu;
        this.message = message;
    }

    public int getMathechinh() {
        return mathechinh;
    }

    public void setMathechinh(int mathechinh) {
        this.mathechinh = mathechinh;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public Float getSodu() {
        return sodu;
    }

    public void setSodu(Float sodu) {
        this.sodu = sodu;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
