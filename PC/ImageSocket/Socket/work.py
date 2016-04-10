import socket
import base64
import numpy as np
import rtp
import cv2


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

		roughPng = ""
		while True:
			try:
				data = self.workSock.recv(2000000)
				print "length: ", len(data)
				roughPng += data
				if len(data) < 66:
					break
				self.workSock.settimeout(0.5)
			except socket.timeout:
				data = ""
				break

		# Transform image string to numpy (Through OpenCV)
		png = self.cleanHeader(roughPng)
		png = base64.b64decode(png)
		png = self.formImgArr(png)
		png = self.oneD2Numpy(png)
		return png

	def cleanHeader(self, png):
		"""
			Drop the header (No consider RTP information in TCP)
		"""
		png = png[:len(png)-65]
		copy = png
		png = ""

		# Get the 1st header to show speed
		header = copy[:65]
		r = rtp.RTP()
		r.decode(header)

		# Drop the rest
		while len(copy) > 0:
			piece = copy[65:1445]
			png += piece
			copy = copy[1445:]
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