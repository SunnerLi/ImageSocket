import socket
import base64
import numpy as np
import rtp
import cv2


class ImageSocket_TCP():
	"""
		This class define the TCP ImageSocket contain and method
	"""
	sock = None					# Accept socket object
	opSock = None				# Work socket object
	hadSetTimeout = False		# The flag to record if set the timeout of the socket

	def __init__(self):
		"""
			Constructor of ImageSocket
		"""
		self.opSock = ImageSocket_Work()

	def socket(self, family, socketType):
		"""
			Construct the TCP socket
		"""
		self.sock = socket.socket(family,socketType)

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
		self.sock.bind(tuplee)

	def listen(self, times):
		"""
			Set the number of max listen request for one time
		"""
		self.sock.listen(times)

	def settimeout(self, _time):
		"""
			Set the timeout of the tcp socket
		"""
		self.sock.settimeout(_time)

	def accept(self):
		"""
			Accept the tcp connect request
			This function force to set timeout
		"""
		if not self.hadSetTimeout:
			self.settimeout(10)
		opSock, address = self.sock.accept()
		self.opSock.setWorkSocket(opSock)
		return self.opSock, address

	def close(self):
		"""
			Close the tcp socket
		"""
		self.sock.close()
		self.opSock.close()

class ImageSocket_Work():
	"""
		This class define the working socket contain and method
	"""
	workSock = None			# The working socket object
	hadSetTimeout = False		# The flag to record if set the timeout of the socket

	def __init__(self):
		"""
			Constructor of working socket
		"""
		pass

	def setWorkSocket(self, sock):
		"""
			Set the socket object.
			Or the afterware process would give error
		"""
		self.workSock = sock

	def close(self):
		"""
			Close the tcp socket
		"""
		self.workSock.close()

	def recv(self, size):
		"""
			Recv the image string from the opposite
			This function force to set the timeout
		"""
		self.workSock.setsockopt(socket.SOL_SOCKET, socket.SO_SNDBUF, 200000)  # Buffer size 8192
		if not self.hadSetTimeout:
			self.workSock.settimeout(10)

		png = ""
		while True:
			try:
				data = self.workSock.recv(2000000)
				print "length: ", len(data)
				r = rtp.RTP()
				png += data[65:]
				if r.decodeAndGetMarker(data[:65]) == 0:
					break
			except socket.timeout:
				data = ""
				break

		# Transform image string to numpy (Through OpenCV)
		png = base64.b64decode(png)
		png = self.formImgArr(png)
		png = self.oneD2Numpy(png)
		return png

	def formImgArr(self, data):
		"""
			Change the image string into 1D array
		"""
		png = []
		for i in range(len(data)):
			png.append(ord(data[i]))
		return png

	def oneD2Numpy(self, data):
		"""
			Decode the 1D image by the OpenCV
		"""
		data = np.asarray(data, dtype=np.uint8)
		data = cv2.imdecode(data, 1)
		return data
