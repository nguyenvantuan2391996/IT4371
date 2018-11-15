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
public class ThePhu {
    private int mathephu;
    private int mathechinh;
    private String matkhau;
    private String hoten;
    private Float hanmuc;
    private String message;
    
    public ThePhu() {
    }

    public ThePhu(int mathephu, int mathechinh, String matkhau, String hoten, Float hanmuc, String message) {
        this.mathephu = mathephu;
        this.mathechinh = mathechinh;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.hanmuc = hanmuc;
        this.message = message;
    }

    public int getMathephu() {
        return mathephu;
    }

    public void setMathephu(int mathephu) {
        this.mathephu = mathephu;
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

    public Float getHanmuc() {
        return hanmuc;
    }

    public void setHanmuc(Float hanmuc) {
        this.hanmuc = hanmuc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
