import socket, Queue
import base64

import numpy as np
import cv2

RECV_UDP_IP = "192.168.0.100"
port = 12345


def decode_base64(data):
    """Decode base64, padding being optional.

    :param data: Base64 data as an ASCII byte string
    :returns: The decoded byte string.

    """
    missing_padding = 4 - len(data) % 4
    if missing_padding:
        data += b'='* missing_padding
    return base64.decodestring(data)

def printt(data):
	for i in range(len(data)):
		print "arr[", i, "]: ", data[i], "\tcode: ", ord(data[i])


png = ""
sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock.bind ((RECV_UDP_IP, port))
sock.settimeout(100)

try:
	data, addr = sock.recvfrom(20000000)
	print "length: ", len(data)
except socket.timeout:
	data = ""
png += data


data = base64.b64decode(png)
printt(data)
sock.close()
