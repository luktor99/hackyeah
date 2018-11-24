#!/usr/bin/env python3
# -*- coding: utf-8 -*-
from flask import Flask
from flask_cors import CORS
from flask_restful import Api

from src import Test, PathEntryPoint, LotteryTicket
from src.database import db

app = Flask(__name__)
cors = CORS(app, resources={r"/api/*": {"origins": "*"}})
api = Api(app)


@app.before_first_request
def setup():
    db.connect()


@app.route('/api/')
def hello_world():
    return '<b>API</b>'


api.add_resource(Test.Test, '/api/test')
api.add_resource(PathEntryPoint.PathEntryPoint, '/api/entry_point')
api.add_resource(LotteryTicket.LotteryTicket, '/api/lottery_ticket')

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
