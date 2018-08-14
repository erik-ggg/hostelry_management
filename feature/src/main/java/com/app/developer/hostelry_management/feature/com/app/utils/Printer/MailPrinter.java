package com.app.developer.hostelry_management.feature.com.app.utils.Printer;

import android.hardware.camera2.TotalCaptureResult;

import com.app.developer.hostelry_management.feature.com.app.utils.DataClasses.ProductQuantity;

import java.util.List;

public class MailPrinter {

    private final static String title = "Alsedi";
    private final static int DATE_LENGTH = 10;
    private final static int TOTAL_HYPHEN_AMOUNT = 45;
    private final static int FINAL_HYPHEN_AMOUNT = 31;

    private StringBuffer text;
    private String date;
    private List<ProductQuantity> products;

    public MailPrinter(String date, List<ProductQuantity> products) {
        this.date = date;
        this.products = products;
        text = new StringBuffer();
    }

    public StringBuffer print() {
        // title
        addLimitRow();
        addBlankRow();
        addTitle();
        addBlankRow();
        addLimitRow();
        // subject
        addBlankRow();
        addSubject();
        addBlankRow();
        addLimitRow();
        // products
        addBlankRow();
        addProducts();
        addBlankRow();
        // close
        addLimitRow();
        return text;
    }

    private void addProducts() {
        for (ProductQuantity product : products) {
//            text.append("|  " + product.getProduct().getName() + "\t\t\t" + product.getQuantity() + "\t   |\n");
            // dos guiones menos por principio y fin = 43
            // menos 3 y 9 del principio y fin = 31
            text.append("|   ");
            text.append(product.getProduct().getName());
            addSpaces(FINAL_HYPHEN_AMOUNT -
                    (product.getProduct().getName().length() + String.valueOf(product.getQuantity()).length()));
            text.append(product.getQuantity());
            text.append("         |\n");
        }
    }

    private void addSpaces(int size) {
        for (int i = 0; i < size; i++) {
            text.append(" ");
        }
    }

    private void addLimitRow() {
        text.append("---------------------------------------------\n");
    }

    private void addBlankRow() {
        text.append("|                                           |\n");
    }

    private void addTitle() {
        text.append("|");
        int numberSpaces = (TOTAL_HYPHEN_AMOUNT - 2 - title.length());
        int spaces = (TOTAL_HYPHEN_AMOUNT - 2 - title.length()) / 2;
        addSpaces(spaces);
        text.append(title);
        addSpaces(spaces);
        if (numberSpaces % 2 == 1) {
            addSpaces(1);
        }
        text.append("|\n");
    }

    private void addSubject() {
//        text.append("|  Productos\t\tFecha: " + date + " \t\t|\n");
        text.append("|");
        int numberSpaces = (TOTAL_HYPHEN_AMOUNT - 2 - 6 - 7 - DATE_LENGTH);
        int spaces = (TOTAL_HYPHEN_AMOUNT - 2 - 6 - 7 - DATE_LENGTH)/3;
        addSpaces(spaces - 2);
        text.append("Pedido");
        addSpaces(spaces + 8);
        text.append("Fecha: " + date);
        addSpaces(spaces - 4);
        if (numberSpaces % 3 == 1) {
            addSpaces(1);
        }
        text.append("|\n");
    }


}
