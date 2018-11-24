#!/usr/bin/env python3
# -*- coding: utf-8 -*-
from flask import Flask, Response, jsonify, request
from flask_restful import Resource, Api
from flask_cors import CORS
from database import Database

app = Flask(__name__)
cors = CORS(app, resources={r"/api/*": {"origins": "*"}})
api = Api(app)
db = Database()


@app.route('/api/')
def hello_world():
    return '<b>API</b>'


@app.before_first_request
def setup():
    db.connect()


class Test(Resource):
    def get(self):
        return Response(db.getTest(), content_type='application/json; charset=utf-8')

    def post(self):
        db.putTest(request.json['data_time'], request.json['data_text'])
        return jsonify(success=True)


api.add_resource(Test, '/api/test')


# class Prediction(Resource):
#     def get(self, departure_time, is_workday, id_start, id_end):
#         return Response(db.getPrediction(id_start, id_end, departure_time, is_workday != 0), content_type='application/json; charset=utf-8')
#
#
# class PredictionsAll(Resource):
#     def get(self, departure_time, is_workday):
#         return Response(db.getPredictionsAll(departure_time, is_workday != 0), content_type='application/json; charset=utf-8')
#
#
# api.add_resource(Prediction, '/api/predictions/<int:departure_time>/<int:is_workday>/<int:id_start>/<int:id_end>')
# api.add_resource(, '/api/predictions/<int:departure_time>/<int:is_workday>')

if __name__ == '__main__':
    app.run(host="localhost")
