/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author tuannguyen
 */
public class Data implements Serializable {

    private String matkhau;
    private int mathephu1;
    private int mathephu2;
    private int mathechinh;
    private String hoten;
    private String hoten1;
    private String hoten2;
    private int sodu;
    private int hanmuc;
    private String message;
    private int sotienrut;
    private int sotiennap;
    private String matkhaumoi;
    private String location;
    private String primary;

    public Data() {
    }

    public Data(String matkhau, int mathephu1, int mathephu2, int mathechinh, String hoten, String hoten1, String hoten2, int sodu, int hanmuc, String message, int sotienrut, int sotiennap, String matkhaumoi, String location, String primary) {
        this.matkhau = matkhau;
        this.mathephu1 = mathephu1;
        this.mathephu2 = mathephu2;
        this.mathechinh = mathechinh;
        this.hoten = hoten;
        this.hoten1 = hoten1;
        this.hoten2 = hoten2;
        this.sodu = sodu;
        this.hanmuc = hanmuc;
        this.message = message;
        this.sotienrut = sotienrut;
        this.sotiennap = sotiennap;
        this.matkhaumoi = matkhaumoi;
        this.location = location;
        this.primary = primary;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getMathephu1() {
        return mathephu1;
    }

    public void setMathephu1(int mathephu1) {
        this.mathephu1 = mathephu1;
    }

    public int getMathephu2() {
        return mathephu2;
    }

    public void setMathephu2(int mathephu2) {
        this.mathephu2 = mathephu2;
    }

    public int getMathechinh() {
        return mathechinh;
    }

    public void setMathechinh(int mathechinh) {
        this.mathechinh = mathechinh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHoten1() {
        return hoten1;
    }

    public void setHoten1(String hoten1) {
        this.hoten1 = hoten1;
    }

    public String getHoten2() {
        return hoten2;
    }

    public void setHoten2(String hoten2) {
        this.hoten2 = hoten2;
    }

    public int getSodu() {
        return sodu;
    }

    public void setSodu(int sodu) {
        this.sodu = sodu;
    }

    public int getHanmuc() {
        return hanmuc;
    }

    public void setHanmuc(int hanmuc) {
        this.hanmuc = hanmuc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSotienrut() {
        return sotienrut;
    }

    public void setSotienrut(int sotienrut) {
        this.sotienrut = sotienrut;
    }

    public int getSotiennap() {
        return sotiennap;
    }

    public void setSotiennap(int sotiennap) {
        this.sotiennap = sotiennap;
    }

    public String getMatkhaumoi() {
        return matkhaumoi;
    }

    public void setMatkhaumoi(String matkhaumoi) {
        this.matkhaumoi = matkhaumoi;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }
}
