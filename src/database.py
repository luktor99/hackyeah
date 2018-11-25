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
        self.cursor.execute('CREATE TABLE test (data_time DATE, data_text TEXT)')
        self.cursor.execute('CREATE TABLE entry_areas (center_lat DOUBLE, center_lng DOUBLE, radius INTEGER)')

    def drop(self):
        self.cursor.execute('DROP TABLE IF EXISTS test')
        self.cursor.execute('DROP TABLE IF EXISTS entry_areas')

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

    def putEntryArea(self, center_latitude, center_longitude, radius):
        self.cursor.execute('INSERT INTO entry_areas VALUES (?,?,?)', (center_latitude, center_longitude, radius))
        self.conn.commit()

    def getEntryAreas(self):
        self.cursor.execute('SELECT center_lat, center_lng, radius FROM entry_areas')
        columns = ('c_lat', 'c_lng', 'rad')
        results = []
        for row in self.cursor.fetchall():
            results.append(dict(zip(columns, row)))
        return json.dumps(results, ensure_ascii=False)

db = Database()

if __name__ == '__main__':
    # Drop and create database
    db.connect()
    db.drop()
    db.create()

    # Our coords: 52.2914847, 20.9992046
    db.putEntryArea(52.2941931, 20.9974818, 200)
    db.putEntryArea(52.2762293, 20.980959, 300)
    db.putEntryArea(52.2883235, 21.0186957, 400)
    db.putEntryArea(52.2494261, 20.9936908, 300)
    db.putEntryArea(52.2417624, 21.0050243, 150)

    db.close()
