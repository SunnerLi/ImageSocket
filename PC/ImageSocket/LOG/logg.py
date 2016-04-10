needShowLog = False				# log control flag
SHOW = 1						# constant to show log
HIDE = 0						# constant to hide log

def showLog(status):
	"""
		Control the flag to show the log
	"""
	needShowLog = SHOW

def hideLog(status):
	"""
		Control the flag to hide the log
	"""
	needShowLog = HIDE

def LOG(string):
	if needShowLog:
		print "--< ImageSocket >--   :", string

def LOG(string, intt):
	if needShowLog:
		print "--< ImageSocket >--   :", string, intt