package com.restaurant.restaurant.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.restaurant.restaurant.entity.Order;
import com.restaurant.restaurant.entity.OrderDetails;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
@Data
public class ReceiptPrinter {


    @SneakyThrows
    public String printReceipt(Order order) throws FileNotFoundException, DocumentException {
        Document receipt = new Document();
        String fileName = order.getUser().getName()+order.getDate().getTime() + ".pdf";
       // String fileName = order.getId() + ".pdf";
        PdfWriter.getInstance(receipt,new FileOutputStream("src/main/resources/static/receipts/"+fileName));

        receipt.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        receipt.add(new Paragraph("           My Restaurant       \n",font));
        receipt.add(new Paragraph("-------------------------------- \n",font));
        receipt.add(new Paragraph(String.format("%-5s %15s\n", "Date: ", order.getDate()),font));
        receipt.add(new Paragraph(String.format("%-5s %25s\n", "User:",order.getUser().getName() + " "
                + order.getUser().getLastname()),font));
        receipt.add(new Paragraph(String.format("%-15s %5s %10s\n", "Item", "Qty", "Price"),font));
        receipt.add(new Paragraph(String.format("%-15s %5s %10s\n", "----", "---", "-----"),font));

        for (OrderDetails dish : order.getDishes()){
            receipt.add(new Paragraph(String.format("%-15s %5s %10s\n",dish.getDish().getName(),
                    dish.getNum(), dish.getDish().getCost()),font));
        }
        receipt.add(new Paragraph(String.format("%-15s %5s %10s\n", "----", "---", "-----"),font));
        receipt.add(new Paragraph(String.format("%-15s %5s %10s\n", "Total", "", String.valueOf(order.getAmount())),font));
        receipt.close();

        return fileName;
    }
}
