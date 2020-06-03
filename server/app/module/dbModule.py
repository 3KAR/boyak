import pymysql
 
class Database():
    def __init__(self):
        self.db= pymysql.connect(host='boyak.c3ssrmbdhehz.ap-northeast-2.rds.amazonaws.com',
                                  user='root',
                                  password='12341234',
                                  db='boyak',
                                  charset='utf8')
        self.cursor= self.db.cursor(pymysql.cursors.DictCursor)
 
    def execute(self, query):
        self.cursor.execute(query) 
 
    def executeOne(self, query):
        self.cursor.execute(query)
        row= self.cursor.fetchone()
        return row
 
    def executeAll(self, query):
        self.cursor.execute(query)
        rows= self.cursor.fetchall()
        return rows
 
    def commit(self):
        self.db.commit()

    def close(self):
        self.cursor.close()
