import socket, Queue
import base64
import numpy as np
import cv2
import rtp
import work

class ImageSocket():
	"""
		This class define the ImageSocket contain and method
	"""
	# Constant definition
	Def = -1
	TCP = 0
	UDP = 1
	mode = Def

	sock = None					# Socket object
	opSock = None				# Work socket object (TCP used)
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
		self.sock = socket.socket(family,socketType)
		if socketType == socket.SOCK_DGRAM:
			self.mode = self.UDP
		elif socketType == socket.SOCK_STREAM:
			self.mode = self.TCP
			self.opSock = work.ImageSocket_Work()
		else:
			print "This plugin didn't support other type of socket."

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

	def listen(self, times):
		"""
			( TCP Only )
			Set the number of max listen request for one time
		"""
		if self.mode == self.TCP:
			self.sock.listen(times)
		elif self.mode == self.UDP:
			print "UDP mode cannot call this function. Try to remove this call."
		else:
			print "Invalid mode cannot call this function."

	def settimeout(self, _time=10):
		"""
			Set the timeout of the udp socket
		"""
		self.sock.settimeout(_time)

	def accept(self):
		"""
			( TCP Only )
			Accept the tcp connect request
			This function force to set timeout
		"""
		if self.mode == self.TCP:
			if not self.hadSetTimeout:
				self.settimeout(10)
			opSock, address = self.sock.accept()
			self.opSock.setWorkSocket(opSock)
			return self.opSock, address
		elif self.mode == self.UDP:
			print "UDP mode cannot call this function. Try to remove this call."
		else:
			print "Invalid mode cannot call this function."

	def recvfrom(self, size):
		"""
			( UDP Only )
			Recv the image string from the opposite
			This function force to set the timeout
		"""
		if self.mode == self.UDP:
			if not self.hadSetTimeout:
				self.sock.settimeout(10)

			png = ""
			while True:
				try:
					data, addr = self.sock.recvfrom(size)
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
		elif self.mode == self.TCP:
			print "TCP mode cannot call this function! Try ' recv() '."
		else:
			print "Invalid mode cannot call this function."

	def printWithASCII(self, data):
		"""
			Print the string by each character with ascii code.
			This function just used to debug
		"""
		for i in range(len(data)):
			print "arr[", i, "]: ", data[i], "\tASCII: ", ord(data[i])

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

	def close(self):
		"""
			Close the socket
		"""
		self.sock.close()
		if self.mode == self.TCP:
			self.opSock.close()