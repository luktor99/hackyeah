import math


class GeoMath:
    def __init__(self, lat_deg, lng_deg):
        self.lat_deg = float(lat_deg)
        self.lng_deg = float(lng_deg)

    def get_entry_point(self):
        return {'lat': self.lat_deg,
                'lng': self.lng_deg}

    def get_lottery_ticket(self):
        return {'lat': self.lat_deg,
                'lng': self.lng_deg}

    def __distance_to(self, lat, lng):
        lat_diff_rad = math.radians(lat - self.lat_deg)
        lng_diff_rad = math.radians(lng - self.lng_deg)

        a = math.sin(lat_diff_rad / 2) * math.sin(lat_diff_rad / 2) \
            + math.cos(math.radians(self.lat_deg)) * math.cos(math.radians(lat)) \
            * math.pow(math.sin(lng_diff_rad / 2), 2)

        c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))

        earth_radius_m = 6371e+3
        return earth_radius_m * c
