from flask import Response, jsonify, request
from flask_restful import Resource

from src.database import db


class Test(Resource):
    def get(self):
        return Response(db.getTest(), content_type='application/json; charset=utf-8')

    def post(self):
        db.putTest(request.json['data_time'], request.json['data_text'])
        return jsonify(success=True)
