import sys
import os

def add_ImageSocket_path(path):
	"""
		Add the corresponding path package into project
	"""
	if path not in sys.path:
		sys.path.insert(0, path)



this = os.path.dirname(__file__)

# Add ImageSocket to PYTHONPATH
image_socket_path = os.path.join(this, 'ImageSocket', 'Socket')
add_ImageSocket_path(image_socket_path)

#Add RTP to PYTHONPATH
rtp_path = os.path.join(this, 'ImageSocket', 'RTP')
add_ImageSocket_path(rtp_path)