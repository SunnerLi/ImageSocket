import ImageSocket
import socket

RECV_UDP_IP = "192.168.0.103"
port = 12345

sock = ImageSocket.ImageSocket_UDP()
sock.socket(socket.AF_INET,socket.SOCK_DGRAM)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock.bind ((RECV_UDP_IP, port))
img = sock.recvfrom(2000000)
img = img[:65]
sock.printWithASCII(img)
sock.close()