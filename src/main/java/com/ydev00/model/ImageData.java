package com.ydev00.model;

import javax.sql.rowset.serial.SerialBlob;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class ImageData {
    private List<Integer> binaryData;
    private boolean isClosed;

    public ImageData() {
    }

    public ImageData(List<Integer> binaryData, boolean isClosed) {
        this.binaryData = binaryData;
        this.isClosed = isClosed;
    }

    public List<Integer> getBinaryData() {
        return binaryData;
    }

    public void setBinaryData(List<Integer> binaryData) {
        this.binaryData = binaryData;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String toString() {
        return "ImageData {" +
                "binaryData=" + binaryData +
                ", isClosed=" + isClosed +
                '}';
    }

    public ImageData blobToImageData(Blob blob) {
        List<Integer> binaryData = new ArrayList<>();
        try {
            InputStream inputStream = blob.getBinaryStream();
            int b;
            while ((b = inputStream.read()) != -1) {
                binaryData.add(b);
            }
        } catch (Exception ex) {
            System.err.println("Could not convert blob to binary data: "+ex.getMessage());
        }
        return new ImageData(binaryData, false);
    }


    public Blob toBlob() {
        try {
            int[] intArray = this.getBinaryData().stream().mapToInt(i -> i).toArray();
            byte[] byteArray = new byte[intArray.length];
            for (int i = 0; i < intArray.length; i++)
                byteArray[i] = (byte) intArray[i];
            return new SerialBlob(byteArray);
        } catch (Exception ex) {
            System.err.println("Could not convert binary data to blob: "+ex.getMessage());
        }
        return null;
    }

}
