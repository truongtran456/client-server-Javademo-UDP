package test;

import java.net.*;
import java.io.*;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Tạo socket máy chủ với cổng cụ thể (ở đây là cổng 9876)
            socket = new DatagramSocket(9876);

            byte[] receiveData = new byte[1024];

            System.out.println("Máy chủ UDP đang lắng nghe...");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                // Chuyển đổi dữ liệu nhận được thành chuỗi
                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Tách chuỗi thành số a, b và phép tính
                String[] parts = receivedMessage.split(",");
                if (parts.length == 3) {
                    int a = Integer.parseInt(parts[0]);
                    int b = Integer.parseInt(parts[1]);
                    String operator = parts[2];

                    // Thực hiện phép tính
                    int result = 0;
                    switch (operator) {
                        case "+":
                            result = a + b;
                            break;
                        case "-":
                            result = a - b;
                            break;
                        case "*":
                            result = a * b;
                            break;
                        case "/":
                            if (b != 0) {
                                result = a / b;
                            } else {
                                System.out.println("Lỗi: Không thể chia cho 0.");
                            }
                            break;
                        default:
                            System.out.println("Lỗi: Phép tính không hợp lệ.");
                            break;
                    }

                    // Chuyển kết quả thành chuỗi và gửi trả về máy khách
                    String response = Integer.toString(result);
                    byte[] sendData = response.getBytes();
                    InetAddress clientAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    socket.send(sendPacket);
                } else {
                    System.out.println("Dữ liệu không hợp lệ.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
