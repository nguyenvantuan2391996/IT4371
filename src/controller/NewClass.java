/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author tuannguyen
 */
public class NewClass {
    public static void main(String[] args) {
        String s = "Mã thẻ         : 123456789\n" +
"----------------------------------------\n" +
"Mã thẻ phụ     : 888888888\n" +
"Tên khách hàng : Nguyen Quynh Anh\n" +
"Hạn mức        : 8000000";
        System.out.println(s.subSequence(85, 94));
        System.out.println(s.contains("Hạn mức"));
    }
}
