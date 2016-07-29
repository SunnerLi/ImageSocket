from bluetooth import *     # External library you should install and import
from ImageSocket import *
import socket
import cv2

server_sock=ImageSocket.ImageSocket()
server_sock.socket(RFCOMM)
server_sock.settimeout()
server_sock.bind(("",PORT_ANY))
server_sock.listen(1)
port = server_sock.getsockname()[1]
print("Waiting for connection on RFCOMM channel %d" % port)

client_sock, client_info = server_sock.accept()
print("Accepted connection from ", client_info)
img = client_sock.recv(2000000)

# Show result and release resource
cv2.imshow('show', img)
cv2.waitKey()
client_sock.close()
cv2.destroyAllWindows()