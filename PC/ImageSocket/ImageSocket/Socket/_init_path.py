import sys
import os

def add_path(path):
	"""
		Add the module path
	"""
	if path not in sys.path:
		sys.path.insert(0, path)

this = os.path.dirname(__file__)

# Add log module to the PYTHONPATH
log_path = os.path.join(this, '..', 'LOG')
add_path(log_path)