# -*- coding: utf-8 -*-
import sqlite3
import json

DB_FILE = "../database.db"


class Database:
    cursor = None
    conn = None

    def connect(self):
        # Connect to the database
        print("Connecting to database\n	->%s" % (DB_FILE))
        self.conn = sqlite3.connect(DB_FILE, check_same_thread=False)

        # Get cursor to perform queries
        self.cursor = self.conn.cursor()

    def close(self):
        self.conn.close()

    def create(self):
        self.cursor.execute('CREATE TABLE test (data_time data, data_text text)')

    def drop(self):
        self.cursor.execute('DROP TABLE IF EXISTS test')

    def putTest(self, data_time, data_text):
        self.cursor.execute('INSERT INTO test VALUES (?,?)', (data_time, data_text))
        self.conn.commit()

    def getTest(self):
        self.cursor.execute('SELECT data_time, data_text FROM test')
        columns = ('data_time', 'data_text')
        results = []
        for row in self.cursor.fetchall():
            results.append(dict(zip(columns, row)))
        return json.dumps(results, ensure_ascii=False)


if __name__ == '__main__':
    # Drop and create database
    db = Database()
    db.connect()
    db.drop()
    db.create()
    db.close()
