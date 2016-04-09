import socket, Queue
import base64

import numpy as np
import cv2

RECV_UDP_IP = "192.168.0.103"
port = 12345


def printt(data):
	for i in range(len(data)):
		print "arr[", i, "]: ", data[i], "\tcode: ", ord(data[i])
	#print "arr[0]: ", data[0], "\tcode: ", ord(data[0])


def decode_base64(data):
    """Decode base64, padding being optional.

    :param data: Base64 data as an ASCII byte string
    :returns: The decoded byte string.

    """
    missing_padding = 4 - len(data) % 4
    if missing_padding:
        data += b'='* missing_padding
    return base64.decodestring(data)


def formImg(data):
	png = []
	for i in range(len(data)):
		png.append(ord(data[i]))
	return png


png = ""
sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock.bind ((RECV_UDP_IP, port))
sock.settimeout(5)

for i in range(2):
	print "!"
	try:
		data, addr = sock.recvfrom(20000000)
		print "length: ", len(data)
	except socket.timeout:
		data = ""
	png += data


data = base64.b64decode(png + '=' * (-len(png) % 4))
data = base64.b64decode(png)
#data = decode_base64(png)

#printt(data)
sock.close()


"""
png = [137, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 4, 0, 0, 0, 4, 8, 6, 0, 0, 0, 169, 241, 158, 126, 0, 0, 0, 4, 115, 66, 73, 84, 8, 8, 8, 8, 124, 8, 100, 136, 0, 0, 0, 21, 73, 68, 65, 84, 8, 153, 99, 252, 207, 192, 240, 159, 1, 9, 48, 49, 160, 1, 194, 2, 0, 131, 209, 2, 6, 1, 215, 69, 130, 0, 0, 0, 0, 73, 69, 78, 68, 174, 66, 96, 130]
"""
png = formImg(data)
png = np.asarray(png, dtype=np.uint8)
png = cv2.imdecode(png, 1)
cv2.imshow('show', png)
cv2.waitKey()
