from ImageSocket import *
import socket
import cv2

RECV_UDP_IP = "192.168.0.101"
port = 12345

# Just As the process of usual socket
sock = ImageSocket.ImageSocket()
sock.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock.bind (("", port))
sock.listen(1)
sock.settimeout(20)
opSock, address = sock.accept()
img = opSock.recv(2000000)

# Show result and release resource
cv2.imshow('show', img)
cv2.waitKey()
sock.close()
cv2.destroyAllWindows()