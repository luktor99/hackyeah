from flask import request
from flask_restful import Resource

from src.GeoMath import GeoMath


class LotteryTicket(Resource):
    def post(self):
        user_lat = request.json['lat']
        user_lng = request.json['lng']
        return GeoMath(user_lat, user_lng).get_lottery_ticket()
