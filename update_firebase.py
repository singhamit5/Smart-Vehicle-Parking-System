import mysql.connector
from mysql.connector import Error
from mysql.connector import errorcode
from firebase import firebase

fb = firebase.FirebaseApplication('https://fireapp-19296.firebaseio.com/', None)
while True:
	try:
		conn = mysql.connector.connect(host='localhost',database='iot',user='rasp',password='*****')
		cursor = conn.cursor()
		cursor.execute("select * from pending")
		rows = cursor.fetchall()
		for row in rows:
			print row[1]
			sl_no = row[0]
			path_fb = "parking/" + str(row[2]) + "/" + row[1];
			fb.put(path_fb,"status", row[3])
			query = "DELETE FROM pending WHERE sl_no = %(sl_no)s"
			cursor.execute(query, { 'sl_no' : sl_no })
			conn.commit()
		cursor.close()
	except mysql.connector.Error as error :
		print("Failed to update record to database rollback: {}".format(error))
		conn.rollback()
	finally:
		if(conn.is_connected()):
			conn.close()
