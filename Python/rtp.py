import base64

class RTP():
	"""
		This class define the RTP decode process
	"""
	# Header flag
	version = -1								# RTP version number
	padding = -1								# The flag if use the cryption algorithm
	extension = -1								# The flag if header is extend
	cc = -1										# The number of cc
	pt = -1										# The type of media(Ex. png)
	ssrc = -1									# The number of ss

	def DecodeHeader(self, data):
		missing_padding = 4 - len(data) % 4
		if missing_padding:
			data += b'='* missing_padding
		return base64.decodestring(data)

	def printHeader(self, headerString):
		for i in range(len(headerString)):
			print "arr[", i, "]: ", headerString[i], "\tcode: ", ord(headerString[i])