import socket, Queue
import base64
import numpy as np
import cv2
import rtp

class ImageSocket_UDP():
	"""
		This class define the UDP ImageSocket contain and method
	"""
	# Constant definition
	Def = -1
	TCP = 0
	UDP = 1
	mode = Def

	sock = None					# Socket object
	hadSetTimeout = False		# The flag to record if set the timeout of the socket

	def __init__(self):
		"""
			Constructor of ImageSocket
		"""
		pass

	def socket(self, family, socketType):
		"""
			Construct the UDP socket
		"""
		self.sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)

	def setsockopt(self, n1, canReuse, n2):
		"""
			Set socket option
			It can set if the port number can be reuseable
		"""
		self.sock.setsockopt(n1, canReuse, n2)

	def bind(self, tuplee):
		"""
			Bind the port number and host address
			The input is the tuple
		"""
		self.sock.bind (tuplee)

	def settimeout(self, _time):
		"""
			Set the timeout of the udp socket
		"""
		self.sock.settimeout(100)

	def recvfrom(self, size):
		"""
			Recv the image string from the opposite
			This function force to set the timeout
		"""
		png = ""
		print "Had set timeout? ", self.hadSetTimeout
		if not self.hadSetTimeout:
			self.sock.settimeout(10)
			print "Set Default Timeout"
		try:
			data, addr = self.sock.recvfrom(20000000)
			print "length: ", len(data)
		except socket.timeout:
			data = ""
		return data

	def printWithASCII(self, data):
		"""
			Print the string by each character with ascii code
		"""
		for i in range(len(data)):
			print "arr[", i, "]: ", data[i], "\tcode: ", ord(data[i])

		r = rtp.RTP()
		header = r.DecodeHeader(data)
		print "Header string Length: ", len(header)
		r.printHeader(header)

	def close(self):
		"""
			Close the udp socket
		"""
		self.sock.close()
