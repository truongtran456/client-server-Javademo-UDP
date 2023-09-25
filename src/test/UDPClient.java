package test;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Tạo socket máy khách
            socket = new DatagramSocket();

            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 9876;

            System.out.print("Nhập số a: ");
            int a = scanner.nextInt();
            System.out.print("Nhập số b: ");
            int b = scanner.nextInt();

            System.out.println("Chọn phép tính (+, -, *, /): ");
            String operator = scanner.next();

            // Gửi số a, b và phép tính đến máy chủ
            String message = a + "," + b + "," + operator;
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

            // Gửi gói tin đến máy chủ
            socket.send(sendPacket);
//            System.out.println("Đã gửi yêu cầu đến máy chủ.");

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            // Nhận kết quả từ máy chủ
            socket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());

            // In ra kết quả
            System.out.println("Kết quả phép tính là: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
