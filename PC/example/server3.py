import _init_path
import ImageSocket
import socket
import cv2

RECV_UDP_IP = "192.168.0.101"
port = 12345


sock = ImageSocket.ImageSocket()
sock.socket(socket.AF_INET,socket.SOCK_DGRAM)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock.bind ((RECV_UDP_IP, port))
img = sock.recvfrom(2000000)
cv2.imshow('show', img)
cv2.waitKey()
sock.close()

"""
sock = ImageSocket.ImageSocket()
sock.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock.bind (("", port))
sock.listen(1)
sock.settimeout(20)
opSock, address = sock.accept()
img = opSock.recv(2000000)
cv2.imshow('show', img)
cv2.waitKey()
sock.close()
"""