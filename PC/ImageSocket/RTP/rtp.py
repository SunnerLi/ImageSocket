import base64
import datetime

class RTP():
	"""
		This class define the RTP decode process
	"""
	# Header flag
	version = -1								# RTP version number
	padding = -1								# The flag if use the cryption algorithm
	extension = -1								# The flag if header is extend
	cc = -1										# The number of cc
	marker = -1									# The falg if end the image
	pt = -1										# The type of media(Ex. png)
	imageIndex = -1								# The imageIndex flag (Useless now)
	minute = -1									# The minute of time to encode the package
	second = -1									# The second of time to encode the package
	millisecond = -1							# The millisecond of time to encode the package
	ssrc = -1									# The number of ss


	def decode(self, data):
		"""
			The main function of decoding.
			Arg:	the base64 string
		"""
		data = self.DecodeBase64(data)
		#self.printHeaderString(data)
		self.decodeArr(data)
		self.printHeader()

	def decodeAndGetMarker(self, data):
		"""
			Get the marker to judge if end the recving
			Arg:	the base64 string
		"""
		self.decode(data)
		return self.marker

	def DecodeBase64(self, data):
		"""
			Decode the base64 string
			Arg:	the base64 string
		"""
		missing_padding = 4 - len(data) % 4
		if missing_padding:
			data += b'='* missing_padding
		return base64.decodestring(data)

	def printHeader(self):
		"""
			Print the result of decoding
		"""
		if self.version == -1:
			print "Haven't call decodeArr"
		else:
			
			print "Version: ", self.version
			print "Padding: ", self.padding
			print "Extention: ", self.extension
			print "CC: ", self.cc
			print "Marker: ", self.marker
			print "Pt: ", self.pt
			print "Image index: ", self.imageIndex
			print "Minute: ", self.minute
			print "Second", self.second
			print "Millisecond: ", self.millisecond

			tt = datetime.datetime.now()
			print "now:         ", tt.minute, ':', tt.second, ':', str(tt.microsecond)[:3]


	def printHeaderString(self, headerString):
		"""
			Print the string bit by bit including the ASCII order
		"""
		for i in range(len(headerString)):
			print "arr[", i, "]: ", headerString[i], "\tcode: ", ord(headerString[i])

	def decodeArr(self, data):
		"""
			Decode each flag of RTP header
		"""
		if not len(data) == 48:
			print "The header is invalid"
		else:
			# Change to int list
			res = []
			for i in range(len(data)):
				res.append(ord(data[i]))
			data = res

			# Decode each element
			self.version = data[3] >> 6
			self.padding = (data[3] & 0x20) >> 5
			self.extension = (data[3] & 0x10) >> 4
			self.cc = (data[3] & 0x0F)
			self.marker = data[7] >> 7
			self.pt = data[7] & 0x7F
			self.imageIndex = (data[11] << 8) + data[15]
			self.minute = data[19]
			self.second = data[23]
			self.millisecond = (data[27] << 3) + ((data[31] & 0xE0) >> 5)
