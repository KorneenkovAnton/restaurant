package com.restaurant.restaurant.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.restaurant.restaurant.entity.Order;
import com.restaurant.restaurant.entity.OrderDetails;
import com.restaurant.restaurant.util.aws.AWSUtil;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
public class ReceiptPrinter {

    @Autowired
    private AWSUtil awsUtil;

    @Value("${aws.s3.bucket.receipts}")
    private String AWS_BUCKET_RECEIPTS;

    @SneakyThrows
    public String printReceipt(Order order) throws DocumentException {
        Document receipt = new Document();
        String fileName = order.getUser().getName()+order.getDate().getTime() + ".pdf";
//        PdfWriter.getInstance(receipt,new FileOutputStream("src/main/resources/static/receipts/"+fileName));
        PdfWriter.getInstance(receipt,new FileOutputStream(fileName));

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

        File file = new File(fileName);
        System.out.println(AWS_BUCKET_RECEIPTS);
        awsUtil.uploadToAws(file,AWS_BUCKET_RECEIPTS);
        file.delete();

        return fileName;
    }
}
